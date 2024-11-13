/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package q8_cut_vertex;

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
 * @author Nguyen Trung Hieu - CE180575
 */
public class Graph {

    public static final int Infinity = Integer.MAX_VALUE;
    private int NumberOfVertices;
    private int NumberOfEdges;
    private int startVertex, endVertex;
    private int[][] graph;
    private List<GEdge> edges;

    private String result;
    private boolean checkResult;
    List<Integer> path;
    boolean[] isVisited;
    Queue<Integer> q;
    Stack<Integer> s;

    private int[] distance;
    private int[] theVertexBefore; //beforeVertex --> currentVertex
    boolean isGraphConnected;
    private int totalWeight;
    private int[] rank; // Mảng lưu rank của các tập hợp
    private int[][] resultKruskal;
    private int[] pathHamilton;
    private int numberOfConnectedComponent;
    
    private ArrayList<String> dijkstraAllPaths;
    
    ArrayList<Integer> dijkstra_theVertexBefore[];
    ArrayList<String> dijkstraPath;
    String dijkstraMessage;
    
    /**  ----------- Cut Vertex ---------------*/
    private int[] discoveryTime, low , parent; // low is Giá trị nhỏ nhất của mỗi đỉnh
    private boolean[] isCutVertex;
    private int time;

    public Graph(int NumberOfVertices, int NumberOfEdges, int startVertex, int endVertex) {
        this.NumberOfVertices = NumberOfVertices;
        this.NumberOfEdges = NumberOfEdges;
        this.startVertex = startVertex;
        this.endVertex = endVertex;
        graph = new int[NumberOfVertices][NumberOfVertices];
        edges = new ArrayList<>();

        result = "";
        path = new ArrayList<>();
        pathHamilton = new int[NumberOfVertices];
        checkResult = true;
        q = new LinkedList<Integer>();
        s = new Stack<Integer>();
        isVisited = new boolean[NumberOfVertices];

        distance = new int[NumberOfVertices];
        theVertexBefore = new int[NumberOfVertices];
        isGraphConnected = false;
        totalWeight = 0;
        rank = new int[NumberOfVertices];
        resultKruskal = new int[NumberOfVertices - 1][2];
        numberOfConnectedComponent = 0;
        
        dijkstraPath = new ArrayList<>();
        dijkstraMessage = "";
        dijkstraAllPaths = new ArrayList<>();
        
        this.discoveryTime = new int[NumberOfVertices];
        this.low = new int[NumberOfVertices];
        this.parent = new int[NumberOfVertices];
        this.isCutVertex = new boolean[NumberOfVertices];
        this.time = 0;
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

    public void resetResult() {
        this.result = "";
        this.checkResult = true;
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
            isVisited[i] = false;
        }
        q.clear();
        s.clear();
    }

    public void BFS() {
        traversalReset();
        int fromVertex;
        isVisited[startVertex] = true;
        q.add(startVertex);
        while (!q.isEmpty()) {
            fromVertex = q.poll();
            result += fromVertex + ",";
            for (int toVertex = 0; toVertex < NumberOfVertices; toVertex++) {
                if (isVisited[toVertex] == false && graph[fromVertex][toVertex] > 0) {
                    q.add(toVertex);
                    isVisited[toVertex] = true;
                }
            }
        }
    }
    
    public void BFS(int startVertex) {
        int fromVertex;
        isVisited[startVertex] = true;
        q.add(startVertex);
        while (!q.isEmpty()) {
            fromVertex = q.poll();
            if(checkResult) {
                result += fromVertex;
                checkResult = false;
            } else result += "," +fromVertex;
            for (int toVertex = 0; toVertex < NumberOfVertices; toVertex++) {
                if (isVisited[toVertex] == false && graph[fromVertex][toVertex] > 0) {
                    q.add(toVertex);
                    isVisited[toVertex] = true;
                }
            }
        }
        result += "\n";
        checkResult = true;
    }

    public void DFS() {
        traversalReset();
        int fromVertex;
        s.push(startVertex);
        while (!s.isEmpty()) {
            fromVertex = s.pop();
            if (isVisited[fromVertex] == false) {
                result += fromVertex + ",";
                isVisited[fromVertex] = true;
                for (int toVertex = NumberOfVertices - 1; toVertex >= 0; toVertex--) {
                    if (isVisited[toVertex] == false && graph[fromVertex][toVertex] > 0) {
                        s.push(toVertex);
                    }
                }
            }
        }
    }
    
    public void DFS(int startVertex) {
        int fromVertex;
        s.push(startVertex);
        while (!s.isEmpty()) {
            fromVertex = s.pop();
            if (isVisited[fromVertex] == false) {
                if(checkResult) {
                result += fromVertex;
                checkResult = false;
            } else result += "," +fromVertex;
                isVisited[fromVertex] = true;
                for (int toVertex = NumberOfVertices - 1; toVertex >= 0; toVertex--) {
                    if (isVisited[toVertex] == false && graph[fromVertex][toVertex] > 0) {
                        s.push(toVertex);
                    }
                }
            }
        }
        result += "\n";
        checkResult = true;
    }

    public void PrimReset() {
        for (int i = 0; i < NumberOfVertices; i++) {
            distance[i] = Infinity;
        }
        for (int i = 0; i < NumberOfVertices; i++) {
            theVertexBefore[i] = i;
            isVisited[i] = false;
        }
        totalWeight = 0;
        result = "";
    }

    public int findNearestVertex() {
        int minIndex = -1, minValue = Infinity;
        for (int i = 0; i < NumberOfVertices; i++) {
            if (isVisited[i] == false && distance[i] < minValue) {
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
                isVisited[currentVertex] = true;
                for (int toVertex = 0; toVertex < NumberOfVertices; toVertex++) {
                    if (isVisited[toVertex] == false && graph[currentVertex][toVertex] > 0 && graph[currentVertex][toVertex] < distance[toVertex]) {
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

    public void findIsolateVertexs() {
        boolean checkConnectedGraph = true;
        for (int i = 0; i < this.NumberOfVertices; i++) {
            boolean isIsolate = true;
            for (int j = 0; j < this.NumberOfVertices; j++) {
                if (graph[i][j] != 0) {
                    isIsolate = false;
                    break;
                }
            }
            if (isIsolate) {
                if (checkResult) {
                    result += i;
                    checkResult = false;
                } else {
                    result += "," + i;
                }
                checkConnectedGraph = false;
            }
        }
        if (checkConnectedGraph) {
            result = "There is a connected graph";
        }
    }

    /**
     * --------------Eulerian cycle & path --------------------
     */
    // Kiểm tra liên thông sử dụng DFS
    private boolean isConnected() {
        int startVertex = -1;

        // Tìm đỉnh có cạnh (bỏ qua các đỉnh có bậc 0)
        for (int i = 0; i < NumberOfVertices; i++) {
            if (degree(i) > 0) {
                startVertex = i;
                break;
            }
        }

        // Nếu không có cạnh nào, đồ thị rỗng, coi như liên thông
        if (startVertex == -1) {
            return true;
        }

        // Thực hiện DFS bắt đầu từ đỉnh startVertex
        dfs(startVertex);

        // Kiểm tra xem có đỉnh nào chưa được thăm mà có bậc lớn hơn 0
        for (int i = 0; i < NumberOfVertices; i++) {
            if (!isVisited[i] && degree(i) > 0) {
                return false;
            }
        }
        return true;
    }

    // Hàm đếm bậc của đỉnh
    private int degree(int vertex) {
        int degree = 0;
        for (int i = 0; i < NumberOfVertices; i++) {
            degree += graph[vertex][i];
        }
        return degree;
    }

    // DFS để thăm các đỉnh
    private void dfs(int vertex) {
        isVisited[vertex] = true;
        for (int i = 0; i < NumberOfVertices; i++) {
            if (graph[vertex][i] > 0 && !isVisited[i]) {
                result += "," + i;
                dfs(i);
            }
        }
    }

    // Kiểm tra số đỉnh có bậc lẻ
    private int oddDegreeVerticesCount() {
        int count = 0;
        for (int i = 0; i < NumberOfVertices; i++) {
            if (degree(i) % 2 != 0) {
                count++;
            }
        }
        return count;
    }

    // Kiểm tra và in kết quả Euler
    public void checkEulerian() {
        resetResult();  // reset result and checkResult
        if (!isConnected()) {
            System.out.println("The graph is not connected.");
            return;
        }
        
        int oddDegreeVertices = oddDegreeVerticesCount();
        if (oddDegreeVertices == 0) {
            printEulerCycle();
            for (Integer value : path) {
                if (checkResult) {
                    result += value;
                    checkResult = false;
                } else {
                    result += "," + value;
                }
            }
            result += "\n";
            checkResult = true;
            path.clear();
        } else {
            result += "No eulerian cycle!";
            result += "\n";
        }
        if (oddDegreeVertices == 2) {
            printEulerPath();
            for (Integer value : path) {
                if (checkResult) {
                    result += value;
                    checkResult = false;
                } else {
                    result += "," + value;
                }
            }
        } else {
            result += "No eulerian path!";
        }

    }

    // Tìm và in chu trình Euler
    private void printEulerCycle() {
        Stack<Integer> stack = new Stack<>();
        List<Integer> eulerCycle = new ArrayList<>();

        int currentVertex = 0;
        stack.push(currentVertex);

        while (!stack.isEmpty()) {
            if (degree(currentVertex) == 0) {
                eulerCycle.add(currentVertex);
                currentVertex = stack.pop();
            } else {
                for (int i = 0; i < NumberOfVertices; i++) {
                    if (graph[currentVertex][i] > 0) {
                        stack.push(currentVertex);
                        graph[currentVertex][i]--;
                        graph[i][currentVertex]--;
                        currentVertex = i;
                        break;
                    }
                }
            }
        }
        path = eulerCycle;
    }

    // Tìm và in đường đi Euler
    private void printEulerPath() {
        Stack<Integer> stack = new Stack<>();
        List<Integer> eulerPath = new ArrayList<>();
        int startVertex = -1;

        // Tìm đỉnh có bậc lẻ để bắt đầu
        for (int i = 0; i < NumberOfVertices; i++) {
            if (degree(i) % 2 != 0) {
                startVertex = i;
                break;
            }
        }

        stack.push(startVertex);
        int currentVertex = startVertex;

        while (!stack.isEmpty()) {
            if (degree(currentVertex) == 0) {
                eulerPath.add(currentVertex);
                currentVertex = stack.pop();
            } else {
                for (int i = 0; i < NumberOfVertices; i++) {
                    if (graph[currentVertex][i] > 0) {
                        stack.push(currentVertex);
                        graph[currentVertex][i]--;
                        graph[i][currentVertex]--;
                        currentVertex = i;
                        break;
                    }
                }
            }
        }

        path = eulerPath;
    }

    /**
     * ----------------- Hamilton cycle and path ---------------------------
     */
    public void resetHamilton() {
        Arrays.fill(pathHamilton, -1);
    }

    // Hàm kiểm tra có chu trình Hamilton không
    public boolean hasHamiltonianCycle() {
        resetHamilton();
        pathHamilton[0] = 0;
        return hamiltonianCycleUtil(1);
    }

    // Hàm hỗ trợ để tìm chu trình Hamilton
    private boolean hamiltonianCycleUtil(int pos) {
        // Nếu tất cả các đỉnh đã được thêm vào đường đi
        if (pos == NumberOfVertices) {
            // Kiểm tra xem có cạnh nối từ đỉnh cuối đến đỉnh đầu không
            return graph[pathHamilton[pos - 1]][pathHamilton[0]] == 1;
        }

        // Thử các đỉnh tiếp theo
        for (int v = 1; v < NumberOfVertices; v++) {
            if (isSafe(v, pos)) {
                pathHamilton[pos] = v;

                if (hamiltonianCycleUtil(pos + 1)) {
                    return true;
                }

                // Nếu không tìm thấy, bỏ chọn đỉnh này
                pathHamilton[pos] = -1;
            }
        }
        return false;
    }

    // Kiểm tra xem có thể chọn đỉnh v cho vị trí pos không
    private boolean isSafe(int v, int pos) {
        // Kiểm tra nếu có cạnh từ đỉnh trước đó đến đỉnh v
        if (graph[pathHamilton[pos - 1]][v] == 0) {
            return false;
        }

        // Kiểm tra nếu đỉnh đã được thêm vào đường đi
        for (int i = 0; i < pos; i++) {
            if (pathHamilton[i] == v) {
                return false;
            }
        }
        return true;
    }

    // In chu trình Hamilton
    public void printHamiltonianCycle() {
        if (hasHamiltonianCycle()) {
            System.out.print("Hamiltonian Cycle: ");
            for (int i = 0; i < NumberOfVertices; i++) {
                System.out.print(pathHamilton[i] + " ");
            }
            System.out.println(pathHamilton[0]); // In đỉnh đầu để hoàn thành chu trình
        } else {
            System.out.println("No Hamiltonian Cycle found.");
        }
    }

    // Kiểm tra có đường đi Hamilton không
    public boolean hasHamiltonianPath() {
        resetHamilton();
        for (int i = 0; i < NumberOfVertices; i++) {
            pathHamilton[0] = i; // Bắt đầu từ đỉnh i
            if (hamiltonianPathUtil(1)) {
                return true;
            }
        }
        return false;
    }

    // Hàm hỗ trợ để tìm đường đi Hamilton
    private boolean hamiltonianPathUtil(int pos) {
        // Nếu tất cả các đỉnh đã được thêm vào đường đi
        if (pos == NumberOfVertices) {
            return true;
        }

        // Thử các đỉnh tiếp theo
        for (int v = 0; v < NumberOfVertices; v++) {
            if (isSafe(v, pos)) {
                pathHamilton[pos] = v;

                if (hamiltonianPathUtil(pos + 1)) {
                    return true;
                }

                // Nếu không tìm thấy, bỏ chọn đỉnh này
                pathHamilton[pos] = -1;
            }
        }
        return false;
    }

    // In đường đi Hamilton
    public void printHamiltonianPath() {
        if (hasHamiltonianPath()) {
            System.out.print("Hamiltonian Path: ");
            for (int i = 0; i < NumberOfVertices; i++) {
                System.out.print(pathHamilton[i] + " ");
            }
            System.out.println();
        } else {
            System.out.println("No Hamiltonian Path found.");
        }
    }

    /**
     * ----------------- Connected Component -----------------------
     */
    
    // Hàm tìm và in các thành phần liên thông với BFS
    public void connectedComponentWidthBFS() {
        traversalReset();
        resetResult();
        // Duyệt qua tất cả các đỉnh
        for (int i = 0; i < NumberOfVertices; i++) {
            // Nếu đỉnh chưa được thăm và có bậc lớn hơn 0, tức là thuộc một thành phần liên thông mới
            if (!isVisited[i] && degree(i) > 0) {
                numberOfConnectedComponent++; // Tăng số lượng thành phần liên thông
                BFS(i); // Thực hiện BFS cho thành phần liên thông mới
            }
        }
    }
    
    // Hàm tìm và in các thành phần liên thông với BFS
    public void connectedComponentWidthDFS() {
        traversalReset();
        resetResult();
        // Duyệt qua tất cả các đỉnh
        for (int i = 0; i < NumberOfVertices; i++) {
            // Nếu đỉnh chưa được thăm và có bậc lớn hơn 0, tức là thuộc một thành phần liên thông mới
            if (!isVisited[i] && degree(i) > 0) {
                numberOfConnectedComponent++; // Tăng số lượng thành phần liên thông
                DFS(i); // Thực hiện BFS cho thành phần liên thông mới
            }
        }
    }
    
    public void connectedComponent(int startVertex) {
        isConnected(startVertex);
    }
    
    public int getNumberOfConnectedComponent() {
        return this.numberOfConnectedComponent;
    }

    // Kiểm tra liên thông sử dụng DFS
    private boolean isConnected(int startVertex) {

        result += startVertex;
        numberOfConnectedComponent++;
        
        // Thực hiện DFS bắt đầu từ đỉnh startVertex
        dfs(startVertex);

        // Kiểm tra xem có đỉnh nào chưa được thăm mà có bậc lớn hơn 0
        for (int i = startVertex; i < NumberOfVertices; i++) {
            if (!isVisited[i] && degree(i) > 0) {
                result += "\n";
                isConnected(i);
                return false;
            }            
        }
        return true;
    }
    
    /** ------------------------- Dijkstra -----------------------------*/
    
    public void DijkstraReset() {
        dijkstra_theVertexBefore = new ArrayList[NumberOfVertices];

        for (int i = 0; i < NumberOfVertices; i++) {
            distance[i] = Infinity;
            dijkstra_theVertexBefore[i] = new ArrayList<>();
            dijkstra_theVertexBefore[i].add(i);
            isVisited[i] = false;
        }
        dijkstraPath = new ArrayList<>();
        dijkstraMessage = "";
    }

    public void Dijkstra() {

        DijkstraReset();
        distance[startVertex] = 0;
        int currentVertex;
        isGraphConnected = true;
        for (int i = 0; i < NumberOfVertices; i++) {
            currentVertex = findNearestVertex();
            if (currentVertex == -1) {
                //do thi khong lien thong => khong tim thay cay khung co trong so nho nhat
                isGraphConnected = false;
                break;
            } else {
                isVisited[currentVertex] = true;
                for (int toVertex = 0; toVertex < NumberOfVertices; toVertex++) {
                    if ((isVisited[toVertex] == false || toVertex == endVertex) && graph[currentVertex][toVertex] > 0 && distance[currentVertex] + graph[currentVertex][toVertex] <= distance[toVertex]) {
                        if (distance[currentVertex] + graph[currentVertex][toVertex] < distance[toVertex]) {
                            dijkstra_theVertexBefore[toVertex].clear();
                        }
                        distance[toVertex] = distance[currentVertex] + graph[currentVertex][toVertex];
                        dijkstra_theVertexBefore[toVertex].add(currentVertex);
                    }
                }
            }
        }
        if (isGraphConnected) {
            dijkstraPath.clear();
            String path = "" + endVertex;
            currentVertex = endVertex;
            
            
            // Dijkstra_displayPath(path, currentVertex, startVertex, endVertex);
            
            // Print all paths for each vertex
            for (int vertex = 0; vertex < NumberOfVertices; vertex++) {
                if (distance[vertex] != Infinity) {
                    Dijkstra_displayPath("" + vertex, vertex, startVertex, vertex);
                }
            }
            
            // In kết quả đường đi kèm khoảng cách
            for (String data : dijkstraAllPaths) {
                result += data + "\n";
            }
            result = result.trim();
            // dijkstraMessage = "The length of the shortest path from " + startVertex + " to " + endVertex + " is "
            // + distance[endVertex] + ": ";
        } else {
            // dijkstraMessage = "Can't find path from " + startVertex + " to " + endVertex + "!";
        }
        // System.out.println(dijkstraMessage);
        
        // Final Path
//        for (String data : dijkstraPath) {
//            result += data + ": " + distance[endVertex] + "\n";
//        }
//        result = result.trim();
            
    }

    public void Dijkstra_displayPath(String path, int currentVertex, int startVertex, int endVertex) {
        if (currentVertex != endVertex) {
            path = currentVertex + "->" + path;
        }
        if (currentVertex == startVertex) {
            dijkstraPath.add(path);
            
            // Thêm đường đi vào danh sách kết quả kèm khoảng cách
            dijkstraAllPaths.add(path + ": " + distance[endVertex]);
        } else {
            for (int i = 0; i < dijkstra_theVertexBefore[currentVertex].size(); i++) {
                Dijkstra_displayPath(path, dijkstra_theVertexBefore[currentVertex].get(i), startVertex, endVertex);
            }
        }
    }
    
    /** ---------------------------- Cut Vertex ---------------------------- */
    
    public void resetCutVertex() {
        Arrays.fill(parent, -1);
        resetResult();
    }
    
        // Hàm tìm tất cả cut vertices
    public void findCutVertices() {
        resetCutVertex();
        
        for (int i = 0; i < NumberOfVertices; i++) {
            if (!isVisited[i]) {
                dfsCutVertex(i);
            }
        }

        List<Integer> cutVertex = new ArrayList<>();
        for (int i = 0; i < NumberOfVertices; i++) {
            if (isCutVertex[i]) {
                cutVertex.add(i);
            }
        }
        
        // Print reult
        result += cutVertex.size() + "\n";
        for (Integer vertex : cutVertex) {
            if(checkResult) {
                result += vertex;
                checkResult = false;
            } else result += "," + vertex;
        }
    }
    
    // Hàm DFS để tìm cut vertex
    private void dfsCutVertex(int u) {
        isVisited[u] = true;
        discoveryTime[u] = low[u] = ++time;
        int children = 0; // Đếm số lượng con trực tiếp của u

        for (int v = 0; v < NumberOfVertices; v++) {
            if (graph[u][v] == 1) { // Nếu u và v kết nối
                if (!isVisited[v]) {
                    children++;
                    parent[v] = u;
                    dfsCutVertex(v);

                    // Kiểm tra điều kiện để u là đỉnh cắt
                    low[u] = Math.min(low[u], low[v]);

                    // (1) Nếu u là root và có ít nhất 2 con
                    if (parent[u] == -1 && children > 1) {
                        isCutVertex[u] = true;
                    }

                    // (2) Nếu u không phải là root và low của con lớn hơn hoặc bằng discovery time của u
                    if (parent[u] != -1 && low[v] >= discoveryTime[u]) {
                        isCutVertex[u] = true;
                    }
                } else if (v != parent[u]) {
                    // Cập nhật low[u] khi có một back edge
                    low[u] = Math.min(low[u], discoveryTime[v]);
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
