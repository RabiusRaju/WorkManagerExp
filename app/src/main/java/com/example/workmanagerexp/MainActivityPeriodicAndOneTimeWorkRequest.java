package com.example.workmanagerexp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

public class MainActivityPeriodicAndOneTimeWorkRequest extends AppCompatActivity {

    private Button btnStart;
    private TextView tvStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        btnStart = (Button) findViewById(R.id.btnStart);
        tvStatus = (TextView) findViewById(R.id.tvStatus);



        /*
        * Sending Input/Output Data
        * */

        Data inputData = new Data.Builder().putString("input_data", "This is Input Data").build();


        /*
        * Constraints Concept
        * */
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.UNMETERED)
                .build();

        /*
        * OneTimeWorkRequest
        * */


        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(MyWorker.class)
                .setConstraints(constraints)
                .setInputData(inputData)
                .build();

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WorkManager.getInstance(MainActivityPeriodicAndOneTimeWorkRequest.this).enqueue(request);
            }
        });

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(request.getId())
            .observe(this, new Observer<WorkInfo>() {
                @Override
                public void onChanged(WorkInfo workInfo) {
                    String status = workInfo.getState().name();
                    tvStatus.append(status + "\n");

                    if(workInfo !=null){
                        if(workInfo.getState().isFinished()){
                            Data retrievedData = workInfo.getOutputData();
                            String outputData = retrievedData.getString("output_data");

                            tvStatus.append(outputData + "\n");
                        }
                    }
                }
        });


        /*
        * PeriodicWorkRequest
        * */

        /*final PeriodicWorkRequest request = new PeriodicWorkRequest.Builder(MyWorker.class,2, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .build();

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WorkManager.getInstance(MainActivity.this).enqueue(request);
            }
        });

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(request.getId())
            .observe(this, new Observer<WorkInfo>() {
                @Override
                public void onChanged(WorkInfo workInfo) {
                    String status = workInfo.getState().name();

                    tvStatus.append(status + "\n");


                }
        });*/


    }
}