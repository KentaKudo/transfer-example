package com.kentakudo.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import kotlin.Pair;

public class Datastore
{
    public Datastore(){
        data.put(KEY_ACCOUNTS, new ArrayList<Object>());
        data.put(KEY_TRANSFERS, new ArrayList<Object>());
    }

    public List<Account> getAccounts() {
        List<Account> result = new ArrayList<Account>();
        readLock.lock();
        for (Object obj : data.get(KEY_ACCOUNTS)) {
            result.add((Account)obj);
        }
        readLock.unlock();
        return result;
    }

    public void createAccount(Account account) {
        writeLock.lock();
        int id = data.get(KEY_ACCOUNTS).size();
        account.setId(id);
        data.get(KEY_ACCOUNTS).add(account);
        writeLock.unlock();
    }

    public Account getAccountById(int id) {
        readLock.lock();
        for (Object obj : data.get(KEY_ACCOUNTS)) {
            Account account = (Account)obj;
            if (account.getId() == id) {
                readLock.unlock();
                return account;
            }
        }

        readLock.unlock();
        return null;
    }

    public List<Transfer> getTransfers() {
        List<Transfer> result = new ArrayList<Transfer>();
        readLock.lock();
        for (Object obj : data.get(KEY_TRANSFERS)) {
            result.add((Transfer)obj);
        }
        readLock.unlock();
        return result;
    }

    public void createTransfer(Transfer transfer) {
        writeLock.lock();
        int id = data.get(KEY_TRANSFERS).size();
        transfer.setId(id);
        data.get(KEY_TRANSFERS).add(transfer);
        writeLock.unlock();
    }

    public Transfer getTransferById(int id) {
        readLock.lock();
        for (Object obj : data.get(KEY_TRANSFERS)) {
            Transfer transfer = (Transfer)obj;
            if (transfer.getId() == id) {
                readLock.unlock();
                return transfer;
            }
        }

        readLock.unlock();
        return null;
    }

    @FunctionalInterface
    interface Callback {
        Pair<Integer, String> apply(Datastore datastore);
    }

    public Pair<Integer, String> runWithLock(Callback callback)
    {
        // ReentrantLock allows you to call lock more than twice in the same thread;
        // If lock is called inside apply it won't get deadlocked.
        writeLock.lock();
        Pair<Integer, String> res = callback.apply(this);
        writeLock.unlock();
        return res;
    }

    private static final String KEY_ACCOUNTS = "accounts";
    private static final String KEY_TRANSFERS = "transfers";

    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private Lock readLock = readWriteLock.readLock();
    private Lock writeLock = readWriteLock.writeLock();
    private Map<String, List<Object>> data = new HashMap<String, List<Object>>();
}