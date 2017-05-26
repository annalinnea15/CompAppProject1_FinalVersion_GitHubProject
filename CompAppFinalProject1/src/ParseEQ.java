

// import libraries needed to use DOM
import java.io.File;
//import java.util.ArrayList;

import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
//import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class ParseEQ {
	Document doc;

	//public ParseEQ(String fileName) {
	public ParseEQ(File EQFile) {
		try{
			//parse the EQ XML file (make sure it is imported into the folder)
			//File EQFile = new File(fileName);
			//File EQFile = new File("MicroGridTestConfiguration_T1_BE_EQ_V2.xml");
			
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(EQFile);
			
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
			

	//read in attributes for each node
	public ArrayList<BaseVoltage> makeBaseVoltage(){
		System.out.println("Node: Base Voltage" ); //which type are we looking at
		NodeList bvList = doc.getElementsByTagName("cim:BaseVoltage");//create NodeList of all Base Voltages
		ArrayList<BaseVoltage> baseVoltageArray = new ArrayList<BaseVoltage>();//create ArrayList to store them in
		for (int i=0; i<bvList.getLength(); i++){//go through each Base Voltage
			Node node=bvList.item(i);
			String[] cim=extractNode(node);//extract attributes for each node
			//                0      1        2            3      4 	   5	  6		7		8		9	   10		    11		    12    13     14	
			//String[] test={rdfID, name, nominalValue, region, sub_rdf, bv_rdf, maxP, minP, equipC, ratedS, genUnit_rdf, regCont_rdf, PT_r, PT_x, PT_rdf};
			baseVoltageArray.add(new BaseVoltage(cim[0], Double.parseDouble(cim[2])));//add to array
		}
		return baseVoltageArray;
	}
		
	public ArrayList<Substation> makeSubstation(){
		System.out.println("Node: Substation" );
		NodeList subList = doc.getElementsByTagName("cim:Substation");
		ArrayList<Substation> substationArray = new ArrayList<Substation>();
		for (int i=0; i<subList.getLength(); i++){
			Node node=subList.item(i);
			String[] cim=extractNode(node);
			substationArray.add(new Substation(cim[0], cim[1], cim[3]));
		}
		return substationArray;
	}
	
	public ArrayList<VoltageLevel> makeVoltageLevel(){
		System.out.println("Node: Voltage Level" );
		NodeList volLev = doc.getElementsByTagName("cim:VoltageLevel");
		ArrayList<VoltageLevel> voltageLevelArray = new ArrayList<VoltageLevel>();
		for (int i=0; i<volLev.getLength(); i++){
			Node node=volLev.item(i);
			String[] cim=extractNode(node);
			voltageLevelArray.add(new VoltageLevel(cim[0], cim[1], cim[4], cim[5]));
		}
		return voltageLevelArray;
	}
	
	public ArrayList<GeneratingUnit> makeGeneratingUnit(){
		System.out.println("Node: Generating Unit" );
		NodeList genList = doc.getElementsByTagName("cim:GeneratingUnit");
		ArrayList<GeneratingUnit> generatingUnitArray = new ArrayList<GeneratingUnit>();
		for (int i=0; i<genList.getLength(); i++){
			Node node=genList.item(i);
			String[] cim=extractNode(node);
			generatingUnitArray.add(new GeneratingUnit(cim[0], cim[1], Double.parseDouble(cim[6]), Double.parseDouble(cim[7]), cim[8]));
		}
		return generatingUnitArray;
	}
		
	public ArrayList<SynchronousMachine> makeSynchronousMachine(){
		System.out.println("Node: Synchronous Machines" );
		NodeList synchMach = doc.getElementsByTagName("cim:SynchronousMachine");
		ArrayList<SynchronousMachine> synchronousMachineArray = new ArrayList<SynchronousMachine>();
		for (int i=0; i<synchMach.getLength(); i++){
			Node node=synchMach.item(i);
			String[] cim=extractNode(node);
			synchronousMachineArray.add(new SynchronousMachine(cim[0], cim[1], Double.parseDouble(cim[9]), cim[10], cim[11], cim[8]));
		}
		return synchronousMachineArray;
	}
	
	public ArrayList<RegulatingControl> makeRegulatingControl(){
		System.out.println("Node: Regulating Control" );
		NodeList regCon = doc.getElementsByTagName("cim:RegulatingControl");
		ArrayList<RegulatingControl> regulatingControlArray = new ArrayList<RegulatingControl>();
		for (int i=0; i<regCon.getLength(); i++){
			Node node=regCon.item(i);
			String[] cim=extractNode(node);
			regulatingControlArray.add(new RegulatingControl(cim[0], cim[1]));
		}
		return regulatingControlArray;
	}
	
	public ArrayList<PowerTransformer> makePowerTransformer(){
		System.out.println("Node: Power Transformer" );
		NodeList powerXfmr = doc.getElementsByTagName("cim:PowerTransformer");
		ArrayList<PowerTransformer> powerTransformerArray = new ArrayList<PowerTransformer>();
		for (int i=0; i<powerXfmr.getLength(); i++){
			Node node=powerXfmr.item(i);
			String[] cim=extractNode(node);
			powerTransformerArray.add(new PowerTransformer(cim[0], cim[1], cim[8]));
		}
		return powerTransformerArray;
	}
	
	public ArrayList<EnergyConsumer> makeEnergyConsumer(){
		System.out.println("Node: Energy Consumer" );
		NodeList loadList = doc.getElementsByTagName("cim:EnergyConsumer");
		ArrayList<EnergyConsumer> energyConsumerArray = new ArrayList<EnergyConsumer>();
		for (int i=0; i<loadList.getLength(); i++){
			Node node=loadList.item(i);
			String[] cim=extractNode(node);
			energyConsumerArray.add(new EnergyConsumer(cim[0], cim[1], cim[8]));
		}
		return energyConsumerArray;
	}
	
	public ArrayList<PowerTransformerEnd> makePowerTransformerEnd(){
		System.out.println("Node: Power Transformer End" );
		NodeList powerXfmrEnd = doc.getElementsByTagName("cim:PowerTransformerEnd");
		ArrayList<PowerTransformerEnd> powerTransformerEndArray = new ArrayList<PowerTransformerEnd>();
		for (int i=0; i<powerXfmrEnd.getLength(); i++){
			Node node=powerXfmrEnd.item(i);
			String[] cim=extractNode(node);
			powerTransformerEndArray.add(new PowerTransformerEnd(cim[0], cim[1], Double.parseDouble(cim[12]), 
					Double.parseDouble(cim[13]), cim[14], cim[20], Double.parseDouble(cim[21]), Double.parseDouble(cim[22])));
		}
		return powerTransformerEndArray;
	}
	
	public ArrayList<Breaker> makeBreaker(){
		System.out.println("Node: Breaker" );
		NodeList breakerList = doc.getElementsByTagName("cim:Breaker");
		ArrayList<Breaker> breakerArray = new ArrayList<Breaker>();
		for (int i=0; i<breakerList.getLength(); i++){
			Node node=breakerList.item(i);
			String[] cim=extractNode(node);
			breakerArray.add(new Breaker(cim[0], cim[1], cim[8]));
		}
		return breakerArray;
	}
	
	public ArrayList<RatioTapChanger> makeRatioTapChanger(){
		System.out.println("Node: Ratio Tap Changer" );
		NodeList tap = doc.getElementsByTagName("cim:RatioTapChanger");
		ArrayList<RatioTapChanger> ratioTapChangerArray = new ArrayList<RatioTapChanger>();
		for (int i=0; i<tap.getLength(); i++){
			Node node=tap.item(i);
			String[] cim=extractNode(node);
			ratioTapChangerArray.add(new RatioTapChanger(cim[0], cim[1]));
		}
		return ratioTapChangerArray;
	}
	
	public ArrayList<ACLine> makeACLine(){
		System.out.println("Node: ACLine" );
		NodeList line = doc.getElementsByTagName("cim:ACLineSegment");
		ArrayList<ACLine> ACLineArray = new ArrayList<ACLine>();
		for (int i=0; i<line.getLength(); i++){
			Node node=line.item(i);
			String[] cim=extractNode(node);
			ACLineArray.add(new ACLine(cim[0], cim[1], Double.parseDouble(cim[15]), 
					Double.parseDouble(cim[16]), Double.parseDouble(cim[23]), Double.parseDouble(cim[24])));
		}
		return ACLineArray;
	}
	
	public ArrayList<ConnectivityNode> makeConnectivityNode(){
		System.out.println("Node: Connectivity Node" );
		NodeList connNode = doc.getElementsByTagName("cim:ConnectivityNode");
		ArrayList<ConnectivityNode> ConnectivityNodeArray = new ArrayList<ConnectivityNode>();
		for (int i=0; i<connNode.getLength(); i++){
			Node node=connNode.item(i);
			String[] cim=extractNode(node);
			ConnectivityNodeArray.add(new ConnectivityNode(cim[0], cim[1]));
		}
		return ConnectivityNodeArray;
	}
	
	public ArrayList<Terminal> makeTerminal(){
		System.out.println("Node: Terminal" );
		NodeList term = doc.getElementsByTagName("cim:Terminal");
		ArrayList<Terminal> TerminalArray = new ArrayList<Terminal>();
		for (int i=0; i<term.getLength(); i++){
			Node node=term.item(i);
			String[] cim=extractNode(node);
			TerminalArray.add(new Terminal(cim[0], cim[1], cim[17], cim[19], cim[18]));
			System.out.println("Node: Terminal" );
		}
		return TerminalArray;
	}
		
			//extract attributes for each node
			public static String[] extractNode(Node node){
				//convert node to element in order to search for data inside it
				//  http://stackoverflow.com/questions/5895160/get-an-attribute-of-a-dom-node
				Element element = (Element)node;
				
				//read in all possible attributes for each node, some will be null
				String rdfID = element.getAttribute("rdf:ID");
				String name = extractTag(element,"cim:IdentifiedObject.name",true,false);
				String nominalValue = extractTag(element,"cim:BaseVoltage.nominalVoltage",true,false);
				String region = extractTag(element,"cim:Substation.Region",false,true);
				String sub_rdf = extractTag(element,"cim:VoltageLevel.Substation",false,true);
				String bv_rdf = extractTag(element,"cim:VoltageLevel.BaseVoltage",false,true);
				String maxP = extractTag(element,"cim:GeneratingUnit.maxOperatingP",true,false);
				String minP = extractTag(element,"cim:GeneratingUnit.minOperatingP",true,false);
				String equipC = extractTag(element,"cim:Equipment.EquipmentContainer",false,true);
				String ratedS = extractTag(element,"cim:RotatingMachine.ratedS",true,false);
				String genUnit_rdf = extractTag(element,"cim:RotatingMachine.GeneratingUnit",false,true);
				String regCont_rdf = extractTag(element,"cim:RegulatingCondEq.RegulatingControl",false,true);
				String PT_r = extractTag(element,"cim:PowerTransformerEnd.r",true,false);
				String PT_x = extractTag(element,"cim:PowerTransformerEnd.x",true,false);
				String PT_rdf = extractTag(element,"cim:PowerTransformerEnd.PowerTransformer",false,true);
				String PT_BVrdf = extractTag(element,"cim:TransformerEnd.BaseVoltage",false,true);
				String PT_g = extractTag(element,"cim:PowerTransformerEnd.g",true,false);
				String PT_b = extractTag(element,"cim:PowerTransformerEnd.b",true,false);
				
				String ACLine_r = extractTag(element,"cim:ACLineSegment.r",true,false);
				String ACLine_x = extractTag(element,"cim:ACLineSegment.x",true,false);
				String ACLine_g = extractTag(element,"cim:ACLineSegment.gch",true,false);
				String ACLine_b = extractTag(element,"cim:ACLineSegment.bch",true,false);
				
				String conNode_rdf = extractTag(element,"cim:Terminal.ConnectivityNode",false,true);
				String condEq_rdf = extractTag(element,"cim:Terminal.ConductingEquipment",false,true);
				String description = extractTag(element,"cim:IdentifiedObject.description",true,false);
				
				//output as String[]
				String[] attributeString={rdfID, name, nominalValue, region, sub_rdf, bv_rdf, maxP, minP, 
						equipC, ratedS, genUnit_rdf, regCont_rdf, PT_r, PT_x, PT_rdf, ACLine_r, ACLine_x, 
						conNode_rdf, condEq_rdf,description, PT_BVrdf, PT_g, PT_b, ACLine_g, ACLine_b};
				
				//------------------------------GET FROM SSH FILE----------------------------------------//
				//Breaker state in SSH file
				//RatioTapChanger step in SSH file
				//P,Q in SSH xml file
				//String P_sm = element.getElementsByTagName("cim:VoltageLevel.Substation").item(0).getAttributes().item(0).toString();
				//String Q_sm = element.getElementsByTagName("cim:VoltageLevel.Substation").item(0).getAttributes().item(0).toString();
				//RegulatingControl target value in SSH file
				
				

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

