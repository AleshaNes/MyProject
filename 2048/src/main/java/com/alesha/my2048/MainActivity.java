package com.alesha.my2048;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

public class MainActivity extends ActionBarActivity implements View.OnTouchListener {

    CellLayout table;
    RelativeLayout layout;
    int x, y;
    boolean f = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        table = (CellLayout)findViewById(R.id.table);
        layout = (RelativeLayout) getResources().getLayout(R.layout.activity_main);
        layout.setOnTouchListener(this);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                x = (int)event.getX();
                y = (int)event.getY();
                f = true;
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = (int)event.getX() - x;
                int dy = (int)event.getY() - y;
                if (dx <= -10 && f)
                    table.up();
                else if (dx >= 10 && f)
                    table.down();
                else if (dy <= -10 && f)
                    table.left();
                else if (dy >= 10 && f)
                    table.right();
                else
                    break;
            case MotionEvent.ACTION_UP:
                f = false;
                break;
        }
        return true;
    }
}
