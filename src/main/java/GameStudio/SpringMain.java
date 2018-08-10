package GameStudio;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import GameStudio.Game.MineSweeper.Console.MineSweeperConsoleUI;
import GameStudio.Game.Number.GuessTheNumberConsoleUI;
import GameStudio.Game.Pexeso.Console.PexesoConsoleUI;
import GameStudio.Game.Puzzle.Console.PuzzleConsoleUI;
import GameStudio.Menu.MainMenu;
import GameStudio.Service.ScoreService;
import GameStudio.Service.ScoreServiceJDBC;

@SpringBootApplication
@Configuration
public class SpringMain {
	
	public static void main(String[] args) {
		SpringApplication.run(SpringMain.class, args);
	}

	
	@Bean 
	public CommandLineRunner runner (MainMenu mainMenu) {
		return (args0)-> mainMenu.run();
	}
	
	@Bean 
	public MainMenu mainMenu () {
		return new MainMenu();
	}
	
	
	@Bean 
	public ScoreService scoreService() {
		return new ScoreServiceJDBC();
	}
	@Bean
	public MineSweeperConsoleUI mineSweeperConsoleUI() {
		return new MineSweeperConsoleUI();
	}
	@Bean 
	public PuzzleConsoleUI puzzleConsoleUI() {
		return  new PuzzleConsoleUI();
	}
	@Bean 
	public PexesoConsoleUI pexesoConsoleUI() {
		return new PexesoConsoleUI();
	}
	@Bean
	public  GuessTheNumberConsoleUI guessTheNumberConsoleUI() {
		return new GuessTheNumberConsoleUI();
	}
	
}
