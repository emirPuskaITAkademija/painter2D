package gui.todo;

import okhttp3.Request;
import retrofit2.Call;
import okhttp3.OkHttpClient;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.List;

public class TodoMain {
    public static void main(String[] args) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor( chain -> {
            Request original = chain.request();
            Request request = original.newBuilder()
                    .header("Authorization", "Basic aXNtZXQ6aXNtZXQxMjM=")
                    .build();
            return chain.proceed(request);
        });
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:8080/api/todos/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        TodoService service = retrofit.create(TodoService.class);
        Call<List<Todo>> callSync = service.getTodos();
        callSync.enqueue(new Callback<List<Todo>>() {
            @Override
            public void onResponse(Call<List<Todo>> call, Response<List<Todo>> response) {
                List<Todo> todos = response.body();
                for (Todo todo : todos) {
                    System.out.println(todo);
                }
            }

            @Override
            public void onFailure(Call<List<Todo>> call, Throwable throwable) {
                System.out.println(throwable);
            }
        });
//        try {
//            Response<List<Todo>> response = callSync.execute();
//            List<Todo> todos = response.body();
//            todos.forEach(todo -> System.out.println(todo));
//        } catch (Exception ex) {
//            System.err.println(ex.getMessage());
//        }
    }
}
