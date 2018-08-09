package GameStudio.Game.MineSweeper.Console;

import java.util.Date;
import java.util.Scanner;

import GameStudio.Entity.Score;
import GameStudio.Game.MineSweeper.Core.Clue;
import GameStudio.Game.MineSweeper.Core.Field;
import GameStudio.Game.MineSweeper.Core.GameState;
import GameStudio.Game.MineSweeper.Core.Mine;
import GameStudio.Game.MineSweeper.Core.Tile;
import GameStudio.Game.MineSweeper.Core.TileState;
import GameStudio.Game.MineSweeper.Core.GameDifficulty;
import GameStudio.Service.*;

public class ConsoleUI {
	private Field field;
	private ScoreService scoreService = new ScoreServiceJDBC();
	private GameDifficulty gd;
	
	public ConsoleUI() {

		setDifficulty();
		field = new Field(gd);

	}

	public void play() {
		printScores();
		print();

		while (field.getState().equals(GameState.PLAYING)) {
			processInput();
			print();
		}
		if (field.getState() == GameState.SOLVED) {
			scoreService.addScore(new Score("mines",System.getProperty("user.name"), field.getScore(), new Date()));
			System.out.println("Game solved!");
		} else if (field.getState() == GameState.FAILED) {
			System.out.println("Game failed!");

		}
	}

	private void processInput() {
		System.out.print("Enter input: ");
		Scanner scanner = new Scanner(System.in);
		String line = scanner.nextLine().toUpperCase().trim();

		if ("X".equals(line))
			System.exit(0);
		if (line.matches("[OM][A-I][1-9]")) {
			int row = line.charAt(1) - 'A';
			int column = line.charAt(2) - '1';
			if (line.charAt(0) == 'O')
				field.openTile(row, column);
			else
				field.markTile(row, column);
		} else
			System.out.println("Invalid input!");
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

	public void printEnd() {
		System.out.print(" ");
		for (int column = 0; column < field.getColumnCount(); column++) {
			System.out.print(" " + (column + 1));
		}
		System.out.println();
		for (int row = 0; row < field.getRowCount(); row++) {
			System.out.print(((char) (row + 'A')));

			for (int column = 0; column < field.getColumnCount(); column++) {
				Tile tile = field.getTile(row, column);
				if (tile instanceof Mine) {
					System.out.print(" X");
				} else {
					System.out.print(" ");
					System.out.print(((Clue) tile).getValue());
					;
				}
			}
			System.out.println();
		}

	}

	private void printScores() {
		int index = 1;
		System.out.println("-----------------------------");
		System.out.println("No.  Player             Score");
		System.out.println("-----------------------------");
		for (Score score : scoreService.getBestScores("mines")) {
			System.out.printf("%3d. %-16s %5d\n", index, score.getPlayer(), score.getPoints());
			index++;
		}
		System.out.println("-----------------------------");
	}
	
	public void print() {
		System.out.print(" ");
		System.out.println(field.getColumnCount());
		for (int column = 0; column < field.getColumnCount(); column++) {
			System.out.print(" " + (column + 1));
		}
		System.out.println();
		for (int row = 0; row < field.getRowCount(); row++) {
			System.out.print(((char) (row + 'A')));

			for (int column = 0; column < field.getColumnCount(); column++) {
				Tile tile = field.getTile(row, column);
				if (tile.getState().equals(TileState.CLOSED)) {
					System.out.print(" -");

				} else if (tile.getState().equals(TileState.MARKED)) {
					System.out.print(" M");
				} else {
					if (tile instanceof Mine) {
						System.out.print(" X");
					} else {
						System.out.print(" ");
						System.out.print(((Clue) tile).getValue());
						;
					}
				}
			}
			System.out.println();

		}
	}
}
