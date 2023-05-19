package projeserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import game.Message;
import static game.Message.Message_Type.Move;
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
    String pairedClientName="";

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

        this.SendMessage(Message.Message_Type.Tile, playerTile);//Player tile bağlanan clienta bildirilir.

        if (this.pairedClient != null) { //Rakip null değilse ikisi birbirine bağlanmıştır.
            this.SendMessage(Message.Message_Type.Turn, this.pairedClient.playerTile); //Rakibimin taş rengini bana gönderiyorum
            this.pairedClient.SendMessage(Message.Message_Type.Turn, this.pairedClient.playerTile); //Rakibime onun taş rengini gönderiyorum.
        }

        Message received = null;

        while (this.isListening) { //isListening true olduğu sürece gelen mesajları dinler.
            try {

                received = (Message) (input.readObject()); //message nesnesini alır ve türüne göre işlemleri gerçekleştirir.
                switch (received.type) {
                    case Name:    //Message tipi Name ise,
                        System.out.println("Oyuncu adı:" + received.content.toString()); //Öncelikle oyuncu adını ekrana yazdırır.
            
                        break;
                    case Move:  //Message tipi Move ise ,
                        String move = (String) received.content;
                        String[] arrMoves = move.split(" to "); //Oyuncunun gönderdiği stringi split yapar ve taşın başlangıç ve bitiş konumlarını alır.
                        System.out.println(Arrays.toString(arrMoves));//Hangi renk taşın nereden nereye gitmek istediğini ekrana yazdırır.
                        this.pairedClient.SendMessage(Message.Message_Type.Move, move);//Oyuncudan aldığım hamleyi rakibine de gönderiyorum çünkü onun ekranı da güncellenmeli.
                        break;
                    case Turn:
                        if(received.content.equals(false)){
                        this.pairedClient.SendMessage(Message.Message_Type.Turn, this.pairedClient.playerTile);
                        }
                        break;
                    case Tile:
                        break;
                    case End://Şah yendiğinde oyunun bittiğini belirten mesajı rakibe gönderiyorum.
                        this.SendMessage(Message.Message_Type.End,received.content.toString() + " you won the game.");
                        this.pairedClient.SendMessage(Message.Message_Type.End, received.content.toString() +" won the game you Failed.");
                        break;
                }

            } catch (IOException ex) {
                System.out.println("Error: Message couldn't get.");//Mesaj alınamadığında client oyundan çıkmıştır.
                this.Stop(); // Bu durumda socket kapatılır ve dinlemeyi bırakır.

            } catch (ClassNotFoundException ex) {//input.readObject() kısmında oluşabilecek hatalar burada yakalanır.
                this.Stop();// Bu durumda socket kapatılır ve dinlemeyi bırakır.
            }
        }
    }

}
