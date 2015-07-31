package View;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

import Controller.Fonctions;

public class RepresSolo extends JFrame {

	
	public RepresSolo(Fonctions fonction, String choix, String[] listeRelations){
		
		JLabel repres = new JLabel();
		String tempo = "<html>";
		if(choix.equals("Tous")){
			for(int i =0; i<listeRelations.length-1; i++){
				tempo += fonction.RepresentativiteF(listeRelations[i]).replaceAll("\n", "<br>");
				tempo+="<br>";
			}
			
		}
		else{
			tempo += fonction.RepresentativiteF(choix).replaceAll("\n", "<br>");
		}
		tempo+="</html>";
		repres.setText(tempo);
		this.setTitle("Affichage de la representativité");
		this.setSize(600, 400);
		this.setLayout(new BorderLayout());
		this.setLocationRelativeTo(null);
		this.add(repres);
		this.setVisible(true);
	}
}
