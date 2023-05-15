package projeserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import game.Message;
import static game.Message.Message_Type.Hamle;
import static game.Message.Message_Type.Name;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nurefsanalbas
 */
public class ServerClient extends Thread {

    Socket socket;               //Socket oluşturmak için kullanılacak.
    String playerTile = "";     //Oyuncunun taş rengini tutar.
    ObjectOutputStream output; //Client verilerini okuyabilmek için tanımlandı.
    ObjectInputStream input;  //Clienta mesaj gönderebilmek için tanımlandı.
    boolean isListening;     //Dinleme kontrolü için tanımlandı.
    public ServerClient pairedClient; //rakibim.

    public ServerClient(Socket soket) throws IOException { //Clientın mesajlarını serverda karşılamak için ServerClient objesi oluşturmam gerekiyordu ve burada constructor oluşturdum.
        this.socket = soket;
        this.output = new ObjectOutputStream(socket.getOutputStream()); //Mesaj gönderebilmemiz için outputStream oluşturmalıyız.
        this.input = new ObjectInputStream(socket.getInputStream());   // Mesajları okuyabilmemiz için inputStream oluşturmalıyız.
        this.isListening = false;
    }

    public void Listen() {   //Clientı dinlememizi sağlıyor.

        this.isListening = true; //True olduğu sürece clienttan gelen veri dinlenecek.
        this.start();    //run fonksiyonunu çalıştırır ve clienttan gelen mesajlar dinlenmeye başlar.
    }

    public void Stop() {  //Clientı dinlemeyi durdurmak için çalışır.
        try {
            this.isListening = false;  //Clientı dinlemeyi durdurur.
            this.output.close();       //mesaj gönderimi engellenir.
            this.input.close();        //mesaj alımı engellenir.
            this.socket.close();       //socket kapatılır.
        } catch (IOException ex) {
            Logger.getLogger(ServerClient.class.getName()).log(Level.SEVERE, null, ex);//yukarıdaki işlemlerde hata meydana geldiğinde, hata burada yakalanır.
        }
    }

    public void SendMessage(Message.Message_Type type, String content) { //Clienta serverdan mesaj göndermek için bu metodu tanımladım.
        try {
            Message message = (Message) new Message(type); //Message tipinde bir mesaj objesi oluşturur.
            message.content = content; //contentini atar.
            output.writeObject(message); //write object metodu ile mesajı gönderir.
        } catch (IOException ex) { //Yukarıdaki işlemlerde bir hata oluşması durumunda bu kısımda yakalanır.
            Logger.getLogger(ServerClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {//start metodu çalıştığında thread burayı çalıştırır.

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
                        System.out.println("Oyuncu adı:" + received.content.toString());
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
                    case End:
                        this.pairedClient.SendMessage(Message.Message_Type.End, "You failed " + received.content.toString() + " won the game.");
                        break;
                }

            } catch (IOException ex) {

                System.out.println("Hata: Mesaj alınamadı.");
                this.Stop();

            } catch (ClassNotFoundException ex) {
                System.out.println("Hata: Class not found exception.");
                this.Stop();
            }
        }
    }

}
