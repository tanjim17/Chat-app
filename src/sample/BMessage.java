package sample;

public class BMessage {

    private String text;

    public BMessage(String text) {
        this.text = text;
    }

    public String generateMessage(){

        return ("B#" + text);
    }
}

