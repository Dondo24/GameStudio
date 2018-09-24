package GameStudio.Entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name="web_user")
public class User {
	@Id
	@GeneratedValue
	private long id;
	@Column(unique=true)
	private String username;
	private String password;
	@Transient
	private String repeatPassword;
	@ColumnDefault("false")
	private boolean admin;
	private User() {
	}
	
	public User(String username,String password) {
		super();
		this.username = username;
		this.password= password;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	

	public boolean validatePassword() {
		return password.equals(repeatPassword);
		
	}
	public void setRepeatPassword(String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public long getId() {
		return id;
	}
	

}
