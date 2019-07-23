package bean;

import java.io.Serializable;

public class LoginBean implements Serializable{
	private String userName;
	private String passWord;
	private String number;   
	
	public LoginBean(String userName,String passWord,String number) {
		this.userName=userName;
		this.passWord=passWord;
		this.number=number;
	}

	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName=userName;
	}
	
	public String getPassWord() {
		return passWord;
	}
	
	public void setPassWord(String passWord) {
		this.passWord=passWord;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
}
