package id.co.github.yamanmnur.belajarcrud;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);



    }

    public void klikHasil(View view){
        TextView hasil = (TextView) findViewById(R.id.hasil);
        CheckBox cbJava = (CheckBox) findViewById(R.id.java);
        CheckBox cbPhp = (CheckBox) findViewById(R.id.php);

        String s = "";
        if (cbJava.isChecked()){
            s = "anda menceklis java";
        }else if(cbPhp.isChecked()){
            s = "anda menceklis php";
        }

        hasil.setText(s);

    }
}
