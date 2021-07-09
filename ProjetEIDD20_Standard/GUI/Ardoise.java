package GUI;

import Formes.*;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import javax.swing.JPanel;

/**
 *
 * @author pierrecharbit
 */
public class Ardoise extends JPanel {

    
    /**
     * l'objet graphics qui sert aux méthodes de dessin. 
     */
    Graphics2D crayon;
    
    /**
     * La liste des objets dessinables
     */
    private ArrayList<Dessinable> liste;
    
    /**
     * le Dessinable courant *
     */
    private Dessinable dCourant;
    
    /**
     * le MouseAdapter ecouteur du panneau. Cet ecouteur va changer selon ce qu'on est en train
     * de faire, on va donc créer des classes internes (voir fin du fichier) pour les différents modes de l'appli
     */
    private MouseAdapter mouseAda;
    
    /**
     * les coordonées min max de la partie visible actuellement dans l'ardoise
     */
    private double xMin, xMax, yMin, yMax;

    /**
     * Un pointeur vers l'appli qui contient cette ardoise (pour avoir un lien
     * dans les deux sens)
     */
    private Appli app;

    /**
     * Le constructeur : il prend en arguemnt les coordonées de la fenetre
     * visible
     */
    public Ardoise(double xMin, double xMax, double yMin, double yMax) {
        this.setBackground(Color.white);
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;

        //creation des listes et de la map
        liste = new ArrayList<>();

        // au début un mouseSelecAdapter qui ne fait rien du tout 
        mouseAda = new SelecMouseAdapter();

    }

    /**
     *
     */
    public Ardoise() {
        this(-1, 10, -1, 10);
    }



    /**
     * change le mode de la souris
     *
     * @param mode un String, peut etre mieux de remplacer ca par un int ou un
     * membre d'une Enum (TO DO)
     */
    public void setMode(String mode) {
        this.removeMouseListener(mouseAda);
        this.removeMouseMotionListener(mouseAda);
        setdCourant(null);
        if (mode.equals("Cercle")) {
            mouseAda = new CircleMouseAdapter();
        }
        else if (mode.equals("Select")) {
            mouseAda = new SelecMouseAdapter();
        }
        this.addMouseListener(mouseAda);
        this.addMouseMotionListener(mouseAda);

    }

    /*
    * ATTENTION ON A FAIT LE CHOIX D'AVOIR LES OBJETS DESSINABLES (POINTS, DROITES ETC)
    * DONNÉES AVEC DES COORDONNÉES DOUBLE DANS UN REPERE ORTHONORMÉ MATHEMATIQUE STANDARD
    * 
    * LE PANNEAU FONCTIONNE POUR LE DESSIN EN PIXELS (ENTIERS) ET L'ORIGINE EST EN HAUT A GAUCHE DE L'ARDOISE
    * 
    * ICI ON TROUVE LES FONCTIONS DE CONVERSIONS ENTRE LES DEUX SYSTEMES DE COORDONNEES
     */
    /**
     * @param xRepere l'abscisse double du Point en tant qu'objet mathematique
     * de notre modele
     * @return l'abscisse entière du point dans le repère du panneau pour le
     * dessin. Peut etre negatif ou plus grand que la taille de la fenetre si le
     * point demandé est hors de la fenetre xmin xmax ymin ymax
     *
     */
    public int getXArdoise(double xRepere) {
        int largeurArdoise = this.getWidth();
        return (int) ((xRepere - xMin) * largeurArdoise / (xMax - xMin));
    }

    /**
     * @param yRepere l'ordonnée double du Point en tant qu'objet mathematique
     * de notre modele
     * @return l'ordonnée entière du point dans le repère du panneau pour le
     * dessin. Peut etre negatif ou plus grand que la taille de la fenetre si le
     * point demandé est hors de la fenetre xmin xmax ymin ymax
     *
     */
    public int getYArdoise(double yRepere) {
        int hauteurArdoise = this.getHeight();
        return hauteurArdoise - (int) ((yRepere - yMin) * hauteurArdoise / (yMax - yMin));
    }

    /**
     *
     * @param xArdoise
     * @return fonction inverse de getXArdoise
     */
    public double getXRepere(int xArdoise) {
        int largeurArdoise = this.getWidth();
        return xMin + (0.0 + xArdoise) / largeurArdoise * (xMax - xMin);
    }

    /**
     *
     * @param yArdoise
     * @return fonction inverse de getYArdoise
     */
    public double getYRepere(int yArdoise) {
        int hauteurArdoise = this.getHeight();
        return (hauteurArdoise - yArdoise) * (yMax - yMin) / (0.0 + hauteurArdoise) + yMin;
    }

    /**
     * ajoute un Dessinable a la liste
     *
     * @param d le Dessinable a jouter
     */
    void ajoute(Dessinable d) {
        liste.add(d);
    }

    /*
    * Les trois commandes de base pour dessiner les formes
    * Elles sont utilisées par les méthodes dessineSur de chacune des classes Dessinable
    * Notez que l'on commence par convertir les coordonées double en int pour avoir les coordonées pour le dessin dans le panneau
     */
    public void dessineCercle(double x, double y, double rayon) {
        int xx = getXArdoise(x);
        int yy = getYArdoise(y);
        int rrx = (int) (rayon * this.getWidth() / (xMax - xMin));
        int rry = (int) (rayon * this.getHeight() / (yMax - yMin));
        
        crayon.drawOval(xx - rrx, yy - rry, 2 * rrx, 2 * rry);
        
    }

    public void dessinePoint(double x, double y) {
        int xx = getXArdoise(x);
        int yy = getYArdoise(y);
        crayon.fillOval(xx - 5, yy - 5, 10, 10);
    }

    public void dessineSegment(double x1, double y1, double x2, double y2) {
        int xx1 = getXArdoise(x1);
        int yy1 = getYArdoise(y1);
        int xx2 = getXArdoise(x2);
        int yy2 = getYArdoise(y2);
        crayon.drawLine(xx1, yy1, xx2, yy2);
    }

    /* Tous les getters et setters publics */
    public double getxMin() {
        return xMin;
    }

    public void setxMin(double xMin) {
        this.xMin = xMin;
    }

    public double getxMax() {
        return xMax;
    }

    public void setxMax(double xMax) {
        this.xMax = xMax;
    }

    public double getyMin() {
        return yMin;
    }

    public void setyMin(double yMin) {
        this.yMin = yMin;
    }

    public double getyMax() {
        return yMax;
    }

    public void setyMax(double yMax) {
        this.yMax = yMax;
    }

    public ArrayList<Dessinable> getListeDes() {
        return liste;
    }

    public void setListeDes(ArrayList<Dessinable> listeDes) {
        this.liste = listeDes;
    }

    public Dessinable getdCourant() {
        return dCourant;
    }

    public void setdCourant(Dessinable dCourant) {
        this.dCourant = dCourant;
    }

    public void setApp(Appli app) {
        this.app = app;
    }

    /**
     * La méthode qui contient les instructions de redessinage du panneau Elle
     * est appelée a chaque fois que la fenetre doit s'actualiser On peut la
     * rappeler par la commande repaint()
     *
     * @param g le contexte graphique de dessin
     */
    @Override
    protected void paintComponent(Graphics gg) {
        super.paintComponent(gg);
        crayon = (Graphics2D) gg;

        // on dessine tous les dessinables de la liste
        for (Dessinable d : liste) { 
            d.dessineSur(this);
        }
        //s'il n'est pas vide, on dessine le dessinable courant
        if (dCourant != null) {
            crayon.setColor(Color.red);
            crayon.setStroke(new BasicStroke(4));
            dCourant.dessineSur(this);
        }
    }



    /*
     * Ici on va écrire les différentes classes internes de MouseAdpater     
     */
    /**
     * Le MouseSelecAdapter utilisé quand on veut selectionner une forme Ne fait
     * rien pour l'instant, ce sera a vous de l'implémenter
     */
    class SelecMouseAdapter extends MouseAdapter {
    }

    /**
     * Le MouseAdapter utilisé quand on dessine un cercle
     */
    class CircleMouseAdapter extends MouseAdapter {

        Point centre;

        @Override
        public void mouseClicked(MouseEvent e) {
            //le premier clic, on crée le cercle et on l'affecte a dCourant
            if (centre == null) {
                // notez qu'on convertit les coordonnées entieres e.getX() et e.getY() qui sont relatives au panneau
                // en les coordonnées dans notre systeme de Repere orthornormé pour construire le Point
                centre = new Point(getXRepere(e.getX()), getYRepere(e.getY()));
                dCourant = new Cercle(centre, centre);
            }
            //le second clic, on l'ajoute a la liste et on reinitialise
            else {
                Ardoise.this.liste.add(dCourant);
                dCourant = null;
                centre = null;
            }
            repaint();

        }

        @Override
        public void mouseMoved(MouseEvent e) {
            //si le centre a deja ete choisi, on reaffecte dCour en fonction de l'emplacement de la souris
            if (centre != null) {
                Point p = new Point(getXRepere(e.getX()), getYRepere(e.getY()));
                dCourant = new Cercle(centre, p);
            }
            repaint();
        }

    }

}
