package src.main;

import javax.swing.JFrame;
import src.main.GamePanel;
import src.main.GameWindow;
import java.awt.event.*;

/**
 * Aqui se ubica el main que corre todo el programa.
 * @author Juárez Pérez Hugo
 * 
 */
public class Game  {
    public static void main(String[] args) {
        GameWindow window = new GameWindow();
        //Instanciar un GamePanel.
        window.setContentPane(new GamePanel());
        //Instanciar un Event Listener.
        
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        //window.pack();
        window.setLocationByPlatform(true);
        window.setVisible(true);
    }
}
