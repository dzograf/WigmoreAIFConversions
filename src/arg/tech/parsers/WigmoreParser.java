package arg.tech.parsers;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import arg.tech.wigmore.enums.EdgeType;
import arg.tech.wigmore.enums.EvidenceForce;
import arg.tech.wigmore.enums.EvidenceSide;
import arg.tech.wigmore.enums.EvidenceType;
import arg.tech.wigmore.enums.SchemeForce;
import arg.tech.wigmore.enums.Stance;
import arg.tech.wigmore.models.WigEdge;
import arg.tech.wigmore.models.WigNode;
import arg.tech.wigmore.models.WigmoreGraph;

public class WigmoreParser {

	private static WigNode findNode(String id, ArrayList<WigNode> nodes) {
		for (WigNode node : nodes) {
			if (node.getID().compareTo(id) == 0) {
				return node;
			}
		}
		return null;
	}

	public static WigmoreGraph parse(JSONObject jsonObj) {

		//JSONParser parser = new JSONParser();

		ArrayList<WigNode> nodes = new ArrayList<WigNode>();
		ArrayList<WigEdge> edges = new ArrayList<WigEdge>();
		try {

			// A JSON object. Key value pairs are unordered. JSONObject supports
			// java.util.Map interface.
		//	JSONObject jsonObj = (JSONObject) parser.parse(new FileReader(filename));

			JSONArray nodesJSON = (JSONArray) jsonObj.get("nodes");
			JSONArray edgesJSON = (JSONArray) jsonObj.get("edges");

			Iterator<JSONObject> iterator = nodesJSON.iterator();
			while (iterator.hasNext()) {
				JSONObject obj = iterator.next();
				String nodeIDString = "";
				if (obj.get("nodeID") != null)
					nodeIDString = obj.get("nodeID").toString();

				String textString = "";
				if (obj.get("text") != null)
					textString = obj.get("text").toString();

				String typeString = "";
				if (obj.get("type") != null)
					typeString = obj.get("type").toString();

				String sideString = "";
				if (obj.get("side") != null)
					sideString = obj.get("side").toString();

				String forceString = "";
				if (obj.get("force") != null)
					forceString = obj.get("force").toString();

				String stanceString = "";
				if (obj.get("stance") != null)
					stanceString = obj.get("stance").toString();

				WigNode node = new WigNode(nodeIDString);
				node.setType(EvidenceType.getEvidenceType(typeString));
				node.setText(textString);
				node.setSide(EvidenceSide.getEvidenceSide(sideString));
				node.setForce(EvidenceForce.getEvidenceForce(forceString));
				node.setStance(Stance.getStance(stanceString));

				nodes.add(node);
			}

			Iterator<JSONObject> iter = edgesJSON.iterator();
			while (iter.hasNext()) {
				JSONObject obj = iter.next();
				String edgeIDString = "";
				if (obj.get("edgeID") != null)
					edgeIDString = obj.get("edgeID").toString();
				WigNode fromNode = findNode(obj.get("fromID").toString(), nodes);
				WigNode toNode = findNode(obj.get("toID").toString(), nodes);

				if (fromNode != null && toNode != null) {

					String typeString = "";
					if (obj.get("type") != null)
						typeString = obj.get("type").toString();

					String forceString = "";
					if (obj.get("force") != null)
						forceString = obj.get("force").toString();

					String stanceString = "";
					if (obj.get("stance") != null)
						stanceString = obj.get("stance").toString();

					WigEdge edge = new WigEdge();
					edge.setID(edgeIDString);
					edge.setType(EdgeType.getEdgeType(typeString));
					edge.setForce(SchemeForce.getSchemeForce(forceString));
					edge.setStance(Stance.getStance(stanceString));
					edge.setFromNode(fromNode);
					edge.setToNode(toNode);

					fromNode.addOutgoingEdge(edge);
					toNode.addIncomingEdge(edge);
					edges.add(edge);
				} else {
					throw new Exception("Invalid diagram");
				}
			}

			WigmoreGraph graph = new WigmoreGraph(nodes, edges);
			return graph;

		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

}
