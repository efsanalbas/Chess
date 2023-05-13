/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
import java.io.OutputStream;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Chess {

    public static boolean isWhiteTurn = true;
    public static Chess thisGame;
    public static MyPanel currentClickedPanel;
    public static MyButton currentClickedButton;
    public static MyPanel rivalsPanel;
//    public static MyButton rivalMoveTile;
    public static String rivalMoveTileName;
    // public static MyPanel rivalMovePanel;
    public static String rivalMovePanelName;

    static int k = 8, l = 8, m = 8, n = 8, p = 8, r = 8, s = 8, t = 8;
    static JFrame frame = new JFrame("Board");
    static Color color = new Color(162, 97, 57);
    static Color color2 = new Color(237, 211, 169);
    static ArrayList<MyPanel> myPanels = new ArrayList<>();
    static ArrayList<MyButton> myButtons = new ArrayList<>();
    static ArrayList<MyButton> blackTiles = new ArrayList<>();
    static ArrayList<MyButton> whiteTiles = new ArrayList<>();
    static ArrayList<MyPanel> currentAvaliableSquares = new ArrayList<>();
    static OutputStream clientOutput;
    static int rowCount = 8;
    static int columnCount = 8;
    static MyPanel[][] board = new MyPanel[rowCount][columnCount];
    private static MyPanel currentPanel = null;
    static JButton connectButton;
    public static JTextField usernameField;

    public Chess() {
        gameBoard();
        thisGame = this;
    }

    public static void addButton(ArrayList panels, MyPanel panel, ArrayList buttons, String pieceName, String tileColor, String type) {
        MyButton button = new MyButton();
        ImageIcon icon = new ImageIcon(pieceName);
        button.setIcon(icon);
        button.buttonIndex = panel.name;
        button.locatedPanel = panel;
        button.type = type;
        button.tileColor = tileColor;
        if (tileColor.equals("black")) {
            blackTiles.add(button);
        }
        if (tileColor.equals("white")) {
            whiteTiles.add(button);
        }
        buttons.add(button);
        panel.add(button);

    }

    public static void gameScreen() {

        // Ana panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.BLACK);
        // Bağlanma butonu ve kullanıcı adı alanı içeren panel
        JPanel connectionPanel = new JPanel();
        usernameField = new JTextField(20);
        connectButton = new JButton("Connect");
        connectionPanel.add(usernameField);
        connectionPanel.add(connectButton);
        JPanel chessBoard = new JPanel(new GridLayout(8, 8));
        mainPanel.add(connectionPanel, BorderLayout.NORTH);

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                MyPanel square = new MyPanel();
                if ((i + j) % 2 == 0) {
                    square.setBackground(color);
                    square.previousColor = color;
                } else {
                    square.setBackground(color2);
                    square.previousColor = color2;
                }

                if (i % 8 == 0) {

                    square.setForeground(Color.black);
                    square.name = "A" + (k);
                    square.row = 'A';
                    square.column = Integer.toString(k);

                    k--;
                }
                if (i % 8 == 1) {
                    square.setForeground(Color.black);
                    square.name = "B" + (l);
                    square.row = 'B';
                    square.column = Integer.toString(l);
                    l--;
                }
                if (i % 8 == 2) {

                    square.setForeground(Color.black);
                    square.name = "C" + (m);
                    square.row = 'C';
                    square.column = Integer.toString(m);
                    m--;
                }
                if (i % 8 == 3) {

                    square.setForeground(Color.black);
                    square.name = "D" + (n);
                    square.row = 'D';
                    square.column = Integer.toString(n);
                    n--;
                }
                if (i % 8 == 4) {

                    square.setForeground(Color.black);
                    square.name = "E" + (p);
                    square.row = 'E';
                    square.column = Integer.toString(p);
                    p--;
                }
                if (i % 8 == 5) {

                    square.setForeground(Color.black);
                    square.name = "F" + r;
                    square.row = 'F';
                    square.column = Integer.toString(r);
                    r--;
                }
                if (i % 8 == 6) {

                    square.setForeground(Color.black);
                    square.name = "G" + s;
                    square.row = 'G';
                    square.column = Integer.toString(s);

                    s--;
                }
                if (i % 8 == 7) {
                    square.setForeground(Color.black);
                    square.name = "H" + t;
                    square.row = 'H';
                    square.column = Integer.toString(t);
                    t--;
                }

                if (square.name.startsWith("G")) {
                    addButton(myPanels, square, myButtons, "pawnBlack.png", "black", "pawn");
                }
                if (square.name.startsWith("B")) {
                    addButton(myPanels, square, myButtons, "pawnWhite.png", "white", "pawn");
                }
                if (square.name.contains("A8") || square.name.contains("A1")) {
                    addButton(myPanels, square, myButtons, "rookWhite.png", "white", "rook");
                }
                if (square.name.contains("H1") || square.name.contains("H8")) {
                    addButton(myPanels, square, myButtons, "rookBlack.png", "black", "rook");
                }
                if (square.name.contains("A7") || square.name.contains("A2")) {
                    addButton(myPanels, square, myButtons, "horseWhite.png", "white", "horse");
                }
                if (square.name.contains("H7") || square.name.contains("H2")) {
                    addButton(myPanels, square, myButtons, "horseBlack.png", "black", "horse");
                }
                if (square.name.contains("A6") || square.name.contains("A3")) {
                    addButton(myPanels, square, myButtons, "bishopWhite.png", "white", "bishop");
                }
                if (square.name.contains("H6") || square.name.contains("H3")) {
                    addButton(myPanels, square, myButtons, "bishopBlack.png", "black", "bishop");
                }

                if (square.name.contains("A5")) {
                    addButton(myPanels, square, myButtons, "kingWhite.png", "white", "king");

                }
                if (square.name.contains("H5")) {
                    addButton(myPanels, square, myButtons, "kingBlack.png", "black", "king");
                }
                if (square.name.endsWith("A4")) {
                    addButton(myPanels, square, myButtons, "queenWhite.png", "white", "queen");
                }
                if (square.name.endsWith("H4")) {
                    addButton(myPanels, square, myButtons, "queenBlack.png", "black", "queen");
                }
                board[i][j] = square;
                myPanels.add(square);
                chessBoard.add(board[i][j]);
            }

        }
        mainPanel.add(chessBoard, BorderLayout.CENTER);

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                System.out.print(board[i][j].name + " ");
            }
            System.out.println();
        }

        connectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Client.Start("127.0.0.1", 3000);
            }
        });

        for (int j = 0; j < myButtons.size(); j++) {//butonu bulmak için
            isClicked(myButtons.get(j));
        }
        frame.add(mainPanel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(350, 350, 800, 800);
        frame.setVisible(true);
    }

    public static void gameBoard() {
        gameScreen();

        for (int i = 0; i < myPanels.size(); i++) {
            final int index = i;
            myPanels.get(index).addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {

                    if (currentClickedButton.tileColor != null && Client.myTile.equals(currentClickedButton.tileColor)) {
                        // Rakibin ekranını güncelliyoruz
                        
                        myPanels.get(index).setClicked(true);
                        moveControl(currentClickedButton, myPanels.get(index));
                        System.out.println(myPanels.get(index).name + " clicked");
                        Message msg = new Message(Message.Message_Type.Hamle);
                        msg.content = currentClickedButton.tileColor + " to " + currentClickedPanel.name + " to " + myPanels.get(index).name;
                        Client.Send(msg);

                    }
                }
            });
        }
    }

    public static void isClicked(MyButton button) {

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (currentPanel != null) {
                    currentPanel.setBackground(currentPanel.previousColor);
                }
                for (int i = 0; i < currentAvaliableSquares.size(); i++) {
                    if (currentAvaliableSquares.get(i) != null) {
                        currentAvaliableSquares.get(i).setBackground(currentAvaliableSquares.get(i).previousColor);
                    }
                }

                MyButton clickedButton = (MyButton) e.getSource();
                MyPanel parentPanel = (MyPanel) clickedButton.getParent();
                currentClickedButton = clickedButton;
                parentPanel.setBackground(Color.green);
                currentPanel = parentPanel;
                System.out.println("Clicked button is on panel: " + parentPanel);
                currentClickedPanel = parentPanel;

            }
        });
    }

    public static void makeMove(MyButton tile, MyPanel destinationPanel, MyPanel currentPanel) {
        if (!destinationPanel.panelHasButton().equals("blank") && destinationPanel.panelHasButton().equals(tile.tileColor)) { //Gidilmek istenen konum boş değilse ya da aynı renk taş içeriyorsa hamle yapılamaz.
            JOptionPane.showMessageDialog(null, "Invalid move", "Square Filled", JOptionPane.WARNING_MESSAGE);
        }
        if (!destinationPanel.panelHasButton().equals(tile.tileColor) && !destinationPanel.panelHasButton().equals("blank")) {

            currentPanel.remove(tile);
            currentPanel.setBackground(currentPanel.previousColor);
            destinationPanel.remove(destinationPanel.panelIncludeButton());
            destinationPanel.add(tile);
            tile.locatedPanel = destinationPanel;
            destinationPanel.repaint();
            currentPanel.repaint();
        } else {

            currentPanel.remove(tile);
            currentPanel.setBackground(currentPanel.previousColor);
            destinationPanel.add(tile);
            tile.locatedPanel = destinationPanel;
            destinationPanel.repaint();
            currentPanel.repaint();
        }
    }

    public static void moveControl(MyButton tile, MyPanel square) {
        int currentRow = tile.locatedPanel.row;
        int currentColumn = Integer.parseInt(tile.locatedPanel.column);
        int destinationRow = square.row;
        int destinationColumn = Integer.parseInt(square.column);

        if (tile.type.equals("pawn")) {
            // Piyonlar beyazsa sadece ileri yöne gidebilir
            if (tile.tileColor.equals("white")) {
                if (destinationRow == currentRow + 1 && destinationColumn == currentColumn) {
                    makeMove(tile, square, tile.locatedPanel);
                } else if (currentRow == 66 && destinationRow == 68 && destinationColumn == currentColumn) {
                    makeMove(tile, square, tile.locatedPanel);
                } else if (!tile.tileColor.equals(square.panelIncludeButton().tileColor) && Math.abs(currentColumn - destinationColumn) == 1 && Math.abs(currentRow - destinationRow) == 1) {
                    makeMove(tile, square, tile.locatedPanel);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid move", "Pawn cannot move like this.", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            } else {//Piyonlar siyahsa geri yöne de gidebilir.
                if (destinationRow == currentRow - 1 && destinationColumn == currentColumn) {
                    makeMove(tile, square, tile.locatedPanel);
                } else if (currentRow == 71 && destinationRow == 69 && destinationColumn == currentColumn) {
                    makeMove(tile, square, tile.locatedPanel);
                } else if (!tile.tileColor.equals(square.panelIncludeButton().tileColor) && Math.abs(currentColumn - destinationColumn) == 1 && Math.abs(currentRow - destinationRow) == 1) {
                    makeMove(tile, square, tile.locatedPanel);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid move", "Pawn cannot move like this.", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }
        }
        if (tile.type.equals("rook")) {
            // İleri, geri, sağa ve sola hareket için koşullar kontrol edilir
            if (currentColumn == destinationColumn) {
                // Dikey hareketler
                if (destinationRow > currentRow) {
                    // İleri hareket
                    for (int i = currentRow + 1; i < destinationRow; i++) {
                        MyPanel panel = getPanelFromName(squareName(i, currentColumn));
                        if (panel != null && !panel.panelHasButton().equals("blank")) {
                            JOptionPane.showMessageDialog(null, "Invalid move", "Square Filled", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                    }
                    makeMove(tile, square, tile.locatedPanel);
                } else {
                    // Geri hareket
                    for (int i = currentRow - 1; i > destinationRow; i--) {
                        MyPanel panel = getPanelFromName(squareName(i, currentColumn));
                        if (panel != null && !panel.panelHasButton().equals("blank")) {
                            JOptionPane.showMessageDialog(null, "Invalid move", "Square Filled", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                    }
                    makeMove(tile, square, tile.locatedPanel);
                }
            } else if (currentRow == destinationRow) {
                // Yatay hareketler
                if (destinationColumn > currentColumn) {
                    // Sağa hareket
                    for (int i = currentColumn + 1; i < destinationColumn; i++) {
                        MyPanel panel = getPanelFromName(squareName(currentRow, i));
                        if (panel != null && !panel.panelHasButton().equals("blank")) {
                            JOptionPane.showMessageDialog(null, "Invalid move", "Square Filled", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                    }
                    makeMove(tile, square, tile.locatedPanel);
                } else {
                    // Sola hareket
                    for (int i = currentColumn - 1; i > destinationColumn; i--) {
                        MyPanel panel = getPanelFromName(squareName(currentRow, i));
                        if (panel != null && !panel.panelHasButton().equals("blank")) {
                            JOptionPane.showMessageDialog(null, "Invalid move", "Square Filled", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                    }
                    makeMove(tile, square, tile.locatedPanel);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Rook cannot move like this.", "Invalid Move", JOptionPane.WARNING_MESSAGE);
            }
        }
        if (tile.type.equals("bishop")) { //Fil çapraz sağ, sol, ve bu yönler için geri hareket eder
            int rowDifference = Math.abs(destinationRow - currentRow);
            int columnDifference = Math.abs(destinationColumn - currentColumn);

            // Çapraz hareket yapmak için satır ve sütunların farkı eşit olmalı
            if (rowDifference == columnDifference) {
                // Çapraz sağa-ileri
                if (destinationRow < currentRow && destinationColumn > currentColumn) {
                    for (int i = 1; i < rowDifference; i++) {
                        MyPanel panel = getPanelFromName(squareName(currentRow - i, currentColumn + i));
                        if (panel != null && !panel.panelHasButton().equals("blank")) {
                            JOptionPane.showMessageDialog(null, "Invalid move", "Square Filled", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                    }
                    makeMove(tile, square, tile.locatedPanel);

                } // Çapraz sola-ileri
                else if (destinationRow < currentRow && destinationColumn < currentColumn) {
                    for (int i = 1; i < rowDifference; i++) {
                        MyPanel panel = getPanelFromName(squareName(currentRow - i, currentColumn - i));
                        if (panel != null && !panel.panelHasButton().equals("blank")) {
                            JOptionPane.showMessageDialog(null, "Square Filled", "Invalid move", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                    }
                    makeMove(tile, square, tile.locatedPanel);

                } // Çapraz sağa-geri
                else if (destinationRow > currentRow && destinationColumn > currentColumn) {
                    for (int i = 1; i < rowDifference; i++) {
                        MyPanel panel = getPanelFromName(squareName(currentRow + i, currentColumn + i));
                        if (panel != null && !panel.panelHasButton().equals("blank")) {
                            JOptionPane.showMessageDialog(null, "Square Filled", "Invalid move", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                    }
                    makeMove(tile, square, tile.locatedPanel);

                } // Çapraz sola-geri
                else if (destinationRow > currentRow && destinationColumn < currentColumn) {
                    for (int i = 1; i < rowDifference; i++) {
                        MyPanel panel = getPanelFromName(squareName(currentRow + i, currentColumn - i));
                        if (panel != null && !panel.panelHasButton().equals("blank")) {
                            JOptionPane.showMessageDialog(null, "Invalid move", "Square Filled", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                    }
                    makeMove(tile, square, tile.locatedPanel);
                }
                // Yeme durumu
                if (!tile.tileColor.equals(square.panelIncludeButton().tileColor)) {
                    makeMove(tile, square, tile.locatedPanel);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Bishop cannot move like this.", "Invalid move", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }
        if (tile.type.equals("horse")) {
            // At 2 kare yuları sağ ya da sol yöne doğru hareket eder.
            int rowDifference = Math.abs(destinationRow - currentRow);
            int columnDifference = Math.abs(destinationColumn - currentColumn);
            //her iki taş içinde aynı şekilde konumlar hesaplanır.
            if ((rowDifference == 2 && columnDifference == 1) || (rowDifference == 1 && columnDifference == 2)) {
                // Hedef kare boş veya rakip taşının olduğu kare ise hamle yapılabilir
                if (square.panelHasButton().equals("blank") || !tile.tileColor.equals(square.panelIncludeButton().tileColor)) {
                    makeMove(tile, square, tile.locatedPanel);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid move", "Cannot move there", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            } else {
                JOptionPane.showMessageDialog(null, "Invalid move", "Knight cannot move like this.", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }
        if (tile.type.equals("king")) {
            //Şah sadece 1 kare şeklinde hareket edebilir.
            int rowDifference = Math.abs(destinationRow - currentRow);
            int columnDifference = Math.abs(destinationColumn - currentColumn);
            if ((rowDifference == 1 && columnDifference == 0) || (rowDifference == 0 && columnDifference == 1) || (rowDifference == 1 && columnDifference == 1)) {
                // Önündeki kare boş ise şah ilerler veya karede rakip taş var ise şah rakibi yiyebilir.
                if (square.panelHasButton().equals("blank") || !tile.tileColor.equals(square.panelIncludeButton().tileColor)) {
                    makeMove(tile, square, tile.locatedPanel);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid move", "Cannot move to this square", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            } else {
                JOptionPane.showMessageDialog(null, "Invalid move", "King cannot move like this.", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }
        if (tile.type.equals("queen")) {
            //buraya gerekli kontrolleri ekleyeceğim.
            makeMove(tile, square, tile.locatedPanel);
        }

    }

    public static String squareName(int row, int column) {
        // satırı sütuna karşılık gelen harflerle birleştirerek kare ismini oluşturur.
        char col = (char) ('A' + column);
        char rw = (char) ('8' - row);
        return "" + col + rw;
    }

    public static MyPanel getPanelFromName(String name) {
        for (MyPanel panel : myPanels) {
            if (panel.name.equals(name)) {
                return panel;
            }
        }
        return null;
    }

    public static void main(String[] args) throws InterruptedException {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Chess();
            }
        });

    }

}
