import com.ptrader.connector.ApiJsonExchange;
import com.ptrader.connector.kraken.KrakenApiClient;
import com.ptrader.connector.kraken.KrakenApiException;
import com.ptrader.connector.kraken.result.Result;

public class Main {

    public static void main (String args[]) {

        String apiKey = "...the key here...";
        String secret = "...the private key here...";

        KrakenApiClient client = new KrakenApiClient(apiKey, secret);

        try {
            Result result = client.getOpenOrders();

            System.out.println("result : " + result.getResult());
            System.out.println("error : " + result.getError());
        } catch (KrakenApiException e) {
            e.printStackTrace();
        }

        ApiJsonExchange lastExchange = client.getLastExchange();

        System.out.println(lastExchange.toString());


    }
}
