public class ACLine extends Substation {

	double r;
	double x;
	double g;
	double b;
	
	public ACLine() {	
	}
	
	public ACLine(String rdf,String Name, double R, Double X, double G, double B) {
		//shortcut
		rdfID = rdf;
		name = Name;
		r=R;
		x=X;
		g=G;
		b=B;
		
	}

}