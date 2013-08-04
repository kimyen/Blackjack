import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class Shoe {
	
	private static int NUM_CARDS = 8*52;
	private static int NUM_DECKS = 8;
	private ArrayList<Card> cards=new ArrayList<Card>();
	private int index=0;
	
	//constructor -- create a shoe
	public Shoe(){
		Deck deck = new Deck();
		for (int i=0; i<NUM_DECKS; i++)
			cards.addAll(deck.allCards());
		Collections.shuffle(cards);
	}
	
	public void shuffle()
	{
		index=0;
		Collections.shuffle(cards);
	}
	
	public Card draw()
	{
		if (index>415) throw new IllegalArgumentException("There is no cards left!");
		return cards.get(index++);
	}
	
	public int cardsAvailable()
	{
		return NUM_CARDS-index;
	}
	
	public String toString(){
		return "Shoe = "+cards.toString();
	}
	
	public ArrayList<Card> allCards(){
		return cards;
	}
	
	public static void main(String[] args)
	{
		Shoe shoe=new Shoe();
		System.out.println(shoe.toString());
		System.out.println("Draw a card = "+shoe.draw()+" "+shoe.cardsAvailable()+" card(s) are(is) available!");
		System.out.println("Draw a card = "+shoe.draw()+" "+shoe.cardsAvailable()+" card(s) are(is) available!");
	}

	public boolean loadDeck(File file) throws FileNotFoundException {
		Scanner sc=new Scanner(file);
		String str;
		String[] line;
		
		Card.Value v;
		Card.Suit s;
		
		ArrayList<Card> newCards=new ArrayList<Card>();
		
		while (sc.hasNext()){
			str=sc.next();
			line=str.split(",");
			if ((!Card.Value.has(line[0].toUpperCase()))||(!Card.Suit.has(line[1].toUpperCase()))){
				return false;
			}
			v=Card.Value.valueOf(line[0].toUpperCase());
			s=Card.Suit.valueOf(line[1].toUpperCase());
			newCards.add(new Card(s,v));
		}
		cards=newCards;
		index=0;
		return true;
	}

}
