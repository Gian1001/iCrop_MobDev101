package com.example.icrop_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class SliderPage extends AppCompatActivity {

    private ViewPager viewPager;
    private int[] imageIds = {R.drawable.splash, R.drawable.splash2, R.drawable.splash3, R.drawable.splash4, R.drawable.splash5, R.drawable.splash6};
    private static final int NUM_PAGES = 6; // Number of images in the slider
    private static final int AUTO_SLIDE_DELAY = 2000; // Delay in milliseconds (2 seconds)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider_page);

        viewPager = findViewById(R.id.viewPager);
        SliderPagerAdapter sliderPagerAdapter = new SliderPagerAdapter(this, imageIds);
        viewPager.setAdapter(sliderPagerAdapter);

        // Set up automatic image slider
        final Handler handler = new Handler();
        final Runnable update = new Runnable() {
            public void run() {
                int currentPage = viewPager.getCurrentItem();
                int nextPage = (currentPage + 1) % NUM_PAGES;
                viewPager.setCurrentItem(nextPage);
            }
        };

        // Schedule image slider to run with the specified delay
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                handler.post(update);
            }
        }, AUTO_SLIDE_DELAY);

        // Initialize the indicator views
        final ImageView[] dots = new ImageView[NUM_PAGES];
        final LinearLayout indicatorLayout = findViewById(R.id.indicatorLayout);

        for (int i = 0; i < NUM_PAGES; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageResource(R.drawable.ic_dot_unselected);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(8, 0, 8, 0);
            indicatorLayout.addView(dots[i], params);
        }

        // Update the indicator dots when the page changes
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < NUM_PAGES; i++) {
                    dots[i].setImageResource(i == position ? R.drawable.ic_dot_selected : R.drawable.ic_dot_unselected);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        // Handle the "Next" button click
        Button nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the next action here, e.g., go to the next page or finish the activity
                Intent intent = new Intent(SliderPage.this, Login.class);
                startActivity(intent);
                finish(); // Finish the SliderPage activity
            }
        });
    }
}
