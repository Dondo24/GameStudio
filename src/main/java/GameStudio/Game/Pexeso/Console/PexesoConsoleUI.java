package GameStudio.Game.Pexeso.Console;

import java.util.Date;
import java.util.Scanner;

import GameStudio.Entity.Score;
import GameStudio.Game.Game;
import GameStudio.Game.Pexeso.Core.Field;
import GameStudio.Game.Pexeso.Core.Tile;
import GameStudio.Game.Pexeso.Core.TileState;
import GameStudio.Service.ScoreService;
import GameStudio.Service.ScoreServiceFile;
import GameStudio.Service.ScoreServiceJDBC;

public class PexesoConsoleUI implements Game{

	private Field field;
	private ScoreService scoreService =new ScoreServiceJDBC();

	public PexesoConsoleUI() {
		this.field = new Field(2, 2);
	}

	public void play() {
		printScores();
		print(field);
		while (!field.isSolved()) {
			processInput();
		}
		System.out.println("GameSolved");
		scoreService.addScore(new Score("pexeso",System.getProperty("user.name"), field.getScore(), new Date()));

	}

	private void processInput() {
		System.out.println("Enter first posion:");
		Scanner s = new Scanner(System.in);
		String input = s.nextLine().toUpperCase().trim();
		if (input.matches("[A-I][A-I]")) {
			int row1 = input.charAt(0) - 'A';
			int column1 = input.charAt(1) - 'A';
			if (row1 < field.getRowCount() && column1 < field.getColumnCount()&&field.getTile(row1, column1).getState().equals(TileState.CLOSED)) {
				field.openTile(row1, column1);
				print(field);
				System.out.println("Ented second position:");
				if (input.matches("[A-I][A-I]")) {
					input = s.nextLine().toUpperCase().trim();
					int row2 = input.charAt(0) - 'A';
					int column2 = input.charAt(1) - 'A';
					if (row2 < field.getRowCount() && column2 < field.getColumnCount()&&field.getTile(row2, column2).getState().equals(TileState.CLOSED)) {
						field.openTile(row2, column2);
						print(field);
						field.checkTiles(row1, column1, row2, column2);
						System.out.println();
						print(field);
					} else {
						
						field.closeTile(row1, column1);
						print(field);
						System.out.println("Invalid Input");
					}
				} else {
					System.out.println("Invalid Input");
				}
			} else {
				System.out.println("Invalid Input");
			}
		} else {
			System.out.println("Invalid Input");
		}
	}

	private void printScores() {
		int index = 1;
		System.out.println("-----------------------------");
		System.out.println("No.  Player             Score");
		System.out.println("-----------------------------");
		for (Score score : scoreService.getBestScores("pexeso")) {
			System.out.printf("%3d. %-16s %5d\n", index, score.getPlayer(), score.getPoints());
			index++;
		}
		System.out.println("-----------------------------");
	}

	private void print(Field field2) {
		System.out.print(" ");
		for (int column = 0; column < field.getColumnCount(); column++) {
			System.out.print(" " + ((char) (column + 'A')));
		}
		System.out.println();
		for (int row = 0; row < field.getRowCount(); row++) {
			System.out.print(((char) (row + 'A')));

			for (int j = 0; j < field.getColumnCount(); j++) {
				Tile tile = field.getTile(row, j);
				if (tile.getState().equals(TileState.OPENED) || tile.getState().equals(TileState.SOLVED)) {
					System.out.print(" ");
					System.out.print(tile.getValue());
				} else {
					System.out.print(" ");
					System.out.print("-");
				}
			}
			System.out.println();
		}

	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "pexeso";
	}
}
