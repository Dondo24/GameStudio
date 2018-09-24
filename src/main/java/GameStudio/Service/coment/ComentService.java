package GameStudio.Service.coment;

import java.util.List;

import GameStudio.Entity.Coment;

public interface ComentService {

	
	boolean addComent(Coment coment);
	List<Coment> getComents(String gameName);
	List<Coment> allComentsOfUser(String userName);
	void deleteComent(Long id);
		
	
	
	
}
