package id.co.github.yamanmnur.belajarcrud;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class alertDialog extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_dialog);
    }

    public void alertDialogKlik(View v){
        AlertDialog.Builder modal = new AlertDialog.Builder(this);




        modal.setNegativeButton("tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(alertDialog.this, "indit lah", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        modal.setPositiveButton("ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(alertDialog.this, "terimakasih telah percaya kepada saya", Toast.LENGTH_SHORT).show();
            }
        });

        modal.setMessage("ini alert dialog, mau dilanjutkan?");
        AlertDialog alertDialog = modal.create();
        alertDialog.show();
    }
}
