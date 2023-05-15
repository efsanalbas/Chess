
package projeserver;

import game.Message;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nurefsanalbas
 */
public class Server extends Thread {

    static int i = 0;
    ServerSocket serverSocket;
    int port;
    boolean isListening;
    ArrayList<ServerClient> clients;

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


    @Override
    public void run() {

        while (this.isListening) {
            try {
                System.out.println("Client Bekleniyor...");
                Socket clientSocket = this.serverSocket.accept();
                System.out.println("Client Geldi..");
                ServerClient nclient = new ServerClient(clientSocket);
                if (this.clients.size() % 2 == 0) {
                    nclient.playerTile = "white";

                } else {
                    nclient.playerTile = "black";
                    nclient.pairedClient = this.clients.get(i);
                    this.clients.get(i).pairedClient = nclient;
                    i = i + 2;
                }
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
