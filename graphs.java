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

*/

import java.util.Scanner;

public class graphs {

    public int[][] takeInput() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of vertices: ");
        int n = sc.nextInt();
        System.out.println("Enter the number of edges: ");
        int e = sc.nextInt();

        // create a 2D array
        int[][] edges = new int[n][n];

        for (int i = 0; i < e; i++) {
            int firstVertex = sc.nextInt();
            int secondVertex = sc.nextInt();

            edges[firstVertex][secondVertex] = 1;
            edges[secondVertex][firstVertex] = 1;
        }

        sc.close();
        return edges;
    }


}
