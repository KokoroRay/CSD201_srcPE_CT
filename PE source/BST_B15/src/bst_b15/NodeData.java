/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bst_b15;

/**
 *
 * @author 
 */
public class NodeData {

    private int data;
    private int count;

    public NodeData(int data, int count) {
        this.data = data;
        this.count = count;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
