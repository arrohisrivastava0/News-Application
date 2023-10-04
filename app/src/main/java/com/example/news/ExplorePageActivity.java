package com.example.news;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ExplorePageActivity extends AppCompatActivity implements CategoryRVAdapter.CategoryOnClickInterface{
    private String category;
    private String lang;
    private CategoryRVAdapter exCategoryRVAdapter;
    private NewsRVAdapter exNewsRVAdapter;
    private RecyclerView exploreCatRV, exploreNewsRV;
    private ArrayList<CategoryRVModal> exCategoryRVModalArrayList;
    private ArrayList<Article> articleArrayList;
    private ProgressBar loadingPB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_page);
        loadingPB=findViewById(R.id.ExLoadingPB);
        exploreCatRV=findViewById(R.id.ExploreCategoryItemsRV);
        exploreNewsRV=findViewById(R.id.ExNewsRV);
        articleArrayList=new ArrayList<>();
        exCategoryRVModalArrayList=new ArrayList<>();
        exNewsRVAdapter=new NewsRVAdapter(articleArrayList,this);
        exCategoryRVAdapter=new CategoryRVAdapter(this, exCategoryRVModalArrayList, this::onCategoryClick);
        exploreNewsRV.setAdapter(exNewsRVAdapter);
        exploreCatRV.setAdapter(exCategoryRVAdapter);
        category=getIntent().getStringExtra("category");
        lang=getIntent().getStringExtra("language");
        getCategories();
        getCatNews(category);
        exNewsRVAdapter.notifyDataSetChanged();
    }

    private void getCategories(){
        exCategoryRVModalArrayList.add(new CategoryRVModal("Technology"));
        exCategoryRVModalArrayList.add(new CategoryRVModal("Science"));
        exCategoryRVModalArrayList.add(new CategoryRVModal("Sports"));
        exCategoryRVModalArrayList.add(new CategoryRVModal("General"));
        exCategoryRVModalArrayList.add(new CategoryRVModal("Business"));
        exCategoryRVModalArrayList.add(new CategoryRVModal("Entertainment"));
        exCategoryRVModalArrayList.add(new CategoryRVModal("Health"));
        exCategoryRVAdapter.notifyDataSetChanged();
    }

    private void getCatNews(String category){
        articleArrayList.clear();
        loadingPB.setVisibility(View.VISIBLE);
        String category_url="https://newsapi.org/v2/top-headlines?category="+category+"&language="+lang+"&apiKey=1f840d4b397343a2b5518856eabf6d4d";
        String base_url="https://newsapi.org/";
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetroAPI retroAPI=retrofit.create(RetroAPI.class);
        Call<NewsModal> call;
        call=retroAPI.getNewsByCategory(category_url);
        call.enqueue(new Callback<NewsModal>() {
            @Override
            public void onResponse(Call<NewsModal> call, Response<NewsModal> response) {
                Log.d("Response", response.toString());
                if(response.isSuccessful() && response.body() != null) {
                    NewsModal newsModal = response.body();
                    loadingPB.setVisibility(View.GONE);
                    ArrayList<Article> articles=newsModal.getArticles();
                    if (articles!=null) {
                        for (int i = 0; i < articles.size(); i++){
                            articleArrayList.add(new Article(articles.get(i).getTitle(),articles.get(i).getDescription(),articles.get(i).getContent(),
                                    articles.get(i).getUrlToImage(),articles.get(i).getUrl()));
                        }
                        exNewsRVAdapter.notifyDataSetChanged();
                    }
                    else{
                        Log.d("Response", "No articles found");
                        Toast.makeText(ExplorePageActivity.this,"Response:No articles found",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Log.d("Response", "Response unsuccessful or body is null");
                    Toast.makeText(ExplorePageActivity.this,"Response:Body null/response not success",Toast.LENGTH_SHORT).show();
                }

                }

            @Override
            public void onFailure(Call<NewsModal> call, Throwable t) {
                Toast.makeText(ExplorePageActivity.this,"Failed To Get News",Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onCategoryClick(int position) {
        String categorySel=exCategoryRVModalArrayList.get(position).getCategory();
        getCatNews(categorySel);
    }
}