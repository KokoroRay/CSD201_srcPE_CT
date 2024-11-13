/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package q8;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

/**
 *
 * @author Nguyen Van Quoc Bao - SE161837
 */
public class Graph {

    int[][] graph;
    static final int MAX_VERTEX = 100;
    int numberOfVerties;
    ArrayList<Edge> edges = new ArrayList<>();
    boolean[] isVisited = new boolean[MAX_VERTEX];
    int startTraversal;
    String resultDFS = "";
    String resultBFS = "";
    String resultFindVertex = ""; //6,5,7
    int[] distance = new int[MAX_VERTEX];
    int[] theVertexBefore = new int[MAX_VERTEX];
    int sumPrim;
    int sumKruskal;
    ArrayList<Edge> listEdgesKruskal = new ArrayList<>();
    int[] parentKruskal = new int[MAX_VERTEX];
    String result = "";

    ArrayList<Vertex> listCutVertices = new ArrayList<>();

    public Graph() {
        this.graph = new int[MAX_VERTEX][MAX_VERTEX];
        for (int i = 0; i < MAX_VERTEX; i++) {
            for (int j = 0; j < MAX_VERTEX; j++) {
                graph[i][j] = 0;
            }
        }
    }

    public void readListDataFile(String fileName) {
        try {
            Scanner sc = new Scanner(new File(fileName));
            // 8 10 0
            this.numberOfVerties = sc.nextInt(); //8 đỉnh
            int countEdge = sc.nextInt(); //10 cạnh
            this.startTraversal = sc.nextInt(); //0
            int start, end;
            for (int i = 0; i < countEdge; i++) {
                start = sc.nextInt();
                end = sc.nextInt();
                graph[start][end] = 1;
                graph[end][start] = 1;
                edges.add(new Edge(new Vertex(start), new Vertex(end), 1));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void print() {
        for (int i = 0; i < numberOfVerties; i++) {
            for (int j = 0; j < numberOfVerties; j++) {
                System.out.print(graph[i][j]);
            }
            System.out.println("");
        }
    }

    public void resetIsVisited() {
        for (int i = 0; i < MAX_VERTEX; i++) {
            isVisited[i] = false;
        }
    }

    public void DFS(int start) {
        resultDFS = "";
        //resetIsVisited();
        Stack<Integer> s = new Stack<>();
        s.push(start);
        int fromVertex;
        while (!s.isEmpty()) {
            fromVertex = s.pop();
            if (isVisited[fromVertex] == false) {
//                if(fromVertex >= 5) {
//                    resultDFS += "," + fromVertex;
//                } // nếu đề 8 10 5
                resultDFS += "," + fromVertex;
                isVisited[fromVertex] = true;
                for (int toVertex = numberOfVerties - 1; toVertex >= 0; toVertex--) {
                    if (graph[fromVertex][toVertex] > 0 && isVisited[toVertex] == false) {
                        s.push(toVertex);
                    }
                }
            }
        }
    }

    public void BFS(int start) {
        resultBFS = "";
        resetIsVisited();
        Queue<Integer> q = new LinkedList<>();
        q.add(start);
        int fromVertex;
        while (!q.isEmpty()) {
            fromVertex = q.poll();
            if (isVisited[fromVertex] == false) {
                resultBFS += "," + fromVertex;
                isVisited[fromVertex] = true;
                for (int toVertex = 0; toVertex < numberOfVerties; toVertex++) {
                    if (graph[fromVertex][toVertex] > 0 && isVisited[toVertex] == false) {
                        q.add(toVertex);
                    }
                }
            }
        }
    }

    public void findVertex(int start) {
        resultFindVertex = "";
        resetIsVisited();
        Stack<Integer> s = new Stack<>();
        s.push(0);
        int fromVertex;
        while (!s.isEmpty()) {
            fromVertex = s.pop();
            if (isVisited[fromVertex] == false) {
                if (fromVertex >= start) {
                    resultFindVertex += "," + fromVertex;
                }
                isVisited[fromVertex] = true;
                for (int toVertex = numberOfVerties - 1; toVertex >= 0; toVertex--) {
                    if (graph[fromVertex][toVertex] > 0 && isVisited[toVertex] == false) {
                        s.push(toVertex);
                    }
                }
            }
        }
    }

    public void resetDistance() {
        for (int i = 0; i < MAX_VERTEX; i++) {
            distance[i] = Integer.MAX_VALUE;
        }
    }

    public void resetTheVertexBefore() {
        for (int i = 0; i < MAX_VERTEX; i++) {
            theVertexBefore[i] = i;
        }
    }

    public int findNearestVertex() {
        int minIndex = -1;
        int minValue = Integer.MAX_VALUE;
        for (int i = 0; i < distance.length; i++) {
            if (distance[i] < minValue && isVisited[i] == false) {
                minValue = distance[i];
                minIndex = i;
            }
        }
        return minIndex;
    }

    public void prim() {
        resetIsVisited();
        resetDistance();
        resetTheVertexBefore();
        sumPrim = 0;
        distance[0] = 0;
        int current;
        for (int i = 0; i < numberOfVerties; i++) {
            current = findNearestVertex();
            sumPrim += distance[current];
            isVisited[current] = true;
            for (int toVertex = 0; toVertex < numberOfVerties; toVertex++) {
                if (graph[current][toVertex] > 0 && isVisited[toVertex] == false && graph[current][toVertex] < distance[toVertex]) {
                    distance[toVertex] = graph[current][toVertex];
                    theVertexBefore[toVertex] = current;
                }
            }
        }
    }

    public void make_Set() {
        for (int i = 0; i < numberOfVerties; i++) {
            parentKruskal[i] = i;
        }
    }

    public int findParent(int v) {
        if (v == parentKruskal[v]) {
            return v;
        }
        return findParent(parentKruskal[v]);
    }

    public boolean union(int a, int b) {
        a = findParent(a);
        b = findParent(b);
        if (a == b) {
            return false;
        }
        parentKruskal[a] = b;
        return true;
    }

    public void kruskal() {
        make_Set();
        Collections.sort(edges);
        sumKruskal = 0;
        for (Edge edge : edges) {
            if (union(edge.start.value, edge.end.value)) {
                listEdgesKruskal.add(edge);
                sumKruskal += edge.weight;
            }
        }
    }

    public void readMatrixDataFile(String fileName) {
        try {
            // 8 0
            Scanner sc = new Scanner(new File(fileName));
            this.numberOfVerties = sc.nextInt(); //5
            for (int i = 0; i < numberOfVerties; i++) {
                for (int j = 0; j < numberOfVerties; j++) {
                    graph[i][j] = sc.nextInt();
                    if (graph[i][j] > 0 && i < j) {
                        edges.add(new Edge(new Vertex(i), new Vertex(j), graph[i][j]));
                    }
                }
            }
        } catch (Exception e) {
        }
    }

    public void findCutVertex() {
        int theNumberOfConnectedComponent = 0;
        for (int i = 0; i < numberOfVerties; i++) {
            if (isVisited[i] == false) {
                theNumberOfConnectedComponent++;
                DFS(i);
            }
        }
//        System.out.println("theNumberOfConnectedComponent: " + theNumberOfConnectedComponent);
        for (int i = 0; i < numberOfVerties; i++) {
            resetIsVisited();
            isVisited[i] = true;
            int count = 0;
            for (int j = 0; j < numberOfVerties; j++) {
                if (isVisited[j] == false) {
                    count++;
                    DFS(j);
                }
            }
//            System.out.println("i: " + i + " - count: " + count);
            if (count > theNumberOfConnectedComponent) {
                listCutVertices.add(new Vertex(i));
            }
        }
    }

    public String printFindCutVertex() {

//        findCutVertex();
        result += listCutVertices.size() + "\r\n";
        String str = "";
        for (Vertex v : listCutVertices) {
            str += "," + v.value;
        }

        return result += str.substring(1);

    }
}
