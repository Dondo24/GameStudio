package GameStudio.Service.score;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import GameStudio.Entity.Score;


public class ScoreServiceJPA implements ScoreService{
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void addScore(Score score) throws ScoreException {
		try {
			entityManager.persist(score);
		} catch (Exception ex) {
			throw new ScoreException("Cannot save score", ex);
		}
	}

	@Override
	public List<Score> getBestScores(String gameName) throws ScoreException {
		try {
			return entityManager
					.createQuery("SELECT s FROM Score s WHERE s.game = :gameName ORDER BY s.points DESC", Score.class)
					.setParameter("gameName", gameName)
					.setMaxResults(10)
					.getResultList();
		} catch (Exception ex) {
			throw new ScoreException("Error getting score for game "+gameName, ex);
		}
	}


}
