import java.util.Scanner;




public class ATM 
{
	private Bank bank;
	private Printer print;
	private String state;
	
	private int accountNumber;
	private int pin;
	private int amt;
	
	private String transactionType = "none";
	
	public ATM(Printer p){
		bank = new Bank();
		print = p;
	}
	
	/*public void start(){
		bank = new Bank();
		Scanner reader = new Scanner(System.in);
		
		System.out.println("ATM Startup");
		System.out.print("Enter your account number: " );
		int account_number = reader.nextInt();
		Card card = new Card(account_number);
		while(account_number != 0){
			System.out.print("Enter your PIN: " );
			int pin = reader.nextInt();
			if (bank.validate(card, pin)){
				System.out.println("Choose your Operations: (W:Withdraw D:Deposit):");
				String operation = reader.next();
				if(operation.equals("W")){
					System.out.print("Enter the amount to withdraw: ");
					double amount = reader.nextDouble();
					if (bank.withdraw(card, pin, amount))
						System.out.printf("Withdraw of %s from account %s sucessful!\n\n", amount, card.getAccountID());
					else
						System.out.println("Withdraw failed.\n");
				}
				else if(operation.equals("D")){
					System.out.print("Enter the amount to Deposit: ");
					double amount = reader.nextDouble();
					if (bank.deposit(card, pin, amount))
						System.out.printf("Deposit of %s into account %s successful.\n", amount, card.getAccountID());
					else
						System.out.println("Deposit Failed.\n");
				}
				else{
					System.out.println("Invalid Operation.\n");
				}
			}
			else {
				System.out.println("Failed to validate. \n");
			}
			System.out.print("Enter your account number: " );
			account_number = reader.nextInt();
		}
		reader.close();
	}*/

	public void withdraw() {
		// TODO Auto-generated method stub
		
	}

	public void checkBalance() {
		// TODO Auto-generated method stub
		
	}

	public void cancel() {
		// TODO Auto-generated method stub
		
	}

	public void inputNum(int parseInt) {
		// TODO Auto-generated method stub
		if(state.equalsIgnoreCase("none")){
			
		}
		
		else if(state.equalsIgnoreCase("pin")){
			
		}
		
		else if(state.equalsIgnoreCase("with")){
			
		}
		
		else{
			
		}
	}

	public void insertCard(Card card) {
		// TODO Auto-generated method stub
		
		accountNumber = card.getAccountID();
		state = "pin";
	}
}
