import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;


public class ParseSSH {
	Document doc;

	//public ParseSSH(String fileName) {
	public ParseSSH(File SSHFile) {
		try{
			//parse the EQ XML file (make sure it is imported into the folder)
			//File SSHFile = new File(fileName);
			//File EQFile = new File("MicroGridTestConfiguration_T1_BE_EQ_V2.xml");
			
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(SSHFile);
			
			// normalize CIM XML file
			// read in CIM objects (Node names)
			doc.getDocumentElement().normalize();

			
		}
		catch(Exception e){
			e.printStackTrace();
			System.out.println("oops");
			//return null;
			}
	}
	
	public ArrayList<SynchronousMachine> makeSynchronousMachine(ArrayList<SynchronousMachine> synchronousMachineArray){
		System.out.println("Node: Synchronous Machines" );
		NodeList synchMach = doc.getElementsByTagName("cim:SynchronousMachine");
		//ArrayList<SynchronousMachine> synchronousMachineArray = new ArrayList<SynchronousMachine>();
		for (int i=0; i<synchMach.getLength(); i++){
			Node node=synchMach.item(i);
			String[] cim=extractNode(node);
			synchronousMachineArray.get(i).setPQ(Double.parseDouble(cim[0]), Double.parseDouble(cim[1]));
		}
		return synchronousMachineArray;
	}
	
	public ArrayList<RegulatingControl> makeRegulatingControl(ArrayList<RegulatingControl> regulatingControlArray){
		System.out.println("Node: Regulating Control" );
		NodeList regCon = doc.getElementsByTagName("cim:RegulatingControl");
		//ArrayList<RegulatingControl> regulatingControlArray = new ArrayList<RegulatingControl>();
		for (int i=0; i<regCon.getLength(); i++){
			Node node=regCon.item(i);
			String[] cim=extractNode(node);
			regulatingControlArray.get(i).setTarget(Double.parseDouble(cim[2]));;
		}
		return regulatingControlArray;
	}
	
	public ArrayList<EnergyConsumer> makeEnergyConsumer(ArrayList<EnergyConsumer> energyConsumerArray){
		System.out.println("Node: Energy Consumer" );
		NodeList loadList = doc.getElementsByTagName("cim:EnergyConsumer");
		//ArrayList<EnergyConsumer> energyConsumerArray = new ArrayList<EnergyConsumer>();
		for (int i=0; i<loadList.getLength(); i++){
			Node node=loadList.item(i);
			String[] cim=extractNode(node);
			energyConsumerArray.get(i).setPQ(Double.parseDouble(cim[3]), Double.parseDouble(cim[4]));
		}
		return energyConsumerArray;
	}
	
	public ArrayList<Breaker> makeBreaker(ArrayList<Breaker> breakerArray){
		System.out.println("Node: Breaker" );
		NodeList breakerList = doc.getElementsByTagName("cim:Breaker");
		//ArrayList<Breaker> breakerArray = new ArrayList<Breaker>();
		for (int i=0; i<breakerList.getLength(); i++){
			Node node=breakerList.item(i);
			String[] cim=extractNode(node);
			breakerArray.get(i).setState(Boolean.parseBoolean(cim[5]));
		}
		return breakerArray;
	}
	
	public ArrayList<RatioTapChanger> makeRatioTapChanger(ArrayList<RatioTapChanger> ratioTapChangerArray){
		System.out.println("Node: Ratio Tap Changer" );
		NodeList tap = doc.getElementsByTagName("cim:RatioTapChanger");
		//ArrayList<RatioTapChanger> ratioTapChangerArray = new ArrayList<RatioTapChanger>();
		for (int i=0; i<tap.getLength(); i++){
			Node node=tap.item(i);
			String[] cim=extractNode(node);
			ratioTapChangerArray.get(i).setStep(Double.parseDouble(cim[6]));
		}
		return ratioTapChangerArray;
	}
		
			




public static String[] extractNode(Node node){
	//convert node to element in order to search for data inside it
	//  http://stackoverflow.com/questions/5895160/get-an-attribute-of-a-dom-node
	Element element = (Element)node;
	
	//read in all possible attributes for each node, some will be null
	String synchP = extractTag(element,"cim:RotatingMachine.p",true,false);
	String synchQ = extractTag(element,"cim:RotatingMachine.q",true,false);
	String target = extractTag(element,"cim:RegulatingControl.targetValue",true,false);
	String loadP = extractTag(element,"cim:EnergyConsumer.p",true,false);
	String loadQ = extractTag(element,"cim:EnergyConsumer.p",true,false);
	String state = extractTag(element,"cim:Switch.open",true,false);
	String step = extractTag(element,"cim:TapChanger.step",true,false);
	
	String[] attributeString={synchP, synchQ, target, loadP, loadQ, state, step};
	
	
	//------------------------------GET FROM SSH FILE----------------------------------------//
	//Breaker state in SSH file
	//RatioTapChanger step in SSH file
	//P,Q in SSH xml file
	//String P_sm = element.getElementsByTagName("cim:VoltageLevel.Substation").item(0).getAttributes().item(0).toString();
	//String Q_sm = element.getElementsByTagName("cim:VoltageLevel.Substation").item(0).getAttributes().item(0).toString();
	//RegulatingControl target value in SSH file
	
	
	//print out results, just for checking
	//if the answer is null (it isn't there), don't print
	//This ensures that only actual values (like in table 1 of the assignment) are shown
//	if (synchP != null) System.out.println("synchP = "+synchP);
//	if (synchQ != null) System.out.println("synchQ = "+synchQ);
//	if (target != null) System.out.println("target = "+target);
//	if (loadP != null) System.out.println("loadP = "+loadP);
//	if (loadQ != null) System.out.println("loadQ = "+loadQ);
//	if (state != null) System.out.println("state = "+state);
//	if (step != null) System.out.println("step = "+step);
//
//	System.out.println(" ");
	return attributeString;

	
}

		//extract the element and get the value of it
		public static String extractTag(Element element, String tag, boolean t, boolean sp){
		//convert node to element in order to search for data inside it
			//  http://stackoverflow.com/questions/11828560/null-pointer-exception-in-xml-parsing

			NodeList nlList = element.getElementsByTagName(tag); //get the element as a node list
			Node nValue = (Node) nlList.item(0); //convert it to a node for testing
			if(nValue == null) //if there isn't anything there (for example, Substation doesn't have a ratedS), return null
				return null;
	
			if(t) //if the content is numbers, not some rdf code
				return nlList.item(0).getTextContent();
	
			if(sp)// remove rdf:resource"# ..." to just report the actual code
				//                                            remove rdf:resource"#                                   remove final "
				return nValue.getAttributes().item(0).toString().substring(15,nValue.getAttributes().item(0).toString().length()-1);
	
			return nValue.getAttributes().item(0).toString(); //otherwise, return the result
    
}
}