package GameStudio.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;

import GameStudio.Service.user.UserService;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class AdminController {

	@Autowired
	private UserService userService;
	@Autowired
	private UserController userController;
	
	@RequestMapping("/admin")
	public String login() {

		return "admin";
	}
	
	@RequestMapping("/deleteUser")
	public String deleteUser(Long id) {
		if(id !=null && !userService.isAdmin(id) ) {
			userService.deleteUser(id);
		}
		
		return "redirect:/admin";
	}
	
	@RequestMapping("/changePassword")
	public String changePassword(String username,String pass,String passagain) {
		if(username !=null && pass.equals(passagain)) {
			userService.changePassword(username, pass);
		}
		
		
		
		return "redirect:/admin";
	}
	@RequestMapping("/changeAdminPrivileges")
	public String changeAdminPrivileges(String username) {
		if(username !=null && !username.equals(userController.getLoggedUserName())) {
			userService.changeAdminPrivileges(username);
		}
		return "redirect:/admin";
	}

}
