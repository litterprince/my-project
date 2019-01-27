package algorithm.graph.core.impl;

import algorithm.graph.core.AbstractComputer;
import algorithm.graph.domain.IGraph;
import algorithm.graph.domain.IResult;
import algorithm.graph.domain.IVertex;
import algorithm.graph.domain.result.Result;

import java.util.*;

public class TopologyComputer extends AbstractComputer<IResult> {
    private int[] inDegree;
    private char[] result;
    public TopologyComputer(IGraph graph) {
        super(graph);
        inDegree = new int[graph.getVertexNum()];
        result = new char[graph.getVertexNum()];
    }

    @Override
    public IResult compute(IVertex start){
        topologySort(start);
        return new Result();
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

    private void topologySort(IVertex start){
        // check
        if(start == null){
            System.out.println("start vertex can not be null!");
            return;
        }

        // init zero-queue
        Queue<IVertex> zeroQueue = new LinkedList<>();
        for (int i = 0; i < graph.getVertexNum(); i++) {
            IVertex vertex = graph.getVertex(i);
            inDegree[i] = vertex.getInDegree();
            if(inDegree[i] == 0){
                zeroQueue.add(vertex);
            }
        }

        // check
        if(!zeroQueue.contains(start)){
            System.out.println("it's a wrong start vertex because it's in-degree is not zero!");
            return;
        }

        int i = 0;
        zeroQueue.remove(start);
        IVertex vertex = graph.getVertex(start.getValue()); // don't use clone becase of shallow copy
        while (zeroQueue.size() > 0){
            if(vertex == null) {
                vertex = zeroQueue.poll();
            }
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

            vertex = null;
        }

        // display reslut
        for (int j = 0; j < i; j++) {
            System.out.print(result[j] + " ");
        }
    }
}
