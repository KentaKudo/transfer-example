package com.kentakudo.api;

import io.javalin.Javalin;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Javalin app = Javalin.create().start(7000);
        app.get("/accounts", ctx -> {
            List<Account> accounts = datastore.getAccounts();
            JSONObject json = new JSONObject();
            json.put("accounts", accounts);

            ctx.json(json);
        });
        app.post("/accounts", ctx -> {
            Account account = ctx.bodyAsClass(Account.class);
            datastore.createAccount(account);
            ctx.json(account);
        });
        app.get("/accounts/:id", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            ctx.json(datastore.getAccountById(id));
        });
        app.get("/transfers", ctx -> {
            List<Transfer> transfers = datastore.getTransfers();
            JSONObject json = new JSONObject();
            json.put("transfers", transfers);

            ctx.json(json);
        });
        app.post("/transfers", ctx -> {
            Transfer transfer = ctx.bodyAsClass(Transfer.class);
            Account fromUser = datastore.getAccountById(transfer.getFromUserId());
            Account toUser = datastore.getAccountById(transfer.getToUserId());
            fromUser.setAmount(fromUser.getAmount() - transfer.getAmount());
            toUser.setAmount(toUser.getAmount() + transfer.getAmount());
            datastore.createTransfer(transfer);
            ctx.json(transfer);
        });
        app.get("/transfers/:id", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            ctx.json(datastore.getTransferById(id));
        });
    }

    private static Datastore datastore = new Datastore();
}
