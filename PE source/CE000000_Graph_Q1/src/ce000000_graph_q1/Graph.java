/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ce000000_graph_q1;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 *
 * @author Huynh Bao Ngoc - CE170488
 */
public class Graph {

    int graph[][];
    static final int MAX_VERTEX = 100;
    int numberOfVertex;
    ArrayList<Edge> edges = new ArrayList<>();
    int startTraversal;
    String resultDFS = "";
    String resultBFS = "";
    boolean isVisited[] = new boolean[MAX_VERTEX];

    public Graph() {
        graph = new int[MAX_VERTEX][MAX_VERTEX];
        for (int i = 0; i < MAX_VERTEX; i++) {
            for (int j = 0; j < MAX_VERTEX; j++) {
                graph[i][j] = 0;
            }
        }
    }

    public void readDataList(String fileName) {
        try {
            Scanner sc = new Scanner(new File(fileName));
            numberOfVertex = sc.nextInt();
            int numEdge = sc.nextInt();
            startTraversal = sc.nextInt();
            for (int i = 0; i < numEdge; i++) {
                int start, end;
                start = sc.nextInt();
                end = sc.nextInt();
                graph[start][end] = graph[end][start] = 1;
                edges.add(new Edge(new Vertex(start), new Vertex(end), 1));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void showGraph() {
        for (int i = 0; i < numberOfVertex; i++) {
            for (int j = 0; j < numberOfVertex; j++) {
                System.out.print(graph[i][j] + " ");
            }
            System.out.println("");
        }
    }

    public String dfs() {
        resultDFS = "";
        for (int i = 0; i < MAX_VERTEX; i++) {
            isVisited[i] = false;
        }
        dfsTraversal(startTraversal);
        return resultDFS;
    }

    private void dfsTraversal(int vertex) {
        isVisited[vertex] = true;
        resultDFS += vertex + ",";
        for (int i = 0; i < numberOfVertex; i++) {
            if (graph[vertex][i] == 1 && !isVisited[i]) {
                dfsTraversal(i);
            }
        }
    }

    public String bfs() {
        resultBFS = "";
        for (int i = 0; i < MAX_VERTEX; i++) {
            isVisited[i] = false;
        }
        bfsTraversal(startTraversal);
        return resultBFS;
    }

    private void bfsTraversal(int vertex) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(vertex);
        isVisited[vertex] = true;

        while (!queue.isEmpty()) {
            int v = queue.poll();
            resultBFS += v + ",";
            for (int i = 0; i < numberOfVertex; i++) {
                if (graph[v][i] == 1 && !isVisited[i]) {
                    queue.add(i);
                    isVisited[i] = true;
                }
            }
        }
    }
}
