package me.divkix.cse360project.helperFunctions;


import java.util.Map;

import static me.divkix.cse360project.Healnet.userDetailsTable;

public class checkLoginInfo {
    public static boolean checkLogin(String username, String password) {
        // Check if the username and password are correct by getting data from the database and comparing it
        Map<String, String> userDetails = sqlHelper.getData(userDetailsTable, username);
        return userDetails.get("password").equals(password);
    }
}
