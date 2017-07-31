package com.example.ll300.simpleresume;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.EditText;

import com.example.ll300.simpleresume.Mdel.BasicInfo;

public class EditBasicInfo extends EditBaseActivity<BasicInfo> {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_basic_info;
    }

    @Override
    protected void setupUIForCreate() {

    }

    @Override
    protected void setupUIForEdit(@NonNull BasicInfo data) {
        ((EditText) findViewById(R.id.info_name)).setText(data.name);
        ((EditText) findViewById(R.id.info_email)).setText(data.email);

    }

    @Override
    protected void saveAndExit(@Nullable BasicInfo data) {
        if (data == null) data = new BasicInfo();

        data.name = ((EditText) findViewById(R.id.info_name)).getText().toString();
        data.email = ((EditText) findViewById(R.id.info_email)).getText().toString();

        Intent result = new Intent();
        result.putExtra("info",data);
        setResult(RESULT_OK,result);
        finish();
    }

    @Override
    protected BasicInfo initializeData() {
        return getIntent().getParcelableExtra("info");
    }
}
