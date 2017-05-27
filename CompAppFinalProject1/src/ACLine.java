public class ACLine extends Substation {

	double r;
	double x;
	double g;
	double b;
	String BV_rdfID;
	
	public ACLine() {	
	}
	
	public ACLine(String rdf,String Name, double R, Double X, double G, double B, String BV) {
		//shortcut
		rdfID = rdf;
		name = Name;
		r=R;
		x=X;
		g=G;
		b=B;
		BV_rdfID = BV;
		
	}

}