package arg.tech.wigmore.enums;


public enum EvidenceType { 
	TESTIMONIAL {
		@Override
    	public String toString() {
			return "Testimonial";
		}
	}, 
	CIRCUMSTANTIAL {
		@Override
    	public String toString() {
			return "Circumstantial";
		}
	}, 
	EXPLANATORY{
		@Override
    	public String toString() {
			return "Explanatory";
		}
	}, 
	CORROBORATIVE{
		@Override
    	public String toString() {
			return "Corroborative";
		}
	};
	

	public static EvidenceType getEvidenceType(String type) {

		switch (type) {
		case "Circumstantial":
			return CIRCUMSTANTIAL;
		case "Testimonial":
			return TESTIMONIAL;
		case "Corroborative":
			return CORROBORATIVE;
		case "Explanatory":
			return EXPLANATORY;
		default:
			break;
		}
		return null;

	}
}


