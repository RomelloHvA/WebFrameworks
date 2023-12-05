package app.rest;

import app.exceptions.NotAcceptableException;
import app.models.User;
import app.utility.APIConfig;
import app.utility.JWToken;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    @Autowired
    private APIConfig apiConfig;

    @PostMapping("login")
    public ResponseEntity<Object> login(@RequestBody ObjectNode user){
        // Check for null user

        if (user == null || user.isEmpty()){
            return ResponseEntity.badRequest().body("email, password cannot be empty");
        }
        // Check if the JSON has the appropriate fields
        if (user.has("email") && user.has("password")) {
            String email = user.get("email").asText();
            String password = user.get("password").asText();
            // Check if the email has @ in it.
            if (!emailContains("@", email)){
                throw new NotAcceptableException("Email needs to contain: @");
            }
            // Successful login creates a new user.
            if (email.split("@")[0].equals(password)){
                User newUser = new User();
                newUser.setRandomId();
                newUser.setEmail(email);
                newUser.setHashedPassword(password);
                if (user.has("role") && Objects.equals(user.get("role").asText(), "admin")){
                    newUser.setRole(user.get("role").asText());
                } else {
                    newUser.setRole("guest");
                }

                JWToken jwToken = new JWToken(newUser.getName(), newUser.getId(), newUser.getRole());
                String tokenString = jwToken.encode(apiConfig.getJwtIssuer(), apiConfig.getJwtPassphrase(),
                        (int) apiConfig.getJwtExpirationTime());
                return ResponseEntity.accepted().header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenString).body(newUser);
            }else {
                throw new NotAcceptableException("Password and email fields not matching.");
            }
        } else {
            // Missing email and password fields.
            throw new NotAcceptableException("Email and password fields missing");
        }

    }

    private boolean emailContains(String character, String email){
        return email.contains(character);
    }

    @ExceptionHandler(NotAcceptableException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ResponseEntity<String> handleNotAcceptableException(NotAcceptableException exception) {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(exception.getMessage());
    }
}
