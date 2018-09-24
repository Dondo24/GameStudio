package GameStudio.Service.gamePlay;

import GameStudio.Entity.GamePlay;

public interface GamePlayService {

	
	void addGamePlay(GamePlay gamePlay);
	public Long NumberOfPlaysForGame(String gameName);
}
