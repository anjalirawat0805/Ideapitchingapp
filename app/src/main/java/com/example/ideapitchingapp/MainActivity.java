package com.example.ideapitchingapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements IdeaRVAdapter.IdeaClickInterface {

    private RecyclerView ideaRV;
    private ProgressBar loadingPB;
    private FloatingActionButton addFAB;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ArrayList<IdeaRVModel> ideaRVModelArrayList;
    private RelativeLayout bottomSheetRL;
    private IdeaRVAdapter ideaRVAdapter;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ideaRV = findViewById(R.id.idRVIdeas);
        loadingPB = findViewById(R.id.idPBLoading);
        addFAB = findViewById(R.id.idAddFAB);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Ideas");
        ideaRVModelArrayList = new ArrayList<>();
        bottomSheetRL = findViewById(R.id.idRLBSheet);
        mAuth = FirebaseAuth.getInstance();
        ideaRVAdapter = new IdeaRVAdapter(ideaRVModelArrayList,this ,this);
        ideaRV.setLayoutManager(new LinearLayoutManager(this));
        ideaRV.setAdapter(ideaRVAdapter);   //add adapter class
        addFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,AddIdeaActivity.class)); //adding new idea
            }
        });

        getAllIdeas();

    }

    private void getAllIdeas(){
        ideaRVModelArrayList.clear();
        // read all the data from database
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                  //call when new child is added
                loadingPB.setVisibility(View.GONE);
                ideaRVModelArrayList.add(snapshot.getValue(IdeaRVModel.class));
                ideaRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                ideaRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                loadingPB.setVisibility(View.GONE);
                ideaRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                ideaRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onIdeaClick(int position) {
       displayBottomSheet(ideaRVModelArrayList.get(position));
    }

    // to display the bottom sheet
    private void displayBottomSheet(IdeaRVModel ideaRVModel){
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View layout = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_dialog,bottomSheetRL);
        bottomSheetDialog.setContentView(layout);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();

        TextView ideaNameTV = findViewById(R.id.idTVIdeaName);
        TextView ideaDescTV = findViewById(R.id.idTVDescription);
        TextView ideaContactInfoTV = findViewById(R.id.idTVContactInfo);
        TextView ideaUserTV = findViewById(R.id.idTVUser);
        ImageView ideaIV = findViewById(R.id.idIVIdea);
        Button editBtn = findViewById(R.id.idBtnEdit);




        ideaDescTV.setText(ideaRVModel.getIdeaDescription());
        ideaNameTV.setText(ideaRVModel.getIdeaName());
        ideaContactInfoTV.setText(ideaRVModel.getIdeaContactInfo());
        ideaUserTV.setText(ideaRVModel.getIdeaUser());
        Picasso.get().load(ideaRVModel.getIdeaImg()).into(ideaIV);

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,EditIdeaActivity.class);
                i.putExtra("idea", ideaRVModel);
                startActivity(i);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch(id) {
            case R.id.idLogOut:
                Toast.makeText(this, "User Logged Out..", Toast.LENGTH_SHORT).show();
                mAuth.signOut();
                Intent i = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(i);
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}