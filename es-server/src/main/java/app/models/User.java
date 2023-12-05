package app.models;

import jakarta.persistence.*;

@Entity
@Table
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String email;
    private String hashedPassword;
    private String role;

    public User(){}

    public User(String name, String email, String hashedPassword, String role) {
        this.id = generateRandomId();
        this.name = name;
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setRandomId(){
        this.id = generateRandomId();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;

        if (email != null && email.contains("@")) {
            setName(email.split("@")[0]);
        }
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    private long generateRandomId() {
        return (int) Math.round(Math.random() * 10000);
    }
}
