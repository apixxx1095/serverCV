package com.centrovaccinale.centrovaccinale.utils;

import com.centrovaccinale.centrovaccinale.grafica.server.main.ServerApplication;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

/**
 * @author Jean Pierre Sotamba 728447 VA
 * @author Simone Caputo 736816 VA
 * @author Laura Gjetja 738629 VA
 * @author Erica Bonetti 740946 VA
 * @version 0.0.1
 */
public class LoadStage {
    /**
     * Questo metodo si occupa del caricamento di un nuovo Stage.
     * @param event Evento corrente.
     * @throws IOException Nel caso in cui non si trovi il file fxml da aprire.
     */
    public static void loadStage(Event event) throws IOException {
        // Questa riga riassume la parte sotto
        //((Node)(event.getSource())).getScene().getWindow().hide();

        //Parte sotto:
        Object evenSource = event.getSource();
        Node sourceAsNode = (Node) evenSource;
        Scene oldScene = sourceAsNode.getScene();
        Window window = oldScene.getWindow();
        Stage stage = (Stage) window;
        stage.hide();

        new ServerApplication().start(stage);
    }
}