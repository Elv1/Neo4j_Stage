package View;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Controller.Fonctions;

public class FenetreDiversite extends JFrame {
	private JPanel pathPanel;
	private JButton validation;
	
	public FenetreDiversite(final Fonctions fonction){
		validation = new JButton("Valider");
		pathPanel= new JPanel();
		JLabel path= new JLabel("Veuillez saisir le motif(Source-Relation-Destination)");
		JLabel choixDivers = new JLabel("Veuillez choisir le type de Diversité voulu");
		JPanel choixPanel= new JPanel();
		choixPanel.setLayout(new BorderLayout());
		pathPanel.setLayout(new BorderLayout());
		String[] choixTab = {"Source", "Relation", "Destination", "Tout"};
		String[] sourceTab=fonction.getNodes().split("\n");
		String[] relationTab = fonction.getRels().split("\n");
		String[] destTab=fonction.getNodes().split("\n");
		final JComboBox choix = new JComboBox(choixTab);
		final JComboBox Source = new JComboBox(sourceTab);
		final JComboBox Relation = new JComboBox(relationTab);
		final JComboBox Destination = new JComboBox(destTab);
		choix.setSize(100, 20);
		this.setTitle("Choix de path");
		this.setSize(400, 200);
		this.setLayout(new BorderLayout());
		this.setLocationRelativeTo(null);
		pathPanel.add(path, BorderLayout.NORTH);
		pathPanel.add(Source, BorderLayout.WEST);
		pathPanel.add(Relation, BorderLayout.CENTER);
		pathPanel.add(Destination, BorderLayout.EAST);
		choixPanel.add(choixDivers, BorderLayout.NORTH);
		choixPanel.add(choix, BorderLayout.CENTER);
		validation.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				DiversiteCalc fen = new DiversiteCalc(fonction, Source.getSelectedItem().toString(), Relation.getSelectedItem().toString(), Destination.getSelectedItem().toString(), choix.getSelectedItem().toString());
			}
		});
		this.add(pathPanel, BorderLayout.NORTH);
		this.add(choixPanel, BorderLayout.CENTER);
		this.add(validation, BorderLayout.SOUTH);
		this.setVisible(true);
	}
}




/*JPanel choixPanel = new JPanel();
JPanel constructionPath = new JPanel();
JLabel path= new JLabel("Veuillez saisir le path(Source-Relation-Destination)");
JLabel choixDivers = new JLabel("Veuillez choisir le type de Diversité voulu");
choixPanel.add(choixDivers);
choixPanel.add(diversite);
constructionPath.add(path);
constructionPath.add()
*/
