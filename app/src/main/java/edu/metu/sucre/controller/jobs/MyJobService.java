package edu.metu.sucre.controller.jobs;

import android.util.Log;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

public class MyJobService extends JobService {


    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.d("_______IA_______", "Performing long running task in scheduled job");

        // TODO(developer): add long running task here.
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.d("_______IA_______", "Job stopped!");
        return false;
    }

}
