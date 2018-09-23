package id.co.github.yamanmnur.belajarcrud;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.ParsedRequestListener;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class aktivitasbaru extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aktivitasbaru);
        String[] items = {"satu","dua"};
        ArrayAdapter<String> adapter;
        AndroidNetworking.initialize(getApplicationContext());

        AndroidNetworking.get("https://fierce-cove-29863.herokuapp.com/getAnUserDetail/{userId}")
                .addPathParameter("userId", "1")
                .setTag(this)
                .setPriority(Priority.LOW)
                .build()
                .getAsObject(User.class, new ParsedRequestListener<User>() {
                    @Override
                    public void onResponse(User user) {
                        // do anything with response
                        TextView kata = (TextView) findViewById(R.id.hasilRadio);

                        //kata.setText(user.firstname);

                    }
                    @Override
                    public void onError(ANError anError) {
                        // handle error
                    }
                });


        ListView ls = (ListView) findViewById(R.id.listAngka);

        adapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1,items);
        ls.setAdapter(adapter);

    }
    public void bukaToast(View v){

        Toast t = Toast.makeText(this,"sdf",Toast.LENGTH_LONG);
        t.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
        t.show();
        AndroidNetworking.get("https://fierce-cove-29863.herokuapp.com/getAllUsers/{pageNumber}")
                .addPathParameter("pageNumber", "0")
                .addQueryParameter("limit", "10")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // do anything with response
                        TextView kata = (TextView) findViewById(R.id.hasilRadio);

                        kata.setText(response.toString());

                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });
    }

    public void klikHasilRadio(View v){
        TextView hasilRadio = (TextView) findViewById(R.id.hasilRadio);
        RadioGroup rdJenisKelamin = (RadioGroup) findViewById(R.id.jenisKelamin);

        int id = rdJenisKelamin.getCheckedRadioButtonId();
        String s = "";

        if (id == R.id.pria){
            s = "laki-laki";
        }else if(id == R.id.wanita){
            s = "perempuan";
        }else{
            s = "anda tidak memilih";
        }
        Toast t = Toast.makeText(this, "sdfsdf", Toast.LENGTH_SHORT);

                t.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
                t.show();
        hasilRadio.setText(s);

    }
    public void klikHasil(View v){
        TextView tvlihat = (TextView) findViewById(R.id.lihat);
        CheckBox cbjv = (CheckBox) findViewById(R.id.jv);
        CheckBox cbph = (CheckBox) findViewById(R.id.ph);
        String s="";
        if (cbjv.isChecked()){
            s= "Java";
        }
        else if (cbph.isChecked()){
            s=s + "PHP";
        }
        tvlihat.setText(s);
    }

}
