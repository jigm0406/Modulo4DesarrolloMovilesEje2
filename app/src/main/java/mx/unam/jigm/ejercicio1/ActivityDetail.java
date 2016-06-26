package mx.unam.jigm.ejercicio1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import mx.unam.jigm.ejercicio1.fragment.fragment_list;
import mx.unam.jigm.ejercicio1.fragment.FragmentProfile;
import mx.unam.jigm.ejercicio1.model.ModelFecha;
import mx.unam.jigm.ejercicio1.service.ServiceTimer;
import mx.unam.jigm.ejercicio1.util.PreferenceUtil;

/**
 * Created by Mario on 12/06/2016.
 */

public class ActivityDetail extends AppCompatActivity implements View.OnClickListener {
    String  userName;
    private TextView txtTimer,txtFechaAcces;
    private PreferenceUtil util;
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int counter =intent.getExtras().getInt("timer");
            txtTimer.setText(String.format("Session length %s seconds",counter));
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //String userName=getIntent().getExtras().getString("key_user");
        userName=getIntent().getExtras().getString("key_user");
        String hello = String.format(getString(R.string.hello),userName);
        //txt.setText(hello);
        findViewById(R.id.btnFragmentA).setOnClickListener(this);
        findViewById(R.id.btnFragmentB).setOnClickListener(this);
        //txtTimer = (TextView) findViewById(R.id.txtTimer);
        txtFechaAcces = (TextView) findViewById(R.id.txtFechaAcceso);
        txtTimer = (TextView) findViewById(R.id.txtTimer);
        util =new PreferenceUtil(getApplicationContext());
        ModelFecha modelFecha = util.getFecha();
        //cargar datos fecha si existen en sharePreference
        if (modelFecha==null)
        {}
        else
            {
             if (modelFecha.fechaAcceso.trim().length() > 0)
            {
                  txtFechaAcces.setText(R.string.txtlastdate+modelFecha.fechaAcceso);
            }
                else
             {
                 txtFechaAcces.setText(R.string.txtoneacces);
             }
        }
        //guardar la ultima hora y dia de acceso.
            String fechaAcc = new SimpleDateFormat("dd-MM-yy hh:mm").format(new Date());
            util.saveFecha(new ModelFecha(fechaAcc));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnFragmentA:
                changeFragmentA();
                break;
            case R.id.btnFragmentB:
                changeFragmentB();
                break;
        }
    }

    private void changeFragmentB() {
        //FragmentProfile f = FragmentProfile.newInstance(" Adios mundo :(");
        getFragmentManager().beginTransaction().replace(R.id.FragmentHolder,new fragment_list()).commit();
        /*FragmentProfile fragment= (FragmentProfile) getFragmentManager().findFragmentById(R.id.fragment_xml);
        if(fragment!=null)
        {
            fragment.changeImage();
        }*/
    }

    private void changeFragmentA() {
       // FragmentProfile f = FragmentProfile.newInstance("Hola mundo");
        FragmentProfile f = FragmentProfile.newInstance(userName);
        getFragmentManager().beginTransaction().replace(R.id.FragmentHolder,f).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ServiceTimer.ACTION_SEND_TIMER);
        registerReceiver(broadcastReceiver,filter);
        Log.d(ServiceTimer.TAG,"OnResume se reinicia el broadcast");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(ServiceTimer.TAG,"OnPause  quitando broadcast");
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(ServiceTimer.TAG,txtTimer.getText().toString());
        stopService(new Intent(getApplicationContext(),ServiceTimer.class));
    }
}
