package application.main;

import java.io.*;
import java.util.ArrayList;

public class HandleCache {

    private ArrayList<FixerResponse> cachedList = new ArrayList<>();

    public ArrayList<FixerResponse> getCachedList() {
        return cachedList;
    }

    public void setCachedList(ArrayList<FixerResponse> cachedList) {
        this.cachedList = cachedList;
    }
    public FixerResponse findInArray(String base){
        for (FixerResponse item : cachedList){
            if (item.getBase().equals(base)){
                return item;
            }
        }
        return null;
    }
    public void writeObj(FixerResponse obj) throws FileNotFoundException , IOException{
        ObjectOutputStream oos= new ObjectOutputStream(new FileOutputStream("recent.cache",true));
        oos.writeObject(obj);
        oos.close();

    }
    public FixerResponse readObject(String base)throws FileNotFoundException , IOException,ClassNotFoundException{
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("recent.cache"));
        FixerResponse obj = (FixerResponse) ois.readObject();
        ois.close();
        if(obj.getBase().equals(base)){
            return obj;
        }
        return null;
    }
    public static void main(String[] args){
        HandleCache cache = new HandleCache();
        try {
            FixerResponse response = new FixerResponse();
            response.setBase("USD");
            FixerResponse response2 = new FixerResponse();
            response2.setBase("PKR");
            cache.writeObj(response);
            cache.writeObj(response2);
            System.out.println(cache.readObject("PKR").getBase());
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }
}
