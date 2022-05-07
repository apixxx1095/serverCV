package com.centrovaccinale.centrovaccinale.utils;

import java.sql.*;

/**
 * Classe che si occupa delle operazioni di ricerca ed inserimento di tuple.
 * @author Jean Pierre Sotamba 728447 VA
 * @author Simone Caputo 736816 VA
 * @author Laura Gjetja 738629 VA
 * @author Erica Bonetti 740946 VA
 * @version 0.0.1
 * */
public class ConnectDB {
    private static String username = "postgres";
    private static String password = "admin";
    private static String dbHost = "localhost";
    private static final String dbName = "centrovaccinale";
    private static ConnectDB instance = null;

    private Connection conn;

    private ConnectDB(String host, String username, String password) throws SQLException {
        String stringConnection = String.format("jdbc:postgresql://%s:5432/%s", host, dbName);
        conn = DriverManager.getConnection(stringConnection, username, password);
    }

    /**
     * Questo metodo restituisce un'istanza di un oggetto contenente
     * il riferimento alla connessione con il Database.
     * Questo metodo server per impostare i parametri di connessione al DB.
     * @param host ip del server
     * @param username username per db
     * @param password password del db.
     * @return ConnectDB oggetto contenente la connessione al DB
     * @throws SQLException Nel caso in cui ci siano errori di connessione col DB.
     */
    public static synchronized ConnectDB setInstance(String host, String username, String password) throws SQLException {
        ConnectDB.dbHost = host;
        ConnectDB.username = username;
        ConnectDB.password = password;
        if(instance == null) {
            instance = new ConnectDB(host, username, password);
        }
        return instance;
    }
    /**
     * Metodo che restituisce l'istanza dell'oggetto
     * contenente la connessione al DB.
     * @return Un oggetto contenente il riferimento della connessione a DB.
     * */
    public static synchronized ConnectDB getInstance() {
        if(instance == null) {
            try {
                instance = new ConnectDB(dbHost, username, password);
            } catch (SQLException e) {
                System.err.println(e.getMessage());
                instance = null;
            }
        }
        return instance;
    }
    // TODO: VERIFICARE QUANDO EFFETTIVAMENTE CHIUDERE LA CONNESSIONE

    /**
     * Metodo per chiusura della connessione al DB.
     * @throws SQLException viene lanciata l'eccezione nel caso di problemi.
     */
    public synchronized void closeConnection() throws SQLException {
        conn.close();
    }

    // TODO: VEDERE SE SERVE O MENO QUESTO METODO
    /**
     * Metodo che restituisce la connessione al DB.
     * @return Connection connessione al DB
     * */
    public synchronized Connection getConnection(){
        try{
            if(conn == null){
                //conn = DriverManager.getConnection(stringConnection, username, password);
                instance = new ConnectDB(dbHost, username, password);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            conn = null;
        }
        return conn;
    }

    /**
     * Metodo che effettua le query di SELECT
     * e restituisce il risultato.
     * @param statement query SELECT
     * @return ResultSet risultato della SELECT
     * @throws SQLException Nel caso in cui ci siano errori di query.
     */
    public synchronized ResultSet selectQuery(PreparedStatement statement) throws SQLException {
        ResultSet resultSet;
        resultSet = statement.executeQuery();
        return resultSet;
    }

    /**
     * Metodo che si occupa delle query di INSERT.
     * @param statement query di INSERT
     * @return true se è stata inserita la tupla, false altrimenti.
     * @throws SQLException viene lanciata l'eccezione del caso ci siano problemi.
     */
    public synchronized boolean insertQuery(PreparedStatement statement) throws SQLException {
        // REMEMBER
        return statement.executeUpdate() > 0;

    }
}

