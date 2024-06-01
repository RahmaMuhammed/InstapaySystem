//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package Entites;

public class User {
    private String userName;
    private String password;
    private double balance;
    private String mobileNumber;
    private AccountType accountType;

    public User(String userName, String password, String mobileNumber, double balance, AccountType accountType) {
        this.userName = userName;
        this.password = password;
        this.mobileNumber = mobileNumber;
        this.balance = balance;
        this.accountType = accountType;
    }

    public User(String username, String password, String mobileNumer) {
        this.userName = username;
        this.password = password;
        this.mobileNumber = mobileNumer;
    }

    public User() {
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getBalance() {
        return this.balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getMobileNumber() {
        return this.mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public AccountType getAccountType() {
        return this.accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }
}
