package application.main;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;

public class CurrencyConvert {
    private Controller controller;

    public CurrencyConvert(Controller controller){
        this.controller = controller;
    }



    private static final String API_PROVIDER = "http://api.fixer.io";
    private HandleCache handleCache = new HandleCache();
    public  double convert(String fromCurrencyCode, String toCurrencyCode) {

        FixerResponse cached = handleCache.findInArray(fromCurrencyCode);
        if (cached != null) {
            String rate = cached.getRates().get(toCurrencyCode);
            double conversionRate = Double.valueOf((rate != null) ? rate : "0.0");
            System.out.println("Got from cache !!!!");
            return conversionRate;

        }else {
            FixerResponse response = getResponse(API_PROVIDER + "/latest?base=" + fromCurrencyCode);
            if (response != null) {
                handleCache.getCachedList().add(response);
                String rate = response.getRates().get(toCurrencyCode);
                double conversionRate = Double.valueOf((rate != null) ? rate : "0.0");
                return conversionRate;
            }
        }


        return 0.0;
    }
    public void refresh(String fromCurrencyCode){
        FixerResponse response = getResponse(API_PROVIDER + "/latest?base=" + fromCurrencyCode);
        if (response != null) {
            FixerResponse cached = handleCache.findInArray(fromCurrencyCode);
            if (cached!=null){
             cached.setBase(response.getBase());
             cached.setDate(response.getDate());
             cached.setRates(response.getRates());
            }else {
                handleCache.getCachedList().add(response);
            }
        }
    }

    // Method to get the response from API
    private  FixerResponse getResponse(String strUrl) {

        FixerResponse response = null;

        Gson gson = new Gson();
        StringBuffer sb = new StringBuffer();

        if(strUrl == null || strUrl.isEmpty()) {

            System.out.println("Application Error");
            return null;
        }

        URL url;
        try {
            url = new URL(strUrl);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            InputStream stream = connection.getInputStream();

            int data = stream.read();

            while (data != -1) {

                sb.append((char) data);

                data = stream.read();
            }

            stream.close();

            response = gson.fromJson(sb.toString(), FixerResponse.class);

        } catch (MalformedURLException e) {

            System.out.println(e.getMessage());
            e.printStackTrace();

        }catch (UnknownHostException ex){
            controller.getError().setText("unable to fetch data check your internet connection.");

        } catch (IOException e) {

            System.out.println("Unavailable data for this country's currency");
            controller.getError().setText("Unavailable data for this country's currency");

//            e.printStackTrace();
        }

        return response;
    }

}
