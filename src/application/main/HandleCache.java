package application.main;

import java.io.*;
import java.util.ArrayList;

public class HandleCache {

    private ArrayList<FixerResponse> cachedList ;

    public HandleCache(){
        cachedList = new ArrayList<>();

    }
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
        File file = new File("recent.cache");
        if (!file.exists()){
            file.createNewFile();
        }
        ObjectOutputStream oos= new ObjectOutputStream(new FileOutputStream(file));
        oos.writeObject(obj);
        oos.close();

    }
    public FixerResponse readObject(String base)throws FileNotFoundException , IOException,ClassNotFoundException{
        File file = new File("recent.cache");
        if (!file.exists()){
            file.createNewFile();
        }
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
        FixerResponse obj = (FixerResponse) ois.readObject();
        ois.close();
        if(obj.getBase().equals(base)){
            return obj;
        }
        return null;
    }

}
