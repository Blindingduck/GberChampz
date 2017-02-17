package a57121025_1.it.punarin.gber;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btnUser,btnRegis,btnSub,btnCan,btnLogin;
    EditText edUser,edPass,edUserre,edPassre,edPassre2,edAddress;
    ImageView imLogo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Dialog regis = new Dialog(MainActivity.this);
        regis.setContentView(R.layout.popupregister);

        btnUser = (Button) findViewById(R.id.btnLogin);
        btnRegis = (Button) findViewById(R.id.btnRegis);
        btnSub = (Button) regis.findViewById(R.id.btnSubmit);
        btnCan = (Button) regis.findViewById(R.id.btnCancel);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        edUser = (EditText) findViewById(R.id.edUser);
        edPass = (EditText) findViewById(R.id.edPass);
        edUserre = (EditText) regis.findViewById(R.id.edUserre);
        edPassre = (EditText) regis.findViewById(R.id.edPassre);
        edPassre2 = (EditText) regis.findViewById(R.id.edPassre2);
        edAddress = (EditText) regis.findViewById(R.id.edAddress);
        imLogo = (ImageView) findViewById(R.id.imLogo);

        edAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent map = new Intent(MainActivity.this,MapsActivity.class);
                map.putExtra("classAddress","Address");
                startActivity(map);
            }
        });



        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent get = getIntent();
                if(get.hasExtra("MapClass")){
                    edAddress.setText(get.getStringExtra("lat")+" : "+get.getStringExtra("lng"));
                }
                regis.setCancelable(false);
                regis.show();
                Window regissize = regis.getWindow();
                regissize.setLayout(LinearLayoutCompat.LayoutParams.FILL_PARENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
            }
        });

        btnCan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regis.dismiss();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String DeUser = "User";
                String DePass = "Pass";
                String FromUser = edUser.getText().toString();
                String FromPass = edPass.getText().toString();
                if(DeUser==FromUser||DePass==FromPass){
                    if (FromUser!=DeUser){
                        Toast.makeText(MainActivity.this,"User ไม่ถูกต้อง",Toast.LENGTH_LONG).show();
                    }
                    if (DePass!=FromPass){
                        Toast.makeText(MainActivity.this,"Pass ไม่ถูกต้อง",Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(MainActivity.this,"ยินดีต้อนรับสู่ Gber",Toast.LENGTH_LONG).show();
                    Intent Map = new Intent(MainActivity.this,MapsActivity.class);
                    startActivity(Map);
                }
            }
        });




    }
}
