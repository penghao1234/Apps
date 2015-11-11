package android.com.changyou;

import android.com.changyou.fragment.ProductListFragment;
import android.com.changyou.fragment.ProductListFragment2;
import android.com.changyou.fragment.ProductListFragment3;
import android.com.changyou.fragment.ProductListFragment4;
import android.com.changyou.util.VolleyUtil;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.AlertDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.toolbox.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 抽屉布局
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        // 工具栏
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 应用栏
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);

        // 导航视图
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        if (navView != null) {
            setDrawerContent(navView);
        }

        // 滑动分页
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (viewPager != null) {
            setViewPage(viewPager);
        }

        // fab 按钮
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Snackbar 类似于 Toast
                Snackbar.make(view, "Snackbar for 分页", Snackbar.LENGTH_SHORT).
                        setAction(R.string.app_name, null).show();
            }
        });

        // 选项卡布局 关联 滑动分页
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setViewPage(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new ProductListFragment(),
                getString(R.string.city_name));

        // 添加其他 Fragment (省略)
        adapter.addFragment(new ProductListFragment2(), getString(R.string._collection));
        adapter.addFragment(new ProductListFragment3(), getString(R.string._massage));
        adapter.addFragment(new ProductListFragment4(), getString(R.string._journey));

        viewPager.setAdapter(adapter);
    }

    private void setDrawerContent(NavigationView navView) {
        TextView tvUsername = (TextView) navView.findViewById(R.id.tvUsername);
        //String username = "test";
        //tvUsername.setText(username);

        // 设置当前登录的用户图片
        ImageView imgUsername = (ImageView) navView.findViewById(R.id.imgUsername);
        // 可用 ImageLoader 加载自定义的用户图片
        // 用户表中新增一列 img_path 存储用户图片 (替换硬代码)
//        String imgPath = "images/xxx.jpg";
//        if (username.length() > 0 && "admin".equals(username)) {
//            imgPath = "images/qq.jpg";
//        }
        // 来自选项存储的图片路径 (实际项目中此行替换以上四行代码)
        // String imgPath = TmallUtil.getImgPath(this);

       // ImageLoader imageLoader =
                //VolleyUtil.getInstance(this).getImageLoader();
       // ImageLoader.ImageListener listener =
               // ImageLoader.getImageListener(
                        //imgUsername, R.mipmap.ic_launcher, R.drawable.inter_logo);
        //imageLoader.get("drawable/ic_launcher", listener);

        // -- 左侧抽屉导航视图 菜单 ------------------------------
        // 导航视图中的菜单选中事件
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    // 匹配菜单的选项 (其它选项的处理 省略)
                    case R.id.action_about:
                        //aboutDialog();
                        break;
                    case R.id.action_login:
                        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                        break;
                }
                // 选择后自动关闭左侧抽屉
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // 点击左上角图标,打开抽屉
                drawerLayout.openDrawer(GravityCompat.START);
                break;
 //           case R.id.action_logout:
//                // 注销当前用户
//                TmallUtil.savePreferences(this, 0, "", "", "");
//                finish();
  //              break;
        }
        return true;
    }

    static class Adapter extends FragmentPagerAdapter {
        // Fragment 集合
        private final List<Fragment> fragments = new ArrayList<>();
        // Fragment Title 集合
        private final List<String> fragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        // 添加 Fragment 和 Fragment Title
        public void addFragment(Fragment fragment, String title) {
            fragments.add(fragment);
            fragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitles.get(position);
        }
    }
}
