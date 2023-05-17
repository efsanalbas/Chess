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

    static int i = 0; //bağlanan clientları eşleştirmek ve oyunun multiclientı destekleyebilmesi için bu değişkeni tanımladım.
    ServerSocket serverSocket; //socket tanımladım.
    int port; //port numarasını tanımladım.
    boolean isListening; //dinleme durumunu tutması için tanımladım.
    ArrayList<ServerClient> clients; //bağlanan clientları bu arraylistte tuttum.

    public Server(int port) { //Server oluşturmak için constructor tanımladım.
        try {
            this.port = port; //sınıfın port değişkenini parametre olarak gönderilen port değişkenine atıyorum.
            this.serverSocket = new ServerSocket(port); //Server socket oluşturuyorum.
            this.isListening = false;//Dinleme değeri başlangıçta false olarak belirlendi.
            this.clients = new ArrayList<>(); //Bağlanan clientları eklemek için arraylist oluşturdum.
        } catch (IOException ex) { //Socket oluşturulurken oluşabilecek hataları yakalar.
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void Listen() { //Server çalıştıktan sonra clientların bağlanmasını sağlar.
        this.isListening = true; //dinlemeye başlar.
        this.start(); //threadin run fonksiyonunu çalıştırır.

    }

    public void Stop() { //Socketi kapatır ve artık client kabul etmez.
        try {
            this.isListening = false; //Client bağlanmasını durdurur.
            this.serverSocket.close(); //serverSocketi kapatır.
        } catch (IOException ex) {//serverSocket kapatılırken oluşan hataları yakalar.
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addClient(ServerClient server_client) { //Arrayliste clientı ekler.
        this.clients.add(server_client);

    }

    public void removeClient(ServerClient server_client) { //Arraylisten clientı siler.
        this.clients.remove(server_client);
    }

    @Override
    public void run() {

        while (this.isListening) {//isListening true olduğu sürece döner.
            try {
                System.out.println("Client Bekleniyor...");
                Socket clientSocket = this.serverSocket.accept();//Clientı kabul eder.
                System.out.println("Client Geldi..");
                ServerClient nclient = new ServerClient(clientSocket); //Clienttan bir serverClient oluşturur,gelen mesajlar gönderilecek mesajlar bu obje üzerinden yapılır.
                if (this.clients.size() % 2 == 0) {//Clients arrayin boyutuna göre gelen clientın rengine karar verilir.0,2,4,6 gibi clientlar beyaz diğerleri siyah renge sahip olur.
                    nclient.playerTile = "white";

                } else {
                    nclient.playerTile = "black";
                    nclient.pairedClient = this.clients.get(i);//Clientlar birbiri ile eşleştirilir.
                    this.clients.get(i).pairedClient = nclient;
                    i = i + 2; //sırasıyla bağlanan clientlar eşleştirilir. Bu şekilde multiclient çalışması sağlanır.
                }
                this.addClient(nclient); //arrayliste client eklenir.
                nclient.Listen(); //ve client dinlemeye başlanır.

            } catch (IOException ex) {// Socket oluştururken ortaya çıkabilecek hataları yakalar.
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public static void Send(ServerClient cl, Message msg) {//Serverın clienta mesaj göndermesini sağlar.

        try {
            cl.output.writeObject(msg);
        } catch (IOException ex) {//output.writeObject(msg) metodundaki hataları yakalar.
            Logger.getLogger(ServerClient.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
