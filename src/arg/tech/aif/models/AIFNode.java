package arg.tech.aif.models;

import java.util.ArrayList;

import arg.tech.aif.enums.NodeType;


public class AIFNode {
	private String nodeID = "";
	private NodeType type;
	private ArrayList<AIFEdge> incomingEdges;
	private ArrayList<AIFEdge> outgoingEdges;
	
	public AIFNode() {

		incomingEdges = new ArrayList<AIFEdge>();
		outgoingEdges = new ArrayList<AIFEdge>();
	}
	
	public AIFNode(String nodeID, NodeType type) {
		super();
		this.nodeID = nodeID;
		this.type = type;
		incomingEdges = new ArrayList<AIFEdge>();
		outgoingEdges = new ArrayList<AIFEdge>();
	}


	public String getNodeID() {
		return nodeID;
	}


	public void setNodeID(String nodeID) {
		this.nodeID = nodeID;
	}

	
	public NodeType getType() {
		return type;
	}


	public void setType(NodeType type) {
		this.type = type;
	}


	public ArrayList<AIFEdge> getIncomingEdges() {
		return incomingEdges;
	}


	public void setIncomingEdges(ArrayList<AIFEdge> incomingEdges) {
		this.incomingEdges = incomingEdges;
	}
	

	public ArrayList<AIFEdge> getOutgoingEdges() {
		return outgoingEdges;
	}

	
	public void setOutgoingEdges(ArrayList<AIFEdge> outgoingEdges) {
		this.outgoingEdges = outgoingEdges;
	}

	
	public void addIncomingEdge(AIFEdge edge) {
		incomingEdges.add(edge);
	}

	
	public void addOutgoingEdge(AIFEdge edge) {
		outgoingEdges.add(edge);
	}

	public String toJSON() {
		// TODO Auto-generated method stub
		return "";
	}

	
}
