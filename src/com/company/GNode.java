package com.company;

import java.awt.*;
import java.util.HashSet;

/**
 * Created by davidnagar on 3/31/15.
 */
public class GNode implements Comparable<GNode>{
    private String name;
    private HashSet<GEdge> adjEdges;
    private HashSet<GNode> adj;
    private int distance;  // For path-finding algorithms
    private GNode prev;   // For path-finding algorithms
    private Color color;  // For DFS
    boolean visited;
    private int start; // For DFS
    private int finish; // For DFS
    private int num;
    private int low;
    private GEdge prevEdge;
    boolean isRoot;
    //private int numChildren;

    public GNode(String name, HashSet<GNode> adj, HashSet<GEdge> adjEdges) {
        this.name = name;
        this.adj = adj;
        this.adjEdges = adjEdges;
        color = Color.WHITE;
        prevEdge = null;
    }

    public GNode(String name) {
        this.name = name;
        adj = new HashSet<GNode>();
        adjEdges = new HashSet<GEdge>();
        color = Color.WHITE;
        start = 0;
        finish = 0;
        prevEdge = null;
    }

    public HashSet<GNode> getAdj(){
        return adj;
    }

    public void addToAdj(GNode adjNode){
        adj.add(adjNode);
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getFinish() {
        return finish;
    }

    public void setFinish(int finish) {
        this.finish = finish;
    }

    public GNode getPrev() {
        return prev;
    }

    public void setPrev(GNode prev) {
        this.prev = prev;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getLow() {
        return low;
    }

    public void setLow(int low) {
        this.low = low;
    }

    public boolean isRoot() {
        return isRoot;
    }

    public void setRoot(boolean isRoot) {
        this.isRoot = isRoot;
    }

//    public int getNumChildren() {
//        return numChildren;
//    }
//
//    public void setNumChildren(int numChildren) {
//        this.numChildren = numChildren;
//    }

    public HashSet<GEdge> getAdjEdges() {
        return adjEdges;
    }

    public void addToAdjEdges(GEdge adjEdge) {
        adjEdges.add(adjEdge);
    }

    public GEdge getPrevEdge() {
        return prevEdge;
    }

    public void setPrevEdge(GEdge prevEdge) {
        this.prevEdge = prevEdge;
    }

    public String toDFSString(){
        return "("+name+")" + " distance: " + distance;
    }

    public String toString() {
        return "(" + name + ")";
    }

    public int compareTo(GNode a){ return this.distance - a.getDistance();}

    public String toBFSString(){
        return "("+name+")" + " start: " + start + " finish: " + finish;
    }
}
