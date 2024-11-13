/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ce000000_q6;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
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
    int from, to;
    
    int[] distance = new int[MAX_VERTEX];
    int[] theVertexBefore = new int[MAX_VERTEX];
    
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
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void readMatrixData(String fileName){
        try {
            File f = new File(fileName);
            Scanner sc = new Scanner(f);
            theNumberOfVertex = sc.nextInt(); //8 đỉnh
            from = sc.nextInt(); //0
            to = sc.nextInt(); //7
            for (int i = 0; i < theNumberOfVertex; i++) {
                for (int j = 0; j < theNumberOfVertex; j++) {
                    int data = sc.nextInt();
                    graph[i][j] = data;
                    if(graph[i][j] > 0 && i < j){
                        Edge edge = new Edge(new Vertex(i), new Vertex(j), data);
                        edges.add(edge);
                    }
                }
            }
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
    
    public void BFS(int start){
        resetIsVisited();
        Queue<Integer> q = new LinkedList<>();
        q.add(start);
        int fromVertex;
        while(!q.isEmpty()){
            fromVertex = q.poll();
            if(isVisited[fromVertex] == false){
                isVisited[fromVertex] = true;
                resultBFS += "," + fromVertex;
                for (int toVertex = 0; toVertex < theNumberOfVertex; toVertex++) {
                    if(graph[fromVertex][toVertex] > 0 && isVisited[toVertex] == false){
                        q.add(toVertex);
                    }
                }
            }
        }
    }
    
    public void resetDistance(){
        for (int i = 0; i < MAX_VERTEX; i++) {
            distance[i] = Integer.MAX_VALUE;
        }
    }
    
    public void resetTheVertexBefore(){
        for (int i = 0; i < MAX_VERTEX; i++) {
            theVertexBefore[i] = i;
        }
    }
    
    public int findNearestVertex(){
        int minIndex = -1;
        int minValue = Integer.MAX_VALUE;
        for (int i = 0; i < theNumberOfVertex; i++) {
            if(distance[i] < minValue && isVisited[i] == false){
                minValue = distance[i];
                minIndex = i;
            }
        }
        return minIndex;
    }
    
    public void dijkstra(){
        resetIsVisited();
        resetDistance();
        resetTheVertexBefore();
        distance[from] = 0;
        int current;
        for (int i = 0; i < theNumberOfVertex; i++) {
            current = findNearestVertex();
            if(current == -1){
                break;
            }else{
                isVisited[current] = true;
                for (int toVertex = 0; toVertex < theNumberOfVertex; toVertex++) {
                    if(graph[current][toVertex] > 0 &&
                       isVisited[toVertex] == false &&
                       distance[current] + graph[current][toVertex] < distance[toVertex]
                    ){
                       theVertexBefore[toVertex] = current;
                       distance[toVertex] = distance[current] + graph[current][toVertex];
                    }
                }
            }
        }
    }
    
    public void writeData(String fileName){
        dijkstra();
        
        try {
            FileWriter out = new FileWriter(fileName);
            String res = to + "";
            int current = to;
            while(current != from){
                current = theVertexBefore[current];
                res = current + "->" + res;
            }
            out.write(res + ": " + distance[to] + "");
            out.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
