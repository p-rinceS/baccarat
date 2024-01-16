import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MyTest {
	
	static BaccaratDealer theDealer;
	static BaccaratGameLogic logic;
	
	@BeforeEach
	 public void setUp() {
		theDealer = new BaccaratDealer();
		theDealer.generateDeck();
		
    	logic = new BaccaratGameLogic();
		
	}

	@Test
	void testCardConstructor() {

		Card myCard = new Card("hearts", 5, "nil");
		
		assertEquals(myCard.getSuit(), "hearts");
		assertEquals(myCard.getValue(), 5);
		assertNotEquals(myCard.getSuit(), "diamonds");
		assertNotEquals(myCard.getSuit(), "clubs");
		assertNotEquals(myCard.getSuit(), "spades");
		assertNotEquals(myCard.getValue(), 6);
	}
	
	@Test
	void testGenerateDeck() {
		
		BaccaratDealer myDealer = new BaccaratDealer();
		myDealer.generateDeck();
		assertEquals(myDealer.deckSize(), 52); // ensure there are 52 cards, the standard card count.
		
		
		
	}
	
    @Test
    void testShuffleDeck() {
        BaccaratDealer dealer = new BaccaratDealer();
        dealer.generateDeck(); // generate deck

        // copy of the original deck for comparison
        ArrayList<Card> originalDeck = new ArrayList<>(dealer.getDeck());
        
        // make sure they are equal now, so we know it changes later.
        assertEquals(originalDeck, dealer.getDeck());
        
        // shuffle first deck (not the copy we just created);
        dealer.shuffleDeck(); 

        // If shuffle has worked properly, the two decks will not be equal.
        assertEquals(dealer.deckSize(), 52);
        assertEquals(originalDeck.size(), 52);// make sure there are the cards in both the decks.
        assertNotEquals(originalDeck, dealer.getDeck());
   
    }
    
    @Test
    void testDrawOne() {
    	// original deck size
    	assertEquals(theDealer.deckSize(), 52);
    	
    	Card myCard = theDealer.drawOne();
    	// what is deck size now
    	assertNotEquals(theDealer.deckSize(), 52);
    	// does the deck get smaller?
    	assertEquals(theDealer.deckSize(), 51);
    	
    	// didnt shuffle, so should be in order of my loop i set up.
    	assertEquals(myCard.getValue(), 1);
    	

    	
    }
    
    @Test
    void testDealHand() {
    	// ensure size is full rn, 52.
    	assertEquals(theDealer.deckSize(),52);
    	
    	ArrayList<Card> hand = theDealer.dealHand();
    	assertEquals(hand.size(),2);
    	// deck gets smaller by 1 hand.
//    	assertEquals(theDealer.deckSize(), 50);
    	
    	// ensure the cards we are dealing are expected.
    	assertEquals(hand.get(0).getValue(), 1);
    	assertEquals(hand.get(1).getValue(), 2);
    	
    }
    
    @Test
    void testHandTotal() {
    	
    	ArrayList<Card> hand1 = theDealer.dealHand();
    	
    	// since we didn't shuffle, we know that first value is 1, and second is 2, 1+2 = 3 quick maths
    	assertEquals(logic.handTotal(hand1), 3);
    	
    	
    }
    
    @Test
    void whoWonTestBanker() {
    	
    	ArrayList<Card> hand1 = theDealer.dealHand(); // 3
    	
    	ArrayList<Card> hand2 = theDealer.dealHand(); // 7
    	
    	// let's make sure we are testing this correctly and have what we expect.
    	assertEquals(logic.handTotal(hand1), 3);
    	assertEquals(logic.handTotal(hand2), 7);
    	// banker wins test
    	assertEquals(logic.whoWon(hand1,hand2), "Banker");
    	
    	
    	
    }
    @Test
    void whoWonTestPlayer() {
    	
    	ArrayList<Card> hand1 = theDealer.dealHand(); // 3
    	
    	ArrayList<Card> hand2 = theDealer.dealHand(); // 7
    	
    	// let's make sure we are testing this correctly and have what we expect.
    	hand1.get(0).setValue(6); // made first number 6, which will result in natural.
    	assertEquals(logic.handTotal(hand1), 8); 
    	assertEquals(logic.handTotal(hand2), 7);
    	// banker wins test
    	assertEquals(logic.whoWon(hand1,hand2), "Player");
    	
    	
    	
    }
    
    @Test
    void whoWonTestDraw() {
    	
    	ArrayList<Card> hand1 = theDealer.dealHand(); // 3
    	
    	ArrayList<Card> hand2 = theDealer.dealHand(); // 7
    	
    	// let's make sure we are testing this correctly and have what we expect.
    	hand1.get(0).setValue(5); // made first number 5, which will result in draw.
    	assertEquals(logic.handTotal(hand1), 7); 
    	assertEquals(logic.handTotal(hand2), 7);
    	// banker wins test
    	assertEquals(logic.whoWon(hand1,hand2), "Draw");
    	
    	
    	
    }
    
    @Test
	void testEvaluateBankerDraw(){
    	
    	ArrayList<Card> hand = theDealer.dealHand(); // total == 3
    	
    	Card myCard = new Card("Heart", 4, "nil");
    	
    	assertEquals(logic.evaluateBankerDraw(hand, myCard), true);
    	
    	Card myCard2 = new Card("Heart", 8, "nil");
    	
    	assertEquals(logic.evaluateBankerDraw(hand, myCard2), false);
    			
    }
    
    @Test
    void testPlayerDraw() {
    	
    	ArrayList<Card> hand = theDealer.dealHand();
    	
    	assertEquals(logic.evaluatePlayerDraw(hand), true);
    	
    	// change it to greater than 5
    	hand.get(0).setValue(5);
    	
    	assertEquals(logic.evaluatePlayerDraw(hand), false);
    }


}
