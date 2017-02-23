
public class Account {
	private int accountNumber;
	private int pinCode;
	private double balance;
	
	public Account(int aN, int pC, double b){
		accountNumber = aN;
		pinCode = pC;
		balance = b;
	}
	
	public boolean validate(int pin){
		if(pin == pinCode)
			return true;
		return false;
	}
	
	public boolean deposit(double cash){
		balance += cash;
		return true;
	}
	
	public boolean withdraw(double cash){
		if(cash <= balance){
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
