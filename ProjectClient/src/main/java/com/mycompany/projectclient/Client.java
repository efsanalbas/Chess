
package com.mycompany.projectclient;

import game.Message;
import static game.Message.Message_Type.Name;
import static com.mycompany.projectclient.Chess.getPanelFromName;
import static com.mycompany.projectclient.Chess.moveControl;
import static com.mycompany.projectclient.Chess.rivalMovePanelName;
import static com.mycompany.projectclient.Chess.rivalMoveTileName;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author nurefsanalbas
 */
class Listen extends Thread {

    public void run() {
        Message name = new Message(Message.Message_Type.Name); //İsim mesajı oluşturur.Oyun başladığında bağlanan oyuncuyu servera bildirmek için bu adımı ekledim.
        name.content = Chess.usernameField.getText(); //ismin içeriğine oyuncunun girdiği textfield değeri atandı.
        Client.Send(name); //Mesaj servera gönderildi.
        Client.name = Chess.usernameField.getText(); //Clientın adına da aynı şekilde textField değeri atandı.
        while (Client.socket.isConnected()) { //Client socket bağlı olduğu sürece bu döngü dönmeye devam etti.
            try {

                Message received = (Message) (Client.input.readObject()); //Alınan mesaj input.readObject() ile okundu ve mesaj objesine atandı.
                switch (received.type) { //Mesajın tipine göre aşağıdaki koşullar çalıştırılır.
                    case Name:
                        break;
                    case Move://Mesaj tipi hamle ise,servera gönderdiğim hamleyi tekrar istedim.
                        System.out.println("Hamle:" + received.content.toString());
                        String[] arrMoves = received.content.toString().split(" to ");//Hamle için gelen cümleyi split yaptım ve başlangıç ve hedef konum bilgilerini arraya attım.
                        Chess.rivalMoveTileName = arrMoves[1]; //Rakip ekranında güncelleme yapmak için kullanacağım tıklanılan taşı rivalMoveTileName'e atadım.
                        Chess.rivalMovePanelName = arrMoves[2]; //Aynı zamanda gidilmek istenilen hedef paneli de rivalMovePanelName'e atadım.
                        if (rivalMoveTileName != null && rivalMovePanelName != null && getPanelFromName(rivalMoveTileName).panelIncludeButton() != null && getPanelFromName(rivalMoveTileName).panelIncludeButton().locatedPanel != null) {
                            //rakip ekranında güncelleme yapılacak olan panelin ve taşın null olmadığı durumda, taşı içeren panelin buton içermesi durumunda, ve aynı zamanda taşın bulunduğu panel null değilse rakip ekranında güncelleme yapılır.
                            moveControl(getPanelFromName(rivalMoveTileName).panelIncludeButton(), getPanelFromName(rivalMovePanelName));
                        }
                        break;
                    case Turn://Hamle sırası bu kısımda belirlenir.
                        if (received.content.toString().equals(Client.myTile)) { //Serverda gelen hamle için taş rengi ile Clientın taş rengi eşitse,
                            System.out.println("Cevap:" + received.content.toString());
                            Client.isMyTurn = true; //O clientın sırasıdır.
                        } else {
                            System.out.println("Cevap:" + received.content.toString());
                            Client.isMyTurn = false; //Eşit olmadığı durumda client turn false olur.
                        }
                        break;
                    case Tile://Taş rengi belirlenirken burası çalışır.
                        if (received.content.toString().equals("white")) {//Serverdan gelen renge göre taş rengi belirlenir.
                            Client.myTile = "white"; //beyaz ise taş rengi beyazdır.
                        } else {
                            Client.myTile = "black"; //siyah ise taş rengi siyahtır.
                        }
                        break;
                    case End: //Oyun bitirilirken şah-mat durumunda bu kısım çalışır.
                        JOptionPane.showMessageDialog(null, received.content.toString(), "GAME END", JOptionPane.WARNING_MESSAGE);//Oyunu kazanan ve kaybeden oyunculara bildirilir.
                        for (MyButton tile : Chess.myButtons) { //Butonlar enabled yapılır.
                            tile.setEnabled(false);
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
    public static Socket socket;   //verileri almak için gerekli nesne
    public static ObjectInputStream input;  //verileri göndermek için gerekli nesne
    public static ObjectOutputStream output; //serverı dinleme thredi 
    public static Listen listenMe;
    public static String myTile;//taş rengi
    public static String id;//Client id
    public static Boolean isMyTurn;//Client turn
    public static String name;//Client name

    public static void Start(String ip, int port) {
        try {
            Client.socket = new Socket(ip, port);// Client Soket nesnesi
            Client.input = new ObjectInputStream(Client.socket.getInputStream()); // input stream
            Client.output = new ObjectOutputStream(Client.socket.getOutputStream()); // output stream
            Client.listenMe = new Listen(); //Client için listen thread oluşturuldu.
            Client.listenMe.start();//thread başlatıldı.
        } catch (IOException ex) {//Socket oluştuktan sonra hata meydana gelirse burada yakalanır.
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void Send(Message msg) {//mesaj gönderimi için bu fonksiyon kullanılır.
        try {
            Client.output.writeObject(msg);//output.writeObject(msg) ile mesaj server'a gönderilir.
        } catch (IOException ex) { //Gönderim sırasında hata oluşursa burada hata yakalanır.
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void sendMove(String move) {//Hamle gönderimi sırasında bu fonksiyonu kullandım.
        try {
            Message message = new Message(Message.Message_Type.Move); //Move türünde bir hamle mesajı oluşturdum.
            message.content = move;//İçeriğine move stringini atadım.
            output.writeObject(message);//Mesajı gönderdim.
        } catch (IOException ex) {
            System.out.println("Error: Move couldn't send.");
        }
    }
}
