import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;



import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;


public class Controller {
	
	private JFileChooser fc;
	private BJGUI gui;
	private BJ bj;
	private int oldBet;
	private String link;
	private static final String rule=" Welcome to Autumn Casino! \n" +
						"\n" +
						"Guess what? You are the lucky customer of the month! \n" +
						"$1000 has been sent into your Autumn account. \n" +
						"\n" +
						"Let's start! Click on the chips to place your bet then click \"Deal\" to play! \n" +
						"(The next time, you can repeat your bet by clicking on the button \"Repeat Bet\" on the bottom right.) \n" +
						"You can always adjust your bet by clicking on the chips under your hand to remove them from your bet. \n" +
						"You can also add to your bet by clicking on the chips on the right. \n" +
						"Your balance will be dipslayed on the bottom left and your bet will be displayed on the bottom right. \n" +
						"\n" +
						"NOW CLICK ON THE CHIPS TO PLACE YOUR BET AND BECOME A TRILLIONAIRE!!!! \n" +
						"GOOD LUCK!!!!";
						
	
	private class LoadDeck implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			fc=new JFileChooser(); 
			int val=fc.showOpenDialog(null);
			if (val== JFileChooser.APPROVE_OPTION) {
	            File file = fc.getSelectedFile();
	            try {
					if (!bj.loadDeck(file)){
						JOptionPane.showMessageDialog(null, "Oops!!! The file you chose does not content the correct default deck. \n Please double check and choose another file!");
					}
					else {
						JOptionPane.showMessageDialog(null, "Decks loaded successfully!");
					}
					
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	
	private class Rules implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			JOptionPane.showMessageDialog(null, rule);
		}
	}

	private class H17 implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			bj.setH17(true);	
		}
	}
	
	private class S17 implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			bj.setH17(false);
		}
	}

	private void gameOver(){
		gui.gameOver();
		JOptionPane.showMessageDialog(null, "Game Over!");
	}
	
	private class Deal implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			gui.blockChips();
			bj.initialDeal();
			gui.disableButton("Deal Button");
			gui.updatePlayersHand(bj.getPlayersHand());
			gui.initialDealersHand(bj.getDealersHand());
			if (bj.getPlayersAceScore()==21){
				if (bj.dealerBJ()){
					gui.updateDealersHand(bj.getDealersHand());
					bj.push();
					gui.enableLabels("Push");
					oldBet=newGame(bj.getBalance());
				}
				else {
					gui.enableLabels("BlackJack");
					int balance = bj.payment(true);
					gui.updateDealersHand(bj.getDealersHand());
					oldBet=newGame(balance);
				}		
			}
			else {
				gui.enableButton("Hit");
				gui.enableButton("Stand");
				if (bj.getBalance()>=bj.getBet()){
					gui.enableButton("Double Down");
				}
				gui.enableButton("Surrender");
				if ((bj.getDealersHand().get(1).substring(1)).equals("1")){
					gui.enableButton("Insurance");
				}
				if ((bj.getPlayersHand().get(1).substring(1)).equals(bj.getPlayersHand().get(0).substring(1))){
					gui.enableButton("Split");
				}
			}
			
		}

		
	}
	
	private int newGame(int balance) {
		// TODO Auto-generated method stub

		int bet=bj.newGame();
		if (!bj.available()){
			gameOver();
		}
		else {
			gui.newGame(balance, bet);	
		}
		return bet;
	}
	
	private class Record implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			if (link.equals("")){
				fc=new JFileChooser(); 
				int val=fc.showSaveDialog(null);
				if (val== JFileChooser.APPROVE_OPTION) {
		            File file = fc.getSelectedFile();
		            link=file.getAbsolutePath();
		            if (checkName(link)){
						bj.setRecord(true, link);
						JOptionPane.showMessageDialog(null, "Your data will be save to "+link);
					}
					else {
						link="";
						JOptionPane.showMessageDialog(null, "Oops!!! The file you chose does not have the correct default. \n Please double check and choose another file! \n File should be named .csv");
						gui.clearSelection();
					}
				}
			}
			else {
				bj.setRecord(true, link);
			}
		}

		private boolean checkName(String link) {
			// TODO Auto-generated method stub
			String str=link.substring(link.length()-4,link.length());
			if (str.equals(".csv")) {
				return true;
			}
			//link="";
			return false;
		}
	}

	private class StopRecording implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			bj.setRecord(false, link);
		}
	}

	private class Surrender implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			bj.setBalance(bj.getBalance()+bj.getBet()/2);
			gui.enableLabels("You Surrender");
			oldBet=newGame(bj.getBalance());
		}
	}

	private class Insurance implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			JOptionPane.showMessageDialog(null, "This feature is only available in the premium version. Please send $19.95 to KimYen's bank account to enable this feature!");
			gui.disableButton("Insurance");
		}
	}

	private class Split implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			JOptionPane.showMessageDialog(null, "This feature is only available in the premium version. Please send $19.95 to KimYen's bank account to enable this feature!");
			gui.disableButton("Split");
		}
	}
	
	private class DoubleDown implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			
			//hit once
			if (bj.dealerBJ()){
				gui.updateDealersHand(bj.getDealersHand());
				gui.enableLabels("You Lose");
				oldBet=newGame(bj.getBalance());
			}
			else {	
				if (bj.cardsAvailable()<1){
					gameOver();
				}
				
				bj.setBalance(bj.getBalance()-bj.getBet());
				gui.setBalance(bj.getBalance());
				bj.setBet(bj.getBet()*2);
				gui.setBet(bj.getBet());
				
				boolean bust=bj.hit();
				gui.updatePlayersHand(bj.getPlayersHand());
				
				if (bust){
					gui.enableLabels("Bust");
					oldBet=newGame(bj.getBalance())/2;
				}		
				else {
					//stand
					if (bj.cardsAvailable()<3){
						gameOver();
					}
					int dealerWin=bj.dealersTurn();
					gui.updateDealersHand(bj.getDealersHand());
					if (dealerWin>0){
						gui.enableLabels("You Lose");
					}
					else if (dealerWin==0){
						bj.push();
						gui.enableLabels("Push");
					}
					else{
						gui.enableLabels("You Win");
						bj.payment(false);
					}
					
					oldBet=newGame(bj.getBalance())/2;

				}
			}
			
		}
	}

	private class Hit implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			gui.disableButton("Surrender");
			gui.disableButton("Insurance");
			gui.disableButton("Split");
			gui.disableButton("Double Down");
			if (bj.dealerBJ()){
				gui.updateDealersHand(bj.getDealersHand());
				gui.enableLabels("You Lose");
				oldBet=newGame(bj.getBalance());
			}
			else {	
				if (bj.cardsAvailable()<1){
					gameOver();
				}
				boolean bust=bj.hit();
				gui.updatePlayersHand(bj.getPlayersHand());
				
				if (bust){
					gui.disableButton("Stand");
					gui.enableLabels("Bust");
					oldBet=newGame(bj.getBalance());
				}
				else if (bj.getPlayersHand().size()==5){
					gui.disableButton("Stand");
					gui.enableLabels("You Win");
					bj.payment(false);
					oldBet=newGame(bj.getBalance());
				}
			}
			
		}
	}

	private class Stand implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			gui.disableButton("Surrender");
			gui.disableButton("Insurance");
			gui.disableButton("Split");
			gui.disableButton("Double Down");
			gui.disableButton("Hit");
			gui.disableButton("Stand");
			if (bj.cardsAvailable()<3){
				gameOver();
			}
			int dealerWin=bj.dealersTurn();
			gui.updateDealersHand(bj.getDealersHand());
			if (dealerWin>0){
				gui.enableLabels("You Lose");
			}
			else if (dealerWin==0){
				bj.push();
				gui.enableLabels("Push");
			}
			else{
				gui.enableLabels("You Win");
				bj.payment(false);
			}
			oldBet=newGame(bj.getBalance());
		}
	}

	private class RepeatBet implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			gui.disableButton("Repeat Bet");
			gui.setBet(oldBet);
			bj.setBet(oldBet);
			bj.setBalance(bj.getBalance()-oldBet);
			gui.setBalance(bj.getBalance());
			gui.initialDisableLabels();
			gui.enableButton("Deal Button");
			gui.resetCards();
			gui.updateChips(bj.getBalance(), oldBet);
		}
	}

	
	public Controller(){
		
		gui=new BJGUI();
		bj=new BJ();
		gui.setBalance(bj.getBalance());
		gui.setBet(bj.getBet());
		link="";
		initialAssignAction();
	}

	private class Bet implements MouseListener {

		private int value;
		
		public Bet(int i){
			this.value=i;
		}
		
		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			gui.initialDisableLabels();
			gui.resetCards();
			gui.disableButton("Repeat Bet");
			
			int bet=bj.getBet()+value;
			int balance=bj.getBalance()-value;
			
			bj.setBet(bet);
			bj.setBalance(balance);
			gui.setBalance(bj.getBalance());
			gui.setBet(bj.getBet());
			
			gui.updateChips(balance, bet);
			gui.updateDeal(bet);
	
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	private class Schedule implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			JOptionPane.showMessageDialog(null, "Sorry! You do not have the permission to look at dealers' schedule!");
		}
	}

	private class Vin implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			JOptionPane.showMessageDialog(null, "Hello! I'm Vin. It's my pleasure to serve you today!");
		}
	}
	
	private class KimYen implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			JOptionPane.showMessageDialog(null, "Sorry! KimYen is not available today! Please choose another dealer!");
		}
	}
	
	private void initialAssignAction() {
		// TODO Auto-generated method stub
		gui.assignAction("Load Deck", new LoadDeck());
		gui.assignAction("Rules", new Rules());
		gui.assignAction("H17", new H17());
		gui.assignAction("S17", new S17());
		gui.assignAction("Deal Button", new Deal());
		gui.assignAction("Record", new Record());
		gui.assignAction("Stop Recording", new StopRecording());
		gui.assignAction("Surrender", new Surrender());
		gui.assignAction("Insurance", new Insurance());
		gui.assignAction("Split", new Split());
		gui.assignAction("Double Down", new DoubleDown());
		gui.assignAction("Hit", new Hit());
		gui.assignAction("Stand", new Stand());
		gui.assignAction("Repeat Bet", new RepeatBet());
		
		gui.assignLabelsAction(1, new Bet(1));
		gui.assignLabelsAction(5, new Bet(5));
		gui.assignLabelsAction(25, new Bet(25));
		gui.assignLabelsAction(100, new Bet(100));
		gui.assignLabelsAction(500, new Bet(500));
		gui.assignLabelsAction(1000, new Bet(1000));
		
		gui.assignLabelsAction(-1, new Bet(-1));
		gui.assignLabelsAction(-5, new Bet(-5));
		gui.assignLabelsAction(-25, new Bet(-25));
		gui.assignLabelsAction(-100, new Bet(-100));
		gui.assignLabelsAction(-500, new Bet(-500));
		gui.assignLabelsAction(-1000, new Bet(-1000));
		
		gui.assignMenuAction("Schedule", new Schedule());
		gui.assignMenuAction("Vin", new Vin());
		gui.assignMenuAction("KimYen", new KimYen());
	}

	public static void main(String[] agrs){
		Controller c=new Controller();
	}
}
