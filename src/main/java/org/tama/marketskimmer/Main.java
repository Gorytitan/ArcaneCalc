package org.tama.marketskimmer;


import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws Exception {
        ArrayList<Order> orders = Market.getOrders("primed_fulmination");
        /*//https://api.warframe.market/v1
        try {
            URL url = new URL("https://api.warframe.market/v1/items/primed_fulmination/orders");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            //Check if connect is made
            int responseCode = conn.getResponseCode();

            //200 ok
            if (responseCode != 200)
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            else {
                StringBuilder informationString = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());

                while (scanner.hasNext()) {
                    informationString.append(scanner.nextLine());
                }
                scanner.close();
                conn.disconnect();

                //turn the string into a json
                JSONParser parse = new JSONParser();
                JSONObject dataObject = (JSONObject) parse.parse(String.valueOf(informationString));
                JSONArray arr = (JSONArray) ((JSONObject) dataObject.get("payload")).get("orders");

                //sorting by price for easier access
                Collections.sort(arr, new Comparator<JSONObject>() {
                    @Override
                    public int compare(JSONObject o1, JSONObject o2) {
                        return ((Long) o1.get("platinum")).intValue() - ((Long) o2.get("platinum")).intValue();
                    }
                });
                //delete old posts (might not be needed if i just do online ones
                DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
                DateTime now = new DateTime();
                DateTime old = now.minusDays(30);
                for (int i = arr.size() - 1; i >= 0; i--) {
                    DateTime dt = formatter.parseDateTime(((JSONObject) arr.get(i)).get("last_update").toString());
                    if (dt.isBefore(old)) {
                        arr.remove(i);
                    }
                }

                //seperate into 2 arrays, one for unranked one for maxed
                JSONArray unranked = new JSONArray();
                JSONArray maxed = new JSONArray();
                for (Object order :
                        arr) {
                    JSONObject orderjson = (JSONObject) order;
                    switch (((Long) orderjson.get("mod_rank")).intValue()) {
                        case 0:
                            if (orderjson.get("order_type").toString().equals("buy")
                                    && ((((JSONObject) orderjson.get("user")).get("status").toString().equals("online"))
                                    || ((JSONObject) orderjson.get("user")).get("status").toString().equals("ingame"))) {
                                unranked.add(orderjson);
                            }
                            break;
                        case 10:
                            if (orderjson.get("order_type").toString().equals("sell")
                                    && ((((JSONObject) orderjson.get("user")).get("status").toString().equals("online"))
                                    || ((JSONObject) orderjson.get("user")).get("status").toString().equals("ingame"))) {
                                maxed.add(orderjson);
                            }
                            break;
                    }
                }
                System.out.println(maxed);
            }

        } catch (Exception e) {
            System.out.print(e);
        }*/

    }
}