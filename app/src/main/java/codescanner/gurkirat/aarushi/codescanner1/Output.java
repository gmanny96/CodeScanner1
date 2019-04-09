package codescanner.gurkirat.aarushi.codescanner1;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import bsh.Interpreter;

public class Output extends Fragment {
    View view;
    AppCompatTextView text;
    Toolbar bar;
    String strtext;
    dataBaseAdapter adapter;
    viewChange mCallback;

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
        view = inflater.inflate(R.layout.output, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        text = view.findViewById(R.id.text);
        bar = view.findViewById(R.id.bar);

        adapter = new dataBaseAdapter(getActivity());
        strtext = getArguments().getString("code");


        bar.setTitle(getArguments().getString("name", "Code " + getDate()));
        if (bar.getTitle().equals(""))
            bar.setTitle("Code " + getDate());

        bar.setNavigationOnClickListener(view -> {
            getActivity().getSupportFragmentManager().popBackStack();
            modifyCodeInDatabase();
        });

        Interpreter i = new Interpreter();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream stdout = System.out;
        System.setOut(ps);
        try {
            i.eval(strtext);
        } catch (Exception e) {
            text.setText(e.toString());
        }
        String out = baos.toString();
        text.setText(out);

        System.setOut(stdout);

    }

    private String getDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy HH:mm:ss", Locale.getDefault());

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        return formatter.format(calendar.getTime());
    }

    @Override
    public void onPause() {
        super.onPause();
        modifyCodeInDatabase();
    }

    void modifyCodeInDatabase(){
        if (adapter.checkName(bar.getTitle().toString())) {
            adapter.modifyCode(bar.getTitle().toString(), strtext);
        } else
            adapter.insertCode(bar.getTitle().toString(), strtext, System.currentTimeMillis());

        mCallback.updateCodesList();
    }

}
