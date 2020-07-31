package arg.tech.aif.enums;

public enum AnchoringScheme {
	ASSERT{
		@Override
    	public String toString() {
			return "Asserting";
		}
	}, 
	
	ARGUE{
		@Override
    	public String toString() {
			return "Arguing";
		}
	},
	
	TESTIFY{
		@Override
    	public String toString() {
			return "Testifying";
		}
	},
	
	CHALLENGING {
		@Override
    	public String toString() {
			return "Challenging";
		}
	},
	
	AGREE { 
		@Override
    	public String toString() {
			return "Agreeing";
		}
	};
	
	
	public static AnchoringScheme getAnchoringScheme(String schemeString) {
		switch (schemeString) {
		case "Asserting":
			return ASSERT;
		case "Arguing":
			return ARGUE;
		case "Testifying":
			return TESTIFY;
		case "Challenging":
			return CHALLENGING;
		case "Agreeing":
			return AGREE;
		default:
			break;
		}
		return ASSERT;
	}
}
