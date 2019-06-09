package com.example.instagramclone;


import android.os.Bundle;


import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class UsersTab extends Fragment {
    private ListView listView;
    private ArrayList<String> arrayList;
    private ArrayAdapter<String> arrayAdapter;
    private TextView tvBeforeLoading;
    ;

    public UsersTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_users_tab, container, false);
        listView = view.findViewById(R.id.myListView);
        arrayList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,arrayList);
        tvBeforeLoading = view.findViewById(R.id.tvBeforeLoading);
        tvBeforeLoading.setText("Loading.. Please wait!");
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if(e==null){
                    if(objects.size() > 0){
                        for(ParseUser users: objects){
                           arrayList.add(users.getUsername());
                        }
                        listView.setAdapter(arrayAdapter);
                        tvBeforeLoading.animate().rotation(360).alpha(0).setDuration(2000);
                        listView.setVisibility(View.VISIBLE);

                    }
                }else{
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

        });
        return view;
    }

}
