package com.example.ll300.simpleresume;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ll300.simpleresume.Mdel.Education;
import com.example.ll300.simpleresume.Utils.Dateutil;

import java.util.Arrays;

public class EditEducationActivity extends AppCompatActivity {

    private Education data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_education);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        data = getIntent().getParcelableExtra("education");
        if (data != null) {
            ((EditText) findViewById(R.id.edit_education_school)).setText(data.school);
            ((EditText) findViewById(R.id.edit_education_major)).setText(data.Major);
            ((EditText) findViewById(R.id.edit_education_start)).setText(Dateutil.dateToString(data.startDate));
            ((EditText) findViewById(R.id.edit_education_end)).setText(Dateutil.dateToString(data.endDate));
            ((EditText) findViewById(R.id.edit_education_courses)).setText(TextUtils.join("\n", data.courses));

        }
    }

    //home是android内置资源，需要用android.R.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else if (item.getItemId() == R.id.action_svae) {
            saveAndExit(data);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveAndExit(@Nullable Education data) {
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
