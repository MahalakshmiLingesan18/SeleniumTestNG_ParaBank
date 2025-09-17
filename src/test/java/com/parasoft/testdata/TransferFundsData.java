package com.parasoft.testdata;

import java.util.Map;

/**
 * Immutable POJO for TransferFunds rows.
 * Constructor accepts Map<String,String> returned from BaseUtils.readExcel(...)
 */

public final class TransferFundsData {
	
	private final String amount;
    private final String fromAccount;
    private final String toAccount;
    
    public TransferFundsData(String amount, String fromAccount, String toAccount) {
    	this.amount = safe(amount);
        this.fromAccount = safe(fromAccount);
        this.toAccount = safe(toAccount);
    }
    
    public TransferFundsData(Map<String, String> row) {
        this(
            row == null ? "" : row.get("amount"),
            row == null ? "" : row.get("fromAccount"),
            row == null ? "" : row.get("toAccount")
        );
    }
    
    private static String safe(String s) { 
    	return s == null ? "" : s.trim(); 
    }
    
    public String getAccount() { 
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
    
    @Override
    public String toString() {
        return "TransferFundsData{" +
               "amount=" + (amount.isEmpty() ? "<empty>" : maskMiddle(amount)) +
               "}";
    }

}
