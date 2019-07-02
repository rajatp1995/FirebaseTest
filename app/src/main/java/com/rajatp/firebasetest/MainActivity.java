package com.rajatp.firebasetest;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    ChildEventListener listenerChild;

    ListView listV;
    ArrayAdapter<String> arrayAdapter;
    List<String> words = new ArrayList<>();
    EditText addD, delD;
    List<KeyValue> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listV = findViewById(R.id.listView);
        addD = findViewById(R.id.textToAdd);
        delD = findViewById(R.id.textToDelete);

        myRef = database.getReference("");
        databaseGetFunc();
    }

    public void databaseGetFunc() {
        listenerChild = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                KeyValue obj = new KeyValue();
                obj.key = dataSnapshot.getKey();
                obj.value = dataSnapshot.getValue().toString();
                data.add(obj);

                words.add(dataSnapshot.getValue().toString());
                arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, words);
                listV.setAdapter(arrayAdapter);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String text = dataSnapshot.getValue().toString() + " is the new value below " + s;
                Toast.makeText(getApplicationContext(),text,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue().toString() + " got deleted";
                Toast.makeText(getApplicationContext(),text,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Toast.makeText(getApplicationContext(),dataSnapshot.getValue().toString(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
            }
        };

        myRef.addChildEventListener(listenerChild);
    }

    public void addData(View view) {
        String text = addD.getText().toString();
        String tempKey = myRef.push().getKey();
        myRef.child(tempKey).setValue(text);
        addD.setText("");
    }

    public void delData(View view) {
        String text = delD.getText().toString();
        for (int i=0;i<data.size();i++) {
            if (data.get(i).value.equals(text)) {
                myRef.child(data.get(i).getKey()).removeValue();
            }
        }
        delD.setText("");
    }
}
