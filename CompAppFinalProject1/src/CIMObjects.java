import java.util.ArrayList;


public class CIMObjects {
	
	
	ArrayList<BaseVoltage> baseVoltageArray;
	ArrayList<Substation> substationArray;
	ArrayList<VoltageLevel> voltageLevelArray;
	ArrayList<GeneratingUnit> generatingUnitArray;
	ArrayList<SynchronousMachine> synchronousMachineArray;
	ArrayList<RegulatingControl> regulatingControlArray;
	ArrayList<PowerTransformer> powerTransformerArray;
	ArrayList<EnergyConsumer> energyConsumerArray;
	ArrayList<PowerTransformerEnd> powerTransformerEndArray;
	ArrayList<Breaker> breakerArray;
	ArrayList<RatioTapChanger> ratioTapChangerArray;
	
	public CIMObjects() {	
	}
	
	
	public CIMObjects(ArrayList<BaseVoltage>  bV,	ArrayList<Substation> S, ArrayList<VoltageLevel> vL,
			ArrayList<GeneratingUnit> gU,	ArrayList<SynchronousMachine> sM,ArrayList<RegulatingControl> rC,
			ArrayList<PowerTransformer> pT,	ArrayList<EnergyConsumer> eC,ArrayList<PowerTransformerEnd> pTE,
			ArrayList<Breaker> b,ArrayList<RatioTapChanger> rTC) {
		//shortcut 
		baseVoltageArray=bV;
		 substationArray=S;
		 voltageLevelArray=vL;
		 generatingUnitArray=gU;
		 synchronousMachineArray=sM;
		 regulatingControlArray=rC;
		 powerTransformerArray=pT;
		 energyConsumerArray=eC;
		 powerTransformerEndArray=pTE;
		 breakerArray=b;
		 ratioTapChangerArray=rTC;
	}
}
