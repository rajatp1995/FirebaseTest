package com.rajatp.firebasetest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseSkeleton extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    ChildEventListener listenerChild;
    ValueEventListener listenerValue;
    ValueEventListener test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_skeleton);

        myRef = database.getReference();

        childEventFunc();
        valueEventFunc();
        singleEventFunc();
        queryFunc();
        saveDataFunc();
    }

    public void childEventFunc() {
        listenerChild = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                dataSnapshot.getValue();
                dataSnapshot.getChildrenCount();
                dataSnapshot.getChildren();
                dataSnapshot.child("").child("").getValue();
                dataSnapshot.exists();
                dataSnapshot.getKey();
                dataSnapshot.getPriority();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        myRef.addChildEventListener(listenerChild);
    }

    public void valueEventFunc() {
        listenerValue = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        myRef.addValueEventListener(listenerValue);
    }

    public void singleEventFunc() {
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void queryFunc() {
        myRef.orderByChild("root").addValueEventListener(test);
        myRef.orderByKey().addValueEventListener(test);
        myRef.orderByValue().addValueEventListener(test);

        myRef.limitToFirst(10).addValueEventListener(test);
        myRef.limitToLast(20).addValueEventListener(test);

        myRef.orderByValue().startAt(3).addValueEventListener(test);
        myRef.orderByChild("root").endAt("test").addValueEventListener(test);
        myRef.orderByKey().equalTo(121).addValueEventListener(test);
    }

    public void saveDataFunc() {
        myRef.child("root").setValue("Hello World");

        myRef.child("test").setValue("Demo").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.i("Message", "Successful!");
            }
        });

        String tempKey = myRef.push().getKey();
        myRef.child(tempKey).setValue("Hello");
    }
}
