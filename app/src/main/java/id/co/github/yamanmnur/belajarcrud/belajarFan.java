package id.co.github.yamanmnur.belajarcrud;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.ParsedRequestListener;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class belajarFan extends AppCompatActivity {
    private static final String TAG = "belajarFan";
    private ArrayList<DataPegawai> dataPegawais;
    private RecyclerView recyclerView;

    OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            . writeTimeout(120, TimeUnit.SECONDS)
            .build();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_belajar_fan);
        recyclerView = (RecyclerView) findViewById(R.id.readAllData);

        dataPegawais = new ArrayList<>();


        final SwipeRefreshLayout swipeRefreshLayout  ;
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshData);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                dataPegawais.clear();
                getData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        AndroidNetworking.initialize(getApplicationContext(),okHttpClient);
        getData();
        ListPegawaiAdapter adapter = new ListPegawaiAdapter( dataPegawais );
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
    public void cariData(){
        Intent i = new Intent(this,SearchViewActivity.class);
        startActivity(i);
    }
    public void getData(){
        AndroidNetworking.get("http://10.42.0.1:8000/androidCrudApi/read.php")
                .setPriority(Priority.LOW)
                .getResponseOnlyFromNetwork()
                .setExecutor(Executors.newSingleThreadExecutor())
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try{


                            for (int i = 0 ; i < response.length(); i++){
                                JSONObject data = response.getJSONObject(i);
                                dataPegawais.add(new DataPegawai(
                                        data.getInt("id_pegawai"),
                                        data.getString("nama_pegawai"),
                                        data.getString("posisi_pegawai"),
                                        data.getInt("gajih_pegawai")
                                ));
                            }

                            //return dataPegawais;
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }
    public void masukanData(View v){
        EditText nama = (EditText) findViewById(R.id.nama_pegawai);
        EditText posisi = (EditText) findViewById(R.id.posisi_karyawan);
        EditText gajih = (EditText)  findViewById(R.id.gajih_karyawan);
        final ProgressDialog progress = new ProgressDialog(belajarFan.this);
        progress.setCancelable(false);
        progress.setMessage("Loading ...");
        progress.show();
        AndroidNetworking.post("http://10.42.0.1:8000/androidCrudApi/create.php")
                .addBodyParameter("nama_pegawai",nama.getText().toString())
                .addBodyParameter("posisi_pegawai", posisi.getText().toString())
                .addBodyParameter("gajih_pegawai",gajih.getText().toString())
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progress.dismiss();
                        dataPegawais.clear();
                        getData();
                        Toast.makeText(belajarFan.this, "Data berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(ANError anError) {
                        progress.dismiss();
                        Toast.makeText(belajarFan.this, "Data gagal ditambahkan", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public class AddItemToList extends AsyncTask<Void, String , Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }

}
