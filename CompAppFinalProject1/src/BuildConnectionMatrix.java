import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
//import java.io.*;
//import java.util.*;

public class BuildConnectionMatrix{
	double[][] connMatrix; //connection matrix with impedance values
	ArrayList<List<Integer>> connOnes = new ArrayList<List<Integer>>();
	public static ArrayList<Integer> paths = new ArrayList<Integer>(); // array to store intermediate paths
	
	//ArrayList<List<Integer>> connectionsAsInts = new ArrayList<List<Integer>>();

	//take in all Connectivity Nodes and mainly use their name (and rdf:ID afterwards)
	//identify main nodes by their name: anything that starts with "BE-Busbar"
		//This might not be as modular as I would like, another option could be to use anything that also has a shortName or description (must be parsed in)
		//However, this also includes connectivity Nodes that are named something like N1230992288 <--must clarify what these mean, ask in class
	
	//loop through the connectivity nodes and define which ones are main
	//Now read all the Terminals. An attribute of each terminal is what connectivity node it is connected to
	//The terminals also have an attribute called "ConductingEquipment". This attribute is associated with 2 terminals (when only considering transformers and ACLines)
	//FInd the other terminal it is connected to, then pull the connectivity node of that terminal
	//Those two connectivity nodes are thus connected together
	//The resistance/reactance of the COnductingEquipment can be placed in a matrix of the connection nodes
	
	
	
///////////////////////////////////////////////////////////////////////////////
//        MAKE THE FINAL YBUS
	public Complex[][] makeYbus(ArrayList<Terminal> Terminals, ArrayList<ConnectivityNode> Connections, ArrayList<Breaker> Breakers, ArrayList<ACLine> ACLines, ArrayList<PowerTransformerEnd> powerTransformerEnds, ArrayList<BaseVoltage> BaseVoltage) {
		//get the main nodes
		ArrayList<Integer> mainNodes = findMainNodes(Connections); //find the main busbars of the system
		ArrayList<List<Integer>> connOnes= makeConnOnes(Terminals,Connections,Breakers,ACLines,powerTransformerEnds);//how are all the connectivity nodes connected
		Complex[][] connMatrix= makeConnMatrix(Terminals, Connections, Breakers, ACLines,powerTransformerEnds, BaseVoltage); //what are the impedances between connectivity nodes
		ArrayList<List<Integer>> connOnesClone = connOnes;//make a copy of connOnes
		ArrayList<Integer> paths= findAllPaths(connOnesClone,mainNodes);//find the paths between main busbars
		connOnes= makeConnOnes(Terminals,Connections,Breakers,ACLines,powerTransformerEnds);//remake connOnes
		ArrayList<Integer> pathClone=paths; //copy paths
		
		
		Complex[][] Ybus = new Complex[mainNodes.size()][mainNodes.size()];
		while(pathClone.size()>0){
			ArrayList<Integer> onePath=new ArrayList<Integer>();
			while(pathClone.get(0) != -1){
				onePath.add(pathClone.get(0));//find a section of the path
				pathClone.remove(0);
			}
			pathClone.remove(0);
			if(mainNodes.contains(onePath.get(0)-1)==false || mainNodes.contains(onePath.get(onePath.size()-1)-1)==false){
				onePath.subList(0, onePath.size()).clear(); //clear the whole path
			}
			
			for(int p=0; p<onePath.size()-1; p++){
				//go through the path and add to the Ybus matrix
				if(Ybus[onePath.get(0)-1][onePath.get(onePath.size()-1)-1]==null){
					Ybus[onePath.get(0)-1][onePath.get(onePath.size()-1)-1]=new Complex(0,0);
				}
				if(Ybus[onePath.get(onePath.size()-1)-1][onePath.get(0)-1]==null){
					Ybus[onePath.get(onePath.size()-1)-1][onePath.get(0)-1]=new Complex(0,0);
				}
				
				Ybus[onePath.get(0)-1][onePath.get(onePath.size()-1)-1]=
						Ybus[onePath.get(0)-1][onePath.get(onePath.size()-1)-1].plus(connMatrix[onePath.get(p)-1][onePath.get(p+1)-1]);
				
				Ybus[onePath.get(onePath.size()-1)-1][onePath.get(0)-1]=
						Ybus[onePath.get(onePath.size()-1)-1][onePath.get(0)-1].plus(connMatrix[onePath.get(p+1)-1][onePath.get(p)-1]);
				
			}
		}
		
		//add the components together to get the Ybus diagonal
		for(int y=0; y<mainNodes.size(); y++){
			for(int yy=0; yy<mainNodes.size(); yy++){
				if(y!=yy){
					if(Ybus[y][y]==null){
						Ybus[y][y]=new Complex(0,0);
					}
					if(Ybus[y][yy]==null){
						Ybus[y][yy]=new Complex(0,0);
					}
					Complex pos = Ybus[y][yy].scale(-1);
					Ybus[y][y]=Ybus[y][y].plus(pos); //add the admittance values together (positive)
				}
			}
			
			//only add a diagonal element if there are non-zero elements in that row
			boolean testRow = false;
			for(int temp=0; temp<mainNodes.size(); temp++){
				if(Ybus[y][temp].re !=0 || Ybus[y][temp].im !=0){
					testRow=true; //if any of the row values are non-zero, there should be a diagonal element
				}
			}
			if(testRow){
				Ybus[y][y]=Ybus[y][y].plus(connMatrix[mainNodes.get(y)][mainNodes.get(y)]); //include the ysh, diagonal elements
			}
		}
		
		
		//print the Ybus matrix, just to check
		for (int row=0; row<mainNodes.size(); row++) //Loop to display the connection matrix
		{
		    for (int col=0; col<mainNodes.size(); col++) 
		    {
		        System.out.print(Ybus[row][col].toString()+" ");
		    }
		    System.out.println();
		}	
		
		return Ybus;
	}
	
	
	
//////////////////////////////////////////////////////////////////////////////////////
	//				make array of all paths
	public static ArrayList<Integer> findAllPaths(ArrayList<List<Integer>> connOnesClone, ArrayList<Integer> mainNodes){
	int pos=0;
	ArrayList<Integer> paths=new ArrayList<Integer>();
	for(int i1=0;i1<mainNodes.size();i1++) //loop to cycle through each main bus
	{
			while(connOnesClone.get(mainNodes.get(i1)).contains(1)) //condition to ensure all connections to main node are considered
			{
				
				paths.add(mainNodes.get(i1));
				//paths.add(Arrays.asList
				//paths.add(mainNodes.get(i1)); //adding the first element (main bus) to path
				pos= connOnesClone.get(mainNodes.get(i1)).indexOf(1); //finding the connection to other nodes by searching '1' in connection matrix
				connOnesClone.get(pos).set(mainNodes.get(i1), 0); //once found, reset the connection value to '0' to indicating path traced!
				paths=find_paths(pos, connOnesClone, mainNodes, paths);
				connOnesClone.get(mainNodes.get(i1)).set(pos, 0);//set the immediate element (from requested main bus) of formed network to zero
				paths.add(-2); //add a delimiter in paths array
			}
	}
	
	for(int l=0; l< paths.size(); l++)
	paths.set(l, paths.get(l)+1);
	System.out.println(paths); //display path matrix
	return paths;
}
	
///////////////////////////////////////////////////////////////////////////////
//				FIND THE PATHS BETWEEN MAIN BUSES
	private static ArrayList<Integer> find_paths(int pos, ArrayList<List<Integer>> connOnesClone, ArrayList<Integer> mainNodes, ArrayList<Integer> paths) //function to find the network till connected main bus is found
	{
		int k=0; //variable used as a trace from one node to other 
		if(mainNodes.contains(pos)) //check if requested node is main bus
		{
			paths.add(pos); //add the main bus to the array paths
			return paths;
		}
		else
		{
			int a=connOnesClone.get(pos).indexOf(1); //get the next node connection
			paths.add(pos); //add the next node to the path
			if(a==-1){
				return paths;
				
			}
			connOnesClone.get(a).set(pos, 0); //ensure traced connection is set to zero
			connOnesClone.get(pos).set(a, 0); //ensure traced connection is set to zero
			if(mainNodes.contains(a)) //check if main node is traced
				paths.add(a);
			else
			{
				k=a; //remember the previous node
				{
					do
					{
						paths.add(k);
						int y=k; //store k in temp variable y
						k=connOnesClone.get(k).indexOf(1); //check for next node connection
						if(k==-1){
							int prev = paths.lastIndexOf(-1);
							paths.subList(prev+1,paths.size()).clear();
							break;
						}
						connOnesClone.get(k).set(y, 0); //ensure traced connection is set to zero
						connOnesClone.get(y).set(k, 0); //ensure traced connection is set to zero
						if (mainNodes.contains(k)) //check if the node is a main bus
							break;			
					}while(!paths.contains(k)); //continue till the node is a main bus
				}

				paths.add(k);
			}
			return paths;
		}
	}


	///////////////////////////////////////////////////////////////////////////////
	//        FIND THE MAIN NODES OF THE SYSTEM
	public static ArrayList<Integer> findMainNodes(ArrayList<ConnectivityNode> Connections){
		//find the main buses in the system
	
		ArrayList<Integer> mainNodes = new ArrayList<Integer>();
		for(int c=0; c<Connections.size(); c++){ //cycle through all the connectivity nodes
			String name=Connections.get(c).name.toString();
			
			if(name.startsWith("BE-Busbar", 0)){//add node index to mainNodes
				//node is a main busbar
				mainNodes.add(c);
			}
	}
		return mainNodes;
	}
	
	
///////////////////////////////////////////////////////////////////////////////
//        MAKE THE NODE CONNECTION ARRAY
	public static ArrayList<List<Integer>> makeConnOnes(ArrayList<Terminal> Terminals, ArrayList<ConnectivityNode> Connections, ArrayList<Breaker> Breakers, ArrayList<ACLine> ACLines, ArrayList<PowerTransformerEnd> powerTransformerEnds) {
		// cycle through connectivity nodes and find all terminals
		
		//initialize ones matrix
		ArrayList<List<Integer>> connOnes = new ArrayList<List<Integer>>();
		
		//cycle through each connectivity node
		for(int c=0; c<Connections.size(); c++){
			
			//initiate the array as having all zeros
			Integer[] data = new Integer[Connections.size()];
			Arrays.fill(data, 0);
			connOnes.add(Arrays.asList(data));
							
			String id=Connections.get(c).rdfID.toString(); //********First connectivityNode i
			
			//cycle through the terminals
			for(int j=0; j<Terminals.size(); j++){
				String connRdfID = Terminals.get(j).connNode_rdf.toString();
				if(connRdfID.equals(id)){//if the terminal has the connectivity node      *****First terminal HAS connectivityNode i
					//read conducting equipment
					String condEq = Terminals.get(j).condEq_rdf.toString();//             *****First terminal HAS conductingEquipment j
					for(int jj=0; jj<Terminals.size(); jj++){ //look at remaining terminals to find same conducting equipment
						//read conducting equipment
						String condEQ2=Terminals.get(jj).condEq_rdf.toString();
						if(condEq.equals(condEQ2) && j!=jj){//    *****Second terminal HAS conductingEquipment jj
							//connectivity nodes are connected                            *****Second terminal HAS connectivityNode
							String connRdfID2 = Terminals.get(jj).connNode_rdf.toString(); //Second connectivityNode
							for(int cc=0; cc<Connections.size(); cc++){
								//read rdfID
								String id2=Connections.get(cc).rdfID.toString(); //       ********Second connectivityNode ii
								if(id2.equals(connRdfID2)){//the two nodes are connected
									connOnes.get(c).set(cc,1);
									
									//however, if the conducting equipment is an open breaker, there isn't a connection
									for(int bb=0; bb<Breakers.size(); bb++){
										boolean state = Breakers.get(bb).state;
										String breakerID = Breakers.get(bb).rdfID;
										if(breakerID.equals(condEq)){//the conducting equipment is a breaker
											if(state){//the breaker is open
												connOnes.get(c).set(cc,0);//reset the connection to zero
											}
										}
									}
								}
							}
						}
					}
				}	
			}
		}
		//print out connection node matrix
//		for (List<Integer> list : connOnes) //Loop to display the connection matrix
//		{
//		    for (Integer k : list) 
//		    {
//		        System.out.print(k+" ");
//		    }
//		    System.out.println();
//		}
		
		return connOnes;
	}
	
	
	///////////////////////////////////////////////////////////////////////////////
	//				MAKE THE NODE CONNECTION Matrix
	public static Complex[][] makeConnMatrix(ArrayList<Terminal> Terminals, ArrayList<ConnectivityNode> Connections, ArrayList<Breaker> Breakers, ArrayList<ACLine> ACLines, ArrayList<PowerTransformerEnd> powerTransformerEnds, ArrayList<BaseVoltage> BaseVoltage) {
		// cycle through connectivity nodes and find all terminals

		//initialize matrix
		Complex[][] connMatrix = new Complex[Connections.size()][Connections.size()];

		//cycle through each connectivity node
		for(int c=0; c<Connections.size(); c++){

			String id=Connections.get(c).rdfID.toString(); //********First connectivityNode i

			//cycle through the terminals
			for(int j=0; j<Terminals.size(); j++){
				String connRdfID = Terminals.get(j).connNode_rdf.toString();
				if(connRdfID.equals(id)){//if the terminal has the connectivity node      *****First terminal HAS connectivityNode i
					//read in conducting equipment
					String condEq = Terminals.get(j).condEq_rdf.toString();//             *****First terminal HAS conductingEquipment j
					for(int jj=0; jj<Terminals.size(); jj++){ //look at remaining terminals to find same conducting equipment
						//read second conducting equipment
						String condEQ2=Terminals.get(jj).condEq_rdf.toString();
						if(condEq.equals(condEQ2) && j!=jj){//                            *****Second terminal HAS conductingEquipment jj
							//connectivity nodes are connected                            *****Second terminal HAS connectivityNode
							String connRdfID2 = Terminals.get(jj).connNode_rdf.toString(); //Second connectivityNode
							for(int cc=0; cc<Connections.size(); cc++){
								String id2=Connections.get(cc).rdfID.toString(); //       ********Second connectivityNode ii
								if(id2.equals(connRdfID2)){
									//nodes are connected
									Complex[][] ynew = makeImpedanceArray(ACLines, powerTransformerEnds,condEq, BaseVoltage);//there is a connection between the nodes
									connMatrix[c][cc]=ynew[0][0].scale(-1);
									
									if(connMatrix[c][c]==null){
										connMatrix[c][c]=new Complex(0,0); //value hasn't been initiated yet, must set as zero so next line is ok
									}
									
									connMatrix[c][c]=connMatrix[c][c].plus(ynew[0][1]);
											
											
									//however, if the conducting equipment is an open breaker, there isn't a connection
									for(int bb=0; bb<Breakers.size(); bb++){
										boolean state = Breakers.get(bb).state;
										String breakerID = Breakers.get(bb).rdfID;
										if(breakerID.equals(condEq)){//the conducting equipment is a breaker
											if(state){//the breaker is open
												connMatrix[c][cc]=new Complex(0,0);//reset the connection to zero
											}
										}
									}
								}
							}
						}
					}
				}	
			}
		}
//		print out connection node matrix
		for (int m=0; m<Connections.size(); m++) //Loop to display the connection matrix
		{
		    for (int n=0; n<Connections.size(); n++) 
		    {
		    	if(connMatrix[m][n]==null){
		    		connMatrix[m][n]=new Complex(0,0);
		    	}
		    }
		}
		return connMatrix;
	}

	
///////////////////////////////////////////////////////////////////////////////
//               FIND THE IMPEDANCE/ADMITTANCE OF EACH CONNECTION
	public static Complex[][] makeImpedanceArray(ArrayList<ACLine> ACLines, ArrayList<PowerTransformerEnd> powerTransformerEnds, String condEqRDF, ArrayList<BaseVoltage> BaseVoltage) {
		double basePower = 1000;//hard coded, could be added to GUI
		
		Complex Y=new Complex(0,0);
		Complex yy=new Complex(0,0);
		
		//loop through ACLines
		for(int a=0; a<ACLines.size(); a++){
			String AC_RDF = ACLines.get(a).rdfID;
			String AC_BV_RDF = ACLines.get(a).BV_rdfID;
			double AC_r = ACLines.get(a).r;//resistance
			double AC_x = ACLines.get(a).x;//reactance
			double AC_g = ACLines.get(a).g /2   ; //line admittance
			double AC_b = ACLines.get(a).b /2   ;
			
			//find the base voltage
			double ACbaseVoltage=1;
			for(int bv=0; bv<BaseVoltage.size(); bv++){
				if(AC_BV_RDF.equalsIgnoreCase(BaseVoltage.get(bv).rdfID)){
					ACbaseVoltage = BaseVoltage.get(bv).nominalVal;
				}
			}
			double ACbaseImpedance = ACbaseVoltage*ACbaseVoltage/basePower;
			
			
			//Y=1/(r+jx)
			if(AC_RDF.equals(condEqRDF)){
				//Y = Y+1/(AC_r+i*AC_x); //if there is an ACLine, add it to the Y bus value
				Complex Z_AC = new Complex(AC_r/ACbaseImpedance, AC_x/ACbaseImpedance);
				Complex gb = new Complex(AC_g*ACbaseImpedance, AC_b*ACbaseImpedance);
				yy=yy.plus(gb);
				Y=Y.plus(Z_AC.reciprocal());
			}
		}
		
		//loop through transformers
		double rr=0;
		double xx=0;
		double gg=0;
		double bb=0;
		double PTbaseImpedance=1;//just in case
		for(int t=0; t<powerTransformerEnds.size(); t++){
			//String PT_RDF = powerTransformerEnds.get(t).rdfID;
			String PT_condEQ = powerTransformerEnds.get(t).transformer_rdf;
			String PT_BV_RDF = powerTransformerEnds.get(t).baseVoltage_rdf;
			double PT_r = powerTransformerEnds.get(t).r;//resistance
			double PT_x = powerTransformerEnds.get(t).x;//reactance
			double PT_g = powerTransformerEnds.get(t).g;//resistance
			double PT_b = powerTransformerEnds.get(t).b;//reactance
			
			
			if(PT_condEQ.equals(condEqRDF)){
				//find the base voltage
				if(PT_r!=0 && PT_x!=0){//correct side of transformer
					
					//find the base voltage
					double PTbaseVoltage=1;
					for(int bv=0; bv<BaseVoltage.size(); bv++){
						if(PT_BV_RDF.equalsIgnoreCase(BaseVoltage.get(bv).rdfID)){
							PTbaseVoltage = BaseVoltage.get(bv).nominalVal;
						}
					}
					PTbaseImpedance = PTbaseVoltage*PTbaseVoltage/basePower;
				}
				
				
				rr = rr+PT_r;
				xx = xx+PT_x;
				gg = gg+PT_g;
				bb = bb+PT_b;
			}
		}
		//if there was any transformer data, add it to the Y bus value
		if(rr!=0 || xx!=0 || gg!=0 || bb!=0){//there was some transformer data
			//Y=Y+1/(rr+i*xx);//admittance
			Complex Z_PT = new Complex(rr/PTbaseImpedance,xx/PTbaseImpedance);
			Complex ggbb = new Complex(gg*PTbaseImpedance,bb*PTbaseImpedance);
			Y=Y.plus(Z_PT.reciprocal());
			yy=yy.plus(ggbb);
		}
		
		//return the 1/Z and ysh values
		Complex[][] Yvals = new Complex[1][2];
		Yvals[0][0]=Y;
		Yvals[0][1]=yy;
		return Yvals;
		
	}

}
