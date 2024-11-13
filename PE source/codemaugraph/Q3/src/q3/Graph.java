/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package q3;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
    int startTraversal;
    boolean[] isVisited = new boolean[MAX_VERTEX];
    String resultDFS = "";
    String resultBFS = "";
    boolean isConectedComponent;
    ArrayList<Vertex> listPrim = new ArrayList<>();
    int[] distance = new int[MAX_VERTEX];
    int[] theVertexBefore = new int[MAX_VERTEX];
    int sumPrim = 0;//In ra tổng số đường đi của prim
    //Kruskal
    int[] parentKruskal = new int[MAX_VERTEX];
    ArrayList<Edge> listEdgeKruskal = new ArrayList<>();
    int sumKruskal = 0;
    String result = "";

    public Graph() {
        this.graph = new int[MAX_VERTEX][MAX_VERTEX];
        for (int i = 0; i < MAX_VERTEX; i++) {
            for (int j = 0; j < MAX_VERTEX; j++) {
                graph[i][j] = 0;
            }
        }
    }

    public void readFile(String fileName) {
        try {
            File openFile = new File(fileName);
            Scanner sc = new Scanner(openFile);
            this.edges.clear();
            this.numberOfVerties = sc.nextInt();
            int countEdge = sc.nextInt();
            this.startTraversal = sc.nextInt();
            int start, end;
            for (int i = 0; i < countEdge; i++) {
                start = sc.nextInt();
                end = sc.nextInt();
                graph[start][end] = graph[end][start] = 1;
                edges.add(new Edge(new Vertex(start), new Vertex(end), 1));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void readMatrix(String fileName) {
        try {
            File openFile = new File(fileName);
            Scanner sc = new Scanner(openFile);
            this.edges.clear();
            this.numberOfVerties = sc.nextInt();
            for (int i = 0; i < numberOfVerties; i++) {
                for (int j = 0; j < numberOfVerties; j++) {
                    graph[i][j] = sc.nextInt();
                    if (i < j && graph[i][j] > 0) {
                        edges.add(new Edge(new Vertex(i), new Vertex(j), graph[i][j]));
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void print() {
        for (int i = 0; i < numberOfVerties; i++) {
            for (int j = 0; j < numberOfVerties; j++) {
                System.out.println(graph[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void resetIsVisited() {
        for (int i = 0; i < MAX_VERTEX; i++) {
            isVisited[i] = false;
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
        for (int i = 0; i < numberOfVerties; i++) {
            if (isVisited[i] == false && distance[i] < minValue) {
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
        listPrim.clear();
        distance[0] = 0;
        int current;
        isConectedComponent = true;
        for (int i = 0; i < numberOfVerties; i++) {
            current = findNearestVertex();
            if (current == -1) {
                isConectedComponent = false;
            } else {
                //System.out.println(current + " -> ");
                listPrim.add(new Vertex(current));
                sumPrim += distance[current];
                isVisited[current] = true;
                for (int toVertex = 0; toVertex < numberOfVerties; toVertex++) {
                    if (isVisited[toVertex] == false
                            && graph[current][toVertex] > 0
                            && graph[current][toVertex] < distance[toVertex]) {
                        distance[toVertex] = graph[current][toVertex];
                        theVertexBefore[toVertex] = current;
                    }
                }
            }
        }
    }

    public void make_set() {
        for (int i = 0; i < MAX_VERTEX; i++) {
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
        parentKruskal[b] = a;
        return true;
    }

    public void kruskal() {
        make_set();
        sumKruskal = 0;
        Collections.sort(edges);
        for (int i = 0; i < edges.size(); i++) {
            if (listEdgeKruskal.size() == numberOfVerties - 1) {
                break;
            }
            if (union(edges.get(i).getStart().getValue(), edges.get(i).getEnd().getValue())) {
                listEdgeKruskal.add(edges.get(i));
                sumKruskal += edges.get(i).getWeight();
            }
        }
    }

    public ArrayList<Integer> findIsolatedVertices() {
        ArrayList<Integer> isolatedVertices = new ArrayList<>();

        for (int i = 0; i < numberOfVerties; i++) {
            boolean hasEdge = false;
            for (int j = 0; j < numberOfVerties; j++) {
                if (graph[i][j] == 1) {
                    hasEdge = true;
                    break;
                }
            }
            if (!hasEdge) {
                isolatedVertices.add(i);
            }
        }
        return isolatedVertices;
    }

    public String findIsolated() {
        ArrayList<Integer> isolatedVertices = findIsolatedVertices();
        boolean hasIsolatedVertices = !isolatedVertices.isEmpty();

        if (hasIsolatedVertices) {
            StringBuilder sb = new StringBuilder();
            for (int vertex : isolatedVertices) {
                sb.append(vertex).append(",");
            }
            sb.deleteCharAt(sb.length() - 1);
            result += sb.toString();
        } else {
            result += "There is a connected graph";
        }
        return result;
    }
}
