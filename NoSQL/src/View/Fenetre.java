package View;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

//CTRL + SHIFT + O pour générer les imports nécessaires

public class Fenetre extends JFrame {

	private JButton choisir, valider;
	private JFileChooser fc = new JFileChooser(".") ;
	private JLabel chemin;
	
	public Fenetre() throws IOException{
		chemin = new JLabel();
		JLabel text = new JLabel("Entrez le chemin vers la BD, svp");
		JLabel text2 = new JLabel("ou choisisze l'emplacement de la BD");
		JPanel panelExplorer = new JPanel();
		JPanel panelJTF = new JPanel();
		JPanel validation = new JPanel();
		JPanel chois = new JPanel();
		final JTextField jtf = new JTextField();
		this.setLayout(new BorderLayout());
		panelExplorer.setLayout(new BorderLayout());
		choisir = new JButton("Choisir");
		valider = new JButton("Valider");
		choisir.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try {
					chemin.setText(explorer());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		valider.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try {
					if(!chemin.getText().isEmpty()){
						FenTraitement fenTrait = new FenTraitement(chemin.getText());
					}else{
						FenTraitement fenTrait = new FenTraitement(jtf.getText());
					}
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			});
		this.setSize(400, 200);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("CalculetteODRG");
		chois.add(choisir);
		panelJTF.setLayout(new BorderLayout());
		panelJTF.add(text, BorderLayout.NORTH);
		panelJTF.add(jtf, BorderLayout.CENTER);
		panelExplorer.add(text2, BorderLayout.NORTH);
		panelExplorer.add(chois, BorderLayout.CENTER);
		panelExplorer.add(chemin, BorderLayout.SOUTH);
		validation.add(valider);
		this.add(panelExplorer, BorderLayout.CENTER);
		this.add(panelJTF, BorderLayout.NORTH);
		this.add(validation, BorderLayout.SOUTH);
		this.setVisible(true);
	}
	
	public String explorer() throws IOException{
		String Resultat ="";
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if(fc.showOpenDialog(null)==JFileChooser.APPROVE_OPTION){
			Resultat = fc.getSelectedFile().getPath().toString();
			System.out.println(Resultat);
		}
		return Resultat;
	}

}