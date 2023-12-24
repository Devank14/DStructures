public class sorting {

    /*
     ************************
     **** INSERTION SORT ****
     ************************
     */

    public void insertionSort(int[] arr) {

        for (int i = 1; i < arr.length; i++) {
            int temp = arr[i];
            int j = i - 1;

            while (j >= 0 && arr[j] > temp) {
                arr[j + 1] = arr[j];
                j--;
            }

            arr[j + 1] = temp;
        }
    }

    /*
     ************************
     **** SELECTION SORT ****
     ************************
     * Figure out the minimum element from all the elements, then position it at the
     * start.
     */

    public void selectionSort(int[] arr, int n) {
        int current = 0;
        int min = Integer.MAX_VALUE;
        int minIndex = 0;

        while (current < n - 1) {

            min = arr[current];
            minIndex = current;

            for (int i = current + 1; i < arr.length; i++) {
                if (arr[i] < min) {
                    min = arr[i];
                    minIndex = i;
                }
            }

            if (minIndex != current) {
                arr[minIndex] = arr[current];
                arr[current] = min;
            }
            current++;
        }
    }

    /*
     ************************
     ****** MERGE SORT ******
     ************************
     * 
     * Merge Sort is a Divide and Conquer algorithm.
     * Divide the n element sequence into two sub-sequences of n/2.
     * Conquer: Sort the two sub-sequences recursively, by breaking them again until
     * we have single elements.
     * Combine: Merge the subsequences
     * 
     * Merge Sort is not an inplace sorting algorithm.
     * Time Complexity: O(n logn)
     * Merge Sort is input independent.
     */

    public void mergeSort(int[] arr) {
        mergeSort(arr, 0, arr.length - 1);
    }

    private void mergeSort(int[] arr, int start, int end) {

        int mid = start + (end - start) / 2;
        mergeSort(arr, start, mid);
        mergeSort(arr, mid + 1, end);
        merge(arr, start, mid, end);

    }

    private void merge(int[] arr, int start, int mid, int end) {

        // The sizes of the temporary arrays
        int n1 = mid - start + 1;
        int n2 = end - mid;

        // create two temporaray new arrays
        int[] arr1 = new int[n1];
        int[] arr2 = new int[n2];

        for (int i = 0; i < n1; i++) {
            arr1[i] = arr[start + i];
        }
        for (int i = 0; i < n2; i++) {
            arr2[i] = arr[start + mid + i];
        }

        int i = 0, j = 0;
        int k = start;

        while (i < n1 && j < n2) {
            if (arr1[i] > arr2[j]) {
                arr[k] = arr2[j];
                j++;
            } else {
                arr[k] = arr1[i];
                i++;
            }
            k++;
        }

        while (i < n1) {
            arr[k] = arr[i];
            i++;
            k++;
        }

        while (j < n2) {
            arr[k] = arr[j];
            j++;
            k++;
        }
    }

    /*
     ************************
     ****** QUICK SORT ******
     ************************
     * This is a Divide and Conquer Algorithm
     * Partition the array into two subarrays based on a pivot element X. Such that
     * Left-subArray <= X and Right-subArray >= X
     * The elements in the subarrays should not necessarily be sorted.
     * 
     * Here we are taking the first element as pivot.
     * 
     * The best case scenario will be that the pivot is partitioning the array into
     * two equal subarrays. The average case run time of quickSort is much closer to
     * the best case than the worst case.
     * 
     * If the partition algorithm can ensure some fractional split of the input
     * which may result in breaking the input into smaller chunks, then we can get a
     * case which is not as bad as the worst case.
     * Hence we have to add a picot which is not maximum or minimum. Adding
     * randomization to the algorithm is better to ensure good performance over all
     * inputs.
     */

    public void quickSort(int[] arr) {
        quickSort(arr, 0, arr.length - 1);
    }

    private void quickSort(int[] arr, int start, int end) {

        if (start < end) {
            int pivot = partition(arr, start, end);
            quickSort(arr, start, pivot - 1);
            quickSort(arr, pivot + 1, end);
        }

    }

    private int partition(int[] arr, int start, int end) {

        int i = start;
        int pivot = start;
        for (int j = i + 1; j <= end; j++) {
            if (arr[pivot] > arr[j]) {
                i++;
                int temp = arr[j];
                arr[j] = arr[i];
                arr[i] = temp;
            }
        }
        int temp = arr[i];
        arr[i] = arr[pivot];
        arr[pivot] = temp;

        return i;

    }

    // Heap Sort

    // Bin Sort

    // Bucket Sort

    // Radix Sort

}
