import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class binaryTrees {

    public static void main(String args[]){
        binaryTrees bt = new binaryTrees();
        //12 14 6 8 10 -1 9 -1 -1 -1 -1 21 17 5 18 -1 -1 -1 -1 -1 13 -1 -1
        //BinaryTreeNode<Integer> root = bt.takeInputRoot(new Scanner(System.in));

        //12 14 6 8 10 21 17 -1 9 -1 -1 5 18 -1 13 -1 -1 -1 -1 -1 -1 -1 -1
        
        BinaryTreeNode<Integer> root = bt.takeInputLevelwise(new Scanner(System.in));
        System.out.println();
        bt.print(root);
        System.out.println(bt.countNodes(root));
        System.out.println(bt.getSum(root));
        System.out.println(bt.isNodePresent(root, 11));
        bt.printLevelWise(root);
        System.out.println(bt.largest(root));
        System.out.println(bt.countNodesGreaterThanX(root, 13));
        System.out.println(bt.numLeafNodes(root));
        bt.printAtDepthK(root, 3);
        System.out.println();
        // bt.replaceWithDepth(root, 0);
        // bt.printLevelWise(root);
        // System.out.println();
        bt.printNodesWithoutSibling(root);
       //bt.removeLeafNodes(root);
    //    System.out.println();
    //    bt.mirrorBinaryTree(root);
    //    bt.printLevelWise(root);
    System.out.println();
    bt.inOrder(root);
    System.out.println();
    bt.preOrder(root);
    //bt.insertDuplicateNodeToLeft(root);
    System.out.println();
    bt.printAllPathsFromRoot(root);
    bt.printAllPathsFromRootWithSumK(root, 36);
    System.out.println();
    bt.printAllPaths(root);
    System.out.println();
    //bt.printNodesAtDistanceK(root, 2);


        // System.out.println(bt.isBalanced(root));
        // System.out.println(bt.diameter(root));
        // ArrayList<Integer> alist = bt.getPathFromRoot(root, 18);
        // for(int value : alist){
        //     System.out.print(value +"   ");
        // }
        // System.out.println();

        System.out.println(bt.getLowestCommonAncestor(root, 8, 21));
        bt.levelOrderTraversal(root);
        System.out.println();
        bt.getPathFromRoot2(root, 13);
        System.out.println();
        System.out.println(bt.minimumDepth(root));
    }

    public class BinaryTreeNode<T> implements Comparable<BinaryTreeNode<T>>{
        T data;
        BinaryTreeNode<T> leftNode;
        BinaryTreeNode<T> rightNode;

        BinaryTreeNode(T data){
            this.data = data;
        }

        @Override
        public int compareTo(BinaryTreeNode<T> o) {
            return Integer.compare((Integer)o.data, (Integer)this.data);
        }
    }

    public BinaryTreeNode<Integer> takeInputRoot(Scanner sc){
        System.out.println("Enter the value of the node: ");
        int data = sc.nextInt();
        BinaryTreeNode<Integer> node = new BinaryTreeNode<Integer>(data);
        System.out.println("Enter the left child of "+node.data+": ");
        int leftData = sc.nextInt();
        BinaryTreeNode<Integer> leftNode = (leftData == -1) ? null : new BinaryTreeNode<Integer>(leftData);
        System.out.println("Enter the right child of "+node.data+": ");
        int rightData = sc.nextInt();
        BinaryTreeNode<Integer> rightNode = (rightData == -1) ? null : new BinaryTreeNode<Integer>(rightData);

        node.leftNode = leftNode;
        node.rightNode = rightNode; 

        takeInput(node.leftNode, sc);
        takeInput(node.rightNode, sc);

        return node;
    }

    public void takeInput(BinaryTreeNode<Integer> node, Scanner sc){

        if(node == null) return;
        System.out.println("Enter the value of the left node of "+node.data+" :");
        int leftData = sc.nextInt();
        BinaryTreeNode<Integer> leftNode = (leftData == -1) ? null : new BinaryTreeNode<Integer>(leftData);

        System.out.println("Enter the value of the right node of "+node.data+" :");
        int rightData = sc.nextInt();
        BinaryTreeNode<Integer> rightNode = (rightData == -1) ? null : new BinaryTreeNode<Integer>(rightData);

        node.leftNode = leftNode;
        node.rightNode = rightNode;

        takeInput(node.leftNode, sc);
        takeInput(node.rightNode, sc);

    }

    public void print(BinaryTreeNode<Integer> node){
        if(node == null) return;
        String sb = node.data+" ";
        if(node.leftNode != null){
            sb += "L: "+node.leftNode.data+" ";
        }
        if(node.rightNode != null){
            sb += "R: "+node.rightNode.data+" ";
        }
        if(sb.contains("L:") ||  sb.contains("R:"))
        System.out.println(sb);
        print(node.leftNode);
        print(node.rightNode);
    }

    public BinaryTreeNode<Integer> takeInputLevelwise(Scanner sc){

        Queue<BinaryTreeNode<Integer>> queue = new LinkedList<BinaryTreeNode<Integer>>();
        System.out.println("Enter the value of the node: ");
        int nodeData = sc.nextInt();
        if(nodeData == -1) return null;
        BinaryTreeNode<Integer> node = new BinaryTreeNode<Integer>(nodeData);
        queue.add(node);

        while(!queue.isEmpty()){
            BinaryTreeNode<Integer> item = queue.remove();

            System.out.print("Enter the value of the left node of "+item.data+" :");
            int leftData = sc.nextInt();
            BinaryTreeNode<Integer> left =  (leftData == -1) ? null : new BinaryTreeNode<Integer>(leftData);

            if(left != null){
                item.leftNode = left;
                queue.add(left);
            }

            System.out.print("Enter the value of the right node of "+item.data+" :");
            int rightData = sc.nextInt();
            BinaryTreeNode<Integer> right =  (rightData == -1) ? null : new BinaryTreeNode<Integer>(rightData);

            if(right != null){
                item.rightNode = right;
                queue.add(right);
            }
        }
        return node;
    }

    public int countNodes(BinaryTreeNode<Integer> root){

        int leftCount =0, rightCount = 0;

        if(root == null) return 0;

        leftCount += countNodes(root.leftNode);
        rightCount += countNodes(root.rightNode);

        return 1+leftCount+rightCount;

    }

    public int getSum(BinaryTreeNode<Integer> root){
        int leftSum =0, rightSum =0;
        if(root == null) return 0;
        leftSum += getSum(root.leftNode);
        rightSum += getSum(root.rightNode);
        return root.data+leftSum+rightSum;
    }

    public boolean isNodePresent(BinaryTreeNode<Integer> node, Integer n) {
        if(node == null) return false;
        if(node.data == n) return true;
        return isNodePresent(node.leftNode, n) || isNodePresent(node.rightNode, n);
    }

    public void printLevelWise(BinaryTreeNode<Integer> node){
        Queue<BinaryTreeNode<Integer>> queue = new LinkedList<BinaryTreeNode<Integer>>();
        queue.add(node);
        
        while(!queue.isEmpty()){
            BinaryTreeNode<Integer> item = queue.remove();
            int leftData = (item.leftNode == null) ? -1 : item.leftNode.data;
            int rightData = (item.rightNode == null) ? -1 : item.rightNode.data;
            System.out.print(item.data+":L:"+leftData+",R:"+rightData);

            if(item.leftNode != null) queue.add(item.leftNode);
            if(item.rightNode != null) queue.add(item.rightNode);
            System.out.println();
        }
    }

    public int largest(BinaryTreeNode<Integer> node){

        if(node == null) return Integer.MIN_VALUE;

        int largest = node.data;
        int leftLargest = largest(node.leftNode);
        int rightLargest = largest(node.rightNode);

        int value = (largest > leftLargest) ? largest : leftLargest;
        return (value > rightLargest) ? value : rightLargest;
    }

    public int countNodesGreaterThanX(BinaryTreeNode<Integer> node, int x){
        int count = 0;

        if(node == null) return 0;
        if(node.data > x) count++;

        int leftCount = countNodesGreaterThanX(node.leftNode, x);
        int rightCount = countNodesGreaterThanX(node.rightNode, x);

        return count+leftCount+rightCount;
    }

    public int heightOfTheTree(BinaryTreeNode<Integer> node){

        if(node == null) return 0;

        int leftheight = heightOfTheTree(node.leftNode);
        int rightheight = heightOfTheTree(node.rightNode);

        return ((leftheight> rightheight)? leftheight : rightheight)+1;
    }

    public int numLeafNodes(BinaryTreeNode<Integer> node){

        if(node == null) return 0;
        if(node.leftNode == null && node.rightNode == null) return 1;

        int leftLeaf = numLeafNodes(node.leftNode);
        int rightLeaf = numLeafNodes(node.rightNode);

        return leftLeaf+rightLeaf;
    }

    public void printAtDepthK(BinaryTreeNode<Integer> node, int k){

        if(node == null) return;
        if(k == 0){
            System.out.print(node.data+"    ");
        }
        printAtDepthK(node.leftNode, k-1);
        printAtDepthK(node.rightNode, k-1);
    }

    public void replaceWithDepth(BinaryTreeNode<Integer> node, int depth){

        if(node == null){
            return;
        }
        node.data = depth;
        replaceWithDepth(node.leftNode, depth+1);
        replaceWithDepth(node.rightNode, depth+1);
    }

    public void printNodesWithoutSibling(BinaryTreeNode<Integer> node){

        if(node == null){
            return;
        }

        if ((node.leftNode != null && node.rightNode == null) || (node.leftNode == null && node.rightNode != null)) {
            if(node.leftNode != null){
                System.out.print(node.leftNode.data+" ");
            }else{
                System.out.print(node.rightNode.data+" ");
            }
        }
        printNodesWithoutSibling(node.leftNode);
        printNodesWithoutSibling(node.rightNode);
    }

    public BinaryTreeNode<Integer> removeLeafNodes(BinaryTreeNode<Integer> node){

        if(node == null) return null;

        if(node.leftNode == null && node.rightNode == null){
            return null;
        }
        node.leftNode = removeLeafNodes(node.leftNode);
        node.rightNode = removeLeafNodes(node.rightNode);

        return node;
    }

  /*
   * Balanced Binary Tree: The height of the left subtree minus the
   * height of the right subtree should be equal to or less than 1
   * This condition should be satisfied at every node.
   * height(left-subtree) - height(right-subtree) <= 1
   * So, at every node get the height of the left sub tree and the height
   * of the right subtree, subtract them and check
   */

   public boolean isBalanced(BinaryTreeNode<Integer> node){

    if(node == null){
        return true;
    }

    int value = Math.abs(height(node.leftNode) - height(node.rightNode));
    if(value > 1) return false;

    boolean left = isBalanced(node.leftNode);
    boolean right = isBalanced(node.rightNode);

    return left && right;
   }

   public int height(BinaryTreeNode<Integer> node) {
       if (node == null)
           return 0;
       int left = height(node.leftNode);
       int right = height(node.rightNode);

       return Math.max(left, right) + 1;
   }

   public class pair<T, V>{
        T one;
        V two;

        pair(){}
        pair(T one, V two){
            this.one = one;
            this.two = two;
        }
    }

    public pair<Integer, Boolean> improvedIsBalanced(BinaryTreeNode<Integer> node){

        //Using the pair class, we will compute both isBalanced and height 
        //at the same time, and will return the object as it is. Without
        //having to work for them individually.
        //one = height
        //two = isBalanced
        
        if(node == null){
            return new pair<Integer, Boolean>(0, true);
        }

        pair<Integer, Boolean> left = improvedIsBalanced(node.leftNode);
        pair<Integer, Boolean> right = improvedIsBalanced(node.rightNode);
        
        pair<Integer, Boolean> p = new pair<Integer, Boolean>();
        p.one = Math.max(left.one, right.one) + 1;
        boolean isBalanced = (Math.abs(left.one - right.one) > 1) ? false : true;
        p.two = isBalanced && left.two && right.two;

        return p;
    }
    
    /*
     * Diameter of a Binary Tree: A diameter in a circle is the
     * distance between two farthest points. Similarly a diameter
     * of a binary tree is the distance between two farthest nodes.
     * Distance means number of edges. Usually these nodes are in
     * different subtrees- that is left and right.In that case, just
     * return the sum of the heights of left subtree and right subtree.
     * But this is not always the case, so many times the farthest nodes
     * lie in the same subtree. So, we have three options that we can 
     * return - left diameter, right diamater or the sum of left subtree 
     * height and right subtree height.
     */

     public int diameter(BinaryTreeNode<Integer> node){
        if(node == null){
            return 0;
        }

        int option1 = height(node.leftNode) + height(node.rightNode);
        int leftDiameter = diameter(node.leftNode);
        int rightDiameter = diameter(node.rightNode);

        return Math.max(option1, Math.max(leftDiameter, rightDiameter));
     }

     public ArrayList<Integer> getPathFromRoot(BinaryTreeNode<Integer> node, int value){

        if(node == null){
            return null;
        }

        if(node.data == value){
            ArrayList<Integer> output = new ArrayList<Integer>();
            output.add(node.data);
            return output;
        }

        ArrayList<Integer> leftOutput = getPathFromRoot(node.leftNode, value);
        if(leftOutput != null){
            leftOutput.add(node.data);
            return leftOutput;
        }

        ArrayList<Integer> rightOutput = getPathFromRoot(node.rightNode, value);
        if(rightOutput != null){
            rightOutput.add(node.data);
            return rightOutput;
        }else{
            return null;
        }

     }

    public BinaryTreeNode<Integer> mirrorBinaryTreeNode(BinaryTreeNode<Integer> node){

        if(node == null){
            return null;
        }

        BinaryTreeNode<Integer> newNode = mirrorBinaryTreeNode(node.rightNode);
        node.rightNode = mirrorBinaryTreeNode(node.leftNode);
        node.leftNode = newNode;

        return node;
    }

    public void preOrder(BinaryTreeNode<Integer> node){
        //Root Left-Child Right-Child

        if(node == null) return;
        System.out.print(node.data +" ");
        preOrder(node.leftNode);
        preOrder(node.rightNode);
    }

    public void postOrder(BinaryTreeNode<Integer> node){
        //Left-Child Right-Child Root

        if(node == null) return;
        postOrder(node.leftNode);
        postOrder(node.rightNode);
        System.out.print(node.data+" ");

    }

    public void inOrder(BinaryTreeNode<Integer> node){

        //Left-Child Root Right-Child

        if(node == null) return;
        inOrder(node.leftNode);
        System.out.print(node.data+" ");
        inOrder(node.rightNode);
    }

    public void insertDuplicateNodeToLeft(BinaryTreeNode<Integer> node){

        if(node == null) return;

        insertDuplicateNodeToLeft(node.leftNode);
        insertDuplicateNodeToLeft(node.rightNode);

        if(node.leftNode == null){
            node.leftNode = new BinaryTreeNode<Integer>(node.data);
        }else {
            BinaryTreeNode<Integer> left = node.leftNode;
            node.leftNode = new BinaryTreeNode<Integer>(node.data);
            node.leftNode.leftNode = left;
        }
    }

    public pair<Integer, Integer> getMinAndMax(BinaryTreeNode<Integer> node){

        //One = minimum value
        //Two = maximum value

        if(node == null) return new pair<Integer, Integer>(Integer.MAX_VALUE, Integer.MIN_VALUE);

        pair<Integer, Integer> p = new pair<Integer, Integer>(node.data, node.data);

        pair<Integer, Integer> leftPair = getMinAndMax(node.leftNode);
        pair<Integer, Integer> rightPair = getMinAndMax(node.rightNode);

        int min = Math.min(p.one, Math.min(leftPair.one, rightPair.one));
        int max = Math.max(p.two, Math.max(leftPair.two, rightPair.two));

        return new pair<Integer, Integer>(min, max);
    }

    public void printAllPathsFromRoot(BinaryTreeNode<Integer> node){
        int[] arr = new int[10];
        printAllPathsFromRoot(node, arr, 0);
    }

    public void printAllPathsFromRoot(BinaryTreeNode<Integer> node, int[] arr, int index){

        if(node == null) return;
        arr[index] = node.data;

        if(node.leftNode == null && node.rightNode == null){
            for(int i =0; i<=index; i++){
                System.out.print(arr[i]+" ");
            }
            System.out.println();
        }

        printAllPathsFromRoot(node.leftNode, arr, index+1);
        printAllPathsFromRoot(node.rightNode, arr, index+1);
    }

    public void printAllPathsFromRootWithSumK(BinaryTreeNode<Integer> node, int k){
        int[] arr = new int[10];
        printAllPathsFromRootWithSumK(node, arr, 0, k);
    }

    public void printAllPathsFromRootWithSumK(BinaryTreeNode<Integer> node, int[] arr, int index, int k) {

        if (node == null)
            return;
        arr[index] = node.data;

        int sum = Arrays.stream(arr, 0, index+1).sum();

        if (node.leftNode == null && node.rightNode == null && sum == k) {
            for (int i = 0; i <= index; i++) {
                System.out.print(arr[i] + " ");
            }
            System.out.println();
        }

        printAllPathsFromRootWithSumK(node.leftNode, arr, index + 1, k);
        printAllPathsFromRootWithSumK(node.rightNode, arr, index + 1, k);
    }

    public void getToTheNode(BinaryTreeNode<Integer> node, int n, BinaryTreeNode<Integer> position){

        if(node.data == n){
            position = node;
            return;
        }
        getToTheNode(node.leftNode, n, position);
        getToTheNode(node.rightNode, n, position);
    }

    public void nodesAtDistanceK(BinaryTreeNode<Integer> node, int k){
        if(node == null) return;
        if(k == 0){
            System.out.println(node.data);
            return;
        }
        nodesAtDistanceK(node.leftNode, k-1);
        nodesAtDistanceK(node.rightNode, k-1);
    }

    public void printAllPaths(BinaryTreeNode<Integer> node){
        Stack<Integer> stack = new Stack<Integer>();
        printAllPaths(node, stack);
    }

    public void printAllPaths(BinaryTreeNode<Integer> node, Stack<Integer> stack){
        // Using Stacks
        if(node == null) return;

        stack.push(node.data);

        if(node.leftNode == null && node.rightNode == null){
            for(Integer a : stack){
                System.out.print(a+" ");
            }
            System.out.println();
        }

        printAllPaths(node.leftNode, stack);
        printAllPaths(node.rightNode, stack);
        stack.pop();
    }

    public void printNodesAtDistanceK(BinaryTreeNode<Integer> current, int targetNodeData, int k){

        if(current.data == targetNodeData){
            //We have reached home, we just have to bring nodes which are at a distnace k in the SUBTREE.
            printNodesAtDistanceKinSubTree(current, k);
        }else{

        }
    }

    public void printNodesAtDistanceKinSubTree(BinaryTreeNode<Integer> node, int k){
        if(node == null) return;
        if(k == 0){
            System.out.println(node.data);
        }
        printNodesAtDistanceKinSubTree(node.leftNode, k-1);
        printNodesAtDistanceKinSubTree(node.rightNode, k-1);
    }

    public int distanceBetNodes(BinaryTreeNode<Integer> root, BinaryTreeNode<Integer> one, BinaryTreeNode<Integer> two){

       /*  
        To calculate distance between two nodes in a Binary Tree:
        Distance(n1, n2) = Distance(root, n1) + Distance(root, n2) - 2*Distance(root, LCA)
        LCA is the lowest Common ancestor of the two nodes
        */

        //First find out the LCA of the two nodes.
        return 0;

    }

    public Integer getLowestCommonAncestor(BinaryTreeNode<Integer> node, int one, int two){

        /* Get the paths from the root to the nodes in two different  stacks
        pop the stacks one by one -> if same value appears that means it is 
        the lowest common ancestor of both the nodes. This would be quite easier in BST
         */

        Stack<Integer> oneS = getPath(node, one);
        Stack<Integer> twoS = getPath(node, two);

        while(!oneS.isEmpty() && !twoS.isEmpty()){
            int firstPop = oneS.pop();
            int secondPop = twoS.pop();

            if(firstPop == secondPop) return firstPop;
        }
        return null;
    }

    public Stack<Integer> getPath(BinaryTreeNode<Integer> node, int data){
         Stack<Integer> stack = new Stack<Integer>();
         getPath(node, data, stack);
         return stack;
    }

    public void getPath(BinaryTreeNode<Integer> node, int data, Stack<Integer> stack){
        if(node == null) return;

        stack.push(node.data);

        if(node.data == data){
            return;    
        }

        getPath(node.leftNode, data, stack);
        getPath(node.rightNode, data, stack);
        if(stack.peek() == data) return;
        stack.pop();
    }

    public void levelOrderTraversal(BinaryTreeNode<Integer> node){

        Queue<BinaryTreeNode<Integer>> queue = new LinkedList<BinaryTreeNode<Integer>>();
        levelOrderTraversal(node, queue);
    }

    public void levelOrderTraversal(BinaryTreeNode<Integer> node, Queue<BinaryTreeNode<Integer>> queue){

        queue.add(node);
        queue.add(new BinaryTreeNode<Integer>(Integer.MIN_VALUE));

        while(!queue.isEmpty()){

            BinaryTreeNode<Integer> item = queue.remove();

            if(item.data == Integer.MIN_VALUE && queue.size() >= 1){
                System.out.println();
                item = queue.remove();
                queue.add(new BinaryTreeNode<Integer>(Integer.MIN_VALUE));
            }

            if(item.data > Integer.MIN_VALUE)
            System.out.print(item.data+" ");

            if(item.leftNode != null)
            queue.add(item.leftNode);

            if(item.rightNode != null)
            queue.add(item.rightNode);
        }
    }

    public void getPathFromRoot2 (BinaryTreeNode<Integer> node, int data){

        Stack<Integer> stack = new Stack<Integer>();
        getPathFromRoot2(node, data, stack);
    }

    public void getPathFromRoot2(BinaryTreeNode<Integer> node, int data, Stack<Integer> stack){

        //Using stacks
        //First we push the node, then we push the left node, then the right node
        //Only after that we pop - which means both the left and right have been pushed

        if(node == null) return;

        stack.push(node.data);

        if(stack.peek() == data){
            while(!stack.isEmpty()){
                System.out.print(stack.pop()+" ");
            }
            return;
        }
        getPathFromRoot2(node.leftNode, data, stack);
        getPathFromRoot2(node.rightNode, data, stack);
        if(stack.size() > 0) stack.pop();

    }

    public int minimumDepth(BinaryTreeNode<Integer> node){

        if(node == null) return 0;

        int leftDepth = minimumDepth(node.leftNode) + 1;
        int rightDepth = minimumDepth(node.rightNode) + 1;

        return Math.min(leftDepth, rightDepth);
    }

}
