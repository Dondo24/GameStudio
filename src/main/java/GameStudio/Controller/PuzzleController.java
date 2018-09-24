package GameStudio.Controller;

import java.util.Date;
import java.util.Formatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;

import GameStudio.Entity.GamePlay;
import GameStudio.Entity.Score;
import GameStudio.Game.MineSweeper.Core.GameState;
import GameStudio.Game.Puzzle.Core.Field;
import GameStudio.Game.Puzzle.Core.GameDifficulty;
import GameStudio.Game.Puzzle.Core.Tile;
import GameStudio.Service.gamePlay.GamePlayService;
import GameStudio.Service.score.ScoreService;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class PuzzleController {
	private static final String DB_GAME_NAME = "puzzle";
	private Field field = new Field(GameDifficulty.EASY);
	private boolean bolo = false;
	@Autowired
	private ScoreService scoreService;

	@Autowired
	private UserController userController;
	@Autowired
	private GamePlayService gamePlayService;
	
	@RequestMapping("/puzzle")
	public String puzzle(@RequestParam(name = "row", required = false) Integer r,
			@RequestParam(name = "column", required = false) Integer c, Model model) {
		if (!field.isSolved() && r != null && c != null) {
			if(!bolo) {
				SaveNewGamePlay();
			}
			
			bolo =true;
			field.move(r, c);

		}
		if (field.isSolved()) {
			scoreService
					.addScore(new Score(DB_GAME_NAME,  userController.getLoggedUserName(), field.getScore(), new Date()));
		}

		setModel(model);
		return "puzzle";
	}

	private void setModel(Model model) {
		// TODO Auto-generated method stub

	}
	private void SaveNewGamePlay() {
		GamePlay gp = new GamePlay();
		gp.setGame(DB_GAME_NAME);
		gp.setWhenP(new Date());
		gp.setUserName(userController.getLoggedUserName());
		gamePlayService.addGamePlay(gp);
	}


	@RequestMapping("/PuzzleNewEasy")
	public String startHardNewGame() {
		SaveNewGamePlay();
		field = new Field(GameDifficulty.EASY);
		return "redirect:/puzzle";
	}

	@RequestMapping("/PuzzleNewNormal")
	public String startNormalNewGame() {
		SaveNewGamePlay();
		field = new Field(GameDifficulty.NORMAL);
		return "redirect:/puzzle";
	}

	@RequestMapping("/PuzzleNewHard")
	public String startNewEasyGame() {
		SaveNewGamePlay();
		field = new Field(GameDifficulty.HARD);
		return "redirect:/puzzle";
	}

	public String getHTMLField() {
		Formatter sb = new Formatter();
		sb.format("<table class='puzzle_field'>\n");
		int pocet = 0;
		for (int row = 0; row < field.getRowCount(); row++) {
			sb.format("<tr>\n");
			for (int column = 0; column < field.getColumnCount(); column++) {
				pocet++;
				Tile tile = field.getTile(row, column);
				int hodnota =tile.getValue();
			 
					if(hodnota == 0) {
						sb.format("<td>\n");
						sb.format("<a href='/puzzle?row=%d&column=%d'>", row, column);
						sb.format( "</a>");
					}else {
						sb.format("<td>\n");
						sb.format("<a href='/puzzle?row=%d&column=%d'>", row, column);
						sb.format("" + hodnota + "</a>");
					}
			}
		}
		sb.format("</table>\n");
		return sb.toString();
	}

	public boolean isSolved() {
		return field.isSolved();
	}
}
