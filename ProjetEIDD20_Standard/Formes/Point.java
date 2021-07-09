
package Formes;

import GUI.Ardoise;

/**
 *
 * @author pierrecharbit
 */
public class Point implements Dessinable{
    String label;
    public double x;
    public double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point(String label, double x, double y) {
        this.label = label;
        this.x = x;
        this.y = y;
    }  

    public double distance(Point p){
        return Calcul.norm2(x-p.x,y-p.y);
    }
    
    @Override
    public void dessineSur(Ardoise a) {
        a.dessinePoint(this.x,this.y);
    }

    @Override
    public String toString() {
        return "Point : "+this.x+","+this.y;
    }
    

    

}
