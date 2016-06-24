package mx.unam.jigm.ejercicio1.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import mx.unam.jigm.ejercicio1.model.ModelTimestamp;
import mx.unam.jigm.ejercicio1.util.PreferenceUtil;

/**
 * Created by Mario on 23/06/2016.
 */
public class ServiceTimer extends Service {
    public static final String TAG = "unam_cont";
    public static final String ACTION_SEND_TIMER="mx.unam.jigm.ejercicio.SEND_TIMER";
    private PreferenceUtil utilServiceTimer;
    int counter;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable(){
        @Override
        public void run(){
            counter++;
            handler.postDelayed(runnable,1000);
            Intent i = new Intent(ACTION_SEND_TIMER);
            i.putExtra("timer",counter);
            sendBroadcast(i);
         }
    };

    @Nullable

       public int onStartCommand(Intent intent,int flags,int startId){
        Log.d(TAG,"OnstartCommand called:");
        //para preparar la consulta en el sharepreference
        utilServiceTimer =new PreferenceUtil(getApplicationContext());
        ModelTimestamp modelTimestamp = utilServiceTimer.getTimestamp();
        //cargar datos del timestamp si existen en sharePreference
        if (modelTimestamp==null)
        {}
        else
        {
            if (modelTimestamp.timestamp.trim().length() > 0)
            {
                counter=Integer.parseInt(modelTimestamp.timestamp);
            }
            else
            {
                counter=0;
            }
        }
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"Oncreate servicio:");
        handler.post(runnable);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"OnDestroy Servicio:");
        //guardar el ultimo timestamp contado de uso de la app.
        String timeStamp = String.valueOf(counter);
        utilServiceTimer.saveTimestamp(new ModelTimestamp(timeStamp));
        handler.removeCallbacks(runnable);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
