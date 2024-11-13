package bst_b13;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author DELL
 */
public class BSTree {

    BSTNode root;
    String result;
    ArrayList<BSTNode> path = new ArrayList<>();
    //ArrayList<NodeData> treeData = new ArrayList<>();
    //====phuc vu ve===//
    int screenWidth;
    int yMin;
    //====phuc vu ve===//

    public BSTree() {
        root = null;
        this.screenWidth = 0;
        this.yMin = 0;
        result = "";
        path = new ArrayList<>();
        //treeData = new ArrayList<>();
    }

    public BSTree(int screenWidth, int yMin) {
        root = null;
        this.screenWidth = screenWidth;
        this.yMin = yMin;
        path = new ArrayList<>();
        //treeData = new ArrayList<>();

    }

    public BSTNode getRoot() {
        return this.root;
    }

    /*
     * add new node to the BST
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
                    //node.setCount(node.getCount() + 1);
                    isAdded = true;
                }

            }
        }
    }

    /**
     * add new node to the BST( ham can bang)
     */
//    public void addNode(int data, int count) {
//        if (root == null) {
//            root = new BSTNode(data, yMin, screenWidth);
//        } else {
//            boolean isAdded = false;
//            BSTNode node = root;
//            while (!isAdded) {
//                if (data < node.getData()) {
//                    if (node.hasLeftChild()) {
//                        node = node.getLeftChild();
//                    } else {
//                        node.setLeftChild(new BSTNode(data, count));
//                        isAdded = true;
//                    }
//                } else if (data > node.getData()) {
//                    if (node.hasRightChild()) {
//                        node = node.getRightChild();
//                    } else {
//                        node.setRightChild(new BSTNode(data, count));
//                        isAdded = true;
//                    }
//                } else {
//                    node.setCount(node.getCount() + 1);
//                    isAdded = true;
//                }
//
//            }
//        }
//    }
    /**
     * Get traversal result
     */
    public String getTraversalResult() {
        int mn = result.length();
        return result.substring(0, mn - 2);
    }

    /**
     * Get traversal result find
     */
    public String getTraversalResultFind() {
        int mn = result.length();
        return result.substring(0, mn - 3);
    }

    /**
     * pre-order traversal
     */
    public void preOrder() {
        result = "";
        preOrder(root);
    }

    /**
     * pre-order traversal
     */
    private void preOrder(BSTNode node) {
        if (node == null) {
            return;
        }
        for (int i = 0; i < node.getCount(); i++) {
            result += (node.getData() + ", ");
//            System.out.print(node.getData() + ", ");
        }
        preOrder(node.getLeftChild());
        preOrder(node.getRightChild());
    }

    /**
     * in-order traversal
     */
    public void inOrder() {
        result = "";
        inOrder(root);
    }

    /**
     * in-order traversal
     */
    private void inOrder(BSTNode node) {
        if (node == null) {
            return;
        }
        inOrder(node.getLeftChild());
        for (int i = 0; i < node.getCount(); i++) {
            result += (node.getData() + ", ");
//            System.out.print(node.getData() + ", ");
        }
        inOrder(node.getRightChild());
    }

    /**
     * post-order traversal
     */
    public void postOrder() {
        result = "";
        postOrder(root);
    }

    /**
     * post-order traversal
     */
    private void postOrder(BSTNode node) {
        if (node == null) {
            return;
        }
        postOrder(node.getLeftChild());
        postOrder(node.getRightChild());
        for (int i = 0; i < node.getCount(); i++) {
            result += (node.getData() + ", ");
//            System.out.print(node.getData() + ", ");
        }

    }

    /**
     * find node to the BST
     */
    public BSTNode findNode(int data) {
        BSTNode node = this.root;
        result = "";

        path.clear();
        while (node != null) {
            //System.out.print(node.getData() + " -> ");
            result += node.getData() + " -> ";
            path.add(node);
            if (data == node.getData()) {
                return node;
            } else if (data < node.getData()) {
                node = node.getLeftChild();
            } else {
                node = node.getRightChild();
            }
        }
        path.clear();
        return null;
    }

    /**
     * Get the path
     */
    public ArrayList<BSTNode> getPath() {
        return this.path;
    }

    public boolean removeNode(int data) {
        BSTNode node = findNode(data);
        return removeNode(node);
    }

    /**
     * remove node to the BST
     */
    public boolean removeNode(BSTNode node) {
        if (node == null) {
            return false;
        }

        //delete tung cai count 
        node.setCount(node.getCount() - 1);

        //delete nguyen cai node do luon
        //node.setCount(0);
        if (node.getCount() == 0) {
            if (node.isLeaf()) {
                node.getParent().removeLeftChild(node);
                return true;
            } else {
                BSTNode incomer = null;
                if (node.hasLeftChild()) { //tim node thay the uu tien ben trai o day nha
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
     * Get clear
     */
    public void clear() {
        clear(this.root);
        this.root = null;
    }

    /**
     * clear node to the BST
     */
    public void clear(BSTNode node) {
        if (node == null) {
            return;
        }
        if (node.isLeaf()) {
            node.getParent().removeLeftChild(node);
        } else {
            clear(node.getLeftChild());
            clear(node.getRightChild());
        }
    }

    void BFS() { //duyet theo chieu rong
        this.result = "";
        Queue<BSTNode> q = new LinkedList<>();
        q.add(root);

        BSTNode node;
        while (!q.isEmpty()) {
            node = q.poll(); //lay gia tri va loai no ra o dau hang doi
            if (node != null) {
                for (int i = 0; i < node.getCount(); i++) {
                    //System.out.println(node.getData() + ", ");
                    result += node.getData() + ", ";
                }
                q.add(node.getLeftChild());
                q.add(node.getRightChild());
            }
        }
    }

    void DFS() { //duyet theo chieu sau
        this.result = "";
        Stack<BSTNode> s = new Stack<>();
        s.add(root);

        BSTNode node;
        while (!s.isEmpty()) {
            node = s.pop(); //lay gia tri vao cuoi cung ra ngoai
            if (node != null) {
                for (int i = 0; i < node.getCount(); i++) {
                    //System.out.println(node.getData() + ", ");
                    result += node.getData() + ", ";
                }
                s.add(node.getRightChild()); //them phai vao truoc se lay con trai ra xu ly truoc
                s.add(node.getLeftChild());
            }
        }
    }

//    private void BST2Array(BSTNode node) { //chuyen cay thanh mang tang dan
//        if (node == null) {
//            return;
//        }
//        BST2Array(node.getLeftChild());
//        treeData.add(new NodeData(node.getData(), node.getCount()));
//        BST2Array(node.getRightChild());
//    }
//
//    /**
//     * can bang node to the BST
//     */
//    public void blancing() {
//        treeData.clear();
//        BST2Array(this.root); //du lieu da duoc luu tu cay vao trong mang roi
//
//        this.clear(); //xoa bo tat ca cac node trong BST
//        Queue<BSTRange> q = new LinkedList<>();
//        q.add(new BSTRange(0, treeData.size() - 1));
//        BSTRange range;
//        NodeData nodeData;
//        int middleIndex, leftIndex, rightIndex;
//        while (!q.isEmpty()) {
//            range = q.poll();
//            leftIndex = range.getLeftIndex();
//            rightIndex = range.getRightIndex();
//            if (leftIndex <= rightIndex) {
//                middleIndex = (leftIndex + rightIndex) / 2;
//                nodeData = treeData.get(middleIndex);
//                this.addNode(nodeData.getData(), nodeData.getCount());
//
//                q.add(new BSTRange(leftIndex, middleIndex - 1));
//                q.add(new BSTRange(middleIndex + 1, rightIndex));
//            }
//        }
//    }
    String max, min;

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public void findMinMax() {
        max = root.findMaxNode().getData() + "";
        min = root.findMinNode().getData() + "";
    }
}
