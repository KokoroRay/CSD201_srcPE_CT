/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package g01_dfs_bfs_traversing;

import g01_dfs_bfs_traversing.Graph;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class G01 {

    //Change the name of input and output file based on practical paper
    String inputFile = "bfs_input2.txt";
    String outputFile = "bfs_output2.txt";

    //--VARIABLES - @STUDENT: DECLARE YOUR VARIABLES HERE:
    int nV, nE, startVertex;
    List<String> input;
    String result;
    String[] cut;
    Graph graph;

    //--FIXED PART - DO NOT EDIT ANY THINGS HERE--
    //--START FIXED PART--------------------------    
    String fi, fo;

    /**
     * Set input and output file for automatic rating
     *
     * @param args path of input file and path of output file
     */
    public void setFile(String[] args) {
        fi = args.length >= 2 ? args[0] : inputFile;
        fo = args.length >= 2 ? args[1] : outputFile;
    }

    /**
     * Reads data from input file
     */
    public void read() {
        try (Scanner sc = new Scanner(new File(fi))) {
            //--END FIXED PART----------------------------

            //INPUT - @STUDENT: ADD YOUR CODE FOR INPUT HERE:
            input = new ArrayList<>();
            while(sc.hasNextLine()) {
                input.add(sc.nextLine());
            }

            //--FIXED PART - DO NOT EDIT ANY THINGS HERE--
            //--START FIXED PART--------------------------    
            sc.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Input Exception # " + ex);
        }
    }
    //--END FIXED PART----------------------------

    //ALGORITHM - @STUDENT: ADD YOUROWN METHODS HERE (IF NEED):
    //--FIXED PART - DO NOT EDIT ANY THINGS HERE--
    //--START FIXED PART--------------------------    
    /**
     * Main algorithm
     */
    public void solve() {
        //--END FIXED PART----------------------------

        //ALGORITHM - @STUDENT: ADD YOUR CODE FOR OUTPUT HERE:
        result = "";
        cut = input.get(0).split(" ");
        nV = Integer.parseInt(cut[0]);
        nE = Integer.parseInt(cut[1]);
        startVertex = Integer.parseInt(cut[2]);
        input.remove(0);
        graph = new Graph(nV, nE, startVertex);
        for (String str : input) {
            cut = str.split(" ");
            graph.addEdge(Integer.parseInt(cut[0]), Integer.parseInt(cut[1]), 1);
        }
        graph.DFS();
        result += graph.getResult();
        result = result.substring(0, result.length() - 1) + "\n";
        
        graph.BFS();
        result += graph.getResult();
        result = result.substring(0, result.length() - 1);

        //--FIXED PART - DO NOT EDIT ANY THINGS HERE--
        //--START FIXED PART-------------------------- 
    }

    /**
     * Write result into output file
     */
    public void printResult() {
        try {
            FileWriter fw = new FileWriter(fo);
            //--END FIXED PART----------------------------

            //OUTPUT - @STUDENT: ADD YOUR CODE FOR OUTPUT HERE:
            fw.write(result);

            //--FIXED PART - DO NOT EDIT ANY THINGS HERE--
            //--START FIXED PART-------------------------- 
            fw.flush();
            fw.close();
        } catch (IOException ex) {
            System.out.println("Output Exception # " + ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        G01 q = new G01();
        q.setFile(args);
        q.read();
        q.solve();
        q.printResult();
    }
    //--END FIXED PART----------------------------    
}
