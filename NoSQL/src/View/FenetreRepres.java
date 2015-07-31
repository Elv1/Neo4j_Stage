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

public class FenetreRepres extends JFrame {
	private JButton validation;
	
	public FenetreRepres(final Fonctions fonction){
		//JLabel types = new JLabel();
		String tempo = fonction.getRels()+"Tous";
		
		final String[] relations = tempo.split("\n");
		final JComboBox listeRelations = new JComboBox(relations);
		
		validation = new JButton("Valider");
		JPanel panChoix = new JPanel();
		JLabel text = new JLabel("Veuillez choisir le traitement shoutaié");
		
		panChoix.setLayout(new BorderLayout());
		validation.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				RepresSolo rep = new RepresSolo(fonction, listeRelations.getSelectedItem().toString(), relations);
			}
		});
		//types.setText(fonction.RepresentativiteF());
		panChoix.add(text, BorderLayout.NORTH);
		panChoix.add(listeRelations, BorderLayout.CENTER);
		panChoix.add(validation, BorderLayout.SOUTH);
		this.setTitle("Affichage de la Representativite");
		this.setSize(400, 200);
		this.setLayout(new BorderLayout());
		this.setLocationRelativeTo(null);
		this.add(panChoix);
		System.out.println(fonction.getRels());
		this.setVisible(true);
	}

}
