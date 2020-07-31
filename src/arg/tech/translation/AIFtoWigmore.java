package arg.tech.translation;

import static org.junit.Assert.assertNotNull;

import java.awt.font.NumericShaper.Range;
import java.util.ArrayList;

import javax.annotation.Generated;
import javax.print.DocFlavor.INPUT_STREAM;

import org.omg.CORBA.INITIALIZE;

import arg.tech.aif.enums.NodeType;
import arg.tech.aif.enums.ReasoningScheme;
import arg.tech.aif.models.AIFEdge;
import arg.tech.aif.models.AIFGraph;
import arg.tech.aif.models.AIFNode;
import arg.tech.aif.models.INode;
import arg.tech.aif.models.LNode;
import arg.tech.aif.models.RANode;
import arg.tech.aif.models.SNode;
import arg.tech.aif.models.YANode;
import arg.tech.wigmore.enums.EdgeType;
import arg.tech.wigmore.enums.EvidenceType;
import arg.tech.wigmore.enums.Stance;
import arg.tech.wigmore.models.WigEdge;
import arg.tech.wigmore.models.WigNode;
import arg.tech.wigmore.models.WigmoreGraph;

public class AIFtoWigmore {
	private static WigmoreGraph wigmoreGraph;
	static int edgeCnt = 0;


	private static String generateEdgeID() {
		String edgeID = "e" + edgeCnt++;
		return edgeID;
	}

	private static LNode findLNode(INode iNode) {

		for (AIFEdge edge : iNode.getIncomingEdges()) {
			AIFNode fromyaNode = edge.getFromNode();
			if (fromyaNode.getType() == NodeType.YA) {
				for (AIFEdge edge2 : fromyaNode.getIncomingEdges()) {
					AIFNode fromLNode = edge2.getFromNode();
					if (fromLNode.getType() == NodeType.L) {
						return (LNode) fromLNode;
					}
				}
			}
		}

		return null;
	}

	private static boolean connectedToDifferentRA(INode iNode, RANode raNode1) {
		for (AIFEdge outEdge : iNode.getOutgoingEdges()) {
			AIFNode outNode = outEdge.getToNode();
			if(outNode.getType() == NodeType.RA) {
				RANode raNode2 = (RANode)outNode;
				if(raNode1.getNodeID().compareTo(raNode2.getNodeID()) != 0){
					return true;
				}
			}
		}
		return false;
	}
	
	private static String fixID(String nodeID) {
		nodeID = nodeID.replaceAll("\\D+","");
		return nodeID;
	}
	
	private static WigNode createEvidence(INode iNode) {

		LNode lNode = findLNode(iNode);
		WigNode evidence = null;
		
		
		for (AIFEdge outEdge : iNode.getOutgoingEdges()) {
			AIFNode outNode = outEdge.getToNode();
			if(outNode.getType() == NodeType.RA) {
				if(((RANode)outNode).getReasoningScheme() == ReasoningScheme.DEFAULT_INFERENCE) {
					if (lNode != null) {
						 evidence = new WigNode(fixID(iNode.getNodeID()), iNode.getText(),
							EvidenceType.CIRCUMSTANTIAL, lNode.getSpeaker(), iNode.getForce(),
							iNode.getStance());
						 
						 return evidence;
					}
				} else if(((RANode)outNode).getReasoningScheme() == ReasoningScheme.WITNESS_TESTIMONY) {
					if (lNode != null) {
						if(iNode.getText().startsWith("Witness:"))
							iNode.setText(iNode.getText().replace("Witness: ", ""));
						 evidence = new WigNode(fixID(iNode.getNodeID()), iNode.getText(),
							EvidenceType.TESTIMONIAL, lNode.getSpeaker(), iNode.getForce(),
							iNode.getStance());
						 
						 return evidence;
					}
				} else if(((RANode)outNode).getReasoningScheme() == ReasoningScheme.CORROBORATIVE_EFFECT &&
						!connectedToDifferentRA(iNode, (RANode)outNode)) {
					if (lNode != null) {
						 evidence = new WigNode(fixID(iNode.getNodeID()), iNode.getText(),
							EvidenceType.CORROBORATIVE, lNode.getSpeaker(), iNode.getForce(),
							iNode.getStance());
						 
						 return evidence;
					}
				}
				
				//na dw thn periptwsh tou explanatory evidence...
			} else if(outNode.getType() == NodeType.CA) {
				ArrayList<AIFEdge> outEdges = outNode.getOutgoingEdges();
				for (AIFEdge aifEdge : outEdges) {
					if(aifEdge.getToNode().getType() == NodeType.RA) {
						evidence = new WigNode(fixID(iNode.getNodeID()), iNode.getText(), 
								EvidenceType.EXPLANATORY, lNode.getSpeaker(), iNode.getForce(), 
								iNode.getStance());
						return evidence;
					}
				}
			}
		}
		
		if (lNode != null) {
			 evidence = new WigNode(fixID(iNode.getNodeID()), iNode.getText(),
				EvidenceType.CIRCUMSTANTIAL, lNode.getSpeaker(), iNode.getForce(),
				iNode.getStance());
		}
		return evidence;
	}
	
	
	
	private static void createEvidenceNodes(AIFGraph aifGraph) {
		for (AIFNode aifNode : aifGraph.getNodes()) {
			
			if(aifNode.getType() == NodeType.I && ! aifNode.getNodeID().startsWith("I'") || 
					aifNode.getType() == NodeType.L && ((LNode)aifNode).getText().startsWith("Witness:")) {
				
				WigNode wigNode = createEvidence((INode)aifNode);
				wigmoreGraph.addNode(wigNode);
				
			}
		}
	}
	
	private static AIFNode findNegatory(AIFNode node) {
		ArrayList<AIFEdge> outEdges = node.getOutgoingEdges();
		ArrayList<AIFEdge> inEdges = node.getIncomingEdges();
		
		for (AIFEdge outEdge : outEdges) {
			AIFNode outNode = outEdge.getToNode();
			if(outNode.getType() == NodeType.CA) {
				AIFNode caOutNode = outNode.getOutgoingEdges().get(0).getToNode();
				return caOutNode;
			}
		}
		
		for (AIFEdge inEdge : inEdges) {
			AIFNode inNode = inEdge.getFromNode();
			if(inNode.getType() == NodeType.CA) {
				AIFNode caInNode = inNode.getIncomingEdges().get(0).getFromNode();
				return caInNode;
			}
		}
		return null;
	}
	
	private static AIFNode caLeadsTo(AIFNode caNode) {
		AIFNode caOutNode = caNode.getOutgoingEdges().get(0).getToNode();
		
		return  caOutNode;
		
	}
	
	private static void createExplanatoryEdge(AIFNode node, RANode raNode) {
		
		
		//if conclusion of raNode is premise of another ra then get create edge with the conclusion of raNode
		boolean getConclusion = false;
		for (AIFEdge outEdge : raNode.getOutgoingEdges()) {
			if(outEdge.getToNode().getType() == NodeType.I) {
				INode conclusionOfRANode = (INode) outEdge.getToNode();
				for (AIFEdge outEdgeOfConcl : conclusionOfRANode.getOutgoingEdges()) {
					if(outEdgeOfConcl.getToNode().getType() == NodeType.RA) {
						getConclusion = true;
					}
				}
			}
		}
		
		
		AIFNode startingINode = new AIFNode();
		
		if(getConclusion) {
			for (AIFEdge edge : raNode.getOutgoingEdges()) {
				if(edge.getToNode().getType() == NodeType.I ) {
					startingINode = (INode) edge.getToNode();
				}
			}
		} else {
			for (AIFEdge edge : raNode.getIncomingEdges()) {
				if((edge.getFromNode().getType() == NodeType.I  || edge.getFromNode().getType() == NodeType.L )
						&& !connectedToDifferentRA( (INode) edge.getFromNode(), raNode)) {
					startingINode = (INode) edge.getFromNode();
				}
			}
		}
		
		WigNode fromEvidence = wigmoreGraph.findNode(fixID(startingINode.getNodeID()));
		AIFNode explanatoryNode = node.getIncomingEdges().get(0).getFromNode();
		WigNode explanatoryEvidence = wigmoreGraph.findNode(fixID(explanatoryNode.getNodeID()));
		
		WigEdge edge = new WigEdge(generateEdgeID(), EdgeType.EXPLANATORY_EDGE, ((SNode)node).getForce(), 
				((SNode)node).getStance(), fromEvidence, explanatoryEvidence);
		fromEvidence.addOutgoingEdge(edge);
		explanatoryEvidence.addIncomingEdge(edge);
		wigmoreGraph.addEdge(edge);
		
	}
	
	private static void createCorroborativeEdge(RANode raNode) {
		ArrayList<AIFNode> corroboratingNodes = new ArrayList<AIFNode>();
		AIFNode corroboratedINode = null;
		
		for (AIFEdge edge : raNode.getIncomingEdges()) {
			AIFNode aifNode = edge.getFromNode();
			if(aifNode.getType() == NodeType.I ||  aifNode.getType() == NodeType.L){
				if(connectedToDifferentRA((INode) aifNode, raNode)) {
					corroboratedINode = aifNode;
				} else {
					corroboratingNodes.add(aifNode);
				
				}
			}
		}
		
		if(corroboratedINode != null) {
			WigNode corroboratedEvidence = wigmoreGraph.findNode(fixID(corroboratedINode.getNodeID()));
 			for (AIFNode corroboratingINode : corroboratingNodes) {
 				WigNode corroboratingEvidence = wigmoreGraph.findNode(fixID(corroboratingINode.getNodeID()));
				WigEdge edge = new WigEdge(generateEdgeID(), EdgeType.CORROBORATIVE_EDGE, raNode.getForce(), 
						raNode.getStance(), corroboratingEvidence, corroboratedEvidence);
				corroboratingEvidence.addOutgoingEdge(edge);
				corroboratedEvidence.addIncomingEdge(edge);
				wigmoreGraph.addEdge(edge);
			}
		}
	}
	
	
	private static void createProvisionalEdge(RANode raNode) {
		
		ArrayList<AIFEdge> inEdges = raNode.getIncomingEdges();
		for (AIFEdge fromEdge : inEdges) {
			AIFNode fromNode = fromEdge.getFromNode();
			if(fromNode.getType() == NodeType.I || fromNode.getType() == NodeType.L) {
				WigNode fromEvidenceNode = wigmoreGraph.findNode(fixID(fromNode.getNodeID()));
				
				ArrayList<AIFEdge> outEdges = raNode.getOutgoingEdges();
				for (AIFEdge toEdge : outEdges) {
					AIFNode toNode = toEdge.getToNode();
					if(toNode.getType() == NodeType.I) {
						WigNode toEvidenceNode = wigmoreGraph.findNode(fixID(toNode.getNodeID()));
						
						if(raNode.getStance() != Stance.NEGATORY) {
							WigEdge edge = new WigEdge(generateEdgeID(), EdgeType.PROVISIONAL, raNode.getForce(), raNode.getStance(), fromEvidenceNode, toEvidenceNode);
							fromEvidenceNode.addOutgoingEdge(edge);
							toEvidenceNode.addIncomingEdge(edge);
							wigmoreGraph.addEdge(edge);
						} else {
							AIFNode toNode2 = findNegatory(toNode);
							WigNode toEvidenceNode2 = wigmoreGraph.findNode(fixID(toNode2.getNodeID()));
							WigEdge edge = new WigEdge(generateEdgeID(), EdgeType.PROVISIONAL, raNode.getForce(), raNode.getStance(), fromEvidenceNode, toEvidenceNode2);
							fromEvidenceNode.addOutgoingEdge(edge);
							toEvidenceNode2.addIncomingEdge(edge);
							wigmoreGraph.addEdge(edge);
						}
					}
				}
			}
		}
	}
	
	
	private static void createEdges(AIFGraph aifGraph) {
		
		for (AIFNode node : aifGraph.getNodes()) {
			
			if(node.getType() == NodeType.CA) {
				AIFNode outNode = caLeadsTo(node);
				if(outNode.getType() == NodeType.RA) {
					
					RANode raNode = (RANode)outNode;
					createExplanatoryEdge(node, raNode);
				}
				
			} else if(node.getType() == NodeType.RA) {
				
				RANode raNode = (RANode) node;
				
				if(raNode.getReasoningScheme() == ReasoningScheme.CORROBORATIVE_EFFECT) 	
					createCorroborativeEdge(raNode);
				else 
					createProvisionalEdge(raNode);
			} 
		}
	}
	
	
	public static WigmoreGraph convert(AIFGraph aifGraph) {

		wigmoreGraph = new WigmoreGraph();
		
		createEvidenceNodes(aifGraph);
		createEdges(aifGraph);
		
		
		return wigmoreGraph;
	}
}
