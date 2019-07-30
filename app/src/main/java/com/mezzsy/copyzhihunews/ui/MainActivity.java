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
    private Toolbar mToolbar;
    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;
    private View mHeadLayout;
    private CircleImageView mImgHeader;
    private TextView tvName;
    private Presenter mPresenter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TextView tvTitle;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case WHAT_SUCCESS:
                    mAdapter.notifyDataSetChanged();
                    mAdapter.updateBanner();
                    break;
                case WHAT_FAIL:
                    Toast.makeText(MainActivity.this
                            , "出现未知错误", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
            mSwipeRefreshLayout.setRefreshing(false);
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
    protected void onStart() {
        super.onStart();
        onRefresh();
    }

    private void initData() {
        EventBus.getDefault().register(this);
        mPresenter = new Presenter(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void LoginEventBus(LoginEvent event) {
        if (LoginContext.getInstance().getState() instanceof LogoutState) {
            tvName.setText("请登入");
            Glide.with(this).load(R.mipmap.ic_launcher_round).into(mImgHeader);
        } else {
            //TODO
        }
    }

    @SuppressLint("ResourceAsColor")
    private void initView() {
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mSwipeRefreshLayout = findViewById(R.id.swipe_fresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);

        mToolbar = findViewById(R.id.tool_bar);
        tvTitle = findViewById(R.id.tv_title);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }

        mNavigationView = findViewById(R.id.nav_view);
        mNavigationView.setCheckedItem(R.id.home);
        mNavigationView.setNavigationItemSelectedListener(this);
        mHeadLayout = mNavigationView.getHeaderView(0);
        mImgHeader = mHeadLayout.findViewById(R.id.img);
        tvName = mHeadLayout.findViewById(R.id.name);

        mRecyclerView = findViewById(R.id.recycler_view);
        mAdapter = new RecyclerViewAdapter();
        mAdapter.setListenter(this);
        mRecyclerView.setAdapter(mAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);

        mRecyclerView.addOnScrollListener(new LoadMoreListener() {
            @Override
            public void onLoadMore() {
                mPresenter.loadMore();
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
                mDrawerLayout.openDrawer(GravityCompat.START);
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
        if (isSuccess) mHandler.sendEmptyMessage(WHAT_SUCCESS);
        else mHandler.sendEmptyMessage(WHAT_FAIL);
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        mPresenter.swipeRefresh();
    }

    /**
     * 处理RecyclerView子项点击事件
     *
     * @param id
     */
    @Override
    public void click(int id, boolean isTopStory, int postition) {
        Intent intent = new Intent(this, NewsActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("isTopStory", isTopStory);
        intent.putExtra("postition", postition);
        startActivity(intent);
    }
}
