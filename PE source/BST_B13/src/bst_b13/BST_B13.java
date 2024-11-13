package bst_b13;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author DELL
 */
public class BST_B13 {

    private String inputFile = "bst13_input.txt";
    private String outputFile = "bst13_output.txt";

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
    BSTNode node;
    String max;
    String min;

    public void readData() {
        try {
            Scanner sc = new Scanner(new File(fi));
            a = new ArrayList<>();
            n = sc.nextInt();
            int value;
            for (int i = 0; i < n; i++) {
                value = sc.nextInt();
                a.add(value);
            }
            sc.close();
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        }

    }

    public void solve() {
        tree = new BSTree();
        for (int i = 0; i < n; i++) {
            tree.addNode(a.get(i));
        }
        tree.findMinMax();
        max = tree.getMax();
        min = tree.getMin();

    }

    public void printResult() {
        try {
            FileWriter fw = new FileWriter(fo);

            fw.write(max + "" + "\n");
            fw.write(min + "");

            fw.flush();
            fw.close();
        } catch (IOException ex) {
            System.out.println(ex);
        }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        BST_B13 mn = new BST_B13();
        mn.setFile(args);
        mn.readData();
        mn.solve();
        mn.printResult();
    }

}
