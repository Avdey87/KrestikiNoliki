package com.aavdeev.krestikinoliki;


public class BoardHard extends Board {

    @Override
    public int move(String value) {
        if (this.board[1][1].equals(" ")) {
            this.board[1][1] = value;
            return 4;
        }
        String x;
        if (value.equals(O)) x = X;
        else x = O;

        if (getSize() == 3) {
            if ((checkElementTrue(1, 1, value) && ((checkElementTrue(2, 2, x) && checkElementTrue(0, 0, x)) || (checkElementTrue(0, 2, x) && checkElementTrue(2, 0, x)))))
                return 1;
        }

        if (checkCorner(0, 0) && (checkElementTrue(0, 1, x) || checkElementTrue(1, 0, x))) {
            this.board[0][0] = value;
            return 0;
        }
        if (checkCorner(0, 2) && (checkElementTrue(0, 1, x) || checkElementTrue(1, 2, x))) {
            this.board[0][2] = value;
            return 2;
        }
        if (checkCorner(2, 0) && (checkElementTrue(1, 0, x) || checkElementTrue(2, 1, x))) {
            this.board[2][0] = value;
            return 6;
        }
        if (checkCorner(2, 2) && (checkElementTrue(1, 2, x) || checkElementTrue(2, 1, x))) {
            this.board[2][2] = value;
            return 8;
        }

                if (checkCorner(0, 0)) {
            this.board[0][0] = value;
            return 0;
        }
        if (checkCorner(0, 2)) {
            this.board[0][2] = value;
            return 2;
        }
        if (checkCorner(2, 0)) {
            this.board[2][0] = value;
            return 6;
        }
        if (checkCorner(2, 2)) {
            this.board[2][2] = value;
            return 8;
        }

        return getRandomMove(value);
    }

    @Override
    public int getStrategyMove(String oValue, String xValue) {
        int selected;
        if (getBoardElement(1, 1).equals(" ")) {
            selected = 4;

        } else {
            int m = winMove(oValue);
            if (m != -1) {
                selected = m;
            } else {
                int mEnemyWin = winEnemyMove(xValue, oValue);
                if (mEnemyWin == -1) {
                    selected = move(oValue);
                } else
                    selected = mEnemyWin;
            }
        }
        return selected;
    }
}