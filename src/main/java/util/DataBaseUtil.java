package util;

import dao.DB;
import dao.DbException;

import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;

public class DataBaseUtil {

    public static int getID(String email) {
        String query = "SELECT email, id FROM password_manager.users";
        try {
            Connection connection = DB.getConnection();
            assert connection != null;
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                if (resultSet.getString("email").equals(UserRepository.standartHash(email))) {
                    return Integer.parseInt(resultSet.getString("id"));
                }
            }

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        return -1;
    }


    public static boolean existEmail(String email) {
        String query = "SELECT email FROM password_manager.users";

        try {
            Connection connection = DB.getConnection();
            assert connection != null;
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                if (resultSet.getString("email").equals(UserRepository.standartHash(email))) {
                    return true;
                }
            }
            return false;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }


    public static String getHashLogin(int id) {
        String query = "SELECT password FROM password_manager.users WHERE id = ?";

        try {
            Connection connection = DB.getConnection();
            assert connection != null;
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("password");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        return null;
    }


    public static ArrayList<ArrayList<String>> getLogins(int userID) {
        String query = "SELECT service_name, login, password, note FROM password_manager.passwords WHERE user_id = ?";
        ArrayList<ArrayList<String>> logins = new ArrayList<>();

        try {
            Connection connection = DB.getConnection();
            assert connection != null;
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userID);

            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()) {
                ArrayList<String> temporary = new ArrayList<>();

                temporary.add(resultSet.getString("service_name"));
                temporary.add(resultSet.getString("login"));
                temporary.add(resultSet.getString("password"));
                temporary.add(resultSet.getString("note"));

                logins.add(temporary);
            }

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        return logins;
    }


    public static byte[] getSaltLogin(int id) {
        try {
            Connection connection = DB.getConnection();
            assert connection != null;
            PreparedStatement statement = connection.prepareStatement("SELECT salt FROM password_manager.users WHERE id = ?");

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Base64.getDecoder().decode(resultSet.getString("salt"));
                }
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        return null;
    }


    public static boolean checkLogin(int userID, String hashPasswordUser) {
        return hashPasswordUser.equals(DataBaseUtil.getHashLogin(userID));
    }


    public static void saveLogin(int userID, String password, String login, String serviceName, String note) {
        String query = "INSERT INTO password_manager.passwords"
                + "(user_id, service_name, login, password, note)"
                + "VALUES"
                +"(?, ?, ?, ?, ?)";
        try {
            Connection connection = DB.getConnection();
            assert connection != null;
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, userID);
            statement.setString(2, serviceName);
            statement.setString(3, login);
            statement.setString(4, password);
            statement.setString(5, note);

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }


    public static int saveUser(String salt, String password, String email) {
        try {
            Connection connection = DB.getConnection();
            assert connection != null;
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO password_manager.users"
                    + "(salt, password, email)"
                    + "VALUES"
                    + "(?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, salt);
            statement.setString(2, password);
            statement.setString(3, email);

            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (Exception e) {
            throw new DbException(e.getMessage());
        }
        return 0;
    }


    public static void update(byte[] keyLogin, String updateText, String field, int id) {
        String query = "update password_manager.passwords set password_manager.passwords." + field + " = ? where id=?";
        try {
            Connection connection = DB.getConnection();
            assert connection != null;
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, AESManager.encrypt(keyLogin, updateText));
            statement.setInt(2, id);

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }


    public static void deleteLogin(int loginID) {
        String query = "delete from password_manager.passwords where id=?";

        try {
            Connection connection = DB.getConnection();
            assert connection != null;
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, Integer.toString(loginID));

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }
}
