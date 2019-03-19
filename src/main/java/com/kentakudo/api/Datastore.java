package com.kentakudo.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Datastore
{
    public Datastore(){
        data.put(KEY_ACCOUNTS, new ArrayList<Object>());
        data.put(KEY_TRANSFERS, new ArrayList<Object>());
    }

    public List<Account> getAccounts() {
        List<Account> result = new ArrayList<Account>();
        for (Object obj : data.get(KEY_ACCOUNTS)) {
            result.add((Account)obj);
        }
        return result;
    }

    public void createAccount(Account account) {
        int id = data.get(KEY_ACCOUNTS).size();
        account.setId(id);
        data.get(KEY_ACCOUNTS).add(account);
    }

    public Account getAccountById(int id) {
        for (Object obj : data.get(KEY_ACCOUNTS)) {
            Account account = (Account)obj;
            if (account.getId() == id) {
                return account;
            }
        }

        return null;
    }

    public List<Transfer> getTransfers() {
        List<Transfer> result = new ArrayList<Transfer>();
        for (Object obj : data.get(KEY_TRANSFERS)) {
            result.add((Transfer)obj);
        }
        return result;
    }

    public void createTransfer(Transfer transfer) {
        int id = data.get(KEY_TRANSFERS).size();
        transfer.setId(id);
        data.get(KEY_TRANSFERS).add(transfer);
    }

    public Transfer getTransferById(int id) {
        for (Object obj : data.get(KEY_TRANSFERS)) {
            Transfer transfer = (Transfer)obj;
            if (transfer.getId() == id) {
                return transfer;
            }
        }

        return null;
    }

    private static final String KEY_ACCOUNTS = "accounts";
    private static final String KEY_TRANSFERS = "transfers";
    private Map<String, List<Object>> data = new HashMap<String, List<Object>>();
}