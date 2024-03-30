package az.edu.bhos.l14todoapp.data

import az.edu.bhos.l14todoapp.entities.TodoEntity
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    suspend fun syncTodos()
    fun observeTodoEntries(): Flow<List<TodoEntity>>
    suspend fun save(todoEntity: TodoEntity)
}

class TodoRepositoryImpl(
    private val localData: TodoLocalData,
    private val remoteData: TodoRemoteData
) : TodoRepository {

    override suspend fun syncTodos() {
        val todoList = remoteData.getTodos()
        // TODO save to local data
        todoList.forEach {
            localData.save(TodoEntity(it.id, it.title, it.completed, it.weekday))
        }
    }

    override fun observeTodoEntries(): Flow<List<TodoEntity>> {
        return localData.observeTodoItems()
    }

    override suspend fun save(todoEntity: TodoEntity) {
        localData.save(todoEntity)
    }

}