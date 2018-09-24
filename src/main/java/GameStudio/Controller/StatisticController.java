package GameStudio.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import GameStudio.Service.gamePlay.GamePlayService;

@Controller
public class StatisticController {

@Autowired
private GamePlayService gamePlayService;
	
	

	public Long NumberOfPlays(String gameName) {
		return gamePlayService.NumberOfPlaysForGame(gameName);
	}
	
	
}
