package com.example.news;

import static com.example.news.R.*;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements CategoryRVAdapter.CategoryOnClickInterface{
    private RecyclerView newsRV, categoryRV, hotNewsRV;
    private ImageView openMenu;
    private ProgressBar loadingPB;
    private ArrayList<Article> articleArrayList;
    private ArrayList<Article> hottestNewsArticleList;
    private ArrayList<CategoryRVModal> categoryRVModalArrayList;
    private CategoryRVAdapter categoryRVAdapter;
    private NewsRVAdapter newsRVAdapter;
    private HottestNewsRVAdapter hottestNewsRVAdapter;
    private TextView totalResultTV;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private int totalResults;
    static String lang="en";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);
        newsRV=findViewById(id.NewsRV);
        categoryRV=findViewById(id.CategoryItemsRV);
        hotNewsRV=findViewById(id.hottestNews);
        loadingPB=findViewById(id.LoadingPB);
        totalResultTV=findViewById(id.TotalResultTV);
        articleArrayList=new ArrayList<>();
        hottestNewsArticleList = new ArrayList<>();
        categoryRVModalArrayList=new ArrayList<>();
        newsRVAdapter=new NewsRVAdapter(articleArrayList,this);
        categoryRVAdapter=new CategoryRVAdapter(this, categoryRVModalArrayList, this::onCategoryClick);
        hottestNewsRVAdapter=new HottestNewsRVAdapter(hottestNewsArticleList,this);
        newsRV.setAdapter(newsRVAdapter);
        categoryRV.setAdapter(categoryRVAdapter);
        hotNewsRV.setAdapter(hottestNewsRVAdapter);
        drawerLayout=findViewById(id.drawer_layout);
        navigationView=findViewById(id.navigation);
        openMenu=findViewById(id.menu_open);
        openMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == id.nav_lang) {
                    showBottomSheetLang();
                    return true;
                }
                return false;
            }
        });

        getCategories();
        getNews("All");
        newsRVAdapter.notifyDataSetChanged();
        hottestNewsRVAdapter.notifyDataSetChanged();
    }

    public void showBottomSheetLang(){
        BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(this);
        View bottomSheetView= LayoutInflater.from(this).inflate(layout.bottom_sheet_layout, null);
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
        bottomSheetView.findViewById(id.lang_english).setOnClickListener(view -> {
            lang="en";
            bottomSheetDialog.dismiss();
            getNews("All");
        });
        bottomSheetView.findViewById(id.lang_spanish).setOnClickListener(view -> {
            lang="es";
            bottomSheetDialog.dismiss();
            getNews("All");
        });
        bottomSheetView.findViewById(id.lang_Spanish2).setOnClickListener(view -> {
            lang="es";
            bottomSheetDialog.dismiss();
        });
        bottomSheetView.findViewById(id.lang_french).setOnClickListener(view -> {
            lang="fr";
            bottomSheetDialog.dismiss();
            getNews("All");
        });
        bottomSheetView.findViewById(id.lang_arabic).setOnClickListener(view -> {
            lang="ar";
            bottomSheetDialog.dismiss();
        });
        bottomSheetView.findViewById(id.lang_chinese).setOnClickListener(view -> {
            lang="zh";
            bottomSheetDialog.dismiss();
        });
        bottomSheetView.findViewById(id.lang_dutch).setOnClickListener(view -> {
            lang="nl";
            bottomSheetDialog.dismiss();
        });
        bottomSheetView.findViewById(id.lang_german).setOnClickListener(view -> {
            lang="de";
            bottomSheetDialog.dismiss();
        });
        bottomSheetView.findViewById(id.lang_hebrew).setOnClickListener(view -> {
            lang="he";
            bottomSheetDialog.dismiss();
        });
        bottomSheetView.findViewById(id.lang_italian).setOnClickListener(view -> {
            lang="it";
            bottomSheetDialog.dismiss();
        });
        bottomSheetView.findViewById(id.lang_norwegian).setOnClickListener(view -> {
            lang="no";
            bottomSheetDialog.dismiss();
        });
        bottomSheetView.findViewById(id.lang_portuguese).setOnClickListener(view -> {
            lang="pt";
            bottomSheetDialog.dismiss();
        });
        bottomSheetView.findViewById(id.lang_russian).setOnClickListener(view -> {
            lang="ru";
            bottomSheetDialog.dismiss();
        });
        bottomSheetView.findViewById(id.lang_swedish).setOnClickListener(view -> {
            lang="sv";
            bottomSheetDialog.dismiss();
        });
    }
    private void getCategories(){
        categoryRVModalArrayList.clear();
        categoryRVModalArrayList.add(new CategoryRVModal("All"));
        categoryRVModalArrayList.add(new CategoryRVModal("Technology"));
        categoryRVModalArrayList.add(new CategoryRVModal("Science"));
        categoryRVModalArrayList.add(new CategoryRVModal("Sports"));
        categoryRVModalArrayList.add(new CategoryRVModal("General"));
        categoryRVModalArrayList.add(new CategoryRVModal("Business"));
        categoryRVModalArrayList.add(new CategoryRVModal("Entertainment"));
        categoryRVModalArrayList.add(new CategoryRVModal("Health"));
        categoryRVAdapter.notifyDataSetChanged();
    }


    private void getNews(String category){
        articleArrayList.clear();
        hottestNewsArticleList.clear();
        loadingPB.setVisibility(View.VISIBLE);

        String url;
        if(lang.equals("en")){
            url="https://newsapi.org/v2/top-headlines?country=in&language="+lang+"&excludeDomains=stackoverflow.com&sortBy=publishedAt&apiKey=1f840d4b397343a2b5518856eabf6d4d";
        }
        else{
            url="https://newsapi.org/v2/top-headlines?language="+lang+"&excludeDomains=stackoverflow.com&sortBy=publishedAt&apiKey=1f840d4b397343a2b5518856eabf6d4d";
        }
        String base_url="https://newsapi.org/";
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetroAPI retroAPI=retrofit.create(RetroAPI.class);
        Call<NewsModal> call;
//        if ("All".equals(category)){
            call=retroAPI.getAllNews(url);
//        }
//        else
//            call=retroAPI.getNewsByCategory(category_url);
        call.enqueue(new Callback<NewsModal>() {
            @Override
            public void onResponse(Call<NewsModal> call, Response<NewsModal> response) {
                Log.d("Response", response.toString());
                if(response.isSuccessful() && response.body() != null){
                    NewsModal newsModal=response.body();
                    loadingPB.setVisibility(View.GONE);
                    ArrayList<Article> articles=newsModal.getArticles();
                    totalResults=newsModal.getTotalResults();
                    totalResultTV.setText("Total results: "+totalResults);
                    if (articles!=null){
                        for (int i=0;i<articles.size();i++){
                            if(i<6){
                                hottestNewsArticleList.add(new Article(articles.get(i).getTitle(),articles.get(i).getDescription(),articles.get(i).getContent(),
                                        articles.get(i).getUrlToImage(),articles.get(i).getUrl()));
                            }
                            else {
                                articleArrayList.add(new Article(articles.get(i).getTitle(),articles.get(i).getDescription(),articles.get(i).getContent(),
                                        articles.get(i).getUrlToImage(),articles.get(i).getUrl()));
                            }
                        }
                        hottestNewsRVAdapter.notifyDataSetChanged();
                        newsRVAdapter.notifyDataSetChanged();

                    }
                    else{
                        Log.d("Response", "No articles found");
                        Toast.makeText(MainActivity.this,"Response:No articles found",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Log.d("Response", "Response unsuccessful or body is null");
                    Toast.makeText(MainActivity.this,"Response:Body null/response not success",Toast.LENGTH_SHORT).show();
                }

                }

            @Override
            public void onFailure(Call<NewsModal> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Failed To Get News",Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onCategoryClick(int position){
        String category=categoryRVModalArrayList.get(position).getCategory();
        if (category!="All"){
            Intent i=new Intent(MainActivity.this,ExplorePageActivity.class);
            i.putExtra("category",category);
            i.putExtra("language", lang);
            this.startActivity(i);
        }
        else getNews(category);
    }

}