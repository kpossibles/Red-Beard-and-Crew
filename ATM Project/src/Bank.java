import java.util.ArrayList;


public class Bank {
	ArrayList<Account> accounts = new ArrayList<Account>();
	
	public Bank(){
		accounts.add(new Account(1234, 6789, 80.0));
		accounts.add(new Account(6789, 4321, 60.0));
	}
	
	public boolean validate(int acct_number, int pin)
	{
		for(int i = 0; i < accounts.size(); i++)
			if(accounts.get(i).getAccountID() == acct_number)
				 return accounts.get(i).validate(pin);
		return false;
	}
	
	public boolean withdraw(int acct_number, int pin, double amount){
		int index = 0; 
		boolean found = false;
		for(int i = 0; i < accounts.size(); i++){
			if(accounts.get(i).getAccountID() == acct_number){ 
				index = i; 
				found = true; 
			}
		} 
		return found &&(accounts.get(index).validate(pin) 
				&& accounts.get(index).withdraw(amount));
	}
	
	public boolean deposit(int acct_number, int pin, double amount){
		int index = 0; 
		boolean found = false;
		for(int i = 0; i < accounts.size(); i++){
			if(accounts.get(i).getAccountID() == acct_number){
				index = i; 
				found = true; 
			}
		} 
		return found && (accounts.get(index).validate(pin) 
				&& accounts.get(index).deposit(amount));
	}
	
	public double getBalance(int acct_number, int pin){
		for(int i = 0; i < accounts.size(); i++)
			if(accounts.get(i).getAccountID() == acct_number)
				if (accounts.get(i).validate(pin))
					return accounts.get(i).getBalance();
				else
					return 0.0;
		return 0.0;
	}
}
