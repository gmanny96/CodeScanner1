package codescanner.gurkirat.aarushi.codescanner1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.myhexaville.smartimagepicker.ImagePicker;

public class Activity extends AppCompatActivity implements viewChange{

    Tabs tabs;
    ImagePicker imagePicker;
    Code code;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();

        editor.putBoolean("op", false).apply();
        editor.putBoolean("sp", false).apply();

        setContentView(R.layout.activity);
        getSupportFragmentManager().beginTransaction().add(R.id.main, new Splash(), "Splash").commit();

    }

    @Override
    public void openCode(String name, String temp) {
        Bundle bundle = new Bundle();
        bundle.putString("Name", name);
        bundle.putString("Code", temp);
        code = new Code();
        code.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.main, code, "code").addToBackStack(null).commit();
    }

    @Override
    public void openTabs() {
        tabs = new Tabs();
        getSupportFragmentManager().beginTransaction().add(R.id.main, tabs, "tabs").commit();
    }

    @Override
    public void output(String name, String code) {
        Bundle bundle = new Bundle();
        bundle.putString("code", code);
        bundle.putString("name", name);
        Output fragobj = new Output();
        fragobj.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.main, fragobj, "output").addToBackStack(null).commit();
    }

    @Override
    public void viewCodes() {
        tabs.viewCodes();
    }

    @Override
    public void viewCamera() {
        tabs.viewCamera();
    }

    @Override
    public void openGallery() {
        imagePicker = new ImagePicker(this ,
                null ,
                (Uri imageUri) -> {
                    tabs.putImage(imageUri);
                });

        imagePicker.choosePicture(false);
    }

    @Override
    public void changeName(String name) {
        code.changeName(name);
    }

    @Override
    public void openChangeNameDialog(String name) {
        Bundle args = new Bundle();
        args.putString("Name", name);
        changeNameDialog dialog = new changeNameDialog();
        dialog.setArguments(args);
        dialog.show(getSupportFragmentManager(), "changeNameDialog");
    }

    @Override
    public void settings() {
        getSupportFragmentManager().beginTransaction().add(R.id.main, new Setting(), "settings").addToBackStack(null).commit();
    }

    @Override
    public void updateCodesList() {
        tabs.updateCodesList();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imagePicker.handleActivityResult(resultCode,requestCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(imagePicker!=null)
            imagePicker.handlePermission(requestCode, grantResults);
    }
}
