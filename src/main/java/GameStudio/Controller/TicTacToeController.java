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
import GameStudio.Game.TicTacToe.core.Field;
import GameStudio.Service.coment.ComentService;
import GameStudio.Service.gamePlay.GamePlayService;
import GameStudio.Game.TicTacToe.core.Tile;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class TicTacToeController {
	private boolean vsPc = false;
	private Field field = new Field();
	private boolean bolo = false;
	private static final String DB_GAME_NAME = "tictactoe";
	@Autowired
	private ComentService comentService;
	@Autowired
	private GamePlayService gamePlayService;
	@Autowired
	private UserController userController;
	
	@RequestMapping("/tictactoe")
	public String tictactoe(@RequestParam(name = "row", required = false) Integer r,
			@RequestParam(name = "column", required = false) Integer c, Model model) {
		if (r != null && c != null && !isSolved()) {
			if(!bolo) {
				SaveNewGamePlay();
			}
			
			bolo =true;
			if (vsPc) {
				field.playPC(r, c);
			} else {
				field.play(r, c);
				//System.out.println("kod :"+isSolved() );
			}
		}

		return "tictactoe";
	}
	private void SaveNewGamePlay() {
		GamePlay gp = new GamePlay();
		gp.setGame(DB_GAME_NAME);
		gp.setWhenP(new Date());
		gp.setUserName(userController.getLoggedUserName());
		gamePlayService.addGamePlay(gp);
	}
	
	public String getHTMLField() {
		Formatter sb = new Formatter();
		sb.format("<table class='puzzle_field'>\n");

		for (int row = 0; row < field.getRowCount(); row++) {
			sb.format("<tr>\n");
			for (int column = 0; column < field.getColumnCount(); column++) {
				Tile tile = field.getTile(row, column);
				int value = tile.getValue();

				if (value == 1) {

					sb.format("<td>\n");
					sb.format("<a href='/tictactoe?row=%d&column=%d'>", row, column);
					sb.format("" + "X" + "</a>");
				} else if (value == 2) {
					sb.format("<td>\n");
					sb.format("<a href='/tictactoe?row=%d&column=%d'>", row, column);
					sb.format("" + "O" + "</a>");
				} else {

					sb.format("<td>\n");
					sb.format("<a href='/tictactoe?row=%d&column=%d'>", row, column);
					sb.format("-" + "</a>");
				}

			}
		}
		sb.format("</table>\n");
		return sb.toString();
	}

	@RequestMapping("/tictactoe1v1")
	public String newGame() {
		SaveNewGamePlay();
		field = new Field();
		vsPc = false;
		return "/tictactoe";
	}

	@RequestMapping("/tictactoe1vpc")
	public String newGamePc() {
		SaveNewGamePlay();
		field = new Field();
		vsPc = true;
		return "/tictactoe";
	}
	public boolean isSolved() {
		return field.isSolved();
	}
	public boolean playerOneWin() {
		return field.whoWin();
	}
	public boolean isDraw() {
		return field.isDraw();
	}
	public boolean vsPc() {
		return vsPc;
	}
}
