package com.haddad.dhaker.radarchartexample.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.haddad.dhaker.radarchart2.RadarChart;
import com.haddad.dhaker.radarchartexample.R;
import com.haddad.dhaker.radarchartexample.global.Utils;

public class Main2Activity extends AppCompatActivity {

    private Button mButtonRandomChart1;
    private Button mButtonRandomChart2;
    private Button mButtonRandomTowCharts;
    private RadarChart mRadarChart;

    private int[] mChart1 = new int[5];
    private int[] mChart2 = new int[5];

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initializeViewComponents();
        initializeListener();
        initializeValues();
    }

    private void initializeValues() {
        mChart1 = Utils.randomValues(mChart1.length);
        mChart2 = Utils.randomValues(mChart2.length);
        mRadarChart.setChart1CharacteristicArray(mChart1);
        mRadarChart.setChart2CharacteristicArray(mChart2);
    }

    private void initializeListener() {
        mButtonRandomChart1.setOnClickListener(getChart1ClickListener());
        mButtonRandomChart2.setOnClickListener(getChart2ClickListener());
        mButtonRandomTowCharts.setOnClickListener(getDoubleChartsRandomListener());
    }

    private void initializeViewComponents() {
        mButtonRandomChart1 = findViewById(R.id.b_chart_1);
        mButtonRandomChart2 = findViewById(R.id.b_chart_2);
        mButtonRandomTowCharts = findViewById(R.id.b_double_chart);
        mRadarChart = findViewById(R.id.cv_radar_chart_double);
    }

    public View.OnClickListener getChart1ClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mChart1 = Utils.randomValues(mChart1.length);
                mRadarChart.setChart1CharacteristicArray(mChart1);
            }
        };
    }

    public View.OnClickListener getChart2ClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mChart2 = Utils.randomValues(mChart2.length);
                mRadarChart.setChart2CharacteristicArray(mChart2);
            }
        };
    }

    public View.OnClickListener getDoubleChartsRandomListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initializeValues();
            }
        };
    }
}
