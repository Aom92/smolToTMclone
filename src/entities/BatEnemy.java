package src.entities;

import java.awt.Graphics2D;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import src.engine.TileMap;
import src.main.GamePanel;


public class BatEnemy extends Enemy{

    private ImageIcon texture;

    public BatEnemy(TileMap tm, double x, double y, int width, int height, String textureName) {
        super(tm, x, y, width, height, textureName);
        
        try {
            File inputFile = new File("./assets/"+textureName);
            texture = new ImageIcon(ImageIO.read(inputFile));
        } catch (Exception e) {
            System.err.println("No se pudo cargar la textura");
            e.printStackTrace();
        }

        down = true;
        moveSpeed = 0.9;
        maxSpeed = 1;
    }

    @Override
    public void update() {
       //checar coliciones 

		//lo primero que queremos es obtener el tile en que esta el jugador osea el cuadro por asi decirlo

		int currCol = tileMap.getColTile((int) x);
		int currRow = tileMap.getRowTile((int) y);

		//coordenadas a las que va
		double tox = x + width;
		double toy = y + height;

		double tempx = x;
        double tempy = y;
        
        if (up) {
			
			dy -= moveSpeed;
			if (dy < -maxSpeed) {
				dy = -maxSpeed;
			}

			
		} else if (down) {
			
			dy += moveSpeed;
			if (dy > maxSpeed) {
				dy = maxSpeed;
			}

		}


		calculateCorners(x, toy);
		/** COLISIONES ARRIBA */
		if(dy < 0){
			if(topLeft || topRight){
				dy = 0;
				tempy = (currRow   ) * tileMap.getTileSize() + height / 2;
                this.up = false;
                this.down = true;
			}
			else{
				tempy += dy;
			}
		}
		/** COLISIONES ABAJO */
		if(dy > 0){
			if(bottomLeft || bottomRight){
				dy = 0;
				tempy = (currRow + 1) * tileMap.getTileSize() - height / 2;
                this.down = false;
                this.up = true;
			}
			else{
				tempy += dy;
			}
		}

		calculateCorners(tox, y);
		/** COLISIONES A LA IZQUIERDA */
		if(dx < 0){
			if(topLeft || bottomLeft){
				dx = 0;
				tempx = (currCol  ) * tileMap.getTileSize() + width / 2;
                this.left = false;
                
			}
			else{
				tempx += dx;
			}
		}
		/** COLISIONES A LA DERECHA */
		if(dx > 0){
			if(topRight || bottomRight){
				dx = 0;
				tempx = (currCol + 1 ) * tileMap.getTileSize() - width / 2;
				this.right = false;
			}
			else{
				tempx += dx;
			}
		}

		x = tempx;
		y = tempy;
    }


    @Override
    public void draw(Graphics2D g, GamePanel gp) {
        
        int tx = tileMap.getx();
        int ty = tileMap.gety();
        
		texture.paintIcon(gp, g, (int) ( tx + x - height / 2), (int) (ty + y - width / 2));

    }

    

  

    
}