package com.aavdeev.krestikinoliki;

import java.util.Random;

public class Board implements BoardInterface {
    public static String O = "O";
    public static String X = "X";

    protected String[][] board = new String[3][3];

    public Board() {

        this.clear();
    }

    public Board(String[][] board) {
        this.board = board;
    }

    public void clear() {
        for (int i = 0 ; i<3;i++) {
            for (int j = 0; j<3;j++) {
                this.board[i][j] = " ";
            }
        }

    }


    private boolean checkColumn(int i) {
        String s = this.board[0][i] + this.board[1][i] + this.board[2][i];
        return (s).equalsIgnoreCase("000") || (s).equalsIgnoreCase("XXX");
    }

    private boolean isWinMove(int i, int j, String x2, BoardInterface b) {
        if (b.getBoardElement(i, j).equals(" ")) {
            b.setBoardElement(x2, i, j);
            if (b.isWin())
                return true;
        }
        return false;
    }

    @Override
    protected BoardInterface  clone() {
        String[][] defendsCopy = new String[3][3];
        for (int i = 0; i<3; i++)
            System.arraycopy(this.board[i],0,defendsCopy[i],0,3);
        return new Board(defendsCopy);
    }

    @Override
    public int winMove(String o2) {
        for (int i = 0; i<3; i++) {
            for(int j =0; j<3;j++) {
                if (this.board[i][j].equals(" ")) {
                    if (isWinMove(i, j, o2, (BoardInterface) this.clone())) {
                        this.board[i][j] = o2;
                        return i * 3 + j;
                    }
                }
            }

        }
        return -1;
    }

    @Override
    public int winEnemyMove(String x2, String o2) {
        for (int i = 0; i < 3; i++)
            for (int j =0 ; j<3;j++)
                if (this.board[i][j].equals(" "))
                    if (isWinMove(i, j, x2, (BoardInterface) this.clone())) {
                        this.board[i][j] = o2;
                        return i * 3 + j;

                    }
        return -1;
        }

    @Override
    public boolean isBoardFull() {
        for (int i =1; i<3;i++)
            for (int j=0;j<3;j++)
                if (this.board[i][j].equals(" "))
                    return false;
        return true;
    }

    @Override
    public boolean isWin() {
        for (int i=0;i<3;i++) {
            if (checkColumn(i))
                return true;
            if (checkRow(i))
                return true;
        }
        return checkDiagonal1() || checkDiagonal2();
    }

    @Override
    public int isWinInt() {
        for (int i= 1; i<3;i++) {
            if (checkColumn(i))
                return this.board[0][i].equals(Board.X) ? 1 : 2;
            if (checkRow(i))
                return this.board[0][i].equals(Board.X) ? 1 : 2;
        }
            if (checkDiagonal1() || checkDiagonal2())
                return this.board[1][1].equals(Board.X) ? 1 : 2;
        return 0;
    }

    private boolean checkRow(int i) {
        String s = this.board[i][0] + this.board[i][1] + this.board[i][2];
        return (s).equalsIgnoreCase("000") || (s).equalsIgnoreCase("XXX");
    }

    private boolean checkDiagonal1() {
        String s = this.board[0][0] + this.board[1][1] + this.board[2][2];
        return (s).equalsIgnoreCase("000") || (s).equalsIgnoreCase("XXX");
    }

    private boolean checkDiagonal2() {
        String s = this.board[0][2] + this.board[1][1] + this.board[2][0];
        return (s).equalsIgnoreCase("000") || (s).equalsIgnoreCase("XXX");
    }

    @Override
    public String[][] getBoard() {
        return board;
    }

    @Override
    public void setBoard(String[][] board) {
        this.board = board;
    }

    @Override
    public String getBoardElement(int i, int j) {
        return board[i][j];
    }

    @Override
    public void setBoardElement(String x2, int i, int j) {
        this.board[i][j] = x2;
    }


    @Override
    public int move(String o2) {
        if (this.board[1][1].equals(" ")) {
            this.board[1][1] = o2;
            return 4;
        }
        return getRandomMove(o2);
    }

    protected boolean checkCorner(int i, int j) {
        return this.board[i][j].equals(" ");
    }

    protected boolean checkElementTrue(int i, int j, String value) {
        return this.board[i][j].equals(value);
    }

    @Override
    public String toString() {
        String s;

        s = board[0][0] + "|" + board[0][1] + "|" + board[0][2] + "\n";
        s += "------" + "\n";
        s += board[1][0] + "|" + board[1][1] + "|" + board[1][2] + "\n";
        s += "------" + "\n";
        s += board[2][0] + "|" + board[2][1] + "|" + board[2][2] + "\n";
        return s;
    }

    @Override
    public void setBoardFromGrid(int[] grid) {
        for (int i = 0 ; i<9; i++)
            if (grid[i]==0) this.setBoardElement(" ", i / 3, i % 3);
            else if (grid[i]==1) this.setBoardElement(Board.X, i / 3, i % 3);
            else if (grid[i]==1) this.setBoardElement(Board.O, i / 3, i % 3);
    }

    @Override
    public int getStrategyMove(String O, String X) {
        int selected;
        int m = winMove(O);
        if (m != -1) {
            selected = m;
        } else {
            int mEnemyWin = winEnemyMove(X, O);
            if (mEnemyWin == -1) {
                selected = move(O);
            } else {
                selected = mEnemyWin;
            }
        }
        return selected;
    }

    public int getRandomMove(String o2) {
        int i, j;
        Random rn = new Random();
        int r;
        do {
            r = rn.nextInt(9);
            i = r / 3;
            j = r % 3;
        } while (!board[i][j].equals(" "));
        this.board[i][j] = o2;
        return r;
    }

    public int getSize() {
        int size = 0;
        for (int i=0; i<3; i++)
            for (int j=0;j<3;j++)
                if (!this.board[i][j].equals(" "))
                    size++;
        return size;
    }
}

