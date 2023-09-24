package com.example.news;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class NewsDetailActivity extends AppCompatActivity {
    private String title, desc, content, url, image;
    private TextView titleTV, subHeadTV, contentTV;
    private ImageView imageIV;
    private Button readMoreBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        titleTV=findViewById(R.id.NewsDetailActHeadTV);
        subHeadTV=findViewById(R.id.NewsDetailActSubTV);
        contentTV=findViewById(R.id.NewsDetailActContentTV);
        imageIV=findViewById(R.id.NewsDetailActIV);
        readMoreBtn=findViewById(R.id.NewsDetailActButton);
        title=getIntent().getStringExtra("title");
        content=getIntent().getStringExtra("content");
        desc=getIntent().getStringExtra("desc");
        url=getIntent().getStringExtra("url");
        image=getIntent().getStringExtra("image");
        titleTV.setText(title);
        subHeadTV.setText(desc);
        contentTV.setText(content);
        Picasso.get().load(image).into(imageIV);
        readMoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }
}