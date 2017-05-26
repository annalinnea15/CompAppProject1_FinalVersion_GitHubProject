public class RegulatingControl extends Substation {

	double targetValue;
	
	public RegulatingControl() {	
	}
	
	public RegulatingControl(String rdf,String Name) {
		//shortcut
		rdfID = rdf;
		name = Name;
	}
	
	public void setTarget(double t) {
		targetValue=t;
	}

}