package GameStudio.Game.Number;

import java.util.Date;
import java.util.Random;
import java.util.Scanner;

import GameStudio.Entity.Score;
import GameStudio.Service.ScoreService;
import GameStudio.Service.ScoreServiceFile;
import GameStudio.Service.ScoreServiceJDBC;

public class GuessTheNumber {
	private boolean solved = false;
	private int numberOfTry;
	private int maxNumber;
	private ScoreService scoreService = new ScoreServiceJDBC();
	
	public void play() {
		printScores();
		int number=genTheRandomNumber();
		while (!solved) {
			processInput(number);
		}

	}

	private int genTheRandomNumber() {
		System.out.println("Enter the max Number:");
		Scanner s = new Scanner(System.in);
		maxNumber = s.nextInt();
		
		Random random = new Random();
		int number = random.nextInt(maxNumber);
		return number;
	}

	public void processInput(int number) {
		Scanner s = new Scanner(System.in);
		System.out.println("Enter Number");
		try {
			int inputInt = Integer.parseInt(s.nextLine());
			numberOfTry++;
			if (inputInt > number) {
				System.out.println("Try a less number");
			}
			if (inputInt < number) {
				System.out.println("Try greaer number");
			}
			if (inputInt == number) {
				solved = true;
				scoreService.addScore(new Score("guess",System.getProperty("user.name"), getScore(), new Date()));
				System.out.println("YOU WIN");
			}

		} catch (Exception e) {
			System.out.println("Invalid input");
		}

	}
	public int getScore() {
		return maxNumber - numberOfTry * (maxNumber/10);
	}

	private void printScores() {
		int index = 1;
		System.out.println("-----------------------------");
		System.out.println("No.  Player             Score");
		System.out.println("-----------------------------");
		for (Score score : scoreService.getBestScores("guess")) {
			System.out.printf("%3d. %-16s %5d\n", index, score.getPlayer(), score.getPoints());
			index++;
		}
		System.out.println("-----------------------------");
	}

}
