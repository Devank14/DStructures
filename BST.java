import java.util.LinkedList;
import java.util.Queue;

public class BST {

    public class BinarySearchTreeNode<T> implements Comparable<BinarySearchTreeNode<T>>{
        T data;
        BinarySearchTreeNode<T> leftNode;
        BinarySearchTreeNode<T> rightNode;

        BinarySearchTreeNode(){}
        BinarySearchTreeNode(T data){
            this.data = data;
        }

        @Override
        public int compareTo(BinarySearchTreeNode<T> o) {
            return Integer.compare((Integer)o.data, (Integer)this.data);
        }
    }

    public static void main(String args[]){
        BST bst = new BST();
        bst.insert(15);
        bst.insert(10);
        bst.insert(5);
        bst.insert(9);
        bst.insert(18);
        bst.insert(29);
        bst.insert(3);
        bst.insert(2);
        bst.printTree();
    }

    BinarySearchTreeNode<Integer> root = null;

    BST() {}

    public void insert(int data) {
        root = insert(root, data);
    }

    public BinarySearchTreeNode<Integer> insert(BinarySearchTreeNode<Integer> node, int data) {
        if (node == null) {
            node = new BinarySearchTreeNode<Integer>(data);
            return node;
        }
        if (node.data > data) {
            node.leftNode = insert(node.leftNode, data);
        } else {
            node.rightNode = insert(node.rightNode, data);
        }
        return node;
    }

    public void remove(int data) {
        root = remove(root,data);
    }

    public BinarySearchTreeNode<Integer> remove(BinarySearchTreeNode<Integer> node, int data){

        if(node == null) return null;

        if(node.data > data){
            node.leftNode = remove(node.leftNode, data);
            return node;
        }else if (node.data < data){
            node.rightNode = remove(node.rightNode, data);
            return node;
        }else {
            if(node.leftNode == null && node.rightNode == null){
                //Leaf Node
                return null;
            } else if(node.leftNode == null || node.rightNode == null){
                //Just one child
                if(node.leftNode == null){
                    return node.rightNode;
                }else{
                    return node.leftNode;
                }
            }else{
                //Has both children
                //Either take the maximum from the left subtree (inplace predecessor) 
                //or the minimum from the right subtree (inplace successor)

                BinarySearchTreeNode<Integer> minNode = node.rightNode;
                while(minNode.leftNode != null){
                    minNode = minNode.leftNode; 
                }
                node.data = minNode.data;
                remove(node.rightNode, minNode.data);
                return node;
            }
        }
    }


    public void printTree() {
        print(root);
    }

    public void print(BinarySearchTreeNode<Integer> node){
        Queue<BinarySearchTreeNode<Integer>> queue = new LinkedList<BinarySearchTreeNode<Integer>>();
        queue.add(node);
        
        while(!queue.isEmpty()){
            BinarySearchTreeNode<Integer> item = queue.remove();
            int leftData = (item.leftNode == null) ? -1 : item.leftNode.data;
            int rightData = (item.rightNode == null) ? -1 : item.rightNode.data;
            System.out.print(item.data+":L:"+leftData+",R:"+rightData);

            if(item.leftNode != null) queue.add(item.leftNode);
            if(item.rightNode != null) queue.add(item.rightNode);
            System.out.println();
        }
    }

    // public boolean search(int data){

    // }
}