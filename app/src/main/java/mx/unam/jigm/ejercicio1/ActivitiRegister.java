package mx.unam.jigm.ejercicio1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import mx.unam.jigm.ejercicio1.model.ModelUserBdd;
import mx.unam.jigm.ejercicio1.sql.UsersDataSource;



/**
 * Created by Mario on 25/06/2016.
 */
public class ActivitiRegister extends AppCompatActivity {
    private UsersDataSource userDataSource;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityregister);
        userDataSource = new UsersDataSource(getApplicationContext());

        final EditText mUser = (EditText) findViewById(R.id.textUser_Register);
        final EditText mPwd = (EditText) findViewById(R.id.textPassword_Register);
        findViewById(R.id.botonResgister_User).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String usr = mUser.getText().toString();
                String passwd=mPwd.getText().toString();
                String datenow= new SimpleDateFormat("dd-MM-yyyy hh:mm").format(new Date());
                if(!TextUtils.isEmpty(usr)&& !TextUtils.isEmpty(passwd))
                {
                    ModelUserBdd modelUserin = new ModelUserBdd();
                    modelUserin.nameusr=usr;
                    modelUserin.pwdusr=passwd;
                    modelUserin.last_sessionusr=datenow;
                    modelUserin.time_inusr="0";
                    userDataSource.saveUser(modelUserin);
                    finish();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),getResources().getText(R.string.errorusrpwd),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
