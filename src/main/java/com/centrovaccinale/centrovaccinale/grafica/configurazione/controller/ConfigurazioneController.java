package com.centrovaccinale.centrovaccinale.grafica.configurazione.controller;

import com.centrovaccinale.centrovaccinale.utils.ConnectDB;
import com.centrovaccinale.centrovaccinale.utils.LoadStage;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ConfigurazioneController implements Initializable{
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
            if(hostText.getText().isEmpty() || passwordText.getText().isEmpty()){
                errorLabel.setText("");
                configuraBtn.setDisable(true);
            }else{
                configuraBtn.setDisable(false);
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
        errorLabel.setText("");
        Task<ConnectDB> task = new Task<>() {
            @Override
            protected ConnectDB call() {
                return ConnectDB.setInstance(hostText.getText(), usernameText.getText(), passwordText.getText());
            }
        };

        errorLabel.setTextFill(Color.GREEN);
        errorLabel.setText("Connessione al DB in corso...");

        new Thread(task).start();

        task.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, workerStateEvent -> {
            if(task.getValue() != null){
                try {
                    LoadStage.loadStage(event);
                } catch (IOException e) {
                    errorLabel.setTextFill(Color.RED);
                    errorLabel.setText(e.getMessage());
                }
            }else{
                errorLabel.setTextFill(Color.RED);
                errorLabel.setText("Errore: controlla che l'host, username e password siano corretti!");
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        errorLabel.setText("");
    }
}
