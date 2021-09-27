package io.sl.webcalculator;


import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;

public class SubtractingApplication {

    private int port;
    private Undertow server;

    public SubtractingApplication(int port) {
        this.port = port;
    }

    public void start() {
        server = Undertow.builder()
                .addHttpListener(port, "localhost")
                .setHandler(Handlers.routing().get("/subtract", new HttpHandler() {
                    @Override
                    public void handleRequest(final HttpServerExchange exchange) throws Exception {
                        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
                        int a = Integer.parseInt(exchange.getQueryParameters().get("a").getFirst());
                        int b = Integer.parseInt(exchange.getQueryParameters().get("b").getFirst());
                        Calculator calculator = new Calculator();
                        exchange.getResponseSender().send(calculator.subtract(a, b) + "");
                    }
                })).build();
        server.start();
    }

    public void stop() {
        server.stop();
    }

}
