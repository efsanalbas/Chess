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

    ObjectOutputStream output;
    ObjectInputStream input;
    boolean isListening;
    private ServerClient pairedClient;

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

    public void SendMessage(Message messages) throws IOException {
        output.writeObject(messages);

    }

    @Override
    public void run() {
        Message received = null;
        try {
            received = (Message) (input.readObject());
            System.out.println("Oyuncu adı: " + received.content.toString());
        } catch (IOException ex) {
            System.out.println("Hata: İlk mesaj alınamadı.");
        } catch (ClassNotFoundException ex) {
            System.out.println("Hata: Class not found exception.");
        }

        while (this.isListening) {
            try {
                received = (Message) (input.readObject());
                if (received.type == Message.Message_Type.Hamle) {
                    String move = (String) received.content;
                    System.out.println("Hamle alındı: " + move);
                    // hamleyi işleme kodları burada yazılır
                }
            } catch (IOException ex) {
                System.out.println("Hata: Mesaj alınamadı.");
            } catch (ClassNotFoundException ex) {
                System.out.println("Hata: Class not found exception.");
            }
        }
    }
}


