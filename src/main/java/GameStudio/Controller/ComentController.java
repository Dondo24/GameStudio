package GameStudio.Controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;

import GameStudio.Entity.Coment;
import GameStudio.Service.coment.ComentService;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class ComentController {

	@Autowired
	private ComentService comentService;
	@Autowired
	private UserController userController;
	
	@RequestMapping("/addcoment")
	public String addcoment(Coment coment) {
		if(coment.getText()!=null && userController.isLogged()) {
			coment.setUserName(userController.getLoggedUserName());
			coment.setWhenW(new Date());
			comentService.addComent(coment);
		}
		
		return "redirect:/"+coment.getGame();
	}
	
	@RequestMapping("/deleteComent")
	public String deleteComent(Long id) {
		if(id !=null  ) {
			comentService.deleteComent(id);
		}
		
		return "redirect:/profil";
	}
	
}
