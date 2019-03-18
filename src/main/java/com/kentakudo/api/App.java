package com.kentakudo.api;

import io.javalin.Javalin;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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
            Account[] accounts = datastore.getAccounts();
            JSONArray objs = new JSONArray();
            for (Account account : accounts) {
                JSONObject obj = new JSONObject();
                obj.put("id", account.getId());
                obj.put("name", account.getName());
                obj.put("amount", account.getAmount());

                objs.add(obj);
            }

            JSONObject json = new JSONObject();
            json.put("accounts", objs);

            ctx.result(json.toString());
        });
        app.post("/accounts", ctx -> {
            ctx.result("TODO: POST /accounts\n");
        });
        app.get("/accounts/:id", ctx -> {
            ctx.result("TODO: GET /accounts/" + ctx.pathParam("id") + "\n");
        });
        app.post("/accounts/:id", ctx -> {
            ctx.result("TODO: POST /accounts/"+ ctx.pathParam("id") + "\n");
        });
        app.get("/transfers", ctx -> {
            ctx.result("TODO: GET /transfers\n");
        });
        app.post("/transfers", ctx -> {
            ctx.result("TODO: POST /transfers\n");
        });
        app.get("/transfers/:id", ctx -> {
            ctx.result("TODO: GET /transfers/" + ctx.pathParam("id") + "\n");
        });
    }

    private static Datastore datastore = new Datastore();
}
