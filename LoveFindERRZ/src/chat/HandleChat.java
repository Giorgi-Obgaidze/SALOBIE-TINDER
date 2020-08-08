package chat;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

public class HandleChat {
    Map<String, Map<String, LinkedBlockingQueue<String>>> data;
    public HandleChat(){
        data = new HashMap<>();
    }

    public synchronized boolean containsId(String id){
        return data.containsKey(id);
    }

    public synchronized void add(String id, Map<String, LinkedBlockingQueue<String>> msg){
        data.put(id, msg);
    }

    public synchronized LinkedBlockingQueue<String> get(String id, String toId){
        return data.get(id).get(toId);
    }
}
