package com.example.studentregister.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface StudentDao {
    // using suspend cuz it'll be executed by a coroutines as ui threads may cause error
    @Insert
    suspend fun insertStudent(student:Student)

    @Update
    suspend fun updateStudent(student: Student)

    @Delete
    suspend fun deleteStudent(student: Student)

    //doesn't need a suspend function bcz the room database does it in the background
    @Query("SELECT * FROM student_data_table")
    fun getAllStudents():LiveData<List<Student>>
}
