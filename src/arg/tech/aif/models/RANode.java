package arg.tech.aif.models;

import arg.tech.aif.enums.NodeType;
import arg.tech.aif.enums.ReasoningScheme;

public class RANode extends SNode {
	
	private ReasoningScheme reasoningScheme;

	public RANode() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RANode(String ID, ReasoningScheme reasoningScheme) {
		super(ID, NodeType.RA);
		this.setReasoningScheme(reasoningScheme);
		// TODO Auto-generated constructor stub
	}

	public ReasoningScheme getReasoningScheme() {
		return reasoningScheme;
	}

	public void setReasoningScheme(ReasoningScheme reasoningScheme) {
		this.reasoningScheme = reasoningScheme;
	}
	
	
	@Override
	public String toJSON() {
		StringBuilder str = new StringBuilder();
		str.append("{");
		str.append("\"nodeID\":" + "\"" + this.getNodeID() + "\",");
		str.append("\"text\":" + "\"" + this.reasoningScheme + "\",");
		str.append("\"type\":" + "\"" + this.getType() + "\",");
		str.append("\"force\":" + "\"" + this.getForce()+ "\",");
		str.append("\"stance\":" + "\"" + this.getStance() + "\"");
		str.append("}");
		return str.toString();
	}
	
}
