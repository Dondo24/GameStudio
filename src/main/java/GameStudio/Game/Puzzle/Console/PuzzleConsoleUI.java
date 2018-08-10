package GameStudio.Game.Puzzle.Console;

import java.util.Date;
import java.util.Scanner;

import GameStudio.Game.Game;
import GameStudio.Game.Puzzle.Core.Field;
import GameStudio.Game.Puzzle.Core.GameDifficulty;
import GameStudio.Game.Puzzle.Core.Tile;
import GameStudio.Entity.Score;
import  GameStudio.Service.*;

public class PuzzleConsoleUI  implements Game{
	private Field field;
	private GameDifficulty gd;
	private ScoreService scoreService = new ScoreServiceJDBC();

	public PuzzleConsoleUI() {
		
		

	}

	public void play() {
		setDifficulty();
		field = new Field(gd);
		printScores();
		print();
		Scanner  s = new Scanner(System.in);
		while (!field.isSolved()) {
			processInput(s);
			print();
		}
		System.out.println("Game Solved");
		scoreService.addScore(new Score("puzzle",System.getProperty("user.name"), field.getScore(), new Date()));
	
	}


	private void setDifficulty() {
		Scanner s = new Scanner(System.in);
		boolean ok = false;
		while (!ok) {
			System.out.println("Enter Difficulty(Number from 1(Easy) to 4(Extreme):");
			int diff = canParseInt(s);
			if (diff < 5 && diff > 0) {
				ok = true;
				gd = GameDifficulty.values()[diff - 1];
				
			}
		}
	}

	public void processInput(Scanner s) {
		System.out.println("Ented Number to Move:");
		int input = getInput(s);
		if (input != -1 && input != 0) {
			if (!field.move(field.getRowOfTile(input), field.getColumnOfTile(input))) {
				System.out.println("You cant move with that tile");
			}

		}
	}

	private int getInput(Scanner s) {
		int inputInt = canParseInt(s);
		if (inputInt < field.getRowCount() * field.getColumnCount() && inputInt > 0) {
			return inputInt;
		} else {
			System.out.println("Tile not exist");
		}
		return -1;
	}

	private int canParseInt(Scanner s) {
		int inputInt = 0;
		String inputString = s.next();
		try {

			inputInt = Integer.parseInt(inputString);

		} catch (NumberFormatException e) {
			System.out.println(" ");
			System.err.println("Cannot parse  number");

		}
		return inputInt;
	}

	private void printScores() {
		int index = 1;
		System.out.println("-----------------------------");
		System.out.println("No.  Player             Score");
		System.out.println("-----------------------------");
		for (Score score : scoreService.getBestScores("puzzle")) {
			System.out.printf("%3d. %-16s %5d\n", index, score.getPlayer(), score.getPoints());
			index++;
		}
		System.out.println("-----------------------------");
	}

	private void print() {
		for (int i = 0; i < field.getRowCount(); i++) {

			for (int j = 0; j < field.getColumnCount(); j++) {
				Tile tile = field.getTile(i, j);
				if (tile.getValue() != 0) {
					if (tile.getValue() > 9) {
						System.out.print(" ");
						System.out.print((tile).getValue());
						System.out.print("|");
					} else {
						System.out.print(" ");
						System.out.print((tile).getValue());
						System.out.print(" ");
						System.out.print("|");
					}
				} else {
					System.out.print(" ");
					System.out.print("X");
					System.out.print(" ");
					System.out.print("|");

				}
			}
			System.out.println();
		}
		System.out.println();

	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "puzzle";
	}

}
