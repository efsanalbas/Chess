/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projeserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import static java.lang.Thread.sleep;
import java.net.Socket;
import game.Message;
import static game.Message.Message_Type.Hamle;
import static game.Message.Message_Type.Name;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nurefsanalbas
 */
public class ServerClient extends Thread {

    int id;
    Socket socket;
    private String playerName;
    String playerTile = "";
    ObjectOutputStream output;
    ObjectInputStream input;
    boolean isListening;
    public ServerClient pairedClient; //rakibim 

    public ServerClient(Socket soket) throws IOException {
        this.socket = soket;
        this.output = new ObjectOutputStream(socket.getOutputStream());
        this.input = new ObjectInputStream(socket.getInputStream());
        this.isListening = false;
    }

    public void Listen() {

        this.isListening = true;
        this.start();
    }

    public void Stop() {
        try {
            this.isListening = false;
            this.output.close();
            this.input.close();
            this.socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void SendMessage(Message.Message_Type type, String content) {
        try {
            Message message = (Message) new Message(type);
            message.content = content;
            output.writeObject(message);
        } catch (IOException ex) {
            Logger.getLogger(ServerClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {

        //Player tile bağlanan clienta bildirilir.
        this.SendMessage(Message.Message_Type.Tile, playerTile);

        if (this.pairedClient != null) { //null değilse ikisi birbirine bağlanmıştır, öbürü burayı atlamıştır.(white)

            this.SendMessage(Message.Message_Type.Turn, this.pairedClient.playerTile);
            this.pairedClient.SendMessage(Message.Message_Type.Turn, this.pairedClient.playerTile); //rakibime ve bana gönderiyorum.
        }

        Message received = null;

        while (this.isListening) {
            try {

                received = (Message) (input.readObject());
                switch (received.type) {
                    case Name:
                         System.out.println("Oyuncu adı:"+received.content.toString());
                        break;
                    case Hamle:

                        String move = (String) received.content;
                        String[] arrMoves = move.split(" to ");
                        System.out.println(Arrays.toString(arrMoves));
                        this.pairedClient.SendMessage(Message.Message_Type.Hamle, move);
                        break;

                    case Turn:
                        break;
                    case Tile:
                        break;
                }

            } catch (IOException ex) {
                System.out.println("Hata: Mesaj alınamadı.");
            } catch (ClassNotFoundException ex) {
                System.out.println("Hata: Class not found exception.");
            }
        }
    }

}