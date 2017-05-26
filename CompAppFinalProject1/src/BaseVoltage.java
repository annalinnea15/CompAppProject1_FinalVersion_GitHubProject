public class BaseVoltage extends Substation {

	double nominalVal;

	public BaseVoltage() {	
	}
	
	public BaseVoltage(String rdf,double nomVal) {
		//shortcut for making a base voltage
		rdfID = rdf;
		nominalVal = nomVal;
	}

}