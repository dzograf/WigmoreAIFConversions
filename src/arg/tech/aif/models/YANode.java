package arg.tech.aif.models;

import arg.tech.aif.enums.AnchoringScheme;
import arg.tech.aif.enums.NodeType;

public class YANode extends SNode {
	private AnchoringScheme anchorScheme;

	public YANode() {
		super();
		// TODO Auto-generated constructor stub
	}

	public YANode(String ID, AnchoringScheme anchorScheme) {
		super(ID, NodeType.YA);
		this.anchorScheme = anchorScheme;
		// TODO Auto-generated constructor stub
	}
	
	public AnchoringScheme getAnchorScheme() {
		return anchorScheme;
	}

	public void setAnchorScheme(AnchoringScheme anchorScheme) {
		this.anchorScheme = anchorScheme;
	}

	@Override
	public String toJSON() {
		StringBuilder str = new StringBuilder();
		str.append("{");
		str.append("\"nodeID\":" + "\"" + this.getNodeID() + "\",");
		str.append("\"text\":" + "\"" + this.anchorScheme + "\",");
		str.append("\"type\":" + "\"" + this.getType() + "\",");
		str.append("\"force\":" + "\"" + this.getForce() + "\",");
		str.append("\"stance\":" + "\"" + this.getStance() + "\"");
		str.append("}");
		return str.toString();
	}
	

}
