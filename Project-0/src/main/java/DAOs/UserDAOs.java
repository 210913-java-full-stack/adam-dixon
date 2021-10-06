package DAOs;

import ListStructure.MyList;
import Tables.BankStatement;
import Tables.User;

import java.sql.*;

public class UserDAOs implements CrudOperations<User>{
    Connection connection;
    private MyList<User> users;

    public UserDAOs(Connection conn) {
        users = new MyList<>();
        connection = conn;
    }

    @Override
    public void save(User user) throws SQLException {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, user.getUser_id());
        ResultSet rs = ps.executeQuery();

        if (rs.next()){
            String sqlUpdate = "UPDATE users SET password = ? WHERE user_id = ?";
            PreparedStatement psUpdate = connection.prepareStatement(sqlUpdate);
            psUpdate.setString(1, user.getPassword());
            psUpdate.setInt(2, user.getUser_id());

            psUpdate.executeUpdate();

        } else {
            String sqlInsert = "INSERT INTO users (username, password, first_name, last_name) VALUES (?, ?, ?, ?)";
            PreparedStatement psInsert = connection.prepareStatement(sqlInsert);
            psInsert.setString(1, user.getUsername());
            psInsert.setString(2, user.getPassword());
            psInsert.setString(3, user.getFirstName());
            psInsert.setString(4, user.getLastName());

            psInsert.executeUpdate();
        }
    }

    @Override
    public User getItemByID(String username) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ?";
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
           return new User(rs.getInt("user_id"), rs.getString("username"), rs.getString("password"), rs.getString("first_name"), rs.getString("last_name"));
        }
        return null;
    }

    public User getItemByID(int e) throws SQLException {
        return null;
    }

    public User getItemByID(String username, String password) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, username);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return new User(rs.getInt("user_id"), rs.getString("username"), rs.getString("password"), rs.getString("first_name"), rs.getString("last_name"));
        }
        return null;
    }

    @Override
    public MyList<User> getAllItems() throws SQLException {
        String sql = "SELECT * FROM user_credentials";
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            User row = new User();
            row.setPassword(rs.getString("username"));
            row.setUsername(rs.getString("password"));
            users.add(row);
        }
        return users;
    }

    public MyList<User> getAllItems(String e) throws SQLException{
        return null;
    }

    @Override
    public void deleteByID(User user) throws SQLException {
        String sql = "DELETE FROM user_credentials WHERE ? = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, user.getUser_id());
        ResultSet rs = ps.executeQuery();
    }
}
