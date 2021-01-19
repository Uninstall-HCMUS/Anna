package com.example.anna.model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.anna.MainActivity;
import com.example.anna.PreBeginActivity;
import com.example.anna.R;

import java.util.List;

public class CustomDepartmentAdapter extends ArrayAdapter<String> {
    private Context context;
    private int resource;

    List<String> listDepartment;

    public CustomDepartmentAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, resource, objects);

        this.context = context;
        this.resource = resource;
        this.listDepartment = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(R.layout.custom_department,null);
            TextView textView = row.findViewById(R.id.departmentTxt);

            textView.setText(listDepartment.get(position).toString());
        }
        return  row;
    }

}
