package com.mezzsy.copyzhihunews.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mezzsy.copyzhihunews.R;
import com.mezzsy.copyzhihunews.adapter.RecyclerViewAdapter;
import com.mezzsy.copyzhihunews.event.LoginEvent;
import com.mezzsy.copyzhihunews.presenter.Presenter;
import com.mezzsy.copyzhihunews.state.LoginContext;
import com.mezzsy.copyzhihunews.state.LogoutState;
import com.mezzsy.copyzhihunews.view.IView;
import com.mezzsy.copyzhihunews.view.LoadMoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener, IView, SwipeRefreshLayout.OnRefreshListener,
        RecyclerViewAdapter.OnItemClickListenter {
    private static final String TAG = "MainActivity";
    private static final int WHAT_SUCCESS = 0;
    private static final int WHAT_FAIL = 1;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private View headLayout;
    private CircleImageView imgHeader;
    private TextView tvName;
    private Presenter presenter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView tvTitle;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case WHAT_SUCCESS:
                    adapter.notifyDataSetChanged();
                    adapter.updateBanner();
                    break;
                case WHAT_FAIL:
                    Toast.makeText(MainActivity.this
                            , "出现未知错误", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
            swipeRefreshLayout.setRefreshing(false);
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
        EventBus.getDefault().post(new LoginEvent());
    }

    @Override
    protected void onResume() {
        super.onResume();
        onRefresh();
    }

    private void initData() {
        EventBus.getDefault().register(this);
        presenter = new Presenter(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void LoginEventBus(LoginEvent event) {
        if (LoginContext.getInstance().getState() instanceof LogoutState) {
            tvName.setText("请登入");
            Glide.with(this).load(R.mipmap.ic_launcher_round).into(imgHeader);
        } else {
            //TODO
        }
    }

    @SuppressLint("ResourceAsColor")
    private void initView() {
        drawerLayout = findViewById(R.id.drawer_layout);
        swipeRefreshLayout = findViewById(R.id.swipe_fresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(R.color.colorPrimary);

        toolbar = findViewById(R.id.tool_bar);
        tvTitle = findViewById(R.id.tv_title);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }

        navigationView = findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.home);
        navigationView.setNavigationItemSelectedListener(this);
        headLayout = navigationView.getHeaderView(0);
        imgHeader = headLayout.findViewById(R.id.img);
        tvName = headLayout.findViewById(R.id.name);

        recyclerView = findViewById(R.id.recycler_view);
        adapter = new RecyclerViewAdapter();
        adapter.setListenter(this);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        recyclerView.addOnScrollListener(new LoadMoreListener() {
            @Override
            public void onLoadMore() {
                presenter.loadMore();
            }

            @Override
            public void setTitle(String title) {
                tvTitle.setText(title);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.news:
                break;
            case R.id.night:
                break;
            case R.id.setting:
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void load(boolean isSuccess) {
        if (isSuccess) handler.sendEmptyMessage(WHAT_SUCCESS);
        else handler.sendEmptyMessage(WHAT_FAIL);
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        presenter.swipeRefresh();
    }

    /**
     * 处理RecyclerView子项点击事件
     * @param id
     */
    @Override
    public void click(int id) {
        Intent intent = new Intent(this, NewsActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }
}
