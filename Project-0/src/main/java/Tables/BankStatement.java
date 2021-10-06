package Tables;

public class BankStatement {
    private int transaction_id;
    private int user_id;
    private int account_id;
    private String account_type;
    private String transaction_type;
    private double amount;
    private double balance;

    public BankStatement() {
    }

    public String getAccount_type() {
        return account_type;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }

    public BankStatement(int transaction_id, int account_id, String account_type, String transaction_type, double amount, double balance) {
        this.transaction_id = transaction_id;
        this.account_id = account_id;
        this.account_type = account_type;
        this.transaction_type = transaction_type;
        this.amount = amount;
        this.balance = balance;
    }

    public int getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(int transaction_id) {
        this.transaction_id = transaction_id;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public String getTransaction_type() {
        return transaction_type;
    }

    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
