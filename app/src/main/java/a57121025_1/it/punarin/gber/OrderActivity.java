package a57121025_1.it.punarin.gber;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends AppCompatActivity {


        Button btnConfirm;
        Spinner spGas;
        RadioButton rdDont,rdHave;
        String userid;
        String text = "Have";
        String size = "";
        int totalprice = 0;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_order);
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            spGas = (Spinner)findViewById(R.id.spGas);
            btnConfirm = (Button)findViewById(R.id.btnConfirm);
            rdDont = (RadioButton)findViewById(R.id.rdDont);
            rdHave = (RadioButton) findViewById(R.id.rdHave);




            btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent get = getIntent();
                    userid = get.getStringExtra("userid");
                    if(spGas.getSelectedItemPosition()==0){
                        totalprice =250;
                        size = "7 KG";
                        if(rdDont.isChecked()){
                            totalprice+=1800;
                            text = "Not Have";
                        }
                    }
                    else if(spGas.getSelectedItemPosition()==1){
                        totalprice =400;
                        size = "15 KG";
                        if(rdDont.isChecked()){
                            totalprice+=2300;
                            text = "Not Have";
                        }
                    }
                    else if(spGas.getSelectedItemPosition()==2){
                        totalprice =1100;
                        size = "48 KG";
                        if(rdDont.isChecked()){
                            totalprice+=4300;
                            text = "Not Have";
                        }
                    }
                    AlertDialog myDialog = new AlertDialog.Builder(OrderActivity.this)
                            .setTitle("Confirm ?")
                            .setMessage("Size : " +size +
                                    "\nGas Tank : "+text+
                                    "\nPrice : "+totalprice + " Baht")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    List<NameValuePair> nv = new ArrayList<NameValuePair>();
                                    HttpClient hc = new DefaultHttpClient();
                                    HttpPost hp = new HttpPost("http://punarin.coolpage.biz/android/order.php");
                                    nv.add(new BasicNameValuePair("size",size));
                                    nv.add(new BasicNameValuePair("have",text));
                                    nv.add(new BasicNameValuePair("totalprice",totalprice+""));
                                    nv.add(new BasicNameValuePair("userid",userid));
                                    try {
                                        hp.setEntity(new UrlEncodedFormEntity(nv));
                                        hc.execute(hp);
                                    } catch (UnsupportedEncodingException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    Toast.makeText(OrderActivity.this, "You Order have been receive.", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
                            .show();
                }
            });
    }
}
