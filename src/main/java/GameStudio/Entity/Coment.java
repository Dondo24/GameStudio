package GameStudio.Entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Coment implements Serializable {

	@Id
	@GeneratedValue
	private long id;
	private String userName;
	private String game;
	private Date whenW;
	private String text;

	public Coment() {

	}

	public Coment(String userName, String game, Date whenW, String text) {
		super();
		this.userName = userName;
		this.game = game;
		this.whenW = whenW;
		this.text = text;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public Date getWhenW() {
		return whenW;
	}

	public void setWhenW(Date whenW) {
		this.whenW = whenW;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
