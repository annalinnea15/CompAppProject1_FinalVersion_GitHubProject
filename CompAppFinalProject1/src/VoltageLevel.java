
public class VoltageLevel extends Substation {

	String substation_rdf;
	String baseVoltage_rdf;

	public VoltageLevel() {	
	}
	
	public VoltageLevel(String rdf, String Name, String sub_rdf,String baseV_rdf) {
		//shortcut for making a base voltage
		rdfID = rdf;
		name = Name;
		substation_rdf = sub_rdf;
		baseVoltage_rdf = baseV_rdf;
	}

}