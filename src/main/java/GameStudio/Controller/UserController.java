package GameStudio.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;

import GameStudio.Entity.User;
import GameStudio.Service.user.UserService;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class UserController {

	@Autowired
	private UserService userService;

	private User loggedUser;

	public boolean isLogged() {
		return loggedUser != null;
	}

	@RequestMapping("/login")
	public String login(String username, String password) {
		if (username != null && password != null) {

			loggedUser = userService.login(username, password);
			if (loggedUser == null) {
				return "login";
			} else {
				return "redirect:/";
			}
		}
		return "login";
	}

	@RequestMapping("/register")
	public String register(User user, Model model) {
		String message;
		if (user.getUsername() != null) {
			if (user.validatePassword()) {
				try {
					userService.register(user);
					message = "You have been registred.:)";

					loggedUser = userService.login(user.getUsername(), user.getPassword());
					return "redirect:/";
				} catch (Exception e) {
					message = "Problem creating user, probably username already exists";
					// fail register
				}
			} else {
				message = "Password doesn t  match.";
				// fail validate password
			}
			model.addAttribute("registerMessage", message);
		}
		return "register";

	}

	@RequestMapping("/logout")
	public String logout() {
		loggedUser = null;
		return "redirect:/";
	}
	

	public String getLoggedUserName() { 
		if (loggedUser != null) {
			return loggedUser.getUsername();
		} else {
			return "anonymous";
		}
	}
	public boolean adminLogged() {
		if(loggedUser==null)
			return false;
		return loggedUser.isAdmin();
	}
}
