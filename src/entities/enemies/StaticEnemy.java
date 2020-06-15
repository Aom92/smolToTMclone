package src.entities.enemies;

import src.engine.TileMap;
import src.entities.Enemy;
import src.main.GamePanel;

import java.awt.Graphics2D;

/**
 * Clase de Enemigos estaticos, solo estaran ahi sin hacer nada.
 * @author Juárez Pérez Hugo
 * 
 */
public class StaticEnemy extends Enemy{

    public StaticEnemy(TileMap tm, double x, double y, int width, int height, String textureName){
        super(tm, x, y, width, height, textureName);

    }

    @Override
    public void update(){

    }

    @Override
    public void draw(Graphics2D g, GamePanel gp){

        int tx = super.tileMap.getx();
        int ty = super.tileMap.gety();


        super.idleTexture.paintIcon(gp, g, 
            (int) (x + tx - super.width / 2), 
            (int)(y + ty - super.height / 2));
    }
}