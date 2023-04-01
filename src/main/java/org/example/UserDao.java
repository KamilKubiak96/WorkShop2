package org.example;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.Arrays;

public class UserDao extends User {

    public static final String CREATE_USER_QUERY =
            "INSERT INTO users(username, email, password) VALUES(?,?,?";
    private static final String READ_USER_QUERY =
            "SELECT * FROM users WHERE id =?";
    private static final String UPDATE_USER_QUERY =
            "UPDATE users SET email = ?, username = ?, password = ?, WHERE Id = ?";
    private static final String DELETE_USER_QUERY =
            "DELETE FROM users WHERE id = ?";
    private static final String SHOW_ALL =
            "SELECT * FROM users";

    public User[] findAll() {

        try (Connection conn = DBUtill.connect();
             PreparedStatement stmt = conn.prepareStatement(SHOW_ALL);
             ResultSet rs = stmt.executeQuery()) {
            User[] usersList = new User[0];

            while (rs.next()) {
                User user = new User();
                usersList = Arrays.copyOf(usersList, usersList.length + 1);
                usersList[usersList.length - 1] = new User
                        (rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
                System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4));
            }
            return usersList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void update(User user) {
        try (Connection conn = DBUtill.connect()) {
            PreparedStatement prepStm = conn.prepareStatement(UPDATE_USER_QUERY, Statement.RETURN_GENERATED_KEYS);
            prepStm.setString(1, user.getUserName());
            prepStm.setString(2, user.getEmail());
            prepStm.setString(3, hashPassword(user.getPassword()));
            prepStm.setInt(4, user.getId());
            prepStm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public User create(User user) {

        try (Connection conn = DBUtill.connect()) {
            PreparedStatement preparedStatement = conn.prepareStatement(CREATE_USER_QUERY, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, this.hashPassword(user.getPassword()));
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void remove(int userId) {
        try (Connection conn = DBUtill.connect()) {
            PreparedStatement preparedStatement = conn.prepareStatement(DELETE_USER_QUERY);
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public User read(int userId) {
        try (Connection conn = DBUtill.connect()) {
            PreparedStatement statement = conn.prepareStatement(READ_USER_QUERY);
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUserName(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User readToUpdate(int userId) {
        try (Connection conn = DBUtill.connect()) {
            PreparedStatement stmt = conn.prepareStatement(READ_USER_QUERY);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                System.out.println(rs.getInt(1) + " " + rs.getString(2) +
                        " " + rs.getString(3) + " " + rs.getString(4));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());

    }
}