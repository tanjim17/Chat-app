package sample;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import static sample.Main.client;

public class LogInControl {

    @FXML private TextField username_field;
    @FXML private PasswordField password_field;
    @FXML private TextField actype_field;


    public void login(){

        if(username_field.getText()!=null &&
                password_field.getText()!=null &&
                actype_field.getText()!=null){

            LMessage lMessage = new LMessage(username_field.getText(),
                    password_field.getText(),
                    actype_field.getText());

            client.out.println(lMessage.generateMessage());
        }
    }


}
