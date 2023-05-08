/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projeclient;

import java.util.ArrayList;

/**
 *
 * @author nurefsanalbas
 */
public class MyButton extends javax.swing.JButton {

    public String buttonIndex;
    public MyPanel locatedPanel;
    public String type;

    ArrayList<Character> avaliableWhitePawnLocations(MyButton pawn, int stepSize) {
        char currentLocation = pawn.locatedPanel.row;
        ArrayList<Character> list = calculateNextChars(currentLocation, 2);
        return list;
    }

    ArrayList<Character> avaliableBlackPawnLocations(MyButton pawn, int stepSize) {
        char currentLocation = pawn.locatedPanel.row;
        ArrayList<Character> list = calculatePrevChars(currentLocation, 2);
        return list;
    }

    ArrayList<Character> calculateNextChars(char ch, int stepSize) {
        ArrayList<Character> nextCharArr = new ArrayList<>();
        for (int i = 1; i <= stepSize; i++) {
            int index = ch - 'A'; // karakterin alfabedeki sırasını hesaplar
            char nextChar = (char) ('A' + index + i);
            nextCharArr.add(nextChar);
        }
        return nextCharArr;
    }

    ArrayList<Character> calculatePrevChars(char ch, int stepSize) {
        ArrayList<Character> nextCharArr = new ArrayList<>();
        for (int i = 1; i <= stepSize; i++) {
            int index = ch - 'A'; // karakterin alfabedeki sırasını hesaplar
            char prevChar = (char) ('A' + index - i);
            nextCharArr.add(prevChar);
        }
        return nextCharArr;
    }
}
