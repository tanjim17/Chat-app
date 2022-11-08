package sample;

public class CMessage {

    private String receiver;
    private String text;
    private String filename;
    private Integer filesize;

    public CMessage(String receiver, String text) {
        this.receiver = receiver;
        this.text = text;
    }

    public CMessage(String receiver, String text, String filename,int filesize) {
        this.receiver = receiver;
        this.text = text;
        this.filename = filename;
        this.filesize = filesize;
    }

    public String generateMessage(){

        return ("C#"+receiver+"#"+text);
    }

    public String generateFileMessage(){

        return ("C#"+receiver+"#"+text+"#"+filename+"#"+filesize.toString());
    }
}

