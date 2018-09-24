package GameStudio.Service.coment;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import GameStudio.Entity.Coment;
import GameStudio.Entity.User;
import GameStudio.Service.score.ScoreException;

@Transactional
public class ComentServiceJpa implements ComentService {

	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	
	public boolean addComent(Coment coment) {
		try {
			entityManager.persist(coment);
			return true;
		}catch(Exception e) {
			throw new ScoreException("Canot save coment", e);
			
		}
	}

	@Override
	public List<Coment> getComents(String gameName) throws ComentException {
		try {
			return entityManager
					.createQuery("Select c from Coment c where c.game= :game",Coment.class)
					.setParameter("game", gameName)
					.getResultList();
		}catch(Exception e) {
			throw new ComentException("Cant load score for game"+gameName, e);
		}
	}

	@Override
	public List<Coment> allComentsOfUser(String userName) {
		try {
			return entityManager
					.createQuery("Select c from Coment c where c.userName =:userName",Coment.class)
					.setParameter("userName", userName)
					.getResultList();
		}catch(NoResultException e ) {
			
		}
		return null;
	}

	@Override
	public void deleteComent(Long id) {
		Coment c = null;
		try {
			c = entityManager.createQuery("Select c from Coment c where c.id =:id", Coment.class)
					.setParameter("id", id).getSingleResult();
		} catch (NoResultException e) {

		}
		if (c != null) {
			entityManager.remove(c);
		}

		
	}

}
