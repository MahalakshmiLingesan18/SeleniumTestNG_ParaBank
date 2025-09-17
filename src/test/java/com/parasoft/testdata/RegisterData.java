package com.parasoft.testdata;

import java.util.Map;

/**
 * Immutable POJO for Register sheet rows.
 * Constructor accepts Map<String,String> returned from BaseUtils.readExcel(...)
 */

public final class RegisterData {
	
	private final String firstName;
    private final String lastName;
    private final String address;
    private final String city;
    private final String state;
    private final String zipCode;
    private final String phone;
    private final String ssn;
    private final String username;
    private final String password;
    private final String confirmPassword;

    public RegisterData(String firstName, String lastName, String address, String city,
                        String state, String zipCode, String phone, String ssn,
                        String username, String password, String confirmPassword) {
        this.firstName = safe(firstName);
        this.lastName = safe(lastName);
        this.address = safe(address);
        this.city = safe(city);
        this.state = safe(state);
        this.zipCode = safe(zipCode);
        this.phone = safe(phone);
        this.ssn = safe(ssn);
        this.username = safe(username);
        this.password = safe(password);
        this.confirmPassword = safe(confirmPassword);
    }

    // convenience constructor from Map keys (use the exact header names from your sheet)
    public RegisterData(Map<String, String> row) {
        this(
            row == null ? "" : row.get("firstName"),
            row == null ? "" : row.get("lastName"),
            row == null ? "" : row.get("address"),
            row == null ? "" : row.get("city"),
            row == null ? "" : row.get("state"),
            row == null ? "" : row.get("zipCode"),
            row == null ? "" : row.get("phone"),
            row == null ? "" : row.get("ssn"),
            row == null ? "" : row.get("username"),
            row == null ? "" : row.get("password"),
            row == null ? "" : row.get("confirmPassword")
        );
    }

    private static String safe(String s) { 
    	return s == null ? "" : s.trim(); 
    }

    // getters
    public String getFirstName() { 
    	return firstName; 
    }
    
    public String getLastName() { 
    	return lastName; 
    }
    
    public String getAddress() { 
    	return address; 
    }
    
    public String getCity() { 
    	return city; 
    }
    
    public String getState() { 
    	return state; 
    }
    
    public String getZipCode() { 
    	return zipCode; 
    }
    
    public String getPhone() { 
    	return phone; 
    }
    
    public String getSsn() { 
    	return ssn; 
    }
    
    public String getUsername() { 
    	return username; 
    }
    
    public String getPassword() { 
    	return password; 
    }
    
    public String getConfirmPassword() { 
    	return confirmPassword; 
    }

    // mask helpers
    private String maskAllButLast(String s) {
        if (s == null || s.isEmpty()) return "<empty>";
        if (s.length() <= 3) return "***";
        int keep = Math.max(1, s.length() / 4);
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < s.length() - keep; i++) b.append('*');
        b.append(s.substring(s.length() - keep));
        return b.toString();
    }

    @Override
    public String toString() {
        return "RegisterData{" +
               "firstName=" + firstName +
               ", lastName=" + lastName +
               ", address=" + address +
               ", city=" + city +
               ", state=" + state +
               ", zipCode=" + zipCode +
               ", phone=" + (phone.isEmpty() ? "<empty>" : maskAllButLast(phone)) +
               ", ssn=" + (ssn.isEmpty() ? "<empty>" : maskAllButLast(ssn)) +
               ", username=" + (username.isEmpty() ? "<empty>" : username.charAt(0) + "****") +
               ", password=<hidden>" +
               "}";
    }

}
