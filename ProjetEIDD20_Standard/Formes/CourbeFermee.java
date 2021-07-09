package Formes;

/**
 *
 * @author pierrecharbit
 */

public interface CourbeFermee extends Courbe, Perimetrable, Surfacable{
    boolean isConvex();  
    boolean interieurContient(Point p);   
    
}
