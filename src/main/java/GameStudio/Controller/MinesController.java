package GameStudio.Controller;

import java.util.Date;
import java.util.Formatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

import GameStudio.Entity.GamePlay;
import GameStudio.Entity.Score;
import GameStudio.Game.MineSweeper.Core.Clue;
import GameStudio.Game.MineSweeper.Core.Field;
import GameStudio.Game.MineSweeper.Core.GameDifficulty;
import GameStudio.Game.MineSweeper.Core.GameState;
import GameStudio.Game.MineSweeper.Core.Mine;
import GameStudio.Game.MineSweeper.Core.Tile;
import GameStudio.Service.coment.ComentService;
import GameStudio.Service.gamePlay.GamePlayService;
import GameStudio.Service.gamePlay.GamePlayServiceJpa;
import GameStudio.Service.score.ScoreService;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class MinesController {
	private static final String DB_GAME_NAME = "mines";

	private Field field = new Field(GameDifficulty.EASY);

	private boolean marking = false;
	private boolean bolo = false;
	@Autowired
	private ScoreService scoreService;
	@Autowired
	private UserController userController;
	@Autowired
	private GamePlayService gamePlayService;
	
	@RequestMapping("/mines")
	public String mines(@RequestParam(name = "row", required = false) Integer r,
			@RequestParam(name = "column", required = false) Integer c, Model model) {

		if (field.getState() == GameState.PLAYING && r != null && c != null) {
			if(!bolo) {
				SaveNewGamePlay();
			}
			
			bolo =true;
			if (marking) {
				field.markTile(r, c);
			} else {
				field.openTile(r, c);
				if (field.getState() == GameState.SOLVED) {
					scoreService.addScore(
							new Score(DB_GAME_NAME, userController.getLoggedUserName(), field.getScore(), new Date()));
				}
			}
		}
		setModel(model);
		return "mines";
	}

	private void setModel(Model model) {
		model.addAttribute("marking", marking);
		model.addAttribute("scores", scoreService.getBestScores(DB_GAME_NAME));
		// model.addAttribute("field",field); //potrebne, len ak nechceme pouzit
		// getHTMLfield() metodu
	}

	@RequestMapping("/minesNewHard")
	public String startHardNewGame() {
		SaveNewGamePlay();
		field = new Field(GameDifficulty.HARD);
		return "redirect:/mines";
	}

	private void SaveNewGamePlay() {
		GamePlay gp = new GamePlay();
		gp.setGame(DB_GAME_NAME);
		gp.setWhenP(new Date());
		gp.setUserName(userController.getLoggedUserName());
		gamePlayService.addGamePlay(gp);
	}

	@RequestMapping("/minesNewNormal")
	public String startNormalNewGame() {
		SaveNewGamePlay();
		field = new Field(GameDifficulty.NORMAL);
		return "redirect:/mines";
	}

	@RequestMapping("/minesNewEasy")
	public String startNewEasyGame() {
		SaveNewGamePlay();
		field = new Field(GameDifficulty.EASY);
		return "redirect:/mines";
	}

	@RequestMapping("/minesChangeMarking")
	public String changeMarking() {
		marking = !marking;
		return "redirect:/mines";
	}

	public String getGameState() {
		return field.getState().name();
	}

	public String getHTMLField() {
		Formatter sb = new Formatter();
		sb.format("<table class='mines_field'>\n");
		for (int row = 0; row < field.getRowCount(); row++) {
			sb.format("<tr>\n");
			for (int column = 0; column < field.getColumnCount(); column++) {
				Tile tile = field.getTile(row, column);
				sb.format("<td>\n");
				sb.format("<a href='/mines?row=%d&column=%d'>", row, column);
				sb.format("<img src='/images/mines/" + getImageName(tile) + ".png'>\n");
				sb.format("</a>");
			}
		}
		sb.format("</table>\n");
		return sb.toString();
	}

	public String getImageName(Tile tile) { // public, len ak nepouzivame getHTMLField()
		switch (tile.getState()) {
		case CLOSED:
			return "closed";
		case MARKED:
			return "marked";
		case OPENED:
			if (tile instanceof Mine)
				return "mine";
			else
				return "open" + ((Clue) tile).getValue();
		}
		throw new IllegalArgumentException("Uns. tile state " + tile.getState());
	}

}
