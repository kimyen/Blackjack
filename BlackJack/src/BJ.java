import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;


public class BJ {
	private Shoe shoe;
	private Player dealer;
	private Player player;
	private Boolean H17;
	private boolean record;
	private String result, link;

	public BJ(){
		this.shoe=new Shoe();
		this.dealer=new Player("Dealer");
		this.player=new Player("Player");
		this.H17=false;
		this.record=false;
		this.result="";
	}
	
	public int cardsAvailable(){
		return shoe.cardsAvailable();
	}
	
	public void initialDeal(){
		this.player.drawCard(this.shoe.draw());
		this.dealer.drawCard(this.shoe.draw());
		this.player.drawCard(this.shoe.draw());
		this.dealer.drawCard(this.shoe.draw());
	}
	
	public void setH17(Boolean b){
		this.H17=b;
	}
	
	public String toString(){
		String s=this.shoe.toString();
		s+="\n"+"Dealer has "+this.dealer.getHand().toString();
		s+="\n"+"Player has "+this.player.getHand().toString();
		s+="\n";
		return s;
	}
		
	public static void main(String[] args){
		BJ bj=new BJ();
		bj.initialDeal();
		System.out.println("Dealer has "+bj.dealer.getHand().toString()+" Score "+bj.dealer.getScore()+" Ace Score "+bj.dealer.getAceScore());
		System.out.println("Player has "+bj.player.getHand().toString()+" Score "+bj.player.getScore()+" Ace Score "+bj.player.getAceScore());
	}

	public boolean loadDeck(File file) throws FileNotFoundException {
		return shoe.loadDeck(file);
	}

	public boolean hit() {
		// TODO Auto-generated method stub
		this.player.drawCard(this.shoe.draw());
		return player.bust();
	}
	
	public int newGame(){
		int oldBet= this.player.clearHand();
		this.dealer.clearHand();
		this.player.setBet(0);
		if (this.record){
			this.result+="1,"+oldBet+","+this.player.getBalance()+" \n ";
			writeResults();
		}
		return oldBet;
	}
	
	private void writeResults() {
		// TODO Auto-generated method stub
		Writer output = null;
		File file = new File(link);
		try {
			output = new BufferedWriter(new FileWriter(file));
			output.write(result);
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public boolean dealerBJ(){
		return dealer.blackJack();
	}

	public int payment(boolean blackjack) {
		// TODO Auto-generated method stub
		this.player.win(blackjack);
		return this.player.getBalance();
	}

	public int dealersTurn() {
		// TODO Auto-generated method stub
		while ((dealer.getAceScore()<17)||
				((dealer.getAceScore()!=dealer.getScore())&&(H17)&&(dealer.getAceScore()==17))||
				((dealer.getAceScore()>21)&&(dealer.getScore()<17))){
			dealer.drawCard(shoe.draw());
			if ((dealer.getHand().size()==5)&&(dealer.getScore()<22)){
				return 1;
			}
		}
		if (dealer.getScore()>21){
			return -1;
		}
		
		int p=player.getAceScore();
		if (p>21){
			p=player.getScore();
		}
		int d=dealer.getAceScore();
		if (d>21){
			d=dealer.getScore();
		}
		return d-p;
	}

	public void push(){
		this.player.push();
	}
	
	public int getBalance() {
		// TODO Auto-generated method stub
		return this.player.getBalance();
	}

	public int getBet() {
		// TODO Auto-generated method stub
		return this.player.getBet();
	}
	
	public void setBet(int bet){
		this.player.setBet(bet);
	}
	
	public void setBalance(int balance){
		this.player.setBalance(balance);
	}
	
	public ArrayList<String> getPlayersHand(){
		ArrayList<String> str=new ArrayList<String>();
		ArrayList<Card> cards=this.player.getHand();
		for(Card c:cards){
			String s=c.getSuit().getValue()+c.getValue().getValue();
			str.add(s);
		}
		return str;
	}
	
	public ArrayList<String> getDealersHand(){
		ArrayList<String> str=new ArrayList<String>();
		ArrayList<Card> cards=this.dealer.getHand();
		for(Card c:cards){
			String s=c.getSuit().getValue()+c.getValue().getValue();
			str.add(s);
		}
		return str;
	}
	
	public int getPlayersAceScore(){
		return this.player.getAceScore();
	}

	public boolean available() {
		// TODO Auto-generated method stub
		if ((shoe.cardsAvailable()>4)&&(player.available())){
			return true;
		}
		return false;
	}

	public void setRecord(boolean b, String link) {
		// TODO Auto-generated method stub
		this.record=b;
		this.link=link;
	}
}
