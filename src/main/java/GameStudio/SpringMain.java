package GameStudio;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import GameStudio.Controller.RatingController;
import GameStudio.Controller.UserController;
import GameStudio.Entity.GamePlay;
import GameStudio.Game.MineSweeper.Console.MineSweeperConsoleUI;
import GameStudio.Game.Number.GuessTheNumberConsoleUI;
import GameStudio.Game.Pexeso.Console.PexesoConsoleUI;
import GameStudio.Game.Puzzle.Console.PuzzleConsoleUI;
import GameStudio.Menu.MainMenu;
import GameStudio.Service.coment.ComentService;
import GameStudio.Service.coment.ComentServiceJpa;
import GameStudio.Service.gamePlay.GamePlayService;
import GameStudio.Service.gamePlay.GamePlayServiceJpa;
import GameStudio.Service.rating.RatingService;
import GameStudio.Service.rating.RatingServiceJpa;
import GameStudio.Service.score.ScoreService;
import GameStudio.Service.score.ScoreServiceJDBC;
import GameStudio.Service.score.ScoreServiceJPA;
import GameStudio.Service.user.UserService;
import GameStudio.Service.user.UserServiceJpa;

@SpringBootApplication
@Configuration
public class SpringMain {

	public static void main(String[] args) {
		SpringApplication.run(SpringMain.class, args);
	}

//	@Bean
//	public CommandLineRunner runner(MainMenu mainMenu) {
//		return (args0) -> mainMenu.run();
//	}

//	@Bean
//	public MainMenu mainMenu() {
//		return new MainMenu();
//	}

	

	@Bean
	public ComentService comentService() {
		return new ComentServiceJpa();
	}

	@Bean
	public UserService userService() {
		return new UserServiceJpa();
	}

	@Bean
	public ScoreService scoreService() {
		return new ScoreServiceJPA();
	}

	@Bean
	public RatingService ratingService() {
		return new RatingServiceJpa();
	}
	
	@Bean 
	public GamePlayService gamePlayService() {
		return new GamePlayServiceJpa();
			
	}

//	
//	@Bean
//	public MineSweeperConsoleUI mineSweeperConsoleUI() {
//		return new MineSweeperConsoleUI();
//	}
//
//	@Bean
//	public PuzzleConsoleUI puzzleConsoleUI() {
//		return new PuzzleConsoleUI();
//	}
//
//	@Bean
//	public PexesoConsoleUI pexesoConsoleUI() {
//		return new PexesoConsoleUI();
//	}
//
//	@Bean
//	public GuessTheNumberConsoleUI guessTheNumberConsoleUI() {
//		return new GuessTheNumberConsoleUI();
//	}

}
