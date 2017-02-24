import java.time.LocalTime;

public class ATM 
{
	// These should not change while the ATM is in operation. 
	private final Bank bank;
	private final Printer print;
	private final Display display;
	private final Dispensor dispensor;
	
	//These are per transaction
	private String state; // Valid states are 'card', 'pin', 'transaction', 'withdraw'
	private int accountNumber;
	private int pin;
	
	public ATM(Printer _printer, Display _display, Dispensor _dispensor){
		bank = new Bank();
		print = _printer;
		display = _display;
		dispensor = _dispensor;
		state = "card";
	}
	
	private void displayState() {
		switch(state){
			case "card":
				display.display("Please Insert Your Card, or enter your account number on the keypad.");
				break;
			case "pin":
				display.display("Please Enter your pin.");
				break;
			case "transaction":
				display.display("Please choose your transaction type.");
				break;
			case "withdraw":
				display.display("Please enter an amount to withdraw.");;
				break;
			default: //If the state is not one of the above, something is wrong! 
				display.display("This ATM is currently out of order. ");
		}
	}

	public void withdraw() {
		state = "withdraw";
		displayState();
	}

	public void checkBalance() {
		double amount = bank.getBalance(accountNumber, pin);
		display.display("Your Balance will be printed below");
		print.print(getReceipt(amount));
	}

	public void cancel() {
		state = "card";
		accountNumber = 0;
		pin = 0;
		displayState();
	}

	public void inputNum(int value) {
		if(state.equalsIgnoreCase("card")){
			accountNumber = value;
			state = "pin";
		}
		else if(state.equalsIgnoreCase("pin"))
			if (bank.validate(accountNumber, value)){
				pin = value;
				state = "transaction";
			}
			else
				display.display("Invalid Pin.  Please Try again. ");
		else if(state.equalsIgnoreCase("withdraw")){
			if (bank.withdraw(accountNumber, pin, value)){
				state = "transaction";
				display.display("Withdrawl Sucessful");
				dispensor.dispense(value);
				print.print(getReceipt(value));
			}
			else
				display.display("Invalid Amount Selected.");
		}
		displayState();
	}

	private String getReceipt(double value) {
		// TODO This should generate a receipt in the format TIME TRANSACTION AMOUNT
		LocalTime time = LocalTime.now();
		return String.format("%tr %s %d", time, state, value);
	}

	public void insertCard(Card card) {
		if (state.equalsIgnoreCase("card")){
			accountNumber = card.getAccountID();
			state = "pin";
		}
		displayState();
	}
}
