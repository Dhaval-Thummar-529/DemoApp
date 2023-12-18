package com.example.demoapp;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.demoapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private LinearLayout col1;
    private LinearLayout col2;
    private Button generate;
    private EditText inputField;

    private List<Integer> inputItems = new ArrayList<Integer>();
    private List<Integer> randomItems = new ArrayList<Integer>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        initialize();
    }

    private void initialize() {
        col1 = findViewById(R.id.ll_ordered);
        col2 = findViewById(R.id.ll_random);
        inputField = findViewById(R.id.edtCount);
        generate = findViewById(R.id.btn_generate);

        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onGenerateClick();
            }
        });
    }

    private void onGenerateClick() {
        if (inputField.getText().toString().isEmpty()) {
            Toast.makeText(MainActivity.this, "Please input value between 0 to 50!", Toast.LENGTH_SHORT).show();
        } else if (Integer.parseInt((inputField.getText().toString())) > 50) {
            Toast.makeText(MainActivity.this, "Please input value between 0 to 50!", Toast.LENGTH_SHORT).show();
        } else {
            for (int i = 0; i < Integer.parseInt((inputField.getText().toString())); i++) {
                inputItems.add(i + 1);
            }
            createButtons(inputItems, "ordered");
            generate.setBackgroundTintList(ColorStateList.valueOf(this.getColor(R.color.grey)));
            generate.setEnabled(false);
        }
    }

    private void createButtons(List<Integer> items, String from) {

        if (from == "ordered") {
            col1.removeAllViews();
        } else {
            col2.removeAllViews();
        }
        for (int i = 0; i < items.size(); i++) {

            LinearLayout.LayoutParams rlParam = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            rlParam.setMargins(12, 12, 12, 12);

            Button listButton = new Button(this);
            listButton.setText("" + (items.get(i)));
            listButton.setLayoutParams(rlParam);
            listButton.setBackground(this.getDrawable(R.drawable.button_gb));
            listButton.setTextColor(this.getColor(R.color.white));
            if (from == "ordered") {
                if (randomItems != null && randomItems.contains(items.get(i))) {
                    listButton.setBackgroundTintList(ColorStateList.valueOf(this.getColor(R.color.blue)));
                    listButton.setEnabled(false);
                } else {
                    listButton.setBackgroundTintList(ColorStateList.valueOf(this.getColor(R.color.red)));
                }
                int finalI = i;
                listButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onItemButtonClick(items.get(finalI));
                        createButtons(randomItems, "random");
                        createButtons(inputItems, "ordered");
                    }
                });
                col1.addView(listButton);
            } else {
                listButton.setBackgroundTintList(ColorStateList.valueOf(this.getColor(R.color.green)));
                col2.addView(listButton);
            }
        }
    }

    private void onItemButtonClick(int btnCount) {

        Random r = new Random();
        int res = r.nextInt((inputItems.size() + 1) - 1) + 1;
        boolean check = true;
        while (check) {
            if (randomItems.contains(res)) {
                res = r.nextInt((inputItems.size() + 1) - 1) + 1;
            } else {
                check = false;
            }
        }
        randomItems.add(res);
    }

}