import java.util.ArrayList;

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

        ArrayList<Integer> aList = pq.heapSort();
        System.out.println(aList.toString());
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
        ArrayList<Integer> alist = new ArrayList<Integer>();
        while (!heap.isEmpty()) {
            alist.add(removeMin());
        }
        return alist;
    }

}
