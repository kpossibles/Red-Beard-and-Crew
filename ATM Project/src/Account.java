
public class Account {
	private int accountNumber;
	private int pinCode;
	private double balance;
	
	public Account(){
		accountNumber = pinCode = 0;
		balance = 0;
	}
	
	public Account(int aN, int pC, int b){
		accountNumber = aN;
		pinCode = pC;
		balance = b;
	}
	
	public boolean validate(int pin){
		if(pin == pinCode)
			return true;
		return false;
	}
	
	public boolean deposit(int cash){
		balance += cash;
		return true;
	}
	
	public boolean withdraw(int cash){
		if(cash < balance){
			balance -= cash;
			return true;
		}
		return false;
	}
	
	public int getAccountID(){
		return accountNumber;
	}
	public double getBalance(){
		return balance;
	}
}
