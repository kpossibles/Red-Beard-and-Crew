import java.util.ArrayList;


public class Bank {
	ArrayList<Account> accounts = new ArrayList<Account>();
	
	public Bank(){
		
	}
	
	public boolean validate(Account a)
	{
		for(int i = 0; i < accounts.size(); i++){
			if(accounts.get(i).getAccountID() == a.getAccountID()){
				return true;
			}
		}
		return false;
	}
	
	public boolean withdraw(Card c, int pin, int with){
		int index = 0; boolean found = false;
		for(int i = 0; i < accounts.size(); i++){
			if(accounts.get(i).getAccountID() == c.getAccountID()){ index = i; found = true; }
		} return (accounts.get(index).validate(pin) && found && accounts.get(index).withdraw(with));
	}
	
	public boolean deposit(Card c, int pin, int dep){
		int index = 0; boolean found = false;
		for(int i = 0; i < accounts.size(); i++){
			if(accounts.get(i).getAccountID() == c.getAccountID()){ index = i; found = true; }
		} return (accounts.get(index).validate(pin) && found && accounts.get(index).deposit(dep));
	}
}
