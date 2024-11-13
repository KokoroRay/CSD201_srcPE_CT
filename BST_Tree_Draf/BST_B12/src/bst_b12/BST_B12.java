/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bst_b12;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Admin
 */
public class BST_B12 {

    private String inputFile = "ex01_input.txt";
    private String outputFile = "ex01_output.txt";

    private String fi, fo;

    public void setFile(String[] args) {
        if (args.length >= 2) {
            fi = args[0];
            fo = args[1];
        } else {
            fi = inputFile;
            fo = outputFile;
        }
    }

    int n;
    ArrayList<Integer> a;
    BSTree tree;
    String preOrderResult = "", postOrderResult = "", inOrderResult = "";

    public void readData() {
        try {
            Scanner scan = new Scanner(new File(fi));
            a = new ArrayList<>();
            n = scan.nextInt();
            for (int i = 0; i < n; i++) {
                int value = scan.nextInt();
                a.add(value);
            }
            scan.close();
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        }
    }

    public void solve() {
        tree = new BSTree();

        for (int i = 0; i < n; i++) {
            tree.addNode(a.get(i));
        }

        tree.balancing();
        tree.preOrder();
        preOrderResult = tree.getTraversalResult();
        tree.postOrder();
        postOrderResult = tree.getTraversalResult();
        tree.inOrder();
        inOrderResult = tree.getTraversalResult();

    }

    public void printResult() {
        try {
            FileWriter fw = new FileWriter(fo);
            fw.write(preOrderResult + "\n");
            fw.write(postOrderResult + "\n");
            fw.write(inOrderResult);
            fw.flush();
            fw.close();
        } catch (Exception e) {
        }
    }

    public static void main(String[] args) {
        BST_B12 app = new BST_B12();
        app.setFile(args);
        app.readData();
        app.solve();
        app.printResult();
    }

}
