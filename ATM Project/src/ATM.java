
public class ATM 
{
	Bank bank;
	
	public ATM(){
		start();
	}
	
	public void start(){
		bank = new Bank();
		// COMMAND LINE ELEMENTS HERE
		
	}
	
	private boolean withdraw(Card c, int pin, int dep){
		return bank.withdraw(c, pin, dep);
	}
	
	private boolean deposit(Card c, int pin, int dep){
		return bank.deposit(c, pin, dep);
	}
}
