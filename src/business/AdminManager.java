package business;

import model.dao.AdminsTable;
import model.entity.Admin;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;

public class AdminManager {

    public final static int ROOT_PERMISSION_LEVEL  = Admin.LEVEL_ROOT;
    public final static int ADMIN_PERMISSION_LEVEL = Admin.LEVEL_ADMIN;
    public final static int BASIC_PERMISSION_LEVEL = Admin.LEVEL_BASIC;
    private AdminsTable adminsTable;

    public AdminManager(){
        this.adminsTable = new AdminsTable();
    }

    public HashMap<String, Object> getFromUsername(String username){
        ArrayList<Admin> res = adminsTable.getFrom(username, "username");
        HashMap<String, Object> admin = null;
        //la ricerca per username deve produrre un solo risultato
        if(!res.isEmpty()){
            admin = (res.get(0)).map();
        }
        return admin;
    }
    /**
     * crypt the string with SHA-256
     * @param password
     * @return
     */
    public static String cryptPassword(String password){
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        byte[] digest = md.digest(password.getBytes(StandardCharsets.UTF_8));
        BigInteger number = new BigInteger(1, digest);
        StringBuilder hexString = new StringBuilder(number.toString(16));
        while (hexString.length() < 64){
            hexString.insert(0, '0');
        }
        return hexString.toString();
    }
}

