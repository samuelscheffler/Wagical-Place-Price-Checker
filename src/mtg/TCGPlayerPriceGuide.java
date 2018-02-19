package mtg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Used to specific set lists from tcgplayer.com
 * @author Samuel Scheffler
 *
 */
public class TCGPlayerPriceGuide {
	/**
	 * Gets cards from a given set and stores them in an ArrayList of type MTGCard.
	 * @param set
	 * @return An ArrayList of MTGCards from given set. null if the card list could not be retrieved.
	 */
	public static ArrayList <MTGCard> getSet(String set){
		ArrayList <MTGCard> cardList=null;
		
		URL url=null;
		URLConnection connection=null;
		StringBuilder sb=null;
		InputStreamReader isr=null;
		BufferedReader in=null;
		
		try{
			url=new URL(TCGPlayerPriceGuide.getURL(set));
			
			//Used to store html code.
			sb=new StringBuilder("");
			
			//Establishing a connection with tcgplayer.com and retrieving the html code,
			//which is stored in sb.
			try{
				connection=url.openConnection();
				connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
				connection.connect();
				isr=new InputStreamReader(connection.getInputStream());
				in=new BufferedReader(isr);
				
				String line;
				
				//Reading input stream from tcgplayer.com
				while((line=in.readLine())!=null){
					sb.append(line);
				}
				
			
			}catch(IOException e){
				e.printStackTrace();
				return null;
			}finally{
				try {
					in.close();
					isr.close();
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
			}
			
			//html code is parsed into a document for scraping. 
			Document doc=Jsoup.parse(sb.toString());
			Element form=doc.getElementById("Form1");
			
			//If Form1 is not found on the website, then the URL is invalid, and null is returned.
			if(form==null){
				System.err.println("TCGPlayerPriceGuide: " + url.toString() + " is an invalid URL.");
				return null;
			}
			
			//Finding the table with the card list.
			Element table=form.getElementsByTag("table").last();
			Elements rows=table.getElementsByTag("tr");
			
			//ArrayList the stores the cards' information.
			cardList=new ArrayList <MTGCard>();
			
			String cardName;
			char rarity;
			double price;
			//Used to temporarily store the price as a string to ensure that it exists.
			String tempPrice;
			
			for(Element r: rows){
				tempPrice=r.getElementsByTag("td").get(5).text().replace("$", "");
				
				//Checking if the card's price exists, if not, do not store it.
				 if(!tempPrice.equals("N/A")){
					
					cardName=r.getElementsByTag("td").get(0).text().replace(Character.toString((char)160), "");
					rarity=r.getElementsByTag("td").get(3).text().charAt(0);
					price=Double.valueOf(tempPrice);
				
					cardList.add(new MTGCard(cardName, rarity, price));
				}
			}
		}catch(MalformedURLException e){
			e.printStackTrace();
			
			return null;
		}
		
		return cardList;
	}
	
	/**
	 * Returns the TCGPlayer URL to a price guide for a given set.
	 * @param setName
	 * @return a String representing a TCGPlayer URL
	 */
	public static String getURL(String setName){
		String URL="http://magic.tcgplayer.com/db/search_result.asp?Set_Name=";
		
		//Temporary fix for Kaladesh Inventions URL.
		if(setName.equals("Kaladesh Inventions")){
			return URL+="Masterpiece+Series%3A+Kaladesh+Inventions";
		}
				
		URL+=setName.replaceAll(" ", "%20").replaceAll("'", "%27");
		
		return URL;
	}
}
