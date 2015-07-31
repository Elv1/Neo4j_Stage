package Model;

import java.io.IOException;
import java.util.ArrayList;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.factory.GraphDatabaseSettings;
import org.neo4j.tooling.GlobalGraphOperations;

public final class DB  {
	//private static final DB instance = new DB(DB_PATH);
	private String DB_PATH;
	private GraphDatabaseService graph;
	private ArrayList<Node> listNodes;
	private ArrayList<RelationshipType> listRels;
	//private ArrayList<Property> listProp;
	

	public DB(String path) throws IOException{
		this.DB_PATH=path;
		this.createDB(DB_PATH);
	}
	
	private void createDB(String path) throws IOException{
		this.graph=(GraphDatabaseService) new GraphDatabaseFactory().newEmbeddedDatabase(path);
		registerShutdownHook( graph );
	}
	
    private static void registerShutdownHook( final GraphDatabaseService graphDb )
    {
        // Registers a shutdown hook for the Neo4j instance so that it
        // shuts down nicely when the VM exits (even if you "Ctrl-C" the
        // running application).
        Runtime.getRuntime().addShutdownHook( new Thread()
        {
            @Override
            public void run()
            {
                graphDb.shutdown();
            }
        } );
    }
    
    public void shutDown(){
    	this.graph.shutdown();
    }

	public Transaction beginTx() {
		return this.graph.beginTx();
	}
	public GraphDatabaseService getGrpah(){
		return this.graph;
	}



        /*public String getProp(){
    	return null;
    }*/
}
