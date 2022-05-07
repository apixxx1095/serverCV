package com.centrovaccinale.centrovaccinale.grafica.server.controller;

import com.centrovaccinale.centrovaccinale.rmi.ServerImpl;
import com.centrovaccinale.centrovaccinale.utils.RunnerRMI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ServerController implements Initializable {
    @FXML
    private TextField counter;
    @FXML
    private TextArea consoleLogs;
    @FXML
    private TextField hostField;
    @FXML
    private TextField portField;

    @FXML
    private synchronized void cleanConsole(ActionEvent actionEvent){
        System.out.println("Eseguo cleanConsole()\n");
        consoleLogs.setText("");
        consoleLogs.appendText("");
    }

    @Override
    public synchronized void initialize(URL url, ResourceBundle resourceBundle) {
        counter.setText("0");
        hostField.setText(RunnerRMI.getHost());
        portField.setText(RunnerRMI.getPort() + "");
        ServerImpl.setServerController(this);
    }
    public synchronized TextArea getConsoleLogs(){
        return consoleLogs;
    }
    public synchronized TextField getCounterClientLabel(){
        return counter;
    }
}
