package codescanner.gurkirat.aarushi.codescanner1;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

public class changeNameDialog extends DialogFragment {

    viewChange mCallback;
    AppCompatEditText name;
    AppCompatButton save;
    dataBaseAdapter adapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (viewChange) context;
        } catch (ClassCastException e) {
            Log.e("Exception", e.toString());
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.change_name, null);
        builder.setView(view);
        builder.setTitle("Change Name");
        save = view.findViewById(R.id.save);
        name = view.findViewById(R.id.name);
        String oldText = getArguments().getString("Name");
        name.setText(oldText);
        adapter = new dataBaseAdapter(getActivity());
        save.setOnClickListener(view1 -> {
            String text = name.getText().toString().trim();
            if(text.length()>0){
                if(adapter.checkName(text)) Toast.makeText(getActivity(), "Name should be unique!!", Toast.LENGTH_SHORT).show();
                else {
                    mCallback.changeName(text);
                    dismiss();
                }
            }
            else Toast.makeText(getActivity(), "Name can't be empty!!", Toast.LENGTH_SHORT).show();
        });
        return builder.create();
    }
}
