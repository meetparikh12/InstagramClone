package com.example.instagramclone;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;


import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;


/**
 * A simple {@link Fragment} subclass.
 */
public class UsersTab extends Fragment {
    private ListView listView;
    private ArrayList<String> arrayList;
    private ArrayAdapter<String> arrayAdapter;
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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(),UsersPosts.class);
                intent.putExtra("username",arrayList.get(position));
                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ParseQuery<ParseUser> parseQuery = ParseUser.getQuery();
                parseQuery.whereEqualTo("username",arrayList.get(position));
                parseQuery.getFirstInBackground(new GetCallback<ParseUser>() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if(user != null && e==null) {
                            final PrettyDialog prettyDialog = new PrettyDialog((getContext()));
                            prettyDialog.setTitle(user.getUsername() + "'s Info")
                                    .setMessage(user.get("profileName") +"\n"
                                            +user.get("bio") +"\n"
                                            +user.get("profession") +"\n"
                                            +user.get("hobbies"))
                                    .setIcon(R.drawable.person)
                                    .addButton(
                                            "OK", //button Text
                                            R.color.pdlg_color_white, //button text color
                                            R.color.pdlg_color_green, //button background color
                                            new PrettyDialogCallback() { //button OnClick listener
                                                @Override
                                                public void onClick() {
                                                    prettyDialog.dismiss();
                                                }
                                            }
                                    )
                                    .show();
                        }
                    }
                });

                return true;
            }
        });
        arrayList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,arrayList);
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
                        listView.setVisibility(View.VISIBLE);

                    }
                }else{
                    FancyToast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT,FancyToast.ERROR,true).show();
                }

            }

        });
        return view;
    }

}
