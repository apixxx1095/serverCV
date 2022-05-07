module com.centrovaccinale.centrovaccinale {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    requires java.rmi;
    requires java.sql;

    opens com.centrovaccinale.centrovaccinale to javafx.fxml;
    exports com.centrovaccinale.centrovaccinale;


    // APPLICAZIONE CONFIGURAZIONE
    exports com.centrovaccinale.centrovaccinale.grafica.configurazione.controller;
    opens com.centrovaccinale.centrovaccinale.grafica.configurazione.controller to javafx.fxml;
    exports com.centrovaccinale.centrovaccinale.grafica.configurazione.main;
    opens com.centrovaccinale.centrovaccinale.grafica.configurazione.main to javafx.fxml;

    // APPLICAZIONE SERVER
    exports com.centrovaccinale.centrovaccinale.grafica.server.controller;
    opens com.centrovaccinale.centrovaccinale.grafica.server.controller to javafx.fxml;
    exports com.centrovaccinale.centrovaccinale.grafica.server.main;
    opens com.centrovaccinale.centrovaccinale.grafica.server.main to javafx.fxml;


    exports com.centrovaccinale.centrovaccinale.rmi;
    exports com.centrovaccinale.centrovaccinale.utils;
    exports com.centrovaccinale.centrovaccinale.entita;
}