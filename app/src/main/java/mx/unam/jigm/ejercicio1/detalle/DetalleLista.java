package mx.unam.jigm.ejercicio1.detalle;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import mx.unam.jigm.ejercicio1.ActivityDetail;
import mx.unam.jigm.ejercicio1.MainActivity;
import mx.unam.jigm.ejercicio1.R;

// para mostrar el usuario selecionado d ela lista.

public class DetalleLista extends AppCompatActivity implements View.OnClickListener {
    ImageView imagenI;
    TextView texto,t;
    String suser,txt;
    int simagen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_lista);
        //se referencia en java los elementos XML
        imagenI= (ImageView) findViewById(R.id.mItemImage);
        texto= (TextView) findViewById(R.id.mItemText);
        findViewById(R.id.BtnRegresa).setOnClickListener(this);
        Bundle bundle = getIntent().getExtras();
        //se obtienen los datos de la activity anterior
        suser= getIntent().getStringExtra("dato");
        simagen=getIntent().getExtras().getInt("imagen");
        //se asignan valores a los elementos a mostrar
        imagenI.setImageResource(simagen);
        texto.setText("Usuario: "+suser.toUpperCase());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.BtnRegresa:
                Intent intent= new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                break;
        }
    }
}
