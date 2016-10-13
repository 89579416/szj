package com.szj.library.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.szj.library.R;
import com.szj.library.ui.annotation.MTFFragmentFeature;

import butterknife.ButterKnife;

/**
 *
 * Created by MasterFan on 2016/1/14.
 * description:all Fragment class can be extends this.
 */
public abstract class MTFBaseFragment extends Fragment {

    public Context context;

    private final static String TAG = MTFBaseFragment.class.getCanonicalName();

    public MTFBaseFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MTFFragmentFeature fragmentFeature = this.getClass().getAnnotation(MTFFragmentFeature.class);
        if(fragmentFeature == null) throw new IllegalArgumentException(">>> not set layout resources!");
        View view = inflater.inflate(fragmentFeature.layout(), container, false);
        context = getActivity();
        ButterKnife.bind(this, view);
        initialize();
        return view;
    }

    /**
     * fetch data and more ...
     */
    public abstract void initialize();

    /**
     * anim launch Activity
     * @param clazz class
     */
    public void animStart(Class clazz){
        Intent intent = new Intent(context, clazz);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.roll_up, R.anim.roll);
    }

    /**
     * anim launch Activity
     * @param intent intent
     */
    public void animStart(Intent intent){
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.roll_up, R.anim.roll);
    }

    /**
     * anim finish Activity
     */
    public void animFinish(){
        getActivity().finish();
        getActivity().overridePendingTransition(0, R.anim.roll_down);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        context = null;
    }
}
