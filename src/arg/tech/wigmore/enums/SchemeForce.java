package arg.tech.wigmore.enums;


public enum SchemeForce {
	NO_FORCE{
		@Override
    	public String toString() {
			return "no_force";
		}
	}, 
	STRONG {
		@Override
    	public String toString() {
			return "strong";
		}
	}, 
	DOUBT{
		@Override
    	public String toString() {
			return "doubt";
		}
	};
	
	public static SchemeForce getSchemeForce(String force) {
		switch (force) {
		case "strong":
			return SchemeForce.STRONG;
		case "no_force":
			return SchemeForce.NO_FORCE;
		case "doubt":
			return SchemeForce.DOUBT;
		default:
			break;
		}
		return null;
	}

}