package arg.tech.wigmore.enums;


public enum EvidenceSide {
	PROSECUTION{
		@Override
    	public String toString() {
			return "Prosecution";
		}
	}, 
	DEFENCE {
		@Override
    	public String toString() {
			return "Defence";
		}
	}, 
	WITNESS {
		@Override
    	public String toString() {
			return "Witness";
		}
	};
	
	public static EvidenceSide getEvidenceSide(String side) {
		switch (side) {
		case "Prosecution":
			return PROSECUTION;
		case "Defence":
			return DEFENCE;
		case "Witness":
			return WITNESS;
		default:
			break;
		}
		return null;
	}
}
