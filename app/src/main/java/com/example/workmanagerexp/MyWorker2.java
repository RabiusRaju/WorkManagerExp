package com.example.workmanagerexp;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class MyWorker2 extends Worker {

    public MyWorker2(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        Data retrievedData = getInputData();
        int value = retrievedData.getInt("output_data_1st_work",10);

        value = value * 100;

        Data outputData = new Data.Builder().putInt("output_data_2nd_work",value).build();

        return Result.success(outputData);
    }


}
