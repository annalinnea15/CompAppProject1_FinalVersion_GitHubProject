
public class SynchronousMachine extends Substation {

	double ratedS;
	double P;
	double Q;
	String genUnit_rdf;
	String regControl_rdf;
	String equipmentContainer_rdf;
	

	public SynchronousMachine() {	
	}
	
	public SynchronousMachine(String rdf, String Name, double S, String gen, String reg,String equip_rdf) {
		//shortcut for making a base voltage
		rdfID=rdf;
		name=Name;
		genUnit_rdf=gen;
		regControl_rdf=reg;
		equipmentContainer_rdf = equip_rdf;
		ratedS=S;
	}
	
	public void setPQ(double pp, double qq) {
		P=pp;
		Q=qq;
		
	}

}