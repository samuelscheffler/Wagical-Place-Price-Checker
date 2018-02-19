package mtg;

/**
 * Represents a card from Magic the Gathering.
 * Contains such information as a card's name, set, rarity, and price.
 *
 * @author Samuel Scheffler
 *
 */

public class MTGCard {
	private String name;
	private char rarity;
	private double price;
	
	public final static char COMMON='C';
	public final static char UNCOMMON='U';
	public final static char RARE='R';
	
	public MTGCard(){
		this("", 'C', 0.0);
	}
	
	public MTGCard(String name, char rarity, double price){
		this.setName(name);
		this.setRarity(rarity);
		this.setPrice(price);
	}
	
	
	/**
	 *  Sets the name of MTGCard.
	 *  @param name
	*/
	public void setName(String name){
		this.name=name;
	}
	
	/**
	 * Sets the rarity of MTGCards. 
	 * @param rarity which must be a valid card rarity. C represents a common, U represents an uncommon, and R represents a rare.
	 * L for lands and T for tokens are valid and are converted to C.
	 * M for mythic is also valid, and is converted to R.
	 * @return boolean representing the validity of the given rarity.
	 */
	public boolean setRarity(char rarity){
		//If rarity is C, U, or R, it is valid.
		if(MTGCard.COMMON==rarity || MTGCard.UNCOMMON==rarity || MTGCard.RARE==rarity){
			this.rarity=rarity;
			
			return true;
		}
		
		//If rarity is L or T, it is valid, but is converted to C.
		else if(rarity=='L' || rarity=='T'){
			this.rarity=MTGCard.COMMON;
			
			return true;
		}
		
		//If rarity is M, it is valid, but is converted to R.
		else if(rarity=='M'){
			this.rarity=MTGCard.RARE;
			
			return true;
		}
		
		//If rarity is none of the above, it is invalid. rarity is set to C as a default and false is returned.
		else{
			this.rarity='C';
			System.err.println("MTGCard: "+ rarity + " is not a valid rarity.");
			return false;
		}
	}
	
	/**
	 * Sets the price of MTGCard.
	 * @param price
	 */
	public void setPrice(double price){
		//Ensuring that the price is valid.
		if(price>=0.0){
			this.price=price;
		}
		
		//If the price is not valid, make it valid.
		else{
			this.price=Math.abs(price);
		}
	}
	
	/**
	 * Gets the name of MTGCard.
	 * @return string representing the name of MTGCard. 
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * Gets the set name of MTGCard.
	 * @return string representing the set name of MTGCard. 
	 */
	/*public String getSetName(){
		return this.setName;
	}*/
	
	/**
	 * Gets the rarity of MTGCard.
	 * @return char representing the rarity of MTGCard. 
	 */
	public char getRarity(){
		return this.rarity;
	}
	
	/**
	 * Gets the price of MTGCard.
	 * @return double representing the price of MTGCard. 
	 */
	public double getPrice(){
		return this.price;
	}
	
	public String toString(){
		StringBuilder str=new StringBuilder("");
		str.append(name).append(" ").append(rarity).append(" ").append(price);
		
		return str.toString();
	}
}
