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

    public synchronized void addQueue(String fromId, String frId){
        Map<String, LinkedBlockingQueue<String>> from = data.get(fromId);
        if(from.get(frId) == null){
            LinkedBlockingQueue<String> msgFrom = new LinkedBlockingQueue<>();
            from.put(frId, msgFrom);
        }
    }

    public synchronized LinkedBlockingQueue<String> get(String id, String toId){
        return data.get(id).get(toId);
    }
    public synchronized Map<String, LinkedBlockingQueue<String>> getFriendsMap(String id){
        return data.get(id);
    }
}