package com.example.jeremy.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by jeremy on 01/10/2016.
 */

public class MyBroadcaster extends BroadcastReceiver {

    private String TAG;

    public MyBroadcaster() {
        // TODO Auto-generated constructor stub
        this.TAG = this.getClass().toString();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG,"Broadcaster init!");
        Log.d(TAG,intent.getAction());
        Intent it = new Intent(context, MyService.class);
        it.putExtra("event", intent.getAction());
        context.startService(it);
        Log.d(TAG,"Broadcaster fin !");
    }
}
