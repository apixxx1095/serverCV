package com.centrovaccinale.centrovaccinale.grafica.configurazione.controller;

import com.centrovaccinale.centrovaccinale.utils.ConnectDB;
import com.centrovaccinale.centrovaccinale.utils.LoadStage;
import com.centrovaccinale.centrovaccinale.utils.RunnerRMI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConfigurazioneController implements Initializable{
    private static final String IPV4_REGEX =
            "^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                    "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                    "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                    "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";

    private static final Pattern IPv4_PATTERN = Pattern.compile(IPV4_REGEX);

    @FXML
    private TextField hostText;
    @FXML
    private TextField usernameText;
    @FXML
    private PasswordField passwordText;
    @FXML
    private Button configuraBtn;
    @FXML
    private Label errorLabel;

    @FXML
    private void keyEvent(KeyEvent keyEvent){
        Object evt = keyEvent.getSource();
        if(evt.equals(hostText)){
            if(keyEvent.getCode() == KeyCode.SPACE || keyEvent.getCode() == KeyCode.TAB){
                keyEvent.consume();
            }
            else if(hostText.getText().concat(keyEvent.getText()).equals("localhost") || isValidInet4Address(hostText.getText())) {
                errorLabel.setTextFill(Color.GREEN);
                errorLabel.setText("Formato IP corretto");
                configuraBtn.setDisable((usernameText.getText().isEmpty()) && (passwordText.getText().isEmpty()));
            }
            else if(hostText.getText().isEmpty()){
                errorLabel.setText("");
                configuraBtn.setDisable(true);
            }
            else{
                errorLabel.setTextFill(Color.RED);
                errorLabel.setText("IP inserito non ha un formato corretto!");
                configuraBtn.setDisable(true);
            }

        }
        else if(evt.equals(usernameText) || evt.equals(passwordText)){
            errorLabel.setText("");
            if (!usernameText.getText().isEmpty() && !passwordText.getText().isEmpty()) {
                configuraBtn.setDisable(hostText.getText().isEmpty());
            }else {
                configuraBtn.setDisable(true);
            }
        }
    }

    @FXML
    private void configurazioneConnessione(ActionEvent event){
        try {
            ConnectDB.setInstance(hostText.getText(), usernameText.getText(), passwordText.getText());
            LoadStage.loadStage(event);
        } catch (SQLException | UnknownHostException e) {
            errorLabel.setTextFill(Color.RED);
            errorLabel.setText(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        errorLabel.setText("");
    }


    private boolean isValidInet4Address(String ip) {
        if (ip == null) {
            return false;
        }
        Matcher matcher = IPv4_PATTERN.matcher(ip);

        return matcher.matches();
    }
}
