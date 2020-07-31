package arg.tech.translation;

import java.util.ArrayList;

import org.omg.CORBA.INITIALIZE;

import arg.tech.aif.enums.AnchoringScheme;
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
import arg.tech.wigmore.enums.EvidenceForce;
import arg.tech.wigmore.enums.EvidenceSide;
import arg.tech.wigmore.enums.EvidenceType;
import arg.tech.wigmore.enums.SchemeForce;
import arg.tech.wigmore.enums.Stance;
import arg.tech.wigmore.models.WigEdge;
import arg.tech.wigmore.models.WigNode;
import arg.tech.wigmore.models.WigmoreGraph;

public class WigmoreToAIF {

	private static AIFGraph aifGraph;
	static int edgeCnt = 0;

	private static String generateEdgeID() {
		String edgeID = "e" + edgeCnt++;
		return edgeID;
	}

	private static void anchorLocution(INode lnode, INode iNode, AnchoringScheme anchorScheme) {
		YANode yaFromNode = new YANode("YA" + lnode.getNodeID() + iNode.getNodeID(), anchorScheme);
		yaFromNode.setForce(SchemeForce.NO_FORCE);
		yaFromNode.setStance(Stance.NEUTRAL);

		AIFEdge lToYAEdge = new AIFEdge(generateEdgeID(), lnode, yaFromNode);
		AIFEdge yaToIEdge = new AIFEdge(generateEdgeID(), yaFromNode, iNode);

		lnode.addOutgoingEdge(lToYAEdge);
		yaFromNode.addIncomingEdge(lToYAEdge);
		yaFromNode.addOutgoingEdge(yaToIEdge);
		iNode.addIncomingEdge(yaToIEdge);

		aifGraph.addNode(iNode);
		aifGraph.addNode(lnode);
		aifGraph.addNode(yaFromNode);

		aifGraph.addEdge(lToYAEdge);
		aifGraph.addEdge(yaToIEdge);

	}

	private static void createTransition(SNode taNode, LNode fromL, LNode toL) {

		AIFEdge lToTAEdge = new AIFEdge(generateEdgeID(), fromL, taNode);
		AIFEdge taToLEdge = new AIFEdge(generateEdgeID(), taNode, toL);

		fromL.addOutgoingEdge(lToTAEdge);
		taNode.addIncomingEdge(lToTAEdge);
		taNode.addOutgoingEdge(taToLEdge);
		toL.addIncomingEdge(taToLEdge);

		aifGraph.addNode(taNode);
		aifGraph.addEdge(lToTAEdge);
		aifGraph.addEdge(taToLEdge);
	}

	private static void anchorTAtoS(SNode taNode, SNode sNode, AnchoringScheme anchorScheme) {

		YANode yaTANode = new YANode("YA" + taNode.getNodeID() + sNode.getNodeID(), anchorScheme);
		yaTANode.setForce(SchemeForce.NO_FORCE);
		yaTANode.setStance(Stance.NEUTRAL);

		AIFEdge taToYAEdge = new AIFEdge(generateEdgeID(), taNode, yaTANode);
		taNode.addOutgoingEdge(taToYAEdge);
		yaTANode.addIncomingEdge(taToYAEdge);

		AIFEdge yaToRAEdge = new AIFEdge(generateEdgeID(), yaTANode, sNode);
		yaTANode.addOutgoingEdge(yaToRAEdge);
		sNode.addIncomingEdge(yaToRAEdge);

		aifGraph.addNode(yaTANode);

		aifGraph.addEdge(taToYAEdge);
		aifGraph.addEdge(yaToRAEdge);

	}

	private static ILocPair createINode(WigNode evidence) {
		String IDI, IDL;
		INode iNode;
		LNode lNode;

		if (evidence.getType() == EvidenceType.TESTIMONIAL) {
			IDI = "L" + evidence.getID();
			IDL = "LL" + evidence.getID();
			iNode = (INode) aifGraph.findNode(IDI);
			lNode = (LNode) aifGraph.findNode(IDL);
		} else {

			IDI = "I" + evidence.getID();
			IDL = "L" + evidence.getID();
			iNode = (INode) aifGraph.findNode(IDI);
			lNode = (LNode) aifGraph.findNode(IDL);
		}

		if (iNode == null && lNode == null) {

			if (evidence.getType() == EvidenceType.TESTIMONIAL) {
				iNode = new LNode(IDI, EvidenceSide.WITNESS + ": " + evidence.getText(), EvidenceSide.WITNESS);
				iNode.setForce(evidence.getForce());
				iNode.setStance(evidence.getStance());

				lNode = new LNode(IDL, evidence.getSide() + ": " + EvidenceSide.WITNESS + ": " + evidence.getText(),
						evidence.getSide());
				lNode.setForce(evidence.getForce());
				lNode.setStance(evidence.getStance());

				anchorLocution(lNode, iNode, AnchoringScheme.ASSERT);
			} else {
				iNode = new INode(IDI, evidence.getText());
				iNode.setForce(evidence.getForce());
				iNode.setStance(evidence.getStance());
				lNode = new LNode(IDL, evidence.getSide() + ": " + evidence.getText(), evidence.getSide());
				lNode.setForce(evidence.getForce());
				lNode.setStance(evidence.getStance());
				anchorLocution(lNode, iNode, AnchoringScheme.ASSERT);
			}
		}

		ILocPair pair = new ILocPair(iNode, lNode);

		return pair;

	}

	private static ILocPair createINode1(WigNode evidence) {

		String IDI = "I" + evidence.getID();
		String IDL = "L" + evidence.getID();
		INode iNode = (INode) aifGraph.findNode(IDI);
		LNode lNode = (LNode) aifGraph.findNode(IDL);

		if (iNode == null && lNode == null) {
			iNode = new INode(IDI, evidence.getText());

			if (evidence.getType() == EvidenceType.TESTIMONIAL) {
				lNode = new LNode("L" + IDL,
						evidence.getSide() + ": " + EvidenceSide.WITNESS + ": " + evidence.getText(),
						evidence.getSide());
				LNode lWitnessNode = new LNode(IDL, EvidenceSide.WITNESS + ": " + evidence.getText(),
						EvidenceSide.WITNESS);
				anchorLocution(lNode, lWitnessNode, AnchoringScheme.ASSERT);
				anchorLocution(lWitnessNode, iNode, AnchoringScheme.TESTIFY);
			} else {
				lNode = new LNode(IDL, evidence.getSide() + ": " + evidence.getText(), evidence.getSide());
				anchorLocution(lNode, iNode, AnchoringScheme.ASSERT);
			}
		}

		ILocPair pair = new ILocPair(iNode, lNode);

		return pair;
	}

	private static void createRA(WigEdge edge, ArrayList<INode> fromNodes, INode toNode, SNode taNode,
			ReasoningScheme reasoningScheme) {
		RANode raNode = new RANode("RA" + fromNodes.get(0).getNodeID() + toNode.getNodeID(), reasoningScheme);
		raNode.setForce(edge.getForce());
		raNode.setStance(edge.getStance());

		for (INode fromNode : fromNodes) {
			AIFEdge iToRAEdge = new AIFEdge(generateEdgeID(), fromNode, raNode);
			fromNode.addOutgoingEdge(iToRAEdge);
			raNode.addIncomingEdge(iToRAEdge);
			aifGraph.addEdge(iToRAEdge);
		}

		AIFEdge raToIEdge = new AIFEdge(generateEdgeID(), raNode, toNode);
		raNode.addOutgoingEdge(raToIEdge);
		toNode.addIncomingEdge(raToIEdge);
		aifGraph.addEdge(raToIEdge);

		aifGraph.addNode(raNode);
		anchorTAtoS(taNode, raNode, AnchoringScheme.ARGUE);
	}

	private static void createCA(WigEdge edge, INode fromNode, AIFNode toNode, SNode taNode) {
		SNode caNode = new SNode("CA" + fromNode.getNodeID() + toNode.getNodeID(), NodeType.CA);
		caNode.setForce(edge.getForce());
		caNode.setStance(edge.getStance());

		AIFEdge iToCAEdge = new AIFEdge(generateEdgeID(), fromNode, caNode);
		fromNode.addOutgoingEdge(iToCAEdge);
		caNode.addIncomingEdge(iToCAEdge);
		aifGraph.addEdge(iToCAEdge);

		AIFEdge raToIEdge = new AIFEdge(generateEdgeID(), caNode, toNode);
		caNode.addOutgoingEdge(raToIEdge);
		toNode.addIncomingEdge(raToIEdge);
		aifGraph.addEdge(raToIEdge);

		aifGraph.addNode(caNode);
		if (taNode != null)
			anchorTAtoS(taNode, caNode, AnchoringScheme.CHALLENGING);

	}

	private static WigNode findCorroboratedEvidence(WigmoreGraph wigGraph, String currentId) {
		for (WigEdge edge : wigGraph.getEdges()) {
			if (currentId.compareTo(edge.getFromNode().getID()) == 0) {
				return edge.getToNode();
			}
		}

		return null;
	}

	public static AIFGraph convert(WigmoreGraph wigGraph) {

		aifGraph = new AIFGraph();

		for (WigEdge edge : wigGraph.getEdges()) {

			// from Node
			WigNode fromNode = edge.getFromNode();
			ILocPair iLocPairFrom = createINode(fromNode);

			// to Node
			WigNode toNode = edge.getToNode();
			ILocPair iLocPairTo = createINode(toNode);

			SNode taNode = new SNode("TA" + iLocPairFrom.getLNode().getNodeID() + iLocPairTo.getLNode().getNodeID(),
					NodeType.TA);
			taNode.setForce(SchemeForce.NO_FORCE);
			taNode.setStance(Stance.NEUTRAL);
			createTransition(taNode, iLocPairFrom.getLNode(), iLocPairTo.getLNode());

			// createCA(edge, iLocPairFrom.getINode(), iLocPairTo.getINode(), taNode);

			if (fromNode.getType() == EvidenceType.TESTIMONIAL && edge.getType() != EdgeType.EXPLANATORY_EDGE) {
				if (edge.getStance() != Stance.NEGATORY) {
					ArrayList<INode> fromNodes = new ArrayList<INode>();
					fromNodes.add(iLocPairFrom.getINode());

					anchorLocution(iLocPairFrom.getINode(), iLocPairTo.getINode(), AnchoringScheme.TESTIFY);
					createRA(edge, fromNodes, iLocPairTo.getINode(), taNode, ReasoningScheme.WITNESS_TESTIMONY);
				} else {

					ArrayList<INode> fromNodes = new ArrayList<INode>();
					fromNodes.add(iLocPairFrom.getINode());

					INode intermediateNode = new INode("I'" + iLocPairTo.getINode().getNodeID(),
							"Negation of: " + iLocPairTo.getINode().getText());
					intermediateNode.setForce(EvidenceForce.NO_FORCE);
					aifGraph.addNode(intermediateNode);

					anchorLocution(iLocPairFrom.getINode(), intermediateNode, AnchoringScheme.TESTIFY);
					createRA(edge, fromNodes, intermediateNode, taNode, ReasoningScheme.WITNESS_TESTIMONY);

					createCA(edge, intermediateNode, iLocPairTo.getINode(), null);
				}

			} else if (fromNode.getType() == EvidenceType.CIRCUMSTANTIAL
					&& edge.getType() != EdgeType.EXPLANATORY_EDGE) {
				if (edge.getStance() != Stance.NEGATORY) {
					ArrayList<INode> fromNodes = new ArrayList<INode>();
					fromNodes.add(iLocPairFrom.getINode());
					createRA(edge, fromNodes, iLocPairTo.getINode(), taNode, ReasoningScheme.DEFAULT_INFERENCE);
				} else {
					ArrayList<INode> fromNodes = new ArrayList<INode>();
					fromNodes.add(iLocPairFrom.getINode());

					INode intermediateNode = new INode("I'" + iLocPairTo.getINode().getNodeID(),
							"Negation of: " + iLocPairTo.getINode().getText());
					intermediateNode.setForce(EvidenceForce.NO_FORCE);
					aifGraph.addNode(intermediateNode);

					createRA(edge, fromNodes, iLocPairTo.getINode(), taNode, ReasoningScheme.DEFAULT_INFERENCE);

					createCA(edge, intermediateNode, iLocPairTo.getINode(), null);
				}

			} else if (fromNode.getType() == EvidenceType.CORROBORATIVE
					&& edge.getType() != EdgeType.EXPLANATORY_EDGE) {

				WigNode corrobEvidence = findCorroboratedEvidence(wigGraph, toNode.getID());

				if (corrobEvidence != null) {
					ILocPair iLocPairCorrob = createINode(corrobEvidence);
					ArrayList<INode> fromNodes = new ArrayList<INode>();
					fromNodes.add(iLocPairFrom.getINode());
					fromNodes.add(iLocPairTo.getINode());
					createRA(edge, fromNodes, iLocPairCorrob.getINode(), taNode, ReasoningScheme.CORROBORATIVE_EFFECT);

				} else {
					ArrayList<INode> fromNodes = new ArrayList<INode>();
					fromNodes.add(iLocPairFrom.getINode());
					createRA(edge, fromNodes, iLocPairTo.getINode(), taNode, ReasoningScheme.CORROBORATIVE_EFFECT);
				}

			}
		}

		for (WigEdge edge : wigGraph.getEdges()) {
			if (edge.getType() == EdgeType.EXPLANATORY_EDGE) {
				// from Node
				WigNode fromNode = edge.getFromNode();
				ILocPair iLocPairFrom = createINode(fromNode);

				// to Node
				WigNode toNode = edge.getToNode();
				ILocPair iLocPairTo = createINode(toNode);

				SNode taNode = new SNode("TA" + iLocPairFrom.getLNode().getNodeID() + iLocPairTo.getLNode().getNodeID(),
						NodeType.TA);
				createTransition(taNode, iLocPairFrom.getLNode(), iLocPairTo.getLNode());

				boolean hasInEdge = false;
				for (AIFEdge inEdge : iLocPairFrom.getINode().getIncomingEdges()) {
					if (inEdge.getFromNode().getType() == NodeType.RA) {
						createCA(edge, iLocPairTo.getINode(), inEdge.getFromNode(), taNode);
						hasInEdge = true;
					}
				}

				if (!hasInEdge) {
					for (AIFEdge outEdge : iLocPairFrom.getINode().getOutgoingEdges()) {
						if (outEdge.getToNode().getType() == NodeType.RA) {
							createCA(edge, iLocPairTo.getINode(), outEdge.getToNode(), taNode);
						}
					}
				}
			}
		}
		return aifGraph;
	}
}
