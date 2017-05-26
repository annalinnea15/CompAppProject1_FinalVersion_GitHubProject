public class EnergyConsumer extends Substation {

	double P;
	double Q;
	String equipmentContainer_rdf;
	
	public EnergyConsumer() {	
	}
	
	public EnergyConsumer(String rdf,String Name, String equip) {
		//shortcut
		rdfID = rdf;
		name = Name;
		equipmentContainer_rdf=equip;
	}
	
	public void setPQ(double pp, double qq) {
		P=pp;
		Q=qq;
		
	}

}