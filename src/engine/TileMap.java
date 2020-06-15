package src.engine;

import java.io.*;


import javax.imageio.ImageIO;
import javax.swing.ImageIcon;



import src.main.GamePanel;
import java.awt.*;

/**
 * Clase TileMap que permite cargar niveles al juego utilizando un archivo de texto.
 * @author Flores Crúz Daniel.
 * @version 1.0
 */
public class TileMap{

	private int x;
	private int y;

	private int tileSize;
	private int [][] map;
	private int mapWidth;
	private int mapHeight;
	private ImageIcon tileicon;
	private ImageIcon wallicon;
	private ImageIcon spikeicon;
	private ImageIcon coinicon;

	/**
	 * Constructor de TileMap 
	 * El tamaño por casilla se puede entender como un factor de escala que reconoce los valores
	 * dentro del archivo de texto y aplica una transformación para saber que tan grandes deben de verse
	 * dentro de nuestro contexto gráfico.
	 * @param s	Ruta del archivo con extension en txt.
	 * @param tileSize El tamaño por casilla.
	 */
	public TileMap(String s, int tileSize){

		this.tileSize = tileSize;

		try{

			BufferedReader br = new BufferedReader(new FileReader(s));

			mapWidth = Integer.parseInt(br.readLine());
			mapHeight = Integer.parseInt(br.readLine());
			map = new int[mapHeight][mapWidth];

			String delimeters = " ";
			for(int row = 0; row < mapHeight; row++){
				String line = br.readLine();
				String[] tokens = line.split(delimeters);
				for(int col = 0; col < mapWidth; col++){
					map[row][col] = Integer.parseInt(tokens[col]);

				}
			}
			br.close();

			File inputfile = new File("./assets/tileicon.png");
			tileicon = new ImageIcon(ImageIO.read(inputfile));
			inputfile = new File("./assets/wallicon.png");
			wallicon = new ImageIcon(ImageIO.read(inputfile));
			inputfile = new File("./assets/spikeicon.png");
			spikeicon = new ImageIcon(ImageIO.read(inputfile));
			inputfile = new File("./assets/coin.png");
			coinicon = new ImageIcon(ImageIO.read(inputfile));

		}
		catch(Exception e){

		}
	}

	public int getx(){ return x; }
	public int gety(){ return y; }

	/**
	 * Regresa el valor de la casilla en la Columna x.
	 * @param x
	 * @return
	 */
	public int getColTile(int x){
		return x / tileSize;
	}

	/**
	 * Regresa el valor de la casilla en la Fila x.
	 * @param y
	 * @return
	 */
	public int getRowTile(int y){
		return y / tileSize;
	}

	/**
	 * Regresa la Casilla con coordenadas fila y columna
	 * @param row
	 * @param col
	 * @return
	 */
	public int getTile(int row, int col){
		return map[row][col];
	}

	/**
	 * Regresa el tamaño de la casilla.
	 * @return
	 */
	public int getTileSize(){
		return tileSize;
	}

	public void setx(int i){ x = i; }
	public void sety(int i){ y = i; }

	public int update(int x, int y){
		for(int row =0; row < mapHeight; row++){
			for(int col = 0; col < mapWidth; col++){
				
				int rc = map[row][col];

				if (rc == 3 ){
					
					if (col == getColTile(x) && row == getRowTile(y) ){
						map[row][col] = 1;
						return 1;
					}
						
				}

			}

		}

		return 0;

	}

	/**
	 * Genera el TileMap al contexto grafico de destin, dentro un GamePanel
	 * 
	 * @param g El "Pincel"  donde se toma la imagen.
	 * @param gp El contexto gráfico donde se pintara la imagen. 
	 */
	public void draw(Graphics2D g,GamePanel gp ){

		//vamos a dibujar el mapa
		for(int row =0; row < mapHeight; row++){
			for(int col = 0; col < mapWidth; col++){

				int rc = map[row][col];

				if(rc == 0){
					//g.setColor(Color.BLACK);
					tileicon.paintIcon(gp, g, x + col * tileSize, y + row * tileSize);
				}
				if(rc == 1){
					//g.setColor(Color.WHITE);
					wallicon.paintIcon(gp, g, x + col * tileSize, y + row * tileSize);
				}
				if(rc == 2){
					spikeicon.paintIcon(gp, g, x + col* tileSize, y + row * tileSize);
				}
				if(rc == 3){
					coinicon.paintIcon(gp, g, x + col * tileSize, y + row * tileSize);
					
				}

				//g.fillRect(x + col * tileSize, y + row * tileSize, tileSize, tileSize);
				
			}

		}
	}
}