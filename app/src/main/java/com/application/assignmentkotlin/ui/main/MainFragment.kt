package com.application.assignmentkotlin.ui.main

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.UiThread
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.application.assignmentkotlin.R
import com.application.assignmentkotlin.db.*
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

@Suppress("DEPRECATION")
class MainFragment : Fragment() {

    companion object {
        lateinit var repository: TaskRepository

        fun newInstance() = MainFragment()
    }

    private var mainActivity: Context? = null
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var mLayoutManager: LinearLayoutManager
    private lateinit var progressCircular: ProgressBar
    private lateinit var recyclerview: RecyclerView
    private lateinit var viewModel: MainViewModel
    private var tempList  : MutableList<TaskData> = ArrayList()
    private var listTask  : MutableList<TaskData> =ArrayList()
    lateinit var taskDao:TaskDao
    //lateinit var repository:TaskRepository
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        val root = inflater.inflate(R.layout.main_fragment, container, false)
        recyclerview = root.findViewById(R.id.recycler_view)
        progressCircular = root.findViewById(R.id.progress_circular)
        mLayoutManager = LinearLayoutManager(context)
        this.mainActivity =activity?.applicationContext
         taskDao = context?.let { DBHelper.getDatabase(it).taskDao() }!!
         repository = taskDao?.let { TaskRepository(it) }
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recyclerview.layoutManager = mLayoutManager
        recyclerview.itemAnimator = DefaultItemAnimator()
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        if(isConnectingToInternet(activity)) {

            viewModel.loadDataFromWorker(context@ this)
            viewModel.getDataDashBoard().observe(context@ this, Observer { status: String ->
                if (status.equals("Success")) {
                    initViewData(true)

                } else {
                    initViewData(false)
                    Toast.makeText(context, "Failed to retrieve", Toast.LENGTH_SHORT).show()
                }
            })
        }else{
            Toast.makeText(context, R.string.show_local, Toast.LENGTH_SHORT).show()
            initViewData(false)
        }

        recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if(mLayoutManager.findLastVisibleItemPosition() == mLayoutManager.itemCount-1){
                    progressCircular.visibility=View.VISIBLE
                    doAsync {
                     //delay for sho
                         fetchFromDB()
                        Thread.sleep(2000)

                        uiThread {
                            progressCircular.visibility=View.GONE
                            taskAdapter.notifyDataSetChanged()
                            recyclerView.invalidate()
                        }
                    }
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    fun initViewData(value:Boolean){
        doAsync {
            if(value)//For online data to fetch and complete
                Thread.sleep(10000)
            fetchFromDB()
            if(!value)//Forshowing progress bar local fetch
                Thread.sleep(1000)

            uiThread {

                val appCompatActivity = activity as AppCompatActivity

                taskAdapter = context?.applicationContext?.let {
                    TaskAdapter(context!!, tempList, appCompatActivity)
                }!!
                recyclerview.adapter = taskAdapter
                recyclerview.invalidate()
                taskAdapter.notifyDataSetChanged()
                progressCircular.visibility = View.GONE
            }
        }
    }
    @UiThread
    fun fetchFromDB() {
        var offset:Int=0
        if(tempList!=null && tempList.size!=null)
            offset=tempList.size

        var listTaskData: List<TaskDataToSave> = ArrayList()//List<TaskData>= ArrayList()
        mainActivity?.let {
            Log.i("DB", "Start Fetching"+offset)
            GlobalScope.launch {
                val taskDao = DBHelper.getDatabase(it).taskDao()
                val repository = TaskRepository(taskDao)
                listTaskData=repository.getData(offset,10)
                for (taskToSave: TaskDataToSave in listTaskData) {
                    val taskData: TaskData =
                        Gson().fromJson(taskToSave.data, TaskData::class.java)
                    tempList.add(taskData)
                }
            }
        }
    }
    fun isConnectingToInternet(context: Context?): Boolean {
        val connectivity = context?.getSystemService(
            Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivity != null) {
            val info = connectivity.allNetworkInfo
            if (info != null)
                for (i in info)
                    if (i.state == NetworkInfo.State.CONNECTED) {
                        return true
                    }
        }
        return false
    }
}
