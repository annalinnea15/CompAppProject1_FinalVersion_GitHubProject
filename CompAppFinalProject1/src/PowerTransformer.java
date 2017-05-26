public class PowerTransformer extends Substation {

	String equipmentContainer_rdf;
	
	public PowerTransformer() {	
	}
	
	public PowerTransformer(String rdf,String Name, String equip) {
		//shortcut
		rdfID = rdf;
		name = Name;
		equipmentContainer_rdf=equip;
	}

}