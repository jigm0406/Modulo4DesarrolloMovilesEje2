package mx.unam.jigm.ejercicio1;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
//import mx.unam.jigm.service.ServiceTimer;
import java.text.SimpleDateFormat;
import java.util.Date;

import mx.unam.jigm.ejercicio1.model.ModelUser;
import mx.unam.jigm.ejercicio1.service.ServiceTimer;
import mx.unam.jigm.ejercicio1.util.PreferenceUtil;

/**
 * Created by Mario on 12/06/2016.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mUser;
    private EditText mPassword;
    private View loading;
    private PreferenceUtil util;//preferenceUtil,
    private Boolean ChkBoolean;
    private CheckBox checkbox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         mUser=(EditText) findViewById(R.id.activity_main_user);
        mPassword= (EditText) findViewById(R.id.activity_main_password);
        findViewById(R.id.activity_main_login).setOnClickListener(this);
        loading=findViewById(R.id.progress);
        findViewById(R.id.btnRegisterLogin).setOnClickListener(this);
        checkbox = (CheckBox) findViewById(R.id.chkRememberMe);

         util =new PreferenceUtil(getApplicationContext());
        ModelUser modelUser = util.getUser();
          //cargar datos de usuario y password si existen en sharePreference
        if (modelUser==null){}
            else
        {
            if (modelUser.userName.trim().length() > 0 && modelUser.password.trim().length() > 0)
            {
                mUser.setText(modelUser.userName);
                mPassword.setText(modelUser.password);
             }
        }
       }
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.activity_main_login:
                processData();
                break;
        }
    }

    private void processData() {
        final String user= mUser.getText().toString();
        final String pass = mPassword.getText().toString();
        loading.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loading.setVisibility(View.GONE);
                //comprobar que no esten vacias las casillas de usuario y contraseña
                if(user.trim().length()>0 && pass.trim().length()>0)
                {
                    //guardar usuario y pasword en sharedpreference si eligio el usuario
                             if (checkbox.isChecked()){
                                 util.saveUser(new ModelUser(user,pass));
                             }
                      // enviar informacion e ir a la siguiente activity
                    Toast.makeText(getApplicationContext(),"Bienvenido",Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(getApplicationContext(),ActivityDetail.class);
                    intent.putExtra("key_user",user);
                    startActivity(intent);
                    startService(new Intent(getApplicationContext(), ServiceTimer.class));
                    Log.d(ServiceTimer.TAG,"inicia servicio");
                }
                else
                    Toast.makeText(getApplicationContext(),R.string.noUserPassword,Toast.LENGTH_SHORT).show();
            }
        },1000*1);
    }

}
