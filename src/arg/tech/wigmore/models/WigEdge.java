package arg.tech.wigmore.models;

import arg.tech.wigmore.enums.EdgeType;
import arg.tech.wigmore.enums.SchemeForce;
import arg.tech.wigmore.enums.Stance;

public class WigEdge {
	private String ID;
	private EdgeType type;
	private SchemeForce force;
	private Stance stance;

	private WigNode fromNode;
	private WigNode toNode;
	
	public WigEdge() {}

	public WigEdge(String iD, EdgeType type, SchemeForce force, Stance stance, WigNode fromNode, WigNode toNode) {
		super();
		this.ID = iD;
		this.type = type;
		this.force = force;
		this.stance = stance;
		this.fromNode = fromNode;
		this.toNode = toNode;
	}
	
	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public EdgeType getType() {
		return type;
	}

	public void setType(EdgeType type) {
		this.type = type;
	}

	public SchemeForce getForce() {
		return force;
	}

	public void setForce(SchemeForce force) {
		this.force = force;
	}

	public Stance getStance() {
		return stance;
	}

	public void setStance(Stance stance) {
		this.stance = stance;
	}

	public WigNode getFromNode() {
		return fromNode;
	}

	public void setFromNode(WigNode fromNode) {
		this.fromNode = fromNode;
	}

	public WigNode getToNode() {
		return toNode;
	}

	public void setToNode(WigNode toNode) {
		this.toNode = toNode;
	}

	
	public String toJSON() {
		StringBuilder str = new StringBuilder();
		
		str.append("{\"edgeID\":\"" + ID + "\", "
				+ "\"fromID\":" + fromNode.getID() + ", "
				+ "\"toID\":" + toNode.getID() + ", "
				+ "\"type\":\""	+ type +"\", "
				+ "\"force\":\"" + force +"\", "
				+ "\"stance\":\"" + stance + "\"}");
		
		return str.toString();
	} 
	
	public String toString() {
		return "\nEdgeID: " + this.ID +
				"\nType: " + this.type + 
				"\nForce: " + this.force + 
				"\nStance: " + this.stance + 
				"\nFrom Node: " +this.fromNode.getID() +
				"\nTo Node: " + this.toNode.getID() + "\n";
	}
}
