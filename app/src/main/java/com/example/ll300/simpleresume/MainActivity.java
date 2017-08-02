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
import com.example.ll300.simpleresume.Mdel.Experience;
import com.example.ll300.simpleresume.Mdel.Project;
import com.example.ll300.simpleresume.Utils.Dateutil;
import com.example.ll300.simpleresume.Utils.ModelUtils;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQ_CODE_EDIT_EDUCATION = 100;
    private static final int REQ_CODE_EDIT_EXPERIENCE = 101;
    private static final int REQ_CODE_EDIT_PROJECT = 102;
    private static final int REQ_CODE_EDIT_BASIC_INFO = 103;

    private static final String MODEL_EDUCATIONS = "educations";
    private static final String MODEL_EXPERIENCES = "experiences";
    private static final String MODEL_PROJECTS = "projects";
    private static final String MODEL_BASIC_INFO = "basic_info";

    private BasicInfo basicinfo;
    private List<Education> educations;
    private List<Experience> experiences;
    private List<Project> projects;

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

        List<Education> savededucation = ModelUtils.read(this,MODEL_EDUCATIONS, new TypeToken<List<Education>>(){});
        educations = savededucation == null ? new ArrayList<Education>() : savededucation;
        
        List<Experience> savedexperience = ModelUtils.read(this, MODEL_EXPERIENCES, new TypeToken<List<Experience>>(){});
        experiences = savedexperience == null ? new ArrayList<Experience>() : savedexperience;

        List<Project> savedproject = ModelUtils.read(this, MODEL_PROJECTS, new TypeToken<List<Project>>(){});
        projects = savedproject == null ? new ArrayList<Project>() : savedproject;

    }

    private void setupUI() {
        ImageButton editinfobtn = (ImageButton) findViewById(R.id.edit_info_btn);
        editinfobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditBasicInfo.class);
                startActivityForResult(intent, REQ_CODE_EDIT_BASIC_INFO);
            }
        });

        ImageButton addeducationbtn = (ImageButton) findViewById(R.id.add_education_btn);
        addeducationbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditEducationActivity.class);
                startActivityForResult(intent, REQ_CODE_EDIT_EDUCATION);
            }
        });

        ImageButton addexperience = (ImageButton) findViewById(R.id.add_experience_btn);
        addexperience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditEXPActivity.class);
                startActivityForResult(intent, REQ_CODE_EDIT_EXPERIENCE);
            }
        });

        ImageButton addproject = (ImageButton) findViewById(R.id.add_project_btn);
        addproject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProjectEditActivity.class);
                startActivityForResult(intent, REQ_CODE_EDIT_PROJECT);
            }
        });

        setBasicInfo();
        setupEducations();
        setupExperiences();
        setupProjects();
    }

    private void setupExperiences() {
        LinearLayout experienceslayout = (LinearLayout) findViewById(R.id.experience_list);
        experienceslayout.removeAllViews();
        for (Experience experience : experiences) {
            View experienceView = getLayoutInflater().inflate(R.layout.experience_item, null);
            setupExperience(experienceView, experience);
            experienceslayout.addView(experienceView);
        }
    }

    private void setupExperience(View experienceView, final Experience experience) {
        String DateString = Dateutil.dateToString(experience.startDate) + " ~ " +
                Dateutil.dateToString(experience.endDate);
        TextView txt1 = (TextView) experienceView.findViewById(R.id.experience_info);
        TextView txt2 = (TextView) experienceView.findViewById(R.id.experience_detail);
        txt1.setText(experience.company + " ( " + DateString + " )");
        txt2.setText(experience.details);

        ImageButton editexperience = (ImageButton) experienceView.findViewById(R.id.edit_experience);
        editexperience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditEXPActivity.class);
                intent.putExtra(EditEXPActivity.KEY_EXP, experience);
                startActivityForResult(intent, REQ_CODE_EDIT_EXPERIENCE);
            }
        });
    }

    private void setBasicInfo() {
    }

    private void setupEducations() {
        LinearLayout educationslayout = (LinearLayout) findViewById(R.id.education_list);
        educationslayout.removeAllViews();
        for (Education education : educations) {
            View educationView = getLayoutInflater().inflate(R.layout.education_item,null);
            //动态加载View
            setupEducation(educationView, education);
            educationslayout.addView(educationView);

        }
    }

    private void setupEducation(View educationView, final Education education) {
        String DateString = Dateutil.dateToString(education.startDate) + " ~ " +
                Dateutil.dateToString(education.endDate);
        TextView txt1 = (TextView) educationView.findViewById(R.id.education_schoolandtime);
        TextView txt2 = (TextView) educationView.findViewById(R.id.education_courses);
        txt1.setText(education.school + " " + education.Major + " (" + DateString + ")");
        txt2.setText(formatItems(education.courses));

        ImageButton editedubtn = (ImageButton) educationView.findViewById(R.id.edit_education);
        //要注意这里需要用educationView.findViewById
        editedubtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditEducationActivity.class);
                intent.putExtra(EditEducationActivity.KEY_EDUCATION, education);
                //edit功能的实现时也需要让intent携带数据
                startActivityForResult(intent, REQ_CODE_EDIT_EDUCATION);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQ_CODE_EDIT_EDUCATION:
                    String educationID = data.getStringExtra(EditEducationActivity.KEY_EDUCATION_ID);
                    if (educationID != null) {
                        deleteEducation(educationID);
                    } else {
                        Education education = data.getParcelableExtra(EditEducationActivity.KEY_EDUCATION);
                        updateEducations(education);
                    }
                    break;
                case REQ_CODE_EDIT_EXPERIENCE:
                    String experienceID = data.getStringExtra(EditEXPActivity.KEY_EXP);
                    if (experienceID != null) {
                        deleteExperience(experienceID);
                    } else {
                        Experience experience = data.getParcelableExtra(EditEXPActivity.KEY_EXP);
                        updateExperience(experience);
                    }
                    break;
                case REQ_CODE_EDIT_PROJECT:
                    String projectId = data.getStringExtra(ProjectEditActivity.KEY_PROJECT_ID);
                    if (projectId != null) {
                        deleteProject(projectId);
                    } else {
                        Project project = data.getParcelableExtra(ProjectEditActivity.KEY_PROJECT);
                        updateProject(project);
                    }
                    break;
            }
        }

    }

    private void updateProject(Project project) {
        boolean found = false;
        for (int i = 0; i < projects.size(); ++i) {
            Project p = projects.get(i);
            if (TextUtils.equals(p.id, project.id)) {
                found = true;
                projects.set(i, project);
                break;
            }
        }

        if (!found) {
            projects.add(project);
        }

        ModelUtils.save(this, MODEL_PROJECTS, projects);
        setupProjects();
    }

    private void setupProjects() {
        LinearLayout projectslayout = (LinearLayout) findViewById(R.id.project_list);
        projectslayout.removeAllViews();
        for (Project project : projects) {
            View projectView = getLayoutInflater().inflate(R.layout.project_item, null);
            setupProject(projectView,project);
            projectslayout.addView(projectView);
        }
    }

    private void setupProject(View projectView, final Project project) {
        String DateString = Dateutil.dateToString(project.startDate) + " ~ " +
                Dateutil.dateToString(project.endDate);
        TextView txt1 = (TextView) projectView.findViewById(R.id.project_name);
        TextView txt2 = (TextView) projectView.findViewById(R.id.project_details);
        txt1.setText(project.name + " ( " + DateString + " )");
        txt2.setText(formatItems(project.details));

        ImageButton editproject = (ImageButton) projectView.findViewById(R.id.edit_project_btn);
        editproject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProjectEditActivity.class);
                intent.putExtra(ProjectEditActivity.KEY_PROJECT, project);
                startActivityForResult(intent, REQ_CODE_EDIT_PROJECT);
            }
        });

    }

    private void deleteProject(String projectId) {
        for (int i = 0; i < projects.size(); ++i) {
            Project p = projects.get(i);
            if (TextUtils.equals(p.id, projectId)) {
                projects.remove(i);
                break;
            }
        }

        ModelUtils.save(this, MODEL_PROJECTS, projects);
        setupProjects();
    }

    private void updateExperience(Experience experience) {
        boolean found = false;
        for (int i = 0; i < experiences.size(); ++i) {
            Experience e = experiences.get(i);
            if (e.id.equals(experience.id)) {
                found = true;
                experiences.set(i, experience);
                break;
            }
        }

        if (!found) {
            experiences.add(experience);
        }

        ModelUtils.save(this, MODEL_EXPERIENCES, experiences);
        setupExperiences();
    }

    private void deleteExperience(String experienceID) {
        for (int i = 0; i < experiences.size(); ++i) {
            Experience e = experiences.get(i);
            if (TextUtils.equals(e.id, experienceID)) {
                experiences.remove(i);
                break;
            }
        }

        ModelUtils.save(this, MODEL_EXPERIENCES, experiences);
        setupExperiences();
    }

    private void deleteEducation(String educationId) {
        for (int i = 0; i < educations.size(); i++) {
            Education e = educations.get(i);
            if (TextUtils.equals(e.id,educationId)) {
                educations.remove(i);
                break;
            }
        }
        ModelUtils.save(this,MODEL_EDUCATIONS, educations);
        setupEducations();
    }

    private void updateEducations(Education education) {
        boolean found = false;
        for (int i = 0; i < educations.size(); i++) {
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
        ModelUtils.save(this,MODEL_EDUCATIONS,educations);
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
