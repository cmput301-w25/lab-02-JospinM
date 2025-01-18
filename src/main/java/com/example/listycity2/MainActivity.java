package com.example.listycity2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;

    @Override
    @SuppressLint("MissingInflatedId") protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        /*
         * Taken From: https://stackoverflow.com/questions/34710770/how-to-change-action-bar-title
         * Authored By: piotrek1543
         * Taken On: January 17, 2024
         * */

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("ListyCity");

        cityList = findViewById(R.id.city_list);
        cityList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        String[] cities = {"Edmonton", "Montréal"};

        dataList = new ArrayList<String>();
        dataList.addAll(Arrays.asList(cities));

        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);
        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                cityList.setItemChecked(position, true);
                cityAdapter.notifyDataSetChanged();
            }
        });

        Button addCity = findViewById(R.id.addCity);
        EditText newCity = findViewById(R.id.enterCity);
        Button confirm = findViewById(R.id.confirm);

        addCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newCity.setVisibility(View.VISIBLE);
                confirm.setVisibility(View.VISIBLE);
                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!newCity.getText().toString().isEmpty()) {
                            dataList.add(newCity.getText().toString());

                            newCity.setText("");
                            newCity.setVisibility(View.GONE);
                            confirm.setVisibility(View.GONE);

                            cityAdapter.notifyDataSetChanged();
                        }
                    }
                });
            }
        });

        Button deleteCity = findViewById(R.id.deleteCity);

        cityList.setOnItemClickListener((parent, view, position, id) -> {
            deleteCity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int selectedPosition = cityList.getCheckedItemPosition();

                    if(selectedPosition != ListView.INVALID_POSITION) {
                        /*
                         * Taken From: https://stackoverflow.com/questions/27560032/android-deleting-row-from-custom-listview-on-button-click
                         * Authored By: Darpan
                         * Taken On: January 16, 2024
                         * */

                        /*
                         * Taken From: https://stackoverflow.com/questions/17751129/deselect-selected-item-in-listview
                         * Authored By: Ron
                         * Taken On: January 16, 2024
                         * */

                        dataList.remove(position);
                        cityAdapter.notifyDataSetChanged();
                        cityList.clearChoices();
                        cityList.setAdapter(cityAdapter);
                    }
                }
            });
        });
    }
}