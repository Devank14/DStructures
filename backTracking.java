public class backTracking {

    /*
     * Rat in a maze - There is a rat and a maze. The rat has to reach the end from
     * the start. The maze is ofcourse given in a form of 2D matrix. The matrix will
     * have two values - 1 and 0. 1 means its a valid cell and 0 means its a block.
     * 
     * We have to maintain two matrices - one will be the given matrix which is the
     * input but we need another to track our path, so that we do not oscillate
     * between the previous and next cells.
     * 
     * Just return true if there exists a way between (0, 0) to (N-1, N-1) and false
     * otherwise.
     */

    public boolean ratInAMaze(int maze[][]) {
        int[][] path = new int[maze.length][maze.length];
        return ratInAMaze(maze, path, 0, 0);
    }

    public boolean ratInAMaze(int[][] maze, int[][] path, int i, int j) {

        if (i < 0 || j < 0 || i > maze.length - 1 || j > maze.length - 1)
            return false;

        if (path[i][j] == 1 || maze[i][j] == 0)
            return false;

        path[i][j] = 1;

        if (i == maze.length - 1 && j == maze.length - 1) {
            return true;
        }

        boolean top = ratInAMaze(maze, path, i - 1, j);
        boolean left = ratInAMaze(maze, path, i, j - 1);
        boolean right = ratInAMaze(maze, path, i, j + 1);
        boolean down = ratInAMaze(maze, path, i + 1, j);

        return top || left || right || down;

    }

    

}
