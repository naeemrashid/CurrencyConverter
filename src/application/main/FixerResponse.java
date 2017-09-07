package application.main;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

public class FixerResponse implements Serializable{
    private String base;
    private String date;

    private Map<String, String> rates = new TreeMap<String, String>();

    public Map<String, String> getRates() {
        return rates;
    }

    public void setRates(Map<String, String> rates) {
        this.rates = rates;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
