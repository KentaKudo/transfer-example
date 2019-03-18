package com.kentakudo.api;

import io.javalin.Javalin;

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
            ctx.result("TODO: GET /accounts\n");
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
}
