package mtg;

/**
 * A subclass of MTGCard that includes the quantity of cards in stock.
 * @author Samuel Scheffler
 *
 */
public class WagicalMTGCard extends MTGCard{
	private int quantity;
	
	public WagicalMTGCard(){
		super();
		quantity=0;
	}
	
	public WagicalMTGCard(String name, char rarity, double price, int quantity){
		super(name, rarity, price);
		setQuantity(quantity);
	}
	
	/**
	 * Sets the quantity of WagicalMTGCard
	 * @param quantity
	 */
	public void setQuantity(int quantity){
		//Ensuring that the quantity is valid.
		if(quantity>=0){
			this.quantity=quantity;
		}
		
		//If the quantity is not valid, make it valid.
		else{
			this.quantity=0;
		}
	}
	
	/**
	 * Returns the quantity of WagicalMTGCard.
	 * @return integer representing the quantity.
	 */
	public int getQuantity(){
		return quantity;
	}
}
