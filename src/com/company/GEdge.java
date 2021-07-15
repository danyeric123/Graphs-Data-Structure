package com.company;

/**
 * Created by davidnagar on 3/31/15.
 */
public class GEdge {
    private GNode origin;
    private GNode destination;
    private int weight;
    private int flow;
    private int capacity;

    public GEdge(GNode origin, GNode destination) {
        this.origin = origin;
        this.destination = destination;
        this.flow = -1;
        this.weight = -1;
    }

    public GEdge(GNode origin, GNode destination, int weight, int flow) {
        this.origin = origin;
        this.destination = destination;
        this.weight = weight;
        this.flow = flow;
        capacity = flow;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public GNode getOrigin() {
        return origin;
    }

    public void setOrigin(GNode origin) {
        this.origin = origin;
    }

    public GNode getDestination() {
        return destination;
    }

    public void setDestination(GNode destination) {
        this.destination = destination;
    }

    public String classification(){
        String classification =
                (destination.getStart()<origin.getStart())        ? "tree"    :
                        //v.d ≤ u.d < u.f ≤ v.f
                        (destination.getStart()<=origin.getStart())? "back":
                                (origin.getFinish() < destination.getStart())  ? "cross" :
				  /*(adj.ft < vertex.dt)*/  "forward"   ;
        return classification;
    }

    public String toString(){
        return origin + " -> " + destination;
    }

    public int hashCode(){
        return origin.getName().hashCode()+destination.getName().hashCode();
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GEdge)) return false;
        GEdge other = (GEdge) o;
        return this.origin.getName().equals(other.origin.getName()) && this.destination.getName()
                .equals(other.destination.getName());
    }

    public int getFlow() {
        return flow;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public GEdge getReverseEdge() {
        for (GEdge edge1 : this.getDestination().getAdjEdges()){
            if (this.getOrigin().equals(edge1.getDestination())){
                return edge1;
            }
        }
        return null;
    }
}
