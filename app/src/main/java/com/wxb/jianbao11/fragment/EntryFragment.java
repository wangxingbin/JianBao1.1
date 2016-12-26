package com.wxb.jianbao11.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wxb.jianbao11.R;
import com.wxb.jianbao11.activity.GuidePageActivity;
import com.wxb.jianbao11.activity.Login;

/**
 * Created by 诺古 on 2016/12/20.
 */

public class EntryFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_entry,null);
        view.findViewById(R.id.btn_entry).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GuidePageActivity activity = (GuidePageActivity) getActivity();
                Intent intent = new Intent(activity,Login.class);
                startActivity(intent);
                activity.entryApp();
            }
        });
        return view;
    }
}
