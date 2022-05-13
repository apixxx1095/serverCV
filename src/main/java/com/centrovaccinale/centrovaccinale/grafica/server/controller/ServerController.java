package com.centrovaccinale.centrovaccinale.grafica.server.controller;

import com.centrovaccinale.centrovaccinale.utils.RunnerRMI;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
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
    private Button btnPulisciConsole;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            counter.setText("0");
            consoleLogs.textProperty().addListener((observableValue, s, t1) -> consoleLogs.setScrollTop(Double.MAX_VALUE));
            btnPulisciConsole.setOnAction(actionEvent -> cleanConsole());
            String host = InetAddress.getLocalHost().getHostAddress();
            int port = 1099;
            RunnerRMI.setInstance(host, port, this);
            hostField.setText(RunnerRMI.getHost());
            portField.setText(RunnerRMI.getPort() + "");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public synchronized TextArea getConsoleLogs(){
        return consoleLogs;
    }

    public synchronized TextField getCounterClientLabel(){
        return counter;
    }

    public synchronized void cleanConsole(){
        System.out.println("Eseguo cleanConsole()\n");
        consoleLogs.setText("");
    }
}
