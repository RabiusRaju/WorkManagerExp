package com.example.workmanagerexp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button btnStart;
    private TextView tvStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        btnStart = (Button) findViewById(R.id.btnStart);
        tvStatus = (TextView) findViewById(R.id.tvStatus);

        Data inputData = new Data.Builder().putInt("input_data_1st_work",10).build();

        final OneTimeWorkRequest request1 = new OneTimeWorkRequest.Builder(MyWorker.class)
                .setInputData(inputData)
                .addTag("MyWork")
                .build();

        final OneTimeWorkRequest request2 = new OneTimeWorkRequest.Builder(MyWorker2.class)
                .addTag("MyWork")
                .build();

        btnStart.setOnClickListener(v -> WorkManager.getInstance(MainActivity.this)
                .beginWith(request1)
                .then(request2)
                .enqueue());

        WorkManager.getInstance(this).getWorkInfosByTagLiveData("MyWork")
                .observe(this, new Observer<List<WorkInfo>>() {
                    @Override
                    public void onChanged(List<WorkInfo> workInfos) {
                        if(workInfos.size() !=0){
                            for(int i = 0; i< workInfos.size(); i++){
                                String status = workInfos.get(i).getState().name();
                                tvStatus.append(status + "\n");

                                if(workInfos.get(i).getState().isFinished()){

                                    Data retrievedData = workInfos.get(i).getOutputData();
                                    int outputData = retrievedData.getInt("output_data_2nd_work",0);
                                    if(outputData >0){
                                        tvStatus.append(outputData + "\n");
                                    }

                                }
                            }
                        }
                    }
        });

    }
}