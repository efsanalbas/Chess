
package projeclient;

import java.awt.event.MouseAdapter;
import java.awt.Color;
import java.awt.Component;
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

    public String panelHasButton() {
        Component[] components = this.getComponents();
        for (Component component : components) {
            if (component instanceof MyButton) {
                return ((MyButton) component).tileColor;
            }
        }
        return "blank";
    }

    public MyButton panelIncludeButton() {
        Component[] components = this.getComponents();
        for (Component component : components) {
            if (component instanceof MyButton) {
                return ((MyButton) component);
            }
        }
        return null;
    }

    public void setClicked(boolean clicked) {
        isClicked = clicked;
    }

    public String getNameFromThePanel(MyPanel panel) {
        // satır bilgisini al
        char row = panel.row;

        // sütun bilgisini al
        String column = panel.column;

        // satır ve sütunu birleştirerek panel adını oluştur
        String panelName = String.valueOf(row) + column;

        return panelName;
    }

    
}
