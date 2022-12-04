package edu.northeastern.bottomsheet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //14% x=12000;
        //100%
        int x;
        int y=12000;
        x=(y*100)/14;
        String sX=String.valueOf(x);
        Toast.makeText(this,"x is = "+sX.toUpperCase(),Toast.LENGTH_LONG).show();
    }
}