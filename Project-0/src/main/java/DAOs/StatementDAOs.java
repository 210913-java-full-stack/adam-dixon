package DAOs;

import ListStructure.MyList;
import Tables.Account;
import Tables.BankStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StatementDAOs implements CrudOperations <BankStatement> {
    private Connection connection;

    public StatementDAOs(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(BankStatement bankStatement) throws SQLException {
        String sql = "SELECT * FROM bank_statements WHERE user_id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, bankStatement.getUser_id());
        ResultSet rs = ps.executeQuery();

        String sqlInsert = "INSERT INTO bank_statements (user_id, account_id, account_type, transaction_type, amount, balance) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement psInsert = connection.prepareStatement(sqlInsert);
        psInsert.setInt(1, bankStatement.getUser_id());
        psInsert.setInt(2, bankStatement.getAccount_id());
        psInsert.setString(3,bankStatement.getAccount_type());
        psInsert.setString(4, bankStatement.getTransaction_type());
        psInsert.setDouble(5,bankStatement.getAmount());
        psInsert.setDouble(6, bankStatement.getBalance());

        psInsert.executeUpdate();
    }

    @Override
    public BankStatement getItemByID(String user_id) throws SQLException {
        String sql = "SELECT * FROM bank_statements WHERE user_id = ?";
        PreparedStatement ps = connection.prepareStatement(sql, 1);
        int user = Integer.parseInt(user_id);
        ps.setInt(1, user);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            //public BankStatement(int transaction_id, int account_id, String account_type, String transaction_type, double amount, double balance)
            return new BankStatement(rs.getInt("transaction_id"), rs.getInt("account_id"), rs.getString("account_type"), rs.getString("transaction_type"), rs.getDouble("amount"), rs.getDouble("balance"));
        } else {
            return null;
        }
    }

    public BankStatement getItemByID(int e) throws SQLException {
        return null;
    }

    @Override
    public MyList<BankStatement> getAllItems() throws SQLException {
        String sql = "SELECT * from bank_statements";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();

        MyList<BankStatement> statements = new MyList<>();

        while(rs.next()){
            BankStatement row = new BankStatement();
            row.setTransaction_id(rs.getInt("transaction_id"));
            row.setUser_id(rs.getInt("user_id"));
            row.setAccount_id(rs.getInt("account_id"));
            row.setTransaction_type(rs.getString("transaction_type"));
            row.setAccount_type(rs.getString("account_type"));
            row.setAmount(rs.getDouble("amount"));
            row.setBalance(rs.getDouble("balance"));
            statements.add(row);
        }
        return statements;
    }

    public BankStatement getItemByID(String e, String c){
        return null;
    }

    public MyList<BankStatement> getAllItems(String user_id) throws SQLException {
        String sql = "SELECT * FROM bank_statements WHERE user_id = ?";
        PreparedStatement ps = connection.prepareStatement(sql, 1);
        int user = Integer.parseInt(user_id);
        ps.setInt(1, user);
        ResultSet rs = ps.executeQuery();

        MyList<BankStatement> statements = new MyList<>();

        while (rs.next()) {
            //public BankStatement(int transaction_id, int account_id, String account_type, String transaction_type, double amount, double balance)
            BankStatement entry =  new BankStatement(rs.getInt("transaction_id"), rs.getInt("account_id"), rs.getString("account_type"), rs.getString("transaction_type"), rs.getDouble("amount"), rs.getDouble("balance"));
            statements.add(entry);
        }
        return statements;
    }

    @Override
    public void deleteByID(BankStatement bankStatement) throws SQLException {
        String sql = "DELETE FROM bank_statements WHERE account_id = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1, bankStatement.getAccount_id());
        ResultSet rs = pstmt.executeQuery();
    }
}
