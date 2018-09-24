package GameStudio.Controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class GameStudioController {

	@RequestMapping("/gamestudio")
	public String index() {
		
		return "gamestudio";
	}
}
