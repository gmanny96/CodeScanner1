package codescanner.gurkirat.aarushi.codescanner1;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

public class Setting extends Fragment {

    View view;
    Toolbar bar;
    Switch sp, op;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.settings, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();

        op = view.findViewById(R.id.swop);
        sp = view.findViewById(R.id.swsp);

        if(pref.getBoolean("op", false))
            op.setChecked(true);

        if(pref.getBoolean("sp", false))
            sp.setChecked(true);

        bar = view.findViewById(R.id.bar);

        bar.setNavigationOnClickListener(view -> getActivity().getSupportFragmentManager().popBackStack());

        op.setOnCheckedChangeListener((compoundButton, b) -> editor.putBoolean("op", b).commit());

        sp.setOnCheckedChangeListener((compoundButton, b) -> editor.putBoolean("sp", b).commit());
    }
}
