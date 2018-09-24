package GameStudio.Controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;

import GameStudio.Entity.GamePlay;
import GameStudio.Entity.Score;
import GameStudio.Game.MineSweeper.Core.Field;
import GameStudio.Game.MineSweeper.Core.GameDifficulty;
import GameStudio.Game.Number.Logic;
import GameStudio.Service.gamePlay.GamePlayService;
import GameStudio.Service.score.ScoreService;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class GuessTheNumberController {

	private String message = "";
	private static final String DB_GAME_NAME = "guess";
	private Logic logic = new Logic();
	@Autowired
	private ScoreService scoreService;

	@Autowired
	private UserController userController;
	@Autowired
	private GamePlayService gamePlayService;
	
	private boolean bolo = false;
	
	@RequestMapping("/guess")
	public String guess(@RequestParam(name = "number", required = false) Integer number, Model model) {
		if (number != null && !logic.isSolved()) {
			if(!bolo) {
				SaveNewGamePlay();
			}
			
			bolo =true;
			if (!logic.check(number)) {
				System.err.println("somtu");

				if (logic.lessOrGreater(number)) {
					message = "Skus mensie cislo";
				} else {
					message = "Skus vacsie cislo";
				}

			} else {
				System.out.println("solved");
				message = "Solved";
				scoreService.addScore(
						new Score(DB_GAME_NAME, userController.getLoggedUserName(), logic.getScore(), new Date()));
			}
			model.addAttribute("message", message);

		}
		return "guess";
	}
	private void SaveNewGamePlay() {
		GamePlay gp = new GamePlay();
		gp.setGame(DB_GAME_NAME);
		gp.setWhenP(new Date());
		gp.setUserName(userController.getLoggedUserName());
		gamePlayService.addGamePlay(gp);
	}

	@RequestMapping("/newGame")
	public String startHardNewGame() {
		SaveNewGamePlay();
		logic.reset();
		return "redirect:/guess";
	}
}