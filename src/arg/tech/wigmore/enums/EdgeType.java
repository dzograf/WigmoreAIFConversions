package arg.tech.wigmore.enums;


public enum EdgeType {
	PROVISIONAL{
		@Override
    	public String toString() {
			return "provisional";
		}
	}, 
	EXPLANATORY_EDGE{
		@Override
    	public String toString() {
			return "explanatory edge";
		}
	}, 
	CORROBORATIVE_EDGE {
		@Override
    	public String toString() {
			return "corroborative edge";
		}
	};
	
	public static EdgeType getEdgeType(String type) {
		switch (type) {
		case "provisional":
			return PROVISIONAL;
		case "explanatory edge":
			return EXPLANATORY_EDGE;
		case "corroborative edge":
			return CORROBORATIVE_EDGE;
		default:
			break;
		}
		return null;
	}
}
