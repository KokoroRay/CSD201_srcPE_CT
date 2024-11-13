package q9_cut_edge;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Nguyen Trung Hieu - CE180575
 */
public class GEdge {
    private int src, dest, weight;

    // Constructor
    public GEdge(int src, int dest, int weight) {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public int getSrc() {
        return src;
    }

    public int getDest() {
        return dest;
    }
    
    
}
