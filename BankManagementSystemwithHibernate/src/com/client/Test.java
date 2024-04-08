package com.client;

import java.util.Scanner;

import com.servicei.RBI;
import com.serviceimpl.SBI;

public class Test {

	public static void main(String[] args) {
		
		RBI bank = new SBI();
		
		Scanner sc = new Scanner(System.in);
		
		while(true) 
		{
			System.out.println("\n...Enter your CHOICE..."
					+ "\n1. Create Account"
					+ "\n2. Display Customer Details"
					+ "\n3. Deposit Money"
					+ "\n4. Withdraw Money"
					+ "\n5. Balance Check"
					+ "\n6. Exit\n");
			int choice = sc.nextInt();
			switch(choice) 
			{
			case 1:
				bank.createAccount();
				break;
			case 2:
				bank.displayAllDetails();
				break;
			case 3:
				bank.depositMoney();
				break;
			case 4:
				bank.withdrawal();
				break;
			case 5:
				bank.balanceCheck();
				break;
			case 6: System.exit(0);
				break;
			default : 
				System.out.println("INVALID CHOICE");
				break;
			}
			
		}
	}
}
