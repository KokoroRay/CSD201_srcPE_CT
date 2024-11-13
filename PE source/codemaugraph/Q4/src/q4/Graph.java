package q4;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

public class Graph {

    ArrayList<Vertex> vertices;
    boolean isDirected;
    int startingVertex;
    int totalWeight = 0;
    int[][] graph;
    int numberOfVertices;
    String path = "";
    String circle = "";
    String result = "";

    static final int MAX_VERTICES = 20;
//    String fileNameInput = "C:\\Users\\Hi\\IdeaProjects\\CSD Lab\\src\\graph_labs\\Eulerian_4\\bfs_input.txt";
//    String fileNameOutput = "C:\\Users\\Hi\\IdeaProjects\\CSD Lab\\src\\graph_labs\\Eulerian_4\\bfs_output.txt";

    public Graph(boolean isDirected) {
        this.isDirected = isDirected;
        vertices = new ArrayList<>();
        graph = new int[MAX_VERTICES][MAX_VERTICES];
        for (int i = 0; i < MAX_VERTICES; i++) {
            for (int j = 0; j < MAX_VERTICES; j++) {
                graph[i][j] = 0;
            }
        }
    }

    private boolean isConnectedGraph() {
        for (int i = 0; i < numberOfVertices; i++) {
            int count = 0;
            for (int j = 0; j < numberOfVertices; j++) {
                if (graph[i][j] == 0) {
                    count++;
                }
                if (count == numberOfVertices) {
                    return false;
                }
            }
        }
        return true;
    }

    public void findPath() {
        this.path = "";
        this.circle = "";
        ArrayList<Integer> numEdgesOfEachVertex = new ArrayList<>();

        for (int i = 0; i < numberOfVertices; i++) {
            numEdgesOfEachVertex.add(accumulate(graph[i], 0));
        }

        int numOfOdd = 0;
        for (int i = numberOfVertices - 1; i >= 0; i--) {
            if (numEdgesOfEachVertex.get(i) % 2 == 1) {
                numOfOdd++;
                startingVertex = i;
            }
        }

        if (numOfOdd > 2) {
            circle = "No eulerian cycle!";
            path = "No eulerian path!";
            return;
        }
        Stack<Integer> stack = new Stack<>();
        int cur = startingVertex;
        while (!stack.isEmpty() || accumulate(graph[cur], 0) != 0) {

            if (accumulate(graph[cur], 0) == 0) {
                path = path + cur + ",";
                cur = stack.pop();

            } else {
                for (int i = 0; i < numberOfVertices; i++) {
                    if (graph[cur][i] == 1) {
                        stack.add(cur);
                        graph[cur][i] = 0;
                        graph[i][cur] = 0;
                        cur = i;
                        break;
                    }
                }
            }
        }
        if (numOfOdd == 2) {
            circle = "No eulerian cycle!";
            path += startingVertex;

        } else {
            circle += path + startingVertex;
            path = path.substring(0, path.length() - 1);

        }
    }

    static int accumulate(int[] arr, int sum) {
        for (int i : arr) {
            sum += i;
        }
        return sum;
    }

    public void readFile(String fileName) {
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                System.out.println("File does not exist!");
                return;
            }
            Scanner reader = new Scanner(file);
            this.numberOfVertices = reader.nextInt();
            this.startingVertex = reader.nextInt();
            for (int i = 0; i < this.numberOfVertices; i++) {
                for (int j = 0; j < numberOfVertices; j++) {
                    graph[i][j] = reader.nextInt();
                }
            }

        } catch (Exception ignored) {

        }
    }

    public String printPath() {
        if (!isConnectedGraph()) {
            result += "No eulerian cycle!\n";
          result +="No eulerian path!";
        } else {
            findPath();
           result +=circle + "\n";
           result +=path;
        }
        return result;
    }

}
