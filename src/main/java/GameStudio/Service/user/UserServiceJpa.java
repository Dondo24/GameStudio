package GameStudio.Service.user;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import GameStudio.Entity.User;

@Transactional
public class UserServiceJpa implements UserService {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void register(User user) {

		entityManager.persist(user);
	}

	@Override
	public User login(String username, String password) {
		try {
			return entityManager
					.createQuery("Select u from User u  where u.username =:username and u.password =:password",
							User.class)
					.setParameter("username", username).setParameter("password", password).getSingleResult();

		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public List<User> getAllUsers() {
		try {
			return entityManager.createQuery("Select u from User u ", User.class).getResultList();
		} catch (NoResultException e) {

		}
		return null;
	}

	@Override
	public void deleteUser(Long id) {
		User u = null;
		try {
			u = entityManager.createQuery("Select u from User u where u.id =:id", User.class).setParameter("id", id)
					.getSingleResult();
		} catch (NoResultException e) {

		}
		if (u != null) {
			entityManager.remove(u);
		}

	}

	@Override
	public void changePassword(String name, String newPassword) {
		User u = null;
		try {
			u = entityManager.createQuery("Select u from User u where u.username =:username", User.class)
					.setParameter("username", name).getSingleResult();
		} catch (NoResultException e) {

		}
		if (u != null) {
			u.setPassword(newPassword);
		}

	}

	public void changeAdminPrivileges(String name) {
		User u = null;
		try {
			u = entityManager.createQuery("Select u from User u where u.username =:username", User.class)
					.setParameter("username", name).getSingleResult();
		} catch (NoResultException e) {
		}
		if (u != null) {
			if (u.isAdmin()) {
				u.setAdmin(false);
			} else {
				u.setAdmin(true);
			}
		}
	}

	@Override
	public boolean isAdmin(Long id) {
		User u = null;
		try {
			u = entityManager.createQuery("Select u from User u where u.id =:id", User.class).setParameter("id", id)
					.getSingleResult();
		} catch (NoResultException e) {
		}
		return u.isAdmin();
	}
}
