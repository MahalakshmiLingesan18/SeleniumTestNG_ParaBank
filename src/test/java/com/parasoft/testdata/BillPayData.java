package com.parasoft.testdata;

import java.util.Map;

/**
 * Immutable POJO for BillPay rows.
 * Constructor accepts Map<String,String> returned from BaseUtils.readExcel(...)
 */

public final class BillPayData {
	
	private final String payeeName;
    private final String address;
    private final String city;
    private final String state;
    private final String zipCode;
    private final String phone;
    private final String account;
    private final String verifyAccount;
    private final String amount;

    public BillPayData(String payeeName, String address, String city, String state,
                       String zipCode, String phone, String account,
                       String verifyAccount, String amount) {
        this.payeeName = safe(payeeName);
        this.address = safe(address);
        this.city = safe(city);
        this.state = safe(state);
        this.zipCode = safe(zipCode);
        this.phone = safe(phone);
        this.account = safe(account);
        this.verifyAccount = safe(verifyAccount);
        this.amount = safe(amount);
    }

    public BillPayData(Map<String, String> row) {
        this(
            row == null ? "" : row.get("payeeName"),
            row == null ? "" : row.get("address"),
            row == null ? "" : row.get("city"),
            row == null ? "" : row.get("state"),
            row == null ? "" : row.get("zipCode"),
            row == null ? "" : row.get("phone"),
            row == null ? "" : row.get("account"),
            row == null ? "" : row.get("verifyAccount"),
            row == null ? "" : row.get("amount")
        );
    }

    private static String safe(String s) { 
    	return s == null ? "" : s.trim(); 
    }

    // getters
    public String getPayeeName() { 
    	return payeeName; 
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
    
    public String getAccountNumber() { 
    	return account; 
    }
    
    public String getVerifyAccountNumber() { 
    	return verifyAccount; 
    }
    
    public String getAmount() { 
    	return amount; 
    }

    // mask helpers
    private String maskMiddle(String s) {
        if (s == null || s.isEmpty()) return "<empty>";
        if (s.length() <= 4) return "****";
        String start = s.substring(0, 2);
        String end = s.substring(s.length() - 2);
        StringBuilder b = new StringBuilder(start);
        for (int i = 0; i < s.length() - 4; i++) b.append('*');
        b.append(end);
        return b.toString();
    }
    
    private String maskAllButLast(String s) {
        if (s == null || s.isEmpty()) return "<empty>";
        if (s.length() <= 3) return "***";
        int keep = Math.max(1, s.length() / 4);
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < s.length() - keep; i++) b.append('*');
        b.append(s.substring(s.length() - keep));
        return b.toString();
    }
    
    private String mask(String s) {
        if (s == null || s.isEmpty()) return "<empty>";
        if (s.length() <= 2) return "**";
        return s.charAt(0) + "****";
    }

    @Override
    public String toString() {
        return "BillPayData{" +
               "payeeName=" + payeeName +
               ", address=" + address +
               ", city=" + city +
               ", state=" + state +
               ", zipCode=" + zipCode +
               ", phone=" + (phone.isEmpty() ? "<empty>" : maskAllButLast(phone)) +
               ", accountNumber=" + (account.isEmpty() ? "<empty>" : maskMiddle(account)) +
               ", verifyAccountNumber=" + (verifyAccount.isEmpty() ? "<empty>" : maskAllButLast(verifyAccount)) +
               ", amount=" + (amount.isEmpty() ? "<empty>" : mask(amount)) +
               "}";
    }

}
