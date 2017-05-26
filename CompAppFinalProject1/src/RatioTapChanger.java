public class RatioTapChanger extends Substation {

	double step;
	
	public RatioTapChanger() {	
	}
	
	public RatioTapChanger(String rdf,String Name) {
		//shortcut
		rdfID = rdf;
		name = Name;
	}
	
	public void setStep(double s) {	
		step=s;
	}

}