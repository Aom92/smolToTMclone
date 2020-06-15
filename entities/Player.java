package src.entities;

import java.awt.*;

import src.engine.Animation;
import src.engine.SoundEffect;
import src.engine.TileMap;
import src.main.*;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * Clase jugador
 * 
 * @author Daniel Flores Crúz
 * @version 1.1 Ver 1.1 - Hugo, se unio con la itnerfaz Entity
 */
public class Player extends Entity {

	public Animation animleft, animright, animup, animdown,movtrail;
	public Animation movingup, movingdown, movingleft, movingright;

	public Animation currAnim;
	public boolean inMovement = false;

	public Player(TileMap tm, int x, int y, int width, int height, String textureName) {

		super(tm, x, y, width, height, textureName);

		super.tileMap = tm;
		super.moveSpeed = 0.9;
		super.maxSpeed = 17;	// La velocidad máxima debe ser un numero impar. menor a 18
		super.stopSpeed = 0;

		

		try {
			movtrail = initTextures("mov_trail", 5, movtrail);
			movingup = initTextures("upmov", 3, movingup);
			movingdown = initTextures("dwmov", 3, movingdown);
			movingleft = initTextures("lfmov", 3, movingleft);
			movingright = initTextures("rgmov", 3, movingright);
			animleft = initTextures("lfchar", 1, animleft);
			animright = initTextures("rgchar", 1, animright);
			animup = initTextures("upchar", 1, animup);
			animdown = initTextures("dwchar", 1, animdown);

		} catch (IOException e) {
			
			e.printStackTrace();
		
		}

		


	}

	///////////////////////////////////////////
	// Vamos hacer los seters para el movimiento del personaje

	public void setLeft(boolean b) {
		left = b;
	}

	public void setRight(boolean b) {
		right = b;
	}

	public void setUp(boolean a) {
		up = a;
	}

	public void setDown(boolean a) {
		down = a;
	}

	public void update() {

		


		// queremos encontrar cual es la siguiente posicion
		if (left) {
			currAnim = movingleft;
			dx -= moveSpeed;
			if (dx < -maxSpeed) {
				dx = -maxSpeed;
			}
			inMovement = true;
		} else if (right) {
			currAnim = movingright;
			dx += moveSpeed;
			if (dx > maxSpeed) {
				dx = maxSpeed;
			}

			inMovement = true;

		}

		if (up) {
			currAnim = movingup;
			dy -= moveSpeed;
			if (dy < -maxSpeed) {
				dy = -maxSpeed;
			}

			inMovement = true;
		} else if (down) {
			currAnim = movingdown;
			dy += moveSpeed;
			if (dy > maxSpeed) {
				dy = maxSpeed;
			}

			inMovement = true;
		}

		// checar coliciones

		// lo primero que queremos es obtener el tile en que esta el jugador osea el
		// cuadro por asi decirlo

		int currCol = tileMap.getColTile((int) x);
		int currRow = tileMap.getRowTile((int) y);

		// coordenadas a las que va
		double tox = x + dx;
		double toy = y + dy;

		double tempx = x;
		double tempy = y;

		calculateCorners(x, toy);
		/** COLISIONES ARRIBA */
		if (dy < 0) {
			if (topLeft || topRight) {
				dy = 0;
				tempy = (currRow) * tileMap.getTileSize() + height / 2;
				this.up = false;
				inMovement = false;
				currAnim = new Animation();
				currAnim = animup;
				if (!inMovement) {
					SoundEffect.COLLIDE.play(-1);
				}

			} else {
				tempy += dy;
			}
		}
		/** COLISIONES ABAJO */
		if (dy > 0) {
			if (bottomLeft || bottomRight) {
				dy = 0;
				tempy = (currRow + 1) * tileMap.getTileSize() - height / 2;
				this.down = false;
				inMovement = false;
				currAnim = new Animation();
				currAnim = animdown;
				if (!inMovement) {
					SoundEffect.COLLIDE.play(-1);
				}

			} else {
				tempy += dy;
			}
		}

		calculateCorners(tox, y);
		/** COLISIONES A LA IZQUIERDA */
		if (dx < 0) {
			if (topLeft || bottomLeft) {
				dx = 0;
				tempx = (currCol ) * tileMap.getTileSize() + width / 2;
				this.left = false;
				inMovement = false;
				currAnim = new Animation();
				currAnim = animleft;
				idleTexture = animleft.getImage();
				if (!inMovement) {
					SoundEffect.COLLIDE.play(-1);
				}

			} else {
				tempx += dx;
			}
		}
		/** COLISIONES A LA DERECHA */
		if (dx > 0) {
			if (topRight || bottomRight) {
				dx = 0;
				tempx = (currCol + 1) * tileMap.getTileSize() - width / 2;
				this.right = false;
				inMovement = false;
				currAnim = new Animation();
				currAnim = animright;

				if (!inMovement) {
					SoundEffect.COLLIDE.play(-1);
				}

			} else {
				tempx += dx;
			}
		}

		x = tempx;
		y = tempy;

		// mover el mapa
		tileMap.setx((int) (GamePanel.WIDTH / 2 - x));
		tileMap.sety((int) (GamePanel.HEIGHT / 2 - y));

	}

	@Override
	public void draw(Graphics2D g, GamePanel gp) {

		int tx = tileMap.getx();
		int ty = tileMap.gety();

		// si el mapa se mueve, queremos saber cuanto se mueve por eso usamos getx y
		// gety para saberlo
		g.setColor(Color.RED);
		/*
		 * g.fillRect( (int) (tx + x - width / 2), (int) (ty + y - height / 2), width,
		 * height );
		 */

		idleTexture.paintIcon(gp, g, (int) (tx + x - height / 2), (int) (ty + y - width / 2));
		if(inMovement){
			 //movtrail.getImage().paintIcon(gp, g,(int) (tx + x - height / 2) - 100 ,(int) (ty + y - width / 2));
			
		}

	}

	private Animation initTextures(String filename, Integer frames, Animation animset) throws IOException {
		animset = new Animation();
		for (Integer i = 0; i < frames; i++){
			animset.addFrame(new ImageIcon(ImageIO.read(new File("./assets/anims/"+filename+i.toString()+".png"))), 25);
		}

		return animset;
		
	}

}