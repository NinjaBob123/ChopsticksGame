package green.apps;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class NetworkHandler {
    private String serverAddress;
    private String key;
    private HttpClient httpClient;
    private ProxySelector proxySelector;

    public NetworkHandler() {
        this.serverAddress = "";
        this.proxySelector = new ProxySelector() {
            @Override
            public java.util.List<Proxy> select(URI uri) {
                return java.util.List.of(new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("127.0.0.1", 9050)));
            }

            @Override
            public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {
                System.err.println("Connection failed: " + ioe.getMessage());
            }
        };
        this.httpClient = HttpClient.newBuilder()
                .proxy(proxySelector)
                .build();
    }

    public int POST(String states) {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(serverAddress + "/api/v1/chopsticks/states/POST"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + key)
                .POST(HttpRequest.BodyPublishers.ofString(states))
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return 500;
        }
    }
}
