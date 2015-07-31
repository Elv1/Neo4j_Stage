package View;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.neo4j.cypher.internal.compiler.v2_2.commands.expressions.Property;
import org.neo4j.graphdb.Relationship;

import Controller.Fonctions;
import Model.DB;

public class Display {
	private Scanner sc;
	private Fonctions controlleur;
	
	
	
	public Display() throws IOException{
		this.sc= new Scanner(System.in);
		boolean test = false;
		while(!test){	
			System.out.println("Veuillez entrer le chemin complet vers la BDD : ");
			String str = sc.nextLine();
			if(!str.endsWith(".db")){
				System.out.println("Ceci n'est pas le bon fichier! Veuillez recomencer, svp.");
			}else{
				DB graph = new DB(str);
				this.controlleur=new Fonctions(graph);
				System.out.println("Vous travaillez sur la BDD : " + str + ".\n");
				this.traitement(graph);
				test=true;
				
			}
		}
	}
	
	
	public void traitement(DB graph){
		Scanner sc = new Scanner(System.in);
		String str="";
		while(!str.equals("Exit")){
			System.out.println("\nVeuillez choisir le traitement shouaté parmis : labels, types, proprietes, Repres, Diver, DiverExp, Exit\n");
			str = sc.nextLine();
			if(str.equals("labels") || str.equals("types") || str.equals("proprietes") || str.equals("Repres") || str.equals("Diver") || str.equals("DiverExp") || str.equals("Exit")){
				if(str.equals("labels")){
					System.out.println(controlleur.getNodes());
				}
				if(str.equals("types")){
					System.out.println(controlleur.getRels());
				}
				if(str.equals("Repres")){
					System.out.println(this.Representativite());
				}
				if(str.equals("Diver")){
					System.out.println(this.Diversite());
				}
				if(str.equals("proprietes")){
					System.out.println(controlleur.getProps());
				}
				if(str.equals("DiverExp")){
					System.out.println(this.DiverExp());
				}
				if(str.equals("Exit")){
					graph.shutDown();
				}
			}
			else{
				System.out.println("\nCette fonction n'existe pas, veuillez recomencer, svp!\n");
			}
		}
		
	}
	

	
	public String Diversite(){
		String Resultat = "";
		Scanner sc3 = new Scanner(System.in);
		System.out.println("Veuillez entrer le chemin de references avec les Labels et les relations suivants :");
		System.out.println("les labels des noeuds : \n"+ controlleur.getNodes());
		System.out.println("les types des relations : \n"+ controlleur.getRels());
		System.out.println("veuillez saisir le chemin de façon suivante : ");
		System.out.println("Source(prop:val1;prop2:val2;etc...)-Relation(prop:val1;prop2:val2;etc...)-Destination(prop:val1;prop2:val2;etc...) (sans espaces)");
		String path;
		path = sc3.nextLine();
		String[] resultat = path.split("-");

		
		String Source = resultat[0].substring(0, resultat[0].indexOf("("));
		String Relationship = resultat[1].substring(0, resultat[1].indexOf("("));
		String Destination = resultat[2].substring(0, resultat[2].indexOf("("));
		
		
		String[] propSource= path.substring(path.indexOf(Source)+Source.length()+1, path.indexOf(")")).split(";");
		String[] propRel=path.substring(path.indexOf(Relationship)+Relationship.length()+1, path.indexOf(Destination)-2).split(";");
		String[] propDest=path.substring(path.indexOf(Destination)+Destination.length()+1, path.length()-1).split(";");
		

		Scanner sc2 = new Scanner(System.in);
		System.out.println("Veuillez choisir le calcul de diversité voulu : ");
		System.out.println("Diversité relative a la source (1);");
		System.out.println("Diversité relative a la destination (2);");
		System.out.println("Diversité relative a la relation (3);");


		String choix;
		choix = sc.nextLine();
		switch(choix){
		case "1":
			Resultat = controlleur.DiversiteSourceProp(Source, Relationship, Destination, propRel, propDest);
			break;
		case "2":
				Resultat = controlleur.DiversiteDestProp(Source, Relationship, Destination, propSource, propRel);
			break;
		case "3":
			Resultat = controlleur.DiversiteRelationProp(Source, Relationship, Destination, propSource, propDest);
			break;
			default: 
			Resultat = "Mauvais chois, et c'est repartie !!";
			break;
			
		}
		return Resultat;
	}
	public String Representativite(){
		Scanner sc = new Scanner(System.in);
		String relation;
		System.out.println("Veuillez choisir la relation a traiter parmis : " +"\n"+ controlleur.getRels() + "\n avec la propriete voulu sous forme Relation(prop1:val1;prop2:val2;etc...)" +"\n A vous : ");
		relation = sc.nextLine();
		String relationTraite = relation.substring(0, relation.indexOf("("));
		String[] proprietes=relation.substring(relation.indexOf("(")+1, relation.indexOf(")")).split(";");
		System.out.println(relationTraite);
		for(int i =0; i<proprietes.length; i++){
			System.out.println(proprietes[i].toString());
		}
		String Resultat="";
		if(proprietes[0].isEmpty()){
			Resultat = controlleur.RepresentativiteF(relationTraite);
		}else{
			Resultat = controlleur.RepresentativitePropF(relationTraite, proprietes);
		}
	return Resultat;
	}
	
	public String DiverExp(){
		String Resultat = "";
		Scanner sc3 = new Scanner(System.in);
		System.out.println("Veuillez entrer le chemin de references avec les Labels et les relations suivants :");
		System.out.println("les labels des noeuds : \n"+ controlleur.getNodes());
		System.out.println("les types des relations : \n"+ controlleur.getRels());
		System.out.println("veuillez saisir le chemin de façon suivante : ");
		System.out.println("Noeud<-Relation->Noeud<-Relation->Noeud (sans espaces)");
		String path;
		path = sc3.nextLine();
		String[] resultat = path.split("-");
		ArrayList<String> Noeuds= new ArrayList<String>();
		ArrayList<String> Relations= new ArrayList<String>();
		for(int i = 0; i<resultat.length; i++){
			if(!(i%2 ==0)){
				Relations.add(resultat[i]);
			}else{
				Noeuds.add(resultat[i]);
			}
		}
		for(int i = 0; i<Noeuds.size(); i++){
			Resultat+="le Noeud "+i+" : "+ Noeuds.get(i)+"\n";
		}
		for(int j = 0 ; j<Relations.size(); j++){
			Resultat+="la Relation "+ j +" : "+ Relations.get(j)+"\n";
		}
		return Resultat;
	}

}
