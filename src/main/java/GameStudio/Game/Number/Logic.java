package GameStudio.Game.Number;

import java.util.Random;

public class Logic {

	private int generatedNumber = 0;
	private int numberOfTry =0;
	private boolean solved = false;
	public Logic() {
		generateNumber();
	}
	
	public void generateNumber() {
		Random random = new Random();
		generatedNumber =  random.nextInt(10000);
	}
	
	public boolean check(int number) {
		if(number ==generatedNumber) {
			solved = true;
		}
		return number == generatedNumber;
	
}
	
	public boolean lessOrGreater(int number) {
		numberOfTry++;
		return number > generatedNumber;
	}
	public boolean isSolved() {
		return solved;
	}
	
	public int getScore() {
		return 10000-(numberOfTry*100);
	}
	public void reset() {
		solved= false;
		generateNumber();
	}
}
