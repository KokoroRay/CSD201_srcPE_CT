/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package g01_dfs_bfs_traversing;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author admin
 */
public class Graph {
    private int NumberOfVertices;
    private int NumberOfEdges;
    private int startVertex;
    private int[][] graph;
    
    private String result;
    boolean[] isVisisted;
    Queue<Integer> q;
    Stack<Integer> s;

    public Graph(int NumberOfVertices, int NumberOfEdges, int startVertex) {
        this.NumberOfVertices = NumberOfVertices;
        this.NumberOfEdges = NumberOfEdges;
        this.startVertex = startVertex;
        graph = new int[NumberOfVertices][NumberOfVertices];
        
        result = "";
        q = new LinkedList<Integer>();
        s = new Stack<Integer>();
        isVisisted = new boolean[NumberOfVertices];
    }
    
    
    public int getNumberOfVertices() {
        return NumberOfVertices;
    }

    public void setNumberOfVertices(int NumberOfVertices) {
        this.NumberOfVertices = NumberOfVertices;
    }

    public int getNumberOfEdges() {
        return NumberOfEdges;
    }

    public void setNumberOfEdges(int NumberOfEdges) {
        this.NumberOfEdges = NumberOfEdges;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void addEdge(int startIndex, int stopIndex, int edgeValue) {
        graph[startIndex][stopIndex] = edgeValue;
        graph[stopIndex][startIndex] = edgeValue;
    }
    
    private void traversalReset() {
        result = "";
        for (int i = 0; i < NumberOfVertices; i++) {
            isVisisted[i] = false;
        }
        q.clear();
        s.clear();
    }
    
    public void BFS() {
        traversalReset();
        int fromVertex;
        isVisisted[startVertex] = true;
        q.add(startVertex);
        while (!q.isEmpty()) {
            fromVertex = q.poll();
            result += fromVertex + ",";
            for (int toVertex = 0; toVertex < NumberOfVertices; toVertex++) {
                if (isVisisted[toVertex] == false && graph[fromVertex][toVertex] > 0) {
                    q.add(toVertex);
                    isVisisted[toVertex] = true;
                }
            }
        }
    }
    
    public void DFS() {
        traversalReset();
        int fromVertex;
        s.push(startVertex);
        while (!s.isEmpty()) {
            fromVertex = s.pop();
            if (isVisisted[fromVertex] == false) {
                result += fromVertex + ",";
                isVisisted[fromVertex] = true;
                for (int toVertex = NumberOfVertices - 1; toVertex >= 0; toVertex--) {
                    if (isVisisted[toVertex] == false && graph[fromVertex][toVertex] > 0) {
                        s.push(toVertex);
                    }
                }
            }
        }
    }

    public void printGraph() {
        for (int i = 0; i < this.NumberOfVertices; i++) {
            for (int j = 0; j < this.NumberOfVertices; j++) {
                System.out.print(graph[i][j] + " ");
            }
            System.out.println("");
        }
    }
}
