package codescanner.gurkirat.aarushi.codescanner1;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.transitionseverywhere.Transition;

public class Splash extends Fragment implements Animation.AnimationListener {
    View view;
    static viewChange mCallback;
    AppCompatTextView text, brackets;
    RelativeLayout layout;
    Animation animFadein, animSlideup, animFadein2;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (viewChange) context;
        } catch (ClassCastException e) {
            Log.e("Exception", e.toString());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.splash, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        brackets = view.findViewById(R.id.brackets);
        text = view.findViewById(R.id.text);

        animFadein = AnimationUtils.loadAnimation(getActivity().getApplicationContext(),
                R.anim.fade_in);

        brackets.startAnimation(animFadein);
        animSlideup = AnimationUtils.loadAnimation(getActivity().getApplicationContext(),
                R.anim.slide_up);


        brackets.startAnimation(animSlideup);
        animFadein2 = AnimationUtils.loadAnimation(getActivity().getApplicationContext(),
                R.anim.fade_in);
        animFadein2.setAnimationListener(this);
        text.startAnimation(animFadein2);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        mCallback.openTabs();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}