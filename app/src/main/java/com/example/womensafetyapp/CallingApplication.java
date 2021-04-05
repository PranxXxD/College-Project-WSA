package com.example.womensafetyapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.firebase.firestore.FirebaseFirestore;

import static android.Manifest.permission.CALL_PHONE;

public class CallingApplication extends AppCompatActivity {

    private EditText editText1,editText2,editText3,editText4;
    TextView Textview;
    public Object TextView;
//    Button button1,button2,button3,button;


    FirebaseFirestore dbroot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calling_application);
        Textview=findViewById(R.id.editText1);
        //editText1 = findViewById(R.id.editText1);
//        editText = findViewById(R.id.editText);
//        editText1=(EditText) findViewById(R.id.editText1);
//        editText2=(EditText)findViewById(R.id.editText2);
//        editText3=(EditText)findViewById(R.id.editText3);
//        editText4=(EditText) findViewById(R.id.editText4);
//        button=(Button)findViewById(R.id.button);
//        button1=(Button)findViewById(R.id.button1);
//        button2=(Button)findViewById(R.id.button2);
//        button3=(Button)findViewById(R.id.button3);

        dbroot=FirebaseFirestore.getInstance();
        ActivityCompat.requestPermissions(this, new String[]{CALL_PHONE}, PackageManager.PERMISSION_GRANTED);
//        button1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                insertdata();
//            }
//        });
//

    }
//
//    private void insertdata() {
//        Map<String,String> items = new HashMap<>();
//        items.put("phone number",editText1.getText().toString().trim());
//        items.put("phone number",editText2.getText().toString().trim());
//        items.put("phone number",editText3.getText().toString().trim());
//
//        dbroot.collection("Users")
//                .add(items);
//                  .addOnCompleteListener(new OnCompleteListener<DocumentReference>(){
//                     @Override
//                    public void onComplete(@NonNull Task<DocumentReference>Task){
//                         editText1.setText("");
//                     editText2.setText("");
//                     editText3.setText("");
//                     Toast.makeText(getApplicationContext(),"Inserted Successfully",Toast.LENGTH_LONG).show();
//
//
//
//            }
//        });


//    }
   public void callbutton(View view) {
       if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        startActivity(new Intent(Intent.ACTION_CALL, Uri.fromParts("tel", Textview.getText().toString(), null)));

    }


    public void CallButton(View view) {
        if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        startActivity(new Intent(Intent.ACTION_CALL, Uri.fromParts("tel", editText4.getText().toString(), null)));

    }
    public void back_button(View view){
        Intent intent=new Intent(getApplicationContext(),AllCategories.class);
        startActivity(intent);
    }



}