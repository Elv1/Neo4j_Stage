package Controller;

import java.util.ArrayList;

import org.neo4j.cypher.internal.compiler.v2_2.commands.expressions.Property;
import org.neo4j.cypher.internal.compiler.v2_2.functions.Type;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.PropertyContainer;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.tooling.GlobalGraphOperations;

import Model.DB;

public class Fonctions {
	private DB graphDB;
	
	
	public Fonctions(DB graph){
		this.graphDB=graph;
	}
	
	//Methode de recuperation des noms des Noeuds
	public String getNodes(){
		 ArrayList<Node> listNodes = new ArrayList<Node>();
		 String Resultat ="";
		 
	    try (Transaction tx =  graphDB.getGrpah().beginTx())
	    {
	    	
	        for ( Node node : GlobalGraphOperations.at( graphDB.getGrpah() ).getAllNodes() )
	        {
	        	
	       	 	listNodes.add( node );
	        }
	        for(Node e : listNodes){
	        	
	       	 	for(Label l : e.getLabels()){
	       	 	
       	 			if(!Resultat.contains(l.name().toString())){
       	 				Resultat +=l.name().toString()+"\n";
       	 			}
       	 		}
       	 	}
	        tx.success();
	    }
	    return Resultat;
	}
	//methode de recuperation des noms des relations
	public String getRels(){
		String Resultat ="";
		ArrayList<Relationship> listRels = new ArrayList<Relationship>();
		try (Transaction tx = graphDB.getGrpah().beginTx())
		{
			for ( Relationship rel : GlobalGraphOperations.at( graphDB.getGrpah() ).getAllRelationships() )
			{
				listRels.add(  rel );  	 
			}
			for(Relationship r : listRels){
				if(!Resultat.contains(r.getType().name().toString())){
					Resultat+=r.getType().name().toString()+"\n";
				}
			}
			tx.success();
		}
		//System.out.println(Resultat);
		return Resultat;
	}
	
	//method de recuperation des proprietes

	public String getProps(){
		 ArrayList<String> listProperties = new ArrayList<String>();
		 String Resultat ="";
		 
	    try (Transaction tx =  graphDB.getGrpah().beginTx())
	    {
	    	
	        for ( String propertie : GlobalGraphOperations.at( graphDB.getGrpah() ).getAllPropertyKeys() )
	        {
	        	listProperties.add( propertie );
	        }
	        tx.success();
	    }
	    for(String propertie : listProperties){
	    	Resultat += propertie+"\n";
	    }
	    return Resultat;
	}
	
	public String RepresentativiteF(String relation){
		String resultatText="";
		float Resultat ;
		int compteurRel = 0;//Compteur de la relation, choisie par l'utilisateur
		int compteurTotale = 0;//Nombre des relations totale
		ArrayList<Relationship> listRels = new ArrayList<Relationship>();
		   try (Transaction tx = graphDB.getGrpah().beginTx())
		   {
		       for ( Relationship rel : GlobalGraphOperations.at( graphDB.getGrpah() ).getAllRelationships() )
		       {
		    	   compteurTotale++;
		    	   listRels.add(rel);
		    	}
		       for(Relationship r : listRels){
		    	   if(r.getType().name().toString().equals(relation)){
		    		   compteurRel++;
		    	   }
		       }
		       tx.success();
		   }
		   Resultat = compteurRel/(float)compteurTotale;
		   if(Float.isNaN(Resultat)){
			   Resultat=(int)0;
		   }
		   resultatText = "le nombre d'occurence de la relation :" + relation + " est de : " + compteurRel+"\n"+
		   "le nombre totale des relations :" + compteurTotale+"\n"+
		   "la representativite de la relation " + relation+ " est : "+Resultat+"\n";
		   return resultatText;
	}
	
	// calcule de la representativite avec certaines proprietes...
	public String RepresentativitePropF(String relation, String[] proprietes){
		String resultatText="";
		float Resultat ;
		int compteurRel = 0;//Compteur de la relation, choisie par l'utilisateur
		int compteurTotale = 0;//Nombre des relations totale
		ArrayList<Relationship> listRels = new ArrayList<Relationship>();
		   try (Transaction tx = graphDB.getGrpah().beginTx())
		   {
		       for ( Relationship rel : GlobalGraphOperations.at( graphDB.getGrpah() ).getAllRelationships() )
		       {
		    	   compteurTotale++;
		    	   listRels.add(rel);
		       }
		       for(Relationship r : listRels){
		    	   if(r.getType().name().toString().equals(relation)){
		    		   for(String properti : r.getPropertyKeys()){
		    			 for(int j = 0; j<proprietes.length; j++){
		    				 String key =proprietes[j].substring(0, proprietes[j].indexOf(":"));//utilistation de substring(int debut, int fin) pour determiner le premier element de la case du tableau(la key de la propriete)
		    				 String value = proprietes[j].substring(proprietes[j].indexOf(":")+1);//utilisation de dubstring(int debut) pour recuperer le reste de la case du tableau(la value de la propriete)
		    				 String[] tempo = (String[]) r.getProperty(properti);
			    			   for(int i = 0 ; i<tempo.length; i++){
			    				   if(tempo[i].toString().equals(value)){
			    					   compteurRel++;
			    				   }
		    			   } 
		    			 }
		    		   }
		    	   }
		       }
		       tx.success();
		   }
		   Resultat = compteurRel/(float)compteurTotale;
		   if(Float.isNaN(Resultat)){
			   Resultat=(int)0;
		   }
		   resultatText = "le nombre d'occurence de la relation :" + relation + " est de : " + compteurRel+"\n"+
		   "le nombre totale des relations :" + compteurTotale+"\n"+
		   "la representativite de la relation " + relation+ " est : "+Resultat+"\n";
		   return resultatText;
	}
	
	
	public String DiversiteSource(String source, String relationship, String destination){
		String resultatText="";
		float Resultat = 0;
		int nbOcurencePath=0;
		int nbOcurenceSansSource=0;
		ArrayList<Relationship> listRels = new ArrayList<Relationship>();
		   try (Transaction tx = graphDB.getGrpah().beginTx())
		   {
		       for ( Relationship rel : GlobalGraphOperations.at( graphDB.getGrpah() ).getAllRelationships() )
		       {
		    	   listRels.add(rel);
		       }
		       for(Relationship r : listRels){
		    	   if(r.getType().name().equals(relationship)){
			    	   for(Label l :r.getStartNode().getLabels()){
			    		   if(l.name().equals(source)){
			    			   for(Label l2 : r.getEndNode().getLabels()){
			    				   if(l2.name().equals(destination)){
			    					   nbOcurencePath++;
			    				   }
			    			   }
			    		   }
			    	   }
		    	   }
		       }
		       for(Relationship r : listRels){
		    	   if(r.getType().name().equals(relationship)){
			    	   for(Label l2 : r.getEndNode().getLabels()){
			    			if(l2.name().equals(destination)){
			    				nbOcurenceSansSource++;
			    			}
			    		}
			    	}
		       }
	       tx.success();
	   }
		   Resultat = nbOcurencePath/(float)nbOcurenceSansSource;
		   if(Float.isNaN(Resultat)){
			   Resultat=(int)0;
		   }
		   resultatText= "le nombre d'occurences du chemin : "+source+"-"+relationship+"-"+destination +" est : "+nbOcurencePath+"\n"+
		   "le nombre d'occurences du chemin sans source: "+"()"+"-"+relationship+"-"+destination +" est : "+nbOcurenceSansSource+"\n"+
		   "la diversite de la source du chemin : "+source+"-"+relationship+"-"+destination +" est : "+ Resultat+"\n";
	   return resultatText;
	}
	
	public String DiversiteDestination(String source, String relationship, String destination){
		String resultatText="";
		float Resultat = 0;
		int nbOcurencePath=0;
		int nbOcurenceSansDestination=0;
		ArrayList<Relationship> listRels = new ArrayList<Relationship>();
		   try (Transaction tx = graphDB.getGrpah().beginTx())
		   {
		       for ( Relationship rel : GlobalGraphOperations.at( graphDB.getGrpah() ).getAllRelationships() )
		       {
		    	   listRels.add(rel);
		       }
		       for(Relationship r : listRels){
		    	   if(r.getType().name().equals(relationship)){
			    	   for(Label l :r.getStartNode().getLabels()){
			    		   if(l.name().equals(source)){
			    			   for(Label l2 : r.getEndNode().getLabels()){
			    				   if(l2.name().equals(destination)){
			    					   nbOcurencePath++;
			    				   }
			    			   }
			    		   }
			    	   }
		    	   }
		       }
		       for(Relationship r : listRels){
		    	   if(r.getType().name().equals(relationship)){
			    	   for(Label l2 : r.getStartNode().getLabels()){
			    			if(l2.name().equals(destination)){
			    				nbOcurenceSansDestination++;
			    		}
			    	}
		       }
		       }
		   tx.success();
		   }
		   Resultat = nbOcurencePath/(float)nbOcurenceSansDestination;
		   if(Float.isNaN(Resultat)){
			   Resultat=(int)0;
		   }
		   resultatText="le nombre d'occurences du chemin : "+source+"-"+relationship+"-"+destination +" est : "+nbOcurencePath+"\n"+
			"le nombre d'occurences du chemin sans destination: "+source+"-"+relationship+"-"+"()" +" est : "+nbOcurenceSansDestination+"\n"+
			"la diversite de la destination du chemin : "+source+"-"+relationship+"-"+destination +" est : "+ Resultat+"\n";
	   return resultatText;
		}
	
	public String DiversiteRelation(String source, String relationship, String destination){
		String resultatText="";
		float Resultat = 0;
		int nbOcurencePath=0;
		int nbOcurenceSansRelationship=0;
		
		ArrayList<Relationship> listRels = new ArrayList<Relationship>();
		   try (Transaction tx = graphDB.getGrpah().beginTx())
		   {
		       for ( Relationship rel : GlobalGraphOperations.at( graphDB.getGrpah() ).getAllRelationships() )
		       {
		    	   listRels.add(rel);
		       }
		       for(Relationship r : listRels){
		    	   if(r.getType().name().equals(relationship)){
			    	   for(Label l :r.getStartNode().getLabels()){
			    		   if(l.name().equals(source)){
			    			   for(Label l2 : r.getEndNode().getLabels()){
			    				   if(l2.name().equals(destination)){
			    					   nbOcurencePath++;
			    				   }
			    			   }
			    		   }
			    	   }
		    	   }
		       }
		       for(Relationship r2 : listRels){
		    	   for(Label l : r2.getStartNode().getLabels()){
		    		   if(l.name().equals(source)){
		    			   for(Label l2 : r2.getEndNode().getLabels()){
		    				   if(l2.name().equals(destination)){
		    					   nbOcurenceSansRelationship++;
		    				   }
		    			   }
		    		   }
		    	   }
		       }
		tx.success();
		}
		   Resultat = nbOcurencePath/(float)nbOcurenceSansRelationship;
		   if(Float.isNaN(Resultat)){
			   Resultat=(int)0;
		   }
		resultatText="le nombre d'occurences du chemin : "+source+"-"+relationship+"-"+destination +" est : "+nbOcurencePath+"\n"+
				"le nombre d'occurences du chemin sans relation: "+source+"-"+"()"+"-"+destination +" est : "+nbOcurenceSansRelationship+"\n"+
				"la diversite de la relation du chemin : "+source+"-"+relationship+"-"+destination +" est : "+ Resultat+"\n";
	    return resultatText;
	}	
	// la methode de calcule de la diversite de la source avec des proprietes

	public String DiversiteSourceProp(String source, String relationship, String destination, String[] propRel, String[] propDest){
		String resultatText="";
		float Resultat = 0;
		int nbOcurencePath=0;
		int nbOcurenceSansSource=0;
		ArrayList<Relationship> listRels = new ArrayList<Relationship>();
		   try (Transaction tx = graphDB.getGrpah().beginTx())
		   {
		       for ( Relationship rel : GlobalGraphOperations.at( graphDB.getGrpah() ).getAllRelationships() )
		       {
		    	   listRels.add(rel);
		       }
		       for(Relationship r : listRels){//calcul des "modeles" passer en parametre de facon generale (Source-Relation-Destination)
		    	   if(r.getType().name().equals(relationship)){
			    	   for(Label l :r.getStartNode().getLabels()){
			    		   if(l.name().equals(source)){
			    			   for(Label l2 : r.getEndNode().getLabels()){
			    				   if(l2.name().equals(destination)){
			    					   nbOcurencePath++;
			    				   }
			    			   }
			    		   }
			    	   }
		    	   }
		       }
		       for(Relationship r : listRels){//calcul du modele avec les contraintes des proprietes et sans la source
		    	   if(r.getType().name().equals(relationship)){//match de la relation, si ca match alors verification de label de la destination
			    	   for(Label l2 : r.getEndNode().getLabels()){//vu qu'un noeud peut avoir plusiers labels on les parcours tous
			    			if(l2.name().equals(destination)){
			    				boolean checkRel=false;
			    				if(propRel[0].isEmpty() && propDest[0].isEmpty()){//en cas d'abssence des proprietes on compte que les modeles qui correspondent par les labes des noeud et par les types des relations
		    				    	   if(r.getType().name().equals(relationship) && l2.name().equals(destination)){
		    				    		   checkRel=true;	
	    					    		}
			    				}else if(propRel[0].isEmpty()){//si nous avons des proprietes que de coté du noeud Destination mais pas dans la relation
			    					for(int i = 0; i < propDest.length; i ++){//on parcours les proprietes de la Destination
			    						if(r.getEndNode().hasProperty(propDest[i].substring(0, propDest[i].indexOf(":")))){//on test si le noeud contient bien cette propriete si oui on continue l'exploration, sinon on passe a la propriete suivante
				    						if(r.getEndNode().getProperty(propDest[i].substring(0, propDest[i].indexOf(":"))).equals(propDest[i].substring(propDest[i].indexOf(":")+1, propDest[i].length()))){//on verifie la valeur de la propriete de la bd avec celle passé en parametre si ca corresponds alors on increment notre comteur de "modele sans Source"
				    							checkRel = true;
				    						}
				    					}
			    					}
			    				}
			    				else if(propDest[0].isEmpty()){//ca fait la meme chose que la boucle plus haut mais en cas d'absence des proprietes de la destination
			    					for(int i = 0 ; i<propRel.length; i++){
			    						String[] valuesProp = (String[])r.getProperty(propRel[i].substring(0, propRel[i].indexOf(":")));
			    						if(r.hasProperty(propRel[i].substring(0, propRel[i].indexOf(":")))){
				    						for(int k = 0 ; k < valuesProp.length; k++){
				    							if(valuesProp[k].equals(propRel[i].substring(propRel[i].indexOf(":")+1, propRel[i].length()))){
				    								checkRel = true;
				    							}
				    						}
			    						}
			    					}
			    				}else{// en cas ou nous avons les proprietes de la destination et de la relation
				    				for(int i = 0 ; i < propRel.length; i++){//on parcours les proprietes de la relation
				    					for(int j = 0; j < propDest.length; j++){//et les proprietes de la destination 
				    						String[] test = (String[])r.getProperty(propRel[i].substring(0, propRel[i].indexOf(":")));//on recupere les valeurs de la prop de la relation car souvent ce sont des tableaux
				    						if(r.hasProperty(propRel[i].substring(0, propRel[i].indexOf(":")))){// on verifie bien que la propriete existe dans la bd
					    						for(int k = 0; k< test.length; k ++){//avec cette boucle on parcours les valeurs des proprietes de la relation
					    							if(r.getEndNode().hasProperty(propDest[j].substring(0, propDest[j].indexOf(":")))){//ici on verifie la presence de la propriete a la destination dans la bd
						    							if(test[k].equals(propRel[i].substring(propRel[i].indexOf(":")+1, propRel[i].length())) && r.getEndNode().getProperty(propDest[j].substring(0, propDest[j].indexOf(":"))).equals(propDest[j].substring(propDest[j].indexOf(":")+1, propDest[j].length()))){
						    								checkRel=true;// on increment si les valeurs des proprietes de la bd et de celle saisie en parametre sont egalles
							    						}
					    							}
					    						}
				    						}
				    					}
				    				}
			    				}
			    				if(checkRel){
			    					nbOcurenceSansSource++;
			    				}
			    			}
			    		}
			    	}
		       }
	       tx.success();
	   }
		   Resultat = nbOcurencePath/(float)nbOcurenceSansSource;
		   if(Float.isNaN(Resultat)){
			   Resultat=(int)0;
		   }
		   resultatText= "le nombre d'occurences du chemin : "+source+"-"+relationship+"-"+destination +" est : "+nbOcurencePath+"\n"+
		   "le nombre d'occurences du chemin sans source: "+"()"+"-"+relationship+"-"+destination +" est : "+nbOcurenceSansSource+"\n"+
		   "la diversite de la source du chemin : "+source+"-"+relationship+"-"+destination +" est : "+ Resultat+"\n";
	   return resultatText;
		
	}
	
	// la methode de calcule de la diversite de la destination avec des proprietes
	public String DiversiteDestProp(String source, String relationship, String destination,String[] propSource, String[] propRel){
		String resultatText="";
		float Resultat = 0;
		int nbOcurencePath=0;
		int nbOcurenceSansDestination=0;
		ArrayList<Relationship> listRels = new ArrayList<Relationship>();
		   try (Transaction tx = graphDB.getGrpah().beginTx())
		   {
		       for ( Relationship rel : GlobalGraphOperations.at( graphDB.getGrpah() ).getAllRelationships() )//recupperation des relation de la bd dans une liste
		       {
		    	   listRels.add(rel);
		       }
		       for(Relationship r : listRels){//calcul des "patterns" passer en parametre de facon generale (Source-Relation-Destination)
		    	   if(r.getType().name().equals(relationship)){
			    	   for(Label l :r.getStartNode().getLabels()){
			    		   if(l.name().equals(source)){
			    			   for(Label l2 : r.getEndNode().getLabels()){
			    				   if(l2.name().equals(destination)){
			    					   nbOcurencePath++;
			    				   }
			    			   }
			    		   }
			    	   }
		    	   }
		       }
		       for(Relationship r : listRels){//calcul du modele avec les contraintes des proprietes et sans la destination
		    	   if(r.getType().name().equals(relationship)){//match de la relation, si ca match alors verification de label de la source
			    	   for(Label l2 : r.getStartNode().getLabels()){//vu qu'un noeud peut avoir plusiers labels on les parcours tous
			    			if(l2.name().equals(source)){
			    				boolean checkRel=false;
			    				if(propSource[0].isEmpty() && propRel[0].isEmpty()){ //en cas d'abssence des proprietes on compte que les modeles qui correspondent par les labes des noeud et par les types des relations
		    				    	   if(l2.name().equals(source) && r.getType().name().equals(relationship)){
		    				    		   checkRel=true;	
	    					    		}
			    				}
			    				else if(propRel[0].isEmpty()){//si nous avons des proprietes que de coté du noeud Source mais pas dans la relation
			    					for(int i = 0; i < propSource.length; i ++){//on parcours les proprietes de la Source
			    						if(r.getStartNode().hasProperty(propSource[i].substring(0, propSource[i].indexOf(":")))){//on test si le noeud contient bien cette propriete si oui on continue l'exploration, sinon on passe a la propriete suivante
				    						if(r.getStartNode().getProperty(propSource[i].substring(0, propSource[i].indexOf(":"))).equals(propSource[i].substring(propSource[i].indexOf(":")+1, propSource[i].length()))){//on verifie la valeur de la propriete de la bd avec celle passé en parametre si ca corresponds alors on increment notre compteur de "modele sans Destination"
				    							checkRel = true;
				    						}
			    						}
			    					}
			    				}
			    				else if(propSource[0].isEmpty()){//ca fait la meme chose que la boucle plus haut mais en cas d'absence des proprietes de la source
			    					for(int i = 0 ; i<propRel.length; i++){
			    						String[] valuesProp = (String[])r.getProperty(propRel[i].substring(0, propRel[i].indexOf(":")));
			    						if(r.hasProperty(propRel[i].substring(0, propRel[i].indexOf(":")))){
				    						for(int k = 0 ; k < valuesProp.length; k++){
				    							if(valuesProp[k].equals(propRel[i].substring(propRel[i].indexOf(":")+1, propRel[i].length()))){
				    								checkRel = true;
				    							}
				    						}
			    						}
			    					}
			    				}else{// en cas ou nous avons les proprietes de la source et de la relation
				    				for(int i = 0 ; i < propRel.length; i++){//on parcours les proprietes de la relation
				    					for(int j = 0; j < propSource.length; j++){//et les proprietes de la Source 
				    						String[] test = (String[])r.getProperty(propRel[i].substring(0, propRel[i].indexOf(":")));//on recupere les valeurs de la prop de la relation car souvent ce sont des tableaux
				    						if(r.hasProperty(propRel[i].substring(0, propRel[i].indexOf(":")))){// on verifie bien que la propriete existe dans la bd
					    						for(int k = 0; k< test.length; k ++){//avec cette boucle on parcours les valeurs des proprietes de la relation
					    							if(r.getEndNode().hasProperty(propSource[j].substring(0, propSource[j].indexOf(":")))){//ici on verifie la presence de la propriete a la source dans la bd
					    								if(test[k].equals(propRel[i].substring(propRel[i].indexOf(":")+1, propRel[i].length())) && r.getStartNode().getProperty(propSource[j].substring(0, propSource[j].indexOf(":"))).equals(propSource[j].substring(propSource[j].indexOf(":")+1, propSource[j].length()))){
							    							checkRel=true;// on increment si les valeurs des proprietes de la bd et de celle saisie en parametre sont egalles 
							    						}
					    							}
					    						}
				    						}
				    					}
				    				}
			    				}
			    				if(checkRel){
	    							nbOcurenceSansDestination++;
			    				}
			    			}
			    		}
			    	}
		       }
	       tx.success();
		   }
		   Resultat = nbOcurencePath/(float)nbOcurenceSansDestination;
		   if(Float.isNaN(Resultat)){
			   Resultat=(int)0;
		   }
		   resultatText="le nombre d'occurences du chemin : "+source+"-"+relationship+"-"+destination +" est : "+nbOcurencePath+"\n"+
					"le nombre d'occurences du chemin sans destination: "+source+"-"+relationship+"-"+"()" +" est : "+nbOcurenceSansDestination+"\n"+
					"la diversite de la destination du chemin : "+source+"-"+relationship+"-"+destination +" est : "+ Resultat+"\n";
			   return resultatText;
	}
	
	public String DiversiteRelationProp(String source, String relationship, String destination, String[] propSource, String[] propDest){
		String resultatText="";
		float Resultat = 0;
		int nbOcurencePath=0;
		int nbOcurenceSansRelationship=0;
		
		ArrayList<Relationship> listRels = new ArrayList<Relationship>();
		   try (Transaction tx = graphDB.getGrpah().beginTx())
		   {
		       for ( Relationship rel : GlobalGraphOperations.at( graphDB.getGrpah() ).getAllRelationships() )//recuperation des realtion de la bd
		       {
		    	   listRels.add(rel);
		       }
		       for(Relationship r : listRels){//compteur de model(Source-Relation-Destination)
		    	   if(r.getType().name().equals(relationship)){
			    	   for(Label l :r.getStartNode().getLabels()){
			    		   if(l.name().equals(source)){
			    			   for(Label l2 : r.getEndNode().getLabels()){
			    				   if(l2.name().equals(destination)){
			    					   nbOcurencePath++;
			    				   }
			    			   }
			    		   }
			    	   }
		    	   }
		       }
		       for(Relationship r2 : listRels){//boucle de parcours des relation pour pouvoir acceder aux noeuds sources et destionations 
		    	   for(Label l : r2.getStartNode().getLabels()){
		    		   if(l.name().equals(source)){
		    			   for(Label l2 : r2.getEndNode().getLabels()){
		    				   if(l2.name().equals(destination)){
		    					   boolean check = false;
		    					   if(propDest[0].isEmpty() && propSource[0].isEmpty()){//en cas d'absence des proprietes on compte les modele qui correspondent avec les labels des noeuds de source et de destionation
		    				    	   if(l.name().equals(source) && l2.name().equals(destination)){
		    				    		   check=true;//si les valeurs passer en parametre et les valeurs stocké dans la bd correspondent alors on incremente notre compteur du modele sans relation	
	    					    		}
		    					   }else if(propSource[0].isEmpty()){//cas ou les proprietes de la source n'ont pas ete saisie 
		    						   for(int i = 0; i<propDest.length; i++){
		    							   if(r2.getEndNode().hasProperty(propDest[i].substring(0, propDest[i].indexOf(":")))){//si la source dans la bd contien cette propriete alors on continue l'exploration
			    							   if(r2.getEndNode().getProperty(propDest[i].substring(0, propDest[i].indexOf(":"))).equals(propDest[i].substring(propDest[i].indexOf(":")+1, propDest[i].length()))){
			    								   check = true;//si les valeurs correspondent alors on icrement le compteur du modele sans relation
			    							   }
		    							   }
		    						   }
		    					   }else if(propDest[0].isEmpty()){//la meme chose que la boucle plus haut mais dans le cas d'absence des proprietes au noeud de la destination
		    						   for(int i = 0; i<propSource.length; i++){
		    							   if(r2.getStartNode().hasProperty(propSource[i].substring(0, propSource[i].indexOf(":")))){
			    							   if(r2.getStartNode().getProperty(propSource[i].substring(0, propSource[i].indexOf(":"))).equals(propSource[i].substring(propSource[i].indexOf(":")+1, propSource[i].length()))){
			    								   check = true;
			    							   }
		    							   }
		    						   }
		    					   }
		    					   else{//la boucle qui utilisé dans le cas ou toutes les proprietes ont ete saisie
			    					   for(int i =0; i<propSource.length; i++){
			    						   for(int j =0; j<propDest.length; j++){
			    							   if(r2.getStartNode().hasProperty(propSource[i].substring(0, propSource[i].indexOf(":"))) && r2.getEndNode().hasProperty(propDest[j].substring(0, propDest[j].indexOf(":")))){
				    							   if(r2.getStartNode().getProperty(propSource[i].substring(0, propSource[i].indexOf(":"))).equals(propSource[i].substring(propSource[i].indexOf(":")+1, propSource[i].length())) && r2.getEndNode().getProperty(propDest[j].substring(0, propDest[j].indexOf(":"))).equals(propDest[j].substring(propDest[j].indexOf(":")+1, propDest[j].length()))){
				    								   check = true;// apres les 2 test de presence des proprietes dans la bd et de la corespondence des valeurs saisie en parametres et ceux de la bd, en cas de reussite des 2 test on increment le compteru du modele sans relation
				    							   }
			    							   }
			    						   }
			    					   }
		    					   }
		    					   if(check){
		    						   nbOcurenceSansRelationship++;
		    					   }
		    				   }
		    			   }
		    		   }
		    	   }
		       }
		tx.success();
		}
		   Resultat = nbOcurencePath/(float)nbOcurenceSansRelationship;
		   if(Float.isNaN(Resultat)){
			   Resultat=(int)0;
		   }
		resultatText="le nombre d'occurences du chemin : "+source+"-"+relationship+"-"+destination +" est : "+nbOcurencePath+"\n"+
				"le nombre d'occurences du chemin sans relation: "+source+"-"+"()"+"-"+destination +" est : "+nbOcurenceSansRelationship+"\n"+
				"la diversite de la relation du chemin : "+source+"-"+relationship+"-"+destination +" est : "+ Resultat+"\n";
	    return resultatText;
	}
	
	
	/*public String DiversitéEtendue(){
		String Resultat="";
		ArrayList<Node> listNodes = new ArrayList<Node>();
		try (Transaction tx =  graphDB.getGrpah().beginTx())
	    {
	    	
	        for ( Node node : GlobalGraphOperations.at( graphDB.getGrpah() ).getAllNodes() )
	        {
	        	
	       	 	listNodes.add( node );
	        }
	        for(Node e : listNodes){
	        	
	       	 	for(Label l : e.getLabels()){
	       	 	
       	 			if(!Resultat.contains(l.name().toString())){
       	 				Resultat +=l.name().toString()+"\n";
       	 			}
       	 		}
       	 	}
	        tx.success();
		return Resultat;
	}
}
	public void noeudDepart(ArrayList<String> Noeuds){
		
	}*/
}
