import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.form.FormData;
import io.undertow.server.handlers.form.FormDataParser;
import io.undertow.server.handlers.form.FormParserFactory;
import io.undertow.util.Headers;
import io.undertow.util.StatusCodes;


import java.io.IOException;

import static io.undertow.util.Methods.POST;

public class Main {

    public static void main(String args[])
    {
        HttpHandler startPage = new HttpHandler() {

            public void handleRequest(HttpServerExchange exchange) throws Exception {

                if(exchange.getRequestMethod().equals(POST)) {
                    FormDataParser parser = FormParserFactory.builder().build()
                            .createParser(exchange);
                    FormData data = parser.parseBlocking();

                    String firstName = data.getFirst("firstName").getValue();
                    String lastName = data.getFirst("lastName").getValue();

                    exchange.getResponseSender().send(firstName + " " + lastName + " processed");
                }
                else
                {

                    exchange.getResponseSender().send("");
                }
            }
        };

        Undertow server = Undertow.builder()
                .addHttpListener(8080,"0.0.0.0")
                .setHandler(Handlers.path().addExactPath("/index.html",startPage))


                .build();

        server.start();
    }
}
