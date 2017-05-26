
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MatLogic2 
{
	public static ArrayList<Integer> main_nodes = new ArrayList<Integer>(); //main nodes array
	public static ArrayList<List<Integer>> connections = new ArrayList<List<Integer>>(); //connection matrix - n*n showing connectivity within all nodes
	public static ArrayList<List<Integer>> connections_copy = new ArrayList<List<Integer>>();
	public static ArrayList<Integer> paths = new ArrayList<Integer>(); // array to store intermediate paths
		
	public static void main(String[] args) 
	{
		main_nodes.add(0);main_nodes.add(1);main_nodes.add(2);main_nodes.add(3);
		main_nodes.add(4); //forming main nodes array
		int pos=0;
		System.out.println(main_nodes+"\n");
		connections.add(Arrays.asList(0,     0,     0,     0,     1,     0,     1,     0,     0,     0,     0,     0,     0,     0));
		connections.add(Arrays.asList(0,     0,     0,     0,     0,     0,     0,     0,     0,     0,     0,     0,     1,     1));
		connections.add(Arrays.asList(0,     0,     0,     0 ,    0,     0,     0,     0,     1,     0,     1,     1,     0,     0));
		connections.add(Arrays.asList(0,     0,     0,     0,     0,     0 ,    0 ,    0,     0,     1 ,    0 ,    0,     0,     0));
		connections.add(Arrays.asList(1,     0,     0,     0,     0,     1,     0,     1,     0,     1,     0,     0,     0,     0));
		connections.add(Arrays.asList(0,     0,     0,     0,     1,     0,     1,     0,     0,     0,     0,     0,     0,     0));
		connections.add(Arrays.asList(1,     0,     0,     0,     0,     1,     0,     0,     0,     0,     0,     0,     0,     0));
		connections.add(Arrays.asList(0,     0,     0,     0,     1,     0,     0,     0,     1,     0,     0,     0,     0,     0));
		connections.add(Arrays.asList(0,     0,     1,     0,     0,     0,     0,     1,     0,     0,     0,     0,     0,     0));
		connections.add(Arrays.asList(0,     0,     0,     1,     1,     0,     0,     0,     0,     0,     0,     0,     0,     0));
		connections.add(Arrays.asList(0,     0,     1,     0,     0,     0,     0,     0,     0,     0,     0,     0,     1,     0));
		connections.add(Arrays.asList(0,     0,     1,     0,     0,     0,     0,     0,     0,     0,     0,     0,     0,     1));
		connections.add(Arrays.asList(0,     1,     0,     0,     0,     0,     0,     0,     0,     0,     1,     0,     0,     0));
		connections.add(Arrays.asList(0,     1,     0,     0,     0,     0,     0,     0,     0,     0,     0,     1,     0,     0));//forming the connections array
		connections_copy = connections; //backup copy of original connection matrix
		
		for (List<Integer> list : connections) //Loop to display the connection matrix
		{
		    for (Integer i : list) 
		    {
		        System.out.print(i+" ");
		    }
		    System.out.println();
		}
		
		for(int i1=0;i1<main_nodes.size();i1++) //loop to cycle through each main bus
		{
			
				while(connections.get(main_nodes.get(i1)).contains(1)) //condition to ensure all connections to main node are considered
				{
					paths.add(main_nodes.get(i1)); //adding the first element (main bus) to path
					pos= connections.get(main_nodes.get(i1)).indexOf(1); //finding the connection to other nodes by searching '1' in connection matrix
					connections.get(pos).set(main_nodes.get(i1), 0); //once found, reset the connection value to '0' to indicating path traced!
					find_path(pos,main_nodes.get(i1));
					connections.get(main_nodes.get(i1)).set(pos, 0);//set the immediate element (from requested main bus) of formed network to zero
					paths.add(-2); //add a delimiter in paths array
				}
		}
		//System.out.println(paths); //display path matrix
		//System.out.println(connections); //verify that all nodes and paths are covered (complete zero matrix!)
		for(int l=0; l< paths.size(); l++)
		paths.set(l, paths.get(l)+1);
		System.out.println(paths); //display path matrix
	}
	
	private static void find_path(int i, int org_node) //function to find the network till connected main bus is found
	{
		int k=0; //variable used as a trace from one node to other 
		if(main_nodes.contains(i)) //check if requested node is main bus
		{
			paths.add(i); //add the main bus to the array paths
			return;
		}
		else
		{
			int a=connections.get(i).indexOf(1); //get the next node connection
			paths.add(i); //add the next node to the path
			connections.get(a).set(i, 0); //ensure traced connection is set to zero
			connections.get(i).set(a, 0); //ensure traced connection is set to zero
			if(main_nodes.contains(a)) //check if main node is traced
			paths.add(a);
			else
			{
				k=a; //remember the previous node
				{
					do
					{
						paths.add(k);
						int y=k; //store k in temp variable y
						k=connections.get(k).indexOf(1); //check for next node connection
						connections.get(k).set(y, 0); //ensure traced connection is set to zero
						connections.get(y).set(k, 0); //ensure traced connection is set to zero
						if (main_nodes.contains(k)) //check if the node is a main bus
						break;			
					}while(!paths.contains(k)); //continue till the node is a main bus
				}
			
			paths.add(k);
			}
		return;
		}
	}
}

