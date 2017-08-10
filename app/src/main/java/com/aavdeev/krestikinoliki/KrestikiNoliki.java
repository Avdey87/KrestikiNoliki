package com.aavdeev.krestikinoliki;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

public class KrestikiNoliki extends Activity implements View.OnClickListener {

    private View easyButton;
    private View exityButton;
    private View mediumButton;
    private View hardButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        easyButton = findViewById(R.id.EasyDifficulty);
        easyButton.setOnClickListener(this);
        mediumButton = findViewById(R.id.MediumDifficulty);
        mediumButton.setOnClickListener(this);
        hardButton = findViewById(R.id.HardDifficulty);

        exityButton = findViewById(R.id.exitButton);
        exityButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.MediumDifficulty:
                Game.KrestikiNoliki = new Board();
                break;
            case R.id.HardDifficulty:
                Game.KrestikiNoliki = new BoardHard();
                break;
            case R.id.EasyDifficulty:
                Game.KrestikiNoliki = new BoardDumb();
                break;
            case R.id.exitButton:
                finish();
                break;
            }

    }

    private void newGameDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.whosFirstLabel)
                .setItems(R.array.whosFirst,
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialoginterface, int i) {
                                startGame(i);
                            }
                        }).show();
    }

    private void startGame(int i) {
        Intent intent = new Intent(KrestikiNoliki.this, Game.class);
        intent.putExtra(Game.PLAYER, i);
        startActivity(intent);
    }
}
