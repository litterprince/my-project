package design.observer;

public class SubscribeUser implements Observer {
    private String name;

    public SubscribeUser(String name){
        this.name = name;
    }

    @Override
    public void update(Object o) {
        System.out.println(name+" receive news: "+(String)o);
    }
}
