package GameStudio.Service.score;

import java.util.List;

import GameStudio.Entity.Score;


public interface ScoreService {

	void addScore(Score score);

	List<Score> getBestScores(String gameName);

}