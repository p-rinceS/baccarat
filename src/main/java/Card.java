
public class Card {
 private String suite;
 private int value;
 private String faceCard;
 

 
 


// I need a 3 argument constructor for my case but: 
 		// all my cards in my game use the 3 argument constructor because i need to tell 
 		// which a card worth 0 points  
 		// and if so what face it is
 public Card(String suit, int value, String faceCard){
	 this.suite = suit;
	 this.value = value;
	 this.faceCard = faceCard;
	 
 }
 
 // heres a 2 argument constructor because it is required.
public Card(String suit, int value) {
	this.suite = suit;
	this.value = value;
}

 public String getSuit() {
	 
	 return suite;
 }
 
 public void setSuit(String suit) {
	this.suite = suit;
 }
 
 public void setFace(String face) {
	this.faceCard = face;
 }
 
 public String getFace() {
	return faceCard;
 }
 
 public int getValue() {
	 return value;
 }
 
 public void setValue(int value) {
	 this.value = value;
 }
 
}
