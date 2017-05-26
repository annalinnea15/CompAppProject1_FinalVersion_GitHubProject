public class Breaker extends Substation {

	Boolean state;
	String equipmentContainer_rdf;
	
	public Breaker() {	
	}
	
	public Breaker(String rdf,String Name, String equip) {
		//shortcut
		rdfID = rdf;
		name = Name;
		equipmentContainer_rdf=equip;
	}
	
	public void setState(Boolean b) {	
		state=b;
	}

}