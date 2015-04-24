package itcurves.ncs.creditcard.cmt;

import org.kxml2.kdom.Element;
import org.kxml2.kdom.Node;

public class AuthenticationHeader {
	
	private String _username;
	private String _password;
	private String _dataSource;
	public void set_username(String _username) {
		this._username = _username;
	}
	public String get_username() {
		return _username;
	}
	public void set_password(String _password) {
		this._password = _password;
	}
	public String get_password() {
		return _password;
	}
	public void set_dataSource(String _dataSource) {
		this._dataSource = _dataSource;
	}
	public String get_dataSource() {
		return _dataSource;
	}
	
	public Element[] CreateAuthenticationHeader(String NAMESPACE)
	{
		
		Element[] headerOut = new Element[1];
        Element sessionHeader = new Element().createElement(NAMESPACE,"AuthenticationHeader");
        Element sessionUsername=sessionHeader.createElement(NAMESPACE, "Username");
        sessionUsername.addChild(Node.TEXT,_username);
        Element sessionPassword=sessionHeader.createElement(NAMESPACE, "Password");
        sessionPassword.addChild(Node.TEXT,_password);
        Element sessionDatasource=sessionHeader.createElement(NAMESPACE, "DataSource");
        sessionDatasource.addChild(Node.TEXT,_dataSource);
        
        
        sessionHeader.addChild(Node.ELEMENT,sessionUsername);
        sessionHeader.addChild(Node.ELEMENT,sessionPassword);
        sessionHeader.addChild(Node.ELEMENT,sessionDatasource);
        
        
        headerOut[0]=sessionHeader; 
		
		return headerOut;	
		
	}

}
