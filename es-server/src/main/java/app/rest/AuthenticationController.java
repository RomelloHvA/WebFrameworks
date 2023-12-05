package app.rest;

import app.exceptions.NotAcceptableException;
import app.models.User;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

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
                return ResponseEntity.accepted().body(newUser);
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
