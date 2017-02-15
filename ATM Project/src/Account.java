
public class Account {
	private int accountNumber;
	private int pinCode;
	private double balance;
	
	public Account(){
		
	}
	
	public Account(int aN, int pC, int b){
		accountNumber = aN;
		pinCode = pC;
		balance = b;
	}
	
	public boolean validate(){
		
		return true;
	}
	
	public boolean deposit(Card c, int pin, int dep){
		
		return true;
	}
	
	public boolean withdraw(Card c, int pin, int dep){
		
		return true;
	}
}
