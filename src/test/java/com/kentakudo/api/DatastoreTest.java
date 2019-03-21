package com.kentakudo.api;

import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class DatastoreTest extends TestCase {
    public DatastoreTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(DatastoreTest.class);
    }

    public void testAccount() throws Exception {
        Account result = sut.getAccountById(0);
        assertNull(result);
        List<Account> results = sut.getAccounts();
        assertEquals(0, results.size());

        Account mock = new Account(0, "Alice", 100);
        sut.createAccount(mock);

        result = sut.getAccountById(0);
        assertNotNull(result);
        results = sut.getAccounts();
        assertEquals(1, results.size());

        result.setAmount(101);
        sut.updateAccount(result);
        result = sut.getAccountById(0);
        assertEquals(101, result.getAmount());
    }

    public void testTransfer() throws Exception {
        Transfer result = sut.getTransferById(0);
        assertNull(result);
        List<Transfer> results = sut.getTransfers();
        assertEquals(0, results.size());

        Transfer mock = new Transfer(0, 51, 123, 456);
        sut.createTransfer(mock);

        result = sut.getTransferById(0);
        assertNotNull(result);
        results = sut.getTransfers();
        assertEquals(1, results.size());
    }

    private Datastore sut = new Datastore();
}