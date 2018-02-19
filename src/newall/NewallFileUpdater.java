package newall;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import mtg.SetList;
import mtg.WagicalMTGCard;

/**
 * This class is used for updating the card prices in the newall file.
 * @author Samuel Scheffler
 *
 */
public class NewallFileUpdater {
	private File newallFile;
	
	public NewallFileUpdater(File newallFile){
		setNewallFile(newallFile);
	}
	
	public void setNewallFile(File newallFile){
		if(newallFile.getName().equals("newall") && newallFile.exists()){
			this.newallFile=newallFile;
		}
	}
	
	/**
	 * The prices of a given set in the newall file are updated with the prices in newPriceCards.
	 * A new newall file is created that contains the updated prices, and the old one is made into a backup file.
	 * @param setName
	 * @param newPriceCards
	 */
	public void update(String setName, ArrayList<WagicalMTGCard> newPriceCards) throws IOException{
		//Constants that holds indexes to vital information held within newallLine.
		final int CARD_NAME_INDEX=3;
		final int ABBREVIATION_INDEX=6;
		final int PRICE_INDEX=4;

		String setAbbreviation=SetList.getSetAbbreviation(setName);
		
		String newallLine;
		int counter=0;
		
		BufferedReader in;
		BufferedWriter out;
		
		File oldNewallFile;
		String newallBackupName="/newall (Backup)";
		
		//Used to check if newall (Backup) already exists, and if it does, numbers the backup.
		oldNewallFile=new File(newallFile.getParentFile() + newallBackupName);
		if(oldNewallFile.exists()){
			//Represents the number of backups created so far.
			int backupNum=2;
			
			while(oldNewallFile.exists()){
				newallBackupName="/newall (Backup - " + backupNum + ")";
				
				oldNewallFile=new File(newallFile.getParentFile() + newallBackupName);
				
				backupNum++;
			}
		}
	
		//Checking whether file can be renamed.
		if(!newallFile.renameTo(oldNewallFile)){
			System.err.println("Unable to rename newall file.");
			return;
		}
		
		//Sets newallFile to the new newall file that has been created.
		newallFile=new File(newallFile.getAbsolutePath());
		
		if(!newallFile.createNewFile()){
			System.err.println("Unable to create a new newall file.");
			return;
		}
		
		in=new BufferedReader(new FileReader(oldNewallFile));
		out=new BufferedWriter(new FileWriter(newallFile));
		
		out.write(in.readLine());
		out.newLine();
		
		out.write(in.readLine());
		out.newLine();
		
		while((newallLine=in.readLine())!=null){
			//Holds the different sections of a line in the newall file.
			String temp[]=newallLine.split("\\|");
			
			//Checking if a specific card on the newall file matches a card from newPriceCards.
			if(counter<newPriceCards.size() && temp[CARD_NAME_INDEX].equals(newPriceCards.get(counter).getName()) && temp[ABBREVIATION_INDEX].equals(setAbbreviation)){
				temp[PRICE_INDEX]=String.valueOf(newPriceCards.get(counter).getPrice());
				
				StringBuilder sb=new StringBuilder("");
				
				//Rebuilding the line from the newall file with the new price.
				for(int i=0; i<temp.length; i++){
					sb.append(temp[i]);
					
					if(i!=temp.length-1){
						sb.append("|");
					}
				}
				
				out.write(sb.toString());
				out.newLine();
				
				counter++;
			}
			
			//If a card is not a match, the newallLine is just added to the new newall file without any edits.
			else{
				out.write(newallLine);
				out.newLine();
			}
		}
		
		//Closing streams.
		in.close();
		out.close();
	
	}
}
