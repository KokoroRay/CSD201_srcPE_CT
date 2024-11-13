/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphdemo;

import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Nguyen Trung Hieu - CE180575
 */
public class GraphList {

    private LinkedList<Integer>[] adjList;
    private int numVertices;

    public GraphList(int numVertices) {
        this.numVertices = numVertices;
        adjList = new LinkedList[numVertices];
        for (int i = 0; i < numVertices; i++) {
            adjList[i] = new LinkedList<>();
        }
    }

    public void addEdge(int i, int j) {
        adjList[i].add(j);
        adjList[j].add(i); // Đồ thị vô hướng
    }

    /*
             |  0  |  1  |  2  |  3  |  4
          ---------------------------------
           0 |  0  |  1  |  1  |  0  |  0
          ---------------------------------
           1 |  0  |  0  |  0  |  1  |  0
          ---------------------------------
           2 |  1  |  0  |  0  |  0  |  1
          ---------------------------------
           3 |  0  |  1  |  0  |  0  |  0
        ---------------------------------
           4 |  0  |  0  |  1  |  0  |  0
     */
    // Xóa cạnh
    public void removeEdge(int i, int j) {
        adjList[i].remove((Integer) j);
        adjList[j].remove((Integer) i);
    }

    // Hàm tính số đỉnh
    public int getNumVertices() {
        return numVertices;
    }

    // Hàm tính số cạnh
    public int getNumEdges() {
        int numEdges = 0;
        for (int i = 0; i < numVertices; i++) {
            numEdges += adjList[i].size();
        }
        return numEdges / 2; // Đồ thị vô hướng nên chia đôi
    }

    // Hàm duyệt DFS
    public void DFS(int startVertex) {
        boolean[] visited = new boolean[numVertices];
        dfsHelper(startVertex, visited);
    }

    private void dfsHelper(int vertex, boolean[] visited) {
        // Đánh dấu đỉnh đã duyệt
        visited[vertex] = true;
        System.out.print(vertex + " ");

        // Duyệt các đỉnh kề
        for (int neighbor : adjList[vertex]) {
            if (!visited[neighbor]) {
                dfsHelper(neighbor, visited);
            }
        }
    }

    // Hàm duyệt BFS
    public void BFS(int startVertex) {
        boolean[] visited = new boolean[numVertices];
        Queue<Integer> queue = new LinkedList<>();

        // Đánh dấu đỉnh bắt đầu là đã duyệt và thêm vào hàng đợi
        visited[startVertex] = true;
        queue.add(startVertex);

        while (!queue.isEmpty()) {
            int vertex = queue.poll();
            System.out.print(vertex + " ");

            // Duyệt các đỉnh kề
            for (int neighbor : adjList[vertex]) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.add(neighbor);
                }
            }
        }
    }

    public void printList() {
        for (int i = 0; i < numVertices; i++) {
            System.out.print(i + ": ");
            for (Integer node : adjList[i]) {
                System.out.print(node + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        GraphList g = new GraphList(5);
        //  Adjacency Matrix
        /*
             |  0  |  1  |  2  |  3  |  4
          ---------------------------------
           0 |  0  |  1  |  1  |  0  |  0
          ---------------------------------
           1 |  1  |  0  |  0  |  1  |  0
          ---------------------------------
           2 |  1  |  0  |  0  |  0  |  1
          ---------------------------------
           3 |  0  |  1  |  0  |  0  |  0
        ---------------------------------
           4 |  0  |  0  |  1  |  0  |  0
         */

        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(1, 3);
        g.addEdge(2, 4);

        /*
                    0
                   / \
                  1   2
                 /     \
                3       4
         */
        g.printList();
        System.out.println("Number of vertices: " + g.getNumVertices());
        System.out.println("Number of edges: " + g.getNumEdges());

        System.out.print("DFS traversal starting from vertex 0: ");
        g.DFS(0);

        System.out.println();

        System.out.print("BFS traversal starting from vertex 0: ");
        g.BFS(0);

        System.out.println();
    }
}
