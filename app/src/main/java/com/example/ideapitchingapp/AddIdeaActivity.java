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

public class AddIdeaActivity extends AppCompatActivity {

    private TextInputEditText ideaNameEdt,ideaUserEdt,ideaContactEdt,ideaImageEdt,ideaDescEdt;
    private Button addIdeaBtn;
    private ProgressBar loadingPB;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String ideaID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_idea);
        ideaNameEdt = findViewById(R.id.idEdtIdeaName);
        ideaUserEdt = findViewById(R.id.idEdtIdeaUser);
        ideaContactEdt = findViewById(R.id.idEdtIdeaContact);
        ideaImageEdt = findViewById(R.id.idEdtIdeaImageLink);
        ideaDescEdt = findViewById(R.id.idEdtIdeaDesc);
        addIdeaBtn = findViewById(R.id.idBtnAddIdea);
        loadingPB = findViewById(R.id.idPBLoading);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Ideas");

        addIdeaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingPB.setVisibility(View.VISIBLE);
                String ideaName = ideaNameEdt.getText().toString();
                String ideaUser = ideaUserEdt.getText().toString();
                String ideaContact = ideaContactEdt.getText().toString();
                String ideaImage = ideaImageEdt.getText().toString();
                String ideaDesc = ideaDescEdt.getText().toString();
                ideaID = ideaName;
                // passing values to IdeaRVModel
                IdeaRVModel ideaRVModel = new IdeaRVModel(ideaName,ideaDesc,ideaUser,ideaContact,ideaImage,ideaID);

                // adding model to database
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        loadingPB.setVisibility(View.GONE);
                        databaseReference.child(ideaID).setValue(ideaRVModel);
                        //add all the data for a specific idea in our database
                        Toast.makeText(AddIdeaActivity.this, "Idea Added..", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddIdeaActivity.this,MainActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(AddIdeaActivity.this, "Error is"+error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }
}