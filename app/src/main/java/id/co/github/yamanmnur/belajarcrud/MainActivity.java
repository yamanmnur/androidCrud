package id.co.github.yamanmnur.belajarcrud;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import com.android.volley.toolbox.Volley;

import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private  static  final String TAG = MainActivity.class.getName();
    private Button btnRequest;

    private  RequestQueue mRequestQueue;
    private  StringRequest mStringRequest;

    private String url = "http://www.mocky.io/v2/5b9fb5633000004a007b1317";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnRequest = (Button) findViewById(R.id.buttonRequest);

        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendAndRequestResponse();
            }
        });

    }

    public void sendAndRequestResponse(){
        // inisialisasi requestQueue
        mRequestQueue = Volley.newRequestQueue(this);


        mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject obj = new JSONObject(response);

                    //we have the array named hero inside the object
                    //so here we are getting that json array
                    JSONArray heroArray = obj.getJSONArray("users");

                    //now looping through all the elements of the json array

                    //getting the json object of the particular index inside the array
                    JSONObject heroObject = heroArray.getJSONObject(0);

                    //creating a hero object and giving them the values from json object
                    String nama = heroObject.getString("name");

                    TextView kata = (TextView) findViewById(R.id.katatext);

                    kata.setText("nama : " + nama + heroObject.getString("email"));

                }catch (Exception e){

                }




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        mRequestQueue.add(mStringRequest);
    }

}
