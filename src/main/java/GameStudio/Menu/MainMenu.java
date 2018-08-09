package GameStudio.Menu;

import java.util.Optional;
import java.util.Scanner;

import GameStudio.Game.Number.GuessTheNumber;
import GameStudio.Game.Puzzle.Console.ConsoleUI;

public class MainMenu {
	private enum Games {
		MineSweeper, Pexeso, Puzzle, GuessTheNumber
	};

	public static void main(String[] args) {

		Scanner s = new Scanner(System.in);
		int input = 0;
		try {
			int NumberOfGames = 1;
			while (!(input == 5)) {
				for (Games games : Games.values()) {
					System.out.println(NumberOfGames + ". " + games);
					NumberOfGames++;
				}
				input = Integer.parseInt(s.nextLine());
				if (input == 5)
					return;
				if (input < 5 && input > 0) {
					if (input == 1) {
						GameStudio.Game.MineSweeper.Console.ConsoleUI consoleUIMinesweeper = new GameStudio.Game.MineSweeper.Console.ConsoleUI();
						consoleUIMinesweeper.play();
					}
					if (input == 2) {
						GameStudio.Game.Pexeso.Console.ConsoleUI consoleUIPexeso = new GameStudio.Game.Pexeso.Console.ConsoleUI();
						consoleUIPexeso.play();
					}
					if (input == 3) {
						ConsoleUI consoleUIPuzzle = new ConsoleUI();
						consoleUIPuzzle.play();
					}
					if (input == 4) {
						GuessTheNumber gtn = new GuessTheNumber();
						gtn.play();
					}
				} else {
					System.out.println("InvalidInput");
				}
				NumberOfGames=1;
			}
		} catch (Exception e) {
			System.out.println("Invalid Inuput");
		}
	}
}
