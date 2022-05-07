package com.centrovaccinale.centrovaccinale.grafica.configurazione.main;

import com.centrovaccinale.centrovaccinale.utils.ConnectDB;
import com.centrovaccinale.centrovaccinale.utils.RunnerRMI;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.NoSuchObjectException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.Objects;

public class ConfigurazioneApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Caricamento del file fxml
        Parent root = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/com/centrovaccinale/centrovaccinale/configurazione/ConfigurazioneView.fxml"))));
        //FXMLLoader fxmlLoader = new FXMLLoader(ConfigurazioneApplication.class.getResource("configurazione/ConfigurazioneView.fxml"));

        // Tale file lo inizializziamo nella scena
        Scene scene = new Scene(root, 380, 400);

        //Carichiamo la scena nel nostro stage
        primaryStage.setTitle("Configurazione Connessione DB");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    @Override
    public void stop(){
        System.out.println("Entro nello stop() di ConfigurazioneApplication");
        try {
            if(RunnerRMI.getInstance() != null){
                if(UnicastRemoteObject.unexportObject(RunnerRMI.getInstance().getServer(), true)){
                    System.out.println("Server chiuso");
                    System.exit(0);
                }
            }
            if (ConnectDB.getInstance().getConnection() != null) {
                ConnectDB.getInstance().getConnection().close();
            }
        } catch (NoSuchObjectException | SQLException e) {
            e.printStackTrace();
        }
    }
}
