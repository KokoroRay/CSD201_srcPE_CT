/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package q2;

/**
 *
 * @author Vo Hoang Tu - CE000000
 */
public class BSTNode {
    int data;
    BSTNode left;
    BSTNode right;
    BSTNode parent;
    int level;
    int order;
    int count;

    public BSTNode(int data) {
        this.data = data;
        this.left = null;
        this.right = null;
        this.parent = null;
        this.level = 0;
        this.order = 0;
        this.count = 1;
    }
    
    public boolean isLeaf(){
        return this.left == null && this.right == null;
    }
    
    public boolean isRoot(){
        return this.parent == null;
    }
    
    public boolean hasTwoChild(){
        return this.left != null && this.right != null;
    }
    
    public boolean hasOnlyLeftChild(){
        return this.left != null && this.right == null;
    }
    
    public boolean hasOnlyRightChild(){
        return this.right != null && this.left == null;
    }
    
    public boolean hasLeftChild(){
        return this.left != null;
    }
    
    public boolean hasRightChild(){
        return this.right != null;
    }

    public void setLeft(BSTNode left) {
        this.left = left;
        if(left != null)
            this.left.setParent(this);
    }

    public void setRight(BSTNode right) {
        this.right = right;
        if(right != null)
            this.right.setParent(this);
    }
    /**
     * 
     * @param parent 
     */
    public void setParent(BSTNode parent) {
        this.parent = parent;
        this.level = this.parent.level + 1;
        if(this.data < this.parent.data)
            this.order = this.parent.order * 2 + 1;
        else
            this.order = this.parent.order * 2 + 2;
    }
    
    public BSTNode findMinNode(){
        BSTNode currentNode = this;
        while(currentNode.left != null){
            currentNode = currentNode.left;
        }
        return currentNode;
    }
    
    public BSTNode findMaxNode(){
        BSTNode currentNode = this;
        while(currentNode.right != null){
            currentNode = currentNode.right;
        }
        return currentNode;
    }
}
