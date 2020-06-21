package com.application.assignmentkotlin.utils

import android.content.Context
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.work.*
import com.application.assignmentkotlin.db.DBHelper
import com.application.assignmentkotlin.db.TaskData
import com.application.assignmentkotlin.db.TaskDataToSave
import com.application.assignmentkotlin.db.TaskRepository
import com.application.assignmentkotlin.network.ApiCallInterface
import com.application.assignmentkotlin.network.RetrofitInstance
import com.application.assignmentkotlin.ui.main.MainFragment.Companion.repository
import com.application.assignmentkotlin.ui.main.MainViewModel
import com.application.assignmentkotlin.ui.main.TaskAdapter
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.TimeUnit

var WORKER_RESULT_INT = "WORKER_RESULT_INT"

class WorkerClass(context: Context, params: WorkerParameters) : CoroutineWorker(context, params)

{

    override suspend fun doWork(): Result {

        try { Log.i("Worker class","Started"+ WORKER_RESULT_INT)
            val request =
                RetrofitInstance.getRetrofitInstance().create(ApiCallInterface::class.java)
            request.getItemsDetails().enqueue(object : Callback<List<TaskData>> {

                override fun onResponse(
                    call: Call<List<TaskData>>,
                    response: Response<List<TaskData>>
                ) {
                    var resp: String = response.body().toString()

                    GlobalScope.launch {

                        var liveData: MutableList<TaskData> = ArrayList()
                        liveData = response.body() as MutableList<TaskData>
                        var listTaskData: MutableList<TaskDataToSave> = ArrayList()
                        for (datatypeValue: TaskData in liveData) {
                            val storeDB = Gson().toJson(datatypeValue)
                            val taskDataToSave = TaskDataToSave(datatypeValue.id, storeDB)
                            listTaskData.add(taskDataToSave)
                        }
                        WORKER_RESULT_INT= repository.insert(listTaskData)
                        Log.i("DB insert","Complete-"+ WORKER_RESULT_INT)
                    }

                }

                override fun onFailure(call: Call<List<TaskData>>, t: Throwable) {
                    val resultResult = Result.failure()
                }
            })
        }
        catch (e:Exception){

            return Result.failure()

        }
        if(WORKER_RESULT_INT.equals("success")) {
            return Result.success()
        }
        else
            return Result.failure()
    }
}