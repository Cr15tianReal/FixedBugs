package com.cdp.agenda;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cdp.agenda.db.DbContactos;
import com.cdp.agenda.entidades.Contactos;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EditarActivity extends AppCompatActivity {

    EditText txtTitulo, txtDireccion, txtDescripcion;
    TextView eHora,eFecha;
    Activity actividad;

    Button btnGuarda;
    FloatingActionButton fabEditar, fabEliminar;
    boolean correcto = false;
    Contactos contacto;
    int id = 0;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver);

        txtTitulo = findViewById(R.id.txtTitulo);
        eHora= findViewById(R.id.eHora);
        eFecha= findViewById(R.id.eFecha);
        txtDireccion = findViewById(R.id.txtDireccion);
        txtDescripcion = findViewById(R.id.txtDescripcion);
        btnGuarda = findViewById(R.id.btnGuarda);
        fabEditar = findViewById(R.id.fabEditar);
        fabEditar.setVisibility(View.INVISIBLE);
        fabEliminar = findViewById(R.id.fabEliminar);
        fabEliminar.setVisibility(View.INVISIBLE);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                id = Integer.parseInt(null);
            } else {
                id = extras.getInt("ID");
            }
        } else {
            id = (int) savedInstanceState.getSerializable("ID");
        }

        final DbContactos dbContactos = new DbContactos(EditarActivity.this);
        contacto = dbContactos.verContacto(id);

        if (contacto != null) {
            txtTitulo.setText(contacto.getTitulo());
            eHora.setText(contacto.getHora());
            eFecha.setText(contacto.getFecha());
            txtDireccion.setText(contacto.getDireccion());
            txtDescripcion.setText(contacto.getDescripcion());
        }

        btnGuarda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!txtTitulo.getText().toString().equals("") && !eHora.getText().toString().equals("") &&
                        !eFecha.getText().toString().equals("")) {
                    correcto = dbContactos.editarContacto(id, txtTitulo.getText().toString(), eHora.getText().toString(), eFecha.getText().toString(),
                            txtDireccion.getText().toString(), txtDescripcion.getText().toString()
                            );

                    if(correcto){
                        Toast.makeText(EditarActivity.this, "REGISTRO MODIFICADO", Toast.LENGTH_LONG).show();
                        verRegistro();
                    } else {
                        Toast.makeText(EditarActivity.this, "ERROR AL MODIFICAR REGISTRO", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(EditarActivity.this, "DEBE LLENAR LOS CAMPOS OBLIGATORIOS", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void verRegistro(){
       // Intent intent = new Intent(this, VerActivity.class);
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("ID", id);
        startActivity(intent);
        //Intent intent = new Intent(actividad, MainActivity.class);
        //startActivity(intent);
    }
}