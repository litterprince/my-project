package algorithm.graph.topology;

import java.util.*;

public class TopologyG {
    private Map<Integer,Node> nodes;
    private Set<Edge> edges;
    private List<Node> result;
    private Queue<Node> zeroQueue;

    public TopologyG(){
        this.nodes = new HashMap<>();
        this.edges = new HashSet<>();
        this.result = new ArrayList<>();
        this.zeroQueue = new LinkedList<>();
    }

    public TopologyG(Map<Integer,Node> nodes, Set<Edge> edges) {
        this.nodes = nodes;
        this.edges = edges;
        this.result = new ArrayList<>();
        this.zeroQueue = new LinkedList<>();
    }

    public TopologyG buildGraph(String content){
        String[] lines = content.split("\n");
        for(String s : lines){
            String[] line = s.split(",");
            Node fromNode = addNode(new Node(Integer.valueOf(line[0])));
            Node toNode = addNode(new Node(Integer.valueOf(line[1])));
            addEdge(Integer.parseInt(line[2]), fromNode, toNode);
        }
        return this;
    }

    public Node addNode(Node node){
        if(!nodes.containsKey(node.getValue())) {
            nodes.put(node.getValue(), node);
        }
        return nodes.get(node.getValue());
    }

    public void addEdge(int weight, Node from, Node to){
        Edge edge = new Edge(weight, from, to);
        if(!from.equals(to) && !edges.contains(edge)){
            from.incrementOut();
            from.addNext(to);
            from.addEdge(edge);
            to.incrementIn();
            edges.add(edge);
        }
    }

    /**
     * 1.将图中的所有节点全部记录到HashMap，入度为零的节点添加到zeroInQueue
     * 2.建立一个集合用来存放结果
     * 3.从zeroInQueue中弹出一个，遍历她的子节点
     * 4.将其子节点入度减一
     * 5.如果这个子节点的入度为零了，就将其放入到zeroInQueue中
     */
    public void topologySort(){
        // first, init the zero queue
        for(Map.Entry<Integer, Node> entry: nodes.entrySet()){
            Node node = entry.getValue();
            if(node.getValue() == 0){
                zeroQueue.add(node);
            }
        }

        // 1.choose one node from zero queue and visit their nexts
        // 2.repeat no.1 step util the queue is empty
        while (zeroQueue.size() > 0){
            Node start = zeroQueue.poll();
            result.add(start);
            for(Node node : start.getNexts()){
                // visit it and decrement its in-degree
                node.decrementIn();
                // if it should be added
                if(node.getIn() < 1){
                    zeroQueue.add(node);
                    continue;
                }
                result.add(node);
            }
        }

        // finally,display the visit route
        for(Node node : result){
            System.out.print(node.getValue()+ " ");
        }
        System.out.println();
    }

    public void topologySort(int startValue){
        // first, init the zero queue
        Node first = null;
        for(Map.Entry<Integer, Node> entry: nodes.entrySet()){
            Node node = entry.getValue();
            if(node.getValue() == startValue){
                first = node;
                continue;
            }
            if(node.getIn() == 0){
                zeroQueue.add(node);
            }
        }

        // 1.choose one node from zero queue and visit their nexts
        // 2.repeat no.1 step util the queue is empty
        while (zeroQueue.size() > 0){
            Node start;
            if(first != null){
                start = first;
                first = null;
            }else {
                start = zeroQueue.poll();
            }
            result.add(start);

            for(Node node : start.getNexts()){
                // visit it and decrement its in-degree
                node.decrementIn();
                // if it should be added
                if(node.getIn() < 1){
                    zeroQueue.add(node);
                }
            }
        }

        // finally,display the visit route
        for(Node node : result){
            System.out.print(node.getValue()+ " ");
        }
        System.out.println();
    }
}
