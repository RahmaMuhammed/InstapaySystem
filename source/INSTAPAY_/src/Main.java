import Services.*;
import Authentication.BankAuthenticationService;
import Authentication.SignInManager;
import Authentication.UserAuthenticationService;
import Authentication.WalletAuthenticationService;
import Entites.AccountType;
import Entites.Bill;
import Entites.BillType;
import Entites.User;
import OTP.FakeOtpGenerator;
import OTP.IOtpGenerator;
import Repositories.InMemoryUserRepository;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserAuthenticationService userAuthenticationService = null;

        System.out.println("1) Sign Up");
        System.out.println("2) Sign In");

        System.out.print("Enter choice: ");
        int signChoice = scanner.nextInt();

        if (signChoice == 1) {
            System.out.println("Enter account type: ");
            System.out.println("1) Bank Account");
            System.out.println("2) Wallet");

            System.out.print("Enter choice: ");
            int accountChoice = scanner.nextInt();

            if (accountChoice == 1) {
                userAuthenticationService = new BankAuthenticationService();
            } else if (accountChoice == 2) {
                userAuthenticationService = new WalletAuthenticationService();
            }

            signUpProcess(userAuthenticationService);

        } else if (signChoice == 2) {
            System.out.print("\nEnter username: ");
            String username = scanner.next();

            System.out.print("Enter password: ");
            String password = scanner.next();

            SignInManager signInManager = new SignInManager();
            boolean signInResult = signInManager.SignIn(username, password);

            if (!signInResult) {
                throw new RuntimeException("Sign in failed");
            }
            var userAccountType = InMemoryUserRepository.getRepository().getByUsername(username).getAccountType();
            if (userAccountType == AccountType.BankAccount) {
                ShowMenu(new BankAuthenticationService());
            }
            else{
                ShowMenu(new WalletAuthenticationService());
            }
        }
    }
    private static void signUpProcess(UserAuthenticationService userAuthenticationService) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("\nPlease enter mobile Number: ");
        String mobileNumber = scanner.next();

        boolean validMobileNumber =
                userAuthenticationService.validationService.ValidateMobileNumber(mobileNumber);

        if (!validMobileNumber) {
            throw new RuntimeException("Invalid Mobile Number");
        }

        IOtpGenerator otpGenerator = new FakeOtpGenerator();
        int generatedOtp = otpGenerator.generateOtp();

        System.out.println("We have sent an OTP " + generatedOtp + " to the mobile number " + mobileNumber);
        System.out.print("Enter OTP: ");

        int otp = scanner.nextInt();
        scanner.nextLine();
        boolean validOtp = userAuthenticationService.validationService.ConfirmOtp(generatedOtp, otp);

        if (!validOtp) {
            throw new RuntimeException("Invalid OTP");
        }

        System.out.println("OTP validated successfully");

        System.out.print("Enter username: ");
        String username = scanner.next();

        System.out.print("Enter password: ");
        String password = scanner.next();

        boolean registerResult = userAuthenticationService.registerUser(new User(username, password, mobileNumber));

        if (!registerResult) {
            throw new RuntimeException("Username exists");
        }

        System.out.println("Successful Registration");
        SignInManager signInManager = new SignInManager();
        boolean signInResult = signInManager.SignIn(username, password);

        if (!signInResult) {
            throw new RuntimeException();
        }

        System.out.println("Created Account Data\n");

        System.out.println("username :" + SignInManager.CurrentLoggedInUser.getUserName());
        System.out.println("accountType :" + SignInManager.CurrentLoggedInUser.getAccountType());
        System.out.println("balance :" + SignInManager.CurrentLoggedInUser.getBalance() + "\n");


        ShowMenu(userAuthenticationService);
    }

    private static void ShowMenu(UserAuthenticationService userAuthenticationService) {
        ApplicationService applicationService = new ApplicationService();
        Scanner scanner = new Scanner(System.in);

        /////////////////////////////////// choose services /////////////////////////////////////
        while (true) {
            System.out.println("1) Transfer to Wallet using the mobile number");
            System.out.println("2) Transfer to Another instapay account");
            System.out.println("3) Inquire about his balance");
            System.out.println("4) Pay bills");

            if (userAuthenticationService.getAccountType() == AccountType.BankAccount) {
                System.out.println("5) Transfer To Bank account");
                System.out.println("6) Exit");
            } else {
                System.out.println("5) Exit");
            }
            int choice;

            System.out.print("Enter choice: ");
            choice = Integer.parseInt(scanner.nextLine());
            double amount;

            if (choice == 1) {
                System.out.print("Please enter target mobile number: ");
                String mobileNumber = scanner.nextLine();
                System.out.print("Please enter amount to be transferred: ");
                amount = scanner.nextDouble();
                scanner.nextLine();
                var TransferResult = applicationService.transferAmount(mobileNumber, amount, new TransferToWalletStrategy());
                if (!TransferResult) {
                    throw new RuntimeException();
                }
                System.out.println("Current User Balance After Transaction: " + SignInManager.CurrentLoggedInUser.getBalance());
                var targetUser = new WalletService().GetAccount(mobileNumber);

            }

            if (choice == 2) {
                System.out.print("Please enter target username: ");
                String username = scanner.nextLine();
                System.out.print("Please enter amount to be transferred: ");
                amount = scanner.nextDouble();
                scanner.nextLine(); // Consume the newline character

                var TransferResult = applicationService.transferAmount(username, amount, new TransferToInstapayStrategy());
                if (!TransferResult) {
                    throw new RuntimeException();
                }

                System.out.println("Current User Balance After Transaction: " + SignInManager.CurrentLoggedInUser.getBalance());
                var targetUser = InMemoryUserRepository.getRepository().getByUsername(username);
                System.out.println("Target User Balance After Transaction: " + targetUser.getBalance());
            }

            if (choice == 3) {
                double balance = applicationService.inquireBalance();
                System.out.println("Balance: " + balance);
            }
            if (choice == 4) {
                System.out.println("Please enter bill type: ");
                System.out.println("1) Gas");
                System.out.println("2) Electric");
                System.out.println("3) Water");

                System.out.print("Choice: ");
                int billChoice = Integer.parseInt(scanner.nextLine());

                System.out.print("Please enter bill cost: ");
                double billCost = Double.parseDouble(scanner.nextLine());

                Bill bill = null;
                IBillPaymentStrategy billPaymentStrategy = null;

                if (billChoice == 1) {
                    billPaymentStrategy = new GasBillPaymentStrategy();
                    bill = BillGenerator.generateBill(BillType.Gas, billCost);
                } else if (billChoice == 2) {
                    bill = BillGenerator.generateBill(BillType.Electricity, billCost);
                    billPaymentStrategy = new ElectricityBillPaymentStrategy();
                } else if (billChoice == 3) {
                    bill = BillGenerator.generateBill(BillType.Water, billCost);
                    billPaymentStrategy = new WaterBillPaymentStrategy();
                }
                boolean billPaymentResult = applicationService.payBill(bill, billPaymentStrategy);

                if (!billPaymentResult) {
                    throw new RuntimeException();
                }
                System.out.println("Current User Balance After Transaction: " + SignInManager.CurrentLoggedInUser.getBalance());
            }
            if (choice == 5 &&  SignInManager.CurrentLoggedInUser.getAccountType() == AccountType.BankAccount) {
                System.out.print("Please enter target Bank Account: ");
                String bankAccount = scanner.nextLine();
                System.out.print("Please enter amount to be transferred: ");
                amount = scanner.nextDouble();
                scanner.nextLine();
                {
                    applicationService.transferAmount(bankAccount, amount, new TransferToBankStrategy());
                    System.out.println("Current User Balance After Transaction: " + SignInManager.CurrentLoggedInUser.getBalance());
                    var targetUser = new BankService().GetAccount(bankAccount);

                }
            }
                if (choice == 5)
                    break;

                if (choice == 6)
                    break;

        }
    }

}