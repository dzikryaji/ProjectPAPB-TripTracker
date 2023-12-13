package das.mobile.triptracker.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;

import das.mobile.triptracker.adapter.NewsAdapter;
import das.mobile.triptracker.api.ApiService;
import das.mobile.triptracker.databinding.FragmentNewsBinding;
import das.mobile.triptracker.model.News;
import das.mobile.triptracker.model.NewsResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsFragment extends Fragment {

    private ArrayList<News> newsList;
    private NewsAdapter newsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentNewsBinding binding = FragmentNewsBinding.inflate(inflater, container, false);
        binding.rvNews.setLayoutManager(new LinearLayoutManager(getActivity()));
        newsList = new ArrayList<>();
        newsAdapter = new NewsAdapter(newsList, getActivity());
        binding.rvNews.setAdapter(newsAdapter);
        getNews();
        return binding.getRoot();
    }

    private void getNews(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://newsapi.org/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<NewsResponse> call;
        call = apiService.getAllNews("https://newsapi.org/v2/top-headlines?country=id&category=entertainment&apiKey=47b5472777b2431db732548c3373b501");
        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                NewsResponse newsResponse = response.body();
                ArrayList<News> news = newsResponse.getArticles();
                for (int i=0; i<news.size(); i++){
                    newsList.add(new News(news.get(i).getTitle(), news.get(i).getAuthor(), news.get(i).getPublishedAt(), news.get(i).getUrl(), news.get(i).getUrlToImage()));
                }
                newsAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Fail to get news", Toast.LENGTH_SHORT).show();
            }
        });
    }
}