import java.util.Scanner;

public class GuessN {

	public static void main(String[] args) {
	
		Scanner scan= new Scanner(System.in);
		String playAgain="";
		do {
int theNr=(int)(Math.random()*100+1);
//System.out.println(theN);

int quess=0;
while(quess!=theNr) {

System.out.println("Quess a nr between 1 and 100");
quess=scan.nextInt();
System.out.println("you entered  " + quess  +".");

if(quess < theNr)
	System.out.println(quess + "is too low.Try again.");
else if(quess > theNr)
	System.out.println(quess + " is too high Try again");
else
	System.out.println(quess + "is correct" );

	}
	System.out.println("Would you like to play again? yes =y no =n");
		playAgain=scan.next();
	}while(playAgain.equalsIgnoreCase("y"));

}
}
