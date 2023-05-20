
package projeclient;

import java.awt.event.MouseAdapter;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JPanel;

/**
 *
 * @author nurefsanalbas
 */
public class MyPanel extends JPanel { //Kendine özel panel özellikleri ekleyebileceğim JPanelden extend alan MyPanel sınıfı oluşturdum.

    public String name;//square adı
    public Color previousColor;//tıklanmadan önceki rengi
    public char row;//satır adı A,B,C,D,E,F,G,H
    public String column;//sütun rengi 1,2,3,4,5,6,7,8   
    public boolean isClicked; //Panele tıklandıysa true olur.

    public MyPanel() { //Panel constructor'ı oluşturdum.
        super();
        this.name = name;
        isClicked = false;

    }

    public boolean isClicked() { //Panele tıklandığında bu metod çalışır.
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) { //Panele tıklanırsa,
                isClicked = true; //IsClicked özelliğini true yaptım.
                setBackground(Color.GREEN);//arka plan rengini yeşil yaptım.
                repaint(); //Ekranı günceller.
            }
        });
        return isClicked;
    }

    public String panelHasButton() { //Panelde button varsa rengini döndürür yoksa boş yani "blank" döner.
        Component[] components = this.getComponents();//panelin bileşenlerini alır.
        for (Component component : components) {
            if (component instanceof MyButton) {//button türünde olanların rengini döner.
                return ((MyButton) component).tileColor;
            }
        }
        return "blank";
    }

    public MyButton panelIncludeButton() { //Panelde button varsa butonu döndürür yoksa null döner.
        Component[] components = this.getComponents();//panelin bileşenlerini alır.
        for (Component component : components) {
            if (component instanceof MyButton) {//button türünde olanların bileşenini döner.
                return ((MyButton) component);
            }
        }
        return null;
    }

    public void setClicked(boolean clicked) {//panele tıklandığında true olur.
        isClicked = clicked;
    }


    
}
