/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ce000000_q1;

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
    int [][] graph = new int[MAX_VERTEX][MAX_VERTEX];
    int theNumberOfVertex;
    ArrayList<Edge> edges = new ArrayList<>();
    int startTraversal;
    String resultDFS = "";
    String resultBFS = "";
    boolean[] isVisited = new boolean[MAX_VERTEX];
    int start, end;
    int[] theVertexBefore = new int[MAX_VERTEX];
    int[] distance = new int[MAX_VERTEX];
    public Graph(){ 
        for (int i = 0; i < MAX_VERTEX; i++) {
            for (int j = 0; j < MAX_VERTEX; j++) {
                graph[i][j] = 0;
            }
        }
    }
    ArrayList<Vertex> listIsolated = new ArrayList<>();
    
    int theNumberOfConnectedComponent = 0;
    //0,1,2,3,4
    //5,6
    //7,8,9
    ArrayList<String> listConnectedComponent = new ArrayList<>();
    
    public void readListFile(String fileName){
        try {
            File f = new File(fileName);
            Scanner sc = new Scanner(f);
            //8 10 0
            theNumberOfVertex = sc.nextInt(); //8
            int n = sc.nextInt(); //10
            startTraversal = sc.nextInt(); //0
            for (int i = 0; i < n; i++) {
                int s = sc.nextInt();
                int e = sc.nextInt();
                graph[s][e] = 1;
                graph[e][s] = 1;
                edges.add(new Edge(new Vertex(s), new Vertex(e), 1));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void readMatrixFile(String fileName){
        try {
            File f = new File(fileName);
            Scanner sc = new Scanner(f);
            //9
            theNumberOfVertex = sc.nextInt(); //9

            for (int i = 0; i < theNumberOfVertex; i++) {
                for (int j = 0; j < theNumberOfVertex; j++) {
                    graph[i][j] = sc.nextInt();
                    if(graph[i][j] > 0 && i < j){
                        edges.add(new Edge(new Vertex(i), new Vertex(j), graph[i][j]));
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
        resultDFS = "";
        resetIsVisited();
        Stack<Integer> s = new Stack<>();
        s.add(start);
        int fromVertex;
        while(!s.isEmpty()){
            fromVertex =  s.pop();
            if(isVisited[fromVertex] == false){
                isVisited[fromVertex] = true;
                resultDFS += "," + fromVertex;
                for (int toVertex = theNumberOfVertex - 1; toVertex >= 0; toVertex--) {
                    if(isVisited[toVertex] == false && graph[fromVertex][toVertex] > 0){
                        s.add(toVertex);
                    }
                }
            }
        }
        listConnectedComponent.add(resultDFS);
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
                    if(isVisited[toVertex] == false && graph[fromVertex][toVertex] > 0){
                        q.add(toVertex);
                    }
                }
            }
        }
    }
    
    public void resetTherVertexBefore(){
        for (int i = 0; i < MAX_VERTEX; i++) {
            theVertexBefore[i] = i;
        }
    }
    
    public void resetDistance(){
        for (int i = 0; i < MAX_VERTEX; i++) {
            distance[i] = Integer.MAX_VALUE;
        }
    }
    
    public int findNearestVertex(){
        int minIndex = -1;
        int minValue = Integer.MAX_VALUE;
        for (int i = 0; i < theNumberOfVertex; i++) {
            if(isVisited[i] == false && distance[i] < minValue){
                minValue = distance[i];
                minIndex = i;
            }
        }
        return minIndex;
    }
    
    public void dijkstra(){
        resetIsVisited();
        resetTherVertexBefore();
        resetDistance();
        distance[start] = 0;
        int current;
        for (int i = 0; i < theNumberOfVertex; i++) {
            current = findNearestVertex(); // Loop1: = 0
            if(current == -1){
                break;
            }else{
                isVisited[current] = true;
                for (int toVertex = 0; toVertex < theNumberOfVertex; toVertex++) {
                    if(graph[current][toVertex] > 0 
                       && isVisited[toVertex] == false
                       && distance[current] + graph[current][toVertex] < distance[toVertex]){
                        theVertexBefore[toVertex] = current;
                        distance[toVertex] = distance[current] + graph[current][toVertex];
                    }
                }
            }
        }
    }
    
    public void findIsolated(){
        int j;
        for (int i = 0; i < theNumberOfVertex; i++) {
            for (j = 0; j < theNumberOfVertex; j++) {
                if(graph[i][j] > 0){
                    break;
                }
            }
            //Kiểm tra lại J
            if(j == theNumberOfVertex){
                listIsolated.add(new Vertex(i));
            }
        }
    }
    
    public void findConnectedComponent(){
        for (int i = 0; i < theNumberOfVertex; i++) {
            if(isVisited[i] == false){
                DFS(i);
                theNumberOfConnectedComponent++;
            }
        }
    }
    
    public void writeData(String fileName){
        findConnectedComponent();
        try {
            FileWriter out = new FileWriter(fileName);
            //out.write(theNumberOfConnectedComponent + "\r\n");
            out.write(listConnectedComponent.size() + "\r\n");
            for (String str : listConnectedComponent) {
                out.write(str.substring(1) + "\r\n");
            }
            out.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
