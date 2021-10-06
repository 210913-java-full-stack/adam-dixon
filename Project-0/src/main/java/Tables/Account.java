package Tables;

public class Account {
    private int account_id;
    private double balance;
    private String account_type;
    private int user_id;

    public Account(int user_id, int account_id, String account_type, double balance) {
        this.user_id = user_id;
        this.account_id = account_id;
        this.account_type = account_type;
        this.balance = balance;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getAccount_type() {
        return account_type;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Account() {
    }
}
