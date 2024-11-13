/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ce00000000_q1;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

/**
 *
 * @author Vo Hoang Tu - CE000000
 */
public class Graph {
    static final int MAX_VERTEX = 100;
    int[][] graph = new int[MAX_VERTEX][MAX_VERTEX];
    ArrayList<Edge> edges = new ArrayList<>();
    int theNumberOfVertex;
    int startTraversal;
    String resultDFS = "";
    String resultBFS = "";
    boolean[] isVisited = new boolean[MAX_VERTEX];
    int k;
    
    public Graph() {
        for (int i = 0; i < MAX_VERTEX; i++) {
            for (int j = 0; j < MAX_VERTEX; j++) {
                graph[i][j] = 0;
            }
        }
    }
    
    public void readListData(String fileName){
        try {
            File f = new File(fileName);
            Scanner sc = new Scanner(f);
            theNumberOfVertex = sc.nextInt(); //8 đỉnh
            int n = sc.nextInt(); //10 cạnh
            startTraversal = sc.nextInt(); //0 đỉnh bắt đầu duyệt
            for (int i = 0; i < n; i++) {
                int s = sc.nextInt(); // 0
                int e = sc.nextInt(); // 1
                graph[s][e] = 1;
                graph[e][s] = 1;
                Edge edge = new Edge(new Vertex(s), new Vertex(e), 1);
                edges.add(edge);
            }
            k = sc.nextInt();
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void showGraph(){
        for (int i = 0; i < theNumberOfVertex; i++) {
            for (int j = 0; j < theNumberOfVertex; j++) {
                System.out.print(graph[i][j] + " ");
            }
            System.out.println("");
        }
    }
    
    public void resetIsVisited(){
        for (int i = 0; i < MAX_VERTEX; i++) {
            isVisited[i] = false;
        }
    }
    
    public void DFS(int start){
        resetIsVisited();
        Stack<Integer> s = new Stack<>();
        s.add(start);
        int fromVertex;
        while(!s.isEmpty()){
            fromVertex = s.pop();
            if(isVisited[fromVertex] == false){
                isVisited[fromVertex] = true;
                resultDFS += "," + fromVertex;
                for (int toVertex = theNumberOfVertex - 1; toVertex >= 0; toVertex--) {
                    if(graph[fromVertex][toVertex] > 0 && isVisited[toVertex] == false){
                        s.add(toVertex);
                    }
                }
            }
        }
    }
    
    public int getDegree(int v){
        int count = 0;
        for (int i = 0; i < theNumberOfVertex; i++) {
            if(graph[v][i] > 0){
                count++;
            }
        }
        return count;
    }
    
    public void DFSLab09(int start){
        resetIsVisited();
        Stack<Integer> s = new Stack<>();
        s.add(start);
        int fromVertex;
        while(!s.isEmpty()){
            fromVertex = s.pop();
            if(isVisited[fromVertex] == false){
                isVisited[fromVertex] = true;
                if(fromVertex >= k){
                    resultDFS += "," + fromVertex + "(" + getDegree(fromVertex) + ")";
                }
                for (int toVertex = theNumberOfVertex - 1; toVertex >= 0; toVertex--) {
                    if(graph[fromVertex][toVertex] > 0 && isVisited[toVertex] == false){
                        s.add(toVertex);
                    }
                }
            }
        }
    }
    
    public void writeData(String fileName){
        DFSLab09(startTraversal); //resultDFS
        try {
            FileWriter out = new FileWriter(fileName);
            out.write(resultDFS.substring(1));
            out.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
