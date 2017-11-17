package kr.co.zio4272.localservertest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONObject;

import kr.co.zio4272.localservertest.data.Lecture;
import kr.co.zio4272.localservertest.util.ServerUtil;

public class StudentListActivity extends BaseActivity {

    Lecture mLecture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        mLecture = (Lecture) getIntent().getSerializableExtra("lecture");
        bindViews();
        setupEvents();
        setValues();

        setTitle(mLecture.getName());

        getStudentFromServer();
    }

    @Override
    public void setupEvents() {

    }

    void getStudentFromServer() {
        ServerUtil.getStudentByLectureId(mContext, mLecture.getId(), new ServerUtil.JsonResponseHandler() {
            @Override
            public void onResponse(JSONObject json) {

            }
        });
    }

    @Override
    public void setValues() {

    }

    @Override
    public void bindViews() {

    }
}