package com.example.stackedbarchart;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private StackedBarChartView sbv;
    private Map<String,float[]> datas;
    int soNhom;
    int soBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        sbv= (StackedBarChartView) findViewById(R.id.drawId);
        Button btn=(Button)findViewById(R.id.btnNhan);


        datas=new HashMap<String,float[]>();
        float[] d1={3f,1f,2f};
        datas.put("2000",d1);
        float[] d2={4f,1f,3f};
        float[] d3={2f,3f,2f};
        datas.put("2005",d2);
        datas.put("2010",d3);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sbv.update(datas,2,3);
        }
        });
    }
}
