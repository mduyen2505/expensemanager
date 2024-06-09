package com.example.expensemanager.model;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.expensemanager.AddActivity;
import com.example.expensemanager.adapter.ViewPagerAdapter;
import com.example.expensemanager.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView navigationView;
    private ViewPager viewPager;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Thiết lập padding để tránh việc chồng lấn với thanh điều hướng của hệ thống
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        navigationView = findViewById(R.id.bottom_nav);
        viewPager = findViewById(R.id.viewpager);
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
                // Xử lý sự kiện khi FAB được nhấn
            }
        });

        // Thiết lập adapter cho ViewPager
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);

        // Thiết lập sự kiện cho ViewPager khi trang thay đổi
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Xử lý khi trang được cuộn
            }

            @Override
            public void onPageSelected(int position) {
                // Xác định trang hiện tại và cập nhật trạng thái của BottomNavigationView
                if (position == 0) {
                    navigationView.getMenu().findItem(R.id.tong).setChecked(true);
                } else if (position == 1) {
                    navigationView.getMenu().findItem(R.id.timkiem).setChecked(true);
                } else if (position == 2) {
                    navigationView.getMenu().findItem(R.id.thongke).setChecked(true);
                }
            }


            @Override
            public void onPageScrollStateChanged(int state) {
                // Xử lý khi trạng thái cuộn của trang thay đổi
            }
        });

        // Thiết lập sự kiện cho BottomNavigationView
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                // Xác định menu item được chọn và chuyển đổi tới trang tương ứng trong ViewPager
                int id = menuItem.getItemId();
                if (id == R.id.tong) {
                    viewPager.setCurrentItem(0);
                } else if (id == R.id.timkiem) {
                    viewPager.setCurrentItem(1);
                } else if (id == R.id.thongke) {
                    viewPager.setCurrentItem(2);
                }
                return true;
            }

        });
    }
}
