package id.co.github.yamanmnur.belajarcrud;

import android.app.ProgressDialog;
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

public class belajarFan extends AppCompatActivity {
    private static final String TAG = "belajarFan";

    private List<DataPegawai> dataPegawai;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_belajar_fan);
        recyclerView = (RecyclerView) findViewById(R.id.readAllData);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dataPegawai = new ArrayList<>();


        AndroidNetworking.initialize(getApplicationContext());
        getData();

    }
    public void getData(){
        AndroidNetworking.get("http://10.42.0.1:8000/androidCrudApi/read.php")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try{

                            for (int i = 0 ; i < response.length(); i++){
                                JSONObject data = response.getJSONObject(i);
                                dataPegawai.add(new DataPegawai(
                                        data.getInt("id_pegawai"),
                                        data.getString("nama_pegawai"),
                                        data.getString("posisi_pegawai"),
                                        data.getInt("gajih_pegawai")
                                ));
                            }
                            ListPegawaiAdapter adapter = new ListPegawaiAdapter( dataPegawai );
                            recyclerView.setAdapter(adapter);
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
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progress.dismiss();
                        Toast.makeText(belajarFan.this, "Data berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(belajarFan.this, "Data gagal ditambahkan", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}