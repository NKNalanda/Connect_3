package com.example.connect3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    //        0 = yellow, 1 = red
    int activePlayer = 0;

    //        2 means unplayed
    int[] gameState = {2,2,2,2,2,2,2,2,2};

    int[][] winningPositions = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}};

    boolean isGameActive = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void dropIn(View view) {

        ImageView counter = (ImageView) view;

        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        if (gameState[tappedCounter] == 2 && isGameActive) {

            gameState[tappedCounter] = activePlayer;

            counter.setTranslationY(-1000f);

            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.yellow);
                activePlayer = 1;
            } else {
                counter.setImageResource(R.drawable.red_dart);
                activePlayer = 0;
            }

            counter.animate().translationYBy(1000f).rotation(360f).setDuration(300);

            for (int[] position : winningPositions){
                if ((gameState[position[0]] == gameState[position[1]]) &&
                        (gameState[position[1]] == gameState[position[2]]) &&
                        (gameState[position[0]] != 2)) {

                    isGameActive = false;

                    String winner = "Red";

                    if (gameState[position[0]] == 0) {
                        winner = "Yellow";
                    }

                    // someone has won

                    TextView winText = (TextView) findViewById(R.id.winText);
                    winText.setText(winner+ " has won!");


                    LinearLayout linearLayout = (LinearLayout) findViewById(R.id.playAgainLayout);
                    linearLayout.setVisibility(View.VISIBLE);

                }
                else {

                    boolean isGameOver = true;
                    for (int counterState : gameState){
                        if (counterState == 2){
                            isGameOver = false;
                        }
                    }

                    if (isGameOver){

                        TextView winText = (TextView) findViewById(R.id.winText);
                        winText.setText("It's a draw");

                        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.playAgainLayout);
                        linearLayout.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    }

    public void playAgain(View view) {

        isGameActive = true;

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.playAgainLayout);
        linearLayout.setVisibility(View.INVISIBLE);

        activePlayer = 0;

        for (int i = 0; i < gameState.length; i++){
            gameState[i] = 2;
        }

        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);

        for (int i = 0; i < gridLayout.getChildCount(); i++){
            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);
        }
    }
}
