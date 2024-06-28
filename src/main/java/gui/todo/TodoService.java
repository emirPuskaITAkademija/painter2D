package gui.todo;

import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface TodoService {
    @GET ("/api/todos")
    public Call<List<Todo>> getTodos();
}
