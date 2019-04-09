package codescanner.gurkirat.aarushi.codescanner1;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Tabs extends Fragment {
    View view;
    ViewPager pager;
    CameraPage camera;
    CodeList list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tabs, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        camera = new CameraPage();
        list = new CodeList();
        pager = view.findViewById(R.id.pager);
        pager.setAdapter(new MyPagerAdapter(getChildFragmentManager(), camera, list));
    }

    void viewCodes(){
        pager.setCurrentItem(1);
    }

    void viewCamera(){
        pager.setCurrentItem(0);
    }

    public static class MyPagerAdapter extends FragmentPagerAdapter {

        CameraPage camera;
        CodeList list;

        MyPagerAdapter(FragmentManager fragmentManager, CameraPage camera, CodeList list) {
            super(fragmentManager);
            this.camera = camera;
            this.list = list;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:return camera;
                default:return list;
            }
        }
    }

    void putImage(Uri uri){
        camera.putImage(uri);
    }

    void onBack(){
        camera.onBack();
    }

    void updateCodesList(){
        list.updateList();
    }

}
