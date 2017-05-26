import java.io.File;
import java.util.ArrayList;

import org.w3c.dom.NodeList;


public class ParsingTotal {
	

	public static Complex[][] mainOperations(File EQFile, File SSHFile) {
		

		//Parse the EQ file
		//ParseEQ PEQ = new ParseEQ("MicroGridTestConfiguration_T1_BE_EQ_V2.xml"); 
		ParseEQ PEQ = new ParseEQ(EQFile); 
		//make an array for each category to store the data in 
		ArrayList<BaseVoltage> baseVoltageArray = PEQ.makeBaseVoltage();
		ArrayList<Substation> substationArray = PEQ.makeSubstation();
		ArrayList<VoltageLevel> voltageLevelArray = PEQ.makeVoltageLevel();
		ArrayList<GeneratingUnit> generatingUnitArray = PEQ.makeGeneratingUnit();
		ArrayList<SynchronousMachine> synchronousMachineArray = PEQ.makeSynchronousMachine();
		ArrayList<RegulatingControl> regulatingControlArray = PEQ.makeRegulatingControl();
		ArrayList<PowerTransformer> powerTransformerArray = PEQ.makePowerTransformer();
		ArrayList<EnergyConsumer> energyConsumerArray = PEQ.makeEnergyConsumer();
		ArrayList<PowerTransformerEnd> powerTransformerEndArray = PEQ.makePowerTransformerEnd();
		ArrayList<Breaker> breakerArray = PEQ.makeBreaker();
		ArrayList<RatioTapChanger> ratioTapChangerArray = PEQ.makeRatioTapChanger();
		ArrayList<ACLine> ACLineArray = PEQ.makeACLine();
		ArrayList<ConnectivityNode> ConnectivityNodeArray = PEQ.makeConnectivityNode();
		ArrayList<Terminal> TerminalArray = PEQ.makeTerminal();
		
		
		
		//Parse the SSH file and have it add on to the arrays
		//ParseSSH PSSH = new ParseSSH("MicroGridTestConfiguration_T1_BE_SSH_V2.xml"); 
		ParseSSH PSSH = new ParseSSH(SSHFile); 
		synchronousMachineArray = PSSH.makeSynchronousMachine(synchronousMachineArray);
		regulatingControlArray = PSSH.makeRegulatingControl(regulatingControlArray);
		energyConsumerArray = PSSH.makeEnergyConsumer(energyConsumerArray);
		breakerArray = PSSH.makeBreaker(breakerArray);
		ratioTapChangerArray = PSSH.makeRatioTapChanger(ratioTapChangerArray);
		System.out.println(""); 
		System.out.println(""); 
		System.out.println(""); 
		
		SQLdatabase TABLE = new SQLdatabase(); 
		TABLE.databaseCreate();
		//Creating base voltage table
		
		TABLE.BaseVoltageTableCreate( );
		for (int i=0;i<baseVoltageArray.size();i++)
		TABLE.BaseVoltageDataAdd(baseVoltageArray.get(i).rdfID, baseVoltageArray.get(i).nominalVal);
		
		//Creating substation table
		TABLE.SubstationTableCreate( );
		for (int i=0;i<substationArray.size();i++)
		TABLE.SubstationDataAdd(substationArray.get(i).rdfID, substationArray.get(i).name, substationArray.get(i).region);
		
		//Creating voltage level table
		TABLE.VoltageLevelTableCreate( );
		for (int i=0;i<voltageLevelArray.size();i++)
		TABLE.VoltageLevelDataAdd(voltageLevelArray.get(i).rdfID, voltageLevelArray.get(i).name, voltageLevelArray.get(i).substation_rdf, voltageLevelArray.get(i).baseVoltage_rdf );
		
		//Creating Generating Unit table
		TABLE.GeneratingUnitTableCreate( );
		for (int i=0;i<generatingUnitArray.size();i++)
		TABLE.GeneratingUnitDataAdd(generatingUnitArray.get(i).rdfID, generatingUnitArray.get(i).name, generatingUnitArray.get(i).maxP, generatingUnitArray.get(i).minP,generatingUnitArray.get(i).equipmentContainer_rdf  );
			
		//Creating Sync Machine table
		TABLE.SyncMachineTableCreate( );
		for (int i=0;i<synchronousMachineArray.size();i++)
		TABLE.SyncMachineDataAdd(synchronousMachineArray.get(i).rdfID, synchronousMachineArray.get(i).name, synchronousMachineArray.get(i).ratedS,synchronousMachineArray.get(i).P,synchronousMachineArray.get(i).Q, synchronousMachineArray.get(i).genUnit_rdf,synchronousMachineArray.get(i).regControl_rdf,synchronousMachineArray.get(i).equipmentContainer_rdf  );

		//Creating Regulating Control table
		TABLE.RegControlTableCreate( );
		for (int i=0;i<regulatingControlArray.size();i++)
		TABLE.RegControlDataAdd(regulatingControlArray.get(i).rdfID, regulatingControlArray.get(i).name, regulatingControlArray.get(i).targetValue);
		
		//Creating Power Transformer
		TABLE.PowerTransformerTableCreate( );
		for (int i=0;i<powerTransformerArray.size();i++)
		TABLE.PowerTransformerDataAdd(powerTransformerArray.get(i).rdfID, powerTransformerArray.get(i).name, powerTransformerArray.get(i).equipmentContainer_rdf);
		
		//Creating Energy Consumer table
		TABLE.EnergyConsumerTableCreate( );
		for (int i=0;i<energyConsumerArray.size();i++)
		TABLE.EnergyConsumerDataAdd(energyConsumerArray.get(i).rdfID, energyConsumerArray.get(i).name, energyConsumerArray.get(i).P, energyConsumerArray.get(i).Q,energyConsumerArray.get(i).equipmentContainer_rdf  );
		
		//Creating Power Transformer End table
		TABLE.PowerTransformerEndTableCreate( );
		for (int i=0;i<powerTransformerEndArray.size();i++)
		TABLE.PowerTransformerEndDataAdd(powerTransformerEndArray.get(i).rdfID, powerTransformerEndArray.get(i).name, powerTransformerEndArray.get(i).r,powerTransformerEndArray.get(i).x,powerTransformerEndArray.get(i).transformer_rdf, powerTransformerEndArray.get(i).baseVoltage_rdf );
				
		
		//Creating Breaker table
		TABLE.BreakerTableCreate( );
		for (int i=0;i<breakerArray.size();i++)
		TABLE.BreakerDataAdd(breakerArray.get(i).rdfID, breakerArray.get(i).name, breakerArray.get(i).state, breakerArray.get(i).equipmentContainer_rdf);
		
		//Creating Ratio Tap Changer table
		TABLE.RatioTapChangerTableCreate( );
		for (int i=0;i<ratioTapChangerArray.size();i++)
		TABLE.RatioTapChangerDataAdd(ratioTapChangerArray.get(i).rdfID, ratioTapChangerArray.get(i).name, ratioTapChangerArray.get(i).step);
				
		
		//System.out.println("check  "+powerTransformerEndArray.get(0).baseVoltage_rdf);
		
		//make matrices
		BuildConnectionMatrix BuildMat = new BuildConnectionMatrix();
		Complex[][] Ybus = BuildMat.makeYbus(TerminalArray, ConnectivityNodeArray, breakerArray, ACLineArray, powerTransformerEndArray);
		return Ybus;
		

		
	}

}
