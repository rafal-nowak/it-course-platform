package pl.sages.javadevpro.projecttwo.config;


// TODO czy to leży w dobrym pakiecie
public class CredentialsDTO {
    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
