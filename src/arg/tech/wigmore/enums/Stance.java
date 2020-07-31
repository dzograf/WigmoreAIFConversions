package arg.tech.wigmore.enums;


public enum Stance {
	AFFIRMATORY{
		@Override
    	public String toString() {
			return "affirmatory";
		}
	}, 
	NEGATORY{
		@Override
    	public String toString() {
			return "negatory";
		}
	},
	NEUTRAL{
		@Override
    	public String toString() {
			return "neutral";
		}
	};
	
	public static Stance getStance(String stance) {
		switch (stance) {
		case "affirmatory":
			return Stance.AFFIRMATORY;
		case "negatory":
			return Stance.NEGATORY;
		case "neutral":
			return Stance.NEUTRAL;
		default:
			break;
		}
		return null;
	}
}
