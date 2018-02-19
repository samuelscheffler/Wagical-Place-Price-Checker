package newall;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This class locates and retrieves the newall file based on the file path "newall file path.dat"
 * @author Samuel Scheffler
 *
 */
public class NewallFileLocator {
	private static final File NEWALL_FILE_PATH_FILE=new File("newall file path.dat");
	private File newallFile;
	
	public NewallFileLocator() throws IOException{
		retrieveNewallFile();
	}
	
	/**
	 * Reads "newall file path.dat" for newall file's path.
	 * If "newall file path.dat" does not exist, it is created.
	 * @throws IOException
	 */
	private void retrieveNewallFile() throws IOException{
		//Ensuring that "newall file path.dat exists.
		if(NEWALL_FILE_PATH_FILE.exists()){
			BufferedReader in=new BufferedReader(new FileReader(NEWALL_FILE_PATH_FILE));
			String newallFilePath;
			
			newallFilePath=in.readLine();
			
			in.close();
			
			//Ensuring newall path is correct. If not newallFile is set to null.
			if(newallFilePath!=null && newallFilePath.indexOf("newall")!=-1){
				newallFile=new File(newallFilePath);
				
				if(!newallFile.isFile()){
					newallFile=null;
				}
			}
		}
		
		else{
			//"newall file path.dat is created if it does not exist."
			NEWALL_FILE_PATH_FILE.createNewFile();
			newallFile=null;
		}
	}
	
	/**
	 * Sets the newall file path and saves it in "newall file path.dat"
	 * @param newallFilePath
	 * @return true if newallFilePath is a valid file path to the newall file, false if it is not.
	 * @throws IOException
	 */
	public boolean setNewallFilePath(String newallFilePath) throws IOException{
		//Ensuring that the file path contains the word "newall."
		if(newallFilePath.indexOf("newall")!=-1){
			//Used to test if file path exists
			File test=new File(newallFilePath);
			
			//Ensuring that the file path exists.
			if(test.isFile()){
				newallFile=test;
				
				BufferedWriter out=new BufferedWriter(new FileWriter(NEWALL_FILE_PATH_FILE));
				
				out.write(newallFile.getAbsolutePath());
				
				out.close();
				
				return true;
			}
			
			else{
				return false;
			}
		}
		
		else{
			return false;
		}
	}
	
	/**
	 * returns the newall file.
	 * @return newallFile
	 */
	public File getNewallFile(){
		return newallFile;
	}
}
