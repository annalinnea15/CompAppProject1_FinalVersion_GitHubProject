
import java.sql.*;


public class SQLdatabase {
	//public static ParsingTotal PTOT = new main();
	// JDBC driver name and database URL
	 static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	 static final String DB_URL = "jdbc:mysql://localhost/";
	 static final String DISABLE_SSL = "?useSSL=false";
	 // Database credentials
	 static final String USER = "root";
	 static final String PASS = "root"; // insert the password to SQL server
	 public static Connection conn = null;
	 public static Statement stmt = null;
	 public static String sql;

	 public static void main(String[] args) {
		 databaseCreate();
	}

	 public static void databaseCreate(){
	 try{
	 // Register JDBC driver
	 Class.forName(JDBC_DRIVER);
	 // Open a connection
	 System.out.println("Connecting to SQL server...");
	 conn = DriverManager.getConnection(DB_URL+DISABLE_SSL, USER, PASS);
	// execute a query to create database
	 System.out.println("Creating database...");
	 stmt = conn.createStatement();
	 String sql = "CREATE DATABASE IF NOT EXISTS PowerSystem"; // create database Students
	 stmt.executeUpdate(sql);
	 System.out.println("Database created successfully...");
	 }catch(SQLException se){
	 //Handle errors for JDBC
	 se.printStackTrace();
	 }catch(Exception e){
	 //Handle errors for Class.forName
	 e.printStackTrace();}
	 System.out.println("Goodbye!");
	 }
	 
	//function to create table for Base voltage
	 public static void BaseVoltageTableCreate( )
	 {
	// Connect to the created database power system
		 try {
			conn = DriverManager.getConnection(DB_URL + "PowerSystem"+DISABLE_SSL, USER, PASS);
		 sql = "USE PowerSystem";
		 stmt.executeUpdate(sql);
		 sql= "SET foreign_key_checks = 0;"; //avoids warnings on table drop with foreign keys
		 stmt.executeUpdate(sql);
		 sql = "DROP TABLE IF EXISTS Base_Voltage"; 
		 stmt.executeUpdate(sql);
//		 sql= "SET foreign_key_checks = 1;"; //reactivates warnings on table drop with foreign keys
//		 stmt.executeUpdate(sql);
		 // create table base voltage with corresponding attributes
		 sql = "CREATE TABLE IF NOT EXISTS Base_Voltage(rdfID VARCHAR(50) NOT NULL, "
		 		+ "nominal_value double, PRIMARY KEY(rdfID))"; 
		 stmt.executeUpdate(sql) ; // execute query
		 System.out.println("Created Base Voltage table in given database successfully...");
		 } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 }
	 
	 //function to create table for substation
	 public static void SubstationTableCreate( )
	 {
		// Connect to the created database power system
		 try {
			conn = DriverManager.getConnection(DB_URL + "PowerSystem"+DISABLE_SSL, USER, PASS);
		 sql = "USE PowerSystem";
		 stmt.executeUpdate(sql);
		 sql= "SET foreign_key_checks = 0;";//avoids warnings on table drop with foreign keys
		 stmt.executeUpdate(sql);
		 sql = "DROP TABLE IF EXISTS Substation";
		 stmt.executeUpdate(sql);
//		 sql= "SET foreign_key_checks = 1;";//reactivates warnings on table drop with foreign keys
//		 stmt.executeUpdate(sql);
		 // create table substation with corresponding attributes
		 sql = "CREATE TABLE IF NOT EXISTS Substation(rdfID VARCHAR(50) NOT NULL, "
		 		+ "Name VARCHAR(50),Region VARCHAR(50), PRIMARY KEY(rdfID))";
		 stmt.executeUpdate(sql) ; // execute query
		 System.out.println("Created Substation table in given database successfully...");
		 } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 }
	 
	//function to create table for voltage level
	 public static void VoltageLevelTableCreate( )
	 {
	// Connect to the created database power system 
		 try {
			conn = DriverManager.getConnection(DB_URL + "PowerSystem"+DISABLE_SSL, USER, PASS);
		
		 sql = "USE PowerSystem";
		 stmt.executeUpdate(sql);
		 sql= "SET foreign_key_checks = 0;";//avoids warnings on table drop with foreign keys
		 stmt.executeUpdate(sql);
		 sql = "DROP TABLE IF EXISTS Voltage_Level";
		 stmt.executeUpdate(sql);
//		 sql= "SET foreign_key_checks = 1;";//reactivates warnings on table drop with foreign keys
//		 stmt.executeUpdate(sql);
		 // create table voltage level with corresponding attributes
		 sql = "CREATE TABLE IF NOT EXISTS Voltage_Level(rdfID VARCHAR(50) NOT NULL, "
		 		+ "Name VARCHAR(50),Substation_rdfID VARCHAR(50), BaseVoltage_rdfID VARCHAR(50), "
		 		+ "PRIMARY KEY(rdfID),FOREIGN KEY(Substation_rdfID) REFERENCES Substation(rdfID),"
		 		+ "FOREIGN KEY(BaseVoltage_rdfID) REFERENCES Base_Voltage(rdfID))"; 
		 stmt.executeUpdate(sql) ; // execute query

		 System.out.println("Created Voltage Level table in given database successfully...");
		 } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 }
	 
	 
	//function to create table for generating unit
	 public static void GeneratingUnitTableCreate( )
	 {
	// Connect to the created database power system 
		 try {
			conn = DriverManager.getConnection(DB_URL + "PowerSystem"+DISABLE_SSL, USER, PASS);
		
		 sql = "USE PowerSystem";
		 stmt.executeUpdate(sql);
		 sql= "SET foreign_key_checks = 0;";//avoids warnings on table drop with foreign keys
		 stmt.executeUpdate(sql);
		 sql = "DROP TABLE IF EXISTS Generating_Unit";
		 stmt.executeUpdate(sql);
//		 sql= "SET foreign_key_checks = 1;";//reactivates warnings on table drop with foreign keys
//		 stmt.executeUpdate(sql);
		 // create table generating unit with corresponding attributes
		 sql = "CREATE TABLE IF NOT EXISTS Generating_Unit(rdfID VARCHAR(50) NOT NULL, Name VARCHAR(50),"
		 		+ "maxP double, minP double,Equipment_container VARCHAR(50), PRIMARY KEY(rdfID),  "
		 		+ "FOREIGN KEY(Equipment_container) REFERENCES Substation(rdfID))"; 
		 stmt.executeUpdate(sql) ; // execute query

		 System.out.println("Created Generating_Unit table in given database successfully...");
		 } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 }
	 
	//function to create table for synchronous machine
	 public static void SyncMachineTableCreate( )
	 {
	// Connect to the created database power system
		 try {
			conn = DriverManager.getConnection(DB_URL + "PowerSystem"+DISABLE_SSL, USER, PASS);
		 sql = "USE PowerSystem";
		 stmt.executeUpdate(sql);
		 sql= "SET foreign_key_checks = 0;";//avoids warnings on table drop with foreign keys
		 stmt.executeUpdate(sql);
		 sql = "DROP TABLE IF EXISTS Synchronous_Machine";
		 stmt.executeUpdate(sql);
//		 sql= "SET foreign_key_checks = 1;";//reactivates warnings on table drop with foreign keys
//		 stmt.executeUpdate(sql);
		 // create table synchronous machine with corresponding attributes
		 sql = "CREATE TABLE IF NOT EXISTS Synchronous_Machine(rdfID VARCHAR(50) NOT NULL, "
		 		+ "Name VARCHAR(50),ratedS double,P double, Q double,Generating_Unit_rdfID VARCHAR(50),"
		 		+ "Regulating_Control_rdfID VARCHAR(50) ,Equipment_container VARCHAR(50), "
		 		+ "PRIMARY KEY(rdfID),FOREIGN KEY(Generating_Unit_rdfID) REFERENCES Generating_Unit(rdfID), "
		 		+ "FOREIGN KEY(Regulating_Control_rdfID) REFERENCES Regulating_Control(rdfID), "
		 		+ "FOREIGN KEY(Equipment_container) REFERENCES Voltage_Level(rdfID))";
		 stmt.executeUpdate(sql) ; // execute query

		 System.out.println("Created Synchronous Machine table in given database successfully...");
		 } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 }
	 
	 
	//function to create table for regulating control
	 public static void RegControlTableCreate( )
	 {
	// Connect to the created database power system
		 try {
			conn = DriverManager.getConnection(DB_URL + "PowerSystem"+DISABLE_SSL, USER, PASS);
		
		 sql = "USE PowerSystem";
		 stmt.executeUpdate(sql);
		 sql= "SET foreign_key_checks = 0;";//avoids warnings on table drop with foreign keys
		 stmt.executeUpdate(sql);
		 sql = "DROP TABLE IF EXISTS Regulating_Control";
		 stmt.executeUpdate(sql);
//		 sql= "SET foreign_key_checks = 1;";//reactivates warnings on table drop with foreign keys
//		 stmt.executeUpdate(sql);
		 // create table regulating control with corresponding attributes
		 sql = "CREATE TABLE IF NOT EXISTS Regulating_Control(rdfID VARCHAR(50) NOT NULL, "
		 		+ "Name VARCHAR(50), Target_value double, PRIMARY KEY(rdfID))"; 
		 stmt.executeUpdate(sql) ; // execute query

		 System.out.println("Created Regulating Control table in given database successfully...");
		 } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 }
	 
	 
	//function to create table for power transformer
	 public static void PowerTransformerTableCreate( )
	 {
	// Connect to the created database power system
		 try {
			conn = DriverManager.getConnection(DB_URL + "PowerSystem"+DISABLE_SSL, USER, PASS);
		
		 sql = "USE PowerSystem";
		 stmt.executeUpdate(sql);
		 sql= "SET foreign_key_checks = 0;";//avoids warnings on table drop with foreign keys
		 stmt.executeUpdate(sql);
		 sql = "DROP TABLE IF EXISTS Power_Transformer";
		 stmt.executeUpdate(sql);
//		 sql= "SET foreign_key_checks = 1;";//reactivates warnings on table drop with foreign keys
//		 stmt.executeUpdate(sql);
		 // create table power transformer with corresponding attributes
		 sql = "CREATE TABLE IF NOT EXISTS Power_Transformer(rdfID VARCHAR(50) NOT NULL, Name VARCHAR(50), "
		 		+ "equip_cont_ID VARCHAR(50), PRIMARY KEY(rdfID), FOREIGN KEY(equip_cont_ID) "
		 		+ "REFERENCES Substation(rdfID))"; 
		 stmt.executeUpdate(sql) ; // execute query
		 System.out.println("Created Power Transformer table in given database successfully...");
		 } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 }
	 
	 
	//function to create table for energy consumer
	 public static void EnergyConsumerTableCreate( )
	 {
	// Connect to the created database power system 
		 try {
			conn = DriverManager.getConnection(DB_URL + "PowerSystem"+DISABLE_SSL, USER, PASS);
		
		 sql = "USE PowerSystem";
		 stmt.executeUpdate(sql);
		 sql= "SET foreign_key_checks = 0;";//avoids warnings on table drop with foreign keys
		 stmt.executeUpdate(sql);
		 sql = "DROP TABLE IF EXISTS Energy_Consumer";
		 stmt.executeUpdate(sql);
//		 sql= "SET foreign_key_checks = 1;";//reactivates warnings on table drop with foreign keys
//		 stmt.executeUpdate(sql);
		 // create table energy consumer with corresponding attributes
		 sql = "CREATE TABLE IF NOT EXISTS Energy_Consumer(rdfID VARCHAR(50) NOT NULL, "
		 		+ "Name VARCHAR(50),P double, Q double,Equipment_container VARCHAR(50), "
		 		+ "PRIMARY KEY(rdfID),FOREIGN KEY(Equipment_container) "
		 		+ "REFERENCES Voltage_Level(rdfID))"; 
		 stmt.executeUpdate(sql) ; // execute query

		 System.out.println("Created Energy Consumer table in given database successfully...");
		 } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 }
	 
	 
	//function to create table for power transformer end
	 public static void PowerTransformerEndTableCreate( )
	 {
	// Connect to the created database power system 
		 try {
			conn = DriverManager.getConnection(DB_URL + "PowerSystem"+DISABLE_SSL, USER, PASS);
		
		 sql = "USE PowerSystem";
		 stmt.executeUpdate(sql);
		 sql= "SET foreign_key_checks = 0;";//avoids warnings on table drop with foreign keys
		 stmt.executeUpdate(sql);
		 sql = "DROP TABLE IF EXISTS PowerTransformerEnd";
		 stmt.executeUpdate(sql);
//		 sql= "SET foreign_key_checks = 1;";//reactivates warnings on table drop with foreign keys
//		 stmt.executeUpdate(sql);
		 // create table power transformer end with corresponding attributes
		 sql = "CREATE TABLE IF NOT EXISTS PowerTransformerEnd(rdfID VARCHAR(50) NOT NULL, "
		 		+ "Name VARCHAR(50),r double,x double,Transformer_rdfID VARCHAR(50), "
		 		+ "BaseVoltage_rdfID VARCHAR(50), PRIMARY KEY(rdfID), FOREIGN KEY(Transformer_rdfID) "
		 		+ "REFERENCES Power_Transformer(rdfID), FOREIGN KEY(BaseVoltage_rdfID) "
		 		+ "REFERENCES Base_Voltage(rdfID))";
		 stmt.executeUpdate(sql) ; // execute query

		 System.out.println("Created PowerTransformerEnd table in given database successfully...");
		 } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 }
	 
	 
	//function to create table for ratio tap changer
	 public static void RatioTapChangerTableCreate( )
	 {
	// Connect to the created database power system
		 try {
			conn = DriverManager.getConnection(DB_URL + "PowerSystem"+DISABLE_SSL, USER, PASS);
		
		 sql = "USE PowerSystem";
		 stmt.executeUpdate(sql);
		 sql= "SET foreign_key_checks = 0;";//avoids warnings on table drop with foreign keys
		 stmt.executeUpdate(sql);
		 sql = "DROP TABLE IF EXISTS RatioTapChanger";
		 stmt.executeUpdate(sql);
//		 sql= "SET foreign_key_checks = 1;";//reactivates warnings on table drop with foreign keys
//		 stmt.executeUpdate(sql);
		 // create table Ratio tap changer with corresponding attributes
		 sql = "CREATE TABLE IF NOT EXISTS RatioTapChanger(rdfID VARCHAR(50) NOT NULL, "
		 		+ "Name VARCHAR(50), step double, PRIMARY KEY(rdfID))"; 
		 stmt.executeUpdate(sql) ; // execute query

		 System.out.println("Created RatioTapChanger table in given database successfully...");
		 } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 }
	 
	 
	//function to create table for breaker
	 public static void BreakerTableCreate( )
	 {
	// Connect to the created database power system
		 try {
			conn = DriverManager.getConnection(DB_URL + "PowerSystem"+DISABLE_SSL, USER, PASS);
		
		 sql = "USE PowerSystem";
		 stmt.executeUpdate(sql);
		 sql= "SET foreign_key_checks = 0;";//avoids warnings on table drop with foreign keys
		 stmt.executeUpdate(sql);
		 sql = "DROP TABLE IF EXISTS Breakers";
		 stmt.executeUpdate(sql);
//		 sql= "SET foreign_key_checks = 1;";//reactivates warnings on table drop with foreign keys
//		 stmt.executeUpdate(sql);
		 // create table Breaker with corresponding attributes
		 sql = "CREATE TABLE IF NOT EXISTS Breakers(rdfID VARCHAR(50) NOT NULL, Name VARCHAR(50), "
		 		+ "state bool, equip_cont_ID VARCHAR(50), PRIMARY KEY(rdfID), "
		 		+ "FOREIGN KEY(equip_cont_ID) REFERENCES Voltage_Level(rdfID))"; 
		 stmt.executeUpdate(sql) ; // execute query

		 System.out.println("Created Breaker table in given database successfully...");
		 } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 }
	 
	 
	//function to add data to base voltage table
	 public static void BaseVoltageDataAdd(String bv_rdfID, double bv_nominal_value)
	 {
			 sql = "INSERT INTO Base_Voltage " + "VALUES ('"+bv_rdfID+"',"+bv_nominal_value+");";
			 try {
				 
				stmt.executeUpdate(sql);
				sql= "SET foreign_key_checks = 0;";//avoids warnings on table drop with foreign keys
				 stmt.executeUpdate(sql);
			 System.out.println("Inserted records into the table...");
			 // finish the statement
			 System.out.println("The base voltage table is updated...");
			 conn.close();
			 } catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	 }
	 
	 
	//function to add data to substation table
	 public static void SubstationDataAdd(String sb_rdfID, String sb_name, String sb_region)
	 {
			 sql = "INSERT INTO Substation " + "VALUES ('"+sb_rdfID+"','"+sb_name+"','"+sb_region+"');";
			 try {
				stmt.executeUpdate(sql);
			 System.out.println("Inserted records into the table...");
			 // finish the statement
			 System.out.println("The substation table is updated...");
			 conn.close();
			 } catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	 }
	 
	 
	//function to add data to voltage level table
	 public static void VoltageLevelDataAdd(String vl_rdfID, String vl_name, String vl_subID, String vl_baseVID)
	 {
			 sql = "INSERT INTO Voltage_Level " + "VALUES ('"+vl_rdfID+"','"+vl_name+"','"+vl_subID+"',"
			 		+ "'"+vl_baseVID+"');";
			 try {
				stmt.executeUpdate(sql);
			 System.out.println("Inserted records into the table...");
			 // finish the statement
			 System.out.println("The voltage level table is updated...");
			 conn.close();
			 } catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	 }
	 
	 
	//function to add data to generating unit table
	 public static void GeneratingUnitDataAdd(String gu_rdfID, String gu_name, double gu_mxP, 
			 double gu_mnP, String gu_euipID)
	 {
			 sql = "INSERT INTO Generating_Unit " + "VALUES ('"+gu_rdfID+"','"+gu_name+"',"+gu_mxP+","
			 		+ ""+gu_mnP+",'"+gu_euipID+"');";
			 try {
				stmt.executeUpdate(sql);
			 System.out.println("Inserted records into the table...");
			 // finish the statement
			 System.out.println("The Generating_Unit table is updated...");
			 conn.close();
			 } catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	 }
	 
	 
	//function to add data to sync machine table
	 public static void SyncMachineDataAdd(String sm_rdfID, String sm_name, double sm_ratedS, 
			 double sm_P, double sm_Q, String sm_GenUnitID,String sm_RegCtrlID,String sm_euipID)
	 {
		 try {
		 
		
		 	 
		 sql = "INSERT INTO Synchronous_Machine " + "VALUES ('"+sm_rdfID+"','"+sm_name+"',"
			 		+ ""+sm_ratedS+","+sm_P+","+sm_Q+",'"+sm_GenUnitID+"','"+sm_RegCtrlID+"','"+sm_euipID+"');";
			 
				 System.out.println(sql);
				stmt.executeUpdate(sql);
			 System.out.println("Inserted records into the table...");
			 // finish the statement
			 System.out.println("The Synchronous_machine table is updated...");
			 conn.close();
			 
			 } catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	 }
	 
	 
	//function to add data to regulating control table
	 public static void RegControlDataAdd(String rc_rdfID, String rc_name, double rc_trgtValue)
	 {
			 sql = "INSERT INTO Regulating_Control " + "VALUES ('"+rc_rdfID+"','"+rc_name+"','"+rc_trgtValue+"');";
			 try {
				stmt.executeUpdate(sql);
			 System.out.println("Inserted records into the table...");
			 // finish the statement
			 System.out.println("The Regulating_Control table is updated...");
			 conn.close();
			 } catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	 }
	 
	 
	//function to add data to power transformer table
	 public static void PowerTransformerDataAdd(String pt_rdfID, String pt_name, String pt_equipID )
	 {
			 sql = "INSERT INTO Power_Transformer " + "VALUES ('"+pt_rdfID+"','"+pt_name+"' , '"+pt_equipID+"');";
			 try {
				stmt.executeUpdate(sql);
			 System.out.println("Inserted records into the table...");
			 // finish the statement
			 System.out.println("The power transformer table is updated...");
			 conn.close();
			 } catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	 }
	 
	 
	//function to add data to energy consumer table
	 public static void EnergyConsumerDataAdd(String ec_rdfID, String ec_name, double ec_P, 
			 double ec_Q, String ec_euipID)
	 {
			 sql = "INSERT INTO Energy_Consumer " + "VALUES ('"+ec_rdfID+"','"+ec_name+"',"
			 		+ ""+ec_P+","+ec_Q+",'"+ec_euipID+"');";
			 try {
				stmt.executeUpdate(sql);
			 System.out.println("Inserted records into the table...");
			 // finish the statement
			 System.out.println("The Energy Consumer table is updated...");
			 conn.close();
			 } catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	 }
	 	 
	 
	//function to add data to power transformer end table
	 public static void PowerTransformerEndDataAdd(String pte_rdfID, String pte_name, double pte_r, 
			 double pte_x,String pte_transfID, String pte_baseVID)
	 {
			 sql = "INSERT INTO PowerTransformerEnd " + "VALUES ('"+pte_rdfID+"','"+pte_name+"',"
			 		+ ""+pte_r+","+pte_x+",'"+pte_transfID+"','"+pte_baseVID+"');";
			 try {
				stmt.executeUpdate(sql);
			 System.out.println("Inserted records into the table...");
			 // finish the statement
			 System.out.println("The PowerTransformerEnd table is updated...");
			 conn.close();
			 } catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	 }
	 
	 
	//function to add data to ratio tap changer table
	 public static void RatioTapChangerDataAdd(String rt_rdfID, String rt_name, double rt_step )
	 {
			 sql = "INSERT INTO RatioTapChanger " + "VALUES ('"+rt_rdfID+"','"+rt_name+"' , "+rt_step+");";
			 try {
				stmt.executeUpdate(sql);
			 System.out.println("Inserted records into the table...");
			 // finish the statement
			 System.out.println("The RatioTapChanger table is updated...");
			 conn.close();
			 } catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	 }
	 
	 
	//function to add data to breaker table
	 public static void BreakerDataAdd(String br_rdfID, String br_name, boolean br_state, String br_equipID )
	 {
			 sql = "INSERT INTO Breakers " + "VALUES ('"+br_rdfID+"','"+br_name+"' , "+br_state+",'"+br_equipID+"');";
			 try {
				stmt.executeUpdate(sql);
			 System.out.println("Inserted records into the table...");
			 // finish the statement
			 System.out.println("The Breaker table is updated...");
			 conn.close();
			 sql= "SET foreign_key_checks = 1;";//reactivates warnings on table drop with foreign keys
			 stmt.executeUpdate(sql);
			 } catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	 }
	 
	 
	 
}
