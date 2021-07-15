package com.company;

import java.awt.*;
import java.util.*;

/**
 * Created by davidnagar on 3/31/15.
 */
public class ALGraph {
    private HashMap<String, GNode> vertices;
    private boolean directed;
    private int time;
    private int index;
    private Stack<GEdge> edgeStack;
    private HashSet<GEdge> edges;

    public ALGraph(HashMap<String, GNode> vertices, boolean directed) {
        this.vertices = vertices;
        this.directed = directed;
        edgeStack = new Stack<GEdge>();
        edges = new HashSet<GEdge>();
    }

    public ALGraph(boolean directed) {
        vertices = new HashMap<String, GNode>();
        this.directed = directed;
        edgeStack = new Stack<GEdge>();
        edges = new HashSet<GEdge>();
    }

    public HashMap<String, GNode> getVertices() {
        return vertices;
    }

    public void addNode(String name) {
        if (!vertices.containsKey(name)) {
            vertices.put(name,new GNode(name));
        }
    }

    public void addEdge(String from, String to) {
        addEdge(from, to, 1, 1);
    }

    public void addEdge(String from, String to, int weight, int flow) {
        if (directed) {
            GEdge edge = new GEdge(vertices.get(from), vertices.get(to),weight,flow);
            vertices.get(from).addToAdj(vertices.get(to));
            vertices.get(from).addToAdjEdges(edge);
            edges.add(edge);
        }else if (from != to){
            GEdge edge = new GEdge(vertices.get(from), vertices.get(to),weight,flow);
            vertices.get(from).addToAdjEdges(edge);
            vertices.get(from).addToAdj(vertices.get(to));
            edges.add(edge);
            GEdge edge1 = new GEdge(vertices.get(to), vertices.get(from),weight,flow);
            vertices.get(to).addToAdjEdges(edge1);
            vertices.get(to).addToAdj(vertices.get(from));
            edges.add(edge1);
        }
        //System.out.println("Adj for "+ vertices.get(from) + ": " + vertices.get(from).getAdj());
    }


    public void BFS(String string){
        GNode s = vertices.get(string);
        ArrayList<GNode> vertices1 = new ArrayList<GNode>();
        vertices1.addAll(vertices.values());
        vertices1.remove(s);
        for(GNode a: vertices1){
            a.setColor(Color.WHITE);
            a.setDistance(0);
            a.setPrev(null);
        }
        s.setColor(Color.GRAY);
        s.setDistance(0);
        s.setPrev(null);
        LinkedList<GNode> gverticesQueue = new LinkedList<GNode>();
        gverticesQueue.add(s);
        int i = 0;
        while (!gverticesQueue.isEmpty()){
            GNode currentNode = gverticesQueue.pollFirst();
            for (GNode a: currentNode.getAdj()){
                if (a.getColor()==Color.WHITE){
                    a.setColor(Color.GRAY);
                    a.setDistance(currentNode.getDistance()+1);
                    a.setPrev(currentNode);
                    gverticesQueue.add(a);
                }
            }
            currentNode.setColor(Color.BLACK);
            System.out.println(currentNode.toDFSString());
        }
    }

    public void DFS(){
        ArrayList<GNode> vertices1 = new ArrayList<GNode>();
        vertices1.addAll(vertices.values());
        for(GNode a:vertices1){
            a.setColor(Color.WHITE);
            a.setLow(0);
            a.setStart(0);
            a.setFinish(0);
            a.isRoot = false;
            a.setPrev(null);
        }
        time = 0;
        index = 0;
        edgeStack.clear();
        for (GNode a : vertices1){
            if (a.getColor()==Color.WHITE){
                a.isRoot = true;
                DFSVisit(a);
            }
            System.out.println("DFS: " + a.toBFSString());
        }
        lows();
    }

    private void DFSVisit(GNode a) {
        time++;
        index++;
        int children = 0;
        a.setStart(time);
        a.setNum(index);
        a.setLow(index);
        a.setColor(Color.GRAY);
        HashSet<GNode> adjList = a.getAdj();
        String classification = "";
        for (GNode v : adjList){
            if (directed) {
                classification =
                        (v.getStart() == 0)        ? "tree"    :
                                (v.getFinish() == 0)        ? "back"    :
                                        (v.getFinish() > a.getStart())  ? "forward" :  "cross"   ;
                System.out.println(a + "->" + v + " is a " + classification + " edge");
            }
            if (v.getColor()==Color.WHITE) {
                v.setPrev(a);
                edgeStack.push(new GEdge(a, v)); // get the actual edge please
//                v.setNum(index);
//                v.setLow(index);
                children++;
                DFSVisit(v);
                a.setLow(Math.min(a.getLow(),v.getLow()));
                //System.out.println(a.toString()+" "+v.toString()+ " "+children);
                edgeComponentClassification(a,v, children);
            }else if (v.getFinish() == 0 && ( !a.getPrev().equals(v)||directed)){
                edgeStack.push(new GEdge(a, v));
                a.setLow(Math.min(a.getLow(), v.getNum()));
            }

        }
        a.setColor(Color.BLACK);
        time++;
        a.setFinish(time);
    }

    public void lows(){
        for (GNode v: vertices.values()) {
            System.out.println(v + "'s low is " + v.getLow());
        }
    }

    private void edgeComponentClassification(GNode a, GNode v, int children) {
        if (!directed && (!a.isRoot && a.getNum() <= v.getLow()) || (a.isRoot && children >= 2)) {
            System.out.println("Vertex " + a + " is an articulation point");
            Stack<GEdge> tempStack = new Stack<GEdge>();
            while (edgeStack.size() > 1 &&!edgeStack.peek().getOrigin().equals(a)&&
                    !edgeStack.peek().getDestination().equals(v)){
                tempStack.push(edgeStack.pop());
            }
            tempStack.push(edgeStack.pop());
            if (tempStack.size() > 1){
                System.out.println(tempStack+ " is a biconnected component");
            }else if (tempStack.size() == 1){
                System.out.println(tempStack + " is a bridge");
            }
        }
    }

    public void Dijkstra(String origin){
        if (vertices.containsKey(origin)){
            GNode vertex = vertices.get(origin);
            for (GNode v : vertices.values()){
                v.setDistance(10000000);
            }
            vertex.setDistance(0);
            PriorityQueue<GNode> minQueue = new PriorityQueue<GNode>(vertices.values());
            while (!minQueue.isEmpty()){
                GNode s = minQueue.remove();
                for (GEdge edge : s.getAdjEdges()){
                    relaxEdges(edge,minQueue);
                }
                System.out.println(s.toDFSString());
            }
        }
    }

    private void relaxEdges(GEdge edge, PriorityQueue<GNode> minQueue) {
        if (edge.getDestination().getDistance() > (edge.getOrigin().getDistance()+edge.getWeight())){
            edge.getDestination().setDistance(edge.getOrigin().getDistance()+edge.getWeight());
            minQueue.remove(edge.getDestination());
            minQueue.add(edge.getDestination());
        }
    }

    public void maxFlow(String start, String sink){
        int maxFlow = 0;
        if (vertices.containsKey(start)&&vertices.containsKey(sink)){
            ALGraph residualNetwork = new ALGraph(true);
            for (GNode v : vertices.values()){
                residualNetwork.getVertices().put(v.getName(), v);
                //System.out.println(residualNetwork.getVertices().get(v.getName()));
            }
            while (residualNetwork.hasAugumentingPath(residualNetwork.getVertices().get(start),
                    residualNetwork.getVertices().get(sink))){
                GEdge reverseEdge, edge = residualNetwork.getVertices().get(sink).getPrevEdge();
                int augementation = 100000;
                while (edge != null){
                    augementation = Math.min(augementation,edge.getCapacity());
                    edge = edge.getOrigin().getPrevEdge();
                }
                edge = residualNetwork.getVertices().get(sink).getPrevEdge();
                //System.out.println();
                while (edge != null){
                   // System.out.println("start: " + edge);
                    //System.out.println("start: " + edge!=null);
                    if (this.edges.contains(edge)){
                        edge.setCapacity(edge.getCapacity()-augementation);
                    }else {
                        reverseEdge = edge.getReverseEdge();
                        reverseEdge.setCapacity(reverseEdge.getCapacity()+augementation);
                    }
                    edge = edge.getOrigin().getPrevEdge();
                    //System.out.println("after: " + edge!=null);
                }
                maxFlow += augementation;
            }
            System.out.println("Max flow is " + maxFlow);
        }
    }

    private boolean hasAugumentingPath(GNode start, GNode sink) {
       clearPrevEdges();
        LinkedList<GNode> queue = new LinkedList<GNode>();
        queue.add(start);
        while (!queue.isEmpty()){
            GNode v = queue.remove();
            for (GEdge edge : v.getAdjEdges()){
                if (edge.getDestination().getPrevEdge() == null && edge.getCapacity() != 0){
                    edge.getDestination().setPrevEdge(edge);
                    queue.add(edge.getDestination());
                }
            }
        }
        return sink.getPrevEdge() != null;
    }

    private void clearPrevEdges(){
        for (GNode vertex : vertices.values()) {
            vertex.setPrevEdge(null);
        }
    }


    public HashSet<GEdge> getEdges(){
        HashSet<GEdge> edges = new HashSet<GEdge>();
        ArrayList<GNode> vertices1 = new ArrayList<GNode>();
        vertices1.addAll(vertices.values());
        for (GNode a: vertices1){
            edges.addAll(a.getAdjEdges());
        }
        return edges;
    }
}
