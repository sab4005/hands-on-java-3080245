package bank;

import java.util.Scanner;

import javax.security.auth.login.LoginException;

import bank.exceptions.AmountException;

public class Menu {
  private Scanner scanner;

  public static void main(String[] args){
    System.out.println("Welcome to Shawn's Bank");
    Menu menu = new Menu();
    menu.scanner = new Scanner(System.in);

    Customer customer = menu.authenticateUser();
    if(customer != null){
      Account account = DataSource.getAccount(customer.getAccountId());
      menu.showMenu(customer, account);
    }


    menu.scanner.close();
  }

  private Customer authenticateUser(){
    System.out.println("Enter your username");
    String username = scanner.next();
    System.out.println("Enter your password");
    String password = scanner.next();
    Customer customer = null;
    try{
      customer = Authenticator.login(username, password);
    } catch(LoginException e){
      System.out.println(e);
    }

    return customer;
  }

  private void showMenu(Customer customer, Account account) {
    int selection = 0;
    double amount = 0;

    while(selection != 4 && customer.isAuthenticated()) {
      System.out.println("=================");
      System.out.println("Select one of the options");
      System.out.println("1: Deposit");
      System.out.println("2: Withdrawl");
      System.out.println("3: Check Balance");
      System.out.println("4: Exit");
      System.out.println("=================");
      
      selection = scanner.nextInt();

      switch(selection){
        case 1: 
        System.out.println("How much do you want to depsit");
        amount = scanner.nextDouble();
        try{
          account.deposit(amount);
        } catch(AmountException e){
          System.out.println(e.getMessage());
          System.out.println("Please try again");
        }
        break;
        
        case 2:
        System.out.println("How much do you want to withdrawl");
        amount = scanner.nextDouble();
        account.withdrawl(amount);
        break;

        case 3:
        System.out.println("Your balance is: "+ account.getBalance());
        break;

        case 4: 
        Authenticator.logout(customer);
        System.out.println("Thanks for banking with us");
        break;

        default: 
        System.out.println("Invalid choice");
        break;
      }
    }
  }
  
}
