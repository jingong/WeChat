package com.edu.ldu.cn.wechat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;
import android.widget.RadioGroup;
import com.edu.ldu.cn.wechat.fragment.ChatFragment;
import com.edu.ldu.cn.wechat.fragment.FriendFragment;
import com.edu.ldu.cn.wechat.fragment.MineFragment;

public class MainActivity extends FragmentActivity
{
    private Fragment f1;
    private Fragment f2;
    private Fragment f3;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initFragment();
        RadioGroup rg = (RadioGroup)findViewById(R.id.tabs);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                switch (checkedId){
                    case  R.id.tab_chat:
                        ft.show(f1).hide(f2).hide(f3);
                        break;
                    case R.id.tab_friend:
                        ft.show(f2).hide(f1).hide(f3);
                        break;
                    case R.id.tab_mine:
                        ft.show(f3).hide(f2).hide(f1);
                        break;
                }
                ft.commit();
            }
        });
    }
    private void initFragment() {
        f1 = new ChatFragment();
        f2 = new FriendFragment();
        f3 = new MineFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container,f1,"chat")
                .add(R.id.container,f2,"friend")
                .add(R.id.container,f3,"mine")
                .commit();
        getSupportFragmentManager().beginTransaction()
                .hide(f2)
                .hide(f3)
                .commit();
    }

}

