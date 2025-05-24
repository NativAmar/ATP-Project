package algorithms.mazeGenerators;

import java.io.Serializable;

public class Maze implements Serializable {
    private int[][] maze;
    private int row;
    private int columns;
    private Position start;
    private Position end;

    public Maze(int row, int columns) {
        this.row = row;
        this.columns = columns;
        this.maze = new int[row][columns];
    }

    public void setMaze(int row, int column, int value) {
        if (value != 0 && value != 1) {
            throw new IllegalArgumentException("Invalid value for maze cell: " + value + ". Only 0 or 1 allowed.");
        }
        this.maze[row][column] = value;
    }

    public void setStartPosition(Position start_pos) {
        this.start = start_pos;
    }

    public void setGoalPosition(Position end_pos) {
        this.end = end_pos;
    }

    public Position getStartPosition() {
        return this.start;
    }

    public Position getGoalPosition() {
        return this.end;
    }

    public int getPosition(Position p) {
        return maze[p.getRowIndex()][p.getColumnIndex()];
    }

    public int getColumn() {
        return this.columns;
    }

    public int getRow() {
        return this.row;
    }

    public int getCell(int r, int c) {
        return this.maze[r][c];
    }

    public void print() {
        System.out.println("{");
        for (int i = 0; i < this.row; i++) {
            System.out.print(" {");
            for (int j = 0; j < this.columns; j++) {
                if (start != null && i == start.getRowIndex() && j == start.getColumnIndex()) {
                    System.out.print('S');
                } else if (end != null && i == end.getRowIndex() && j == end.getColumnIndex()) {
                    System.out.print('E');
                } else {
                    System.out.print(maze[i][j]);
                }
                if (j < this.columns - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println(" },");
        }
        System.out.println("};");
    }


    /**
     * Converts the Maze object into a byte array representing:
     * [rows(2 bytes), cols(2 bytes), startRow(2 bytes), startCol(2 bytes),
     * goalRow(2 bytes), goalCol(2 bytes), maze content (rows*cols bytes)]
     */
    public byte[] toByteArray() {
        int rows = this.row;
        int cols = this.columns;

        byte[] byteArray = new byte[2 * 6 + rows * cols];
        int index = 0;

        index = putShort(byteArray, index, (short) rows);
        index = putShort(byteArray, index, (short) cols);

        index = putShort(byteArray, index, (short) start.getRowIndex());
        index = putShort(byteArray, index, (short) start.getColumnIndex());

        index = putShort(byteArray, index, (short) end.getRowIndex());
        index = putShort(byteArray, index, (short) end.getColumnIndex());

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                byteArray[index++] = (byte) maze[i][j];
            }
        }

        return byteArray;
    }

    /**
     * Constructs a Maze object from a given byte array formatted as returned by toByteArray().
     */
    public Maze(byte[] byteArray) {
        int index = 0;

        int rows = getShort(byteArray, index);
        index += 2;
        int cols = getShort(byteArray, index);
        index += 2;

        int startRow = getShort(byteArray, index);
        index += 2;
        int startCol = getShort(byteArray, index);
        index += 2;

        int goalRow = getShort(byteArray, index);
        index += 2;
        int goalCol = getShort(byteArray, index);
        index += 2;

        this.row = rows;
        this.columns = cols;
        this.maze = new int[rows][cols];
        this.start = new Position(startRow, startCol);
        this.end = new Position(goalRow, goalCol);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.maze[i][j] = byteArray[index++];
            }
        }
    }

    private int putShort(byte[] arr, int index, short value) {
        arr[index] = (byte) ((value >> 8) & 0xFF);
        arr[index + 1] = (byte) (value & 0xFF);
        return index + 2;
    }

    private int getShort(byte[] arr, int index) {
        return ((arr[index] & 0xFF) << 8) | (arr[index + 1] & 0xFF);
    }
}
