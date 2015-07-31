package View;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import Controller.Fonctions;

public class FenetreProprietes extends JFrame {

	public FenetreProprietes(Fonctions fonction){
		JLabel types = new JLabel();
		String tempo = "<html>"+fonction.getProps().replaceAll("\n", "<br>")+"</html>";
		types.setText(tempo);
		JScrollPane scroller = new JScrollPane(types);
		this.setTitle("Affichage des Proprietes");
		this.setSize(400, 200);
		this.setLayout(new BorderLayout());
		this.setLocationRelativeTo(null);
		this.add(scroller);
		this.setVisible(true);
	}
}
