package model.entity;

import java.util.HashMap;

public class Admin implements Entity{
    public static final String TABLE_NAME = "admins";

    //user level constants
    public static final int LEVEL_ROOT  = 0;
    public static final int LEVEL_ADMIN = 1;
    public static final int LEVEL_BASIC = 2;

    private String username;
    private String password;
    private int level;

    public Admin(String username,String password,int level){
        this.username = username;
        this.password = password;
        this.level = level;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getLevel() {
        return level;
    }

    @Override
    public String getTableName() {
        return this.TABLE_NAME;
    }

    @Override
    public HashMap<String, Object> map() {
        HashMap<String, Object> res = new HashMap<String, Object>();
        res.put("username", this.username);
        res.put("password", this.password);
        res.put("level", this.level);
        return res;
    }
}
