import java.util.HashMap;
import java.util.Map;

public class disjointSets {

    /*
     * Disjoint Sets are sets which do not have anything in common.
     * They have three operations:
     * 1. Makeset(x): Creates a new set containing a single element x.
     * 2. Find(x): Returns the parent of the set containing the element x.
     * 3. Union(x, y): Creates a new set containing the elements of x and y and
     * deletes the set containing the elements x and y.
     * 
     * Rank Based Union: When we perform union, we check whose rank is higher,
     * the higher rank node is made the parent of the other node. If the ranks
     * are equal, then one of the node's rank is incremented by one and it is made
     * the parent. This technique helps in making sure that skew trees are not
     * constructed.
     * 
     * Path Compression: Find operation traverses the list of nodes on its way to
     * the root. When we make each of the vertices directly point to the root then
     * the path is compressed. Resulting in reducing the height of the tree.
     */

    class Node {
        int data;
        int rank; // Helps in making less skew trees.
        Node parent;
    }

    private Map<Integer, Node> map = new HashMap<Integer, Node>();

    public void makeSet(int data) {
        Node node = new Node();
        node.data = data;
        node.parent = node;
        node.rank = 0;
        map.put(node.data, node);
    }

    public int findSet(int data) {
        return findSet(map.get(data)).data;
    }

    private Node findSet(Node node) {

        /*
         * Does path compression, by returning the topmost parent as the parent of all
         * the child nodes. That is we are doing path compression at the time of
         * finding.
         */

        if (node.parent == node) {
            return node;
        }
        node.parent = findSet(node.parent);
        return node.parent;
    }

    public void union(int data1, int data2) {

        Node parent1 = map.get(findSet(data1));
        Node parent2 = map.get(findSet(data2));

        if (parent1.data == parent2.data)
            return;

        if (parent1.rank > parent2.rank) {
            parent2.parent = parent1;
        } else if (parent1.rank < parent2.rank) {
            parent1.parent = parent2;
        } else {
            // Increment the rank by 1
            parent1.rank = +1;
            parent2.parent = parent1;
        }
    }

    public static void main(String args[]) {
        disjointSets ds = new disjointSets();
        ds.makeSet(1);
        ds.makeSet(2);
        ds.makeSet(3);
        ds.makeSet(4);
        ds.makeSet(5);
        ds.makeSet(6);
        ds.makeSet(7);

        ds.union(1, 2);
        ds.union(2, 3);
        ds.union(4, 5);
        ds.union(6, 7);
        ds.union(5, 6);
        ds.union(3, 7);

        System.out.println(ds.findSet(1));
        System.out.println(ds.findSet(2));
        System.out.println(ds.findSet(3));
        System.out.println(ds.findSet(4));
        System.out.println(ds.findSet(5));
        System.out.println(ds.findSet(6));
        System.out.println(ds.findSet(7));
    }
}
