package algorithm.graph.core.impl;

import algorithm.graph.core.AbstractComputer;
import algorithm.graph.domain.IGraph;
import algorithm.graph.domain.IVertex;

import java.util.*;

public class TopologyComputer extends AbstractComputer {
    private int[] inDegree;
    private char[] result;
    public TopologyComputer(IGraph graph) {
        super(graph);
        inDegree = new int[graph.getVertexNum()];
        result = new char[graph.getVertexNum()];
    }

    @Override
    public void compute(IVertex start){
        topologySort();
    }

    private void topologySort(){
        Queue<IVertex> zeroQueue = new LinkedList<>();
        // init zero-queue
        for (int i = 0; i < graph.getVertexNum(); i++) {
            IVertex vertex = graph.getVertex(i);
            inDegree[i] = vertex.getInDegree();
            if(inDegree[i] == 0){
                zeroQueue.add(vertex);
            }
        }

        int i = 0;
        while (zeroQueue.size() > 0){
            IVertex vertex = zeroQueue.poll();
            result[i++] = vertex.getValue();

            for(IVertex v : graph.getReachable(vertex)){
                // decrement in-degree of current vertex
                int position = graph.getPosition(v);
                inDegree[position] = inDegree[position] - 1;
                // if in-degree equals 0 then add to zeroQueue
                if(inDegree[position] == 0){
                    zeroQueue.add(v);
                }
            }
        }

        // display reslut
        for (int j = 0; j < i; j++) {
            System.out.print(result[j] + " ");
        }
    }

    public void topologySort(int startValue){
        /*Queue<Node> zeroQueue = new LinkedList<>();
        List<Node> result = new ArrayList<> ();
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
        System.out.println();*/
    }
}
