package com.example.workmanagerexp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class MyWorker extends Worker {

    public MyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        Data retrievedData = getInputData();
        int val = retrievedData.getInt("input_data_1st_work",5);

        int sum = 0;

        for(int i = 0; i< val; i++){

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            sum = sum + i;
        }

        Data outputData = new Data.Builder().putInt("output_data_1st_work",sum).build();

        return Result.success(outputData);
    }


}
