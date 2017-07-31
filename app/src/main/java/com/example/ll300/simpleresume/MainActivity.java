package com.example.ll300.simpleresume;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ll300.simpleresume.Mdel.Education;
import com.example.ll300.simpleresume.Utils.Dateutil;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Education> educations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupUI();
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

        setupEducations();
    }

    private void setupEducations() {
        LinearLayout educationslayout = (LinearLayout) findViewById(R.id.education_list);
        educationslayout.removeAllViews();
        for (Education education: educations) {
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
//        if (requestCode == 101 && resultCode == RESULT_OK) {
//            Education neweducation = data.getParcelableExtra("education");
//        }
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
