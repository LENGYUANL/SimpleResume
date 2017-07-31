package com.example.ll300.simpleresume;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ll300.simpleresume.Mdel.Education;
import com.example.ll300.simpleresume.Utils.Dateutil;

import java.util.Arrays;

public class EditEducationActivity extends EditBaseActivity<Education> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_education;
    }

    @Override
    protected void setupUIForCreate() {

    }

    @Override
    protected void setupUIForEdit(@NonNull final Education data) {
        ((EditText) findViewById(R.id.edit_education_school)).setText(data.school);
        ((EditText) findViewById(R.id.edit_education_major)).setText(data.Major);
        ((EditText) findViewById(R.id.edit_education_start)).setText(Dateutil.dateToString(data.startDate));
        ((EditText) findViewById(R.id.edit_education_end)).setText(Dateutil.dateToString(data.endDate));
        ((EditText) findViewById(R.id.edit_education_courses)).setText(TextUtils.join("\n", data.courses));
    }


    @Override
    protected void saveAndExit(@Nullable Education data) {
        if (data == null) {
            data = new Education();
        }
        data.school = ((EditText) findViewById(R.id.edit_education_school)).getText().toString();
        data.Major = ((EditText) findViewById(R.id.edit_education_major)).getText().toString();
        data.startDate = Dateutil.stringToDate(
                ((TextView) findViewById(R.id.edit_education_start)).getText().toString());
        data.endDate = Dateutil.stringToDate(
                ((TextView) findViewById(R.id.edit_education_end)).getText().toString());
        data.courses = Arrays.asList(TextUtils.split(
                ((EditText) findViewById(R.id.edit_education_courses)).getText().toString(), "\n"));


        Intent resultIntent = new Intent();
        resultIntent.putExtra("education", data);
        setResult(Activity.RESULT_OK,resultIntent);
        finish();
    }

    @Override
    protected Education initializeData() {
        return getIntent().getParcelableExtra("education");
    }


}
