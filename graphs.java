/* 
____________________________________GRAPHS______________________________________

I have been avoiding graphs for a long time. But finally I am ready to devour it.
Let's see if I can retain it when it is actually required.:P
________________________________________________________________________________

Graph is a data structure, where we have got multiple nodes and they are connected by edges.
Trees are also a kind of graphs. All the nodes in a tree are connected, from one node, there 
is always a way to reach any other node. Which is not always true with graphs. 
Trees do not have cycles, they are acyclic. Graphs can be both cyclic and acyclic.
Hence all trees are graphs but not vice-versa.

*****GRAPH TERMINOLOGY*****

Adjacent - Two vertices in a graph are adjacent if they have a direct edge between them.
Degree of vertex - Number of edges going through the vertex. 
Connected graph - For every two vertices, there exists a path between them.
Connected components - Parts of a graph that are connected.
Complete graph - Every vertex is connected to every other vertex.
Tree - Connected graph which does not have a cycle.

For n vertices, 
What is the minimum number of edges? Zero, do not connect anything.
What is the minimum number of edges in a connected graph? (n-1)
What is the minimum number of edges in a complete graph? n*(n-1)/2 = O(n^2) Maximum number of edges.

*****IMPLEMENTATION*****

1. Edge List: Have two lists, one for all the vertices and the other for all the edges. 
              Bad idea, will have to traverse the whole list to know if two vertices are connected. 
              Maximum number of edges = n^2, hence total time will be O(n^2).

2. Adjacency List: An array/list containing all the vertices. Each vertex will point to a list of vertices, which
                   it is adjacent to. O(n) will be taken to tarverse to the vertices.

3. Adjacency Matrix: We will create an N*N matrix. And (i, j) will represent an edge from ith vertex to jth vertex. 
                     But this will take a N*N total space, are we sure to compromise on space complexity?
                     What if, we have a sparse graph (not too many edges), then is it worth it?

*****PRINTING A GRAPH*****

Since we have to record every vertex twice, (i,j) and (j,i), it is very much possible that we get stuck 
in an infinite loop, wherein we are hopping off the two vertices only, while printing adjacent vertices 
to a vertex. So, we have to maintain to record all the vertices we have already visited.
We will ingest a starting vertex.

Depth First Search: Like the pre-order traversal.
Breadth first Search: Level Order traversal.

Directed Graph - Assigning a direction to the edges of the graphs, like one-way road. 
Weighted Graphs - Not all edges are equal. Every edge will have an assigned weight.

*****SPANNING TREES*****

Trees are graphs where in all the vertices are connected and there is no cycle.
Spanning Tree: Given an undirected and connected graph, a spanning tree is a tree cotaining all the vertices.
For a given graph, there can be multiple spanning trees. In a spanning tree we have n number of vertices and n-1 edges.

Minimum Spanning Tree (MST): Graph is connected, undirected and weighted, we want a spanning tree where the weight is minimum.
Two Algorithms are used to find Minimum Spanning Trees: Both are greedy algorithms
1. Kruskal's Algorithm
2. Prim's Algorithm
*/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class graphs {

    public static void main(String args[]) {
        graphs gph = new graphs();
        // int[][] edges = gph.takeInput();
        // gph.dfs(edges);
        // System.out.println();
        // gph.breadthFirstSearch(edges);
        // System.out.println();
        // gph.hasPath(edges, 0, 3);
        // System.out.println();
        // gph.getPath(edges, 0, 3);
        // gph.getPathBFS(edges, 0, 3);
        // System.out.println(gph.isConnected(edges));
        // gph.allConnectedComponents(edges);

        // gph.kruskal();
        //gph.dijkstra();
        gph.floyd_warshall();
    }

    public int[][] takeInput() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of vertices: ");
        int n = sc.nextInt();
        System.out.println("Enter the number of edges: ");
        int e = sc.nextInt();

        // create a 2D array
        int[][] edges = new int[n][n];

        for (int i = 0; i < e; i++) {
            System.out.println("Enter the vertices of edge no. " + i);
            int firstVertex = sc.nextInt();
            int secondVertex = sc.nextInt();

            edges[firstVertex][secondVertex] = 1;
            edges[secondVertex][firstVertex] = 1;
        }

        sc.close();
        return edges;
    }

    public int[][] takeInputWeighted() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of Vertices: ");
        int vertices = sc.nextInt();
        System.out.println("Enter the number of Edges: ");
        int edges = sc.nextInt();
        int[][] graph = new int[vertices][vertices];

        for (int[] arr : graph) {
            Arrays.fill(arr, -1);
        }

        for (int i = 0; i < edges; i++) {
            System.out.println("Enter the vertices and weight of edge: " + i);
            int vertex1 = sc.nextInt();
            int vertex2 = sc.nextInt();
            int weight = sc.nextInt();

            graph[vertex1][vertex2] = weight;
            graph[vertex2][vertex1] = weight;
        }

        sc.close();
        return graph;
    }

    public void dfs(int[][] edges) {
        System.out.print("DFS: ");
        boolean[] visited = new boolean[edges.length];
        for (int i = 0; i < edges.length; i++) {
            if (!visited[i])
                depthFirstSearch(edges, i, visited);
        }
    }

    public void depthFirstSearch(int[][] edges, int sv, boolean[] visited) {
        System.out.print(sv + "   ");
        visited[sv] = true;
        for (int i = 0; i < edges.length; i++) {
            if (edges[sv][i] == 1 && !visited[sv])
                depthFirstSearch(edges, i, visited);
        }
    }

    public void breadthFirstSearch(int[][] edges) {

        boolean[] visited = new boolean[edges.length];
        System.out.print("BFS: ");
        for (int i = 0; i < visited.length; i++) {
            if (!visited[i])
                bfs(edges, visited, i);
        }
    }

    public void bfs(int[][] edges, boolean[] visited, int startingVertex) {

        Queue<Integer> queue = new LinkedList<Integer>();
        queue.add(startingVertex);
        visited[startingVertex] = true;

        while (!queue.isEmpty()) {
            int vertex = queue.remove();
            System.out.print(vertex + " ");

            for (int i = 0; i < edges.length; i++) {
                if (edges[startingVertex][i] == 1 && !visited[i]) {
                    queue.add(i);
                    visited[i] = true;
                }
            }
        }
    }

    /*
     * Given two vertices, we have to check if there exists a path between the two
     * vertices. Return true if either they are adjacent or they are connected via
     * other
     * vertices. Otherwise return false
     */
    public void hasPath(int[][] edges, int v1, int v2) {

        boolean[] visited = new boolean[edges.length];
        System.out.println(hasPathDFS(edges, visited, v1, v2));
    }

    public boolean hasPathDFS(int[][] edges, boolean[] visited, int v1, int v2) {

        visited[v1] = true;

        if (v1 == v2)
            return true;

        for (int i = 0; i < edges.length; i++) {
            if (edges[v1][i] == 1 && !visited[i])
                return hasPathDFS(edges, visited, i, v2);
        }

        return false;
    }

    public void getPath(int[][] edges, int start, int end) {

        boolean[] visited = new boolean[edges.length];
        ArrayList<Integer> path = new ArrayList<Integer>();
        getPathDFS(edges, start, end, visited, path);
        System.out.println(path.toString());
    }

    public void getPathDFS(int[][] edges, int start, int end, boolean[] visited, ArrayList<Integer> path) {

        visited[start] = true;

        if (start == end) {
            path.add(end);
            return;
        }

        for (int i = 0; i < edges.length; i++) {
            if (edges[i][start] == 1 && !visited[i]) {
                getPathDFS(edges, i, end, visited, path);

                if (!path.isEmpty()) {
                    path.add(start);
                    return;
                }
            }
        }
    }

    // When we want to find through BFS - it is clear that we will have to use a
    // queue, which will be responsible for ingesting
    // the visited nodes one by one. But once the queue is filled with the end
    // vertex, how will we track which vertex actually
    // added the end vertex? This can be done via a Map, that is use a Map to track
    // - <V, Who-Added-Vertex-V>
    // Then through this map we will retrace the path.

    public void getPathBFS(int[][] edges, int start, int end) {

        boolean[] visited = new boolean[edges.length];
        Queue<Integer> queue = new LinkedList<Integer>();
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();

        queue.add(start);
        visited[start] = true;
        map.put(start, Integer.MIN_VALUE);

        while (!queue.isEmpty()) {
            int starting = queue.remove();
            if (starting == end)
                break;
            for (int i = 0; i < edges.length; i++) {
                if (edges[starting][i] == 1 && !visited[i]) {
                    queue.add(i);
                    visited[i] = true;
                    map.put(i, starting);
                }
            }
        }

        int ending = end;
        ArrayList<Integer> path = new ArrayList<Integer>();

        while (ending != Integer.MIN_VALUE) {
            if (!map.containsKey(ending))
                break;
            path.add(ending);
            ending = map.get(ending);
        }

        System.out.println(path.toString());
    }

    // Check if the given graph is striongly connected or not
    // Just run DFS starting from any vertex, after that check the visited array,
    // if it conatins false, then the graph is not connected.

    public boolean isConnected(int[][] edges) {
        boolean[] visited = new boolean[edges.length];
        if (visited.length > 1)
            isConnected(edges, 0, visited);

        for (Boolean value : visited) {
            if (value == false)
                return false;
        }

        return true;
    }

    private void isConnected(int[][] edges, int start, boolean[] visited) {
        visited[start] = true;
        for (int i = 0; i < edges.length; i++) {
            if (edges[start][i] == 1 && !visited[i]) {
                isConnected(edges, i, visited);
            }
        }
    }

    // Print all the connected components in a graph.
    public void allConnectedComponents(int[][] edges) {

        ArrayList<ArrayList<Integer>> list = new ArrayList<ArrayList<Integer>>();
        boolean[] visited = new boolean[edges.length];
        for (int i = 0; i < edges.length; i++) {
            if (!visited[i]) {
                ArrayList<Integer> alist = new ArrayList<Integer>();
                list.add(getConnectedComponent(edges, i, visited, alist));
            }
        }
        for (ArrayList<Integer> lusd : list) {
            Collections.sort(lusd);
            for (Integer value : lusd) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }

    private ArrayList<Integer> getConnectedComponent(int[][] edges, int start, boolean[] visited,
            ArrayList<Integer> alist) {

        alist.add(start);
        visited[start] = true;

        for (int i = 0; i < edges.length; i++) {
            if (edges[i][start] == 1 && !visited[i]) {
                getConnectedComponent(edges, i, visited, alist);
            }
        }

        return alist;
    }

    // *********************************
    // ****** KRUSKAL'S ALGORITHM ******
    // *********************************
    //
    // Given a weighted, connected and undirected graph, Kruskal's Algo, helps us to
    // identify the Minimum Spanning Tree (MST). The most important property of MSTs
    // is that if they have n vertices, then they should have n-1 edges. The other
    // thing is that there should not be any cycles in an MST.
    //
    // According to Kruskal's Algorithm
    // Keep a count on the number of vertices, One-By-One pick the
    // least weighted edges whilst making sure that no cycles are formed.
    // All vertices should be included (n) and (n-1) edges.
    //
    // How will we detect a cycle?
    //
    // 1. Before adding any edge, if we check weather a path exists for the
    // vertices. If path exists, then we will not add that edge, otherwise
    // we will. But this will increase the complexity. The hasPath() function
    // will have a complexity of O(V+E). For every edge, we will have to do
    // this work O(V+E), the maximum number of edges possible will be V^2,
    // so the complexity will be O(V+E)*V^2.
    //
    // 2. Union Find Algorithm:
    // * Initially we will assume that all the vertices are in their own
    // separate disjoint sets, which means they are their own parents.
    // * We will pick up the edge with the least weight, and will make
    // either of the vertices as their parent.
    // * As we add more edges, we have to make sure that the top most parent of the
    // vertices is not same, if it same that means there already exists a path
    // between the vertices.

    class Edge implements Comparable<Edge> {
        int source;
        int destination;
        int weight;

        Edge(int source, int destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge o) {
            return this.weight - o.weight;
        }

    }

    public Edge[] takeInput2() {

        // We return an Edge array, which is sorted in ascending order according to
        // weight.
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of edges: ");
        int edge = sc.nextInt();
        Edge[] edges = new Edge[edge];

        for (int i = 0; i < edge; i++) {
            System.out.println("Enter the source vertex of " + i);
            int source = sc.nextInt();
            System.out.println("Enter the destination vertex of " + i);
            int destination = sc.nextInt();
            System.out.println("Enter the weight of " + i);
            int weight = sc.nextInt();

            Edge item = new Edge(source, destination, weight);
            edges[i] = item;
        }

        sc.close();
        Arrays.sort(edges);
        return edges;
    }

    /*
     * Time Complexity of Kruskal's Algorithm
     * We are doing three things:
     * 1. Taking Input: We are taking input for E edges, hence time complexity will
     * be O(E)
     * 2. Sorting the Input array: We sort the edge array - O(ElogE)
     * 3. Cycle Detection using the Union Find Algorithm: For every edge we will
     * have to check all the vertices, in order to detect a cycle,
     * hence time taken will be O(VE)
     * 
     * O(E) + O(ElogE) + O(VE) = O(ElogE) + O(VE)
     * 
     * In worst case we know that there can be V^2 edges
     * 
     * So, is there a way to reduce this complexity? We can work upon the cycle
     * detection mechanism
     * to reduce the time complexity - Union By Rank & Path Compression.
     */

    public void kruskal() {

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of vertices: ");
        int vertices = sc.nextInt();
        Edge[] input = takeInput2();
        sc.close();

        // Instantiate a parent array to keep record of the parent of vertices.
        // Initialize the values to indices.
        int[] parent = new int[vertices];
        for (int i = 0; i < parent.length; i++) {
            parent[i] = i;
        }

        // If we have n vertices, then we should have n-1 edges, in the output array
        Edge[] output = new Edge[vertices - 1];
        int count = 0, edgeIndex = 0;

        while (count != output.length) {
            Edge currentEdge = input[edgeIndex];

            /*
             * CYCLE DETECTION
             * We have to check if there exists a cycle
             * This can be done via the Union Find Algo, wherein we will check
             * the topmost parent of the vertex using the parent array.
             * If their parent is same, it means there already exists a path
             * between the vertices, if not then we have to add it.
             * Understand, that we have a sorted array according to the weight.
             * So, we are picking the least weighted edge first.
             */

            int sourceParent = findTopMostParent(parent, currentEdge.source);
            int destinationParent = findTopMostParent(parent, currentEdge.destination);

            if (sourceParent != destinationParent) {
                output[count] = currentEdge;
                count++;
                parent[sourceParent] = destinationParent;
            }

            edgeIndex++;
        }

        for (int i = 0; i < output.length; i++) {
            System.out.println(output[i].source + " " + output[i].destination + " " + output[i].weight);
        }

    }

    public int findTopMostParent(int[] parent, int index) {
        // Union Find Algorithm
        if (parent[index] == index)
            return index;
        return findTopMostParent(parent, parent[index]);
    }

    /*
     *********************************
     ******* PRIMS'S ALGORITHM *******
     *********************************
     * 
     * This is used to find the Minimum Spanning Tree. Like Kruskal's algo
     * Prim's algo is also a greedy algorithm, that is we choose the best option out
     * of multiple options without keeping track of how it may unfold out later.
     * 
     * In Prim's Algorithm, we keep track of the following:
     * 1. The parent of each vertex (Initially null except the source, which is
     * parent of itself)
     * 2. The weight of each vertex (Initially, infinity except for the source)
     * 3. Keeping track of both visited and unvisited vertices.
     * 
     * One-by-one, we will visit each un-visited vertex:
     * 1. Mark it visited
     * 2. Pick it's neighbours.
     * 3. Assign them distances if their current distance is greater than the
     * distance from the picked up vertex.
     * 4. Set their parent as the picked up vertex.
     * 
     * Soon we will have a minimum spanning tree.
     * 
     * For Prim's Algorithm we will store the graph in an adjacency matrix.
     * We have a weighted, undirected graph.
     */

    public void prim() {
        int[][] edges = takeInputWeighted();
        boolean[] visited = new boolean[edges.length];
        int[] parent = new int[edges.length];

        int[] weights = new int[edges.length];
        // Setting the inital default values.
        weights[0] = 0;
        parent[0] = -1;
        for (int i = 1; i < weights.length; i++) {
            weights[i] = Integer.MAX_VALUE;
        }

        for (int i = 0; i < edges.length; i++) {

            // Pick the unvisited vertex with minimum weight
            int index = findMinVertex(weights, visited);
            visited[index] = true;

            // Pick the unvisited neighbours and update the distance, parent
            for (int j = 0; j < edges.length; j++) {
                if (edges[index][j] != 0 && !visited[j]) {
                    weights[j] = edges[index][j];
                    parent[j] = index;
                }
            }
        }

        // Just print it
        for (int i = 0; i < edges.length; i++) {
            if (parent[i] > i) {
                System.out.println(i + "  " + parent[i] + " " + weights[i]);
            } else {
                System.out.println(parent[i] + "  " + i + " " + weights[i]);
            }
        }

    }

    public int findMinVertex(int[] weights, boolean[] visited) {

        int minIndex = -1;
        for (int i = 0; i < weights.length; i++) {
            if (!visited[i] && (minIndex == -1 || weights[i] < weights[minIndex])) {
                minIndex = i;
            }
        }
        return minIndex;
    }

    /*
     * Time Complexity of Prim's Algorithm
     * The time complexity of Prim's Algorithm is dependent upon two things:
     * 1. The type of structure used to store the graph.
     * 2. How we compute the minimum vertex.
     * 
     * If we use the adjacency matrix, that means we will have to go to every vertex
     * to check if they are neighbouring. That means - O(V^2) work.
     * In case if we store it in the form of Adjacency list it will be O(V+E)
     * 
     * To compute the min vertex - if we use simple loop - that will take O(V)
     * If we use Min Heap - that will take log(V)
     * 
     * So the total time will be: (V+E)logV if we use adjacency matrix and heap
     */

    /*
     *********************************
     ****** DIJKSTRA'S ALGORITHM *****
     *********************************
     * 
     * Used to find the shortest distance from the source vertex to all vertices.
     * From the source vertex, keep a track of distances to all the vertices,
     * initially it will be infinity. Then step by step we will choose the nearest
     * neighbours and update their distances. We will do this untill all the
     * vertices have been visited.
     * 
     * Time Complexity of Dijkstra: O(V^2)
     * Better after using Min Heaps and Adjacency list - (V+E)logV
     */

    public void dijkstra() {

        int[][] edges = takeInputWeighted();
        int[] distance = new int[edges.length];
        boolean[] visited = new boolean[edges.length];

        distance[0] = 0;
        for (int i = 1; i < distance.length; i++) {
            distance[i] = Integer.MAX_VALUE;
        }

        int i = 0;
        while (i != edges.length - 1) {

            int minIndex = findMinVertex(distance, visited);
            visited[minIndex] = true;
            for (int j = 0; j < edges.length; j++) {
                if (edges[minIndex][j] != -1 && !visited[j]) {
                    if (edges[minIndex][j] + distance[minIndex] < distance[j])
                        distance[j] = edges[minIndex][j] + distance[minIndex];
                }
            }
            i++;
        }

        for (int j = 0; j < distance.length; j++) {
            System.out.println(j + " " + distance[j]);
        }
    }

    /*
     *********************************
     *** FLOYD WARSHALL'S ALGORITHM **
     *********************************
     * 
     * Floyd warshall algorithm makes use of Dynamic Programming.
     * It is used to find the minimum distance from one vertex to another, just like
     * Dijkstra. But in FW, we are able to compute shortest distance from every
     * vertex to every other vertex. The same result can be achieved via Dijkstra,
     * only if we run Dijkstra on every vertex. So, if Dijkstra take O(v^2) then
     * running it on v vertices will result in O(v^3) complexity.
     * 
     * Dijkstra: Shortest path from one node to all nodes
     * Bellman Ford: Shortest path from one node to all nodes, negative edges
     * allowed
     * Floyd Warshall: Shortest path between all pairs of vertices, negative edges
     * allowed.
     */

    public int[][] takeInputFloyd_Warshall() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of Vertices: ");
        int vertices = sc.nextInt();
        System.out.println("Enter the number of Edges: ");
        int edges = sc.nextInt();
        int[][] graph = new int[vertices][vertices];

        for (int[] arr : graph) {
            Arrays.fill(arr, 9999);
        }

        for (int i = 0; i < edges; i++) {
            System.out.println("Enter the vertices and weight of edge: " + i);
            int vertex1 = sc.nextInt();
            int vertex2 = sc.nextInt();
            int weight = sc.nextInt();

            graph[vertex1][vertex2] = weight;
        }

        sc.close();
        return graph;
    }

    public void floyd_warshall() {
        int[][] output = takeInputFloyd_Warshall();

        for (int i = 0; i < output.length; i++) {
            output[i][i] = 0;
        }

        for (int k = 0; k < output.length; k++) {
            for (int i = 0; i < output.length; i++) {
                for (int j = 0; j < output.length; j++) {

                    if(output[i][j] > output[i][k] + output[k][j]){
                        output[i][j] = output[i][k] + output[k][j];
                    }
                }
            }
        }

        for(int i = 0; i < output.length; i++){
            for(int j =0; j < output[0].length; j++){
                System.out.print(output[i][j]+" ");
            }
            System.out.println();
        }
    }
}
