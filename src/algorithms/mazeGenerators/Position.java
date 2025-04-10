package algorithms.mazeGenerators;

public class Position {
    private int row;
    private int column;

    public Position(int row,int column){
        this.row=row;
        this.column=column;
    }
    public int getRowIndex(){
        return this.row;
    }
    public int getColumn(){
        return this.column;
    }

    @Override
    public String toString() {
        return "{" + row + "," + column + "}";
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Position)) return false;
        Position o = (Position) other;
        return this.row == o.row && this.column == o.column;
    }

}
