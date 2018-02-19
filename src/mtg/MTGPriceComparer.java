package mtg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import table.PriceDifferenceTableModel;


/**
 * This class is used to compare card prices from TCGPlayer to the newall file.
 * This class uses three thresholds to generate the list. 
 * These thresholds allow the user to specify how off the prices are before they get displayed.
 * @author Samuel Scheffler
 *
 */
public class MTGPriceComparer {
	//newall file
	File newallFile;
	
	//Price difference thresholds.
	private double maxCommonPriceDiff;
	private double maxUncommonPriceDiff;
	private double maxRarePriceDiff;
	
	//Booleans that determine whether a rarity should be checked if it is out of stock.
	private boolean checkOutOfStockCommons;
	private boolean checkOutOfStockUncommons;
	private boolean checkOutOfStockRares;

	public MTGPriceComparer(File newallFile){
		this(newallFile, 0.0, 0.0, 0.0);
	}
	
	public MTGPriceComparer(File newallFile, double maxCommonPriceDiff, double maxUncommonPriceDiff, double maxRarePriceDiff){
		this(newallFile, maxCommonPriceDiff, maxUncommonPriceDiff, maxRarePriceDiff, true, true, true);
	}
	
	public MTGPriceComparer(File newallFile, double maxCommonPriceDiff, double maxUncommonPriceDiff, double maxRarePriceDiff, boolean checkOutOfStockCommons, boolean checkOutOfStockUncommons, boolean checkOutOfStockRares){
		this.newallFile=newallFile;
		
		setMaxCommonPriceDiff(maxCommonPriceDiff);
		setMaxUncommonPriceDiff(maxUncommonPriceDiff);
		setMaxRarePriceDiff(maxRarePriceDiff);
		
		setCheckOutOfStockCommons(checkOutOfStockCommons);
		setCheckOutOfStockUncommons(checkOutOfStockUncommons);
		setCheckOutOfStockRares(checkOutOfStockRares);
	}
	
	/**
	 * Sets price threshold for commons.
	 * @param maxCommonPriceDiff
	 */
	public void setMaxCommonPriceDiff(double maxCommonPriceDiff){
		if(maxCommonPriceDiff>=0.0){
			this.maxCommonPriceDiff=maxCommonPriceDiff;
		}
		
		else{
			this.maxCommonPriceDiff=0.0;
		}
	}
	
	/**
	 * Sets price threshold for uncommons.
	 * @param maxUncommonPriceDiff
	 */
	public void setMaxUncommonPriceDiff(double maxUncommonPriceDiff){
		if(maxUncommonPriceDiff>=0.0){
			this.maxUncommonPriceDiff=maxUncommonPriceDiff;
		}
		
		else{
			this.maxUncommonPriceDiff=0.0;
		}
	}
	
	/**
	 * Sets price threshold for rares.
	 * @param maxRarePriceDiff
	 */
	public void setMaxRarePriceDiff(double maxRarePriceDiff){
		if(maxRarePriceDiff>=0.0){
			this.maxRarePriceDiff=maxRarePriceDiff;
		}
		
		else{
			this.maxRarePriceDiff=0.0;
		}
	}
	
	/**
	 * Sets whether out stock commons should be checked and included in the priceDiffModel.
	 * @param checkOutOfStockCommons
	 */
	public void setCheckOutOfStockCommons(boolean checkOutOfStockCommons){
		this.checkOutOfStockCommons=checkOutOfStockCommons;
	}
	
	/**
	 * Sets whether out stock uncommons should be checked and included in the priceDiffModel.
	 * @param checkOutOfStockUncommons
	 */
	public void setCheckOutOfStockUncommons(boolean checkOutOfStockUncommons){
		this.checkOutOfStockUncommons=checkOutOfStockUncommons;
	}
	
	/**
	 * Sets whether out stock rares should be checked and included in the priceDiffModel.
	 * @param checkOutOfStockRares
	 */
	public void setCheckOutOfStockRares(boolean checkOutOfStockRares){
		this.checkOutOfStockRares=checkOutOfStockRares;
	}
	
	/**
	 * Gets price threshold for commons.
	 *@return a double representing the price threshold for commons.
	 */
	public double getMaxCommonPriceDiff(){
		return maxCommonPriceDiff;
	}
	
	/**
	 * Gets price threshold for uncommons.
	 * @return a double representing the price threshold for uncommons.
	 */
	public double getMaxUncommonPriceDiff(){
		return maxUncommonPriceDiff;
	}
	
	/**
	 * Gets price threshold for rares.
	 * @return a double representing the price threshold for rares.
	 */
	public double getMaxRarePriceDiff(){
		return maxRarePriceDiff;
	}
	
	/**
	 * @return boolean that tells whether out of stocks commons are checked.
	 */
	public boolean getCheckOutOfStockCommons(){
		return checkOutOfStockCommons;
	}
	
	/**
	 * @return boolean that tells whether out of stocks uncommons are checked.
	 */
	public boolean getCheckOutOfStockUncommons(){
		return checkOutOfStockUncommons;
	}
	
	/**
	 * @return boolean that tells whether out of stocks rares are checked.
	 */
	public boolean getCheckOutOfStockRares(){
		return checkOutOfStockRares;
	}
	
	/**
	 * Gets the price difference table.
	 * @return a DefaultTableModel that holds price differences.
	 
	public DefaultTableModel getPriceDiffModel(){
		//Creating a clone of priceDiffModel to be returned.
		return new DefaultTableModel(priceDiffModel.getDataVector(), COLUMN_NAMES);
	}*/
	
	/**
	 * Compares the card price of a given set from the newall file to the prices on TCGPlayer. A TableModel is then created based on the given thresholds.
	 * @param setName
	 * @return
	 * @throws IOException
	 */
	public PriceDifferenceTableModel Compare(String setName) throws IOException{
	    PriceDifferenceTableModel priceDiffModel=new PriceDifferenceTableModel();
		
		//Getting price list from TCGPlayer for the given set.
		ArrayList <MTGCard> cardList=TCGPlayerPriceGuide.getSet(setName);
		
		//If cardList is null then the set was not found and null is returned.
		if(cardList==null){
			return null;
		}
		
		String setAbbreviation=SetList.getSetAbbreviation(setName);
		
		BufferedReader in=new BufferedReader(new FileReader(newallFile));
		
		String line;
		
		//Indexes for specific information in the lines of the newall file.
		final int QUANTITY_INDEX=0;
		final int CARD_NAME_INDEX=3;
		final int ABBREVIATION_INDEX=6;
		final int RARITY_INDEX=5;
		final int PRICE_INDEX=4;
		
		String temp[];
		
		WagicalMTGCard card;
		
		in.readLine();
		in.readLine();
		
		while((line=in.readLine())!=null){
			//Splitting up lines in newall file.
			temp=line.split("\\|");
			
			//If a certain card is not in stock, ignore it.
			if(temp[ABBREVIATION_INDEX].equals(setAbbreviation)){
				if(temp[QUANTITY_INDEX].equals("")){
					temp[QUANTITY_INDEX]="0";
				}
				
				card=new WagicalMTGCard(temp[CARD_NAME_INDEX], temp[RARITY_INDEX].charAt(0), Double.valueOf(temp[PRICE_INDEX]), Integer.valueOf(temp[QUANTITY_INDEX]));
				
				//Checking whether the card is or foil or in stock. If the card is a foil, it is ignored. If the card is not in stock, then it is checked whether that out of stock card should be compared.
				if(card.getName().indexOf("Foil")==-1 && (!(card.getQuantity()==0)) ||
						(card.getRarity()==MTGCard.COMMON && checkOutOfStockCommons) || 
						(card.getRarity()==MTGCard.UNCOMMON && checkOutOfStockUncommons) || 
						(card.getRarity()==MTGCard.RARE && checkOutOfStockRares)){
					
	
					for(int i=0; i< cardList.size(); i++){
						if(card.getName().equals(cardList.get(i).getName())){
							//Checking if the price difference for the card is under the common price difference threshold.
							if(card.getRarity()==MTGCard.COMMON && Math.abs(card.getPrice()-cardList.get(i).getPrice())>=maxCommonPriceDiff){
								//priceDiffModel.addRow(new Object[]{card.getQuantity(), card.getName(), card.getRarity(), card.getPrice(), cardList.get(i).getPrice()});
								priceDiffModel.addRow(card, cardList.get(i));
							}
							
							//Checking if the price difference for the card is under the uncommon price difference threshold.
							else if(card.getRarity()==MTGCard.UNCOMMON && Math.abs(card.getPrice()-cardList.get(i).getPrice())>=maxUncommonPriceDiff){
								//priceDiffModel.addRow(new Object[]{card.getQuantity(), card.getName(), card.getRarity(), card.getPrice(), cardList.get(i).getPrice()});
								priceDiffModel.addRow(card, cardList.get(i));
							}
							
							//Checking if the price difference for the card is under the rare price difference threshold.
							else if(card.getRarity()==MTGCard.RARE && Math.abs(card.getPrice()-cardList.get(i).getPrice())>=maxRarePriceDiff){
								//priceDiffModel.addRow(new Object[]{card.getQuantity(), card.getName(), card.getRarity(), card.getPrice(), cardList.get(i).getPrice()});
								priceDiffModel.addRow(card, cardList.get(i));
							}
							
							//Once a card has been compared, it is removed from cardList.
							cardList.remove(i);
							break;
						}
					}
				}
			}
		}
		
		in.close();
		
		return priceDiffModel;
	}
}
