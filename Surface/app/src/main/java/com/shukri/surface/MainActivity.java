package com.shukri.surface;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.*;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {


    private GameView gv;
    private ImageButton pac;
    private final static int START_DRAGGING = 0;
    private final static int STOP_DRAGGING = 1;
    private int status;
    LinearLayout ButtonLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gv = new GameView(this);
        pac=new ImageButton(this);
        pac.setBackgroundResource(R.drawable.pacman);

        pac.setOnTouchListener(this);
        pac.setDrawingCacheEnabled(true);


        FrameLayout GameLayout = new FrameLayout(this);
        ButtonLayout = new LinearLayout(this);
        ButtonLayout.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        ButtonLayout.addView(pac);

        GameLayout.addView(gv);
        GameLayout.addView(ButtonLayout);

        setContentView(GameLayout);


    }
    @Override
    protected void onPause()
    {
        super.onPause();
        gv.killThread(); //Notice this reaches into the GameView object and runs the killThread mehtod.
    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        gv.onDestroy();
    }

    @Override
    public boolean onTouch(View view, MotionEvent me) {
        if (me.getAction() == MotionEvent.ACTION_DOWN) {
            status = START_DRAGGING;


        }
        if (me.getAction() == MotionEvent.ACTION_UP) {
            status = STOP_DRAGGING;

        } else if (me.getAction() == MotionEvent.ACTION_MOVE) {
            if (status == START_DRAGGING) {
                System.out.println("Dragging");

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        pac.getWidth(), pac.getHeight());


               layoutParams.setMargins((int) me.getRawX(),  (int) me.getRawY() - 3*pac.getHeight() ,0, 0);
                //ButtonLayout.removeView(pac);
                pac.setLayoutParams(layoutParams);

                pac.invalidate();
            }
        }
        return false;
    }
}
