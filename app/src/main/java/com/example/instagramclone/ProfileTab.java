package com.example.instagramclone;


import android.app.ProgressDialog;
import android.os.Bundle;


import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileTab extends Fragment {
    private EditText edtProfileName, edtContactNumber, edtBio, edtHobbies, edtProfession;
    private Button btnUpdateInfo;
    private ProgressDialog progressDialog;
    public ProfileTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_tab, container, false);
        edtProfileName = view.findViewById(R.id.edtProfileName);
        edtBio = view.findViewById(R.id.edtBio);
        edtHobbies = view.findViewById(R.id.edtHobbies);
        edtContactNumber = view.findViewById(R.id.edtContactNumber);
        edtProfession = view.findViewById(R.id.edtProfession);
        btnUpdateInfo = view.findViewById(R.id.btnUpateInfo);
        final ParseUser parseUser = ParseUser.getCurrentUser();
        if(parseUser.get("profileName") == null){ edtProfileName.setText(""); }
        else { edtProfileName.setText(parseUser.get("profileName") + ""); }
        if(parseUser.get("bio") == null){ edtBio.setText(""); }
        else { edtBio.setText(parseUser.get("bio") + ""); }
        if(parseUser.get("contactNumber") == null){ edtContactNumber.setText(""); }
        else { edtContactNumber.setText(parseUser.get("contactNumber") + ""); }
        if(parseUser.get("hobbies") == null){ edtHobbies.setText(""); }
        else { edtHobbies.setText(parseUser.get("hobbies") + ""); }
        if(parseUser.get("profession") == null){ edtProfession.setText(""); }
        else { edtProfession.setText(parseUser.get("profession") + ""); }
        Log.i("TAG",getContext() +"");
        btnUpdateInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parseUser.put("profileName",edtProfileName.getText().toString());
                parseUser.put("bio",edtBio.getText().toString());
                parseUser.put("hobbies",edtHobbies.getText().toString());
                parseUser.put("contactNumber",edtContactNumber.getText().toString());
                parseUser.put("profession",edtProfession.getText().toString());
                progressDialog = new ProgressDialog(getContext());
                progressDialog.setMessage("Updating Info");
                progressDialog.show();
                parseUser.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e==null){
                            FancyToast.makeText(getContext(), "Info updated Successfully", Toast.LENGTH_SHORT,FancyToast.SUCCESS,true).show();
                        }else{
                            FancyToast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT,FancyToast.ERROR,true).show();
                        }
                        progressDialog.dismiss();
                    }
                });
            }
        });

        return view;
    }

}
