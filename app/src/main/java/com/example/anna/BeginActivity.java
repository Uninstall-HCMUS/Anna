package com.example.anna;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.anna.model.CustomCourseAdapter;

import java.util.Arrays;

public class BeginActivity extends AppCompatActivity {
    String selectedDepartment = "";

    ListView listView;
    String[] departmentList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_begin);

        listView = findViewById(R.id.listView_Department);

        departmentList = new String[]{

                "Information Technology",
                "Economics",
                "Mathematics",
                "Biology",
                "Chemistry",
                "Engineering Physics",
                "Law",
                "Hotel Management",
                "Food Technology"
        };

        CustomCourseAdapter customDepartmentAdapter = new CustomCourseAdapter(BeginActivity.this,R.layout.custom_department, Arrays.asList(departmentList));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedDepartment=departmentList[position].toString();
                Toast.makeText(BeginActivity.this,
                        selectedDepartment,
                        Toast.LENGTH_LONG).show();
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

                Intent intent = new Intent(BeginActivity.this,MainActivity.class);
                startActivity(intent);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(getString(R.string.DEPARTMENT),selectedDepartment);
                editor.commit();
                finish();
            }
        });
        listView.setAdapter(customDepartmentAdapter);
    }
}