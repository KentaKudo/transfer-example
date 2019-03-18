package com.kentakudo.api;

import java.util.HashMap;
import java.util.Map;

public class Datastore
{
    public Datastore(){
        data.put(KEY_ACCOUNTS, new Account[] {});
    }

    public Account[] getAccounts() {
        return (Account[])data.get(KEY_ACCOUNTS);
    }

    public void createAccount(Account account) {
        int id = data.get(KEY_ACCOUNTS).length + 1;
        account.setId(id);
    }

    private static final String KEY_ACCOUNTS = "accounts";
    private Map<String, Object[]> data = new HashMap<String, Object[]>();
}