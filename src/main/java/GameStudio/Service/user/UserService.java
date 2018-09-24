package GameStudio.Service.user;

import java.util.List;

import GameStudio.Entity.Score;
import GameStudio.Entity.User;

public interface UserService {

	void register(User user);
	
	User login (String username , String password);
		
	List<User> getAllUsers();
	
	void deleteUser(Long id);
	void changePassword(String name,String newPassword);
	void changeAdminPrivileges(String name);
	boolean isAdmin(Long id);
}
