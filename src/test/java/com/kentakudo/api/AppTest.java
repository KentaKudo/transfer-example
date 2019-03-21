package com.kentakudo.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AppTest extends TestCase {
    public AppTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(AppTest.class);
    }

    protected void setUp() throws Exception {
        sut.route().start();
    }

    protected void tearDown() {
        sut.stop();
    }

    public void testGetAccountsEmpty() throws Exception {
        String res = reqcurl("GET", "accounts", null);
        assertEquals("{\"accounts\":[]}", res);
    }

    public void testGetAccounts() throws Exception {
        mock.createAccount(new Account(0, "Alice", 100));
        String res = reqcurl("GET", "accounts", null);
        assertEquals("{\"accounts\":[{\"id\":0,\"name\":\"Alice\",\"amount\":100}]}", res);
    }

    public void testPostAccounts() throws Exception {
        String res = reqcurl("POST", "accounts", "{\"name\":\"Alice\",\"amount\":100}");
        assertEquals("{\"id\":0,\"name\":\"Alice\",\"amount\":100}", res);
        assertNotNull(mock.getAccountById(0));
    }

    public void testGetAccountNotFound() throws Exception {
        String res = reqcurl("GET", "accounts/0", null);
        assertEquals("Not found", res);
    }

    public void testGetAccount() throws Exception {
        mock.createAccount(new Account(0, "Alice", 100));
        String res = reqcurl("GET", "accounts/0", null);
        assertEquals("{\"id\":0,\"name\":\"Alice\",\"amount\":100}", res);
    }

    public void testGetTransfersEmpty() throws Exception {
        String res = reqcurl("GET", "transfers", null);
        assertEquals("{\"transfers\":[]}", res);
    }

    public void testGetTransfers() throws Exception {
        mock.createTransfer(new Transfer(0, 51, 123, 456));
        String res = reqcurl("GET", "transfers", null);
        assertEquals("{\"transfers\":[{\"id\":0,\"amount\":51,\"from_user_id\":123,\"to_user_id\":456}]}", res);
    }

    public void testPostTransfers() throws Exception {
        mock.createAccount(new Account(0, "Alice", 100));
        mock.createAccount(new Account(1, "Bob", 100));
        // transfer 51 from Alice to Bob
        String res = reqcurl("POST", "transfers", "{\"amount\":51,\"from_user_id\":0,\"to_user_id\":1}");
        assertEquals("{\"id\":0,\"amount\":51,\"from_user_id\":0,\"to_user_id\":1}", res);

        Transfer result = mock.getTransferById(0);
        assertNotNull(result);
        assertEquals(51, result.getAmount());
        assertEquals(0, result.getFromUserId());
        assertEquals(1, result.getToUserId());

        Account alice = mock.getAccountById(0);
        assertEquals(49, alice.getAmount());
        Account bob = mock.getAccountById(1);
        assertEquals(151, bob.getAmount());
    }

    public void testPostTransfersInvalidAccount() throws Exception {
        String res = reqcurl("POST", "transfers", "{\"amount\":51,\"from_user_id\":0,\"to_user_id\":1}");
        assertEquals("Not found", res);
    }

    public void testPostTransfersInvalidAmount() throws Exception {
        mock.createAccount(new Account(0, "Alice", 100));
        mock.createAccount(new Account(1, "Bob", 100));
        String res = reqcurl("POST", "transfers", "{\"amount\":101,\"from_user_id\":0,\"to_user_id\":1}");
        assertEquals("Invalid amount", res);
    }

    public void testGetTransferEmpty() throws Exception {
        String res = reqcurl("GET", "transfers/0", null);
        assertEquals("Not found", res);
    }

    public void testGetTransfer() throws Exception {
        mock.createTransfer(new Transfer(0, 51, 123, 456));
        String res = reqcurl("GET", "transfers/0", null);
        assertEquals("{\"id\":0,\"amount\":51,\"from_user_id\":123,\"to_user_id\":456}", res);
    }

    private static String reqcurl(String method, String path, String body) {
        try {
            String data = body != null ? String.format("-d %s", body) : "";
            String curl = String.format("curl -X %s %s http://localhost:7000/%s", method, data, path);
            Process proc = Runtime.getRuntime().exec(curl);

            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                builder.append(line);
            }
            in.close();
            return builder.toString();
        } catch (Exception e) {
            return "";
        }
    }

    private Datastore mock = new Datastore();
    private App sut = new App(mock);
}
