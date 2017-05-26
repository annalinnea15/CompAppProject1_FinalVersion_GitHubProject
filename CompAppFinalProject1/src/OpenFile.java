import java.util.Scanner;
import java.io.File;
import javax.swing.JFileChooser;


public class OpenFile {

	JFileChooser fileChooser = new JFileChooser();
	//StringBuilder sb = new StringBuilder();
	
	public File ChooseThisFile(){
		//if(fileChooser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION){
			File fileName = fileChooser.getSelectedFile();
			return fileName;
			
		//}
	}
}
