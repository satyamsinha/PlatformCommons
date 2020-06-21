package com.application.assignmentkotlin.ui.main

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.work.*
import com.application.assignmentkotlin.db.TaskData
import com.application.assignmentkotlin.utils.WorkerClass
import java.util.concurrent.TimeUnit

class MainViewModel(application: Application) : AndroidViewModel(application) {


    private lateinit var lifecycleOwner: LifecycleOwner
    private var application:Context
    var liveData = MutableLiveData<List<TaskData>>()
    var liveDataString = MutableLiveData<String>()

    init {
        this.application=application
    }
    fun loadDataFromWorker(lifecycleOwner:LifecycleOwner){

        this.lifecycleOwner=lifecycleOwner
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val work = PeriodicWorkRequest.Builder( WorkerClass::class.java, 2, TimeUnit.HOURS)
            .setConstraints(constraints)
            .addTag("Repeat Download")
            .build()

        val workManager= WorkManager.getInstance()
        workManager.enqueue(work)

        val statuses = WorkManager.getInstance().getWorkInfosByTagLiveData("Repeat Download")
        statuses.observe(lifecycleOwner,
            Observer{ workStatusList ->
                var lastState:WorkInfo.State =WorkInfo.State.RUNNING
                val currentWorkStatus = workStatusList?.getOrNull(0)
                val isWorkActive = currentWorkStatus?.state?.isFinished == false
                if (currentWorkStatus?.state== WorkInfo.State.RUNNING) {
                    lastState=currentWorkStatus?.state
                }
                if(lastState==WorkInfo.State.RUNNING  && currentWorkStatus?.state==WorkInfo.State.ENQUEUED ){
                    Log.i("workto commit","sucess")
                    liveDataString.value="Success"
                }

                Log.i("Success","sucess" + isWorkActive + "--"+currentWorkStatus)
            })
    }

    fun getDataDashBoard( ): MutableLiveData<String> {
        return liveDataString
    }




}
