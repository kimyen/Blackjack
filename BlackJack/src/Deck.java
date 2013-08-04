import java.util.ArrayList;
import java.util.Collections;


/*KimYen Truong
 *CS211
 *Assignment #3
*/

public class Deck 
{	
	private static int NUM_CARDS = 52;
	private ArrayList<Card> cards=new ArrayList<Card>();
	
	//index keeps tract of the next available card to draw from the deck
	private static int index=0;
	
	//constructor -- create a deck
	public Deck(){
		for(Card.Suit suit : Card.Suit.values())
			for(Card.Value value : Card.Value.values())
				cards.add(new Card(suit,value));
	}
	
	/**
	 * Shuffles a deck of cards so that all 52 cards are randomly distributed.
	 */
	public void shuffle()
	{
		index=0;
		Collections.shuffle(cards);
	}
	
	/**
	 * Draws a single card
	 * @return The card drawn out of the deck
	 */
	public Card draw()
	{
		if (index>51) throw new IllegalArgumentException("There is no cards left!");
		return cards.get(index++);
	}
	
	/**
	 * Sort the cards in the order A-K Spades, A-K Hearts, A-K Diamonds, A-K Clubs
	 */
	public void sort()
	{	
		index=0;
		
		//sort the cards in natural order : 
		//2 CLUB, 2 DIAMOND, 2 HEART, 2 SPADE ... ACE CLUB, ACE DIAMOND, ACE HEART, ACE SPADE
		Collections.sort(cards);
		
		//create lists of cards
		ArrayList<Card> cardsPerSuit =new ArrayList<Card>();
		ArrayList<Card> fakeDeck = new ArrayList<Card>();
		
		for(Card.Suit suit : Card.Suit.values()){
			for(int i = NUM_CARDS - (suit.ordinal() + 1); i >= 0; i -= 4 ){ 
				cardsPerSuit.add(cards.get(i));
			}
			Collections.reverse(cardsPerSuit);
			Collections.rotate(cardsPerSuit, 1); //moves Ace to front of list
			fakeDeck.addAll(cardsPerSuit);
			cardsPerSuit.clear();
		}
			
		//set the value of the fake deck to cards
		cards=fakeDeck;
	}
	
	public int cardsAvailable()
	{
		return NUM_CARDS-index;
	}
	
	public String toString(){
		return "Deck = "+cards.toString();
	}
	
	public ArrayList<Card> allCards(){
		return cards;
	}
	
	public static void main(String[] args)
	{
	    Deck deck=new Deck();
	    System.out.println(deck.toString());
	    deck.shuffle();
	    System.out.println("After shuffle:");
	    System.out.println(deck.toString());
	    for(int i=0;i<52;i++)
	    	System.out.println("Draw a card = "+deck.draw()+" "+deck.cardsAvailable()+" card(s) are(is) available!");
	    deck.sort();
	    System.out.println("After sort:");
	    System.out.println(deck.toString());
	}
}
