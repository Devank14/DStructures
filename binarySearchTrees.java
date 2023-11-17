import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class binarySearchTrees {
    
    //Inspired from Binary Search Algorithm.
    //The tree is sorted. 
    //For any node n: everything in left subtree is less than n and 
    //anything in right subtree is greater than n.

    public static void main(String args[]){
        binarySearchTrees bst = new binarySearchTrees();
        //12 10 14 8 11 13 15 -1 -1 -1 -1 -1 -1 -1 -1
        //4 2 7 1 5 6 8 -1 -1 -1 -1 -1 -1 -1 -1
        //24 12 32 8 18 27 39 4 11 -1 -1 25 -1 -1 42 -1 -1 -1 -1 -1 -1 -1 -1
        BinarySearchTreeNode<Integer> root = bst.takeInputLevelwise(new Scanner(System.in));
        System.out.println();
        bst.elementsInRangeK1K2(root, 10, 15);
        System.out.println();
        System.out.println(bst.isBST(root, Integer.MIN_VALUE, Integer.MAX_VALUE));
        //bst.SortedArrayToBST(new int[] {1,2,3,4,5,6,7}, 0, 6);
        bst.constructLinkedList(root);
        ArrayList<Integer> alist = bst.getPath(root, 16);
        for(int value : alist){
            System.out.print(value +"   ");
        }
        System.out.println();
        bst.printLevelWise(root);

        bst.preOrder(root);
        System.out.println();
        bst.preOrderIterative(root);
        System.out.println();
        bst.inOrder(root);
        System.out.println();
        bst.inOrderIterative(root);
        System.out.println();
        bst.postOrder(root);
        System.out.println();
        bst.postOrderInterative(root);
        System.out.println();
        bst.levelOrderTraversal(root);

    }

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

    public BinarySearchTreeNode<Integer> takeInput(Scanner sc){
        System.out.println("Enter the value of the node");
        int data = sc.nextInt();
        BinarySearchTreeNode<Integer> node = new BinarySearchTreeNode<Integer>(data);
        node.leftNode = takeInput(sc);
        node.rightNode = takeInput(sc);
        return node;
    }

    public BinarySearchTreeNode<Integer> takeInputLevelwise(Scanner sc){

        Queue<BinarySearchTreeNode<Integer>> queue = new LinkedList<BinarySearchTreeNode<Integer>>();
        System.out.println("Enter the value of the node: ");
        int nodeData = sc.nextInt();
        if(nodeData == -1) return null;
        BinarySearchTreeNode<Integer> node = new BinarySearchTreeNode<Integer>(nodeData);
        queue.add(node);

        while(!queue.isEmpty()){
            BinarySearchTreeNode<Integer> item = queue.remove();

            System.out.print("Enter the value of the left node of "+item.data+" :");
            int leftData = sc.nextInt();
            BinarySearchTreeNode<Integer> left =  (leftData == -1) ? null : new BinarySearchTreeNode<Integer>(leftData);

            if(left != null){
                item.leftNode = left;
                queue.add(left);
            }

            System.out.print("Enter the value of the right node of "+item.data+" :");
            int rightData = sc.nextInt();
            BinarySearchTreeNode<Integer> right =  (rightData == -1) ? null : new BinarySearchTreeNode<Integer>(rightData);

            if(right != null){
                item.rightNode = right;
                queue.add(right);
            }
        }
        return node;
    }

    public void printLevelWise(BinarySearchTreeNode<Integer> node){
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

    public boolean searchInBST(BinarySearchTreeNode<Integer> node, int k){
        
        if(node == null) return false;
        if(node.data == k){
            return true;
        }
        boolean val = false;

        if(node.data > k){
            val = searchInBST(node.leftNode, k);
        }else if(node.data < k){
            val = searchInBST(node.rightNode, k);
        }
        return val;
    }

    public void elementsInRangeK1K2(BinarySearchTreeNode<Integer> node,int k1,int k2){

        if(node == null) return;

        if(node.data > k2){
            elementsInRangeK1K2(node.leftNode, k1, k2);
        }else if(node.data < k1){
            elementsInRangeK1K2(node.rightNode, k1, k2);
        }else{
            elementsInRangeK1K2(node.leftNode, k1, k2);
            System.out.print(node.data+" ");
            elementsInRangeK1K2(node.rightNode, k1, k2);
        }

    }

    public boolean isBST(BinarySearchTreeNode<Integer> node, int min, int max){

        if(node == null) return true;

        if(node.data < min || node.data >= max){
            return false;
        }

        boolean left = isBST(node.leftNode, min, node.data);
        boolean right = isBST(node.rightNode, node.data, max);

        return left && right;

    }

    public BinarySearchTreeNode<Integer> SortedArrayToBST(int[] arr, int start, int end){
        
        if(start > end){
            return null;
        }
        int mid = (start + end)/2;
        BinarySearchTreeNode<Integer> node = new BinarySearchTreeNode<Integer>(arr[mid]);
        node.leftNode = SortedArrayToBST(arr, start, mid-1);
        node.rightNode = SortedArrayToBST(arr, mid+1, end);

        return node;
    }

    public LinkedListNode<Integer> constructLinkedList(BinarySearchTreeNode<Integer> node){
        //This does not work, fix it
        if (node == null)
            return null;
        LinkedListNode<Integer> mid = new LinkedListNode<Integer>(node.data);
        LinkedListNode<Integer> lNode = constructLinkedList(node.leftNode);
        LinkedListNode<Integer> rNode = constructLinkedList(node.rightNode);
        if (lNode != null) {
            lNode.next = mid;
            mid.next = rNode;
        }
        return lNode;
    }

    class LinkedListNode<T>{
        T data;
        LinkedListNode<T> next;
        LinkedListNode(T data){
            this.data = data;
        }
    }

    public ArrayList<Integer> getPath(BinarySearchTreeNode<Integer> node, int data){

        if(node == null) return null;

        ArrayList<Integer> output = new ArrayList<>();
        if(node.data == data){
            output.add(node.data);
            return output;
        }

        if(node.data > data){
            ArrayList<Integer> left = new ArrayList<>();
            left = getPath(node.leftNode, data);
            if(left != null && !left.isEmpty()){
                left.add(node.data);
                output.addAll(left);
            }
        }else if(node.data < data){
            ArrayList<Integer> right = new ArrayList<>();
            right = getPath(node.rightNode, data);
            if(right != null && !right.isEmpty()){
                right.add(node.data);
                output.addAll(right);
            }
        }
        return output;
    }

    /* 
    Tree Traversals - Both Recursive and Interative
    inOrder - Left-Root-Right
    preOrder - Root-Left-Right
    postOrder - Left-Right-Root
    */

    public void inOrder(BinarySearchTreeNode<Integer> node){

        if(node == null) return;
        inOrder(node.leftNode);
        System.out.print(node.data+" ");
        inOrder(node.rightNode);
    }

    public void inOrderIterative(BinarySearchTreeNode<Integer> node){
        //Non-Recursive Approach
        //Left-Root-Right

        Stack<BinarySearchTreeNode<Integer>> stack = new Stack<BinarySearchTreeNode<Integer>>();
        BinarySearchTreeNode<Integer> currentNode = node;
        boolean done = false;

        while(!done){
            if(currentNode != null){
                stack.push(currentNode);
                currentNode = currentNode.leftNode;
            }else{
                if(stack.isEmpty()) done = true;
                else{
                    currentNode = stack.pop();
                    System.out.print(currentNode.data+" ");
                    currentNode = currentNode.rightNode;
                }
            }           
        }
    }

    public void preOrder(BinarySearchTreeNode<Integer> node){

        if(node == null) return;
        System.out.print(node.data+" ");
        preOrder(node.leftNode);
        preOrder(node.rightNode);
    }

    public void preOrderIterative(BinarySearchTreeNode<Integer> node){
        //Non-Recursive Approach
        //Root-Left-Right
        //Use a stack, push the root, pop one by one, print the popped element, push it's right node, then it's left node
        Stack<BinarySearchTreeNode<Integer>> stack = new Stack<BinarySearchTreeNode<Integer>>();
        stack.push(node);

        while(!stack.isEmpty()){
            BinarySearchTreeNode<Integer> item = stack.pop();
            System.out.print(item.data+" ");

            if(item.rightNode != null)
            stack.push(item.rightNode);

            if(item.leftNode != null)
            stack.push(item.leftNode);
        }
    }

    public void postOrder(BinarySearchTreeNode<Integer> node){
        //Left-Right-Root
        if(node == null) return;
        postOrder(node.leftNode);
        postOrder(node.rightNode);
        System.out.print(node.data+" ");
    }

    public void postOrderInterative(BinarySearchTreeNode<Integer> node){
        /* 
        Non-Recursive Approach
        Left-Right-Root
        Maintain two stacks, one for exploration and second for to print the order.
        The algorithm starts by pushing the root onto one. Then, it enters a loop 
        where it pops nodes from one, pushes them onto two, and pushes their 
        left and right children onto one. This process continues until all nodes 
        have been visited. 
        */

        Stack<BinarySearchTreeNode<Integer>> one = new Stack<BinarySearchTreeNode<Integer>>();
        Stack<BinarySearchTreeNode<Integer>> two = new Stack<BinarySearchTreeNode<Integer>>();
        BinarySearchTreeNode<Integer> currentNode = null;
        one.push(node);

        while(!one.isEmpty()){
            currentNode = one.pop();
            two.push(currentNode);

            if(currentNode.leftNode != null)
            one.push(currentNode.leftNode);

            if(currentNode.rightNode != null)
            one.push(currentNode.rightNode);
        }

        while(!two.isEmpty()){
            System.out.print(two.pop().data+" ");
        }
    }

    public void levelOrderTraversal(BinarySearchTreeNode<Integer> node){
        Queue<BinarySearchTreeNode<Integer>> queue = new LinkedList<BinarySearchTreeNode<Integer>>();
        queue.add(node);
        queue.add(new BinarySearchTreeNode<Integer>(Integer.MIN_VALUE));
        BinarySearchTreeNode<Integer> currentNode = null;

        while(!queue.isEmpty()){
            currentNode = queue.remove();
            
            if(currentNode.data > Integer.MIN_VALUE){
                System.out.print(currentNode.data+" ");
            }else if(currentNode.data == Integer.MIN_VALUE && queue.size() >= 1){
                System.out.println();
                queue.add(new BinarySearchTreeNode<Integer>(Integer.MIN_VALUE));
            }

            if(currentNode.leftNode != null)
            queue.add(currentNode.leftNode);

            if(currentNode.rightNode != null){
                queue.add(currentNode.rightNode);
            }
        }
    }

}
