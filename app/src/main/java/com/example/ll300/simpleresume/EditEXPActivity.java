package com.example.ll300.simpleresume;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ll300.simpleresume.Mdel.Experience;
import com.example.ll300.simpleresume.Utils.Dateutil;

public class EditEXPActivity extends EditBaseActivity<Experience> {

    public static final String KEY_EXP = "experience";
    public static final String KEY_EXP_ID  = "experience_id";


    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_exp;
    }

    @Override
    protected void setupUIForCreate() {

    }

    @Override
    protected void setupUIForEdit(@NonNull final Experience data) {
        ((EditText) findViewById(R.id.experience_company)).setText(data.company);
        ((EditText) findViewById(R.id.experience_start)).setText(Dateutil.dateToString(data.startDate));
        ((EditText) findViewById(R.id.experience_end)).setText(Dateutil.dateToString(data.endDate));
        ((EditText) findViewById(R.id.experience_detail)).setText(data.details);
        TextView deleteitem = (TextView) findViewById(R.id.experience_delete);
        deleteitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent result = new Intent();
                result.putExtra(KEY_EXP_ID, data.id);
                setResult(Activity.RESULT_OK, result);
                finish();
            }
        });

    }

    @Override
    protected void saveAndExit(@Nullable Experience data) {
        if (data == null) {
            data = new Experience();
        }
        data.company = ((EditText) findViewById(R.id.experience_company)).getText().toString();
        data.startDate = Dateutil.stringToDate(((EditText) findViewById(R.id.experience_start))
                .getText().toString());
        data.endDate = Dateutil.stringToDate(((EditText) findViewById(R.id.experience_end))
                .getText().toString());
        data.details = ((EditText) findViewById(R.id.experience_detail)).getText().toString();

        Intent resultIntent = new Intent();
        resultIntent.putExtra(KEY_EXP, data);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    @Override
    protected Experience initializeData() {
        return getIntent().getParcelableExtra(KEY_EXP);
    }


}
