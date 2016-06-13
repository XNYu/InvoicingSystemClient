package presentation.loginGUI;

import java.io.Serializable;
import java.util.ArrayList;

public class LoginData implements Serializable{
	boolean isAutoLogin = false;
	boolean isRememberPassword = false;
	String loginName = "";
	String password = "";
	String IP = "";
	String port ="";
	public LoginData(){}
	
	
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getIP() {
		return IP;
	}
	public void setIP(String iP) {
		IP = iP;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isAutoLogin() {
		return isAutoLogin;
	}
	public void setAutoLogin(boolean isAutoLogin) {
		this.isAutoLogin = isAutoLogin;
	}
	public boolean isRememberPassword() {
		return isRememberPassword;
	}
	public void setRememberPassword(boolean isRememberPassword) {
		this.isRememberPassword = isRememberPassword;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	

}
