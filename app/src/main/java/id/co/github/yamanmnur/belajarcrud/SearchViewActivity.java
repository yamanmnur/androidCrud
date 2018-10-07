package id.co.github.yamanmnur.belajarcrud;

import android.app.SearchManager;
import android.content.Context;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchViewActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private TextView hasil;
    private ArrayList<DataPegawai> dataPegawais;
    private RecyclerView recyclerView;
    private  SwipeRefreshLayout swipe;
    ListPegawaiAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view2);
        Toolbar toolbar =(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        hasil = (TextView) findViewById(R.id.outout);
        recyclerView = (RecyclerView) findViewById(R.id.readAllData);
        dataPegawais = new ArrayList<>();

        AndroidNetworking.initialize(getApplicationContext());
       swipe = (SwipeRefreshLayout) findViewById(R.id.refreshData);
        //callData();
        adapter = new ListPegawaiAdapter( dataPegawais );
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        swipe.setOnRefreshListener(this);
        swipe.post(new Runnable() {
            @Override
            public void run() {
                swipe.setRefreshing(true);
                callData();
                swipe.setRefreshing(false);

            }
        });

    }
    public void callData(){
        dataPegawais.clear();
        adapter.notifyDataSetChanged();
        AndroidNetworking.get("http://10.42.0.1:8000/androidCrudApi/read.php")
                .setPriority(Priority.MEDIUM)
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
                        adapter.notifyDataSetChanged();
                        swipe.setRefreshing(false);

                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        final SearchView searchview = (SearchView) searchItem.getActionView();
        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                hasil.setText("Hasil Pencarian : "+query);
                Toast.makeText(SearchViewActivity.this, query, Toast.LENGTH_SHORT).show();
                searchview.clearFocus();
               cariData(query);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }

        });
        searchview.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                callData();
                return true;
            }
        });
        
        return true;
//        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        callData();
        super.onBackPressed();
    }

    public void cariData(String query){
        AndroidNetworking.get("http://10.42.0.1:8000/androidCrudApi/cariData.php")
                .addQueryParameter("nama", query)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try{
                            dataPegawais.clear();
                            adapter.notifyDataSetChanged();
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

    @Override
    public void onRefresh() {
        callData();
        swipe.setRefreshing(false);

    }
}
