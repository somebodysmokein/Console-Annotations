package com.browserstack.test.consoleannotations;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.remote.AppiumW3CHttpCommandCodec;
import org.openqa.selenium.UnsupportedCommandException;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.profiler.HttpProfilerLogEntry;
import org.openqa.selenium.remote.*;
import org.openqa.selenium.remote.http.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.openqa.selenium.remote.DriverCommand.QUIT;

public class WebMdCommandExecutor extends HttpCommandExecutor {

    public SessionId session;

    private URL remoteServer;
    public HttpClient client;
    public HttpClient.Factory httpClientFactory;
    private ResponseCodec<HttpResponse> responseCodec;
    public static final String AUTOMATE_USERNAME = System.getenv("BROWSERSTACK_USERNAME");
    public static final String AUTOMATE_ACCESS_KEY = System.getenv("BROWSERSTACK_ACCESS_KEY");

    public WebMdCommandExecutor(URL addressOfRemoteServer, SessionId session) throws MalformedURLException {
        super(addressOfRemoteServer);
        remoteServer = new URL("https://" + AUTOMATE_USERNAME + ":" + AUTOMATE_ACCESS_KEY + "@hub-cloud.browserstack.com/wd/hub");
        this.session=session;

    }

    @Override
    public void defineCommand(String commandName, CommandInfo info) {
        super.defineCommand(commandName,info);
    }


    @Override
    public Response execute(Command command) throws IOException {
        AppiumW3CHttpCommandCodec commandCodec=new AppiumW3CHttpCommandCodec();
        HttpRequest httpRequest = commandCodec.encode(command);
        responseCodec=new org.openqa.selenium.remote.codec.w3c.W3CHttpResponseCodec();
        try {

            httpClientFactory = HttpClient.Factory.createDefault();
            client= httpClientFactory.createClient(remoteServer);

            HttpResponse httpResponse = client.execute(httpRequest);


            Response response = responseCodec.decode(httpResponse);
            if (response.getSessionId() == null) {
                if (httpResponse.getTargetHost() != null) {
                    //response.setSessionId(String.valueOf(HttpSessionId.getSessionId(httpResponse.getTargetHost())));
                    response.setSessionId((String)HttpSessionId.getSessionId(httpResponse.getTargetHost()).orElse((String) null));
                } else {
                    // Spam in the session id from the request
                    response.setSessionId(command.getSessionId().toString());
                }
            }
            if (QUIT.equals(command.getName())) {
                httpClientFactory.cleanupIdleClients();
            }
            return response;
        } catch (UnsupportedCommandException e) {
            if (e.getMessage() == null || "".equals(e.getMessage())) {
                throw new UnsupportedOperationException(
                        "No information from server. Command name was: " + command.getName(),
                        e.getCause());
            }
            throw e;
        }

    }

}
