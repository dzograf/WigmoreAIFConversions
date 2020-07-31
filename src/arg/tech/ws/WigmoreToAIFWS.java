package arg.tech.ws;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import arg.tech.aif.models.AIFGraph;
import arg.tech.parsers.WigmoreParser;
import arg.tech.translation.WigmoreToAIF;
import arg.tech.wigmore.models.WigmoreGraph;


@Path("/wigmoreToAIF")
public class WigmoreToAIFWS {
	JSONObject jsonObject;
	
	
	@POST
	@Produces("application/json")
	public Response wigToAIF(String jsonString) {
		
		jsonObject = new JSONObject();
		
		try {
			Object obj = new JSONParser().parse(jsonString);
			org.json.simple.JSONObject jsonObj = (org.json.simple.JSONObject) obj;
			WigmoreGraph wigGraph = WigmoreParser.parse(jsonObj);
			AIFGraph aifGraph = WigmoreToAIF.convert(wigGraph);
			String aifJSONString = aifGraph.toJSON();
			jsonObject.put("result", aifJSONString);
		} 

		catch (Exception e) {
			e.printStackTrace();
			jsonObject.put("result", "");
		}

		return Response.status(200).entity(jsonObject.toString()).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "*").header("Access-Control-Allow-Headers", "*").build();
	}
	

	
}
