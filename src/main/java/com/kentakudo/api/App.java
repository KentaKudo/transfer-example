package com.kentakudo.api;

import io.javalin.Context;
import io.javalin.Javalin;

import java.util.List;

import org.json.simple.JSONObject;

public class App 
{
    public static void main( String[] args )
    {
        (new App(new Datastore()))
            .route()
            .start();
    }

    public App(Datastore datastore) {
        this.datastore = datastore;
    }

    public App route() {
        javalin.get("/accounts", this::getAccounts);
        javalin.post("/accounts", this::createAccount);
        javalin.get("/accounts/:id", this::getAccount);
        javalin.get("/transfers", this::getTransfers);
        javalin.post("/transfers", this::createTransfer);
        javalin.get("/transfers/:id", this::getTransfer);
        return this;
    }

    public void start() {
        javalin.start(7000);
    }

    public void stop() {
        javalin.stop();
    }

    public void getAccounts(Context ctx) {
        List<Account> accounts = datastore.getAccounts();
        JSONObject json = new JSONObject();
        json.put("accounts", accounts);

        ctx.json(json);
    }

    public void createAccount(Context ctx) {
        Account account = ctx.bodyAsClass(Account.class);
        datastore.createAccount(account);
        ctx.json(account);
    }

    public void getAccount(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Account account = datastore.getAccountById(id);
        if (account == null) {
            ctx.status(404).result("Not found");
            return;
        }
        ctx.json(datastore.getAccountById(id));
    }

    public void getTransfers(Context ctx) {
        List<Transfer> transfers = datastore.getTransfers();
        JSONObject json = new JSONObject();
        json.put("transfers", transfers);

        ctx.json(json);
    }

    public void createTransfer(Context ctx) {
        Transfer transfer = ctx.bodyAsClass(Transfer.class);
        Account fromUser = datastore.getAccountById(transfer.getFromUserId());
        Account toUser = datastore.getAccountById(transfer.getToUserId());
        if (fromUser == null || toUser == null) {
            ctx.status(404).result("Not found");
            return;
        }
        if (fromUser.getAmount() < transfer.getAmount()) {
            ctx.status(400).result("Invalid amount");
            return;
        }
        fromUser.setAmount(fromUser.getAmount() - transfer.getAmount());
        toUser.setAmount(toUser.getAmount() + transfer.getAmount());
        datastore.createTransfer(transfer);
        ctx.json(transfer);
    }

    public void getTransfer(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Transfer transfer = datastore.getTransferById(id);
        if (transfer == null) {
            ctx.status(404).result("Not found");
            return;
        }
        ctx.json(datastore.getTransferById(id));
    }

    private Javalin javalin = Javalin.create();
    private Datastore datastore;
}
