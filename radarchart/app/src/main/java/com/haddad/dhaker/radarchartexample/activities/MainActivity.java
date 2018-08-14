package com.haddad.dhaker.radarchartexample.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import com.haddad.dhaker.radarchart2.RadarChart;
import com.haddad.dhaker.radarchartexample.R;
import com.haddad.dhaker.radarchartexample.global.Utils;

public class MainActivity extends AppCompatActivity {
    private Button mButtonRandomValues;
    private Button mButtonDoubleChart;
    private SeekBar mSeekBarA;
    private SeekBar mSeekBarB;
    private SeekBar mSeekBarC;
    private SeekBar mSeekBarD;
    private SeekBar mSeekBarE;
    private RadarChart mRadarChart;

    private static int[] mCharacteristicArray = new int[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeViewComponents();
        initializeListener();
    }

    private void initializeListener() {
        mButtonRandomValues.setOnClickListener(getOnCLickListener());
        mButtonDoubleChart.setOnClickListener(getDoubleChartClickListener());
        mSeekBarA.setOnSeekBarChangeListener(getListener());
        mSeekBarB.setOnSeekBarChangeListener(getListener());
        mSeekBarC.setOnSeekBarChangeListener(getListener());
        mSeekBarD.setOnSeekBarChangeListener(getListener());
        mSeekBarE.setOnSeekBarChangeListener(getListener());
    }

    private void initializeViewComponents() {
        mButtonRandomValues = findViewById(R.id.b_random_values);
        mButtonDoubleChart = findViewById(R.id.b_double_chart);
        mSeekBarA = findViewById(R.id.sb_a);
        mSeekBarB = findViewById(R.id.sb_b);
        mSeekBarC = findViewById(R.id.sb_c);
        mSeekBarD = findViewById(R.id.sb_d);
        mSeekBarE = findViewById(R.id.sb_e);
        mRadarChart = findViewById(R.id.cv_radar_chart);
    }

    private void initializeValues() {
        mCharacteristicArray = Utils.randomValues(mCharacteristicArray.length);
        mRadarChart.setChart1CharacteristicArray(mCharacteristicArray);
        setSeekBarsValues(mCharacteristicArray);
    }

    private void setSeekBarsValues(int[] values) {
        mSeekBarA.setProgress(values[0]);
        mSeekBarB.setProgress(values[1]);
        mSeekBarC.setProgress(values[2]);
        mSeekBarD.setProgress(values[3]);
        mSeekBarE.setProgress(values[4]);
    }

    public SeekBar.OnSeekBarChangeListener getListener() {
        return new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(final SeekBar seekBar, final int i, boolean b) {
                switch (seekBar.getId()) {
                    case R.id.sb_a:
                        mCharacteristicArray[0] = i;
                        break;

                    case R.id.sb_b:
                        mCharacteristicArray[1] = i;
                        break;

                    case R.id.sb_c:
                        mCharacteristicArray[2] = i;
                        break;

                    case R.id.sb_d:
                        mCharacteristicArray[3] = i;
                        break;

                    case R.id.sb_e:
                        mCharacteristicArray[4] = i;
                        break;
                }
                mRadarChart.setChart1CharacteristicArray(mCharacteristicArray);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };
    }

    public View.OnClickListener getOnCLickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initializeValues();
            }
        };
    }

    public View.OnClickListener getDoubleChartClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Main2Activity.class);
                MainActivity.this.startActivity(i);
            }
        };
    }

}