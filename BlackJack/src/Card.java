/*KimYen Truong
 *CS211
 *Assignment #3
*/

public class Card implements Comparable<Card>
{
	public enum Suit 
	{
		CLUB("c"),
		DIAMOND("d"),
		HEART("h"),
		SPADE("s");
		
		private String value;
		
		private Suit(String s){
			this.value=s;
		}
		
		public String getValue(){
			return this.value;
		}

		public static boolean has(String s) {
			// TODO Auto-generated method stub
			String[] suit={"CLUB","DIAMOND","HEART","SPADE"};
			for(String str:suit){
				if (s.equals(str)){
					return true;
				}
			}
			return false;
		}
	}
	
	public enum Value
	{
		TWO("2"),
		THREE("3"),
		FOUR("4"),
		FIVE("5"),
		SIX("6"),
		SEVEN("7"),
		EIGHT("8"),
		NINE("9"),
		TEN("10"),
		JACK("j"),
		QUEEN("q"),
		KING("k"),
		ACE("1"),;
		
		private String value;
		
		private Value(String s){
			this.value=s;
		}

		public String getValue(){
			return this.value;
		}
		
		public static boolean has(String val) {
			// TODO Auto-generated method stub
			String[] value={"TWO","THREE","FOUR","FIVE","SIX","SEVEN","EIGHT","NINE","TEN","JACK","QUEEN","KING","ACE"};
			for (String v:value){
				if (val.equals(v)){
					return true;
				}
			}
			return false;
		}

	}
	
	private Suit suit;
	private Value value;
	
	//constructor
	public Card(Suit suit, Value value){
		this.suit=suit;
		this.value=value;
	}
	/**
     * @return the suit
     */
    public Suit getSuit()
    {
    	return suit;
    }

	/**
     * @return the value
     */
    public Value getValue()
    {
    	return value;
    }

    public int getScore()
    {
    	if (this.value.ordinal()==12){
    		return 1;
    	}
    	if (this.value.ordinal()>7){
    		return 10;
    	}
    	return value.ordinal()+2;
    }

	@Override
	/*compare 2 cards in natural order
	 * return -1 if the card is smaller than another card
	 * return 1 if the card is bigger than another card
	 * return 0 if two cards are equal 
	 */
    public int compareTo(Card card2)
    {
		int compare = this.value.ordinal() - card2.getValue().ordinal(); //compare values
		if(compare == 0) //values are the same 
			compare = this.suit.ordinal() - card2.getSuit().ordinal(); //compare suits
		return compare;
    }
	
	//return card's suit and value
	public String toString(){
		return value.name()+" "+suit.name();
	}
	
}
