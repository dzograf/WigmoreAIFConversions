package arg.tech.aif.models;

import arg.tech.aif.enums.NodeType;
import arg.tech.wigmore.enums.EvidenceForce;
import arg.tech.wigmore.enums.Stance;

public class INode extends AIFNode{
	private String text;
	private EvidenceForce force;
	private Stance stance;
	
	public INode(String ID, String text){
		super(ID, NodeType.I);
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
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

	
	public String toJSON() {
		StringBuilder str = new StringBuilder();
		str.append("{");
		str.append("\"nodeID\":" + "\"" + this.getNodeID() + "\",");
		str.append("\"text\":" + "\"" + this.getText() + "\",");
		str.append("\"type\":" + "\"" + this.getType() + "\",");
		str.append("\"force\":" + "\"" + force + "\",");
		str.append("\"stance\":" + "\"" + stance + "\"");
		str.append("}");
		return str.toString();
	}

	
}
