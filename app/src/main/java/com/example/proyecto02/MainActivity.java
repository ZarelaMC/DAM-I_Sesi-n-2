package com.example.proyecto02;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyecto02.entity.User;
import com.example.proyecto02.service.ServiceUser;
import com.example.proyecto02.util.ConnectionRest;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Spinner   spnUsuarios;
    ArrayAdapter<String> adaptadorUsuarios;
    ArrayList<String> listaUsuarios = new ArrayList<String>();

    Button   btnFiltrar;
    TextView txtResultado;

    //conecta al servicio REST
    ServiceUser serviceUser;

    private List<User> listaTotalUsuarios;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Relaciona las variables con las variables de la GUI
        adaptadorUsuarios = new ArrayAdapter<String>(
                this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                listaUsuarios);
        spnUsuarios = findViewById(R.id.spnUsuarios);
        spnUsuarios.setAdapter(adaptadorUsuarios);

        btnFiltrar = findViewById(R.id.btnFiltrar);
        txtResultado = findViewById(R.id.txtResultado);

        //Conecta al servicio REST
        serviceUser = ConnectionRest.getConnecionRetrofit().create(ServiceUser.class);

        cargaUsuarios();

        btnFiltrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = spnUsuarios.getSelectedItem().toString();
                int position = spnUsuarios.getSelectedItemPosition();
                String id = item.split("-")[0];
                String nombre = item.split("-")[1];

                User objUserSeleccionado = listaTotalUsuarios.get(position);

                String salida =  "Users: \n\n";
                salida +=  "Position  " + position +"\n";
                salida +=  "Id  " + id +"\n";
                salida +=  "Name  " + nombre +"\n";
                salida +=  "UserName  " + objUserSeleccionado.getUsername() +"\n";
                salida +=  "Email  " + objUserSeleccionado.getEmail() +"\n";
                salida +=  "Phone  " + objUserSeleccionado.getPhone() +"\n";
                salida += "Website " + objUserSeleccionado.getWebsite() + "\n";
                salida += "Street " + objUserSeleccionado.getAddress().getStreet() + "\n";
                salida += "Suite " + objUserSeleccionado.getAddress().getSuite() + "\n";
                salida += "City " + objUserSeleccionado.getAddress().getCity() + "\n";
                salida += "ZipCode " + objUserSeleccionado.getAddress().getZipcode() + "\n";
                salida += "Lat " + objUserSeleccionado.getAddress().getGeo().getLat() + "\n";
                salida += "Lng " + objUserSeleccionado.getAddress().getGeo().getLng() + "\n";

                txtResultado.setText(salida);

            }
        });


    }

    void cargaUsuarios(){
        Call<List<User>> call = serviceUser.listaUsuarios();
        call.enqueue(new Callback<List<User>>() {
            @Override
            //CUANDO HAY RESPUESTA
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()){
                    listaTotalUsuarios = response.body();
                    for(User x:listaTotalUsuarios){
                        listaUsuarios.add(x.getId() + " - " + x.getName());
                    }
                    adaptadorUsuarios.notifyDataSetChanged();
                }
            }

            //CUANDO NO HAY RESPUESTAg
            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });

    }

}