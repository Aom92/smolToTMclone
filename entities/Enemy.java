package src.entities;


import javax.swing.ImageIcon;
import java.awt.Graphics2D;
import src.engine.TileMap;
import src.main.GamePanel;

/**
 * Clase abstracta que permite poder generar diversas clases de enemigos
 * con caracteristicas diferentes, pero que puedan ser alamcenados en un mismo sitio.
 *
 * @author Juárez Pérez Hugo
 * @version 1.0
 */
public abstract class Enemy extends Entity {


    public ImageIcon idleTexture;

    /**
     * Constructor De Enemigos Generico.
     * @param tm    El tilemap donde sera instanciado.
     * @param x Coordenada X.
     * @param y Coordenada Y.
     * @param width Ancho.
     * @param height Alto.
     * @param textureName nombre del archivo de la textura con extencion
     */
    public Enemy(TileMap tm, double x, double y, int width, int height, String textureName){
        super(tm, x, y, width, height, textureName);
    }

    
    public abstract void update();
    
    public abstract void draw(Graphics2D g,GamePanel gp);


        
    
    
}

