package DAOs;

import ListStructure.MyList;
import Tables.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDAOs implements CrudOperations<Account>{
    Connection connection;

    public AccountDAOs(Connection conn) {
        connection = conn;
    }

    @Override
    public void save(Account account) throws SQLException {
        String sql = "SELECT * FROM accounts WHERE user_id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, account.getUser_id());
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            if (account.getAccount_id() == 0) {
                String sqlInsert = "INSERT INTO accounts (user_id, account_type, balance) VALUES (?, ?, ?)";
                PreparedStatement psInsert = connection.prepareStatement(sqlInsert);
                psInsert.setInt(1, account.getUser_id());
                psInsert.setString(2, account.getAccount_type());
                psInsert.setDouble(3, account.getBalance());

                psInsert.executeUpdate();
            } else {
                String sqlUpdate = "UPDATE accounts SET balance = ? WHERE account_id = ?";
                PreparedStatement psUpdate = connection.prepareStatement(sqlUpdate);
                psUpdate.setDouble(1, account.getBalance());
                psUpdate.setInt(2, account.getAccount_id());

                psUpdate.executeUpdate();
            }
        } else {
            String sqlInsert = "INSERT INTO accounts (user_id, account_type, balance) VALUES (?, ?, ?)";
            PreparedStatement psInsert = connection.prepareStatement(sqlInsert);
            psInsert.setInt(1, account.getUser_id());
            psInsert.setString(2, account.getAccount_type());
            psInsert.setDouble(3, account.getBalance());

            psInsert.executeUpdate();
        }
    }

    @Override
    public Account getItemByID(String user_id) throws SQLException {
        String sql = "SELECT * FROM accounts WHERE user_id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        int user = Integer.parseInt(user_id);
        ps.setInt(1, user);
        ResultSet rs = ps.executeQuery();

        if (rs.next()){
            return new Account(rs.getInt("user_id"), rs.getInt("account_id"), rs.getString("account_type"), rs.getDouble("balance"));
        } else {
            return null;
        }

    }

    public Account getItemByID(int account_id) throws SQLException {
        String sql = "SELECT * FROM accounts WHERE account_id LIKE ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        String parameter = "%";
        parameter = parameter + account_id;
        ps.setString(1, parameter);
        ResultSet rs = ps.executeQuery();

        if (rs.next()){
            return new Account(rs.getInt("user_id"), rs.getInt("account_id"), rs.getString("account_type"), rs.getDouble("balance"));
        } else {
            return null;
        }

    }

    public Account getItemByID(String e, String c){
        return null;
    }

    @Override
    public MyList<Account> getAllItems() throws SQLException {
        String sql = "SELECT * from accounts";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();

        MyList<Account> accounts = new MyList<>();

        while(rs.next()){
            Account row = new Account();
            row.setAccount_id(rs.getInt("account_id"));
            row.setBalance(rs.getDouble("balance"));
            accounts.add(row);
        }
        return accounts;
    }

    public MyList<Account> getAllItems(String user_id) throws SQLException{
        String sql = "SELECT * FROM accounts WHERE user_id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        int user = Integer.parseInt(user_id);
        ps.setInt(1, user);
        ResultSet rs = ps.executeQuery();

        MyList<Account> accounts = new MyList<>();

        while(rs.next()){
            Account row = new Account();
            row.setUser_id(rs.getInt("user_id"));
            row.setAccount_id(rs.getInt("account_id"));
            row.setAccount_type(rs.getString("account_type"));
            row.setBalance(rs.getDouble("balance"));
            accounts.add(row);
        }
        return accounts;
    }

    @Override
    public void deleteByID(Account account) throws SQLException {
        String sql = "DELETE FROM accounts WHERE ? = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, "account_id");
        pstmt.setInt(2, account.getAccount_id());
        ResultSet rs = pstmt.executeQuery();
    }
}
