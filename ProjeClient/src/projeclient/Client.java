/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package projeclient;

import game.Message;
import static game.Message.Message_Type.Name;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import static projeclient.Client.sendMove;

/**
 *
 * @author nurefsanalbas
 */
class Listen extends Thread {

    public void run() {
        Message msg1 = new Message(Message.Message_Type.Name);
        msg1.content = Chess.usernameField.getText();
        Client.Send(msg1);
        while (Client.socket.isConnected()) {
            try {

                Message received = (Message) (Client.input.readObject());
                switch (received.type) {
                    case Name:
                        break;
                    case Hamle:
                        System.out.println("Hamle:" + received.content.toString());
                        String[] arrMoves = received.content.toString().split(" to ");
                        Chess.rivalMoveTileName = arrMoves[1];
                        System.out.println("Chess.rivalMoveTileName:" + Chess.rivalMoveTileName);
                        Chess.rivalMovePanelName = arrMoves[2];
                        System.out.println(" Chess.rivalMovePanelName:" + Chess.rivalMovePanelName);

                        break;
                    case Turn:
                        if (received.content.toString().equals(Client.myTile)) {
                            Client.isMyTurn = true;
                        } else {
                            Client.isMyTurn = false;
                        }

                        break;

                    case Tile:
                        if (received.content.toString().equals("white")) {
                            Client.myTile = "white";

                        } else {
                            Client.myTile = "black";
                        }
                        break;
                }
            } catch (IOException ex) {
                Logger.getLogger(Listen.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Listen.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

}

public class Client {

    //her clientın bir soketi olmalı
    public static Socket socket;    //verileri almak için gerekli nesne
    public static ObjectInputStream input;   //verileri göndermek için gerekli nesne
    public static ObjectOutputStream output;  //serverı dinleme thredi 
    public static Listen listenMe;
    public static String myTile;
    public static String id;
    public static Boolean isMyTurn;

    public static void Start(String ip, int port) {
        try {

            // Client Soket nesnesi
            Client.socket = new Socket(ip, port);
            // input stream
            Client.input = new ObjectInputStream(Client.socket.getInputStream());
            // output stream
            Client.output = new ObjectOutputStream(Client.socket.getOutputStream());

            Client.listenMe = new Listen();

            Client.listenMe.start();

        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void Send(Message msg) {
        try {
            Client.output.writeObject(msg);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void sendMove(String move) {
        try {
            Message message = new Message(Message.Message_Type.Hamle);
            message.content = move;
            output.writeObject(message);
            output.flush();
        } catch (IOException ex) {
            System.out.println("Hata: Hamle gönderilemedi.");
        }
    }
}
