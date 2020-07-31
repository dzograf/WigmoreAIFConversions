package arg.tech.aif.models;

import arg.tech.aif.enums.NodeType;
import arg.tech.wigmore.enums.SchemeForce;
import arg.tech.wigmore.enums.Stance;

public class SNode extends AIFNode {
	private SchemeForce force;
	private Stance stance;
	
	public SNode() {}

	public SNode(String ID, NodeType type) {
		super(ID, type);
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
	
	private String getschemeType() {
		String type = "";
		switch (this.getType()) {
		case CA:
			type = "Default Conflict";
			break;
		case MA:
			type = "Default Rephrase";
			break;
		case TA:
			type = "Default Transition";
			break;
		case PA:
			type = "Default Preference";
			break;
		default:
			break;
		}
		
		return type;
	}
	
	@Override
	public String toJSON() {
		StringBuilder str = new StringBuilder();
		str.append("{");
		str.append("\"nodeID\":" + "\"" + this.getNodeID() + "\",");
		str.append("\"text\":" + "\"" + this.getschemeType() + "\",");
		str.append("\"type\":" + "\"" + this.getType() + "\",");
		str.append("\"force\":" + "\"" + force + "\",");
		str.append("\"stance\":" + "\"" + stance + "\"");
		str.append("}");
		return str.toString();
	}
	
}
