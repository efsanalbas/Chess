/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

/**
 *
 * @author nurefsanalbas
 */

public class Message implements java.io.Serializable {
public static enum Message_Type {Name,Move,Tile,Control,Turn,End}
    
    public Message_Type type;
    public Object content;
    public Message(Message_Type t)
    {
        this.type=t;
    }
 
}

