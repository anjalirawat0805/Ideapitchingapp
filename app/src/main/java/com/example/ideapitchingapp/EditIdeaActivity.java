package com.example.ideapitchingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class EditIdeaActivity extends AppCompatActivity {

    private TextInputEditText ideaNameEdt,ideaUserEdt,ideaContactEdt,ideaImageEdt,ideaDescEdt;
    private Button updateIdeaBtn, deleteIdeaBtn;
    private ProgressBar loadingPB;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String ideaID;
    private IdeaRVModel ideaRVModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_idea);
        firebaseDatabase = FirebaseDatabase.getInstance();
        ideaNameEdt = findViewById(R.id.idEdtIdeaName);
        ideaUserEdt = findViewById(R.id.idEdtIdeaUser);
        ideaContactEdt = findViewById(R.id.idEdtIdeaContact);
        ideaImageEdt = findViewById(R.id.idEdtIdeaImageLink);
        ideaDescEdt = findViewById(R.id.idEdtIdeaDesc);
        updateIdeaBtn = findViewById(R.id.idBtnUpdateIdea);
        deleteIdeaBtn = findViewById(R.id.idBtnDeleteIdea);
        loadingPB = findViewById(R.id.idPBLoading);
        //getting data from previous activity
        ideaRVModel = getIntent().getParcelableExtra("idea"); //passing with the help of idea key
        if(ideaRVModel!=null){
            ideaNameEdt.setText(ideaRVModel.getIdeaName());  //set data to edit text
            ideaUserEdt.setText(ideaRVModel.getIdeaUser());
            ideaContactEdt.setText(ideaRVModel.getIdeaContactInfo());
            ideaImageEdt.setText(ideaRVModel.getIdeaImg());
            ideaDescEdt.setText(ideaRVModel.getIdeaDescription());
            ideaID = ideaRVModel.getIdeaID();
        }

        databaseReference = firebaseDatabase.getReference("Ideas").child(ideaID);
        updateIdeaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingPB.setVisibility(View.VISIBLE);
                String ideaName = ideaNameEdt.getText().toString();
                String ideaUser = ideaUserEdt.getText().toString();
                String ideaContact = ideaContactEdt.getText().toString();
                String ideaImage = ideaImageEdt.getText().toString();
                String ideaDesc = ideaDescEdt.getText().toString();

                Map<String,Object> map = new HashMap<>();
                map.put("ideaName",ideaName);
                map.put("ideaDescription",ideaDesc);
                map.put("ideaUser",ideaUser);
                map.put("ideaContactInfo",ideaContact);
                map.put("ideaImg",ideaImage);
                map.put("ideaID",ideaID);


                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        loadingPB.setVisibility(View.GONE);
                        databaseReference.updateChildren(map);
                        Toast.makeText(EditIdeaActivity.this, "Idea Updated..", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditIdeaActivity.this,MainActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(EditIdeaActivity.this, "Fail to update Idea..", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });

        deleteIdeaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteIdea();
            }
        });
    }

    private void deleteIdea(){
        databaseReference.removeValue();
        Toast.makeText(this, "Idea Deleted..", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(EditIdeaActivity.this, MainActivity.class));
    }
}