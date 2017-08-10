package com.aavdeev.krestikinoliki;


public interface BoardInterface {

    public abstract int winMove(String o2);
    public abstract int winEnemyMove(String x2, String o2);
    public abstract boolean isBoardFull();
    public boolean isWin();
    public int isWinInt();
    public abstract String[][] getBoard();
    public abstract void setBoard(String[][] board);
    public abstract String getBoardElement(int i, int j);
    public abstract void setBoardElement(String x2, int i, int j);
    public abstract int move(String o2);
    public abstract String toString();
    public abstract void clear();
    public abstract void setBoardFromGrid(int[] grid);
    int getStrategyMove(String O, String X);
}
