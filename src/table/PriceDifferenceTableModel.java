package table;

import java.util.ArrayList;

import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;

import mtg.MTGCard;
import mtg.WagicalMTGCard;

/**
 * An implementation of AbstractTableModel that is designed to show side by side price comparisons from TCGPlayer and Wagical Place.
 * 
 * @author Samuel Scheffler
 *
 */
public class PriceDifferenceTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	
	public static final String[] COLUMN_NAMES={"Quantity", "Card Name", "Rarity", "Wagical Place Price", "TCGPlayer Price", "New Price"};
	ArrayList<PriceDifferenceTableRow> rows;
	
	public PriceDifferenceTableModel(){
		rows=new ArrayList<PriceDifferenceTableRow>();
	}
	
	/**
	 * Adds a row to the table.
	 * @param wagicalCard
	 * @param TCGCard
	 */
	public void addRow(WagicalMTGCard wagicalCard, MTGCard TCGCard){
		rows.add(new PriceDifferenceTableRow(wagicalCard, TCGCard));
	}
	
	/**
	 * Deletes a row at a specific location.
	 * @param index
	 */
	public void deleteRow(int rowIndex){
		if(rowIndex>=0 && rowIndex<rows.size()){
			rows.remove(rowIndex);
		}
	}

	/**
	 * Deletes all rows on the table.
	 */
	public void clearTable(){
		rows.clear();
	}
	
	public WagicalMTGCard getNewPriceCard(int rowIndex){
		if(rowIndex>=0 && rowIndex<rows.size()){
			return rows.get(rowIndex).getNewPriceCard();
		}
		
		else{
			return null;
		}
	}
	
	@Override
	public int getRowCount() {
		return rows.size();
	}

	@Override
	public int getColumnCount() {
		return COLUMN_NAMES.length;
	}
	
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex){
		//Only allows last column in the row to be edited, and the new value must be a double.
		if(columnIndex==COLUMN_NAMES.length-1){
			//Testing whether a value is a double.
			try{
				double num=Double.valueOf((String)aValue);
				rows.get(rowIndex).setNewPrice(num);
				
				//Firing a TableModelEvent when newPrice has been editied.
				this.fireTableChanged(new TableModelEvent(this, rowIndex));
			} catch(NumberFormatException e){
				System.err.println((String)aValue + " is not a number.");
			}
		}
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return rows.get(rowIndex).getColumn(columnIndex);
	}
	
	@Override
	public String getColumnName(int index){
		if(index>=0 && index<COLUMN_NAMES.length){
			return COLUMN_NAMES[index];
		}
		
		else{
			return null;
		}
	}
	
	@Override 
	public Class<?> getColumnClass(int columnIndex){
		//Getting the class at a specific column, this is done so that an autoRowSorter can be created.
		return PriceDifferenceTableRow.getColumnClass(columnIndex);
	}
	
	/**
	 * Represents a row on the PriceDifferenceTableModel,
	 *  which holds information of a specific card such as the number in stock, 
	 *  its name, its rarity, its price on the wagical place, and its price on TCGPlayer.
	 * @author Samuel Scheffler
	 *
	 */
	private static class PriceDifferenceTableRow{
		private static final Class<?> COLUMN_CLASSES[]={Integer.class, String.class, Character.class, Double.class, Double.class, Double.class};
		private static final int NUM_OF_COLUMNS=5;
		
		private int quantity;
		private String cardName;
		private char rarity;
		private double wagicalPlacePrice;
		private double TCGPlayerPrice;
		private double newPrice;
		
		public PriceDifferenceTableRow(WagicalMTGCard wagicalCard, MTGCard TCGCard){
			if(wagicalCard.getName().equals(TCGCard.getName())){
				quantity=wagicalCard.getQuantity();
				cardName=wagicalCard.getName();
				rarity=wagicalCard.getRarity();
				wagicalPlacePrice=wagicalCard.getPrice();
				TCGPlayerPrice=TCGCard.getPrice();
				newPrice=0.0;
			}
		}
		
		/**
		 * Sets the newPrice column.
		 * @param newPrice
		 */
		public void setNewPrice(double newPrice){
			if(newPrice>=0){
				this.newPrice=newPrice;
			}
		}
		
		/**
		 * Gets information from the column of the given index.
		 * @param index
		 * @return Object representing information at the given index.
		 */
		public Object getColumn(int columnIndex){
			switch(columnIndex){
				case 0: return quantity;
				case 1: return cardName;
				case 2: return rarity;
				case 3: return wagicalPlacePrice;
				case 4: return TCGPlayerPrice;
				case 5: if(newPrice==0){
					return "";
				}
				else{
					return newPrice;
				}
				
				default: return null;
			}
		}
		
		public WagicalMTGCard getNewPriceCard(){
			return new WagicalMTGCard(cardName, rarity, newPrice, quantity);
		}
		
		/**
		 * Returns the Class at the given columnIndex.
		 * @param columnIndex
		 * @return Class
		 */
		public static Class<?> getColumnClass(int columnIndex){
			if(columnIndex>=0 && columnIndex<NUM_OF_COLUMNS){
				return COLUMN_CLASSES[columnIndex];
			}
			
			else{
				return Object.class;
			}
		}
	}
}

