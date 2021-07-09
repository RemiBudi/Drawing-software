package GUI;


import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.*;
import javax.swing.border.EtchedBorder;

/**
 *
 * @author pierrecharbit
 */
public class Appli extends JFrame {
    /**
     * le champ de type Ardoise qui est le panneau de dessin de l'appli 
     */
    private Ardoise ardoise;
    

    
    /**
     * Crée une nouvelle Appli
     */
    public Appli() {
        ardoise = new Ardoise(-1,10,-1,10);
        ardoise.setPreferredSize(new Dimension(600,600));
        this.add(ardoise,"Center");

        JPanel jPanelN=new JPanel();
        JPanel jPanelBout=new JPanel();
        jPanelN.setPreferredSize(new Dimension(700,60));
        jPanelBout.setLayout(new GridLayout(1,0));
        JToggleButton boutonCercle=new JToggleButton();
        JToggleButton boutonSelec =new JToggleButton();

        //ajout des ecouteurs
        //notez que j'utilise une lambda expression au lieu d'une classe anonyme 
        //ET que deporte le code dans des methodes privees ecrites plus bas
        boutonCercle.addActionListener(evt->boutonCercleActionPerformed(evt));
        boutonSelec.addActionListener(evt->boutonSelecActionPerformed(evt));
        
        //les jolies icones
        boutonCercle.setIcon(new ImageIcon("icones/modeCircle.png"));
        boutonSelec.setIcon(new ImageIcon("icones/mode_select.png"));

        //Le groupe de boutons 
        //objet qui permet de n'avoir qu'un seul ToggleButton activé a la fois.
        //ce n'est pas un composant graphique, il ne s'affiche pas.
        ButtonGroup buttonGroupActions = new javax.swing.ButtonGroup();
        buttonGroupActions.add(boutonSelec);
        buttonGroupActions.add(boutonCercle);
        
        jPanelBout.add(boutonCercle);
        jPanelBout.add(boutonSelec);
        jPanelN.add(jPanelBout);
        this.add(jPanelN,"North");
        
        JPanel jPanelE=new JPanel();
        jPanelE.setPreferredSize(new Dimension(80,500));
        this.add(jPanelE,"East");
        this.pack();
        
    }

    /*la fonction appelée par l'écouteur du boutonCercle*/
    private void boutonCercleActionPerformed(java.awt.event.ActionEvent evt) {                                             
        ardoise.setMode("Cercle");
    }                                            

    /*la fonction appelée par l'écouteur du boutonSelec*/
    private void boutonSelecActionPerformed(java.awt.event.ActionEvent evt) {                                            
        ardoise.setMode("Select");
    }                                           



}
