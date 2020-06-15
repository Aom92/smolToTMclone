package src.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.event.*;

import java.util.Random;

////OUR CLASSES
import src.entities.*;
import src.engine.*;

/**
 * GAME PANEL ES DONDE OCURRE EL GAME LOOP EN ESTE CASO
 * 
 * AQUI ES DONDE SE ACTUALIZA, SE LEE EL INPUT Y SE GENERA LA IMAGEN.
 * 
 * @author Juárez Pérez Hugo
 */
public class GamePanel extends JPanel implements Runnable, KeyListener {
    private static final long serialVersionUID = 1L;

    /** The screen WIDTH of the drawing panel */
    public static final int WIDTH = 640;
    /** The screen HEIGHT of the drwaing panel */
    public static final int HEIGHT = 480;

    /** The ball object we will move around on the screen */
    private Player player;

    private TileMap tileMap;

    private Integer points = 0;

    
    public volatile ArrayList<Enemy> enemies = new ArrayList<Enemy>();

    /** EL Objeto de donde se dibuja la imagen */
    private Graphics2D g;

    private BufferedImage image;


    /** The game thread that represents our game loop */
    private Thread thread;
    /** A boolean value indicating that our game is runing */
    private boolean running;
    /** The number of frames per second we want the game to run in */
    private final int FPS = 144;
    

    public GamePanel() {
        super();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        requestFocus();
        addKeyListener(this);

    }

    /**
     * This is invokved automatically but the AWT framework when our component the
     * GamePanel is added to the screen. We trap into this method in order to start
     * up our game loop in its own thread.
     */
    public void addNotify() {
        super.addNotify();
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
        addKeyListener(this);
        running = true;
    }

    /**
     * Initializes our game object - the ball and sets it's initial direction and
     * speed on the screen.
     * 
     * @throws IOException
     */
    public void init() throws IOException {

        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

        g = (Graphics2D) image.getGraphics();

        /** El tamaño por tile debe ser igual al ancho del jugador */
        tileMap = new TileMap("./assets/primernivel.txt", 32);
        player = new Player(tileMap, 50, 50, 32, 32, "char.png");
        player.setx(2160);
        player.sety(1712);

        enemies.add(new BatEnemy(tileMap,2160,1488,32,32,"bat.png"));
        enemies.add(new BatEnemy(tileMap,178,210,32,32,"bat.png"));
        enemies.add(new BatEnemy(tileMap,381,210,32,32,"bat.png"));
        enemies.add(new BatEnemy(tileMap,1520,272,32,32,"bat.png"));

    }

    public void init(String levelname ) throws IOException {
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

        g = (Graphics2D) image.getGraphics();
        enemies.clear();

        /** El tamaño por tile debe ser igual al ancho del jugador */
        tileMap = new TileMap("./assets/"+levelname, 32);
        player = new Player(tileMap, 50, 50, 32, 32, "char.png");
        player.setx(2160);
        player.sety(1712);



        enemies.add(new BatEnemy(tileMap,1488,1520,32,32,"bat.png"));
        enemies.add(new BatEnemy(tileMap,1616,1520,32,32,"bat.png"));
        enemies.add(new BatEnemy(tileMap,464,1520,32,32,"bat.png"));
        enemies.add(new BatEnemy(tileMap,80,1136,32,32,"bat.png"));
        enemies.add(new BatEnemy(tileMap,592,1360,32,32,"bat.png"));
        enemies.add(new BatEnemy(tileMap,976,1264,32,32,"bat.png"));
        enemies.add(new BatEnemy(tileMap,1652,1200,32,32,"bat.png"));
        enemies.add(new BatEnemy(tileMap,144,496,32,32,"bat.png"));
        enemies.add(new BatEnemy(tileMap,1424,496,32,32,"bat.png"));



    }

    /** Repaints the game objects */
    public void paintComponent(final Graphics g) {

        super.paintComponent(g);

        
        
        if(player != null){
            if(player.currAnim != null) player.idleTexture = player.currAnim.getImage();
            tileMap.draw((Graphics2D) g, this);
            player.draw((Graphics2D) g, this);
            g.setColor(Color.MAGENTA);
            
            for (Enemy enemy : enemies) {
                enemy.draw((Graphics2D)g, this);
            }
            
        }
        

    }

    /**
     * The game loop: This is a simple game loop that only: -Updates the gameworld
     * -renders the screen (indirectly by requesting a repaint) -draws the game
     * image
     */
    @Override
    public void run() {
        try {
            init();
        } catch (IOException e1) {
            
            e1.printStackTrace();
        }

        long elapsed;
        
        
       
        Random rnd = new Random();
        /** FPS CONTROL VARIABLES */
        double ups = FPS;
        double fps = FPS;

        long initialTime = System.nanoTime();
        final double timeU = 1000000000 / ups;
        final double timeF = 1000000000 / fps;

        double deltaU = 0, deltaF = 0;
        int frames = 0, ticks = 0;
        long timer = System.currentTimeMillis();

        SoundEffect.BG.play(1);

        

        while (running) {
            elapsed = System.nanoTime();
            

            long currentTime = System.nanoTime();
            deltaU += (currentTime - initialTime) / timeU;
            deltaF += (currentTime - initialTime) / timeF;
            initialTime = currentTime;
            

            /** WAIT TIME CALCULATIONS */
            if (deltaU >= 1) {
                try {
                    Thread.sleep(rnd.nextInt(10));
                    /** UPDATING */
                    player.update();
                    points +=  tileMap.update((int)player.getx(),(int)player.gety());
                    for (Enemy enemy : enemies) {
                        enemy.update();
                        if ( (int)enemy.getx() == (int)player.getx() && (int)enemy.gety() == (int)player.gety() ){
                            player.hurt = true;
                        }
                    }
                    if(player.currAnim != null) player.currAnim.update(elapsed);
                    if(player.hurt){
                        running = false;
                        SoundEffect.BG.stop();
                        SoundEffect.DBG.play(SoundEffect.DBG.getPos());
                        JOptionPane.showMessageDialog(this, "G A M E    O V E R ", "", JOptionPane.ERROR_MESSAGE);
                        
                    }
                    
                    if(player.levelend == true) {
                        try {
                            init("segundonivel.txt");
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }  
                    /** END OF UPDATE BLOCK */
                    ticks++;
                    deltaU--;
                } catch (InterruptedException e) {
                    
                    e.printStackTrace();
                }
            }
            if (deltaF >= 1) {
                try {
                    Thread.sleep(rnd.nextInt(10));
                    
                    /** RENDERING */
                    repaint();
                    player.movtrail.update(elapsed);
                    /** END OF RENDERING BLOCK */
                    
                    
                    
                    /** END OF DRAWING BLOCK */

                    frames++;
                    deltaF--;
                } catch (InterruptedException e) {
                    
                    e.printStackTrace();
                }
            }

            if (System.currentTimeMillis() - timer > 1000){
                System.out.println(String.format("FPS: %s,  X: %s, Y: %s , POINTS: %s ",frames,player.getx(),player.gety(), points ));
                frames = 0;
                ticks = 0;
                timer+= 1000;
                
            }

            /** END OF WAIT TIME CALCULATIONS */
            
            /** DRAWING  */
            g.drawImage(image, 0, 0, null);
            
            
        }
    }


    public void keyTyped(KeyEvent key){}
	public void keyPressed(KeyEvent key){

		int code = key.getKeyCode();

        if (player.inMovement == false ){

            switch (code){
                case KeyEvent.VK_LEFT:
                    player.setLeft(true);
                    break;
                case KeyEvent.VK_RIGHT:
                    player.setRight(true);
                    break;
                case KeyEvent.VK_UP:
                    player.setUp(true);
                    break;
                case KeyEvent.VK_DOWN:
                    player.setDown(true);
                    break;
            }
            
            SoundEffect.MOVE.play();
        }
		
	}

	public void keyReleased(KeyEvent key){

		int code = key.getKeyCode();

        if (player.inMovement == false ){
            if(code == KeyEvent.VK_LEFT){
                player.setLeft(true);
                player.setRight(false);
                player.setUp(false);
                player.setDown(false);
            }
            if(code == KeyEvent.VK_RIGHT){
                player.setRight(true);
                
                player.setLeft(false);
                player.setUp(false);
                player.setDown(false);
            }
            if(code == KeyEvent.VK_UP){
                player.setUp(true);
                
                player.setDown(false);
                player.setLeft(false);
                player.setRight(false);
            }
            if(code == KeyEvent.VK_DOWN){
                player.setDown(true);
                player.setUp(false);
                player.setLeft(false);
                player.setRight(false);
            }
        }
		
	}



}