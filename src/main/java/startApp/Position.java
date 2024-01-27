package startApp;

public class Position {
    /**
     * Sor indexét tároló változó.
     */
    private final int row;
    /**
     * Oszlop indexét tároló változó.
     */
    private final int col;


    /**
     * Pozíciót meghatározó osztáy konstruktora.
     * @param row sor index
     * @param col oszlop index
     */
    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     *
     * @return sor indexét adja vissza
     */
    public int getRow() {
        return row;
    }

    /**
     *
     * @return oszlop indexét adja vissza
     */
    public int getCol() {
        return col;
    }
    @Override
    public String toString() {
        return "(" + row + " " + col + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position position)) return false;
        return getRow() == position.getRow() && getCol() == position.getCol();
    }
}
