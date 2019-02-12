package algorithm.graph.domain.result;

import algorithm.graph.core.AbstractResult;

public class ShortestResult extends AbstractResult {
    private String route;
    private int cost;

    public ShortestResult(String route, int cost) {
        super(null);
        this.route = route;
        this.cost = cost;
    }

    @Override
    public String getMsg(){
        return "the shortest route is: " + route +
                "\nit costs: "+cost;
    }
}
