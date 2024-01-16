import java.util.ArrayList;

public class BaccaratGameLogic {

	public String whoWon(ArrayList<Card> hand1, ArrayList<Card> hand2) {
		
		int handOneTotal = handTotal(hand1);
		int handTwoTotal = handTotal(hand2);
		
		
		// to see which one is closer to 9.
		
		// lets say handOneTotal = 2, handTwoTotal = 5
		
		// handOneDiff = 7, handTwoDiff = 4
		// 9 - 2 = 7  ||   9 - 4 = 5
		// since HandTwoDiff < handOneDiff, hand2 wins.
		
		
		int handOneDiff = 9 - handOneTotal;
		int handTwoDiff = 9 - handTwoTotal;
		if ((handOneTotal == 9 && handTwoTotal == 9) || (handOneTotal == 8 && handTwoTotal == 8)) {
			return "Draw";
			
		}
		else if(handOneDiff == handTwoDiff) {
			return "Draw";
		}
		else if (handTwoTotal == 8 || handTwoTotal == 9){
			return "Banker";
		}
		else if (handOneTotal == 8 || handOneTotal == 9) {
			return "Player"; // "natural"
		}else if( handTwoDiff < handOneDiff){
			return "Banker";
		}
		
		return "Player";
		
	}
	
	public int handTotal(ArrayList<Card> hand) {
		int ttl = 0;
		String operation = "";
		
		for (int i = 0; i < hand.size(); i++) {
			ttl += hand.get(i).getValue();
			operation += hand.get(i).getValue()+ " + ";
		}
		
		operation = operation + " = " + ttl;
		
//	System.out.println(operation);
		if (ttl >= 10) {
			ttl = ttl - 10;
		}
		
		if (ttl > 10) {
			ttl = ttl - 10;
		}
		
		if (ttl == 10) {
			ttl = ttl - 10;	
			}
		
		
		
//		System.out.println(ttl);
		
		return ttl;
		
	}
	
	public boolean evaluateBankerDraw(ArrayList<Card> hand, Card playerCard) {
		int bankerTotal = handTotal(hand);
		
		if (playerCard == null) {
			return false;
		}
		
		
		if (bankerTotal <= 2) {
	        return true; // Banker always draws a third card when total is 0, 1, or 2.
	    }
		else if (bankerTotal == 3) {
			if (playerCard.getValue() != 8) {
				return true; // Banker draws unless the player's third card is an 8.
        	}
		}
		
		// if bankerTotal == 4, then they only draw if player has not drawn or if their value is between 2 & 7 inclusive.		
		else if (bankerTotal == 4) {
			if ((playerCard.getValue() >= 2 && playerCard.getValue() <= 7)) {
				return true;
			} 
		}
		
		// if bankerTotal == 5, then they only draw if player has not drawn or if their value is between 4 & 7 inclusive.
		else if (bankerTotal == 5) {
			if((playerCard.getValue() >= 4 && playerCard.getValue() <= 7)) {
				return true;
			}
		}
		
		// if bankerTotal is 6, then they only draw if playerCard is 6 or 7
		else if (bankerTotal == 6) {
			if (playerCard.getValue() == 6 || playerCard.getValue() == 7) {
				return true;
			}
		}
		
		// if none of the above conditions are met, then false.
		return false;
		
		
	}
	
	public boolean evaluatePlayerDraw(ArrayList<Card> hand) {
		
		int playerTotal = handTotal(hand);
		
		if (playerTotal <= 5) {
			return true;
		}
		
		return false;
	}
}
