package com.example.jeremy.myapplication;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.WindowManager;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * Created by jeremy on 01/10/2016.
 */

public class MyService extends Service {

     String ip="";
    private String TAG;
    private static Socket soc;
    private Context context;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent intent, int flags, int startId){
        this.TAG = this.getClass().toString();
        this.context = this.getApplicationContext();
        Log.d(TAG,"Service start!");
        if(soc!=null && soc.connected()){
            Log.d(TAG,"Connexion au serveur existante");
            soc.emit("message", "Old connection");
        }else{
            Log.d(TAG,"Connexion au serveur...");
            try{
                IO.Options opts = new IO.Options();
                opts.forceNew = true;
                opts.reconnection = true;
                soc = IO.socket("http://88.166.207.71:5000");
            } catch (URISyntaxException e) {
                Log.d(TAG,e.toString());
            }
            soc.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

                @Override
                public void call(Object... args) {

                }

            }).on("test", new Emitter.Listener() {

                @Override
                public void call(Object... args) {
                    Log.d(TAG, "test");
                }

            }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {

                @Override
                public void call(Object... args) {}

            }).on("ring", new Emitter.Listener() {

                @Override
                public void call(Object... args) {
                    Log.d(TAG,"Lancement IHM!");
                    /*Lancement de l'IHM avec prise d'appel*/
                    Intent it = new Intent(context, MainActivity.class);
                    it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    it.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED + WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD + WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON + WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
                    it.putExtra("appel", true);
                    context.startActivity(it);
                }

            });
            soc.connect();
            soc.emit("message", "New connection");
        }

        Log.d(TAG,"Service stop!");
        return START_STICKY;
    }
}
