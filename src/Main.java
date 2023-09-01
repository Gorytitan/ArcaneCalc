import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import

public class Main {
    public static void main(String[] args) {
        //https://api.warframe.market/v1
        try {
            URL url = new URL("https://api.warframe.market/v1/items/primed_fulmination/orders");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.connect();

            //Check if connect is made
            int responseCode = conn.getResponseCode();

            //200 ok
            if (responseCode != 200)
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            else {
                StringBuilder informationString = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());

                while (scanner.hasNext()){
                    informationString.append(scanner.nextLine());
                }
                scanner.close();

                System.out.println(informationString);
            }

            conn.setRequestMethod("GET");
        } catch (Exception e){
            System.out.print(e);
        }

    }
}