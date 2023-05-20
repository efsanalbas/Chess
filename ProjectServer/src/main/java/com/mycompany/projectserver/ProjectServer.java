package com.mycompany.projectserver;

/**
 *
 * @author nurefsanalbas
 */
public class ProjectServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Server server = new Server(3000); //Serverı oluşturuyoruz ve 3000 nolu porttan dinlemeye başlıyor.
        server.Listen(); //server dinlemeye başlıyor.
    }

}
