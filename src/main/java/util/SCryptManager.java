package util;

import config.Config;
import org.bouncycastle.crypto.generators.SCrypt;

public class SCryptManager {
    public static byte[] generateUser(String password, byte[] salt) {
        return SCrypt.generate(password.getBytes(), salt, Config.COST_FACTOR, Config.BLOCK_SIZE, Config.PARALLELIZATION_USER, Config.KEY_LENGTH);
    }

    public static byte[] generateLogin(String password, byte[] salt) {
        return SCrypt.generate(password.getBytes(), salt, Config.COST_FACTOR, Config.BLOCK_SIZE, Config.PARALLELIZATION_LOGIN, Config.KEY_LENGTH);
    }
}
