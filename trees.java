import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

public class trees {

    public class TreeNode<T> implements Comparable<TreeNode<T>> {
        public T data;
        public ArrayList<TreeNode<T>> children;

        public TreeNode(T data) {
            this.data = data;
            this.children = new ArrayList<TreeNode<T>>();
        }

        @Override
        public int compareTo(trees.TreeNode<T> o) {
            return Integer.compare((Integer)o.data, (Integer)this.data);
        }
    }

    public static void main(String args[]) {
        trees tree = new trees();
        // 10 4 9 2 5 0 4 0 8 1 3 0 7 2 2 0 1 0 6 0
        // TreeNode<Integer> root = tree.takeInputRoot();
        // tree.printTree(root);

        // 10 4 9 8 7 6 2 5 4 1 3 2 2 1 0 0 0 0 0 0 
        TreeNode<Integer> root = tree.inputLevelWise();
        tree.printLevelWise(root);
        System.out.println();
        System.out.println(tree.numberOfNodes(root));
        System.out.println(tree.sumOfNodes(root));
        System.out.println(tree.largest(root));
        System.out.println(tree.noOfNodesGreaterThanX(root, 10));
        System.out.println(tree.getHeight(root));
        tree.printNodesAtDepthK(root, 1);
        System.out.println();
        System.out.println(tree.countLeafNodes(root));
        tree.preOrderTraversal(root);
        System.out.println();
        tree.postOrderTraversal(root);
        System.out.println();
        System.out.println(tree.maxSumNode(root));
        System.out.println(tree.justNextLargerNode(root, 7).data);
        System.out.println(tree.findSecondLargest(root).secondLargest.data);
        tree.replaceNodeWithDepth(root, 0);
        tree.printLevelWise(root);

    }

    public TreeNode<Integer> takeInputRoot() {
        //Recursive
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the value of the root node: ");
        TreeNode<Integer> root = new TreeNode<Integer>(sc.nextInt());
        System.out.println("Enter the number of children of the root node: ");
        int numOfChildren = sc.nextInt();
        for (int i = 0; i < numOfChildren; i++) {
            TreeNode<Integer> child = takeInputChild(sc, root.data, i);
            root.children.add(child);
        }
        return root;
    }

    public TreeNode<Integer> takeInputChild(Scanner sc, int rootData, int number) {
        //Recursive
        System.out.println("Enter the value of " + (number + 1) + " child of " + rootData);
        TreeNode<Integer> child = new TreeNode<Integer>(sc.nextInt());
        System.out.println("Enter the number of children of " + child.data);
        int numOfChildren = sc.nextInt();
        for (int i = 0; i < numOfChildren; i++) {
            TreeNode<Integer> childOfChild = takeInputChild(sc, child.data, i);
            child.children.add(childOfChild);
        }
        return child;
    }

    public <T> void printTree(TreeNode<T> node) {
        //recursive
        if (node.children.size() > 0) {
            System.out.print(node.data + " ");
            for (int i = 0; i < node.children.size(); i++) {
                System.out.print(node.children.get(i).data + " ");
            }
            System.out.println();
        }
        for (int i = 0; i < node.children.size(); i++) {
            printTree(node.children.get(i));
        }
    }

    public TreeNode<Integer> inputLevelWise() {

        //Add a node (starting with root) to a queue, dequeue the element
        //Ask for the number of children of the dequeued element and enqueue them
        //Again dequeue and enqueue till it is empty

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the value of the root: ");
        int data = sc.nextInt();
        TreeNode<Integer> root = new TreeNode<Integer>(data);
        Queue<TreeNode<Integer>> q = new PriorityQueue<TreeNode<Integer>>();
        q.add(root);

        while (!q.isEmpty()) {
            TreeNode<Integer> node = q.remove();
            System.out.println("Enter the number of children of "+ node.data);
            int numOfChildren = sc.nextInt();
            for (int i = 0; i < numOfChildren; i++) {
                System.out.println("Enter the data of "+ (i+1)+ " child");
                int childData = sc.nextInt();
                TreeNode<Integer> childNode = new TreeNode<Integer>(childData);
                node.children.add(childNode);
                q.add(childNode);
            }
        }
        return root;
    }

    public void printLevelWise(TreeNode<Integer> root) {
        Queue<TreeNode<Integer>> q = new LinkedList<TreeNode<Integer>>();
        TreeNode<Integer> negative = new TreeNode<Integer>(Integer.MIN_VALUE);
        q.add(root);
        q.add(negative);
        while (q.size() > 1) {
            TreeNode<Integer> node = q.remove();
            if (node.data != Integer.MIN_VALUE) {
                System.out.print(node.data + " ");
                q.addAll(node.children);
            } else {
                System.out.println();
                q.add(negative);
            }
        }
    }

    public int numberOfNodes(TreeNode<Integer> node){
        int value = 1;
        for(int i =0; i<node.children.size(); i++){
            value = value + numberOfNodes(node.children.get(i));
        }
        return value;
    }

    public int sumOfNodes(TreeNode<Integer> node){
        int sum = node.data;
        for(int i =0; i< node.children.size(); i++){
            sum = sum + sumOfNodes(node.children.get(i));
        }
        return sum;
    }

    public int largest(TreeNode<Integer> node){
        int value = node.data;
        for(int i =0; i< node.children.size(); i++){
            int childLarge = largest(node.children.get(i));
            if(value < childLarge){
                value = childLarge;
            }
        }
        return value;
    }

    public int noOfNodesGreaterThanX(TreeNode<Integer> node, int value){
        int total = 0;
        if(node.data > value){
             total++;
        }
        for(int i =0; i<node.children.size(); i++){
            total = total + noOfNodesGreaterThanX(node.children.get(i), value);
        }
        return total;
    }

    public int getHeight(TreeNode<Integer> node){
        int max = 0;
        for(TreeNode<Integer> child : node.children){
            int height = 0;
            height = height + getHeight(child);
            
            if(max < height){
                max = height;
            }
        }
        return max+1;
    }

    public void printNodesAtDepthK(TreeNode<Integer> node, int k){

        if(k == 0){
            System.out.print(node.data+"    ");
        }
        for(TreeNode<Integer> child : node.children){
            printNodesAtDepthK(child, k-1);
        }
    }

    public int countLeafNodes2(TreeNode<Integer> node){
        int count =0;
        if(node.children.size() == 0)
            return 1;

        for(int i =0; i< node.children.size(); i++){
            count = count + countLeafNodes(node.children.get(i));
        }
        return count;
    }

    // Printing the value of the node before printing children's value.
    public void preOrderTraversal(TreeNode<Integer> node){

        System.out.print(node.data +" ");
        for(TreeNode<Integer> child : node.children){
            preOrderTraversal(child);
        }
    }

    public void postOrderTraversal(TreeNode<Integer> node){

        for(TreeNode<Integer> child : node.children){
            postOrderTraversal(child);
        }
        System.out.print(node.data+" ");
    }

    public boolean checkifContainsX(TreeNode<Integer> node, int x){
        if(node == null) return false;
        if(node.data == x){
            return true;
        }
        for(TreeNode<Integer> child : node.children){
          boolean value =  checkifContainsX(child, x);
          if(value) return value;
        }

        return false;
    }

    //Given a tree, find and return the node for which sum of data of all
    //children and the node itself is maximum. In the sum, the data of the 
    //node itself and of immediate children is to be taken

    public int maxSumNode(TreeNode<Integer> node) {
        int max = 0;
        for (int i = 0; i < node.children.size(); i++) {
            int sum = node.data;
            for (TreeNode<Integer> child : node.children) {
                sum += child.data;
            }
            if (max < sum) max = sum;

            int newMax = maxSumNode(node.children.get(i));
            if(newMax> max){
                max = newMax;
            }
        }
        return max;
    }

    public boolean checkIdentical(TreeNode<Integer> root1, TreeNode<Integer> root2){

		if(root1 == null && root2 == null){
			return true;
		}
		if(root1.data != root2.data){
			return false;
		}
		if(root1.children.size() != root2.children.size()){
			return false;
		}
		for(int i =0; i< root1.children.size(); i++){
			boolean value = checkIdentical(root1.children.get(i), root2.children.get(i));
			if(!value) return value;
		}
		return true;
	}

    public TreeNode<Integer> justNextLargerNode(TreeNode<Integer> node, int n){

        TreeNode<Integer> answer = new TreeNode<Integer>(Integer.MAX_VALUE);
        if(node.data > n){
            answer = node;
        }

        for(TreeNode<Integer> child : node.children){
            TreeNode<Integer> value = justNextLargerNode(child, n);
            if(value.data < answer.data){
                answer = value;
            }
        }

        return answer;
    }

    public secondLargest findSecondLargest(TreeNode<Integer> node){

        //does not work.
        //https://classroom.codingninjas.com/app/classroom/me/22712/content/539939/offering/8212973/problem/340

        secondLargest sec = new secondLargest(node.data, Integer.MIN_VALUE);

        for(TreeNode<Integer> child : node.children){
            secondLargest value = findSecondLargest(child);
            if(value.largest.data != sec.largest.data && value.largest.data > sec.largest.data){
                sec.largest = value.largest;
                sec.secondLargest = sec.largest;
            }
            if(value.largest.data > sec.secondLargest.data && value.largest.data != sec.largest.data){
                sec.secondLargest = value.largest;
            }
        }
        return sec;
    }

    class secondLargest{
        TreeNode<Integer> largest;
        TreeNode<Integer> secondLargest;

        public secondLargest(int largest, int secondLargest){
            this.largest = new TreeNode<Integer>(largest);
            this.secondLargest = new TreeNode<Integer>(secondLargest);
        }

        public void setLargest(int largest){
            this.largest = new TreeNode<Integer>(largest);
        }

        public void setSecondLargest(int secondLargest){
            this.secondLargest = new TreeNode<Integer>(secondLargest);
        }

        public TreeNode<Integer> getLargest(){
            return this.largest;
        }

        public TreeNode<Integer> getSecondLargest(){
            return this.secondLargest;
        }
        
    }

    public int countLeafNodes(TreeNode<Integer> node){

        if(node.children.isEmpty()) return 1;
        int value = 0;
        for(TreeNode<Integer> child : node.children){
            value = value + countLeafNodes(child);
        }

        return value;
    }

    public void replaceNodeWithDepth(TreeNode<Integer> node, int depth){
        //replace each node with its depth value
        node.data = depth;
        for(TreeNode<Integer> child : node.children){
            replaceNodeWithDepth(child, depth+1);
        }
    }

}
