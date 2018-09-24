package GameStudio.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Rating {
	@Id
	@GeneratedValue
	private long id;
	
	private String userName;
	private String game;
	private int value;
	
	
	public Rating() {
		
	}


	public Rating(String userName, String game, int value) {
		super();
		this.userName = userName;
		this.game = game;
		this.value = value;
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


	public int getValue() {
		return value;
	}


	public void setValue(int value) {
		this.value = value;
	}

	public long getId() {
		return id;
	}
	
}
