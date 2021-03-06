package com.filtertoucheswhenobscured.example;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.Build;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;


public class OverlayShowingService extends Service implements View.OnClickListener {

    private View topLeftView;

    private Button overlayedButton;
    private WindowManager wm;
    private WindowManager.LayoutParams params;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);

        Point start = new Point();

        overlyPermission(start);

        overlayedButton = new Button(this);
        overlayedButton.setText("Allow this application to access Internet?");
        overlayedButton.setAlpha(0.15f);
        overlayedButton.setTextColor(Color.DKGRAY);
        overlayedButton.setBackgroundColor(Color.RED);
        overlayedButton.setOnClickListener(this);

        overlayedButton.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        overlayedButton.setPadding(40, 0, 0, 0);


        params = new WindowManager.
                LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
//                WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
                WindowManager.LayoutParams.TYPE_TOAST,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE |
                        WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                PixelFormat.TRANSLUCENT);

        params.y = start.y;
        params.height = 640;
        params.width = 400;

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)

            wm.addView(overlayedButton, params);

    }


    private void overlyPermission(Point start) {
        int heightDialog = dpToPx(700);
        int widthDialog = dpToPx(720);
        Point screenSize = new Point();

        WindowManager winMng = (WindowManager) getSystemService(Context.WINDOW_SERVICE);

        winMng.getDefaultDisplay().getRealSize(screenSize);

        start.y = (screenSize.y / 2) - (heightDialog / 2);

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (overlayedButton != null) {
            wm.removeView(overlayedButton);
            overlayedButton = null;
            topLeftView = null;
        }
    }


    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getApplicationContext().getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public int pxToDp(int px) {
        DisplayMetrics displayMetrics = getApplicationContext().getResources().getDisplayMetrics();
        return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    @Override
    public void onClick(View v) {
        Log.d("TAGTAG", "");
    }
}