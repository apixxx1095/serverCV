package com.centrovaccinale.centrovaccinale.rmi;


import com.centrovaccinale.centrovaccinale.entita.*;
import com.centrovaccinale.centrovaccinale.grafica.server.controller.ServerController;
import com.centrovaccinale.centrovaccinale.utils.ConnectDB;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe che serve per rendere pubblico il server.
 * In questa classe vengono implementati i metodi che si trovano nella classe {@link Server Server.class}
 * @see com.centrovaccinale.centrovaccinale.rmi.Server
 * @author Simone Caputo 736816 VA
 * @author Jean Pierre Sotamba 728447 VA
 * @author Laura Gjetja 738629 VA
 * @author Erica Bonetti 740946 VA
 * @version 0.0.1
 */
public class ServerImpl extends UnicastRemoteObject implements Server {
    private static int numConnessioni = 0;
    private static final long serialVersionUID = 1L;
    private final List<Client> listClients = new ArrayList<>();
    private static ServerController serverController;

    /**
     * Il metodo costruttore rende pubblico il server.
     * @throws IOException Nel caso in cui ci siano problemi.
     */
    public ServerImpl() throws IOException {
        super();
    }

    /**
     * Metodo che imposta il riferimento al controller che contiene il label in cui
     * vengono indicati il numero di client connessi e il textArea che serve
     * da console.
     * @param serverController Riferimento del controller.
     */
    public synchronized static void setServerController(ServerController serverController){
        ServerImpl.serverController = serverController;
    }

    private synchronized static ServerController getServerController(){
        return serverController;
    }
    private synchronized static void aumentaNumConnessioni(){
        ++numConnessioni;
    }
    private synchronized static void decrementaNumConnessioni(){
        --numConnessioni;
    }
    private synchronized static int getNumConnessioni(){
        return numConnessioni;
    }

    // COMUNICAZIONE TRA CLIENT-SERVER

    @Override
    public synchronized boolean verifyConnection(Client remoteClient) throws RemoteException {
        System.out.println("Ping da parte di: " + remoteClient + "\n");
        getServerController().getConsoleLogs().appendText("Ping da parte di " + remoteClient + "\n\n");
        return true;
    }

    @Override
    public synchronized boolean connectionOK(Client remoteClient) throws RemoteException {
        System.out.println("Sever: connesso il client - " + remoteClient);
        getServerController().getConsoleLogs().appendText("Sever: connesso il client - " + remoteClient + "\n\n");
        return addClient(remoteClient);
    }

    private synchronized boolean addClient(Client remoteClient) throws RemoteException {
        if(!listClients.contains(remoteClient)){
            listClients.add(remoteClient);
            aumentaNumConnessioni();
            System.out.println("Numero di client attualmente connessi: " + getNumConnessioni() + "\n");
            getServerController().getCounterClientLabel().setText(listClients.size() + "");
            return true;
        }else {
            System.out.println("Server: non aggiunto il client " + remoteClient);
            System.out.println("Attualmente ci sono " + listClients.size() + " utenti registrati\n");
            return false;
        }
    }


    @Override
    public synchronized void removeCliente(Client remoteClient) throws RemoteException {
        if(listClients.remove(remoteClient)){
            System.out.println("Server: rimosso il client - " + remoteClient);
            decrementaNumConnessioni();
            System.out.println("Numero di client attualmente connessi: " + getNumConnessioni() + "\n");
            getServerController().getCounterClientLabel().setText(listClients.size() + "");
            getServerController().getConsoleLogs().appendText("Sever: rimosso il client - " + remoteClient + "\n\n");
        }else{
            System.err.println("Accaduto un errore nel remoteClient()");
        }
    }

    //OPERATORE


    @Override
    public synchronized boolean registraCentroVaccinale(CentroVaccinale centroVaccinale, String remoteClient) throws RemoteException, SQLException {
        System.out.println("**** " + remoteClient + " effettua operazione di registrazione centro vaccinale ****");
        boolean esito = false;

        getServerController().getConsoleLogs().appendText(remoteClient + " - effettua operazione di \"Registrazione Centro Vaccinale\"\n");

        String query = "" +
                "INSERT INTO CentriVaccinali(" +
                "nomeCentro," +
                "qualificatore," +
                "via," +
                "numeroCivico," +
                "comune," +
                "provincia," +
                "cap," +
                "tipologia) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";

        ConnectDB connectDB = ConnectDB.getInstance();
        PreparedStatement statement = connectDB.getConnection().prepareStatement(query);

        statement.setString(1, centroVaccinale.getNomeCentro());
        statement.setString(2, centroVaccinale.getQualificatore());
        statement.setString(3, centroVaccinale.getIndirizzo());
        statement.setInt(4, centroVaccinale.getNumeroCivico());
        statement.setString(5, centroVaccinale.getComune());
        statement.setString(6, centroVaccinale.getProvincia());
        statement.setString(7, centroVaccinale.getCap());
        statement.setString(8, centroVaccinale.getTipologia());

        System.out.println("Query:\n" + statement + "\n");
        getServerController().getConsoleLogs().appendText("Query:\n" + statement + "\n");

        if(connectDB.insertQuery(statement)){
            esito = true;
            getServerController().getConsoleLogs().appendText("Registrazione centro vaccinale andata a buon fine!\n\n");
        }else{
            getServerController().getConsoleLogs().appendText("Registrazione centro vaccinale non andata a buon fine!\n\n");
        }
        return esito;
    }


    @Override
    public synchronized boolean registraVaccinato(CittadinoVaccinato cittadinoVaccinato, String remoteClient) throws RemoteException, SQLException {
        System.out.println("**** " + remoteClient + " - effettua operazione di Registrazione Cittadino Vaccinato ****");
        boolean esito = false;

        getServerController().getConsoleLogs().appendText(remoteClient + " - effettua operazione di \"Registrazione Cittadino Vaccinato\"\n");

        String query = "" +
                "INSERT INTO Vaccinati(" +
                "nomeCentro," +
                "nome," +
                "cognome," +
                "cf," +
                "data," +
                "vaccino," +
                "idVaccinazione)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?);";

        ConnectDB connectDB = ConnectDB.getInstance();
        PreparedStatement statement = connectDB.getConnection().prepareStatement(query);

        statement.setString(1, cittadinoVaccinato.getNomeCentro());
        statement.setString(2, cittadinoVaccinato.getNomeCittadino());
        statement.setString(3, cittadinoVaccinato.getCognomeCittadino());
        statement.setString(4, cittadinoVaccinato.getCodFiscale());
        statement.setObject(5, cittadinoVaccinato.getDataSomministrazioneVaccino());
        statement.setString(6, cittadinoVaccinato.getTipoVaccino());
        statement.setShort(7, cittadinoVaccinato.getIdVaccinazione());

        System.out.println("Query:\n" + statement + "\n");
        getServerController().getConsoleLogs().appendText("Query:\n" + statement + "\n");

        if(connectDB.insertQuery(statement)){
            esito = true;
            getServerController().getConsoleLogs().appendText("Registrazione cittadino vaccinato andata a buon fine!\n\n");
        }else{
            getServerController().getConsoleLogs().appendText("Registrazione cittadino vaccinato non andata a buon fine!\n\n");
        }
        return esito;
    }

    //CITTADINO
    @Override
    public synchronized boolean registraCittadino(CittadinoRegistrato cittadinoRegistrato, String remoteClient) throws RemoteException, SQLException {
        System.out.println("**** " + remoteClient + " - effettua operazione di registrazione a sistema ****");
        boolean esito = false;

        getServerController().getConsoleLogs().appendText(remoteClient + " - effettua operazione di \"Registrazione cittadino\"\n");

        String query = "" +
                "INSERT INTO CittadiniRegistrati(" +
                "nome," +
                "cognome," +
                "cf," +
                "mail," +
                "userId," +
                "password," +
                "idVaccinazione)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?);";

        ConnectDB connectDB = ConnectDB.getInstance();
        PreparedStatement statement = connectDB.getConnection().prepareStatement(query);

        statement.setString(1, cittadinoRegistrato.getNomeUtente());
        statement.setString(2, cittadinoRegistrato.getCognomeUtente());
        statement.setString(3, cittadinoRegistrato.getCodFiscale());
        statement.setString(4, cittadinoRegistrato.getEmail());
        statement.setString(5, cittadinoRegistrato.getUsername());
        statement.setString(6, cittadinoRegistrato.getPassword());
        statement.setShort(7, cittadinoRegistrato.getIdVaccinazione());

        System.out.println("Query:\n" + statement + "\n");
        getServerController().getConsoleLogs().appendText("Query:\n" + statement + "\n");

        if(connectDB.insertQuery(statement)){
            esito = true;
            getServerController().getConsoleLogs().appendText("Registrazione cittadino andata a buon fine!\n\n");
        }else{
            getServerController().getConsoleLogs().appendText("Registrazione cittadino non andata a buon fine!\n\n");
        }
        return esito;
    }

    @Override
    public synchronized boolean registraEventoAvverso(EventoAvverso eventoAvverso, String remoteClient) throws RemoteException, SQLException {
        System.out.println("**** " + remoteClient + " - effettua operazione di registrazione evento avverso ****");
        boolean esito = false;

        getServerController().getConsoleLogs().appendText(remoteClient + " - effettua operazione di \"Registrazione Evento Avverso\"\n");

        String query = "" +
                "INSERT INTO EventiAvversi(" +
                "nomeCentro," +
                "evento," +
                "severita," +
                "note)" +
                " VALUES (?, ?, ?, ?);";

        ConnectDB connectDB = ConnectDB.getInstance();
        PreparedStatement statement = connectDB.getConnection().prepareStatement(query);

        statement.setString(1, eventoAvverso.getNomeCentro());
        statement.setString(2, eventoAvverso.getEvento());
        statement.setInt(3, eventoAvverso.getSeverita());
        statement.setString(4, eventoAvverso.getNote());

        System.out.println("Query:\n " + statement + "\n");
        getServerController().getConsoleLogs().appendText("Query:\n" + statement + "\n");

        if(connectDB.insertQuery(statement)){
            esito = true;
            getServerController().getConsoleLogs().appendText("Registrazione evento avverso andata a buon fine!\n\n");
        }else{
            getServerController().getConsoleLogs().appendText("Registrazione evento avverso non andata a buon fine!\n\n");
        }
        return esito;
    }

    @Override
    public synchronized boolean login(String username, String password, String remoteClient) throws RemoteException, SQLException {
        System.out.println("**** " + remoteClient + " - effettua operazione di Login ****");
        boolean esito = false;

        getServerController().getConsoleLogs().appendText(remoteClient + " - effettua operazione di \"Login\"\n");

        String query = "" +
                "SELECT userid, password\n" +
                "FROM cittadiniregistrati\n" +
                "WHERE userid = ?";

        String usernameResult = null;
        String passwordResult = null;

        ConnectDB connectDB = ConnectDB.getInstance();
        PreparedStatement statement = connectDB.getConnection().prepareStatement(query);

        statement.setString(1, username);

        System.out.println("Query:\n" + statement + "\n");
        getServerController().getConsoleLogs().appendText("Query:\n" + statement + "\n");

        ResultSet resultSet = connectDB.selectQuery(statement);

        while (resultSet.next()){
            usernameResult = resultSet.getString("userid");
            passwordResult = resultSet.getString("password");
        }
        resultSet.close();

        if(usernameResult != null && passwordResult != null){
            if (username.equals(usernameResult) && password.equals(passwordResult)){
                getServerController().getConsoleLogs().appendText("Operazione di login andata a buon fine!\n\n");
                esito = true;
            }else {
                getServerController().getConsoleLogs().appendText("Operazione di login non andata a buon fine!\n\n");
            }
        }
        return esito;
    }

    @Override
    public synchronized List<String> cercaCentroPerNome(String nomeCentro, String remoteClient) throws RemoteException, SQLException {
        System.out.println("**** " + remoteClient + " - effettua operazione di ricerca per nome ****");
        List<String> listaCentri = new ArrayList<>();

        getServerController().getConsoleLogs().appendText(remoteClient + " - effettua operazione di \"Ricerca per nome\"\n");

        String query = "" +
                "SELECT nomecentro\n" +
                "FROM centriVaccinali\n" +
                "WHERE LOWER(nomecentro) LIKE CONCAT('%', LOWER(?), '%');";

        ConnectDB connectDB = ConnectDB.getInstance();
        PreparedStatement statement = connectDB.getConnection().prepareStatement(query);

        statement.setString(1, nomeCentro);

        System.out.println("Query:\n" + statement + "\n");
        getServerController().getConsoleLogs().appendText("Query:\n" + statement + "\n\n");

        ResultSet resultSet = connectDB.selectQuery(statement);
        while (resultSet.next()){
            listaCentri.add(resultSet.getString("nomeCentro"));
        }
        resultSet.close();

        return listaCentri;
    }

    @Override
    public List<RiepilogoEventi> riepilogoEventi(String nomeCentro, String remoteClient) throws RemoteException, SQLException {
        System.out.println("**** " + remoteClient + " - richiede un riepilogo per il centro \"" + nomeCentro + "\" ****");
        List<RiepilogoEventi> listaRiepilogo = new ArrayList<>();

        getServerController().getConsoleLogs().appendText(remoteClient + " - richiede un riepilogo per il centro: \"" + nomeCentro + "\"\n");

        String query = "" +
                "SELECT DISTINCT evento, COUNT(evento) AS quantita, AVG(severita) AS mediaSeverita\n" +
                "FROM CentriVaccinali JOIN EventiAvversi USING (nomeCentro)\n" +
                "WHERE nomeCentro=?\n" +
                "GROUP BY evento;";

        ConnectDB connectDB = ConnectDB.getInstance();
        PreparedStatement statement = connectDB.getConnection().prepareStatement(query);

        statement.setString(1, nomeCentro);

        System.out.println("Query:\n" + statement + "\n");
        getServerController().getConsoleLogs().appendText("Query:\n" + statement + "\n\n");

        ResultSet resultSet = connectDB.selectQuery(statement);
        while (resultSet.next()){
            RiepilogoEventi riepilogoEventi = new RiepilogoEventi(
                    resultSet.getString("evento"),
                    resultSet.getInt("quantita"),
                    resultSet.getDouble("mediaSeverita"));
            listaRiepilogo.add(riepilogoEventi);
        }
        resultSet.close();

        return listaRiepilogo;
    }

    @Override
    public synchronized List<String> cercaCentroPerComuneTipologia(String comune, String tipologia, String remoteClient) throws RemoteException, SQLException {
        System.out.println("**** " + remoteClient + " - effettua operazione di ricerca per comune e tipologia ****");
        List<String> listCentri = new ArrayList<>();

        getServerController().getConsoleLogs().appendText(remoteClient + " - effettua operazione di \"Ricerca per comune e tipologia\"\n");

        String query = "" +
                "SELECT nomecentro\n" +
                "FROM centriVaccinali\n" +
                "WHERE LOWER(comune) LIKE CONCAT(LOWER(?), '%') AND LOWER(tipologia) LIKE CONCAT(LOWER(?), '%')";

        ConnectDB connectDB = ConnectDB.getInstance();
        PreparedStatement statement = connectDB.getConnection().prepareStatement(query);

        statement.setString(1, comune);
        statement.setString(2, tipologia);

        System.out.println("Query:\n" + statement + "\n");
        getServerController().getConsoleLogs().appendText("Query:\n" + statement + "\n\n");

        ResultSet resultSet = connectDB.selectQuery(statement);
        while (resultSet.next()){
            listCentri.add(resultSet.getString("nomeCentro"));
        }
        resultSet.close();

        return listCentri;
    }

    // SERVIZI VARI: LISTA DI TUTTI I CENTRI, LISTA DEGLI IDVACCINAZIONE
    @Override
    public synchronized List<String> getListaCentri() throws RemoteException, SQLException {
        System.out.println("Richiesta lista dei centri");
        List<String> listaCentri = new ArrayList<>();

        getServerController().getConsoleLogs().appendText("Richiesta lista dei centri\n");

        String query = "" +
                "SELECT nomecentro\n" +
                "FROM centrivaccinali;";

        ConnectDB connectDB = ConnectDB.getInstance();
        PreparedStatement statement = connectDB.getConnection().prepareStatement(query);

        System.out.println("Query:\n" + statement + "\n");
        getServerController().getConsoleLogs().appendText("Query:\n" + statement + "\n\n");

        ResultSet resultSet = connectDB.selectQuery(statement);
        while (resultSet.next()){
            listaCentri.add(resultSet.getString("nomecentro"));
        }
        resultSet.close();

        return listaCentri;
    }
    @Override
    public synchronized List<String> getListaCFVaccinati() throws RemoteException, SQLException {
        System.out.println("Richiesta lista dei codici fiscali");
        List<String> listaCF = new ArrayList<>();

        getServerController().getConsoleLogs().appendText("Richiesta lista Codici Fiscali dalla tabella vaccinati\n");

        String query = "" +
                "SELECT cf\n" +
                "FROM vaccinati;";

        ConnectDB connectDB = ConnectDB.getInstance();
        PreparedStatement statement = connectDB.getConnection().prepareStatement(query);

        System.out.println("Query:\n" + statement + "\n");
        getServerController().getConsoleLogs().appendText("Query:\n" + statement + "\n\n");

        ResultSet resultSet = connectDB.selectQuery(statement);
        while (resultSet.next()){
            listaCF.add(resultSet.getString("cf"));
        }
        resultSet.close();

        return listaCF;
    }

    @Override
    public synchronized List<String> getListaCFCittadiniRegistrati() throws RemoteException, SQLException {
        System.out.println("Richiesta lista dei codici fiscali dalla tabella cittadiniregistrati");
        List<String> listaCF = new ArrayList<>();

        getServerController().getConsoleLogs().appendText("Richiesta lista Codici Fiscali dalla tabella cittadiniregistrati\n");

        String query = "" +
                "SELECT cf\n" +
                "FROM cittadiniregistrati;";

        ConnectDB connectDB = ConnectDB.getInstance();
        PreparedStatement statement = connectDB.getConnection().prepareStatement(query);

        System.out.println("Query:\n" + statement + "\n");
        getServerController().getConsoleLogs().appendText("Query:\n" + statement + "\n\n");

        ResultSet resultSet = connectDB.selectQuery(statement);
        while (resultSet.next()){
            listaCF.add(resultSet.getString("cf"));
        }
        resultSet.close();

        return listaCF;
    }
    @Override
    public synchronized boolean idVaccinazioneUtenzeIsPresente(short idVaccinazione) throws RemoteException, SQLException {
        System.out.println("Verifica presenza idVaccinazione: " + idVaccinazione + " dalla tabella delle utenze");
        boolean esisto = false;

        getServerController().getConsoleLogs().appendText("Verifica presenza idVaccinazione: " + idVaccinazione + " dalla tabella delle utenze\n");

        String query = "" +
                "SELECT idvaccinazione\n" +
                "FROM cittadiniregistrati\n" +
                "WHERE idVaccinazione=?;";

        ConnectDB connectDB = ConnectDB.getInstance();
        PreparedStatement statement = connectDB.getConnection().prepareStatement(query);

        statement.setShort(1, idVaccinazione);

        System.out.println("Query:\n" + statement + "\n");
        getServerController().getConsoleLogs().appendText("Query:\n" + statement + "\n\n");

        ResultSet resultSet = connectDB.selectQuery(statement);
        if (resultSet.next()){
            esisto = true;
        }
        resultSet.close();

        return esisto;
    }

    @Override
    public synchronized boolean idVaccinazioneVaccinatiIsPresente(short idVaccinazione) throws RemoteException, SQLException {
        System.out.println("Verifica presenza idVaccinazione: " + idVaccinazione + " dalla tabella vaccinati");
        boolean esito = false;

        getServerController().getConsoleLogs().appendText("Verifica presenza idVaccinazione: " + idVaccinazione + " dalla tabella vaccinati\n");

        String query = "" +
                "SELECT idvaccinazione\n" +
                "FROM vaccinati\n" +
                "WHERE idVaccinazione=?;";

        ConnectDB connectDB = ConnectDB.getInstance();
        PreparedStatement statement = connectDB.getConnection().prepareStatement(query);

        statement.setShort(1, idVaccinazione);

        System.out.println("Query:\n" + statement + "\n");
        getServerController().getConsoleLogs().appendText("Query:\n" + statement + "\n\n");

        ResultSet resultSet = connectDB.selectQuery(statement);
        if (resultSet.next()){
            esito = true;
        }
        resultSet.close();

        return esito;
    }

    @Override
    public synchronized boolean usernameIsPresente(String username) throws RemoteException, SQLException {
        System.out.println("Verifica presenza username: \"" + username + "\"");
        boolean presente = false;

        getServerController().getConsoleLogs().appendText("Verifica presenza username: \"" + username + "\"\n");

        String query = "" +
                "SELECT userId\n" +
                "FROM cittadiniregistrati\n" +
                "WHERE userId = ?;";

        ConnectDB connectDB = ConnectDB.getInstance();
        PreparedStatement statement = connectDB.getConnection().prepareStatement(query);

        statement.setString(1, username);

        System.out.println("Query:\n" + statement + "\n");
        getServerController().getConsoleLogs().appendText("Query:\n" + statement + "\n\n");

        ResultSet resultSet = connectDB.selectQuery(statement);
        if(resultSet.next()){
            presente = true;
        }
        resultSet.close();

        return presente;
    }

    @Override
    public synchronized String cercaCentroUtente(String username) throws RemoteException, SQLException {
        System.out.println("Richiesta nome del centro per: \"" + username + "\"");
        String centro = null;

        getServerController().getConsoleLogs().appendText("Richiesta nome del centro per: \"" + username + "\"\n");

        String query = "" +
                "SELECT nomecentro\n" +
                "FROM cittadiniregistrati c JOIN vaccinati v ON(c.idvaccinazione = v.idvaccinazione)\n" +
                "WHERE userid =?;";

        ConnectDB connectDB = ConnectDB.getInstance();
        PreparedStatement statement = connectDB.getConnection().prepareStatement(query);

        statement.setString(1, username);

        System.out.println("Query:\n" + statement + "\n");
        getServerController().getConsoleLogs().appendText("Query:\n" + statement + "\n\n");

        ResultSet resultSet = connectDB.selectQuery(statement);
        while (resultSet.next()){
            centro = resultSet.getString("nomeCentro");
        }
        resultSet.close();

        return centro;
    }
}
