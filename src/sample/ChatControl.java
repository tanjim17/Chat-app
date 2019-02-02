package sample;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.io.*;

import static sample.Main.client;

public class ChatControl {

    @FXML public ListView<String> listView;
    @FXML private TextArea textArea;
    @FXML public TextArea display;
    @FXML public TextField name;
    @FXML public TextField filebox;

   public void show(){
        SMessage sMessage = new SMessage("show", client.username);
        client.out.println(sMessage.generateShowMessage());
    }

    public void logout(){
        SMessage sMessage = new SMessage("logout", client.username);
        client.out.println(sMessage.generateLogOutMessage());
    }

    public void send(){
        if(textArea.getText()!=null && !listView.getSelectionModel().isEmpty()){
            CMessage cMessage = new CMessage(listView.getSelectionModel().getSelectedItem(),textArea.getText());
            client.out.println(cMessage.generateMessage());
            textArea.clear();
        }
    }

    public void broadcast(){
       if(textArea.getText()!=null && client.isAdmin){
           BMessage bMessage = new BMessage(textArea.getText());
           client.out.println(bMessage.generateMessage());
           textArea.clear();
       }

    }

    public void sendFile(){
        if(textArea.getText()!=null && !listView.getSelectionModel().isEmpty()
        && filebox.getText()!=null){

            fileTransfer(filebox.getText());

            textArea.clear();
        }
    }

    private void fileTransfer(String filename) {
        try
        {
            File file = new File(filename);
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            OutputStream os = client.getSocket().getOutputStream();
            byte[] contents;
            long fileLength = file.length();
            System.out.println(fileLength);
            CMessage cMessage = new CMessage(listView.getSelectionModel().getSelectedItem(),textArea.getText(),filebox.getText(),(int)fileLength);
            client.out.println(cMessage.generateFileMessage());

            long current = 0;

            while(current!=fileLength){
                int size = 10000;
                if(fileLength - current >= size)
                    current += size;
                else{
                    size = (int)(fileLength - current);
                    current = fileLength;
                }
                contents = new byte[size];
                bis.read(contents, 0, size);
                os.write(contents);
            }
            os.flush();
            System.out.println("File sent successfully!");
        }
        catch(Exception e)
        {
            System.err.println("Could not transfer file.");
        }
    }

}
