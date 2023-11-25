import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class priorityQueueUse {

    public static void main(String args[]) {

        priorityQueueUse pqUse = new priorityQueueUse();
        int[] arr = pqUse.sortKSorted(new int[] { 2, 1, 4, 8, 6, 9 }, 2);
        System.out.println(Arrays.toString(arr));
        pqUse.checkMaxHeap(new int[] { 10, 9, 8, 7, 6, 5, 4, 3, 2, 7 });
        System.out.println(pqUse.kthLargest(new int[] { 10101, 565, 4921, 60 }, 2));
    }

    /*
     * Given a K sorted array- It is expected that the sorted array is returned.
     * K sorted array: The final position of every element is within
     * k elements of the current position. For example: [2,1,4,8,6,9] and k = 2.
     * Every element's final position is within 2 elements (+2 and -2) to the
     * current position.
     * 
     * Solution: Create a heap of size k, add first k elements, remove one by one
     * and put it in the array from the starting. This will be done for arr.length -
     * k
     * times. There will come a time when the last k elements are still available.
     * At that time, they will be present in the heap, just remove and put them.
     */

    public int[] sortKSorted(int arr[], int k) {

        PriorityQueue<Integer> pq = new PriorityQueue<Integer>();
        for (int i = 0; i < k; i++) {
            pq.add(arr[i]);
        }
        int i = 0;
        while (i < arr.length - k) {
            int value = pq.remove();
            pq.add(arr[i + k]);
            arr[i] = value;
            i++;
        }

        for (int j = arr.length - k; j < arr.length; j++) {
            arr[j] = pq.remove();
        }
        return arr;
        // The array should be sorted.
    }

    // Return K Largest elements of the array.
    public ArrayList<Integer> kLargest(int[] input, int k) {

        /*
         * Another way to create max Priority Queue.
         * PriorityQueue<Integer> pq = new
         * PriorityQueue<Integer>(Collections.reverseOrder());
         */

        maxPriorityQueue maxPQ = new maxPriorityQueue();
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>(maxPQ);
        ArrayList<Integer> alist = new ArrayList<Integer>();
        for (Integer a : input) {
            pq.add(a);
        }
        for (int i = 0; i < k; i++) {
            alist.add(pq.remove());
        }

        return alist;
    }

    /*
     * A better solution will be that instead of adding all
     * the elements to the PriorityQueue which takes O(n), we add
     * only the first k elements to the pq. Then, for the remaining
     * elements, we just have to check if the smallest of the pq is
     * larger than the remianing elements. This will take only O(k)
     */

    public ArrayList<Integer> kLargest2(int[] input, int k) {
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>();
        for (int i = 0; i < k; i++) {
            pq.add(input[i]);
        }
        for (int i = k; i < input.length; i++) {
            if (pq.peek() < input[i]) {
                pq.remove();
                pq.add(input[i]);
            }
        }
        return new ArrayList<Integer>(pq);
    }

    public ArrayList<Integer> kSmallest(int[] input, int k) {
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>();
        ArrayList<Integer> alist = new ArrayList<Integer>();
        for (Integer a : input) {
            pq.add(a);
        }
        for (int i = 0; i < k; i++) {
            alist.add(pq.remove());
        }

        return alist;
    }

    public class minPriorityQueue implements Comparator<Integer> {
        @Override
        public int compare(Integer o1, Integer o2) {
            if (o1 > o2)
                return 1;
            if (o1 < o2)
                return -1;
            return 0;
        }
    }

    public class maxPriorityQueue implements Comparator<Integer> {
        @Override
        public int compare(Integer o1, Integer o2) {
            if (o1 < o2)
                return 1;
            if (o1 > o2)
                return -1;
            return 0;
        }
    }

    public boolean checkMaxHeap(int arr[]) {
        int parent = 0;
        int leftChild, rightChild;
        while ((2 * parent + 1) < arr.length) {
            leftChild = 2 * parent + 1;
            rightChild = 2 * parent + 2;
            if (arr[leftChild] > arr[parent]) {
                return false;
            }
            if (arr.length - 1 >= rightChild && arr[rightChild] > arr[parent]) {
                return false;
            }
            parent++;
        }
        return true;
    }

    // return the Kth Largest element.
    public int kthLargest(int[] input, int k) {

        /*
         * Create a min heap of size arr.length - K, such that
         * the top most element will be the Kth largest element.
         * Then for the remaining elemenst check if the peek of
         * the queue is larger or not - If yes then we are good.
         * If no, then replace.
         */

        PriorityQueue<Integer> pq = new PriorityQueue<Integer>();
        for (int i = 0; i < k; i++) {
            pq.add(input[i]);
        }

        for (int i = k; i < input.length; i++) {
            if (pq.peek() < input[i]) {
                pq.remove();
                pq.add(input[i]);
            }
        }

        return pq.peek();
    }

    /*
     * Given k different sorted arrays of different sizes,
     * we need to merge the given arrays such that result
     * should be sorted as well.
     */
    public ArrayList<Integer> mergeKSortedArrays(ArrayList<ArrayList<Integer>> input) {

        PriorityQueue<Integer> pq = new PriorityQueue<Integer>();
        ArrayList<Integer> alist = new ArrayList<Integer>();

        for (ArrayList<Integer> list : input) {
            for (Integer value : list) {
                pq.add(value);
            }
        }

        while (!pq.isEmpty()) {
            alist.add(pq.remove());
        }

        return alist;
    }

    // We have to print the running Median. For every i-th integer added to the
    // running list of integers, print the resulting median.

    public void findMedian(int arr[]) {
        /*
         * Keep adding the elements into the priority queue, after
         * each addition, check size of the PQ. If size is odd, then
         * remove till (size/2+1) elements and print. If size is even,
         * remove till (size/2 - 1) and (size/2), store them and print.
         */

        // PriorityQueue<Integer> pq = new PriorityQueue<Integer>();
        // int i = 0;
        // while (i < arr.length) {
        //     for (int j = 0; j <= i; j++) {
        //         pq.add(arr[j]);
        //     }
        //     int size = pq.size();

        //     if (size % 2 == 0) {
        //         while(size/2)
        //     } else {

        //     }
        // }
    }

}
