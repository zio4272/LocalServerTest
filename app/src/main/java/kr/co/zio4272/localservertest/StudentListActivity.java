package kr.co.zio4272.localservertest;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import kr.co.zio4272.localservertest.adapter.StudentAdapter;
import kr.co.zio4272.localservertest.data.Lecture;
import kr.co.zio4272.localservertest.data.User;
import kr.co.zio4272.localservertest.util.ServerUtil;

public class StudentListActivity extends BaseActivity {

    Lecture mLecture;
    private android.widget.ListView studentListView;
    List<User> students = new ArrayList<User>();
    StudentAdapter mAdapter;
    private android.widget.Button findTeacherNameBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        mLecture = (Lecture) getIntent().getSerializableExtra("lecture");
        bindViews();
        setupEvents();
        setValues();

        setTitle(mLecture.getName());

    }

    @Override
    public void setupEvents() {

        findTeacherNameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ServerUtil.getTeacherNameByLectureId(mContext, mLecture.getId(), new ServerUtil.JsonResponseHandler() {
                    @Override
                    public void onResponse(JSONObject json) {

                        try {
                            User teacherName = User.getUserFromJson(json.getJSONObject("teacher"));

                            Toast.makeText(mContext, "담당선생님 : " + teacherName.getName(), Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });

            }
        });

    }

    void getStudentFromServer() {
        ServerUtil.getStudentByLectureId(mContext, mLecture.getId(), new ServerUtil.JsonResponseHandler() {
            @Override
            public void onResponse(JSONObject json) {

                try {
                    if (json.getBoolean("result")) {

                        students.clear();

                        JSONArray stds = json.getJSONArray("students");

                        for (int i = 0; i < stds.length(); i++) {
                            students.add(User.getUserFromJson(stds.getJSONObject(i)));
                        }

                        mAdapter.notifyDataSetChanged();


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }

    @Override
    public void setValues() {

        mAdapter = new StudentAdapter(mContext, students);
        studentListView.setAdapter(mAdapter);
        getStudentFromServer();


    }

    @Override
    public void bindViews() {
        this.findTeacherNameBtn = (Button) findViewById(R.id.findTeacherNameBtn);
        this.studentListView = (ListView) findViewById(R.id.studentListView);

    }
}