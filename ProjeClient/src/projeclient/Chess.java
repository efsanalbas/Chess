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
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Chess {
    //framedeki komponentlere erişim için satatik oyun değişkeni

    public static Chess thisGame;
    public static MyPanel currentClickedPanel;

    static int k = 8, l = 8, m = 8, n = 8, p = 8, r = 8, s = 8, t = 8;
    static JFrame frame = new JFrame("Board");
    static Color color = new Color(162, 97, 57);
    static Color color2 = new Color(237, 211, 169);
    static ArrayList<MyPanel> myPanels = new ArrayList<>();
    static ArrayList<MyButton> myButtons = new ArrayList<>();
    static ArrayList<MyPanel> currentAvaliableSquares = new ArrayList<>();
    static OutputStream clientOutput;
    static byte bytes[];
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

    public static void addButton(ArrayList panels, MyPanel panel, ArrayList buttons, String pieceName) {
        MyButton button = new MyButton();
        ImageIcon icon = new ImageIcon(pieceName);
        button.setIcon(icon);
        button.buttonIndex = panel.name;
        button.locatedPanel = panel;
        button.type = pieceName;
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
                    addButton(myPanels, square, myButtons, "pawnBlack.png");
                }
                if (square.name.startsWith("B")) {
                    addButton(myPanels, square, myButtons, "pawnWhite.png");
                }
                if (square.name.contains("A8") || square.name.contains("A1")) {
                    addButton(myPanels, square, myButtons, "rookWhite.png");
                }
                if (square.name.contains("H1") || square.name.contains("H8")) {
                    addButton(myPanels, square, myButtons, "rookBlack.png");
                }
                if (square.name.contains("A7") || square.name.contains("A2")) {
                    addButton(myPanels, square, myButtons, "horseWhite.png");
                }
                if (square.name.contains("H7") || square.name.contains("H2")) {
                    addButton(myPanels, square, myButtons, "horseBlack.png");
                }
                if (square.name.contains("A6") || square.name.contains("A3")) {
                    addButton(myPanels, square, myButtons, "bishopWhite.png");
                }
                if (square.name.contains("H6") || square.name.contains("H3")) {
                    addButton(myPanels, square, myButtons, "bishopBlack.png");
                }

                if (square.name.contains("A5")) {
                    addButton(myPanels, square, myButtons, "kingWhite.png");

                }
                if (square.name.contains("H5")) {
                    addButton(myPanels, square, myButtons, "kingBlack.png");
                }
                if (square.name.endsWith("A4")) {
                    addButton(myPanels, square, myButtons, "queenWhite.png");
                }
                if (square.name.endsWith("H4")) {
                    addButton(myPanels, square, myButtons, "queenBlack.png");
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
        frame.add(mainPanel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(350, 350, 800, 800);
        frame.setVisible(true);

    }

    public void gameBoard() {
        gameScreen();
        for (int j = 0; j < myButtons.size(); j++) {//butonu bulmak için
            isClicked(myButtons.get(j));
        }

        for (int i = 0; i < myPanels.size(); i++) {
            final int index = i;
            myPanels.get(index).addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    myPanels.get(index).setClicked(true);
                    System.out.println(myPanels.get(index).name + " clicked");
                    Message msg = new Message(Message.Message_Type.Hamle);
                    msg.content = currentClickedPanel.name + " to " + myPanels.get(index).name;
                    Client.Send(msg);
                    myPanels.get(index).repaint();
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

                parentPanel.setBackground(Color.green);
                currentPanel = parentPanel;
                //yorum satırına aldığım yerlerin kontrolleri serverda yapılacak
                //servera bütün hamlelerin neden gitmediği sorunu çözülecek
                //serverda çalışan kısımlar thread kullanılarak yeniden tasarlanacak.

                System.out.println("Clicked button is on panel: " + parentPanel);
                currentClickedPanel = parentPanel;

            }
        });
    }

    public static void main(String[] args) throws InterruptedException {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Chess();
            }
        });

    }

}
