package GameStudio.Menu;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;

import GameStudio.Game.Game;
import GameStudio.Game.MineSweeper.Console.MineSweeperConsoleUI;
import GameStudio.Game.Number.GuessTheNumberConsoleUI;
import GameStudio.Game.Pexeso.Console.PexesoConsoleUI;
import GameStudio.Game.Puzzle.Console.PuzzleConsoleUI;


public class MainMenu {

@Autowired
private Game[] games;

	public void run() {
		while(true) {
			try {
				System.out.println("Games:");
				for (int i = 0; i < games.length; i++) {
					System.out.println((i+1)+". "+games[i].getName());
				}
				System.out.println("0. EXIT GAMESTUDIO");
				Scanner scanner = new Scanner(System.in);
				int input = Integer.parseInt(scanner.nextLine());
				if(input==0) {
					return;
				}
				if(input>0 && input< games.length) {
					games[input-1].play();
				}
			}catch(Exception e ) {
				System.out.println("error:"+e.getMessage());
			}
		}


	}
}
