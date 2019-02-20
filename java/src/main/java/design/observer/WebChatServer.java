package design.observer;

import java.util.ArrayList;
import java.util.List;

public class WebChatServer implements Subject{
    private List<Observer> list = new ArrayList<>();

    @Override
    public void registerObserver(Observer o) {
        if(!list.contains(o))
            list.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        list.remove(o);
    }

    @Override
    public void notifyObservers(Object object) {
        for(Observer o : list){
            o.update(object);
        }
    }

    public void publishNews(String news){
        System.out.println("news updates:");
        notifyObservers(news);
    }
}
