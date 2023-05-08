/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package projeserver;

/**
 *
 * @author nurefsanalbas
 */
public class ProjeServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Server server = new Server(3000);
        server.Listen();
    }
    
}
