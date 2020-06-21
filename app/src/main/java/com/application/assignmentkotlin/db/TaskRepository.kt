package com.application.assignmentkotlin.db

import android.util.Log

class TaskRepository(private val taskDao: TaskDao) {

    //val allTask: List<TaskDataToSave?>? = taskDao.getAll()

    fun getData(offset:Int ,limit:Int): List<TaskDataToSave> {
        val allTask: List<TaskDataToSave> = taskDao.loadDataFromDB(limit,offset)
        Log.i("Task",allTask.toString())
        return allTask
    }
     fun insert( listResponse:List<TaskDataToSave>) :String {

        taskDao.insert(listResponse)

         return "success"
    }
}