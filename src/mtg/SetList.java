package mtg;

/**
 * Holds a list of Magic The Gathering set names with their abreviations,
 * 
 * @author Samuel Scheffler
 *
 */

public class SetList {
	private static final SetNameAbbreviation SET_LIST[]={new SetNameAbbreviation("7th Edition", "7E"), new SetNameAbbreviation("8th Edition", "8E"), new SetNameAbbreviation("9th Edition", "9E"), new SetNameAbbreviation("10th Edition", "XE"), new SetNameAbbreviation("Alara Reborn", "AR"), new SetNameAbbreviation("Alliances", "AL"), new SetNameAbbreviation("Anthologies", "AT"), new SetNameAbbreviation("Apocalypse", "AP"), new SetNameAbbreviation("Arabian Nights", "AN"), new SetNameAbbreviation("Avacyn Restored", "AVR"), new SetNameAbbreviation("Battle for Zendikar", "BFZ"), new SetNameAbbreviation("Betrayers of Kamigawa", "BK"), new SetNameAbbreviation("Born of the Gods", "BNG"), new SetNameAbbreviation("Champions of Kamigawa", "CK"), new SetNameAbbreviation("Chronicles", "CH"), new SetNameAbbreviation("Classic Sixth Edition", "6E"), new SetNameAbbreviation("Coldsnap", "CS"), new SetNameAbbreviation("Conflux", "CF"), new SetNameAbbreviation("Conspiracy", "CNS"), new SetNameAbbreviation("Conspiracy: Take the Crown", "CN2"), new SetNameAbbreviation("Dark Ascension", "DKA"), new SetNameAbbreviation("Darksteel", "DS"), new SetNameAbbreviation("Dissension", "DI"), new SetNameAbbreviation("Dragon's Maze", "DGM"), new SetNameAbbreviation("Dragons of Tarkir", "DTK"), new SetNameAbbreviation("Duel Decks: Divine vs. Demonic", "DVD"), new SetNameAbbreviation("Duel Decks: Garruk vs. Liliana", "GVL"), new SetNameAbbreviation("Duel Decks: Jace vs. Chandra", "JVC"), new SetNameAbbreviation("Duel Decks: Phyrexia vs. The Coalition", "PVC"), new SetNameAbbreviation("Eldritch Moon", "EMN"), new SetNameAbbreviation("Eventide", "ET"), new SetNameAbbreviation("Exodus", "EX"), new SetNameAbbreviation("Fallen Empires", "FE"), new SetNameAbbreviation("Fate Reforged", "FRF"), new SetNameAbbreviation("Fifth Dawn", "FD"), new SetNameAbbreviation("Fifth Edition", "5E"), new SetNameAbbreviation("Fourth Edition", "4E"), new SetNameAbbreviation("Future Sight", "FS"), new SetNameAbbreviation("Gatecrash", "GTC"), new SetNameAbbreviation("Guildpact", "GP"), new SetNameAbbreviation("Homelands", "HL"), new SetNameAbbreviation("Ice Age", "IA"), new SetNameAbbreviation("Innistrad", "ISD"), new SetNameAbbreviation("Invasion", "IN"), new SetNameAbbreviation("Journey into Nyx", "JOU"), new SetNameAbbreviation("Judgment", "JM"), new SetNameAbbreviation("Kaladesh Inventions", "KLDI"), new SetNameAbbreviation("Kaladesh", "KLD"), new SetNameAbbreviation("Khans of Tarkir", "KTK"), new SetNameAbbreviation("Legions", "LE"), new SetNameAbbreviation("Lorwyn", "LW"), new SetNameAbbreviation("Magic 2010 (M10)", "M10"), new SetNameAbbreviation("Magic 2011 (M11)", "M11"), new SetNameAbbreviation("Magic 2012 (M12)", "M12"), new SetNameAbbreviation("Magic 2013 (M13)", "M13"), new SetNameAbbreviation("Magic 2014 (M14)", "M14"), new SetNameAbbreviation("Magic 2015 (M15)", "M15"), new SetNameAbbreviation("Magic Origins", "ORI"), new SetNameAbbreviation("Mercadian Masques", "MM"), new SetNameAbbreviation("Mirage", "MR"), new SetNameAbbreviation("Mirrodin", "MI"), new SetNameAbbreviation("Mirrodin Besieged", "MBS"), new SetNameAbbreviation("Morningtide", "MT"), new SetNameAbbreviation("Nemesis", "NS"), new SetNameAbbreviation("New Phyrexia", "NPH"), new SetNameAbbreviation("Oath of the Gatewatch", "OGW"), new SetNameAbbreviation("Odyssey", "OD"), new SetNameAbbreviation("Onslaught", "ON"), new SetNameAbbreviation("Planar Chaos", "PC"), new SetNameAbbreviation("Planechase", "PCH"), new SetNameAbbreviation("Planeshift", "PS"), new SetNameAbbreviation("Portal", "PO"), new SetNameAbbreviation("Portal Second Age", "PO2"), new SetNameAbbreviation("Premium Deck Series: Slivers", "PDS"), new SetNameAbbreviation("Prophecy", "PP"), new SetNameAbbreviation("Ravnica", "RA"), new SetNameAbbreviation("Return to Ravnica", "RTR"), new SetNameAbbreviation("Rise of the Eldrazi", "ROE"), new SetNameAbbreviation("Saviors of Kamigawa", "SK"), new SetNameAbbreviation("Scars of Mirrodin", "SOM"), new SetNameAbbreviation("Scourge", "SG"), new SetNameAbbreviation("Shadowmoor", "SM"), new SetNameAbbreviation("Shadows over Innistrad", "SOI"), new SetNameAbbreviation("Shards of Alara", "SA"), new SetNameAbbreviation("Stronghold", "SH"), new SetNameAbbreviation("Tempest", "TP"), new SetNameAbbreviation("Theros", "THS"), new SetNameAbbreviation("Time Spiral", "TS"), new SetNameAbbreviation("Timeshifted", "TT"), new SetNameAbbreviation("Torment", "TR"), new SetNameAbbreviation("Unglued", "UG"), new SetNameAbbreviation("Unhinged", "UH"), new SetNameAbbreviation("Urza's Destiny", "UD"), new SetNameAbbreviation("Urza's Legacy", "UY"), new SetNameAbbreviation("Urza's Saga", "US"), new SetNameAbbreviation("Visions", "VI"), new SetNameAbbreviation("Weatherlight", "WL"), new SetNameAbbreviation("Worldwake", "WWK"), new SetNameAbbreviation("Zendikar", "ZEN")};
	
	/**
	 * Gets a set name's abbreviation.
	 * 
	 * @param set
	 * @return A set's abbreviation in the form of a String
	 */
	public static String getSetAbbreviation(String set){
		for(int i=0; i<SET_LIST.length; i++){
			if(SET_LIST[i].getSetName().equals(set)){
				return SET_LIST[i].getAbbreviation();
			}
		}
		
		System.err.println("SetList: " + set + " was not found.");
		return null;
	}
	
	/**
	 * Gets a set abbreviation's name.
	 * 
	 * @param abbreviation
	 * @return A set's name in the form of an abbreviation.
	 */
	public static String getSetName(String abbreviation){
		for(int i=0; i<SET_LIST.length; i++){
			if(SET_LIST[i].getAbbreviation().equals(abbreviation)){
				return SET_LIST[i].getSetName();
			}
		}
		
		System.err.println("SetList: " + abbreviation + " was not found.");
		return null;
	}
	
	/**
	 * Gets an array of all the set names.
	 * 
	 * @return the list of all the set names as an array of Strings.
	 */
	public static String [] getSetNameList(){
		String [] nameList=new String[SET_LIST.length]; 
		
		for(int i=0; i<SET_LIST.length; i++){
			nameList[i]=SET_LIST[i].getSetName();
		}
		
		return nameList;
	}
	
	/**
	 * Gets an array of all the set abbreviations. 
	 * 
	 * @return the list of all the set abbreviations as an array of Strings.
	 */
	public static String [] getSetAbbreviationList(){
		String [] abbreviationList=new String[SET_LIST.length]; 
		
		for(int i=0; i<SET_LIST.length; i++){
			abbreviationList[i]=SET_LIST[i].getAbbreviation();
		}
		
		return abbreviationList;
	}
	
	
	/**
	 * Used to store set names with their abbreviation.
	 * 
	 * @author Samuel Scheffler
	 *
	 */
	private static class SetNameAbbreviation{
		private String setName;
		private String abbreviation;
		
		public SetNameAbbreviation(String setName, String abbreviation){
			this.setName=setName;
			this.abbreviation=abbreviation;
		}
		
		public String getSetName(){
			return setName;
		}
		
		public String getAbbreviation(){
			return abbreviation;
		}
	}
}
