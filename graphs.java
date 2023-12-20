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
*/

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class graphs {

    public static void main(String args[]) {
        graphs gph = new graphs();
        int[][] edges = gph.takeInput();
        gph.dfs(edges);
        System.out.println();
        gph.breadthFirstSearch(edges);
        System.out.println();
        // gph.hasPath(edges, 0, 3);
        // System.out.println();
        // gph.getPath(edges, 0, 3);
        // gph.getPathBFS(edges, 0, 3);
        System.out.println(gph.isConnected(edges));
        gph.allConnectedComponents(edges);
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
    // 2. Union Find Algorithm: What we will do here is, initailly consider every
    // vertex to be in it's own disjoint set which is independent. Such that every 
    // vertex will be its own parent. After adding an edge, we will make one of the 
    // vertex the parent, then whenever we will choose another vertex, we will check
    // if 

}
