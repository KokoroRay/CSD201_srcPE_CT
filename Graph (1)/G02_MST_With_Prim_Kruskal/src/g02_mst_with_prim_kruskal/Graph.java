/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package g02_mst_with_prim_kruskal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author admin
 */
public class Graph {

    public static final int Infinity = Integer.MAX_VALUE;
    private int NumberOfVertices;
    private int NumberOfEdges;
    private int startVertex;
    private int[][] graph;
    private List<GEdge> edges;
    
    private String result;
    boolean[] isVisisted;
    Queue<Integer> q;
    Stack<Integer> s;

    private int[] distance;
    private int[] theVertexBefore; //beforeVertex --> currentVertex
    boolean isGraphConnected;
    private int totalWeight;
    private int[] rank; // Mảng lưu rank của các tập hợp
    int[][] resultKruskal;

    Graph(int NumberOfVertices, int NumberOfEdges, int startVertex) {
        this.NumberOfVertices = NumberOfVertices;
        this.NumberOfEdges = NumberOfEdges;
        this.startVertex = startVertex;
        graph = new int[NumberOfVertices][NumberOfVertices];
        edges = new ArrayList<>();

        result = "";
        q = new LinkedList<Integer>();
        s = new Stack<Integer>();
        isVisisted = new boolean[NumberOfVertices];

        distance = new int[NumberOfVertices];
        theVertexBefore = new int[NumberOfVertices];
        isGraphConnected = false;
        totalWeight = 0;
        rank = new int[NumberOfVertices];
        resultKruskal = new int[NumberOfVertices - 1][2];
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

    public int getTotalWeight() {
        return totalWeight;
    }

    public void initMatrix(List<String> input) {
        String[] cut;
        for (int i = 0; i < NumberOfVertices; i++) {
            cut = input.get(i).split(" ");
            for (int j = 0; j < NumberOfVertices; j++) {
                graph[i][j] = Integer.parseInt(cut[j]);
            }
        }
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

    public void PrimReset() {
        for (int i = 0; i < NumberOfVertices; i++) {
            distance[i] = Infinity;
        }
        for (int i = 0; i < NumberOfVertices; i++) {
            theVertexBefore[i] = i;
            isVisisted[i] = false;
        }
        totalWeight = 0;
        result = "";
    }

    public int findNearestVertex() {
        int minIndex = -1, minValue = Infinity;
        for (int i = 0; i < NumberOfVertices; i++) {
            if (isVisisted[i] == false && distance[i] < minValue) {
                minValue = distance[i];
                minIndex = i;
            }
        }
        return minIndex;
    }

    public void Prim() {
        PrimReset();
        distance[0] = 0;
        int currentVertex;
        isGraphConnected = true;
        for (int i = 0; i < NumberOfVertices; i++) {
            currentVertex = findNearestVertex();
            if (currentVertex == -1) {
                //do thi khong lien thong => khong tim thay cay khung co trong so nho nhat
                isGraphConnected = false;
                return;
            } else {
                isVisisted[currentVertex] = true;
                for (int toVertex = 0; toVertex < NumberOfVertices; toVertex++) {
                    if (isVisisted[toVertex] == false && graph[currentVertex][toVertex] > 0 && graph[currentVertex][toVertex] < distance[toVertex]) {
                        distance[toVertex] = graph[currentVertex][toVertex];
                        theVertexBefore[toVertex] = currentVertex;
                    }
                }
            }
        }
        for (int i = 0; i < NumberOfVertices; i++) {
            result += theVertexBefore[i] + " " + i + " " + graph[i][theVertexBefore[i]] + "\n";
            totalWeight += graph[i][theVertexBefore[i]];
        }
        result = result.trim();
    }

    public void resetKruskal() {
        // Khởi tạo tập hợp ban đầu (mỗi đỉnh là cha của chính nó)
        for (int i = 0; i < NumberOfVertices; i++) {
            theVertexBefore[i] = i;
            rank[i] = 0;
        }
        totalWeight = 0;
        result = "";
    }

    // Tìm đại diện của một tập hợp
    public int find(int i) {
        if (theVertexBefore[i] == i) {
            return i;
        }
        return find(theVertexBefore[i]);
    }

    // Hợp nhất hai tập hợp
    public void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);

        if (rank[rootX] < rank[rootY]) {
            theVertexBefore[rootX] = rootY;
        } else if (rank[rootX] > rank[rootY]) {
            theVertexBefore[rootY] = rootX;
        } else {
            theVertexBefore[rootY] = rootX;
            rank[rootX]++;
        }
    }

    // Triển khai thuật toán Kruskal
    public void Kruskal() {
        resetKruskal();
        // Lấy tất cả các cạnh từ ma trận trọng số
        for (int i = 0; i < NumberOfVertices; i++) {
            for (int j = i + 1; j < NumberOfVertices; j++) { // Chỉ lấy một nửa ma trận vì đồ thị vô hướng
                if (graph[i][j] != 0) {
                    edges.add(new GEdge(i, j, graph[i][j]));
                }
            }
        }

        // Sắp xếp các cạnh theo trọng số tăng dần sử dụng Comparator
        Collections.sort(edges, new Comparator<GEdge>() {
            @Override
            public int compare(GEdge e1, GEdge e2) {
                return e1.getWeight() - e2.getWeight(); // So sánh theo trọng số
            }
        });

        // Mảng để lưu cây khung nhỏ nhất
        GEdge[] mst = new GEdge[NumberOfVertices - 1];

        int e = 0; // Số lượng cạnh trong MST
        int i = 0; // Chỉ số cạnh hiện tại

        // Duyệt qua các cạnh đã sắp xếp
        while (e < NumberOfVertices - 1) {
            GEdge nextEdge = edges.get(i++);
            int x = find(nextEdge.getSrc());
            int y = find(nextEdge.getDest());

            // Nếu cạnh này không tạo thành chu trình, thêm nó vào MST
            if (x != y) {
                mst[e++] = nextEdge;
                union(x, y);
            }
        }

        // In ra kết quả cây khung nhỏ nhất
        for (i = 0; i < e; i++) {
            result += mst[i].getSrc() + " " + mst[i].getDest() + " " + mst[i].getWeight() + "\n";
            totalWeight += mst[i].getWeight();
        }
        result = result.trim();
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
