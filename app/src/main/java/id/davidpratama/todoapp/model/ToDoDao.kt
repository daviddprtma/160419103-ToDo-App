package id.davidpratama.todoapp.model

import androidx.room.*

@Dao
interface ToDoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg todo:ToDo)

    @Query("SELECT * FROM todo")
    suspend fun selectAllTodo(): List<ToDo>

    @Query("SELECT * FROM todo WHERE uuid= :id")
    suspend fun selectTodo(id:Int): ToDo

    @Delete
    suspend fun deleteTodo(todo:ToDo)

}