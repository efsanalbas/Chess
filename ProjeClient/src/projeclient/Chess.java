package projeclient;

/**
 *
 * @author nurefsanalbas
 */
import game.Message;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Chess {

    private static MyPanel currentPanel = null; //tıklanılan butonun bulunduğu panel.
    public static MyButton currentClickedButton; //tıklanılan buton bu değişkene atanır.
    public static String rivalMoveTileName;      //Rakip ekranında güncellenecek taşın adı.
    public static String rivalMovePanelName;     //Rakip ekranında güncellenecek panelin adı.
    static int k = 8, l = 8, m = 8, n = 8, p = 8, r = 8, s = 8, t = 8; //Square isimleri belirlenirken kullanılacak sayılar.
    static JFrame frame = new JFrame("Board");   //Board için bir JFrame oluşturdum.
    static Color color = new Color(162, 97, 57); //Boarddaki kareler için kullanacağım renkler.
    static Color color2 = new Color(237, 211, 169);
    static ArrayList<MyPanel> myPanels = new ArrayList<>(); //Tüm panelleri bu arrayliste ekledim.
    static ArrayList<MyButton> myButtons = new ArrayList<>(); //tüm taşları bu arrayliste ekledim.
    static int rowCount = 8; //satır sayısı
    static int columnCount = 8; //sütun sayısı
    static MyPanel[][] board = new MyPanel[rowCount][columnCount]; //Tüm karelerin ekleneceği en dıştaki büyük panel
    static JButton connectButton; //Oyunu başlatma butonu
    public static JTextField usernameField; //Oyuncunun adını girdiği buton
    static boolean isInvalidMove = false;

    public Chess() {
        gameBoard(); //Bu constructor oyunu başlatır.
    }

    public static void addButton(ArrayList panels, MyPanel panel, ArrayList buttons, String pieceName, String tileColor, String type) {//Satran. tahtasına buton eklerken bu kod çalışır.
        MyButton button = new MyButton(); //Button oluşturuyorum.
        ImageIcon icon = new ImageIcon(pieceName); //ImageIconu butonun üzerine eklemek istediğim taş için oluşturdum.
        button.setIcon(icon); //butonun üzerine ikonu ekledim.
        button.buttonIndex = panel.name; //butondada panelin adını tuttum. Çünkü butonun adı ve panelin adı aynıdır.
        button.locatedPanel = panel;  //butonun bulunduğu panele paneli eşitledim.
        button.type = type;   //taşın tipini belirledim.
        button.tileColor = tileColor; //taşın rengini belirledim.
        buttons.add(button); //buttonları listeye ekledim.
        panel.add(button); //ve panele butonu ekleyerek ekleme aşamasını tamamladım.

    }

    public static void gameScreen() { //Oyunun arayüzünü burada oluşturuyorum.

        JPanel mainPanel = new JPanel(new BorderLayout());//Tüm taşların ve karelerin ekleneceği ana panel.
        JPanel connectionPanel = new JPanel(); // Bağlanma butonu ve kullanıcı adı alanı içeren panel.
        usernameField = new JTextField(20); //Kullanıcının adını yazdığı yer.
        connectButton = new JButton("Connect");//Servera bağlanma butonu.
        connectionPanel.add(usernameField); //panele kullanıcı adı girilmesi için textField'ı ekledim.
        connectionPanel.add(connectButton); //Bağlantı butonunu panele ekledim.
        JPanel chessBoard = new JPanel(new GridLayout(8, 8)); //Squareları eklemek için bir panel daha oluşturdum.
        mainPanel.add(connectionPanel, BorderLayout.NORTH); //Ana panele connection paneli ekledim.

        for (int i = 0; i < 8; i++) {//Satırları yazdırır.
            for (int j = 0; j < 8; j++) {//Sutunları yazdırır.
                MyPanel square = new MyPanel();//Satranç kareleri için panel türünde square oluşturdum.
                if ((i + j) % 2 == 0) {//panelin iki farklı renk olması için satır ve sütunun toplam değerine bakıp tek ve çift olmasına göre rengi belirledim.
                    square.setBackground(color);
                    square.previousColor = color; //panele tıklandıktan sonra eski rengine geri dönebilmesi için bu özelliği ekledim.
                } else {
                    square.setBackground(color2);
                    square.previousColor = color2; //panele tıklandıktan sonra eski rengine geri dönebilmesi için bu özelliği ekledim.
                }

                if (i % 8 == 0) { //0.satır A harfini temsil ediyor.

                    square.setForeground(Color.black);
                    square.name = "A" + (k); //A harfinin yanına k değişkenini ekleyerek dinamik olarak square adını oluşturdum. 
                    square.row = 'A'; //Satır adı.
                    square.column = Integer.toString(k); //Sütun adı
                    k--; //K değerini azaltıyorum.
                }
                if (i % 8 == 1) { //1.satır B harfini temsil ediyor. ve diğer kısımlar da A harfiyle aynı şekilde ilerliyor
                    square.setForeground(Color.black);
                    square.name = "B" + (l);
                    square.row = 'B';
                    square.column = Integer.toString(l);
                    l--;
                }
                if (i % 8 == 2) { //2.satır C harfini temsil ediyor.

                    square.setForeground(Color.black);
                    square.name = "C" + (m);
                    square.row = 'C';
                    square.column = Integer.toString(m);
                    m--;
                }
                if (i % 8 == 3) { //3.satır D harfini temsil ediyor.

                    square.setForeground(Color.black);
                    square.name = "D" + (n);
                    square.row = 'D';
                    square.column = Integer.toString(n);
                    n--;
                }
                if (i % 8 == 4) { //4.satır E harfini temsil ediyor.

                    square.setForeground(Color.black);
                    square.name = "E" + (p);
                    square.row = 'E';
                    square.column = Integer.toString(p);
                    p--;
                }
                if (i % 8 == 5) { //5.satır F harfini temsil ediyor.

                    square.setForeground(Color.black);
                    square.name = "F" + r;
                    square.row = 'F';
                    square.column = Integer.toString(r);
                    r--;
                }
                if (i % 8 == 6) { //6.satır G harfini temsil ediyor.

                    square.setForeground(Color.black);
                    square.name = "G" + s;
                    square.row = 'G';
                    square.column = Integer.toString(s);

                    s--;
                }
                if (i % 8 == 7) { //7.satır ise H harfini temsil ediyor.
                    square.setForeground(Color.black);
                    square.name = "H" + t;
                    square.row = 'H';
                    square.column = Integer.toString(t);
                    t--;
                }

                if (square.name.startsWith("G")) { //Eğer satır G ile başlıyosa siyah piyonlar buton olarak eklenir.
                    addButton(myPanels, square, myButtons, "pawnBlack.png", "black", "pawn");
                }
                if (square.name.startsWith("B")) { //Eğer satır B ile başlıyorsa beyaz piyonlar buton olarak eklenir.
                    addButton(myPanels, square, myButtons, "pawnWhite.png", "white", "pawn");
                }
                if (square.name.contains("A8") || square.name.contains("A1")) { //Square A8 veya A1 ise beyaz kare taş eklenir.
                    addButton(myPanels, square, myButtons, "rookWhite.png", "white", "rook");
                }
                if (square.name.contains("H1") || square.name.contains("H8")) { //Square H8 veya H1 ise siyah kare taş eklenir. 
                    addButton(myPanels, square, myButtons, "rookBlack.png", "black", "rook");
                }
                if (square.name.contains("A7") || square.name.contains("A2")) { //Square A7 veya A2 ise beyaz at eklenir.
                    addButton(myPanels, square, myButtons, "horseWhite.png", "white", "horse");
                }
                if (square.name.contains("H7") || square.name.contains("H2")) { //Square H7 veya H2 ise beyaz at eklenir.
                    addButton(myPanels, square, myButtons, "horseBlack.png", "black", "horse");
                }
                if (square.name.contains("A6") || square.name.contains("A3")) { //Square A6 veya A3 ise beyaz fil eklenir.
                    addButton(myPanels, square, myButtons, "bishopWhite.png", "white", "bishop");
                }
                if (square.name.contains("H6") || square.name.contains("H3")) { //Square A6 veya A3 ise siyah fil eklenir.
                    addButton(myPanels, square, myButtons, "bishopBlack.png", "black", "bishop");
                }
                if (square.name.contains("A5")) { //Square A5 ise beyaz renk şah eklenir.
                    addButton(myPanels, square, myButtons, "kingWhite.png", "white", "king");
                }
                if (square.name.contains("H5")) { //Square H5 ise siyah renk şah eklenir.
                    addButton(myPanels, square, myButtons, "kingBlack.png", "black", "king");
                }
                if (square.name.endsWith("A4")) { //Square A4 ile bitiyorsa beyaz renk vezir eklenir.
                    addButton(myPanels, square, myButtons, "queenWhite.png", "white", "queen");
                }
                if (square.name.endsWith("H4")) { //Square H4 ile bitiyorsa siyah renk vezir eklenir.
                    addButton(myPanels, square, myButtons, "queenBlack.png", "black", "queen");
                }
                board[i][j] = square; //Satranç tahtasının ilgili konumu square'a eşitlenir.
                myPanels.add(square); //Square myPanel arraylistine eklenir.
                chessBoard.add(board[i][j]); //chessboarda square eklenir.
            }

        }
        mainPanel.add(chessBoard, BorderLayout.CENTER); //En dıştaki panele chessBoard eklenir.

        for (int i = 0; i < board.length; i++) { //Boardı ekrana yazdırıyor.
            for (int j = 0; j < board[0].length; j++) {
                System.out.print(board[i][j].name + " ");
            }
            System.out.println();
        }

        connectButton.addActionListener(new ActionListener() { //Oyunu başlatacak olan connectButton'a actionListener ekledim böylece tıklandığında Client start metodunu çalıştırdı.
            public void actionPerformed(ActionEvent e) {
                connectButton.setEnabled(false); //Player bu butona sadece bir kez basabilsin diye bir kez tıkladıktan sonra enable yaptım.
                Client.Start("127.0.0.1", 3000); //Clientı başlattım.
            }
        });

        for (int j = 0; j < myButtons.size(); j++) { //Tıklanılan butonu bulur ve currentClickedButton değişkenine atar.
            isClicked(myButtons.get(j));
        }
        frame.add(mainPanel); //Ana panel frame'e eklenir.
        frame.pack(); //Componentlerin doğru boyutlandırılmasını sağlar.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Çapıya bastığımda JFrame'i kapatır.
        frame.setBounds(350, 350, 800, 800); //Boyutları ayarlar.
        frame.setVisible(true); //frame'i görünür yapar.
    }

    public static void gameBoard() {
        gameScreen(); //Öncelikle satranç tahtası ekrana yazdırılır.

        for (int i = 0; i < myPanels.size(); i++) {//Hamle yapılacağı zaman tüm paneller üzerinde döner.
            final int index = i; //içeride i yi kullanamam bu yüzden index aldında bir değişkene atadım.
            myPanels.get(index).addMouseListener(new MouseAdapter() { //Panelin her indexine mouse listener ekledim bu şekilde panele tıklandığında bunu algılamasını sağladım.
                @Override
                public void mouseClicked(MouseEvent e) { //Mouse panele tıkladığında,

                    if (currentClickedButton != null && Client.myTile.equals(currentClickedButton.tileColor)) {
                        //tıklanılmış bir buton varsa ve 
                        //oyuncunun oynayacağı taşın rengi ona server tarafından atanan playerTile rengine eşitse 
                        //Bu block çalışır.
                        if (Client.isMyTurn) { //Sıra bu oyuncudaysa,(bu bilgiyi serverdan alır),
                            myPanels.get(index).setClicked(true); //panele tıklandığı bilgisini true yapar.
                            moveControl(currentClickedButton, myPanels.get(index));//Tıklanılan buton ve paneli parametre olarak alarak, hareketi kontrol etti.
                            Message msg = new Message(Message.Message_Type.Move); //Oyuncunun hareketi ile ilgili bir mesaj objesi oluşturdu.
                            msg.content = currentClickedButton.tileColor + " to " + currentPanel.name + " to " + myPanels.get(index).name; //tıklanılan butonun rengini ve ilk tıklanılan butonun panelini ve target paneli mesaj olarak oluşturdu.
                            Client.Send(msg);//Servera mesaj olarak gönderdi.
                            Client.isMyTurn = false; //Bu durumda bu oyuncunun bir daha hamle yapamaması için isMyTurn false oldu.
                            Message turnMessage = new Message(Message.Message_Type.Turn); //Turn mesaj türünde başka bir mesaj objesi oluşturuldu.
                            turnMessage.content = false; //İçeriğine false atandı.
                            Client.Send(turnMessage);//Ve bu bilgi de servera gönderildi bu şekilde serverın diğer oyuncuya sıranın onda olduğunu söylemesi sağlandı.
                        } else if (isInvalidMove == true) { //Player yanlış tercih yaparsa tekrar hamle yapmasına izin verilir.
                            myPanels.get(index).setClicked(true); //panele tıklandığı bilgisini true yapar.
                            moveControl(currentClickedButton, myPanels.get(index));//Tıklanılan buton ve paneli parametre olarak alarak, hareketi kontrol etti.
                            Message msg = new Message(Message.Message_Type.Move); //Oyuncunun hareketi ile ilgili bir mesaj objesi oluşturdu.
                            msg.content = currentClickedButton.tileColor + " to " + currentPanel.name + " to " + myPanels.get(index).name; //tıklanılan butonun rengini ve ilk tıklanılan butonun panelini ve target paneli mesaj olarak oluşturdu.
                            Client.Send(msg);//Servera mesaj olarak gönderdi.
                            Client.isMyTurn = false; //Bu durumda bu oyuncunun bir daha hamle yapamaması için isMyTurn false oldu.
                            Message turnMessage = new Message(Message.Message_Type.Turn); //Turn mesaj türünde başka bir mesaj objesi oluşturuldu.
                            turnMessage.content = false; //İçeriğine false atandı.
                            Client.Send(turnMessage);//Ve bu bilgi de servera gönderildi bu şekilde serverın diğer oyuncuya sıranın onda olduğunu söylemesi sağlandı.
                            isInvalidMove = false;
                        }
                    }
                }
            });
        }
    }

    public static void isClicked(MyButton button) {//Butona tıklandığının belirlenmesi için bu metodu yazdım.

        button.addActionListener(new ActionListener() { //ActionListener butona tıklandığında bunu belirtir.
            @Override
            public void actionPerformed(ActionEvent e) {

                if (currentPanel != null) {//Current Panele bir değişken atandıysa ve sonra başka bir değişken atanacaksa, 
                    currentPanel.setBackground(currentPanel.previousColor);//İlk tıklanılan panelin rengi eski haline döndürülür.
                }
                MyButton clickedButton = (MyButton) e.getSource(); //Tıklanılan butonu e.getSource() döndürür.
                MyPanel parentPanel = (MyPanel) clickedButton.getParent(); //Butonun bulunduğu paneli de clickedButton.getParent() döndürür.
                currentClickedButton = clickedButton; //static değişken currentClickedButton'a clickedButton atanır.
                parentPanel.setBackground(Color.green); //tıklanılan butonun bulunduğu panelin rengi yeşil yapılır.
                currentPanel = parentPanel; //tıklanılan butonun bulunduğu panel parentPanele eşitlenir.

            }
        });
    }

    public static void makeMove(MyButton tile, MyPanel destinationPanel, MyPanel currentPanel) { //Taşı bir konumdan diğerine hareket ettirir.
        if (!destinationPanel.panelHasButton().equals("blank") && destinationPanel.panelHasButton().equals(tile.tileColor)) { //Gidilmek istenen konum boş değilse ya da aynı renk taş içeriyorsa hamle yapılamaz.
            JOptionPane.showMessageDialog(null, "Invalid move", "Square Filled", JOptionPane.WARNING_MESSAGE);//Hata mesajı döndürülür.
        }
        if (!destinationPanel.panelHasButton().equals(tile.tileColor) && !destinationPanel.panelHasButton().equals("blank")) { //Eğer ilerlenilmek istenen hedef panelde rakip taş varsa 
            if (destinationPanel.panelIncludeButton().type.equals("king")) { //Eğer rakibin yemek istediği taş şah ise oyun biter.
                JOptionPane.showMessageDialog(null, "CHECKMATE", "GAME END", JOptionPane.WARNING_MESSAGE); //Eğer şah kaybederse şah mat yazısı ekranda görülür.
                if (Client.myTile != null && Client.myTile.equals(tile.tileColor)) { //Clientın taşı tile taş rengine eşitse,
                    Message endMessage = new Message(Message.Message_Type.End); //Client oyunu sonlandırmak için mesajolarak adını gönderir.
                    endMessage.content = Client.name;
                    Client.Send(endMessage);
                }
            }
            currentPanel.remove(tile); //rakip taş bulunduğu konumdan silinir.
            currentPanel.setBackground(currentPanel.previousColor);//panelin rengi eski rengini set edilir.
            destinationPanel.remove(destinationPanel.panelIncludeButton()); //hedef panelde bulunan buton silinir.
            destinationPanel.add(tile); //destinationPanele yeni buton eklenir.
            tile.locatedPanel = destinationPanel; //tile'ın bulunduğu konum yeni destination panel olarak güncellenir.
            destinationPanel.repaint(); //destination panel güncellenir.
            currentPanel.repaint(); //current panel güncellenir.

        } else {

            currentPanel.remove(tile); //rakip taş bulunduğu konumdan silinir.
            currentPanel.setBackground(currentPanel.previousColor);//panelin rengi eski rengini set edilir.
            destinationPanel.add(tile); //destinationPanele yeni buton eklenir.
            tile.locatedPanel = destinationPanel; //tile'ın bulunduğu konum yeni destination panel olarak güncellenir.
            destinationPanel.repaint(); //destination panel güncellenir.
            currentPanel.repaint(); //current panel güncellenir.

        }
    }

    public static void moveControl(MyButton tile, MyPanel square) { //Hamle kontrolleri burada yapılır.
        int currentRow = tile.locatedPanel.row; //taşın bulunduğu panelin satır adını integer'a dönüştürdüm. Çünkü işlemlerde bu kullanılacak.
        int currentColumn = Integer.parseInt(tile.locatedPanel.column); //taşın bulunduğu panelin sütun adını integer'a dönüştürdüm.
        int destinationRow = square.row; //Gidilmek istenilen panelin satıradı.
        int destinationColumn = Integer.parseInt(square.column); //Gidilmek istenilen panelin sütun adı.

        if (tile.type.equals("pawn")) { //Taşın tipi piyon ise bu kod satırları çalışır.

            if (tile.tileColor.equals("white")) { // Piyonlar beyazsa sadece ileri yöne gidebilir
                if (destinationRow == currentRow + 1 && destinationColumn == currentColumn) {//Aynı sutün içinde bir satır ileri gidebilir.
                    makeMove(tile, square, tile.locatedPanel); //hamleyi yapar.
                } else if (currentRow == 66 && destinationRow == 68 && destinationColumn == currentColumn) { //eğer piyon başlangıç konumunda ve ilk defa hamle yapacaksa 2 adım ilerleyebilir.
                    makeMove(tile, square, tile.locatedPanel); //hamleyi yapar.
                } else if (square.panelIncludeButton() != null && !tile.tileColor.equals(square.panelIncludeButton().tileColor) && Math.abs(currentColumn - destinationColumn) == 1 && Math.abs(currentRow - destinationRow) == 1 && square.panelIncludeButton() != null) {
                    //Çaprazında taş varsa o taşı yiyebilir.Burada panelde buton olup olmasdığını da kontrol etmeliyiz.
                    makeMove(tile, square, tile.locatedPanel); //hamleyi yapar.
                } else {//Bunların dışında hareket ettiyse hata mesajı döndürülür.
                    JOptionPane.showMessageDialog(null, "Invalid move", "Pawn cannot move like this.", JOptionPane.WARNING_MESSAGE);
                    isInvalidMove = true; //hatadan sonra bir kez daha hamle yapması için eklendi.
                    return;
                }
            } else {//Piyonlar siyahsa geri yöne de gidebilir.Burada da benzer işlemleri siyah piyonlar için yaptım.
                if (destinationRow == currentRow - 1 && destinationColumn == currentColumn) {
                    makeMove(tile, square, tile.locatedPanel);
                } else if (currentRow == 71 && destinationRow == 69 && destinationColumn == currentColumn) {
                    makeMove(tile, square, tile.locatedPanel);
                } else if (square.panelIncludeButton() != null && !tile.tileColor.equals(square.panelIncludeButton().tileColor) && Math.abs(currentColumn - destinationColumn) == 1 && Math.abs(currentRow - destinationRow) == 1) {
                    makeMove(tile, square, tile.locatedPanel);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid move", "Pawn cannot move like this.", JOptionPane.WARNING_MESSAGE);
                    isInvalidMove = true;
                    return;
                }
            }
        }
        if (tile.type.equals("rook")) { //Taş tipi kale ise bu kod satırları çalıştırılır.
            // İleri, geri, sağa ve sola hareket için koşullar kontrol edilir
            if (currentColumn == destinationColumn) { //Taşın bulunduğu konum ve hedef konum aynı sütündaysa yani taş ileri ya da geri hareket edicekse bu kod satırları çalışır.
                if (destinationRow > currentRow) { //İleri hareket varsa,
                    for (int i = currentRow + 1; i <= destinationRow; i++) {
                        MyPanel panel = getPanelFromName(generateSquareName(i, currentColumn)); //Taş bulana kadar destinationa kadar her paneli kontrol eder.

                        if (panel != null && !panel.panelHasButton().equals("blank") && panel.panelIncludeButton().tileColor.equals(tile.tileColor)) {//Gidilmek istenilen konum için eğer öncesinde taş varsa oraya gidemez.
                            JOptionPane.showMessageDialog(null, "Square Filled", "Invalid move", JOptionPane.WARNING_MESSAGE); //Eğer panel dolu ise hata mesajı döndürülür.
                            isInvalidMove = true; //İnvalid move true yapılır ve bir sonraki durumda hamle yapılmasına izin verilir.
                            return;//Geçersiz bir hamlede diğer kodlar çalışmasın diye ekledim.
                        }
                    }
                    makeMove(tile, square, tile.locatedPanel);//Koşullar sağlanıyorsa move tamamlanır.
                } else if (destinationRow < currentRow) { //Geri hareket varsa,

                    for (int i = currentRow - 1; i >= destinationRow; i--) {//Gidilmek istenilen konuma kadar olan taşlar kontrol edilir. Ve boşsa hamle yapılır.
                        MyPanel panel = getPanelFromName(generateSquareName(i, currentColumn));
                        if (panel != null && !panel.panelHasButton().equals("blank") && panel.panelIncludeButton().tileColor.equals(tile.tileColor)) {
                            JOptionPane.showMessageDialog(null, "Invalid move", "Square Filled", JOptionPane.WARNING_MESSAGE);//Gidilmek istenilen konum için eğer öncesinde taş varsa oraya gidemez.
                            isInvalidMove = true;//İnvalid move true yapılır ve bir sonraki durumda hamle yapılmasına izin verilir.
                            return;
                        }
                    }
                    makeMove(tile, square, tile.locatedPanel);//Koşullar sağlanıyorsa move tamamlanır.
                } else { //Kare başka bir konuma atlatılmak isteniyorsa hareket edemez.Bunun gibi geçersiz hamleler varsa aşağıdaki kod bloğu çalışır.
                    JOptionPane.showMessageDialog(null, "Invalid move", "Same Square", JOptionPane.WARNING_MESSAGE); //Uyarı mesajı kullanıcıya bildirilir.
                    isInvalidMove = true;//İnvalid move true yapılır ve bir sonraki durumda hamle yapılmasına izin verilir.
                }
            } else if (currentRow == destinationRow) { //Aynı satır üzerinde hareket varsa,
                // Yatay hareketler
                if (destinationColumn > currentColumn) {  //Sağa hareket varsa,

                    for (int i = currentColumn + 1; i <= destinationColumn; i++) {//Gidilmek istenilen konuma kadar olan taşlar kontrol edilir. Ve boşsa hamle yapılır.
                        MyPanel panel = getPanelFromName(generateSquareName(currentRow, i));//İlgili satır ve sütündaki taşlar bir değişkene atıldı.
                        if (panel != null && !panel.panelHasButton().equals("blank") && panel.panelIncludeButton().tileColor.equals(tile.tileColor)) { //Panelde taş var ise
                            JOptionPane.showMessageDialog(null, "Invalid move", "Square Filled", JOptionPane.WARNING_MESSAGE);//Uyarı mesajı döndürülür.
                            isInvalidMove = true;//İnvalid move true yapılır ve bir sonraki durumda hamle yapılmasına izin verilir.
                            return;
                        }
                    }
                    makeMove(tile, square, tile.locatedPanel);//Koşullar sağlanıyorsa move tamamlanır.
                } else if (destinationColumn < currentColumn) { // Sola hareket varsa

                    for (int i = currentColumn - 1; i >= destinationColumn; i--) {//Gidilmek istenilen konuma kadar olan taşlar kontrol edilir. Ve boşsa hamle yapılır.
                        MyPanel panel = getPanelFromName(generateSquareName(currentRow, i));//İlgili satır ve sütündaki taşlar bir değişkene atıldı.
                        if (panel != null && !panel.panelHasButton().equals("blank") && panel.panelIncludeButton().tileColor.equals(tile.tileColor)) {//Gidilmek istenilen konum için eğer öncesinde taş varsa oraya gidemez.
                            JOptionPane.showMessageDialog(null, "Invalid move", "Square Filled", JOptionPane.WARNING_MESSAGE);//Uyarı mesajı döndürülür.
                            isInvalidMove = true;//İnvalid move true yapılır ve bir sonraki durumda hamle yapılmasına izin verilir.
                            return;
                        }
                    }
                    makeMove(tile, square, tile.locatedPanel);//Koşullar sağlanıyorsa move tamamlanır.
                } else {//Kare başka bir konuma atlatılmak isteniyorsa hareket edemez.Bunun gibi geçersiz hamleler varsa aşağıdaki kod bloğu çalışır.
                    JOptionPane.showMessageDialog(null, "Invalid move", "Same Square", JOptionPane.WARNING_MESSAGE);//Uyarı mesajı kullanıcıya bildirilir.
                    isInvalidMove = true;//İnvalid move true yapılır ve bir sonraki durumda hamle yapılmasına izin verilir.
                    return;
                }
            } else {//Daha farklı bir hata varsa burası çalışır.
                JOptionPane.showMessageDialog(null, "Invalid move", "Rook cannot move like this.", JOptionPane.WARNING_MESSAGE);
                isInvalidMove = true;
                return;
            }
        }

        if (tile.type.equals("bishop")) { //Fil çapraz aşağı, yukarı, ve bu yönler için geri ve ileri hareket eder
            int rowDifference = Math.abs(destinationRow - currentRow); //satır için başlangıç ve hedef konumu arasındaki farkı alıyoruz çünkü bu şekilde çapraz hareket edebilir.
            int columnDifference = Math.abs(destinationColumn - currentColumn); //sütun için başlangıç ve hedef konumu arasındaki farkı alıyoruz çünkü bu şekilde çapraz hareket edebilir.

            if (rowDifference == columnDifference) { //Çapraz hareket yapmak için satır ve sütunların farkı eşit olmalı

                if (destinationRow < currentRow && destinationColumn > currentColumn) {//Yukarı sola hareket varsa,
                    for (int i = 1; i < rowDifference; i++) {//Karedekine benzer bir mantıkla burada da ilerler.
                        MyPanel panel = getPanelFromName(generateSquareName(currentRow - i, currentColumn + i)); //Çapraz yukarı ve sola ilerleme için satırın i eksiği ve sütünun i fazlası değerler verilir.
                        if (panel != null && !panel.panelHasButton().equals("blank") && panel.panelIncludeButton().tileColor.equals(tile.tileColor)) {//Eğer gidilmek istenilen panel boş değilse veya panelde bulunan butonun rengi tile rengine eşitse,
                            JOptionPane.showMessageDialog(null, "Invalid move", "Square Filled", JOptionPane.WARNING_MESSAGE); //hamle yapılamaz.
                            isInvalidMove = true;
                            return; //Bu hatadan sonra fonksiyonun başına döner.
                        }
                    }
                    makeMove(tile, square, tile.locatedPanel); //Bu durumlardan biri yoksa hamle gerçekleşir.
                } else if (destinationRow < currentRow && destinationColumn < currentColumn) {// Çapraz yukarı ve sağa hareket varsa burası çalışır.
                    for (int i = 1; i < rowDifference; i++) {// Benzer işlemler burada da yapılır.
                        MyPanel panel = getPanelFromName(generateSquareName(currentRow - i, currentColumn - i)); //Yukarı ve sağa hareket için panellerin satır ve sütünlarının i eksik durumlarındaki paneller kontrol edilir.
                        if (panel != null && !panel.panelHasButton().equals("blank") && panel.panelIncludeButton().tileColor.equals(tile.tileColor)) {
                            JOptionPane.showMessageDialog(null, "Square Filled", "Invalid move", JOptionPane.WARNING_MESSAGE);
                            isInvalidMove = true;
                            return;
                        }
                    }
                    makeMove(tile, square, tile.locatedPanel); //Hata yoksa hamle yapılır.

                } else if (destinationRow > currentRow && destinationColumn > currentColumn) {//Çapraz aşağı sola hareket varsa burası çalışır.
                    for (int i = 1; i < rowDifference; i++) {
                        MyPanel panel = getPanelFromName(generateSquareName(currentRow + i, currentColumn + i));//Aşağı ve sola hareket için panellerin satır ve sütünlarının i fazlası durumlarındaki paneller kontrol edilir.
                        if (panel != null && !panel.panelHasButton().equals("blank") && panel.panelIncludeButton().tileColor.equals(tile.tileColor)) {
                            JOptionPane.showMessageDialog(null, "Square Filled", "Invalid move", JOptionPane.WARNING_MESSAGE);
                            isInvalidMove = true;
                            return;
                        }
                    }
                    makeMove(tile, square, tile.locatedPanel);  //Hata yoksa hamle yapılır.

                } else if (destinationRow > currentRow && destinationColumn < currentColumn) {//Çapraz aşağı sağa hareket varsa burası çalışır.
                    for (int i = 1; i < rowDifference; i++) {
                        MyPanel panel = getPanelFromName(generateSquareName(currentRow + i, currentColumn - i)); //Çapraz yukarı ve sola ilerleme için satırın i fazlası ve sütünun i eksiği değerler verilir.
                        if (panel != null && !panel.panelHasButton().equals("blank") && panel.panelIncludeButton().tileColor.equals(tile.tileColor)) {
                            JOptionPane.showMessageDialog(null, "Invalid move", "Square Filled", JOptionPane.WARNING_MESSAGE);
                            isInvalidMove = true;
                            return;
                        }
                    }

                    makeMove(tile, square, tile.locatedPanel); //Hata yoksa hamle yapılır.
                }
            }
        }

        if (tile.type.equals("horse")) { //At 2 kare yukarı sağ ya da sol yöne doğru hareket eder.
            //her iki taş rengi içinde aynı şekilde konumlar hesaplanır.
            int rowDifference = Math.abs(destinationRow - currentRow);
            int columnDifference = Math.abs(destinationColumn - currentColumn);

            if ((rowDifference == 2 && columnDifference == 1) || (rowDifference == 1 && columnDifference == 2)) {//L şeklinde ilerleme için satırlar arasındaki fark 2 sütunlar arasındaki 1 olmalı.
                // Hedef kare boş veya rakip taşının olduğu kare ise hamle yapılabilir
                if (square.panelHasButton().equals("blank") || !tile.tileColor.equals(square.panelIncludeButton().tileColor)) {
                    makeMove(tile, square, tile.locatedPanel);
                } else {
                    JOptionPane.showMessageDialog(null, "Cannot move there", "Invalid move", JOptionPane.WARNING_MESSAGE);
                    isInvalidMove = true;
                    return;
                }
            } else { //Yukarıdakilerden farklı bir konuma hamle yapmak isterse burası çalışır.
                JOptionPane.showMessageDialog(null, "Knight cannot move like this.", "Invalid move", JOptionPane.WARNING_MESSAGE);
                isInvalidMove = true;
                return;
            }
        }

        if (tile.type.equals("king")) { //Şah sadece 1 kare hareket edebilir.
            int rowDifference = Math.abs(destinationRow - currentRow);
            int columnDifference = Math.abs(destinationColumn - currentColumn);
            if ((rowDifference == 1 && columnDifference == 0) || (rowDifference == 0 && columnDifference == 1) || (rowDifference == 1 && columnDifference == 1)) {
                //Aynı sütünda ve peşpeşe farklı satırlarda olabilir, aynı satırda farklı sütunda veya peşpeşe çapraz konumda ise şah hareket edebilir.
                //Önünde boş bir kare varsa şah ilerler veya karede rakip taş var ise şah rakibi yiyebilir.
                if (square.panelHasButton().equals("blank") || !tile.tileColor.equals(square.panelIncludeButton().tileColor)) {
                    makeMove(tile, square, tile.locatedPanel);
                } else {//Bunların dışında hareket edemez.
                    JOptionPane.showMessageDialog(null, "Cannot move to this square", "Invalid move", JOptionPane.WARNING_MESSAGE);
                    isInvalidMove = true;
                    return;
                }
            } else {//Çok daha farklı bir konuma gitmek istiyorsa hata alır.
                JOptionPane.showMessageDialog(null, "King cannot move like this.", "Invalid move", JOptionPane.WARNING_MESSAGE);
                isInvalidMove = true;
                return;
            }
        }
        if (tile.type.equals("queen")) { //Vezir hem fil hem kale gibi hareket edebilir.
            int rowDifference = Math.abs(destinationRow - currentRow); //satır için başlangıç ve hedef konumu arasındaki farkı alıyoruz çünkü bu şekilde çapraz hareket edebilir.
            int columnDifference = Math.abs(destinationColumn - currentColumn); //sütun için başlangıç ve hedef konumu arasındaki farkı alıyoruz çünkü bu şekilde çapraz hareket edebilir.

            if (currentRow == destinationRow || currentColumn == destinationColumn) { //Kale Hareketi aşağıdaki gibidir.
                if (currentRow == destinationRow) { //Yatay harekette sağ ve sol yön kontrolleri yapmalıyız.
                    if (destinationColumn > currentColumn) { //Sağa harekette bu kod çalışır.
                        for (int i = currentColumn + 1; i < destinationColumn; i++) {
                            MyPanel panel = getPanelFromName(generateSquareName(currentRow, i));
                            if (panel != null && !panel.panelHasButton().equals("blank") && tile.tileColor.equals(square.panelIncludeButton().tileColor)) {
                                JOptionPane.showMessageDialog(null, "Invalid move", "Square Filled", JOptionPane.WARNING_MESSAGE);
                                isInvalidMove = true;
                                return;
                            }
                        }
                        makeMove(tile, square, tile.locatedPanel);
                    } else {  //Sola harekette bu kod çalışır.
                        for (int i = currentColumn - 1; i > destinationColumn; i--) {
                            MyPanel panel = getPanelFromName(generateSquareName(currentRow, i));
                            if (panel != null && !panel.panelHasButton().equals("blank") && tile.tileColor.equals(square.panelIncludeButton().tileColor)) {
                                JOptionPane.showMessageDialog(null, "Invalid move", "Square Filled", JOptionPane.WARNING_MESSAGE);
                                isInvalidMove = true;
                                return;
                            }
                        }
                        makeMove(tile, square, tile.locatedPanel);
                    }
                } else { //Dikey yukarı ve aşağı harekette bu kısım çalışır.
                    if (destinationRow > currentRow) { //Dikey aşağı hareket.
                        for (int i = currentRow + 1; i < destinationRow; i++) {
                            MyPanel panel = getPanelFromName(generateSquareName(i, currentColumn));
                            if (panel != null && !panel.panelHasButton().equals("blank") && tile.tileColor.equals(square.panelIncludeButton().tileColor)) {
                                JOptionPane.showMessageDialog(null, "Invalid move", "Square Filled", JOptionPane.WARNING_MESSAGE);
                                isInvalidMove = true;
                                return;
                            }
                        }
                        makeMove(tile, square, tile.locatedPanel);
                    } else { //Dikey yukarı hareket.
                        for (int i = currentRow - 1; i > destinationRow; i--) {
                            MyPanel panel = getPanelFromName(generateSquareName(i, currentColumn));
                            if (panel != null && !panel.panelHasButton().equals("blank") && tile.tileColor.equals(square.panelIncludeButton().tileColor)) {
                                JOptionPane.showMessageDialog(null, "Invalid move", "Square Filled", JOptionPane.WARNING_MESSAGE);
                                isInvalidMove = true;
                                return;
                            }
                        }
                        makeMove(tile, square, tile.locatedPanel);
                    }
                }
            }
            //ÇAPRAZ HAREKET KONTROLÜ
            if (rowDifference == columnDifference) { //Çapraz hareket yapmak için satır ve sütunların farkı eşit olmalı

                if (destinationRow < currentRow && destinationColumn > currentColumn) {//Yukarı sola hareket varsa,
                    for (int i = 1; i < rowDifference; i++) {//Karedekine benzer bir mantıkla burada da ilerler.
                        MyPanel panel = getPanelFromName(generateSquareName(currentRow - i, currentColumn + i)); //Çapraz yukarı ve sola ilerleme için satırın i eksiği ve sütünun i fazlası değerler verilir.
                        if (panel != null && !panel.panelHasButton().equals("blank") && panel.panelIncludeButton().tileColor.equals(tile.tileColor)) {//Eğer gidilmek istenilen panel boş değilse veya panelde bulunan butonun rengi tile rengine eşitse,
                            JOptionPane.showMessageDialog(null, "Invalid move", "Square Filled", JOptionPane.WARNING_MESSAGE); //hamle yapılamaz.
                            isInvalidMove = true;
                            return; //Bu hatadan sonra fonksiyonun başına döner.
                        }
                    }
                    makeMove(tile, square, tile.locatedPanel); //Bu durumlardan biri yoksa hamle gerçekleşir.
                } else if (destinationRow < currentRow && destinationColumn < currentColumn) {// Çapraz yukarı ve sağa hareket varsa burası çalışır.
                    for (int i = 1; i < rowDifference; i++) {// Benzer işlemler burada da yapılır.
                        MyPanel panel = getPanelFromName(generateSquareName(currentRow - i, currentColumn - i)); //Yukarı ve sağa hareket için panellerin satır ve sütünlarının i eksik durumlarındaki paneller kontrol edilir.
                        if (panel != null && !panel.panelHasButton().equals("blank") && panel.panelIncludeButton().tileColor.equals(tile.tileColor)) {
                            JOptionPane.showMessageDialog(null, "Square Filled", "Invalid move", JOptionPane.WARNING_MESSAGE);
                            isInvalidMove = true;
                            return;
                        }
                    }
                    makeMove(tile, square, tile.locatedPanel); //Hata yoksa hamle yapılır.

                } else if (destinationRow > currentRow && destinationColumn > currentColumn) {//Çapraz aşağı sola hareket varsa burası çalışır.
                    for (int i = 1; i < rowDifference; i++) {
                        MyPanel panel = getPanelFromName(generateSquareName(currentRow + i, currentColumn + i));//Aşağı ve sola hareket için panellerin satır ve sütünlarının i fazlası durumlarındaki paneller kontrol edilir.
                        if (panel != null && !panel.panelHasButton().equals("blank") && panel.panelIncludeButton().tileColor.equals(tile.tileColor)) {
                            JOptionPane.showMessageDialog(null, "Square Filled", "Invalid move", JOptionPane.WARNING_MESSAGE);
                            isInvalidMove = true;
                            return;
                        }
                    }
                    makeMove(tile, square, tile.locatedPanel);  //Hata yoksa hamle yapılır.

                } else if (destinationRow > currentRow && destinationColumn < currentColumn) {//Çapraz aşağı sağa hareket varsa burası çalışır.
                    for (int i = 1; i < rowDifference; i++) {
                        MyPanel panel = getPanelFromName(generateSquareName(currentRow + i, currentColumn - i)); //Çapraz yukarı ve sola ilerleme için satırın i fazlası ve sütünun i eksiği değerler verilir.
                        if (panel != null && !panel.panelHasButton().equals("blank") && panel.panelIncludeButton().tileColor.equals(tile.tileColor)) {
                            JOptionPane.showMessageDialog(null, "Invalid move", "Square Filled", JOptionPane.WARNING_MESSAGE);
                            isInvalidMove = true;
                            return;
                        }
                    }

                    makeMove(tile, square, tile.locatedPanel); //Hata yoksa hamle yapılır.
                }
            } else {//Çok daha farklı bir konuma gitmek istiyorsa hata alır.
                JOptionPane.showMessageDialog(null, "Queen cannot move like this.", "Invalid move", JOptionPane.WARNING_MESSAGE);
                isInvalidMove = true;
                return;
            }
        }
    }

    public static String generateSquareName(int row, int column) {//Satırı ve sütunu karşılık gelen karakterlerle birleştirerek square ismini oluşturur.
        int diffRow = row - 65; //önce 65 ten çıkartıp gönderilen değer ile aradaki farkı hesapladım.
        int targetRow = 65 + diffRow; //farkı hedef konuma ekledim.
        char r = (char) targetRow; //Karaktere dönüştürdüm.
        String c = Integer.toString(column); //Sütun adını stringe çevirdim bu değer sayı olarak geliyor.
        return "" + r + c; //Oluşan stringi return ettim.
    }

    public static MyPanel getPanelFromName(String name) { //İsmi verilen paneli bulmak için bu kodu yazdım.
        for (MyPanel panel : myPanels) {//myPanels arraylistte tüm panelleri döndüm eğer panel bulunursa, return ettim.
            if (panel.name.equals(name)) {
                return panel;
            }
        }
        return null;
    }

    public static void main(String[] args) throws InterruptedException {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Chess();//Satranç oyunu run edilir.
            }
        });

    }

}
