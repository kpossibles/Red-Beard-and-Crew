import java.util.Scanner;

public class ATM 
{
	Bank bank;
	
	public ATM(){
		start();
	}
	
	public void start(){
		bank = new Bank();
		Scanner reader = new Scanner(System.in);
		
		System.out.println("ATM Startup");
		System.out.print("Enter your account number: " );
		int account_number = reader.nextInt();
		Card card = new Card(account_number);
		while(account_number != 0){
			System.out.print("Enter your PIN: " );
			int pin = reader.nextInt();
			System.out.print("Choose your Operations: W: Withdraw  D:Deposit");
			String operation = reader.next();
			if(operation == "W"){
				System.out.print("Enter the amount to withdrawel: ");
				double amount = reader.nextDouble();
				if (bank.withdraw(card, pin, amount))
					System.out.println("Withdrawel Sucessful! Take your money and have a nice day.");
				else
					System.out.println("Withdrawel failed.");
			}
			else if(operation == "D"){
				System.out.print("Enter the amount to Deposit: ");
				double amount = reader.nextDouble();
				if (bank.deposit(card, pin, amount))
					System.out.println("Deposit Successful.");
				else
					System.out.println("Deposit Failed");
			}
			else{
				System.out.println("Invalid Operation");
			}
			System.out.print("Enter your account number: " );
			account_number = reader.nextInt();
		}
		reader.close();
	}
}
