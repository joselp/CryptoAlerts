package com.jp.cryptoalerts;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jp.cryptoalerts.pojos.Price;
import com.jp.cryptoalerts.service.PriceService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    static final String JP_MESSAGE = "jp_message";
    static final String BASE_URL = "https://min-api.cryptocompare.com/";
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addToolBar();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        if (navigationView != null) {
            initDrawer(navigationView);
            // Seleccionar item por defecto
            //seleccionarItem(navigationView.getMenu().getItem(0));
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        final Button petButton = findViewById(R.id.petButton);
//        petButton.setOnClickListener( (View v) -> {
//            Intent intent = new Intent(this,Visor.class);
//            startActivity(intent);
//        });


        petButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Visor.class);
                intent.putExtra(JP_MESSAGE, getString(R.string.hi));

                startActivity(intent);

//                Uri webpage = Uri.parse("http://hermosaprogramacion.blogspot.com");
//                Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
//                startActivity(webIntent);

            }
        });

        Log.i("TEST ---", "AQUI");

       TextView tvPrice = findViewById(R.id.tvPrice);
       tvPrice.setText(getPrice(tvPrice));

    }

    public static String getPrice(final TextView tvPrice){

        final Price price = new Price();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PriceService priceService= retrofit.create(PriceService.class);
        Call<Price> call = priceService.getPrice("BTC", "USD");

        call.enqueue(new Callback<Price>() {
            @Override
            public void onResponse(Call<Price> call, Response<Price> response) {
                price.setUSD(response.body().getUSD());

                tvPrice.setText(response.body().getUSD());

                Log.i("INFO ---- ", response.body().getUSD());

//                for (String key : response.body().getCurrency().keySet()) {
//                    Log.i("KEY ------- ", key + "--");
//                }


            }

            @Override
            public void onFailure(Call<Price> call, Throwable t) {
                t.printStackTrace();
                Log.e("ERROR ------- ", "ERROR");
            }
        });

        return price.getUSD();
    }

    private void addToolBar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            // Poner ícono del drawer toggle
            ab.setHomeAsUpIndicator(R.drawable.ic_menu_foreground);
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (item.getItemId()) {

            case R.id.action_settings:
                Log.i("INFO ----- ", "SETTINGS");
                return true;

            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    private void initDrawer(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        selectItem(menuItem);
                        drawerLayout.closeDrawers();
                        return true;
                    }
                });

    }

    private void selectItem(MenuItem itemDrawer) {
//        Fragment fragmentoGenerico = null;
//        FragmentManager fragmentManager = getSupportFragmentManager();

        switch (itemDrawer.getItemId()) {
            case R.id.menu_opcion_1:
                //fragmentoGenerico = new FragmentoInicio();
                Log.i("INFO ----- ", "MENU 1");
                break;
            case R.id.menu_opcion_2:
                // Fragmento para la sección Cuenta
                Log.i("INFO ----- ", "MENU 2");
                break;
            case R.id.menu_seccion_1:
                // Fragmento para la sección Categorías
                Log.i("INFO ----- ", "MENU S 1");
                break;
            case R.id.menu_seccion_2:
                Log.i("INFO ----- ", "MENU S 2");
                // Iniciar actividad de configuración
                break;
        }

//        if (fragmentoGenerico != null) {
//            fragmentManager
//                    .beginTransaction()
//                    .replace(R.id.contenedor_principal, fragmentoGenerico)
//                    .commit();
//        }

        // Setear título actual
        setTitle(itemDrawer.getTitle());
    }

}
