package com.kentakudo.api;

import io.javalin.Context;
import io.javalin.Javalin;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class App 
{
    public static void main( String[] args )
    {
        App app = new App(new Datastore());
        app.start();
        app.javalin.get("/accounts", app::getAccounts);
        app.javalin.post("/accounts", app::createAccount);
        app.javalin.get("/accounts/:id", app::getAccount);
        app.javalin.get("/transfers", app::getTransfers);
        app.javalin.post("/transfers", app::createTransfer);
        app.javalin.get("/transfers/:id", app::getTransfer);
    }

    public App(Datastore datastore) {
        this.datastore = datastore;
    }

    public Javalin start() {
        return javalin.start(7000);
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
        fromUser.setAmount(fromUser.getAmount() - transfer.getAmount());
        toUser.setAmount(toUser.getAmount() + transfer.getAmount());
        datastore.createTransfer(transfer);
        ctx.json(transfer);
    }

    public void getTransfer(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        ctx.json(datastore.getTransferById(id));
    }

    private Javalin javalin = Javalin.create();
    private Datastore datastore;
}
