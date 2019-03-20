package com.kentakudo.api;

import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;

import io.javalin.Context;
import io.javalin.core.util.ContextUtil;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    private HttpServletRequest mockReq;
    private HttpServletResponse mockRes;

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    protected void setUp() {
        mockReq = mock(HttpServletRequest.class);
        mockRes = mock(HttpServletResponse.class);
    }

    public void testGetAccounts() throws Exception
    {
        Context ctx = ContextUtil.init(mockReq, mockRes);
        App sut = new App(new Datastore());
        sut.getAccounts(ctx);

        assertEquals("{\"accounts\":[]}", ctx.resultString());
    }

    public void testGetAccountsOne() throws Exception
    {
        Context ctx = ContextUtil.init(mockReq, mockRes);
        Datastore mockDatastore = new Datastore();
        mockDatastore.createAccount(new Account(0, "Alice", 100));
        App sut = new App(mockDatastore);
        sut.getAccounts(ctx);

        assertEquals("{\"accounts\":[{\"id\":0,\"name\":\"Alice\",\"amount\":100}]}", ctx.resultString());
    }
}
