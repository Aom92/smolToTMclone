package src.main;

import javax.swing.*;

import src.engine.SoundEffect;
import src.engine.SoundEffect.Volume;
import src.main.GamePanel;
import java.awt.event.*;

/**
 * @author Greta
 */
public class GameWindow extends JFrame  {
   private static final long serialVersionUID = 1L;
   // Estas dimensiones en realidad no afectan, ya que el GamePanel altera las
   // dimensiones de la ventana
   // al ser instanciado.
   public static final int DEFAULT_WIDTH = 640;
   public static final int DEFAULT_HEIGHT = 480;

   // *Se removió el atributo Contentpane por que no es la forma de utilizarlo.
   private JMenuItem itemNuevo;
   private JMenuItem itemConfig;
   private JMenuItem itemSalir;
   private JMenuItem itemAbout;

   private JPanel panelJuego;

   public GameWindow() {
      super("TombOfTheMask");
      initComponents();
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
      setDefaultCloseOperation(3);
      addListeners();
   }

   private void initComponents() {
      JMenuBar barraMenu = new JMenuBar();
      setJMenuBar(barraMenu);

      JMenu menuJuego = new JMenu("Juego");
      barraMenu.add(menuJuego);

      JMenu menuConfig = new JMenu("Configura juego");
      barraMenu.add(menuConfig);

      itemNuevo = new JMenuItem("Nuevo juego", 'n');
      //menuJuego.add(itemNuevo);

      itemConfig = new JMenuItem("Silenciar musica.", 'c');
      menuConfig.add(itemConfig);

      itemSalir = new JMenuItem("Salir", 's');
      menuJuego.add(itemSalir);

      JMenu menuAyuda = new JMenu("Acerca de");
      barraMenu.add(menuAyuda);
      itemAbout = new JMenuItem("About", 'a');
      menuAyuda.add(itemAbout);

      panelJuego = new GamePanel();

   }

   private void addListeners() {
      ActionListener listener = new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent evt) {
            Object obj = evt.getSource();
            if (obj == itemNuevo)
               itemNuevoActionPerformed(evt);
            else if (obj == itemConfig)
               itemConfigActionPerformed(evt);
            else if (obj == itemSalir)
               itemSalirActionPerformed(evt);
            else if (obj == itemAbout)
               itemAboutActionPerformed(evt);
         }
      };
      itemNuevo.addActionListener(listener);
      itemConfig.addActionListener(listener);
      itemSalir.addActionListener(listener);
      itemAbout.addActionListener(listener);
   }

   private void itemNuevoActionPerformed(ActionEvent evt)
   {

      this.setContentPane(new GamePanel());
      this.setVisible(true);
      new GameWindow();
      
      
   }

   private void itemConfigActionPerformed(ActionEvent evt) {
      if (SoundEffect.volume != Volume.MUTE) {
         SoundEffect.volume = Volume.MUTE;
         SoundEffect.BG.stop();
         SoundEffect.DBG.stop();
      } else {
         SoundEffect.volume = Volume.LOW;
         SoundEffect.BG.play(-1);
      }

      

   }

   private void itemSalirActionPerformed(ActionEvent evt) {
      System.exit(0);
   }

   private void itemAboutActionPerformed(ActionEvent evt) {
      JOptionPane.showMessageDialog(this,
            "TOMB OF THE MASK, POR : \n Hugo Juarez, Greta Quiroz , Dylan Gonzales, Daniel Flores ");
   }

   


}

