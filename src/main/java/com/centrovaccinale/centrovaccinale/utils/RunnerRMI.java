package com.centrovaccinale.centrovaccinale.utils;


import com.centrovaccinale.centrovaccinale.grafica.server.controller.ServerController;
import com.centrovaccinale.centrovaccinale.rmi.ServerImpl;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Classe che crea un'istanza della classe ServerImpl, quindi viene reso pubblico
 * il riferimento del server. Viene fatto partire il registry sulla porta 1099.
 * Il riferimento del server viene registrato all'interno del registry.
 * @author Jean Pierre Sotamba 728447 VA
 * @author Simone Caputo 736816 VA
 * @author Laura Gjetja 738629 VA
 * @author Erica Bonetti 740946 VA
 * @version 0.0.1
 * */
public class RunnerRMI {
    // CAMPI DI CLASSE
    private static RunnerRMI instance;
    private static String host;
    private static int port;

    // CAMPI DELLA ISTANZA RunnerRMI
    private ServerImpl server;
    private Registry registry;
    private RunnerRMI(String host, int port, ServerController serverController){
        try {
            this.server = new ServerImpl(serverController);
            this.registry = LocateRegistry.createRegistry(port);
            registry.rebind("ServerCentroVaccinale", server);
            System.err.println("Server " + server + " ready.");
            System.err.println("Host: " + host);
            System.err.println("Port: " + port);
            System.out.println();

        } catch (RemoteException e) {
            System.err.println("Server exception: " + e);
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Imposta la porta del registry.
     * @param host Host del server.
     * @param port Porta del server.
     * @param serverController
     */
    public static synchronized void setInstance(String host, int port, ServerController serverController){
        RunnerRMI.host = host;
        RunnerRMI.port = port;
        if(instance == null){
            instance = new RunnerRMI(host, port, serverController);
        }else {
            instance = null;
        }
    }
    /**
     * @return Oggetto che inizializza il server e contiene il suo riferimento.
     */
    public static synchronized RunnerRMI getInstance(){
        return instance;
    }

    /**
     * @return ServerImpl restituisce la referenza del server.
     */
    public ServerImpl getServer() {
        return server;
    }

    /**
     * @return Registry oggetto creato.
     */
    public Registry getRegistry() {
        return registry;
    }

    /**
     * @return IP del server in ascolto.
     */
    public static String getHost() {
        return host;
    }

    /**
     * @return Porta del server in ascolto.
     */
    public static int getPort() {
        return port;
    }
}
