/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projeserver;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import game.Message;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author nurefsanalbas
 */
public class Server extends Thread {

    ServerSocket serverSocket;
    int port;
    boolean isListening;
    ArrayList<ServerClient> clients;
    //Thread listenThread;

    public Server(int port) {
        try {
            this.port = port;
            this.serverSocket = new ServerSocket(port);
            this.isListening = false;
            this.clients = new ArrayList<>();

            //this.listenThread= new Thread();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void Listen() {
        this.isListening = true;
        this.start();

    }

    public void Stop() {
        try {
            this.isListening = false;

            this.serverSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addClient(ServerClient server_client) {
        this.clients.add(server_client);

    }

    public void removeClient(ServerClient server_client) {
        this.clients.remove(server_client);

    }

    public ServerClient clientByIndex(int index) {
        return this.clients.remove(index);

    }

   

    

    @Override
    public void run() {

        while (this.isListening) {
            try {
                System.out.println("Client Bekleniyor...");
                Socket clientSocket = this.serverSocket.accept();
                System.out.println("Client Geldi..");
                ServerClient nclient = new ServerClient(clientSocket);
                this.addClient(nclient);
                nclient.Listen();

            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
    public static void Send(ServerClient cl, Message msg) {

        try {
            cl.output.writeObject(msg);
        } catch (IOException ex) {
            Logger.getLogger(ServerClient.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
