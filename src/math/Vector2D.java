package src.math;

/**
 * Esta es una clase basada en la clase 2D de @figgy para representar desplazamiento de objetos.
 * @author Hugo
 * @version 1.0; 
 */
public class Vector2D{
    private int x;
    private int y;

    /**
     * Crea un vector asisgando la posición (x,y) al valor proporcionado
     * @param i la posición x del vector unitario (x,y)
     * @param j la posición y del vector unitario (x,y)
     */
    public Vector2D(int i, int j){
        x = j;
        y = j;
    }

    /**
     * 
     * @return  retorna el valor de x
     */
    public int getX(){
        return x;
    }

    /**
     * 
     * @param x asigna este valor al parametor x del vector.
     */
    public void setX(int x){
        this.x = x;
    }

    /**
     * 
     * @return  retorna el valor de y
     */
    public int getY(){
        return x;
    }

    /**
     * 
     * @param x asigna este valor al parametor x del vector.
     */
    public void setY(int y){
        this.y = y;
    }

    /**
     * Suma componente a compomente el vector proporcionado
     * @param vector unitario a sumar. 
     */
    public void add(Vector2D vector){
        x = x + vector.getX();
        y = y + vector.getY();
    }

    /**
     * Multiplica cada componente del vector por el escalar i
     * @param i el escalar a multiplicar el vector. 
     */
    public void multiply(int i ){
        x *= i;
        y *= i;
    }


}