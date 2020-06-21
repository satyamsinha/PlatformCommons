package com.application.assignmentkotlin.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "task", indices = [Index(value = ["id"], unique = true) /*,Index(value = ["owner_id"], unique = true)*/])
data class TaskDataToSave(
    @PrimaryKey @ColumnInfo(name = "id")  var id :Int,
    @ColumnInfo(name = "data") var data:String
)