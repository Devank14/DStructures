import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class priorityQueue {

    // MIN HEAP - The parent is smaller than the children.

    public static void main(String args[]) throws PriorityQueueException {
        priorityQueue pq = new priorityQueue();
        pq.insert(52);
        pq.insert(8);
        pq.insert(6);
        pq.insert(29);
        pq.insert(36);
        pq.insert(21);
        pq.insert(24);
        pq.insert(18);
        pq.insert(42);
        pq.insert(2);

        List<Integer> allValues = Arrays.asList(52, 8, 6, 29, 36, 21, 24, 18, 42, 2);
        ArrayList<Integer> listy = pq.inPlaceHeapSort(new ArrayList<Integer>(allValues));

        System.out.println(listy.toString());

        // ArrayList<Integer> aList = pq.heapSort();
        // System.out.println(aList.toString());
    }

    private static ArrayList<Integer> heap;

    public priorityQueue() {
        heap = new ArrayList<Integer>();
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    public int size() {
        return heap.size();
    }

    public int getMin() throws PriorityQueueException {
        if (heap.size() == 0)
            throw new PriorityQueueException();
        return heap.get(0);
    }

    // The node will be inserted as a leaf node from the left side, maintaining the
    // CBT - Complete Binary Tree property.
    public void insert(int data) {
        // This is a min heap, that is the smallest element will be on the first
        // position.
        heap.add(data);
        int childIndex = heap.indexOf(data);
        int parentIndex = (childIndex - 1) / 2;

        while (childIndex > 0) {
            if (heap.get(childIndex) < heap.get(parentIndex)) {
                // swap
                int temp = heap.get(childIndex);
                heap.set(childIndex, heap.get(parentIndex));
                heap.set(parentIndex, temp);
                childIndex = parentIndex;
                parentIndex = (childIndex - 1) / 2;
            } else {
                return;
            }
        }
    }

    public int removeMin() throws PriorityQueueException {
        if (heap.isEmpty()) {
            throw new PriorityQueueException();
        }
        int rem = heap.get(0);
        try {
            heap.set(0, heap.get(heap.size() - 1));
            heap.remove(heap.size() - 1);

            int parent = 0;
            int leftChildIndex = 1;
            int rightChildIndex = 2;
            int minIndex = 0;

            if (rightChildIndex < heap.size() - 1 && leftChildIndex < heap.size() - 1) {
                minIndex = (heap.get(rightChildIndex) > heap.get(leftChildIndex)) ? leftChildIndex
                        : rightChildIndex;
            } else if (rightChildIndex < heap.size() - 1) {
                minIndex = rightChildIndex;
            } else if (leftChildIndex < heap.size() - 1) {
                minIndex = leftChildIndex;
            }

            while (minIndex < heap.size() - 1 && heap.get(parent) > heap.get(minIndex)) {

                int temp = heap.get(parent);
                heap.set(parent, heap.get(minIndex));
                heap.set(minIndex, temp);

                parent = minIndex;
                leftChildIndex = 2 * parent + 1;
                rightChildIndex = 2 * parent + 2;

                if (rightChildIndex < heap.size() - 1 && leftChildIndex < heap.size() - 1) {
                    minIndex = (heap.get(rightChildIndex) > heap.get(leftChildIndex)) ? leftChildIndex
                            : rightChildIndex;
                } else if (rightChildIndex < heap.size() - 1) {
                    minIndex = rightChildIndex;
                } else if (leftChildIndex < heap.size() - 1) {
                    minIndex = leftChildIndex;
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return rem;

    }

    public ArrayList<Integer> heapSort() throws PriorityQueueException {

        // The space complexity of Heap Sort is O(n)
        // We have to reduce it.
        ArrayList<Integer> alist = new ArrayList<Integer>();
        while (!heap.isEmpty()) {
            alist.add(removeMin());
        }
        return alist;
    }

    /**
     * @param alist
     * @return
     */
    public ArrayList<Integer> inPlaceHeapSort(ArrayList<Integer> alist) {

        /*
         * First we will have to create a heap out of this list
         * How is a heap created? - Maintaining CBT and heap property.
         * CBT is already achieved. We have to add elements one by one.
         */
        int index = 0, parentIndex = 0, leftChild = 0, rightChild = 0;

        ArrayList<Integer> newHeap = new ArrayList<Integer>();

        for (Integer element : alist) {
            newHeap.add(element);
            index = newHeap.size() - 1;
            parentIndex = (index - 1) / 2;

            while (parentIndex >= 0 && newHeap.get(parentIndex) > newHeap.get(index)) {
                int temp = newHeap.get(parentIndex);
                newHeap.set(parentIndex, newHeap.get(index));
                newHeap.set(index, temp);
                index = parentIndex;
                parentIndex = (index - 1) / 2;
            }
        }
        /*
         * We have transformed the newHeap into a min heap. Now we have to
         * remove elements one-by-one while maintaining the min heap property.
         * Since this is an inplace version, the element removed from the top,
         * will be placed at the end. So we have to keep where the heap is ending
         * after every removal. :)
         */

        int lastIndex = newHeap.size() - 1;
        while (lastIndex >= 0) {
            int current = 0;
            int removedElement = newHeap.get(current);
            newHeap.set(current, newHeap.get(lastIndex));
            newHeap.set(lastIndex, removedElement);

            leftChild = 1;
            rightChild = 2;
            int minIndex;
            if (leftChild < lastIndex && rightChild < lastIndex) {
                minIndex = (newHeap.get(leftChild) > newHeap.get(rightChild)) ? rightChild : leftChild;
            } else if (leftChild <= lastIndex) {
                minIndex = leftChild;
            } else {
                minIndex = newHeap.size();
            }

            lastIndex--;

            while (minIndex < newHeap.size()) {
                if (newHeap.get(current) > newHeap.get(leftChild) || newHeap.get(current) > newHeap.get(rightChild)) {
                    int temp = newHeap.get(minIndex);
                    newHeap.set(minIndex, newHeap.get(current));
                    newHeap.set(current, temp);

                    current = minIndex;
                    leftChild = 2 * current + 1;
                    rightChild = 2 * current + 2;

                    if (leftChild < lastIndex && rightChild < lastIndex) {
                        minIndex = (newHeap.get(leftChild) > newHeap.get(rightChild)) ? rightChild : leftChild;
                    } else if (leftChild <= lastIndex) {
                        minIndex = leftChild;
                    } else {
                        minIndex = newHeap.size();
                    }
                } else {
                    break;
                }
            }
        }
        return newHeap;
    }

}
