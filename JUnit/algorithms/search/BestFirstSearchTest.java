package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.IMazeGenerator;
import algorithms.mazeGenerators.Position;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BestFirstSearchTest {

    @Test
    public void testBestFirstFindsSolution() {
        // Arrange
        IMazeGenerator generator = new MyMazeGenerator();
        Maze maze = generator.generate(30, 30);
        SearchableMaze searchableMaze = new SearchableMaze(maze);

        // Act
        BestFirstSearch bfs = new BestFirstSearch();
        Solution solution = bfs.solve(searchableMaze);

        // Assert
        assertNotNull(solution, "BestFirstSearch should find a solution.");
        assertFalse(solution.getSolutionPath().isEmpty(), "Solution path should not be empty.");

        // Check that the solution ends at the goal position
        MazeState lastState = (MazeState) solution.getSolutionPath().get(solution.getSolutionPath().size() - 1);
        assertEquals(maze.getGoalPosition(), lastState.getPosition(),
                "The last state in the solution should be the goal position.");
    }
    @Test
    public void testEmptyMaze() {
        assertThrows(IllegalArgumentException.class, () -> {
            IMazeGenerator gen = new MyMazeGenerator();
            gen.generate(0, 0);
        });
    }

    @Test
    public void testNodeEvaluationCount() {
        IMazeGenerator gen = new MyMazeGenerator();
        Maze maze = gen.generate(10, 10);
        SearchableMaze sm = new SearchableMaze(maze);

        BestFirstSearch bfs = new BestFirstSearch();
        bfs.solve(sm);
        assertTrue(bfs.getNumberOfNodesEvaluated() >= 0, "מספר צמתים שנפתחו צריך להיות אי שלילי");
    }

    @Test
    public void testPathStartsAtStartPosition() {
        IMazeGenerator gen = new MyMazeGenerator();
        Maze maze = gen.generate(20, 20);
        SearchableMaze sm = new SearchableMaze(maze);

        BestFirstSearch bfs = new BestFirstSearch();
        Solution sol = bfs.solve(sm);

        MazeState first = (MazeState) sol.getSolutionPath().get(0);
        assertEquals(maze.getStartPosition(), first.getPosition(), "המסלול צריך להתחיל בנקודת ההתחלה של המבוך");
    }

    @Test
    public void testUnreachableGoal() {
        Maze maze = new Maze(5, 5);
        // מלא קירות
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++)
                maze.setMaze(i, j, 1);

        // תן נקודת התחלה ויעד פתוחים אך לא מחוברים
        maze.setMaze(0, 0, 0);
        maze.setStartPosition(new Position(0, 0));
        maze.setMaze(4, 4, 0);
        maze.setGoalPosition(new Position(4, 4));

        SearchableMaze sm = new SearchableMaze(maze);
        BestFirstSearch bfs = new BestFirstSearch();
        Solution sol = bfs.solve(sm);

        assertNull(sol, "לא אמור להיות פתרון כשהמטרה לא ניתנת להגעה");
    }

    @Test
    public void testReasonablePathLength() {
        IMazeGenerator gen = new MyMazeGenerator();
        Maze maze = gen.generate(30, 30);
        SearchableMaze sm = new SearchableMaze(maze);
        BestFirstSearch bfs = new BestFirstSearch();
        Solution sol = bfs.solve(sm);

        assertTrue(sol.getSolutionPath().size() <= (maze.getRow() + maze.getColumn()),
                "המסלול לא אמור להיות ארוך בצורה לא הגיונית");
    }

    @Test
    public void testNoDuplicateNodesEvaluated() {
        IMazeGenerator gen = new MyMazeGenerator();
        Maze maze = gen.generate(30, 30);
        SearchableMaze sm = new SearchableMaze(maze);
        BestFirstSearch bfs = new BestFirstSearch();

        Solution sol = bfs.solve(sm);
        int evaluated = bfs.getNumberOfNodesEvaluated();
        int pathSize = sol.getSolutionPath().size();

        assertTrue(evaluated >= pathSize, "מספר הצמתים שנפתחו אמור להיות לפחות באורך הנתיב");
    }









}
