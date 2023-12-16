import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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

        System.out.println(dp.stepsTo1(50));
        System.out.println(dp.stepsTo1Memo(50));
        System.out.println(dp.stepsTo1DP(50));

        System.out.println(dp.staircase(10));
        System.out.println(dp.staircaseMemo(10));
        System.out.println(dp.staircaseDP(10));

        System.out.println(dp.balancedBTs(5));
        System.out.println(dp.balancedBTsMemo(5));
        System.out.println(dp.balancedBTsDP(5));

        System.out.println(dp.minCount(8));
        System.out.println(dp.minCountMemo(8));

        System.out.println(dp.sumPossible(10, Arrays.asList(3, 7, 6)));
        System.out.println(dp.sumPossibleDP(10, Arrays.asList(3, 7, 6)));

        System.out.println(dp.minChange(12, Arrays.asList(1, 2, 3, 5)));
        System.out.println(dp.minChangeDP(12, Arrays.asList(1, 2, 3, 5)));
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

        int[] arr = new int[n + 1];
        Arrays.fill(arr, -1);
        arr[0] = 0;
        arr[1] = 0;
        return stepsTo1Memo(n, arr);
    }

    private int stepsTo1Memo(int n, int[] arr) {

        if (n == 1)
            return arr[1];
        if (arr[n] != -1)
            return arr[n];

        arr[n] = stepsTo1Memo(n - 1);
        if (n % 3 == 0) {
            int option2 = stepsTo1Memo(n / 3);
            if (option2 < arr[n])
                arr[n] = option2;
        }

        if (n % 2 == 0) {
            int option3 = stepsTo1Memo(n / 2);
            if (option3 < arr[n])
                arr[n] = option3;
        }

        return arr[n] + 1;
    }

    public int stepsTo1DP(int n) {

        // We are going to use DP

        int[] arr = new int[n + 1];
        arr[0] = 0;
        arr[0] = 0;
        return stepsTo1DP(n, arr);
    }

    private int stepsTo1DP(int n, int[] arr) {

        for (int i = 2; i < arr.length; i++) {
            int num = i;

            int ans = num - 1;
            ans = arr[ans] + 1;

            num = i;
            if (num % 3 == 0) {
                int option2 = num / 3;
                option2 = arr[option2] + 1;
                if (ans > option2)
                    ans = option2;
            }

            num = i;
            if (num % 2 == 0) {
                int option3 = num / 2;
                option3 = arr[option3] + 1;
                if (ans > option3)
                    ans = option3;
            }

            arr[i] = ans;
        }

        return arr[n];
    }

    /*
     * A child runs up a staircase with 'n' steps and can hop either 1 step,
     * 2 steps or 3 steps at a time. Implement a method to count and return all
     * possible ways in which the child can run up to the stairs.
     * 
     * For example: 4
     * (1, 3) , (3, 1) , (2, 2) , (1, 1, 1, 1), (1, 1, 2), (1, 2, 1), (2, 1, 1)
     * Answer: 7
     */

    public long staircase(int n) {

        // Basic Recursion

        if (n == 0) {
            return 1;
        }
        Long value1 = staircase(n - 1);

        if (n >= 2) {
            Long value2 = staircase(n - 2);
            value1 += value2;
        }

        if (n >= 3) {
            Long value3 = staircase(n - 3);
            value1 += value3;
        }

        return value1;

    }

    public long staircaseMemo(int n) {

        // Use Memoization,to store the answers to prevent unnecessary computation

        long[] arr = new long[n + 1];
        arr[0] = 0;
        return staircaseMemo(n, arr);
    }

    private long staircaseMemo(int n, long[] arr) {

        if (n == 0) {
            return 1;
        }

        if (arr[n] != 0) {
            return arr[n];
        }

        long value1 = staircaseMemo(n - 1, arr);

        if (n >= 2) {
            long value2 = staircaseMemo(n - 2, arr);
            value1 += value2;
        }

        if (n >= 3) {
            long value3 = staircaseMemo(n - 3, arr);
            value1 += value3;
        }
        arr[n] = value1;
        return arr[n];
    }

    public long staircaseDP(int n) {

        // Using Dynamic Programming now.
        int[] arr = new int[n + 1];
        arr[0] = 1;
        return staircaseDP(n, arr);
    }

    private long staircaseDP(int n, int[] arr) {

        for (int i = 1; i < arr.length; i++) {

            int num = i;
            int value = arr[num - 1];

            if (i >= 2) {
                num = i;
                value += arr[num - 2];
            }

            if (i >= 3) {
                num = i;
                value += arr[num - 3];
            }

            arr[i] = value;
        }

        return arr[n];
    }

    /*
     * Given height, how many structurally different balanced binary trees,
     * can we construct. Where balanced means |left-height - right-height| <= 1.
     * The first solution will be the recursive solution, the second one will
     * use memoization and the third one will be the optimal solution using
     * dynamic programming.
     * 
     * The whole idea of the solution is, if we want the height of the tree to be h,
     * then the sub trees will be atleast h-2 and atmost h-1, only then it will be
     * balanced.
     * That is, we will have three cases:
     * 1) Height of left subtree = h-1 and height of right subtree = h-1
     * 2) Height of left subtree = h-2 and height of right subtree = h-1
     * 3) Height of left subtree = h-1 and height of right subtree = h-2
     * 
     * Let x be height of left subtree and y be the height of right subtree
     * So total will be x^2 + x*y + y*x
     */

    public long balancedBTs(int height) {

        if (height == 0 || height == 1) {
            return 1;
        }

        long x = balancedBTs(height - 1);
        long y = balancedBTs(height - 2);

        return 2 * x * y + x * x;
    }

    public long balancedBTsMemo(int height) {

        if (height == 0 || height == 1) {
            return 1;
        }

        long[] arr = new long[height + 1];
        arr[0] = arr[1] = 1;

        return balancedBTsMemo(height, arr);
    }

    private long balancedBTsMemo(int height, long[] arr) {

        if (arr[height] != 0)
            return arr[height];
        arr[height - 1] = balancedBTsMemo(height - 1, arr);

        long x = arr[height - 1];
        long y = arr[height - 2];

        return 2 * x * y + x * x;

    }

    public long balancedBTsDP(int height) {
        if (height == 0 || height == 1) {
            return 1;
        }

        long[] arr = new long[height + 1];
        arr[0] = arr[1] = 1;

        return balancedBTsDP(height, arr);
    }

    private long balancedBTsDP(int height, long[] arr) {

        for (int i = 2; i < arr.length; i++) {
            long x = arr[i - 1];
            long y = arr[i - 2];

            arr[i] = x * x + 2 * x * y;
        }

        return arr[height];
    }

    /*
     * Given an integer n, find and return the count of minimum numbers
     * required to represent n as a sum of squares. That is, if n = 4, it
     * can be represented by (1^2+1^2+1^2+1^2) and (2^2). The output will
     * be 1, since 1 is the minimum coutn of number required to represent
     * N as a sum of squares.
     */

    // **** TOUGH ****

    public int minCount(int n) {

        if (n == 0)
            return 0;
        int minAns = Integer.MAX_VALUE;
        for (int i = 1; i * i <= n; i++) {
            int currAns = minCount(n - (i * i));

            if (minAns > currAns) {
                minAns = currAns;
            }
        }
        return 1 + minAns;
    }

    public int minCountMemo(int n) {

        int[] arr = new int[n + 1];
        Arrays.fill(arr, -1);
        return minCountMemo(n, arr);
    }

    private int minCountMemo(int n, int[] arr) {

        if (n == 0)
            return 0;
        int minAns = Integer.MAX_VALUE;
        for (int i = 1; i * i <= n; i++) {
            int currAns;
            if (arr[n - (i * i)] == -1) {
                currAns = minCount(n - (i * i));
                arr[n - (i * i)] = currAns;
            } else {
                currAns = arr[n - (i * i)];
            }
            if (minAns > currAns) {
                minAns = currAns;
            }
        }
        return 1 + minAns;

    }

    // We are given a target amount and a list of numbers.
    // We have to find if any combination of the list of numbers
    // can be added to result the amount. The same number can be used multiple
    // times.
    // Tough

    public boolean sumPossible(int amount, List<Integer> numbers) {

        if (amount == 0)
            return true;
        if (amount < 0)
            return false;

        for (Integer value : numbers) {
            boolean val = sumPossible(amount - value, numbers);
            if (val)
                return val;
        }

        return false;

    }

    public boolean sumPossibleDP(int amount, List<Integer> numbers) {
        return sumPossibleDP(amount, numbers, new HashMap<>());
    }

    private boolean sumPossibleDP(int amount, List<Integer> numbers, HashMap<Integer, Boolean> map) {

        if (amount == 0)
            return true;
        if (amount < 0)
            return false;
        if (map.containsKey(amount))
            return map.get(amount);
        boolean answer = false;

        for (Integer value : numbers) {
            answer = sumPossibleDP(amount - value, numbers, map);
            map.put(value, answer);
            if (answer)
                return answer;
        }
        return false;
    }

    // Minimum change: We are given an amount, and some coins
    // We have to find the minimum number of coins that will make up that amount.
    // For example: amount = 5 coins: 1,2,3: [{1,1,1,1,1}, {1,1,1,2}, {1,1,3},
    // {2,2,1}, {2,3}
    // The answer will be: {2,3}

    // *****TOUGH***** :vv

    public int minChange(int amount, List<Integer> coins) {

        if (amount == 0)
            return 0;

        if (amount < 0)
            return -1;
        int minCoins = -1;

        for (Integer coinVal : coins) {
            int subCoins = minChange(amount - coinVal, coins);
            if (subCoins != -1) {
                subCoins++;
                if (minCoins > subCoins || minCoins == -1) {
                    minCoins = subCoins;
                }
            }
        }
        return minCoins;
    }

    public int minChangeDP(int amount, List<Integer> coins) {
        return minChangeDP(amount, coins, new HashMap<>());
    }

    private int minChangeDP(int amount, List<Integer> coins, HashMap<Integer, Integer> map) {

        if (amount == 0)
            return 0;
        if (amount < 0)
            return -1;

        if (map.containsKey(amount))
            return map.get(amount);

        int minCoins = -1;

        for (Integer coin : coins) {
            int subCoins = minChangeDP(amount - coin, coins, map);
            if (subCoins != -1) {
                subCoins++;
                if (subCoins < minCoins || minCoins == -1)
                    minCoins = subCoins;
                map.put(amount, minCoins);
            }
        }
        return minCoins;
    }

}
