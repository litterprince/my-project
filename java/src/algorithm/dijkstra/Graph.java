package algorithm.dijkstra;

import com.sun.xml.internal.bind.v2.TODO;

public class Graph {
    private VNode[] vNodes; // VNode array list
    private int edgeNum; // the number of edge

    public Graph(){
    }

    public Graph buildGraph(String content){
        // init variables
        int vlen=0, elen=0;

        // init VNode
        vNodes = new VNode[vlen];
        for(int i=0;i<vNodes.length;i++){
            vNodes[i] = new VNode();
            //TODO: do sth
        }

        // init ENode
        for(int i=0;i<elen;i++){
            ENode eNode1 = new ENode();

            ENode eNode2 = new ENode();

            //TODO: linkLast

        }

        return this;
    }

    /**
     * put node to the end of list
     * @param list
     * @param node
     */
    private void linkLast(ENode list, ENode node){
        //TODO: do sth
    }

    /**
     * dijkstra: compute the shortest distance between two vertex
     * @param vs start vertex
     * @param prev previous vertex array list
     * @param dist distance array list
     */
    public void dijkstra(int vs, int[] prev, int[] dist){

    }
}
