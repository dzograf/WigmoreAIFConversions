package arg.tech.aif.enums;

public enum NodeType { I, L, RA, CA, PA, YA, TA, MA;
	
	public static NodeType getNodeType(String typeString) {
		switch (typeString) {
		case "I":
			return I;
		case "L":
			return L;
		case "RA":
			return RA;
		case "CA":
			return CA;
		case "PA":
			return PA;
		case "YA":
			return YA;
		case "TA":
			return TA;
		case "MA":
			return MA;
		default:
			break;
		}
		
		return null;
	}

}
