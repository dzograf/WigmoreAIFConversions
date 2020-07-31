package arg.tech.wigmore.enums;


public enum EvidenceForce {
	NO_FORCE{
		@Override
    	public String toString() {
			return "no_force";
		}
	}, 
	BELIEF{
		@Override
    	public String toString() {
			return "belief";
		}
	}, 
	DISBELIEF{
		@Override
    	public String toString() {
			return "disbelief";
		}
	}, 
	STRONG_BELIEF {
		@Override
    	public String toString() {
			return "strong Belief";
		}
	}, 
	STRONG_DISBELIEF {
		@Override
    	public String toString() {
			return "strong didbelief";
		}
	}, 
	DOUBT{
		@Override
    	public String toString() {
			return "doubt";
		}
	};
	
	public static EvidenceForce getEvidenceForce(String force) {
		switch (force) {
		case "belief":
			return BELIEF;
		case "strong_belief":
			return STRONG_BELIEF;
		case "disbelief":
			return DISBELIEF;
		case "strong_disbelief":
			return STRONG_DISBELIEF;
		case "no_force":
			return NO_FORCE;
		case "doubt":
			return DOUBT;
		default:
			break;
		}
		return null;
	}
}