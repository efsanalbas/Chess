/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projeclient;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;

import java.awt.Color;
import javax.swing.JPanel;

/**
 *
 * @author nurefsanalbas
 */
public class MyPanel extends JPanel {

    public String name;
    public Color previousColor;
    public char row;//A,B,C,D,E,F,G,H
    public String column;//1,2,3,4,5,6,7,8   
    public boolean isClicked;

    public MyPanel() {
        super();
        this.name = name;
        isClicked = false;
        
    }

    public boolean isClicked() {
     this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                isClicked = true; // isClicked özelliğini true yap
                setBackground(Color.GREEN);
                repaint();
            }
       
        });
     return isClicked;
    }
public void setClicked(boolean clicked) {
    isClicked = clicked;
}
}
