/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bst_b15;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author 
 */
public class BSTree {

    //Atributes
    BSTNode root;
    String result;
    ArrayList<BSTNode> path;
    ArrayList<NodeData> treeData;

    /*===== for drawing ====*/
    int screenWidth;
    int yMin;

    /*===== Constructors ====*/
    //default constructor
    public BSTree() {
        root = null;
        this.screenWidth = 0;
        this.yMin = 0;

        result = "";
        path = new ArrayList<>();
        treeData = new ArrayList<>();
    }

    /**
     *
     * Constructor for drawing screen
     */
    public BSTree(int screenWidth, int yMin) {
        root = null;
        this.screenWidth = screenWidth;
        this.yMin = yMin;

        result = "";
        path = new ArrayList<>();
        treeData = new ArrayList<>();

    }

    /*===== Get, set methods ====*/
    public BSTNode getRoot() {
        return this.root;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    //get Traversal Result widthout ", " in the end
    public String getTraversalResult() {
        return this.result.substring(0, result.length() - 1);
    }

    //get Find Node Result widthout " -> " in the end
    public String getFindNodeResult() {
        return this.result.substring(0, result.length() - 4) + ".";
    }

    /**
     * add Node into BST tree
     */
    public void addNode(int data) {
        if (root == null) {
            root = new BSTNode(data, yMin, screenWidth);
        } else {
            boolean isAdded = false;
            BSTNode node = root;
            while (!isAdded) {
                if (data < node.getData()) {
                    if (node.hasLeftChild()) {
                        node = node.getLeftChild();
                    } else {
                        node.setLeftChild(new BSTNode(data));
                        isAdded = true;
                    }
                } else if (data > node.getData()) {
                    if (node.hasRightChild()) {
                        node = node.getRightChild();
                    } else {
                        node.setRightChild(new BSTNode(data));
                        isAdded = true;
                    }
                } else {
//                    node.setCount(node.getCount() + 1);
                    isAdded = true;
                }
            }
        }
    }

    /**
     * add Node with count of the node (for delete note, ...) into BST tree
     */
    public void addNode(int data, int count) {
        if (root == null) {
            root = new BSTNode(data, yMin, screenWidth);
        } else {
            boolean isAdded = false;
            BSTNode node = root;
            while (!isAdded) {
                if (data < node.getData()) {
                    if (node.hasLeftChild()) {
                        node = node.getLeftChild();
                    } else {
                        node.setLeftChild(new BSTNode(data, count));
                        isAdded = true;
                    }
                } else if (data > node.getData()) {
                    if (node.hasRightChild()) {
                        node = node.getRightChild();
                    } else {
                        node.setRightChild(new BSTNode(data, count));
                        isAdded = true;
                    }
                } else {
//                    node.setCount(node.getCount() + 1);
                    isAdded = true;
                }
            }
        }
    }

    /**
     * pre-order traversal BST tree
     */
    public void preOrder() {
        result = "";
        preOrder(root);
    }

    /**
     * handle pre-order traversal BST tree (call back)
     */
    private void preOrder(BSTNode node) {
        if (node == null) {
            return;
        }
        for (int i = 0; i < node.getCount(); i++) {
            result += node.getData() + ",";

        }
        preOrder(node.getLeftChild());
        preOrder(node.getRightChild());
    }

    /**
     * in-order traversal BST tree
     */
    public void inOrder() {
        result = "";
        inOrder(root);
    }

    /**
     * handle in-order traversal BST tree (call back)
     */
    private void inOrder(BSTNode node) {
        if (node == null) {
            return;
        }
        inOrder(node.getLeftChild());
        for (int i = 0; i < node.getCount(); i++) {
            result += node.getData() + ",";

        }
        inOrder(node.getRightChild());
    }

    /**
     * post-order traversal BST tree
     */
    public void postOrder() {
        result = "";
        postOrder(root);
    }

    /**
     * handle post-order traversal BST tree (call back)
     */
    private void postOrder(BSTNode node) {
        if (node == null) {
            return;
        }
        postOrder(node.getLeftChild());
        postOrder(node.getRightChild());
        for (int i = 0; i < node.getCount(); i++) {
            result += node.getData() + ",";

        }
    }

    /**
     * // get path list
     */
    public ArrayList<BSTNode> getPath() {
        return path;
    }

    /**
     * finding a node by value
     */
    public BSTNode findNode(int data) {
        result = "";

        path.clear();

        BSTNode node = root;
        while (node != null) {
            result = result += node.getData() + " -> ";
            path.add(node);
            if (node.getData() == data) {
                return node;
            } else {
                if (data < node.getData()) {
                    node = node.getLeftChild();
                } else {
                    node = node.getRightChild();
                }
            }
        }
        path.clear();
        return null;
    }

    /**
     * removing a node by value
     */
    public boolean removeNode(int data) {
        BSTNode node = findNode(data);
        return removeNode(node);
    }

    /**
     *
     * Handle remove a node
     */
    public boolean removeNode(BSTNode node) {
        if (node == null) {
            return false;
        }

        //delete count if having many same value
        node.setCount(node.getCount() - 1);

        //delete a node not care about many same value
        //node.setCount(0);
        if (node.getCount() <= 0) {

            if (node.isRoot() && !node.hasChild()) {
                root = null;
                return true;
            }

            if (node.isLeaf()) {
                node.getParent().removeLeafChild(node);
                return true;
            } else {
                BSTNode incomer = null;
                if (node.hasLeftChild()) {
                    incomer = node.getLeftChild().findMaxNode();
                } else {
                    incomer = node.getRightChild().findMinNode();
                }
                node.setData(incomer.getData());
                node.setCount(incomer.getCount());

                incomer.setCount(1);
                return removeNode(incomer);
            }
        } else {
            return true;
        }
    }

    /**
     * clear the BST tree
     */
    public void clearTree() {
        clearTree(this.root);
        this.root = null;
    }

    /**
     * handle clear the BST tree (call back)
     */
    private void clearTree(BSTNode node) {
        if (node == null) {
            return;
        }
        if (node.isLeaf()) {
            node.getParent().removeLeafChild(node);
        } else {
            clearTree(node.getLeftChild());
            clearTree(node.getRightChild());
        }
    }

    /**
     * handle BFS
     */
    public void BFS() {
        this.result = "";
        Queue<BSTNode> q = new LinkedList<>();
        q.add(root);
        BSTNode node;
        while (!q.isEmpty()) {
            node = q.poll();
            if (node != null) {
                for (int i = 0; i < node.getCount(); i++) {
                    System.out.print(node.getData() + ", ");
                    result += node.getData() + ", ";
                }
                q.add(node.getLeftChild());
                q.add(node.getRightChild());
            }
        }
    }

    /**
     * handle DFS
     */
    public void DFS() {
        this.result = "";
        Stack<BSTNode> q = new Stack<>();
        q.add(root);

        BSTNode node;
        while (!q.isEmpty()) {
            node = q.pop();
            if (node != null) {
                for (int i = 0; i < node.getCount(); i++) {
                    System.out.print(node.getData() + ", ");
                    result += node.getData() + ", ";
                }
                q.add(node.getRightChild());
                q.add(node.getLeftChild());
            }
        }
    }

    public int countHeight(int value, int count) {
        BSTNode node = findNode(value);

//        System.out.println(node.getLeftChild().getData());
        System.out.println(node.getData());
        if (node == null || node.isLeaf()) {
            return count;
        } else {
            if (node.hasLeftChild()) {
                return count += countHeight(node.getLeftChild().getData(), 1);
            }
            if (node.hasRightChild()) {
                return count += countHeight(node.getRightChild().getData(), 1);
            }
            return count;
        }

    }

    private void BST2Array(BSTNode node) {
        if (node == null) {
            return;
        }
        BST2Array(node.getLeftChild());
        treeData.add(new NodeData(node.getData(), node.getCount()));
        BST2Array(node.getRightChild());
    }

    /**
     * Balancing the BST tree
     */
    public void balancing() {
        treeData.clear();
        BST2Array(this.root);

        this.clearTree();
        Queue<BSTRange> q = new LinkedList<>();
        q.add(new BSTRange(0, treeData.size() - 1));
        BSTRange range;
        NodeData nodeData;
        int middleIndex, leftIndex, rightIndex;
        while (!q.isEmpty()) {
            range = q.poll();
            leftIndex = range.getLeftIndex();
            rightIndex = range.getRightIndex();

            if (leftIndex <= rightIndex) {
                middleIndex = (leftIndex + rightIndex) / 2;
                nodeData = treeData.get(middleIndex);
                this.addNode(nodeData.getData(), nodeData.getCount());

                q.add(new BSTRange(leftIndex, middleIndex - 1));
                q.add(new BSTRange(middleIndex + 1, rightIndex));

            }
        }
    }

    public boolean checkFullBinary(BSTNode root) {
        if (root != null) {
            if (root.getRightChild()== null && root.getLeftChild()== null) {
                result = "Yes";
                return true;
            }
            if ((root.getRightChild()!= null && root.getLeftChild()!= null)) {
                return checkFullBinary(root.getLeftChild()) && checkFullBinary(root.getRightChild());
            }
        }
        result = "No";
        return false;
    }

}
