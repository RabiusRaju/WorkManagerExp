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

public class MyWorkerPeriodicAndOneTimeWorkRequest extends Worker {

    public MyWorkerPeriodicAndOneTimeWorkRequest(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        Data retrievedData =  getInputData();
        String  desc = retrievedData.getString("input_data");

        displayNotification("Demo Task", desc);

        Data outputData = new Data.Builder().putString("output_data", "This is an Output Data").build();

        return Result.success(outputData);
    }

    private void displayNotification(String task, String desc){

        NotificationManager manager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("raju","raju",NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),"raju")
                .setContentTitle(task)
                .setContentText (desc)
                .setSmallIcon(R.mipmap.ic_launcher);

        manager.notify(1, builder.build());

    }
}
