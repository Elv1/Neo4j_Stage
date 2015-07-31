package View;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import Controller.Fonctions;

public class FenetreLabels extends JFrame {
	
	public FenetreLabels(Fonctions fonction){
		JLabel types = new JLabel();
		String tempo = "<html>"+fonction.getNodes().replaceAll("\n", "<br>")+"</html>";
		types.setText(tempo);
		JScrollPane scroller = new JScrollPane(types);
		this.setTitle("Affichage des labels");
		this.setSize(400, 200);
		this.setLayout(new BorderLayout());
		this.setLocationRelativeTo(null);
		this.add(scroller);
		this.setVisible(true);
	}
}
