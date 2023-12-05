import java.util.Arrays;

public class DynamicProgramming {

    /*
     * 1) When we have overlapping sub-problems, solving the same problems
     * redundantly.
     * 2) Optimal sub-structure: if we have the optimal solution of the sub
     * problems,
     * then we have the optimal solution of the main problem.
     * 
     * There is one difference between memoization and dynamic programming. In
     * memoization
     * we solve the problem in the top down approach, that is whenever we need a
     * value we
     * calculate it and store it.
     * 
     * In DP, we follow the bottom up approach, that is before having to want a
     * value, we
     * have all the necessary calculations done. We should avoid recursion here.
     */

    public static void main(String args[]) {
        DynamicProgramming dp = new DynamicProgramming();
        System.out.println(dp.fibonacci(4));
        System.out.println(dp.fibonacciMemo(4));
        System.out.println(dp.fibonacciDP(4));
        System.out.println(dp.stepsTo1(11));
        System.out.println(dp.stepsTo1Memo(11));
    }

    public int fibonacci(int n) {

        // Basic Recursive approach.

        if (n == 1 || n == 0) {
            return n;
        }

        return fibonacci(n - 1) + fibonacci(n - 2);
    }

    public int fibonacciMemo(int n) {

        // Following memoization - Calculating as we need.

        int[] arr = new int[n + 1];
        Arrays.fill(arr, -1);
        return fibonacciMemo(n, arr);

    }

    public int fibonacciMemo(int n, int[] arr) {

        if (n == 0 || n == 1) {
            arr[n] = n;
            return n;
        }

        if (arr[n] == -1) {
            arr[n] = fibonacciMemo(n - 1, arr) + fibonacciMemo(n - 2, arr);
        }

        return arr[n];
    }

    public int fibonacciDP(int n) {

        // Having all the values needed, before hand.
        // All the work is done before.

        int[] arr = new int[n + 1];
        arr[0] = 0;
        arr[1] = 1;

        for (int i = 2; i < arr.length; i++) {
            arr[i] = arr[i - 1] + arr[i - 2];
        }
        return arr[n];

    }

    public int stepsTo1(int n) {
        /*
         * We have to find the number of steps needed to reduce the n to 1.
         * We have got three options to reduce:
         * 1) subtract one from n
         * 2) divide by 2
         * 3) divide by 3
         * 
         * The basic recursive approach.
         */

        if (n == 1)
            return 0;

        int option1 = stepsTo1(n - 1);
        int minimum = option1;

        if (n % 3 == 0) {
            int option2 = stepsTo1(n / 3);
            if (option2 < minimum)
                minimum = option2;
        }

        if (n % 2 == 0) {
            int option3 = stepsTo1(n / 2);
            if (option3 < minimum)
                minimum = option3;
        }

        return minimum + 1;

    }

    public int stepsTo1Memo(int n) {
        /*
         * we are going to use memoization to associate every value
         * from n to 2 with the minimum steps needed to reach 1.
         */

        int[] arr = new int[n+1];
        Arrays.fill(arr, -1);
        arr[0] = 0;
        arr[1] = 0;
        return stepsTo1Memo(n, arr);
    }

    public int stepsTo1Memo(int n , int[] arr){

        if(n == 1) return arr[1];
        if(arr[n] != -1) return arr[n];
        
        arr[n] = stepsTo1Memo(n-1);
        if(n%3 == 0){
            int option2 = stepsTo1Memo(n/3);
            if(option2 < arr[n]) arr[n] = option2;
        }

        if(n %2 == 0){
            int option3 = stepsTo1Memo(n/2);
            if(option3 < arr[n]) arr[n] = option3;
        }

        return arr[n] + 1;
    }
}
