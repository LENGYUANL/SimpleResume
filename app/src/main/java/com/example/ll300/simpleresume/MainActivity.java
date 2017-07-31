package com.example.ll300.simpleresume;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ll300.simpleresume.Mdel.BasicInfo;
import com.example.ll300.simpleresume.Mdel.Education;
import com.example.ll300.simpleresume.Utils.Dateutil;
import com.example.ll300.simpleresume.Utils.ModelUtils;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private BasicInfo basicinfo;
    private List<Education> educations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadData();
        setupUI();
    }

    private void loadData() {
        BasicInfo savedinfo = ModelUtils.read(this, "info", new TypeToken<BasicInfo>(){});
        basicinfo = savedinfo == null ? new BasicInfo() : savedinfo;

        List<Education> savededucation = ModelUtils.read(this,"education", new TypeToken<List<Education>>(){});
        educations = savededucation == null ? new ArrayList<Education>() : savededucation;

    }

    private void setupUI() {
        ImageButton editinfobtn = (ImageButton) findViewById(R.id.edit_info_btn);
        editinfobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditBasicInfo.class);
                startActivityForResult(intent, 100);
            }
        });
        ImageButton addeducationbtn = (ImageButton) findViewById(R.id.add_education_btn);
        addeducationbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditEducationActivity.class);
                startActivityForResult(intent, 101);
            }
        });

        setBasicInfo();
        setupEducations();
    }

    private void setBasicInfo() {
    }

    private void setupEducations() {
        LinearLayout educationslayout = (LinearLayout) findViewById(R.id.education_list);
        educationslayout.removeAllViews();
        for (Education education : educations) {
            View educationView = getLayoutInflater().inflate(R.layout.education_item,null);
            setupEducation(educationView, education);
            educationslayout.addView(educationView);

        }
    }

    private void setupEducation(View educationView, final Education education) {
        String DateString = Dateutil.dateToString(education.startDate) + "~" +
                Dateutil.dateToString(education.endDate);
        TextView txt1 = (TextView) educationView.findViewById(R.id.education_schoolandtime);
        TextView txt2 = (TextView) educationView.findViewById(R.id.education_courses);
        txt1.setText(education.school + education.Major + "(" + DateString + ")");
        txt2.setText(formatItems(education.courses));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case 101:
                    Education education = data.getParcelableExtra("education");
                    updateEducations(education);
                    break;
            }
        }

    }

    private void updateEducations(Education education) {
        boolean found = false;
        for (int i = 0; i < educations.size(); ++i) {
            Education e = educations.get(i);
            if (TextUtils.equals(e.id, education.id)) {
                found = true;
                educations.set(i, education);
                break;
            }
        }

        if (!found) {
            educations.add(education);
        }
        ModelUtils.save(this,"education",educations);
        setupEducations();
    }

    public static String formatItems(List<String> items) {
        StringBuilder sb = new StringBuilder();
        for (String item: items) {
            sb.append(' ').append('-').append(' ').append(item).append('\n');
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }
}
