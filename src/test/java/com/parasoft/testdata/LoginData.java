package com.parasoft.testdata;

import java.util.Map;

/**
 * Simple immutable POJO to hold login credentials.
 * Overrides toString() to avoid leaking credentials in logs/reports.
 */

public final class LoginData {
	
	private final String username;
    private final String password;

    public LoginData(String username, String password) {
        this.username = safe(username);
        this.password = safe(password);
    }

    // convenience constructor from Map (your BaseUtils returns Map<String, String>)
    public LoginData(Map<String, String> row) {
        this(row == null ? "" : row.get("username"),
             row == null ? "" : row.get("password"));
    }
    
    private static String safe(String s) { 
    	return s == null ? "" : s.trim(); 
    }

    public String getUsername() { 
    	return username; 
    }
    
    public String getPassword() { 
    	return password; 
    }

    private String mask(String s) {
        if (s == null || s.isEmpty()) return "<empty>";
        if (s.length() <= 2) return "**";
        return s.charAt(0) + "****" + s.charAt(s.length() - 1);
    }

    @Override
    public String toString() {
        // mask username partially and hide password entirely
        return "LoginData{username=" + mask(username) + ", password=<hidden>}";
    }

}

