package com.aavdeev.krestikinoliki;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;



public class Game extends Activity {
    public static final String PLAYER = "com.aavdeev.KrestikiNoliki.Game.PLAYER ";
    public static final String PREF_GAME = "catdogtoe";
    public static final String PREF_NAME = "myPrefs ";
    public static final int CROSS = 1;
    public static final int CIRCLE = 2;
    protected static final int CONTINUE = -1;
    public static BoardInterface KrestikiNoliki;
    public static boolean isMassagerPrint;
    static int i = 0;
    static int j = 0;
    private int[] grid = new int[3 * 3];
    private int player;
    private int opponent;
    private int curMove;


    private GameView gameView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setGrid();

        int temp = getIntent().getIntExtra(PLAYER, CIRCLE);

        if (temp == -1) {
            getSavedState();
            if (curMove == opponent) {
                computerMove();
            }

            SharedPreferences preference = getSharedPreferences(PREF_GAME, 0);
            SharedPreferences.Editor editor = preference.edit();
            editor.clear();
            editor.commit();
        }
        else {
            if (temp == 0) {
                opponent = CROSS;
                player = CIRCLE;
            } else {
                player = CROSS;
                opponent = CIRCLE;
            }
            if (opponent == 1) {
                computerMove();
            }
        }
        gameView = new GameView(this);
        setContentView(gameView);
        gameView.requestFocus();

        getIntent().putExtra(PLAYER,CONTINUE);
    }


    @Override
    protected void onPause() {
        super.onPause();

        if (checkGameFinished() == 0) {
            SharedPreferences settings = getSharedPreferences(PREF_GAME, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString(PREF_NAME, setSavedState());

        }
    }

    private void getSavedState() {
        String puz;
        String def = "000" + "000" + "000" + "121";
        SharedPreferences settings = getSharedPreferences(PREF_GAME, 0);
        puz = settings.getString(PREF_NAME, def);

        for (int i = 0; i < puz.length(); i++) {
            if (i == puz.length() - 1)
                curMove = puz.charAt(i) - '0';
            if (i == puz.length() - 2)
                opponent = puz.charAt(i) - '0';
            if (i == puz.length() - 3)
                player = puz.charAt(i) - '0';
            if (i < puz.length() - 3)
                grid[i] = puz.charAt(i) - '0';

        }
    }

    private String setSavedState() {
        StringBuilder buf = new StringBuilder();
        for (int element : grid) {
            buf.append(element);
        }
        buf.append(player);
        Log.v("before save", "t" + player);
        buf.append(opponent);
        Log.v("before save", "t" + opponent);
        buf.append(curMove);
        Log.v("before save", "t" + curMove);
        return buf.toString();
    }

    public void setGrid() {
        for (int i = 0 ; i<grid.length; i++) {
            grid[i] = 0;
        }
        KrestikiNoliki.clear();
        isMassagerPrint = false;
        if (opponent == 1) {
            computerMove();
        }

    }

    public int getGridPosition(int x, int y) {
        if (y * 3 + x < grid.length) {
            return grid[y * 3 + x];
        } else return 0;
    }

    public boolean setGridPosition(int x, int y, int value) {
        if (y * 3 + x < grid.length)
            if (grid[y * 3 + x] == 0) {
                grid[y * 3 + x] = value;
                return true;
            }
        return false;

    }

    public int getPlayer() {
        return player;
    }

    public void setCurMove(int mov) {
        curMove = mov;
    }

    public int getOpponent() {
        return opponent;
    }

    public int checkGameFinished() {
        KrestikiNoliki.setBoardFromGrid(grid);
        int status = KrestikiNoliki.isWinInt();
        if (status == 0) {
            if (KrestikiNoliki.isBoardFull()) {
                status = 3;
                printMessage(status);
            }
        }else
            printMessage(status);
        return status;
    }

    private void printMessage(int status) {
        if (isMassagerPrint) return;
        String message = "Draw!";
        if (status == 1)
            if (getPlayer() == 1)
                message = "You Win!";
             else message = "You Lost!";
        if (status==2)
            if (getPlayer()==2)
                message = "You Win!";
            else message = "You Lost!";
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        isMassagerPrint = true;
    }


    public void computerMove() {
        String O = Board.O;
        String X = Board.X;
        if (opponent == 1) {
            O = Board.X;
            X = Board.O;
        }
        int selected = -1;
        KrestikiNoliki.setBoardFromGrid(grid);
        Log.v("move: ", String.valueOf(selected));
        grid[KrestikiNoliki.getStrategyMove(O, X)] = opponent;
    }

    @Override
    public boolean onKeyDown(int keycode, KeyEvent event) {
        if (keycode == KeyEvent.KEYCODE_MENU) {
            new AlertDialog.Builder(this).setItems(R.array.optionsDialog,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface,
                                            int i) {
                            if (i == 1) {
                                finish();
                            }

                        }
                    }).create().show();
        }
        return super.onKeyDown(keycode, event);
    }

}

