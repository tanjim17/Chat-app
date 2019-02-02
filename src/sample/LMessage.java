package sample;

public class LMessage {

    private String username;
    private String password;
    private String type;

    public LMessage(String username, String password, String type) {
        this.username = username;
        this.password = password;
        this.type = type;
    }

    public String generateMessage(){

        return ("L#"+username+"#"+password+"#"+type);
    }
}
