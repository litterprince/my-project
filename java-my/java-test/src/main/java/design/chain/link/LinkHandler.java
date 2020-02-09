package design.chain.link;

import design.chain.RequestSource;

public class LinkHandler implements Handler {
    private Handler next;
    private String name;

    public LinkHandler(Handler next, String name) {
        this.next = next;
        this.name = name;
    }

    @Override
    public Handler getNext() {
        return next;
    }

    @Override
    public void doHandler(RequestSource requestSource) {
        Integer header = requestSource.getHeader();
        System.out.println("name=" + this.name + "；header handler= " + header);
        if (next != null) {
            next.doHandler(requestSource);
        }
    }
}
