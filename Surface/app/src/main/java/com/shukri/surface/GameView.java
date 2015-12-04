package com.shukri.surface;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


/**
 * Created by 108160 on 12/2/2015.
 */
public class GameView extends SurfaceView
{

    private SurfaceHolder holder;
    private Bitmap ball;
    private GameThread gthread = null;
    private float ballx = -64.0f;
    private float bally = 250.0f;

    public GameView(Context context) {
        super(context);
        holder = getHolder();
        holder.addCallback( new SurfaceHolder.Callback(){
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

                ball = BitmapFactory.decodeResource(getResources(), R.drawable.ball);
                makeThread();

                gthread.setRunning(true);
                gthread.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        } );


    }

    public void makeThread()
    {
        gthread = new GameThread(this);

    }

    public void killThread()
    {
        boolean retry = true;
        gthread.setRunning(false);
        while(retry)
        {
            try
            {
                gthread.join();
                retry = false;
            }
            catch (InterruptedException e)
            {
            }
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        float movex=10.0f;
        float movey=10.0f;
        ballx = ballx + movex;
        //bally+=movey;
        if(ballx > getWidth()-10) {
            ballx = -64.0f;
            bally=(float)Math.random()*(getHeight()-20);

        }

        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(ball, ballx, bally, null);
    }
    public void onDestroy()
    {
        ball.recycle();
        ball = null;
        System.gc();
    }



}