package arg.tech.parsers;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;

import javax.print.DocFlavor.INPUT_STREAM;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

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
import arg.tech.wigmore.enums.SchemeForce;
import arg.tech.wigmore.enums.Stance;
import arg.tech.wigmore.models.WigEdge;
import arg.tech.wigmore.models.WigNode;

public class AIFParser {
	

	public static AIFGraph parse(JSONObject jsonObj) {
		
		AIFGraph aifGraph = new AIFGraph();
	//	JSONParser jsonParser = new JSONParser();

		ArrayList<AIFNode> nodes = new ArrayList<AIFNode>();
		ArrayList<AIFEdge> edges = new ArrayList<AIFEdge>();

		try {

		//	JSONObject jsonObj = (JSONObject) jsonParser.parse(new FileReader(filename));

			JSONArray nodesJSON = (JSONArray) jsonObj.get("nodes");
			
			Iterator<JSONObject> iterator = nodesJSON.iterator();
			
			while (iterator.hasNext()) {
				JSONObject obj = iterator.next();
				String nodeIDString = "";
				if (obj.get("nodeID") != null)
					nodeIDString = obj.get("nodeID").toString();
				
				String textString = "";
				if(obj.get("text") != null)
					textString = obj.get("text").toString();
				
				String typeString = "";
				if(obj.get("type") != null)
					typeString = obj.get("type").toString();
				
				String forceString = "";
				if(obj.get("force") != null)
					forceString = obj.get("force").toString();
				
				String stanceString = "";
				if(obj.get("stance") != null)
					stanceString = obj.get("stance").toString();
				
				AIFNode node = new AIFNode();
				if(typeString.compareTo("I") == 0) {
					node = new INode(nodeIDString, textString);
					((INode)node).setForce(EvidenceForce.getEvidenceForce(forceString));
					((INode)node).setStance(Stance.getStance(stanceString));
					
				} else if(typeString.compareTo("L") == 0) {
					node = new LNode(nodeIDString, textString);
					((LNode)node).setForce(EvidenceForce.getEvidenceForce(forceString));
					((LNode)node).setStance(Stance.getStance(stanceString));
					
				} else if(typeString.compareTo("RA") == 0) {
					node = new RANode(nodeIDString, ReasoningScheme.getReasoningScheme(textString));
					((RANode)node).setForce(SchemeForce.getSchemeForce(forceString));
					((RANode)node).setStance(Stance.getStance(stanceString));
					
				} else if(typeString.compareTo("YA") == 0) {
					node = new YANode(nodeIDString, AnchoringScheme.getAnchoringScheme(textString));
					((YANode)node).setForce(SchemeForce.getSchemeForce(forceString));
					((YANode)node).setStance(Stance.getStance(stanceString));
					
				} else if(typeString.compareTo("CA") == 0 || typeString.compareTo("PA") == 0 ||
						typeString.compareTo("PA") == 0 || typeString.compareTo("MA") == 0 ||
						typeString.compareTo("TA") == 0) {
					
					node = new SNode(nodeIDString, NodeType.getNodeType(typeString));
					((SNode)node).setForce(SchemeForce.getSchemeForce(forceString));
					((SNode)node).setStance(Stance.getStance(stanceString));
				}
				aifGraph.addNode(node);
			}
			

			JSONArray edgesJSON = (JSONArray) jsonObj.get("edges");
			Iterator<JSONObject> iter = edgesJSON.iterator();
			while (iter.hasNext()) {
				JSONObject obj = iter.next();
				String edgeIDString = "";
				if (obj.get("edgeID") != null)
					edgeIDString = obj.get("edgeID").toString();
				AIFNode fromNode = aifGraph.findNode(obj.get("fromID").toString());
				AIFNode toNode = aifGraph.findNode(obj.get("toID").toString());

				if (fromNode != null && toNode != null) {

					
					AIFEdge edge = new AIFEdge();
					edge.setID(edgeIDString);
					edge.setFromNode(fromNode);
					edge.setToNode(toNode);

					fromNode.addOutgoingEdge(edge);
					toNode.addIncomingEdge(edge);
					aifGraph.addEdge(edge);
				} else {
					throw new Exception("Invalid diagram");
				}
			}
			
			
			JSONArray locutionsJSON = (JSONArray) jsonObj.get("locutions");
			Iterator<JSONObject> locIterator = locutionsJSON.iterator();
			while (locIterator.hasNext()) {
				JSONObject obj = locIterator.next();
				String nodeIDString = "";
				if (obj.get("nodeID") != null) {
					nodeIDString = obj.get("nodeID").toString();
				}
				
				String personIDString = "";
				if (obj.get("personID") != null) {
					personIDString = obj.get("personID").toString();
				}
				
				int nodePos = aifGraph.findPosNode(nodeIDString);
				AIFNode node = aifGraph.findNode(nodePos);
				
				if(node != null) {
					LNode lNode = (LNode) node;
					lNode.setSpeaker(EvidenceSide.getEvidenceSide(personIDString));
					aifGraph.updateNode(nodePos, lNode);
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return aifGraph;
	}
}
