package mx.unam.jigm.ejercicio1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import mx.unam.jigm.ejercicio1.fragment.fragment_list;
import mx.unam.jigm.ejercicio1.fragment.FragmentProfile;
import mx.unam.jigm.ejercicio1.model.ModelFecha;
import mx.unam.jigm.ejercicio1.util.PreferenceUtil;

/**
 * Created by Mario on 12/06/2016.
 */

public class ActivityDetail extends AppCompatActivity implements View.OnClickListener {
    String  userName;
    private TextView txtTimer,txtFechaAcces;
    private PreferenceUtil util;
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
        util =new PreferenceUtil(getApplicationContext());
        ModelFecha modelFecha = util.getFecha();
        //cargar datos fecha si existen en sharePreference
        if (modelFecha==null)
        {}
        else
            {
             if (modelFecha.fechaAcceso.trim().length() > 0)
            {
                  txtFechaAcces.setText("ultima fecha acceso:"+modelFecha.fechaAcceso);
            }
                else
             {
                 txtFechaAcces.setText("Primer acceso");
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
}
