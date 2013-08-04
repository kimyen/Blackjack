import java.util.ArrayList;


public class Player implements Comparable<Player> {
	
	private String name;
	private static int INITIAL_MONEY=1000;
	private int bet;
	private int balance;
	private ArrayList<Card> hand;
	
	public Player(){
		this.hand=new ArrayList<Card>();
		this.balance=INITIAL_MONEY;
		this.bet=0;
	}
	
	public Player(String name){
		this.name=name;
		this.hand=new ArrayList<Card>();
		this.balance=INITIAL_MONEY;
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setBet(int bet){
		this.bet=bet;
	}
	
	public void setBalance(int balance){
		this.balance=balance;
	}
	
	public int getBet(){
		return this.bet;
	}
	
	public int getBalance(){
		return this.balance;
	}
	
	public ArrayList<Card> getHand(){
		return this.hand;
	}
	
	public Card drawCard(Card card){
		this.hand.add(card);
		return card;
	}
	
	public String toString(){
		return this.hand.toString();
	}
	
	public void win(boolean blackjack){
		if (blackjack){
			this.balance+=this.bet+this.bet*3/2;
		}
		else {
			this.balance+=this.bet*2;	
		}
	}
	
	public int getScore(){
		int score=0;
		for(Card card: this.hand){
			score+=card.getScore();
		}
		return score;
	}
	
	public int getAceScore(){
		int score=0;
		boolean ace=false;
		for(Card card: this.hand){
			if ((card.getScore()==1)&&(!ace)){
				score+=11;
				ace=true;
			}
			else {
			score+=card.getScore();
			}
		}
		return score;
	}
	
	public boolean available(){
		if (this.balance<=0){
			return false;
		}
		return true;
	}

	@Override
	public int compareTo(Player player2) {
		// TODO Auto-generated method stub
		int score=this.getAceScore();
		if (score>22){
			score=this.getScore();
		}
		
		int score2=player2.getAceScore();
		if (score2>22){
			score2=player2.getScore();
		}
		
		return score-score2;
	}

	public boolean blackJack(){
		if ((this.hand.size()==2)&&(this.getAceScore()==21)){
			return true;
		}
		return false;
	}
	
	public boolean bust(){
		if (this.getScore()>21){
			return true;
		}
		return false;
	}

	public int clearHand() {
		// TODO Auto-generated method stub
		this.hand.clear();
		return this.bet;
	}

	public void push() {
		// TODO Auto-generated method stub
		this.balance+=this.bet;
	}

}
