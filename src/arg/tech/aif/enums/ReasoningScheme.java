package arg.tech.aif.enums;


public enum ReasoningScheme {
	
	WITNESS_TESTIMONY {
		@Override
    	public String toString() {
			return "Witness Testimony";
		}
	},
	
	DEFAULT_INFERENCE {
		@Override
    	public String toString() {
			return "Default Inference";
		}
	},
	
	CORROBORATIVE_EFFECT {
		@Override
    	public String toString() {
			return "Corroborative Effect";
		}
	};
	
	
	
	public static ReasoningScheme getReasoningScheme(String schemeString) {
		switch (schemeString) {
		case "Witness Testimony":
			return WITNESS_TESTIMONY;
		case "Default Inference":
			return DEFAULT_INFERENCE;
		case "Corroborative Effect":
			return CORROBORATIVE_EFFECT;
		default:
			break;
		}
		return DEFAULT_INFERENCE;
	}
}
