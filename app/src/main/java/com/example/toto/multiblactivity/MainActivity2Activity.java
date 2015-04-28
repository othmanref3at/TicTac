package com.example.toto.multiblactivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainActivity2Activity extends ActionBarActivity implements View.OnClickListener {

    public int [][] buttuns=new int [3][3];
    MediaPlayer Media;
    public   Button [][] TicButton;
    private TextView mGamestateText;
    private  TicTacToe game;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2);
        Media=MediaPlayer.create(this,R.raw.play);
        Media.isLooping();
        Media.start();
        this.TicButton=new Button[3][3];
        TicButton[0][0]=(Button)findViewById(R.id.bt1);
        TicButton[0][1]=(Button)findViewById(R.id.bt2);
        TicButton[0][2]=(Button)findViewById(R.id.bt3);
        TicButton[1][0]=(Button)findViewById(R.id.bt4);
        TicButton[1][1]=(Button)findViewById(R.id.bt5);
        TicButton[1][2]=(Button)findViewById(R.id.bt6);
        TicButton[2][0]=(Button)findViewById(R.id.bt7);
        TicButton[2][1]=(Button)findViewById(R.id.bt8);
        TicButton[2][2]=(Button)findViewById(R.id.bt9);
        mGamestateText=(TextView)findViewById(R.id.Txtnumber);
        for(int row=0;row<3;row++) {
            for (int col = 0; col < 3; col++) {
                TicButton[row][col].setOnClickListener(this);
            }
        }

        this.game=new TicTacToe(this);

        // bt1.setBackgroundResource(R.drawable.cross);
        // Button bn=(Button)findViewById(R.id.bti);
        // bn.setBackgroundResource(R.drawable.cross);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onClick(View v) {

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (v.getId() == TicButton[row][col].getId()) {
                    game.pressButton(row, col);
                }
                TicButton[row][col].setText(
                    game.stringButton(row, col));
            }
        }
        // Uupdate the text edit
        mGamestateText.setText(game.stringForGameState());


    }

     public void restart()
        {
            Intent intent = new android.content.Intent();
            intent.setClass(this, this.getClass());  //We will start the

            this.startActivity(intent);
            //this.finish();

    }
    public static class TicTacToe {


        private GameState gameState;
        MainActivity2Activity mainActivity2Acti=new MainActivity2Activity();
        private int[][] boardArray;
        private Context context;
        int counx=0;
        int county=0;

        public TicTacToe(Context context) {
            this.context = context;
            resetGame();
        }
        //reset the game and player x at first
        public void resetGame() {
            this.boardArray = new int[3][3];
            this.gameState = GameState.Player_X;
        }
        //when the player press on the buttons if the player 1 press put in array 1 else player 2 put in array 2
        public void pressButton(int row, int column) {
         if (this.gameState == GameState.Player_X) {
                this.boardArray[row][column] = 1;
                counx++;
                this.gameState = GameState.Player_O;
            } else if (this.gameState == GameState.Player_O) {
                this.boardArray[row][column] = 2;
                county++;
                this.gameState = GameState.Player_X;
            }//end if
            checkForWin();//call check is the game finish or not
        }//end function pressedButtonLocation

        //this function check any player win
        private void checkForWin() {
            if (if_Playerwin(1)) {

                this.gameState = GameState.Player_X_win;
            } else if (if_Playerwin(2)) {
                this.gameState = GameState.Player_O_win;
            } else if (isFull()) {

                this.gameState = GameState.GameOver;
            }//end if
        }//end function check for win

        //this function check is array full
        private boolean isFull() {
            for (int row = 0 ; row<3 ; row++) {
                for (int col = 0 ; col<3 ; col++) {
                    if (this.boardArray[row][col] == 0)
                    {

                        return false;
                    }//end if
                }//end for
            }//end for
            return true;
        }//end function is full
        //check the player is win or not and return boolean
        private boolean if_Playerwin(int numberplayer) {
            boolean Match = true;//for check the array or bord is the same
            // Check all the columns for a win
            for (int col=0 ; col<3 ; col++) {
                 Match = true;
                for (int row=0 ; row<3 ; row++) {
                    if (this.boardArray[row][col] != numberplayer) {
                        Match = false;
                        break;
                    }//end if

                }//end for
                if (Match) return true;
            }//end for

            // Check all the rows for a win
            for (int row=0 ; row<3 ; row++) {
                Match = true;
                for (int col=0 ; col<3 ; col++) {
                    if (this.boardArray[row][col] != numberplayer) {
                        Match = false;
                        break;
                    }//end if

                }//end for
                if (Match) return true;
            }//end for

            // Check down right diagonal
            if (this.boardArray[0][0]==numberplayer && this.boardArray[1][1]==numberplayer && this.boardArray[2][2]==numberplayer) {

                return true;
            }//end if
            // Check up right diagonal
            if (this.boardArray[2][0]==numberplayer && this.boardArray[1][1]==numberplayer && this.boardArray[0][2]==numberplayer) {

                return true;
            }//end if
            return false;
        }//end function if_player win
        //set the button of string x or o
        public String stringButton(int row, int column) {
            String player = "";
                //check the player is x or o
                if (this.boardArray[row][column] == 1) {
                    return "X";
                } else if (this.boardArray[row][column] == 2) {
                    return "O";
                }//end if

            return player;
        }//end function


        //this function to set the text string
        public String stringForGameState() {
            String gameState = "";
            switch (this.gameState) {
                case Player_X:
                    gameState = "Player_X";
                    break;
                case Player_O:
                    gameState = "Player_O";
                    break;
                case Player_X_win:
                    gameState = "Player_X_win"+counx;
                    aleart();

                    break;
                case Player_O_win:
                    gameState ="Player_O_win"+county;
                    aleart();
                    break;
                default:
                    gameState = "opps- GameOver";
                    aleart();
                    break;
            }//end switch
            return gameState;
        }//end function
        //this function to show aleart
        private void aleart() {
            AlertDialog.Builder  alert=new AlertDialog.Builder(context);
            alert.setTitle("End Game");
            alert.setMessage("Are you sure to end game");
            alert.setIcon(R.drawable.xo);
            //if button yes
            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    MainActivity2Activity v= new MainActivity2Activity();
                    v.finish();
                    System.exit(0);


                }
            });
            //if button no
            alert.setNegativeButton("No",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    MainActivity main=new MainActivity();

                    main.finish();

                    System.exit(0);



                }
            });
            alert.show();
        }

        public enum  GameState {
            Player_X,
            Player_O,
            Player_X_win,
            Player_O_win,
            GameOver
        }
    }

}
