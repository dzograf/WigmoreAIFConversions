package arg.tech.aif.models;


public class AIFEdge {
	
	private String ID;
	private AIFNode fromNode;
	private AIFNode toNode;
	
	public AIFEdge() {}
	
	public AIFEdge(String ID, AIFNode fromNode, AIFNode toNode) {
		this.ID = ID;
		this.fromNode = fromNode;
		this.toNode = toNode;
	}

	public AIFNode getFromNode() {
		return fromNode;
	}

	public void setFromNode(AIFNode fromNode) {
		this.fromNode = fromNode;
	}

	public AIFNode getToNode() {
		return toNode;
	}

	public void setToNode(AIFNode toNode) {
		this.toNode = toNode;
	}

	
	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String toJSON() {
		StringBuilder str = new StringBuilder();
		
		str.append("{\"edgeID\":\"" + ID + "\",");
		str.append("\"fromID\":\""+ fromNode.getNodeID() + "\",");
		str.append("\"toID\":\""+ toNode.getNodeID() + "\"}");
		
		return str.toString();
	}
}
