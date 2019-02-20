package design.observer;

public class Client {
    public static void main(String[] args){
        Observer user1 = new SubscribeUser("jeff");
        Observer user2 = new SubscribeUser("tom");
        Observer user3 = new SubscribeUser("james");

        WebChatServer server = new WebChatServer();
        server.registerObserver(user1);
        server.registerObserver(user2);
        server.registerObserver(user3);

        server.publishNews("java is the best program language in the world");
    }
}
