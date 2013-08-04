import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;


public class BJGUI {
	private final static int SCREEN_WIDTH=1000;
	private final static int SCREEN_HEIGHT=500;	
	
	private JFrame frame = new JFrame("Black Jack - Designed by KimYen Truong");
	
	private JButton loadDeck=new JButton("Load Deck");
	private JButton rules=new JButton("How to Play");
	private ButtonGroup hitRule=new ButtonGroup();
	private JRadioButton H17=new JRadioButton("H17");
	private JRadioButton S17=new JRadioButton("S17");
	
	private JLabel balance=new JLabel("$ ");//Player's balance
	private JLabel currentBalance=new JLabel("Current Balance");
	
	private JButton dealButton=new JButton("Deal");
	
	private ButtonGroup recordDecision=new ButtonGroup();
	private JRadioButton record=new JRadioButton("Record");
	private JRadioButton stopRecording=new JRadioButton("Stop Recording");
	
	private JButton surrender=new JButton("Surrender");
	private JButton insurance=new JButton("Insurance");
	private JButton split=new JButton("Split");
	private JButton doubleDown=new JButton("Double");
	private JButton hit=new JButton("Hit");
	private JButton stand=new JButton("Stand");
	
	private JButton repeatBet=new JButton("Repeat Bet");
	private JLabel bet=new JLabel("$ ");//Player's bet
	private JLabel betLabel=new JLabel("Your Bet");
	
	private HashMap<String, AbstractButton> buttons=new HashMap<String, AbstractButton>();
	
	private JLabel n1;
	private JLabel n5;
	private JLabel n25;
	private JLabel n100;
	private JLabel n500;
	private JLabel n1000;
	
	private JLabel b1;
	private JLabel b5;
	private JLabel b25;
	private JLabel b100;
	private JLabel b500;
	private JLabel b1000;
	
	private HashMap<Integer, JLabel> chips;
	
	private ArrayList<JLabel> playerCards=new ArrayList<JLabel>();
	private ArrayList<JLabel> dealerCards=new ArrayList<JLabel>();
	
	private JLabel blackJack=new JLabel("BLACKJACK!");
	private JLabel bust=new JLabel("Bust!");
	private JLabel YouLose=new JLabel("You Lose!");
	private JLabel YouWin=new JLabel("You Win!");
	private JLabel push=new JLabel("Push");
	private JLabel YouSurrender=new JLabel("You Surrender!");
	private JLabel YouGotInsurance=new JLabel("You Got Insurance!");
	
	private HashMap<String, JLabel> labels=new HashMap<String, JLabel>();
	
	private JMenuBar menuBar=new JMenuBar();
	private JMenu chooseYourDealer=new JMenu("Choose Your Dealer");
	private JMenu options=new JMenu("Choose Your Dealer Today");
	private JMenuItem dealersSchedule=new JMenuItem("Dealers' Schedule");
	private JMenuItem Vin=new JMenuItem("Vin");
	private JMenuItem KimYen=new JMenuItem("KimYen");
	
	private HashMap<String, JMenuItem> menuItems = new HashMap<String, JMenuItem>();
	
	public void initialCards(){
		for(int i=0;i<5;i++){
			playerCards.add(new JLabel(new ImageIcon(getClass().getResource("cards/cards.png"))));
			dealerCards.add(new JLabel(new ImageIcon(getClass().getResource("cards/cards.png"))));
		}
	}
	
	public void resetCards(){
		for(int i=0;i<5;i++){
			playerCards.get(i).setIcon(new ImageIcon(getClass().getResource("cards/cards.png")));
			dealerCards.get(i).setIcon(new ImageIcon(getClass().getResource("cards/cards.png")));
		}
	}
	
	public BJGUI(){
		frame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setChips();
		initialCards();
		SetLayouts();
		frame.setVisible(true);
		createButtonsMap();
		createChipsMap();
		createLabelsMap();
		createMenuItemsMap();
		initialDisable();
		initialDisableLabels();
		frame.setResizable(false);
	}
	
	private void createMenuItemsMap() {
		// TODO Auto-generated method stub
		menuItems.put("Schedule", this.dealersSchedule);
		menuItems.put("Vin", this.Vin);
		menuItems.put("KimYen", this.KimYen);
	}

	public void initialDisableLabels() {
		// TODO Auto-generated method stub
		blackJack.setVisible(false);
		bust.setVisible(false);
		YouLose.setVisible(false);
		YouWin.setVisible(false);
		push.setVisible(false);
		YouSurrender.setVisible(false);
		YouGotInsurance.setVisible(false);
	}
	
	public void enableLabels(String str){
		if (labels.containsKey(str)){
			labels.get(str).setVisible(true);
		}
		else throw new IllegalArgumentException("Label is not found!");
	}
	
	public void disableLabels(String str){
		if (labels.containsKey(str)){
			labels.get(str).setVisible(false);
		}
		else throw new IllegalArgumentException("Label is not found!");
	}

	private void createLabelsMap() {
		// TODO Auto-generated method stub
		labels.put("BlackJack", blackJack);
		labels.put("Bust", bust);
		labels.put("You Lose", YouLose);
		labels.put("You Win", YouWin);
		labels.put("Push", push);
		labels.put("You Surrender", YouSurrender);
		labels.put("You Got Insurance", YouGotInsurance);
	}

	public void newGame(int balance, int bet){
		initialDisable();
		if (balance>=bet){
			this.enableButton("Repeat Bet");
		}
		setBalance(balance);
		setBet(0);
		updateChips(balance, 0);
	}
	
	public void blockChips(){
		this.disableChip(1);
		this.disableChip(5);
		this.disableChip(25);
		this.disableChip(100);
		this.disableChip(500);
		this.disableChip(1000);
		this.disableChip(-1);
		this.disableChip(-5);
		this.disableChip(-25);
		this.disableChip(-100);
		this.disableChip(-500);
		this.disableChip(-1000);
	}
	
	private void initialDisable(){
		
		this.disableButton("Surrender");
		this.disableButton("Insurance");
		this.disableButton("Split");
		this.disableButton("Double Down");
		this.disableButton("Hit");
		this.disableButton("Stand");
		this.disableButton("Deal Button");
		this.disableButton("Repeat Bet");
		
		this.disableChip(-1);
		this.disableChip(-5);
		this.disableChip(-25);
		this.disableChip(-100);
		this.disableChip(-500);
		this.disableChip(-1000);
	}

	private void setChips() {
		// TODO Auto-generated method stub
		n1=createChips("1");
		n5=createChips("5");
		n25=createChips("25");
		n100=createChips("100");
		n500=createChips("500");
		n1000=createChips("1000");

		b1=createChips("1");
		b5=createChips("5");
		b25=createChips("25");
		b100=createChips("100");
		b500=createChips("500");
		b1000=createChips("1000");
	}

	private void createButtonsMap() {
		buttons.put("Load Deck",loadDeck);
		buttons.put("Rules", rules);
		buttons.put("H17", H17);
		buttons.put("S17", S17);
		buttons.put("Deal Button", dealButton);
		buttons.put("Record", record);
		buttons.put("Stop Recording", stopRecording);
		buttons.put("Surrender", surrender);
		buttons.put("Insurance", insurance);
		buttons.put("Split", split);
		buttons.put("Double Down", doubleDown);
		buttons.put("Hit", hit);
		buttons.put("Stand", stand);
		buttons.put("Repeat Bet", repeatBet);
		
	}
	
	private void createChipsMap() {
		
		chips=new HashMap<Integer, JLabel>();
		chips.put(-1,n1);
		chips.put(-5,n5);
		chips.put(-25,n25);
		chips.put(-100,n100);
		chips.put(-500,n500);
		chips.put(-1000,n1000);
		
		chips.put(1,b1);
		chips.put(5,b5);
		chips.put(25,b25);
		chips.put(100,b100);
		chips.put(500,b500);
		chips.put(1000,b1000);
	}
	
	public void assignAction(String button, ActionListener action){
		if (buttons.containsKey(button)){
			buttons.get(button).addActionListener(action);	
		}
		else throw new IllegalArgumentException("Button is not found!");
	}
	
	public void assignMenuAction(String menu, ActionListener action){
		if (menuItems.containsKey(menu)){
			menuItems.get(menu).addActionListener(action);	
		}
		else throw new IllegalArgumentException("Menu Item is not found!");
	}
	
	private void SetLayouts() {
		frame.setLayout(new BorderLayout());
		
		JPanel leftOptions=new JPanel(new GridLayout(4,1));
		leftOptions.add(loadDeck);
		leftOptions.add(rules);	
		hitRule.add(H17);
		hitRule.add(S17);
		leftOptions.add(H17);
		leftOptions.add(S17);
		
		JPanel playersBalance=new JPanel(new GridLayout(2,1));
		playersBalance.add(balance);
		playersBalance.add(currentBalance);
				
		JPanel left=new JPanel(new BorderLayout());
		left.add(BorderLayout.NORTH, leftOptions);
		left.add(BorderLayout.SOUTH, playersBalance);
		
		JPanel dealersHand=new JPanel(new FlowLayout());
		for(JLabel l:dealerCards){
			dealersHand.add(l);
		}
		
		JPanel playersHand=new JPanel(new FlowLayout());
		for(JLabel l:playerCards){
			playersHand.add(l);
		}
		
		JPanel playersBet=new JPanel(new FlowLayout());
		playersBet.add(n1);
		playersBet.add(n5);
		playersBet.add(n25);
		playersBet.add(n100);
		playersBet.add(n500);
		playersBet.add(n1000);
		
		JPanel status=new JPanel(new FlowLayout());
		status.add(blackJack);
		status.add(bust);
		status.add(YouLose);
		status.add(YouWin);
		status.add(push);
		status.add(YouSurrender);
		status.add(YouGotInsurance);
		
		JPanel player=new JPanel(new GridLayout(3,1));
		player.add(playersHand);
		player.add(status);
		player.add(playersBet);
		
		JPanel deal=new JPanel(new FlowLayout());
		deal.add(dealButton);
		
		JPanel center=new JPanel(new BorderLayout());
		center.add(BorderLayout.NORTH, dealersHand);
		center.add(BorderLayout.CENTER, player);
		center.add(BorderLayout.SOUTH, deal);
		
		JPanel rightOptions=new JPanel(new GridLayout(8,1));
		recordDecision.add(record);
		recordDecision.add(stopRecording);
		rightOptions.add(record);
		rightOptions.add(stopRecording);
		rightOptions.add(surrender);
		rightOptions.add(insurance);
		rightOptions.add(split);
		rightOptions.add(doubleDown);
		rightOptions.add(hit);
		rightOptions.add(stand);
		
		JPanel betOptions=new JPanel(new GridLayout(1,6));
		betOptions.add(b1);
		betOptions.add(b5);
		betOptions.add(b25);
		betOptions.add(b100);
		betOptions.add(b500);
		betOptions.add(b1000);
		
		JPanel betCenter=new JPanel(new GridLayout(4,1));
		betCenter.add(betOptions);
		betCenter.add(repeatBet);
		betCenter.add(bet);
		betCenter.add(betLabel);
		
		JPanel right=new JPanel(new BorderLayout());
		right.add(BorderLayout.NORTH, rightOptions);
		right.add(BorderLayout.SOUTH, betCenter);
		
		options.add(Vin);
		options.add(KimYen);
		
		chooseYourDealer.add(dealersSchedule);
		chooseYourDealer.add(options);
		
		menuBar.add(chooseYourDealer);
		
		frame.add(BorderLayout.WEST, left);
		frame.add(BorderLayout.CENTER, center);
		frame.add(BorderLayout.EAST, right);
		frame.setJMenuBar(menuBar);
		
	}
	
	public void setBalance(int playersBalance){
		balance.setText("$ "+playersBalance);
	}
	
	public void setBet(int bet){
		this.bet.setText("$ "+bet);
	}
	public static void main(String[] args){
		BJGUI gui=new BJGUI();
	}

	public void disableButton(String button) {
		// TODO Auto-generated method stub
		if (buttons.containsKey(button)){
			buttons.get(button).setEnabled(false);
		}
		else throw new IllegalArgumentException("Button is not found!");

	}
	
	public void enableButton(String button) {
		// TODO Auto-generated method stub
		if (buttons.containsKey(button)){
			buttons.get(button).setEnabled(true);
		}
		else throw new IllegalArgumentException("Button is not found!");

	}
	
	public void disableChip(int label){
		// TODO Auto-generated method stub
		if (chips.containsKey(label)){
			chips.get(label).setVisible(false);
		}
		else throw new IllegalArgumentException("Chip is not found!");
	}
	
	public void enableChip(int label) {
		// TODO Auto-generated method stub
		if (chips.containsKey(label)){
			chips.get(label).setVisible(true);
		}
		else throw new IllegalArgumentException("Chip is not found!");

	}
	
	public JLabel createChips(String str){
		String s="chips/"+str+".png";
		JLabel l=new JLabel(new ImageIcon(getClass().getResource(s)));
		return l;
	}
	
	public ImageIcon createCards(String str){
		String s="cards/"+str+".gif";
		return new ImageIcon(getClass().getResource(s));
	}

	public void assignLabelsAction(int label, MouseListener listener) {
		// TODO Auto-generated method stub
		if (chips.containsKey(label)){
			chips.get(label).addMouseListener(listener);	
		}
		else throw new IllegalArgumentException("Chip is not found!");
	}

	public void updateChips(int balance, int bet) {
		// TODO Auto-generated method stub
		Set<Integer> chip=chips.keySet();
		for(int i: chip){
			if (((i>0)&&(i>=balance))||((i<0)&&(Math.abs(i)>=bet))){
				this.disableChip(i);
			}
			if (((i>0)&&(i<=balance))||((i<0)&&(Math.abs(i)<=bet))){
				this.enableChip(i);
			}
		}
		
	}

	public void updateDeal(int bet) {
		// TODO Auto-generated method stub
		if (bet>0){
			this.enableButton("Deal Button");
		}
		else{
			this.disableButton("Deal Button");
		}
	}

	public void updatePlayersHand(ArrayList<String> playersHand) {
		// TODO Auto-generated method stub
		int i=0;
		for (String s:playersHand){
			this.playerCards.get(i).setIcon(createCards(s));
			i++;
		}
	}
	
	public void initialDealersHand(ArrayList<String> dealersHand) {
		// TODO Auto-generated method stub
		this.dealerCards.get(0).setIcon(new ImageIcon(getClass().getResource("cards/b1fv.gif")));
		this.dealerCards.get(1).setIcon(createCards(dealersHand.get(1)));		
	}
	
	public void updateDealersHand(ArrayList<String> dealersHand) {
		// TODO Auto-generated method stub
		int i=0;
		for (String s:dealersHand){
			this.dealerCards.get(i).setIcon(createCards(s));
			i++;
		}
	}

	public void gameOver() {
		// TODO Auto-generated method stub
		initialDisableLabels();
		initialDisable();
		this.disableChip(1);
		this.disableChip(5);
		this.disableChip(25);
		this.disableChip(100);
		this.disableChip(500);
		this.disableChip(1000);
		disableButton("Load Deck");
		disableButton("Rules");
	}

	public void clearSelection() {
		// TODO Auto-generated method stub
		recordDecision.clearSelection();
	}
}
