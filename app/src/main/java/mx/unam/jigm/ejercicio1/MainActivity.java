package mx.unam.jigm.ejercicio1;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import mx.unam.jigm.ejercicio1.model.ModelUser;
import mx.unam.jigm.ejercicio1.model.ModelUserBdd;
import mx.unam.jigm.ejercicio1.service.ServiceTimer;
import mx.unam.jigm.ejercicio1.util.PreferenceUtil;
import mx.unam.jigm.ejercicio1.sql.UsersDataSource;

/**
 * Created by Mario on 12/06/2016.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mUser;
    private EditText mPassword;
    private View loading;
    private PreferenceUtil util;
    private CheckBox checkbox;
    private UsersDataSource userDataSource;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         mUser=(EditText) findViewById(R.id.activity_main_user);
        mPassword= (EditText) findViewById(R.id.activity_main_password);
        findViewById(R.id.activity_main_login).setOnClickListener(this);
        findViewById(R.id.btnCerrarSesion).setOnClickListener(this);
        loading=findViewById(R.id.progress);
        findViewById(R.id.btnRegisterLogin).setOnClickListener(this);
        checkbox = (CheckBox) findViewById(R.id.chkRememberMe);
        userDataSource = new UsersDataSource(getApplicationContext());
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
            case R.id.btnCerrarSesion:
                cerrarSesiones();
                break;
            case R.id.btnRegisterLogin:
                launchRegister();
                break;
        }
    }
    private void launchRegister() {
        Intent intent = new Intent(getApplicationContext(), ActivitiRegister.class);
        startActivity(intent);
    }
    private void cerrarSesiones(){
        stopService(new Intent(getApplicationContext(),ServiceTimer.class));
        util.clearProfile();
        Toast.makeText(getApplicationContext(),R.string.TxtBorrarShared,Toast.LENGTH_LONG).show();
        //finish();
    }
    private void processData()
    {
        final String user= mUser.getText().toString();
        final String pass = mPassword.getText().toString();
        loading.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run() {
                loading.setVisibility(View.GONE);

                //comprobar que no esten vacias las casillas de usuario y contraseña
                if(user.trim().length()>0 && pass.trim().length()>0)
                {
                    //revisar en la bdd si es usuario registrado
                    List<ModelUserBdd> modelUserList= userDataSource.getUser(user,pass);
                    if(!modelUserList.isEmpty())
                    {
                        //guardar usuario y pasword en sharedpreference si eligio el usuario
                        if (checkbox.isChecked()) {
                            util.saveUser(new ModelUser(user, pass));
                        }
                        // enviar informacion e ir a la siguiente activity
                        Toast.makeText(getApplicationContext(), R.string.txtwelcome, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), ActivityDetail.class);
                        intent.putExtra("key_user", user);
                        startActivity(intent);
                        //iniciar servicio timestamp
                        startService(new Intent(getApplicationContext(), ServiceTimer.class));
                        Log.d(ServiceTimer.TAG, "inicia servicio");
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), R.string.erroregister, Toast.LENGTH_SHORT).show();
                    }
                }
                else
                    Toast.makeText(getApplicationContext(),R.string.noUserPassword,Toast.LENGTH_SHORT).show();
            }
        },1000*1);
    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
         //service.stopSelf();
    }
}
