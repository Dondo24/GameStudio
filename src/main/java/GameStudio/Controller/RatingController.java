package GameStudio.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;

import GameStudio.Entity.Coment;
import GameStudio.Entity.Rating;
import GameStudio.Service.rating.RatingService;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class RatingController {

	@Autowired
	private RatingService ratingService;
	@Autowired
	private UserController UserController;

	private double rating = 0;

	public static void main(String[] args) {

	}

	@RequestMapping("/rate")
	public String addOrUpdate(Rating rating) {

		if (rating.getValue() != 0 && UserController.isLogged()) {
			rating.setUserName(UserController.getLoggedUserName());
			ratingService.changeRating(rating);
			return rating.getGame();
		} else {
			return "/";
		}

	}

	public double calculate(String gameName) {
		return ratingService.getAVGRating(gameName);
	}

	@RequestMapping("/deleteRating")
	public String deleteComent(Long id) {
		if (id != null) {
			ratingService.deleteRating(id);
		}

		return "redirect:/profil";
	}

}
