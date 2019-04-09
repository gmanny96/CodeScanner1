package codescanner.gurkirat.aarushi.codescanner1;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Code extends Fragment {
    View view;

    EditText text;
    FloatingActionButton run;
    Toolbar bar;
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
        view = inflater.inflate(R.layout.code, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        bar = view.findViewById(R.id.bar);
        text = view.findViewById(R.id.code);
        run = view.findViewById(R.id.run);

        adapter = new dataBaseAdapter(getActivity());

        if(getArguments()!=null) {
            bar.setTitle(getArguments().getString("Name", "Code "+getDate()));
            if(bar.getTitle().equals(""))
                bar.setTitle(getDate());
            text.setText(getArguments().getString("Code", ""));
        }

        if(getArguments()==null){
            bar.setTitle("Code "+getDate());
            run.hide();
        }

        bar.setNavigationOnClickListener(view -> {
            getActivity().getSupportFragmentManager().popBackStack();
            modifyCodeInDatabase();
        });

        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(text.getText().length()>0)
                    run.show();
                else run.hide();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        bar.setOnClickListener(view ->{
            mCallback.openChangeNameDialog(bar.getTitle().toString());
        });

        run.setOnClickListener(view -> {
            mCallback.output(bar.getTitle().toString(), text.getText().toString());
            modifyCodeInDatabase();
        });

    }

    private String getDate()
    {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy HH:mm:ss", Locale.getDefault());

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        return formatter.format(calendar.getTime());
    }

    void changeName(String name){
        adapter.updateName(bar.getTitle().toString(), name);
        bar.setTitle(name);
    }

    void modifyCodeInDatabase(){
        if(text.getText().toString().trim().length()>0) {
            if (adapter.checkName(bar.getTitle().toString())) {
                adapter.modifyCode(bar.getTitle().toString(), text.getText().toString());
            } else {
                adapter.insertCode(bar.getTitle().toString(), text.getText().toString(), System.currentTimeMillis());
            }
        }
        mCallback.updateCodesList();
    }

    @Override
    public void onStop() {
        super.onStop();
        modifyCodeInDatabase();
    }
}
