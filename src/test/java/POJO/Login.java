package POJO;

public class Login {
    private String UserEmail;
    private String UserPassword;

    public String getUserEmail() {
        return UserEmail;
    }

    public void setUserEmail(String username) {
        this.UserEmail = username;
    }

    public String getUserPassword() {
        return UserPassword;
    }

    public void setUserPassword(String password) {
        this.UserPassword = password;
    }
}
