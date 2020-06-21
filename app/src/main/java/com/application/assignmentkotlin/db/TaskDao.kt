package com.application.assignmentkotlin.db

import androidx.room.*

@Dao
interface  TaskDao {

    @Query("SELECT * FROM task")
     fun getAll(): List<TaskDataToSave?>?

   /* @Insert
     fun insert(task: List<ResponseDashboard>)*/
   @Query("SELECT * FROM task LIMIT :limit OFFSET :offset")
   open fun loadDataFromDB(limit: Int, offset: Int): List<TaskDataToSave>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
     fun insert(task:List<TaskDataToSave>)

    @Delete
     fun delete(task: TaskDataToSave?)

    @Update
     fun update(task: TaskDataToSave?)

}
