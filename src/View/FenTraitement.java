package View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Controller.Fonctions;
import Model.DB;

public class FenTraitement extends JFrame {
	
	private Fonctions controller;
	private DB graph;
	private JButton validation;
		
	public FenTraitement(String path) throws IOException{
		graph = new DB(path);
		controller = new Fonctions(graph);
		validation = new JButton("Valider");
		JPanel panChoix = new JPanel();
		JLabel text = new JLabel("Veuillez choisir le traitement shoutaié");
		
		panChoix.setLayout(new BorderLayout());
		String[] lesChoix = {"Labels", "Types", "Proprietes" ,"Representativite", "Diversite"};
		final JComboBox choix = new JComboBox(lesChoix);
		validation.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(choix.getSelectedItem().toString().equals("Labels")){
					FenetreLabels fenLabels = new FenetreLabels(controller);
				}
				if(choix.getSelectedItem().toString().equals("Types")){
					FenetreTypes fenTypes = new FenetreTypes(controller);
				}
				if(choix.getSelectedItem().toString().equals("Proprietes")){
					FenetreProprietes fenTypes = new FenetreProprietes(controller);
				}
				if(choix.getSelectedItem().toString().equals("Representativite")){
					FenetreRepres fenTypes = new FenetreRepres(controller);
				}
				if(choix.getSelectedItem().toString().equals("Diversite")){
					FenetreDiversite fenTypes = new FenetreDiversite(controller);
				}
			}
		});
		this.setSize(400, 200);
		this.setLayout(new BorderLayout());
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Choix de Traitement");
		choix.setPreferredSize(new Dimension(100, 20));
		panChoix.add(text, BorderLayout.NORTH);
		panChoix.add(choix, BorderLayout.SOUTH);
		this.add(panChoix, BorderLayout.NORTH);
		this.add(validation, BorderLayout.SOUTH);
		this.setVisible(true);
	}
}
