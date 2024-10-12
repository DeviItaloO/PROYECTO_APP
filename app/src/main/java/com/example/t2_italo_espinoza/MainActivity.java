package com.example.t2_italo_espinoza;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.t2_italo_espinoza.entity.Product;
import com.example.t2_italo_espinoza.service.ProductService;
import com.example.t2_italo_espinoza.util.RestConnection;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    Spinner spnProductos;
    ArrayAdapter<String> adaptadorProductos;
    ArrayList<String> listaProductos = new ArrayList<>();
    Button btnFiltrar;
    TextView txtResultado;

    ProductService productService;

    List<Product> lstProductos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        adaptadorProductos = new ArrayAdapter<>(
                this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                listaProductos
        );
        spnProductos = findViewById(R.id.spnProductos);
        spnProductos.setAdapter(adaptadorProductos);

        txtResultado = findViewById(R.id.txtResultado);
        btnFiltrar = findViewById(R.id.btnFiltrar);

        productService = RestConnection.getConnetion().create(ProductService.class);
        cargaProductos();

        btnFiltrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int idProducto = (int) spnProductos.getSelectedItemId();
                Product producto = lstProductos.get(idProducto);
                String salida = "Producto: " + "\n\n";
                salida += " * ID: " + producto.getId() + "\n\n";
                salida += " * Title: " + producto.getTitle() + "\n\n";
                salida += " * Price: " + producto.getPrice() + "\n\n";
                salida += " * Description: "+ "\n" + producto.getdescription() + "\n\n";
                salida += " * Category: " + producto.getCategory() + "\n\n";
                salida += " * Image: "+ "\n" + producto.getImage() + "\n\n";
                salida += " * Rating => Rate: " + producto.getRating().getRate() + "\n\n";
                salida += " * Rating => Count: " + producto.getRating().getCount() + "\n\n";
                txtResultado.setText(salida);
                Log.d(TAG, "Onclick, Id producto elegido: " + idProducto);
            }
        });
    }
    private void cargaProductos() {
        Call<List<Product>> call = productService.LISTA_DE_PRODUCTOS();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    lstProductos = response.body();
                    for (Product product : lstProductos) {
                        listaProductos.add(product.getId() + " - " + product.getTitle());
                    }
                    adaptadorProductos.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e(TAG, "MainActivity, Error al cargar los productos", t);
            }
        });
    }
}