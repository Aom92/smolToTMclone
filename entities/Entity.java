package src.entities;

import java.awt.Graphics2D;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import src.engine.TileMap;
import src.main.GamePanel;


/**
 * Clase Abstracta Entity, basada en la clase player proporcionada por Daniel, esto permite generar
 * diversas unidades adicionales, tales como enemigos que se mueven como obstaculos.
 * @author Daniel Flores Crúz
 * @author Hugo Juárez Pérez
 * 
 */
public abstract class Entity {

    protected double x,y;
    protected double dx, dy;

    protected int width, height;

    public boolean left, right, up, down;

    public boolean levelend, hurt;
    protected double moveSpeed, maxSpeed, stopSpeed;

    protected TileMap tileMap;
    public ImageIcon idleTexture;

    protected boolean topLeft, topRight, bottomLeft, bottomRight;

    public Entity(TileMap tm, double x, double y, int width, int height, String textureName ) 
    {
        try {
            File inputFile = new File("./assets/"+textureName);
            idleTexture = new ImageIcon(ImageIO.read(inputFile));
        } catch (Exception e) {
            System.err.println("No se pudo cargar la textura");
            e.printStackTrace();
        }

        this.tileMap = tm;
        this.x = (int)x;
        this.y = (int)y;
        this.width = width;
        this.height = height;

    }

    
    public void setx(int i){ x = i; }
    public void sety(int i){ y = i; }

    public double getx(){ return this.x;}
    public double gety(){ return this.y;}
    

    /**
     * Este metodo calcula las esquinas de nuestra unidad, lo que se puede utilizar
     * para determinar si dos unidades chocaron o no.
     * 
     * @param x
     * @param y
     */
    protected void calculateCorners(double x, double y){

		int leftTile = tileMap.getColTile((int) (x - width / 2));
		int rightTile = tileMap.getColTile((int) (x + width / 2) - 1);

		int topTile = tileMap.getRowTile((int) (y - height / 2));
		int bottomTile = tileMap.getRowTile((int) (y + height / 2) - 1);
		topLeft = tileMap.getTile(topTile, leftTile) == 0;
		topRight = tileMap.getTile(topTile, rightTile) == 0;
		bottomLeft = tileMap.getTile(bottomTile, leftTile) == 0;
        bottomRight = tileMap.getTile(bottomTile, rightTile) == 0;
        /** AQUI PODRE MARCAR UNA CASILLA ESPECIAL PARA EL FINAL DEL NIVEL. */
        levelend = tileMap.getTile(bottomTile, rightTile) == 5;
        
    }   
    
    public abstract void update();

    public abstract void draw(Graphics2D g, GamePanel gp);


}