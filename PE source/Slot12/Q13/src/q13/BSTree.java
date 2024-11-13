/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package q13;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Vo Hoang Tu - CE000000
 */
public class BSTree {

    BSTNode root;

    String resultPreOrder = "";
    String resultInOrder = "";
    String resultPostOrder = "";
    //7->9->12->10
    String resultFindNode = "";
    ArrayList<BSTNode> listFindNode = new ArrayList<>();
    
    int k; //searchKey

    public BSTree() {
        this.root = null;
    }

    public void addNode(int data) {
        if (root == null) {
            BSTNode newNode = new BSTNode(data);
            root = newNode;
        } else {
            BSTNode currentNode = root;
            boolean isAdded = false;
            while (!isAdded) {
                if (data < currentNode.data) {
                    if (currentNode.left == null) {
                        BSTNode newNode = new BSTNode(data);
                        //newNode.setParent(currentNode);
                        currentNode.setLeft(newNode);
                        isAdded = true;
                    } else {
                        currentNode = currentNode.left;
                    }
                } else if (data > currentNode.data) {
                    if (currentNode.right == null) {
                        BSTNode newNode = new BSTNode(data);
                        //newNode.setParent(currentNode);
                        currentNode.setRight(newNode);
                        isAdded = true;
                    } else {
                        currentNode = currentNode.right;
                    }
                } else {
                    //currentNode.count++;
                    isAdded = true;
                }
            }
        }
    }

    /**
     * Node - Left - Right preOrder(root) preOrder()
     *
     * @param node resultPreOrder = ,7,4,1,6,9,12,10
     */
    private void preOrder(BSTNode node) {
        if (node == null) {
            return;
        }
        for (int i = 0; i < node.count; i++) {
            System.out.println(node.data);
            resultPreOrder += "," + node.data;
        }
        preOrder(node.left);
        preOrder(node.right);
    }

    public void preOrder() {
        preOrder(root);
    }

    private void preOrderQ16(BSTNode node) {
        if (node == null) {
            return;
        }
        for (int i = 0; i < node.count; i++) {
            System.out.println(node.data);
            resultPreOrder += "," + node.data + "[" + node.order + "]";
        }
        preOrderQ16(node.left);
        preOrderQ16(node.right);
    }

    public void preOrderQ16() {
        preOrderQ16(root);
    }

    private void preOrderQ1(BSTNode node) {
        if (node == null) {
            return;
        }
        if (node.data >= k) {
            for (int i = 0; i < node.count; i++) {
                System.out.println(node.data);
                resultPreOrder += "," + node.data;
            }
        }
        preOrderQ1(node.left);
        preOrderQ1(node.right);
    }

    public void preOrderQ1() {
        preOrderQ1(root);
    }

    private void inOrderQ1(BSTNode node) {
        if (node == null) {
            return;
        }
        
        inOrderQ1(node.left);
        if (node.data >= k) {
            for (int i = 0; i < node.count; i++) {
                System.out.println(node.data);
                resultInOrder += "," + node.data;
            }
        }
        inOrderQ1(node.right);
    }

    public void inOrderQ1() {
        inOrderQ1(root);
    }
    
    private void postOrderQ1(BSTNode node) {
        if (node == null) {
            return;
        }
        
        postOrderQ1(node.left);
        
        postOrderQ1(node.right);
        
        if (node.data >= k) {
            for (int i = 0; i < node.count; i++) {
                System.out.println(node.data);
                resultPostOrder += "," + node.data;
            }
        }
    }

    public void postOrderQ1() {
        postOrderQ1(root);
    }
    
    
    
    public void readFile(String fileName) {
        try {
            File f = new File(fileName);
            Scanner sc = new Scanner(f);
            int n = sc.nextInt();
            
            for (int i = 0; i < n; i++) {
                int data = sc.nextInt();
                addNode(data);
            }
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    //String resultFindNode = "";
    //ArrayList<BSTNode> listFindNode = new ArrayList<>();
    
    public BSTNode findNode(int data){
        BSTNode currentNode = root;
        while(currentNode != null){
            listFindNode.add(currentNode);
            resultFindNode += "->" + currentNode.data;
            if(data == currentNode.data){
                return currentNode;
            }else if(data < currentNode.data){
                currentNode = currentNode.left;
            }else{
                currentNode = currentNode.right;
            }
        }
        resultFindNode = "";
        listFindNode.clear();
        return null;
    }
    /**
     * 
     * @param fileName 
     */
    public void writeData(String fileName) {
        //findMinNode()
        //findMaxNode()
        try {
            FileWriter out = new FileWriter(fileName);
            System.out.println(root.findMaxNode().data);
            out.write(root.findMaxNode().data + "\r\n");
            out.write(root.findMinNode().data + "");
            out.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
