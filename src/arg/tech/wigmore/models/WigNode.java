package arg.tech.wigmore.models;

import java.util.ArrayList;

import arg.tech.wigmore.enums.EvidenceForce;
import arg.tech.wigmore.enums.EvidenceSide;
import arg.tech.wigmore.enums.EvidenceType;
import arg.tech.wigmore.enums.Stance;


public class WigNode {
	
	private String ID;
	private String text;
	private EvidenceType type;
	private EvidenceSide side;
	private EvidenceForce force;
	private Stance stance;
	

	private ArrayList<WigEdge> incomingEdges;
	private ArrayList<WigEdge> outgoingEdges;
	
	public WigNode() {}
	
	public WigNode(String ID) {
		this.ID = ID;

		incomingEdges = new ArrayList<WigEdge>();
		outgoingEdges = new ArrayList<WigEdge>();
	}
	
	public WigNode(String ID, String text, EvidenceType type, EvidenceSide side, EvidenceForce force, Stance stance) {
		this.ID = ID;
		this.text = text;
		this.type = type;
		this.side = side;
		this.force = force;
		this.stance = stance;
		incomingEdges = new ArrayList<WigEdge>();
		outgoingEdges = new ArrayList<WigEdge>();
	}
	
	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public EvidenceType getType() {
		return type;
	}

	public void setType(EvidenceType type) {
		this.type = type;
	}

	public EvidenceSide getSide() {
		return side;
	}

	public void setSide(EvidenceSide side) {
		this.side = side;
	}

	public EvidenceForce getForce() {
		return force;
	}

	public void setForce(EvidenceForce force) {
		this.force = force;
	}

	public Stance getStance() {
		return stance;
	}

	public void setStance(Stance stance) {
		this.stance = stance;
	}

	public ArrayList<WigEdge> getIncomingEdges() {
		return incomingEdges;
	}

	public void setIncomingEdges(ArrayList<WigEdge> incomingEdges) {
		this.incomingEdges = incomingEdges;
	}

	public ArrayList<WigEdge> getOutgoingEdges() {
		return outgoingEdges;
	}

	public void setOutgoingEdges(ArrayList<WigEdge> outgoingEdges) {
		this.outgoingEdges = outgoingEdges;
	}
	
	public void addIncomingEdge(WigEdge edge) {
		incomingEdges.add(edge);
	}
	
	public void addOutgoingEdge(WigEdge edge) {
		outgoingEdges.add(edge);
	}
	
	public String toJSON() {
		StringBuilder str = new StringBuilder();
		
		str.append("{");
		str.append("\"nodeID\":" + "\"" + ID + "\",");
		str.append("\"type\":" + "\"" + type + "\",");
		str.append("\"text\":" + "\"" + text + "\",");
		str.append("\"side\":" + "\"" + side + "\",");
		str.append("\"force\":" + "\"" + force + "\",");
		str.append("\"stance\":" + "\"" + stance + "\"");
		str.append("}");
		return str.toString();
	}

	public String toString() {
		
		StringBuilder stringBuilder = new StringBuilder();
		
		stringBuilder.append("ID: " + this.ID +
				"\nText: " + this.text + 
				"\nType: " + this.type + 
				"\nSide: " +this.side + 
				"\nForce: " + this.force + 
				"\nStance: " + this.stance);
		
		stringBuilder.append("\nINCOMING Nodes\n");
		for (WigEdge edge : incomingEdges) {
			stringBuilder.append(edge.getFromNode().getID()+"\n");
		}
		stringBuilder.append("\nOUTGOING Nodes\n");
		for (WigEdge edge : outgoingEdges) {
			stringBuilder.append(edge.getToNode().getID() + "\n");
		}
		return stringBuilder.toString();
	}
}
