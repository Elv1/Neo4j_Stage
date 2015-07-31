package View;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Controller.Fonctions;

public class DiversiteCalc extends JFrame {

	
	public DiversiteCalc(Fonctions fonction, String Source, String Relation, String Destination, String Choix){
		JLabel diversite = new JLabel();
		String tempo = "<html>";
		if(Choix.equals("Source")){
			tempo+=fonction.DiversiteSource(Source, Relation, Destination).replaceAll("\n", "<br>");
		}else if(Choix.equals("Relation")){
			tempo+=fonction.DiversiteRelation(Source, Relation, Destination).replaceAll("\n", "<br>");			
		}else if(Choix.equals("Destination")){
			tempo+=fonction.DiversiteDestination(Source, Relation, Destination).replaceAll("\n", "<br>");
		}else if(Choix.equals("Tout")){
			tempo+=fonction.DiversiteSource(Source, Relation, Destination).replaceAll("\n", "<br>")+"<br>";
			tempo+=fonction.DiversiteRelation(Source, Relation, Destination).replaceAll("\n", "<br>")+"<br>";		
			tempo+=fonction.DiversiteDestination(Source, Relation, Destination).replaceAll("\n", "<br>")+"<br>";
		}
		diversite.setText(tempo);
		this.setTitle("Affichage des calculs de la diversité");
		this.setSize(600, 400);
		this.setLayout(new BorderLayout());
		this.setLocationRelativeTo(null);
		this.add(diversite);
		this.setVisible(true);
	}
}
