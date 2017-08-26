    package com.tkpraktikum.fragments;

    /**
     * Created by Abu on 8/14/2017.
     */

    import android.content.Intent;
    import android.os.Bundle;
    import android.support.v4.app.Fragment;
    import android.view.LayoutInflater;
    import android.view.Menu;
    import android.view.MenuInflater;
    import android.view.MenuItem;
    import android.view.View;
    import android.view.ViewGroup;

    import com.tkpraktikum.activity.EditProfile;
    import com.tkpraktikum.activity.ProfileActivity;
    import com.tkpraktikum.R;

    public class ItemFourFragment extends Fragment {
        public static ItemFourFragment newInstance() {
            ItemFourFragment fragment = new ItemFourFragment();
            return fragment;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setHasOptionsMenu(true);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_item_four, container, false);
        }

        /** @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.main, menu);
            return true;
        } **/

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            inflater.inflate(R.menu.navigation, menu);
            super.onCreateOptionsMenu(menu, inflater);
        }

        public boolean onOptionsItemSelected(MenuItem item) {
            int id=item.getItemId();

            if(id==R.id.eProfile) {
                Intent intent = new Intent(getActivity(), EditProfile.class);
                startActivity(intent);
                return true;
            }

            if(id==R.id.eReset) {
                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                startActivity(intent);
                return true;
            }

            if(id==R.id.eLogout) {
                Intent intent = new Intent(getActivity(), LoginFragment.class);
                startActivity(intent);
                return true;
            }
            return false;
        }


    }
