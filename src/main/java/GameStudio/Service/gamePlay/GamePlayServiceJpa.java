package GameStudio.Service.gamePlay;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import GameStudio.Entity.GamePlay;

public class GamePlayServiceJpa implements GamePlayService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void addGamePlay(GamePlay gamePlay) {
		entityManager.persist(gamePlay);

	}

	@Override
	public Long NumberOfPlaysForGame(String gameName) {
		try {
			return  entityManager
					.createQuery("Select count(gp.game) from GamePlay gp where game= :game",Long.class)
					.setParameter("game", gameName)
					.getSingleResult();

		} catch (NoResultException e) {

		}
		return 0L;

	}

}
