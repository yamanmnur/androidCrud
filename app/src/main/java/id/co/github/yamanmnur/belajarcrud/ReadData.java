package id.co.github.yamanmnur.belajarcrud;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ReadData extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_data);




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
                            ArrayList<DataPegawai> dataPegawais;
                            RecyclerView recyclerView;

                            dataPegawais = new ArrayList<>();
                            recyclerView = (RecyclerView) findViewById(R.id.readAllData);
                            for (int i = 0 ; i < response.length(); i++){
                                JSONObject data = response.getJSONObject(i);
                                dataPegawais.add(new DataPegawai(
                                        data.getInt("id_pegawai"),
                                        data.getString("nama_pegawai"),
                                        data.getString("posisi_pegawai"),
                                        data.getInt("gajih_pegawai")
                                ));
                            }

                            ListPegawaiAdapter adapter = new ListPegawaiAdapter( dataPegawais );
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
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
}
