package mx.unam.jigm.ejercicio1.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.widget.Toast;

import mx.unam.jigm.ejercicio1.model.ModelFecha;
import mx.unam.jigm.ejercicio1.model.ModelTimestamp;
import mx.unam.jigm.ejercicio1.model.ModelUser;

/**
 * Created by Mario on 22/06/2016.
 */
public class PreferenceUtil {
    //archivo de preferencias
    private static final String FILE_NAME ="app_preference";
    private final SharedPreferences sp;

//define el nombre de las preferencias, mode private nadie mas que la aplicacion puede usarlo
    public PreferenceUtil(Context context) {
        sp=context.getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE);
    }
    //para guardar los datos en el preference
    public void saveUser (ModelUser modelUser) {
        //se valida que no venga vacio
        if (modelUser.userName.trim().length()>0 && modelUser.password.trim().length()>0)
        {
            sp.edit().putString("user_name", modelUser.userName).apply();
            sp.edit().putString("user_password", modelUser.password).apply();
        }
    }
    public ModelUser getUser()
    {
        String mUser=sp.getString("user_name",null);
        String mPassword=sp.getString("user_password",null);
        if (TextUtils.isEmpty(mUser)|| TextUtils.isEmpty(mPassword))
            return null;
        return new ModelUser(mUser,mPassword);
    }
    public void saveFecha (ModelFecha modelFecha){
        if (modelFecha.fechaAcceso.trim().length()>0 )
        {
            sp.edit().putString("fecha_acceso", modelFecha.fechaAcceso).apply();
        }
    }
    public ModelFecha getFecha()
    {
        String mFechaAcceso=sp.getString("fecha_acceso",null);
        if (TextUtils.isEmpty(mFechaAcceso))
            return null;
        return new ModelFecha(mFechaAcceso);
    }
    public void saveTimestamp (ModelTimestamp modelTimestamp){
        if (modelTimestamp.timestamp.trim().length()>0 )
        {
            sp.edit().putString("timestamp", modelTimestamp.timestamp).apply();
        }
    }
    public ModelTimestamp getTimestamp()
    {
        String mTimestamp=sp.getString("timestamp",null);
        if (TextUtils.isEmpty(mTimestamp))
            return null;
        return new ModelTimestamp(mTimestamp);
    }
}
