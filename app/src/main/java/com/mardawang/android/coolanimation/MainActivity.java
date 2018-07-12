package com.mardawang.android.coolanimation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * author mardawang
 * <p>
 * email:wy363681759@163.com
 * <p>
 * date: 2018/5/29
 * <p>
 */
public class MainActivity extends AppCompatActivity {

    private EditText mEt_p1x;
    private EditText mEt_p1y;
    private EditText mEt_p1_speed;
    private EditText mEt_p2x;
    private EditText mEt_p2y;
    private EditText mEt_p2_speed;
    private Spinner mSpinner_color;
    private Spinner mSpinner_type;
    private TextView mTv_godraw;
    private AnimationView mAnimate_view;
    private ArrayList<String> spinnerMenu;
    private ArrayList<String> spinnerColor;
    private ArrayList<float[]> drawMenu;

    private boolean firstIn = true;
    private float[] data1 = {350, 350, 200, 200, 0.05f, 0.35f};
    private float[] data2 = {200, 400, 400, 200, 0.4f, 0.4f};
    private float[] data3 = {350, 350, 350, 350, 0.35f, 0.05f};
    private float[] data4 = {400, 200, 200, 400, 0.1f, 0.05f};
    private float[] data5 = {350, 350, 350, 350, 0.5f, 0.15f};
    private float[] data6 = {90, 90, 350, 350, 0.5f, 0.15f};
    private float[] data7 = {200, 200, 350, 350, 0.55f, 0.15f};
    private float[] data8 = {350, 200, 200, 350, 0.15f, 0.05f};
    private float[] data9 = {350, 350, 150, 150, 1f, 0.15f};
    //    private float[] data10 = {200, 200, 300, 300, 0.56f, 0.03f};
//    private float[] data11 = {300, 120, 120, 220, 0.12f, 0.25f};
    private int mColor_pos = 0;
    private float[] mSelect_type = data1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
        inflateData(mSelect_type);
        mAnimate_view.startDraw(mColor_pos);
    }

    private void initView() {

        mEt_p1x = findViewById(R.id.et_p1x);
        mEt_p1y = findViewById(R.id.et_p1y);
        mEt_p1_speed = findViewById(R.id.et_p1_speed);

        mEt_p2x = findViewById(R.id.et_p2x);
        mEt_p2y = findViewById(R.id.et_p2y);
        mEt_p2_speed = findViewById(R.id.et_p2_speed);

        mSpinner_color = findViewById(R.id.spinner_color);
        mSpinner_type = findViewById(R.id.spinner_type);
        mTv_godraw = findViewById(R.id.tv_godraw);
        mAnimate_view = findViewById(R.id.animate_view);

        SpinnerAdapter adapter_color = new ArrayAdapter(this, R.layout.item_spinner, spinnerColor);
        mSpinner_color.setAdapter(adapter_color);
        mSpinner_color.setSelection(0);
        mSpinner_color.setOnItemSelectedListener(selectedListener_color);

        SpinnerAdapter adapter_type = new ArrayAdapter(this, R.layout.item_spinner, spinnerMenu);
        mSpinner_type.setAdapter(adapter_type);
        mSpinner_type.setSelection(0);
        mSpinner_type.setOnItemSelectedListener(selectedListener_type);

        mAnimate_view.setDrawingListener(drawingListener);

        mTv_godraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float p1xLen = Float.valueOf(mEt_p1x.getText().toString());
                float p1yLen = Float.valueOf(mEt_p1y.getText().toString());
                float p2xLen = Float.valueOf(mEt_p2x.getText().toString());
                float p2yLen = Float.valueOf(mEt_p2y.getText().toString());
                float p1speed = Float.valueOf(mEt_p1_speed.getText().toString());
                float p2speed = Float.valueOf(mEt_p2_speed.getText().toString());
                mAnimate_view.setParam(p1xLen, p1yLen, p2xLen, p2yLen, p1speed, p2speed);
                mAnimate_view.startDraw(mColor_pos);
            }
        });
    }

    private void initData() {
        spinnerMenu = new ArrayList<>();
        spinnerMenu.add("一号图案");
        spinnerMenu.add("二号图案");
        spinnerMenu.add("三号图案");
        spinnerMenu.add("四号图案");
        spinnerMenu.add("五号图案");
        spinnerMenu.add("六号图案");
        spinnerMenu.add("七号图案");
        spinnerMenu.add("八号图案");
//        spinnerMenu.add("九号图案");
//        spinnerMenu.add("十号图案");
        spinnerMenu.add("终极图案");

        spinnerColor = new ArrayList<>();
        spinnerColor.add("白色");
        spinnerColor.add("紫色");
        spinnerColor.add("蓝色");
        spinnerColor.add("绿色");
        spinnerColor.add("橙色");
        spinnerColor.add("红色");

        drawMenu = new ArrayList<>();
        drawMenu.add(data1);
        drawMenu.add(data2);
        drawMenu.add(data3);
        drawMenu.add(data4);
        drawMenu.add(data5);
        drawMenu.add(data6);
        drawMenu.add(data7);
        drawMenu.add(data8);
        drawMenu.add(data9);
//        drawMenu.add(data10);
//        drawMenu.add(data11);
    }

    private AnimationView.DrawingListener drawingListener = new AnimationView.DrawingListener() {
        @Override
        public void drawStart() {
            mSpinner_type.setClickable(false);
            mTv_godraw.setTextColor(getResources().getColor(R.color.bg_gray6));
            mTv_godraw.setClickable(false);
        }

        @Override
        public void drawOver() {
            mTv_godraw.setBackgroundColor(getResources().getColor(R.color.bg_blue));
            mTv_godraw.setClickable(true);
            mSpinner_type.setClickable(true);
        }
    };

    private AdapterView.OnItemSelectedListener selectedListener_color = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (parent.getId() == R.id.spinner_color) {
                if (position < 0 || position >= spinnerColor.size())
                    return;

                if (firstIn) {
                    firstIn = false;
                } else {
                    inflateData(mSelect_type);
                    mColor_pos = position;
                    mAnimate_view.startDraw(mColor_pos);
                }
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            Log.d("MainActivity", "onNothingSelected");
        }
    };

    private AdapterView.OnItemSelectedListener selectedListener_type = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (parent.getId() == R.id.spinner_type) {
                if (position < 0 || position >= drawMenu.size())
                    return;

                if (firstIn) {
                    firstIn = false;
                } else {
                    mSelect_type = drawMenu.get(position);
                    inflateData(mSelect_type);
                    mAnimate_view.startDraw(mColor_pos);
                }
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            Log.d("MainActivity", "onNothingSelected");
        }
    };

    private void inflateData(float[] datas) {
        if (datas == null) {
            datas = mSelect_type;
        }
        mEt_p1x.setText(datas[0] + "");
        mEt_p1y.setText(datas[1] + "");
        mEt_p2x.setText(datas[2] + "");
        mEt_p2y.setText(datas[3] + "");
        mEt_p1_speed.setText(datas[4] + "");
        mEt_p2_speed.setText(datas[5] + "");
        mAnimate_view.setParam(datas[0], datas[1], datas[2], datas[3], datas[4], datas[5]);

    }
}
