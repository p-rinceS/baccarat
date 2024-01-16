import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class BaccaratDealer {

 private ArrayList<Card> deck;
 
 public BaccaratDealer() {
	 	
	 deck = new ArrayList<Card>();
	 	
 }
 
 public void generateDeck() {
	 
	 	deck.clear();
	 	
	    String[] suits = { "hearts", "spades", "clubs", "diamonds" };
	    
	    String[] faceCards = {"ten", "king", "queen", "jack", }; // all these cards have 0 points so had to add 10
	    
	    int[] values = {1, 2, 3, 4, 5, 6, 7, 8, 9}; // Numbered cards except 10, where 1 is ace. 
	    
	    for (String suit : suits) {
	        for (int value : values) {
	            Card card = new Card(suit, value, "nil");
	            deck.add(card);
	    
	        }
	        
	        for (String face : faceCards) {
	            Card faceCard = new Card(suit, 0, face); // face cards have 0 value 
	            deck.add(faceCard);
	        }
	    }
 }
 
 public ArrayList<Card> dealHand(){
	 
	 ArrayList<Card> hand = new ArrayList<Card>();
	 
	 for (int i = 0; i < 2; i++) {
		 hand.add(drawOne());
	 }
	 
	 return hand;

 }
 
 public Card drawOne() {	 
	 
	 
	 Card cardToDraw;
	 cardToDraw = deck.get(0);
	 deck.remove(0);
	 return cardToDraw;
 }
 
 
 public void shuffleDeck() {
	 
	 generateDeck();
	 
     long seed = System.nanoTime(); // time as a seed for randomness
     Collections.shuffle(deck, new Random(seed));
	 
	 
 }
 
 
 public int deckSize() {
	
	 return deck.size();
 }
 
 public ArrayList<Card> getDeck(){

	 return deck;
 }
 
	
	
}
