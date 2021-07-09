
package Formes;

import GUI.Ardoise;

/**
 *
 * @author pierrecharbit
 */
public class Cercle  implements  CourbeFermee  {

    Point centre;
    Point bord;

    
    public Cercle(Point centre, Point bord){
        this.centre=centre;
        this.bord=bord;
    }

    
    public double getRayon(){
        return centre.distance(bord);
    }
    
    @Override
    public boolean contient(Point p) {
        return Calcul.isNul(p.distance(centre)-getRayon());
    }

 
    
    public boolean interieurContient(Point p) {
        return p.distance(centre)<getRayon();
    }

    public double getPerimetre() {
        return 2*Math.PI*getRayon();
    }

    public double getSurface() {
        return Math.PI*getRayon()*getRayon();
    }


    @Override
    public boolean isConvex() {
        return true;
    }



        @Override
    public void dessineSur(Ardoise a) {
        a.dessineCercle(centre.x,centre.y,getRayon());
    }
    
    
}
