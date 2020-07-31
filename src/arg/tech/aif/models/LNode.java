package arg.tech.aif.models;

import arg.tech.aif.enums.NodeType;
import arg.tech.wigmore.enums.EvidenceSide;

public class LNode extends INode {

	private EvidenceSide speaker;
	
	public LNode(String ID, String text, EvidenceSide speaker) {
		super(ID, text);
		this.setType(NodeType.L);
		this.speaker = speaker;	
	}
	
	public LNode(String ID, String text) {
		super(ID, text);
		this.setType(NodeType.L);
	}
	public EvidenceSide getSpeaker() {
		return speaker;
	}

	public void setSpeaker(EvidenceSide speaker) {
		this.speaker = speaker;
	}
	
	
	public String toJSON() {
		StringBuilder str = new StringBuilder();
		str.append("{");
		str.append("\"nodeID\":" + "\"" + this.getNodeID() + "\",");
		str.append("\"text\":" + "\"" + this.getText() + "\",");
		str.append("\"type\":" + "\"" + this.getType() + "\",");
		str.append("\"force\":" + "\"" + this.getForce() + "\",");
		str.append("\"stance\":" + "\"" + this.getStance() + "\"");
		str.append("}");
		return str.toString();
	}
	
	public String locutionJSON() {
			
		StringBuilder str = new StringBuilder();
		str.append("{\"nodeID\":\""+this.getNodeID()+"\",\"personID\":\"" + speaker + "\",\"start\":null,\"end\":null,\"source\":null}");
		return str.toString();
	}
	
}
