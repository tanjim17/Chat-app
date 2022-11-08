package sample;

public class SMessage {

    private String command;
    private String username;

    public SMessage(String command,String username) {
        this.command = command;
        this.username = username;
    }

    public String generateShowMessage(){

        return ("S#" + command + "#" + username+ " wants to see active user list.");
    }

    public String generateLogOutMessage(){

        return ("S#" + command + "#" + username+ " wants to log out.");
    }
}
