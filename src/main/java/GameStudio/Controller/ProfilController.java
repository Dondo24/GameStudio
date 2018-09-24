package GameStudio.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;

import GameStudio.Entity.Coment;
import GameStudio.Service.user.UserService;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class ProfilController {

	@Autowired
	private UserService userService;
	@Autowired
	private ComentController comentController;
	@Autowired
	private UserController userController;
	
	@RequestMapping("/profil")
	public String profil() {
		
		
		return "profil";
	}
}
