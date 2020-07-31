package arg.tech.translation;

import arg.tech.aif.models.INode;
import arg.tech.aif.models.LNode;

public class ILocPair {
	private INode iNode;
	private LNode lNode;
	
	public ILocPair(INode iNode, LNode lNode) {
		this.iNode = iNode;
		this.lNode = lNode;
	}
	
	public INode getINode() {
		return iNode;
	}
	public void setINode(INode iNode) {
		this.iNode = iNode;
	}
	public LNode getLNode() {
		return lNode;
	}
	public void setLNode(LNode lNode) {
		this.lNode = lNode;
	}
	
	
}
