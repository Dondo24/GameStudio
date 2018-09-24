package GameStudio.Controller;

import java.util.Date;
import java.util.Formatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;

import GameStudio.Entity.GamePlay;
import GameStudio.Entity.Score;
import GameStudio.Game.Pexeso.Core.Field;
import GameStudio.Game.Pexeso.Core.GameDifficulty;
import GameStudio.Game.Pexeso.Core.Tile;
import GameStudio.Game.Pexeso.Core.TileState;
import GameStudio.Service.gamePlay.GamePlayService;
import GameStudio.Service.score.ScoreService;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class PexesoController {

	private static final String DB_GAME_NAME = "pexeso";
	@Autowired
	private ScoreService scoreService;
	private boolean bolo = false;
	private Field field = new Field(GameDifficulty.EASY);
	@Autowired
	private GamePlayService gamePlayService;

	
	@Autowired
	private UserController userController;

	@RequestMapping("/pexeso")
	public String pexeso(@RequestParam(name = "row", required = false) Integer r,
			@RequestParam(name = "column", required = false) Integer c) throws InterruptedException {
		if (r != null && c != null && !field.isSolved()) {
			if(!bolo) {
				SaveNewGamePlay();
			}
			
			bolo =true;
			field.openTile(r, c);

			if (field.isSolved()) {
				scoreService.addScore(
						new Score(DB_GAME_NAME, userController.getLoggedUserName(), field.getScore(), new Date()));
			}
		}
		return "pexeso";
	}

	private void SaveNewGamePlay() {
		GamePlay gp = new GamePlay();
		gp.setGame(DB_GAME_NAME);
		gp.setWhenP(new Date());
		gp.setUserName(userController.getLoggedUserName());
		gamePlayService.addGamePlay(gp);
	}
	
	@RequestMapping("/PexesoNewEasy")
	public String startHardNewGame() {
		SaveNewGamePlay();
		field = new Field(GameDifficulty.EASY);
		return "redirect:/pexeso";
	}

	@RequestMapping("/PexesoNewNormal")
	public String startNormalNewGame() {
		SaveNewGamePlay();
		field = new Field(GameDifficulty.NORMAL);
		return "redirect:/pexeso";
	}

	@RequestMapping("/PexesoNewHard")
	public String startNewEasyGame() {
		SaveNewGamePlay();
		field = new Field(GameDifficulty.HARD);
		return "redirect:/pexeso";
	}
	
	public boolean isSolved() {
		return field.isSolved();
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
				int hodnota = tile.getValue();
				{
					if (tile.getState().equals(TileState.CLOSED)) {
						sb.format("<td>\n");
						sb.format("<a href='/pexeso?row=%d&column=%d'>", row, column);
						sb.format("" + "P" + "</a>");
					} else {
						sb.format("<td>\n");
						sb.format("<a href='/pexeso?row=%d&column=%d'>", row, column);
						sb.format("" + hodnota + "</a>");
					}
				}
			}
		}
		sb.format("</table>\n");

		return sb.toString();
	}

}
