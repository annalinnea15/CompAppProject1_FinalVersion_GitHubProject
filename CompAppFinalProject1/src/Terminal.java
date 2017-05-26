public class Terminal extends Substation {

	String connNode_rdf;
	String description;
	String condEq_rdf;
	
	public Terminal() {	
	}
	
	public Terminal(String rdf,String Name, String connNode, String desc, String condEq) {
		//shortcut
		rdfID = rdf;
		name = Name;
		connNode_rdf=connNode;
		description=desc;
		condEq_rdf=condEq;

		
	}

}