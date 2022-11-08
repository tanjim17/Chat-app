package sample;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Client {

    private Socket socket;
    public BufferedReader in;
    public PrintWriter out;
    public String username;
    public boolean isAdmin;
    private LogInControl lc;
    private ChatControl cc;
    private ObservableList<String> nameList = FXCollections.observableArrayList();

    Client(){

        try {
            socket = new Socket("localhost",7777);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(),true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        new Thread(()->receive(),"client receive thread").start();
    }

    public void setLogInControl(LogInControl lc){
        this.lc = lc;
    }

    private void receive() {

        while(true){
            try {
                String data = in.readLine();
                System.out.println(data);
                parseData(data);

            } catch (IOException e) {
                continue;
            }
        }
    }

    private void parseData(String data){

        String [] message = data.split("#");

        switch (message[0]){

            case "accept":
                this.username = message[1];
                if(message[2].equals("admin"))
                    isAdmin = true;
                else isAdmin = false;
                changeScene();
                break;

            case "show":
                Platform.runLater(
                        () -> {
                            nameList.clear();
                            for(int i=1; i<message.length; i++){
                                nameList.add(message[i]);
                            }
                        }
                );
                break;

            case "disconnect":
                System.exit(0);
                break;

            case "broad":
                cc.display.appendText(message[1]+"\n");
                break;

            case "text":
                cc.display.appendText(message[1]+"\n");
                break;

            case "file":
                receiveFile(message[1],message[2]);
        }

    }

    private void changeScene(){

        Platform.runLater(
                () -> {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("box.fxml"));
                        Parent root = loader.load();

                        ChatControl cc = loader.getController();
                        this.cc = cc;

                        Main.stage.close();
                        Main.stage.setTitle("TABLE");
                        Main.stage.setScene(new Scene(root, 1000, 650));
                        Main.stage.show();

                        cc.name.setText(username);
                        cc.listView.setItems(nameList);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    private void receiveFile(String filename, String size){
        try
        {
            int filesize=Integer.parseInt(size);
            byte[] contents = new byte[10000];

            FileOutputStream fos = new FileOutputStream(username+filename);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            InputStream is = socket.getInputStream();

            int bytesRead;
            int total=0;

            while(total!=filesize)
            {
                bytesRead=is.read(contents);
                total+=bytesRead;
                bos.write(contents, 0, bytesRead);
            }
            bos.flush();
        }
        catch(Exception e)
        {
            System.err.println("Could not transfer file.");
        }
    }

    public Socket getSocket(){
        return socket;
    }


}

