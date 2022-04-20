package id.davidpratama.todoapp.model

import androidx.room.*

@Dao
interface ToDoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg todo:ToDo)

    @Query("SELECT * FROM todo ORDER BY priority DESC")
    suspend fun selectAllTodo(): List<ToDo>

    @Query("SELECT * FROM todo WHERE uuid= :id")
    suspend fun selectTodo(id:Int): ToDo

    @Query("UPDATE todo SET title=:title,notes=:notes,priority=:priority WHERE uuid=:id")
    suspend fun  update(title:String, notes:String, priority:Int, id:Int)

    @Delete
    suspend fun deleteTodo(todo:ToDo)

}