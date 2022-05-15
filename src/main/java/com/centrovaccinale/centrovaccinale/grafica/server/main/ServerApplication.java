package com.centrovaccinale.centrovaccinale.grafica.server.main;

import com.centrovaccinale.centrovaccinale.utils.ConnectDB;
import com.centrovaccinale.centrovaccinale.utils.RunnerRMI;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.NoSuchObjectException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;

public class ServerApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        System.out.println("Entro nello start() di ServerApplication");
        FXMLLoader fxmlLoader = new FXMLLoader(ServerApplication.class.getResource("/com/centrovaccinale/centrovaccinale/server/ServerView.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 1000, 600);

        primaryStage.setTitle("Server");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    @Override
    public void stop(){
        System.out.println("Entro nello stop() di ConfigurazioneApplication");
        try {
            if (ConnectDB.getInstance().getConnection() != null) {
                ConnectDB.getInstance().closeConnection();
            }
            if(RunnerRMI.getInstance() != null){
                if(UnicastRemoteObject.unexportObject(RunnerRMI.getInstance().getServer(), true)){
                    System.out.println("Server chiuso");
                    System.exit(0);
                }
            }
        } catch (NoSuchObjectException | SQLException e) {
            e.printStackTrace();
        }
    }
}
