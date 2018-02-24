import com.ptrader.connector.kraken.KrakenApiClient;
import com.ptrader.connector.kraken.result.RecentTradeResult;

import java.util.Optional;

public class Main {

    public static void main(String args[]) {

        // Find these in your Kraken account (you might have to create them)
        String apiKey = "...the key here...";
        String secret = "...the private key here...";

        KrakenApiClient client = new KrakenApiClient(apiKey, secret);

        Optional<RecentTradeResult> optionalResult = client.getRecentTrades("XRPEUR");

        // Access result if any
        optionalResult.ifPresent(
                o -> System.out.println("result : " + o.getResult())
        );

        // Access last exchange.
        System.out.println(client.getLastExchange().toString());
    }
}
