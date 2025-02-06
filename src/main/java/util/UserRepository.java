package util;

import config.Config;
import dao.DB;
import model.User;
import org.bouncycastle.crypto.generators.SCrypt;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
import java.util.ArrayList;

import static util.DataBaseUtil.getSaltLogin;
import static util.DataBaseUtil.saveUser;

public class UserRepository {
    public static void createUser(String email, String password) {

        byte[] salt = util.Random.generateSalt();
        byte[] hashPassword = SCryptManager.generateUser(password, salt);
        byte[] hashEmail = SCryptManager.generateUser(email, Config.STANDART_SALT);

        String hashPasswordBase64 = Base64.getEncoder().encodeToString(hashPassword);
        String hashEmailBase64 = Base64.getEncoder().encodeToString(hashEmail);
        String saltBase64 = Base64.getEncoder().encodeToString(salt);

        saveUser(saltBase64, hashPasswordBase64, hashEmailBase64);
    }


    public static String standartHash(String text) {
        byte[] hash = SCryptManager.generateUser(text, Config.STANDART_SALT);
        return Base64.getEncoder().encodeToString(hash);
    }


    public static String passwordHash(String password, int id) {
        byte[] hash = SCryptManager.generateUser(password, getSaltLogin(id));
        return Base64.getEncoder().encodeToString(hash);
    }


    public static User loadLogins(int userID, byte[] keyLogin) {
        try {
            Connection connection = DB.getConnection();

            String sql = "SELECT service_name, login, password, note, id FROM password_manager.passwords WHERE user_id = ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userID);
            ResultSet resultSet = statement.executeQuery();

            ArrayList<ArrayList<String>> logins = new ArrayList<>();

            if (!resultSet.isBeforeFirst()) {
                return new User(userID, logins, keyLogin);
            }

            Cipher cipher = Cipher.getInstance("AES", new BouncyCastleProvider());

                while (resultSet.next()) {
                    String serviceName = resultSet.getString("service_name");
                    serviceName = AESManager.decrypt(keyLogin, serviceName, cipher);

                    String login = resultSet.getString("login");
                    login = AESManager.decrypt(keyLogin, login, cipher);

                    String password = resultSet.getString("password");
                    password = AESManager.decrypt(keyLogin, password, cipher);

                    String note = resultSet.getString("note");
                    note = AESManager.decrypt(keyLogin, note, cipher);

                    int id = resultSet.getInt("id");

                    ArrayList<String> arrayList = new ArrayList<>();
                    arrayList.add(serviceName);
                    arrayList.add(login);
                    arrayList.add(password);
                    arrayList.add(note);
                    arrayList.add(Integer.toString(id));

                    logins.add(arrayList);
                }
                return new User(userID, logins, keyLogin);
            } catch (Exception e) {
                throw new RuntimeException(e);
        }
    }


    public static void createLogin(byte[] keyLogin, String service, String login, String password, String note, int userID) {
        String serviceEncrypted = AESManager.encrypt(keyLogin, service);
        String loginEncrypted = AESManager.encrypt(keyLogin, login);
        String passwordEncrypted = AESManager.encrypt(keyLogin, password);
        String noteEncrypted = AESManager.encrypt(keyLogin, note);

        DataBaseUtil.saveLogin(userID, passwordEncrypted, loginEncrypted, serviceEncrypted, noteEncrypted);
    }
}
