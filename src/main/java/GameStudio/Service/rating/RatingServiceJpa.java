package GameStudio.Service.rating;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import GameStudio.Entity.Rating;
import GameStudio.Entity.User;

@Transactional
public class RatingServiceJpa implements RatingService {

	@PersistenceContext
	private EntityManager entityManager;

	

	@Override
	public void changeRating(Rating rating) {

		Rating r = null;
		try {
			r = entityManager
					.createQuery("Select r from Rating r where r.userName = :username and r.game = :game", Rating.class)
					.setParameter("username", rating.getUserName()).setParameter("game", rating.getGame())
					.getSingleResult();
		} catch (NoResultException e) {

		}
		if (r != null) {
			r.setValue(rating.getValue());
		} else {
			entityManager.persist(rating);

		}
	}

	@Override
	public double getAVGRating(String gameName) {
		return entityManager.createQuery("Select avg(value) from Rating r where r.game = :game", Double.class)
				.setParameter("game", gameName).getSingleResult();
	}

	@Override
	public List<Rating> getAllRatingOfUser(String userName) {
		try {
			return entityManager.createQuery("Select r from Rating r where r.userName =:userName", Rating.class)
					.setParameter("userName", userName).getResultList();
		} catch (NoResultException e) {

		}
		return null;
	}

	@Override
	public void deleteRating(Long id) {
		Rating r = null;
		try {
			r = entityManager.createQuery("Select r from Rating r where r.id =:id", Rating.class).setParameter("id", id)
					.getSingleResult();
		} catch (NoResultException e) {

		}
		if (r != null) {
			entityManager.remove(r);
		}

	}

}