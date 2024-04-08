package com.serviceimpl;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.config.HibernateUtil;
import com.model.Account;
import com.servicei.RBI;

public class SBI implements RBI{

	Scanner sc = new Scanner(System.in);
	
	public void createAccount(){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		Account account = new Account();
		
		//Enter account number
		while(true)
		{
			System.out.println("Enter Account Details");
			try
			{
				System.out.print("Enter account 10 digit number: ");
				long accNo = sc.nextLong();
				int temp = 0;
				for(long i = accNo;i > 0;i = i/10)
				{
					temp++;
				}
				if(temp == 10)
				{
					account.setAccno(accNo);
					break;
				}
				else
				{
					System.out.print("Please Enter 10 digit Account Number: ");
				}
			}
			catch(InputMismatchException e)
			{
				System.out.println(e.getMessage());
				System.out.println("Account number should not contain Characters");
				sc.next();
			}
		}
		
		//Enter acc holder name
		while(true)
		{	
			System.out.print("Enter name: ");
			String accHolderName = sc.next()+sc.nextLine();
			String specialChar = "`!@#$%^&*()_+=-|}]{[;:',.<>/?";
			String str;
			boolean flag = false;
			for(int i = 0; i < accHolderName.length(); i++)
			{
				str = Character.toString(accHolderName.charAt(i));
				if(specialChar.contains(str))
				{
					flag = true;
					break;
				}
			}
			if(flag)
			{
				System.out.println("Name should not contain special characters");
			}
			else
			{
				account.setName(accHolderName);
				break;
			}
		}
		
		//Enter mobile number
		while(true)
		{
			try
			{
				System.out.print("Enter 10 digit mobile number: ");
				long mobNo = sc.nextLong();
				int temp = 0;
				for(long i = mobNo;i > 0;i = i/10)
				{
					temp++;
				}
				if(temp == 10)
				{
					account.setMbno(mobNo);
					break;
				}
		    	else
		    	{
					System.out.println("Enter Valid Mobile number");
				}
			}	
			catch(InputMismatchException e)
			{
				System.out.println(e.getMessage());
				System.out.println("Mobile number should not contain Characters");
				sc.next();
			}
		}
		
		//Enter adhar no
		while(true)
		{
			try
			{
				System.out.print("Enter Aadhaar number of 12 digits: ");
				long adharNo = sc.nextLong();
				int temp = 0;
				for(long i = adharNo;i > 0;i = i/10)
				{
					temp++;
				}
				if(temp == 12)
				{
					account.setAdharno(adharNo);
					break;
				}
				else
				{
					System.out.println("Aadhar number must be of 12 digit");
				}
			}catch(InputMismatchException e)
			{
				System.out.println(e.getMessage());
				System.out.println("Aadhar number should not contain Characters");
				sc.next();
			}		
		}
		
		//Choose gender
		boolean flag = true;
		while(flag)
		{
			System.out.print("Choose gender\n"+"1. Male\n"+"2. Female\n"+"3. Transgender\n");
			int gender = sc.nextInt();
			switch(gender)
			{
				case 1: account.setGender("Male");
						flag = false;
						break;
				case 2: account.setGender("Female");
						flag = false;
						break;
				case 3: account.setGender("Transgender");
						flag = false;
						break;
				default : System.out.println("Invalid choice for Gender ");
						break;
			}
		}
		
		//Enter age
		while(true)
		{
			System.out.print("Enter age: ");
			int age = sc.nextInt();
			if(age >= 18)
			{
				account.setAge(age);
				break;
			}
			else
			{
				System.out.println("Your age is Less than required age");
			}
		}
		
		//Enter balance
		while(true)
		{
			System.out.print("Enter account balance greater than 1000: ");
			double balance = sc.nextDouble();
			if(balance >= 1000)
			{	
				account.setBalance(balance);
				break;
			}
			else
			{
				System.out.println("Minimum Balance must be greater than 1000");
			}
		}
		
		
		session.save(account);
		tx.commit();
		session.close();
		
		
	}
	
	public void displayAllDetails()
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		boolean flag = true;
		while(true)
		{
			System.out.print("Enter acc number to display your details: ");
			long tempaccNo = sc.nextLong();
			List<Account> query = session.createQuery("from Account").getResultList();
			
			for(Account acc : query)
			{
				if (tempaccNo == acc.getAccno())
				{
					System.out.println("Account No.    : "+acc.getAccno());
					System.out.println("Acc Holder Name: "+acc.getName());
					System.out.println("Mobile No.     : "+acc.getMbno());
					System.out.println("Aadhaar No.    : "+acc.getAdharno());
					System.out.println("Gender         : "+acc.getGender());
					System.out.println("Age            : "+acc.getAge());
					System.out.println("BALANCE        : "+acc.getBalance());
					session.close();
					flag = true;
					break;
				}
				else {
					//count++;
					flag = false;
				}
			}
			
			if(flag)
			{
				break;
			}

		}
	}
	public void depositMoney()
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		System.out.print("Enter your acc no:");
		long tempaccNo = sc.nextLong();
		List<Account> list = session.createQuery("from Account").getResultList();
		list.forEach(a->{
			if(tempaccNo == a.getAccno())
			{
				System.out.print("Enter money to be deposit(greater than 5000): ");
				double depoAmount = sc.nextDouble();
				if(depoAmount >= 500 && depoAmount <= 10000)
				{
					double balance = a.getBalance() + depoAmount;
					a.setBalance(balance);
					Transaction tx = session.beginTransaction();
					session.update(a);
					tx.commit();
				}else 
				{
					System.out.println("Money to be deposited should be greater than 500");
				}
			}
		});
		session.close();
		
	}
	
	public void withdrawal()
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		System.out.println("Enter acc no to withdrwal money: ");
		long tempaccNo = sc.nextLong();
		List<Account> list = session.createQuery("from Account").getResultList();
		list.forEach(a->{
			if(tempaccNo == a.getAccno())
			{
				System.out.print("Enter money to be withdrawl: ");
				double tempwitMoney = sc.nextDouble();
				if(tempwitMoney <= 1000 && tempwitMoney <a.getAccno())
				{
					double withdrawMoney = a.getBalance() - tempwitMoney;
					a.setBalance(withdrawMoney);
					Transaction tx = session.beginTransaction();
					session.update(a);
					tx.commit();
				}else {
					System.out.println("Withdrawing money shloud be less than 10K or Insufficient Balance");
				}
			}
		});
	}
	
	public void balanceCheck()
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		boolean flag = true;
		while(flag)
		{
			System.out.print("Enter acc no to check the balance: ");
			long tempaccNo = sc.nextLong();
			List<Account>  list = session.createQuery("from Account").getResultList();
			
			for(Account a : list)
			{
				if(tempaccNo == a.getAccno())
				{
					System.out.print("Your available balance: "+a.getBalance());
					//flag = false;
					//break;
				}
				if(flag == false)
				{
					System.out.println("Account number doesnt matched");
				}
			}
			break;
		}
		
	}
}
