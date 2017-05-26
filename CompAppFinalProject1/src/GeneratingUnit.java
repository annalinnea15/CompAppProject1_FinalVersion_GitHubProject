
public class GeneratingUnit extends Substation {

	double maxP;
	double minP;
	String equipmentContainer_rdf;

	public GeneratingUnit() {	
	}
	
	public GeneratingUnit(String rdf,String Name, double mxP, double mnP,String equip_rdf) {
		//shortcut for making a base voltage
		rdfID = rdf;
		name = Name;
		maxP=mxP;
		minP=mnP;
		equipmentContainer_rdf = equip_rdf;
	}

}