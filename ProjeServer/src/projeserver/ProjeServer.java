
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
        Server server = new Server(3000); //Serveri olusturuyoruz ve 3000 nolu porttan dinlemeye basliyor.
        server.Listen(); //server dinlemeye basliyor.
    }
    
}
