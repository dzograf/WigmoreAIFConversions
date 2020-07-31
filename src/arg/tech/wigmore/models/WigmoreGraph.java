package arg.tech.wigmore.models;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import arg.tech.aif.models.AIFEdge;
import arg.tech.aif.models.AIFNode;

public class WigmoreGraph {
	private ArrayList<WigNode> nodes;
	private ArrayList<WigEdge> edges;
	
	public WigmoreGraph() {	
		nodes = new ArrayList<WigNode>();
		edges = new ArrayList<WigEdge>();
	}

	public WigmoreGraph(ArrayList<WigNode> nodes, ArrayList<WigEdge> edges) {
		super();
		this.nodes = nodes;
		this.edges = edges;
	}

	public ArrayList<WigNode> getNodes() {
		return nodes;
	}

	public void setNodes(ArrayList<WigNode> nodes) {
		this.nodes = nodes;
	}

	public ArrayList<WigEdge> getEdges() {
		return edges;
	}

	public void setEdges(ArrayList<WigEdge> edges) {
		this.edges = edges;
	}
	
	public void addNode(WigNode node) {
		nodes.add(node);
	}
	
	public void addEdge(WigEdge edge) {
		edges.add(edge);
	}
	
	public WigNode findNode(String id) {

		for (WigNode node : nodes) {
			if (node.getID().compareTo(id) == 0) {
				return node;
			}
		}

		return null;
	}
	
	
	public String toJSON() {
		StringBuilder str = new StringBuilder();
		str.append("{\"nodes\":\n[");
		Iterator<WigNode> nodeIterator = nodes.iterator();
		
		while(nodeIterator.hasNext()) {
			WigNode node = nodeIterator.next();
			
			str.append("\t"+node.toJSON());
			if(nodeIterator.hasNext()) {
				str.append(",\n");
			}
		}
		
		str.append("],\n");
		str.append("\"edges\":\n[");
		Iterator<WigEdge> edgeIterator = edges.iterator();
		
		while(edgeIterator.hasNext()) {
			WigEdge edge = edgeIterator.next();
			
			str.append("\t"+edge.toJSON());
			if(edgeIterator.hasNext()) {
				str.append(",\n");
			}
		}
		str.append("]\n");
		
		str.append("}");
		return str.toString();
	}
	
	public String toString() {
		StringBuilder str = new StringBuilder();
		
		str.append("EVIDENCE NODES");
		for (WigNode node : nodes) {
			str.append(node.toString());
		}
		
		str.append("\nEDGES");
		for (WigEdge edge : edges) {
			str.append(edge.toString());
		}
		return str.toString();
	}

	
	
	public void saveToFile(String filename) {
		 BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(filename));
			
			
			writer.write(this.toString());
			    
			writer.close();
			    
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
