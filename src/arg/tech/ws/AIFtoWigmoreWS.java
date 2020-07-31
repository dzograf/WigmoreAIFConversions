package arg.tech.ws;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import arg.tech.aif.models.AIFGraph;
import arg.tech.parsers.AIFParser;
import arg.tech.parsers.WigmoreParser;
import arg.tech.translation.AIFtoWigmore;
import arg.tech.translation.WigmoreToAIF;
import arg.tech.wigmore.models.WigmoreGraph;

@Path("/AIFtoWigmore")
public class AIFtoWigmoreWS {
JSONObject jsonObject;
	
	
	@POST
	@Produces("application/json")
	public Response aifToWig(String jsonString) {
		
		jsonObject = new JSONObject();
		
		try {
			Object obj = new JSONParser().parse(jsonString);
			org.json.simple.JSONObject jsonObj = (org.json.simple.JSONObject) obj;
			AIFGraph aifGraph = AIFParser.parse(jsonObj);
			WigmoreGraph wigmoreGraph = AIFtoWigmore.convert(aifGraph);
			String wigmoreJSONString = wigmoreGraph.toJSON();
			jsonObject.put("result", wigmoreJSONString);
		} 

		catch (Exception e) {
			e.printStackTrace();
			jsonObject.put("result", "");
		}

		return Response.status(200).entity(jsonObject.toString()).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "*").header("Access-Control-Allow-Headers", "*").build();
	}
	

	
}
