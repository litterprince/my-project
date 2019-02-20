package design.adapter;

public class Adapter implements Target {
    private Adaptee adaptee;

    Adapter(Adaptee adaptee){
        this.adaptee = adaptee;
    }

    @Override
    public void operation(){
        System.out.print("adapter call adaptee's operation: ");
        adaptee.operation();
    }
}
