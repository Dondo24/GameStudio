package GameStudio.Entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class GamePlay implements Serializable{

	
	@Id
	@GeneratedValue
	private long id;
	private String userName;
	private String game;
	private Date whenP;
	
	
	public GamePlay() {
		
	}


	public GamePlay(String userName, String game, Date whenP) {
		super();
		this.userName = userName;
		this.game = game;
		this.whenP = whenP;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getGame() {
		return game;
	}


	public void setGame(String game) {
		this.game = game;
	}


	public Date getWhenP() {
		return whenP;
	}


	public void setWhenP(Date whenP) {
		this.whenP = whenP;
	}
	
	
	
	
}
