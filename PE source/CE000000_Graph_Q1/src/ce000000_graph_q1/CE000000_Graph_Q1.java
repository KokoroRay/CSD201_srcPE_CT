/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ce000000_graph_q1;

import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Huynh Bao Ngoc - CE170488
 */
public class CE000000_Graph_Q1 {

    Graph g = new Graph();
    String inputFile = "input.txt";
    String outputFile = "output.txt";
    String fi, fo;

    void setFile(String[] args) {
        fi = args.length >= 2 ? args[0] : inputFile;
        fo = args.length >= 2 ? args[1] : outputFile;
    }

    void readFile() {
        g.readDataList(fi);
    }

    void writeData() {
        try {
            FileWriter writer = new FileWriter(fo);
            // DFS traversal
            writer.write(g.dfs().substring(0, g.dfs().length() - 1) + "\n");
            // BFS traversal
            writer.write(g.bfs().substring(0, g.bfs().length() - 1) + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        CE000000_Graph_Q1 q1 = new CE000000_Graph_Q1();
        q1.setFile(args);
        q1.readFile();
        q1.writeData();
    }

}
