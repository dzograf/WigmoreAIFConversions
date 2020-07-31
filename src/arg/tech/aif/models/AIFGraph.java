package arg.tech.aif.models;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import arg.tech.aif.enums.NodeType;



public class AIFGraph {
	
	private ArrayList<AIFNode> nodes;
	private ArrayList<AIFEdge> edges;
	
	public AIFGraph() {
		nodes = new ArrayList<AIFNode>();
		edges = new ArrayList<AIFEdge>();
	}
	
	public AIFGraph(ArrayList<AIFNode> nodes, ArrayList<AIFEdge> edges) {
		super();
		this.nodes = nodes;
		this.edges = edges;
	}

	public ArrayList<AIFNode> getNodes() {
		return nodes;
	}
	public void setNodes(ArrayList<AIFNode> nodes) {
		this.nodes = nodes;
	}
	public ArrayList<AIFEdge> getEdges() {
		return edges;
	}
	public void setEdges(ArrayList<AIFEdge> edges) {
		this.edges = edges;
	}
	
	public AIFNode findNode(String id) {

		for (AIFNode node : nodes) {
			if (node.getNodeID().compareTo(id) == 0) {
				return node;
			}
		}

		return null;
	}
	
	public int findPosNode(String id) {

		for (int i=0; i<nodes.size(); i++) {
			if (nodes.get(i).getNodeID().compareTo(id) == 0) {
				return i;
			}
		}

		return -1;
	}
	
	public AIFNode findNode(int pos) {
		return nodes.get(pos);
	}
	
	
	public void updateNode(int pos, AIFNode node) {
		nodes.set(pos, node);
	}
	
	public void addNode(AIFNode node) {
		if(findNode(node.getNodeID()) == null) 
			nodes.add(node);
	}
	
	public void addEdge(AIFEdge edge) {
		edges.add(edge);
	}
	

	public String toJSON() {
		StringBuilder str = new StringBuilder();
		str.append("{\"nodes\":\n[");
		Iterator<AIFNode> nodeIterator = nodes.iterator();
		
		while(nodeIterator.hasNext()) {
			AIFNode node = nodeIterator.next();
			
			str.append("\t"+node.toJSON());
			if(nodeIterator.hasNext()) {
				str.append(",\n");
			}
		}
		
		str.append("],\n");
		str.append("\"edges\":\n[");
		Iterator<AIFEdge> edgeIterator = edges.iterator();
		
		while(edgeIterator.hasNext()) {
			AIFEdge edge = edgeIterator.next();
			
			str.append("\t"+edge.toJSON());
			if(edgeIterator.hasNext()) {
				str.append(",\n");
			}
		}
		str.append("],\n");
		
		str.append("\"locutions\":\n[");
		Iterator<AIFNode> locIterator = nodes.iterator();
		
		while(locIterator.hasNext()) {
			AIFNode node = locIterator.next();
			if(node.getType() == NodeType.L) {
				str.append("\t"+((LNode)node).locutionJSON());
				str.append(",\n");
			}
		}
		str.deleteCharAt(str.length()-2);
		str.append("]}");
		return str.toString();
	}
	
	public void saveToFile(String filename) {
		 BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(filename));
			
			
			writer.write(toJSON());
			    
			writer.close();
			    
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
