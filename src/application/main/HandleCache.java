package application.main;

import java.io.*;

public class HandleCache {
    public void writeObj(FixerResponse obj) throws FileNotFoundException , IOException{
        ObjectOutputStream oos= new ObjectOutputStream(new FileOutputStream("recent.cache"));
        oos.writeObject(obj);
        oos.close();

    }
    public FixerResponse readObject(String base)throws FileNotFoundException , IOException,ClassNotFoundException{
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("recent.cache"));
        FixerResponse obj = (FixerResponse) ois.readObject();
        if(obj.getBase().equals(base)){
            return obj;
        }
        return null;
    }
}
