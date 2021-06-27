package com.example.dailysentence;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.NoCopySpan;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class StartActivity extends AppCompatActivity {
    //SharedPreferences sharedPreferences;//related radio
    EditText et;
    String name = "myFile";
    String radio = "rdFile";//related radio
    RadioGroup radioGroup;//related radio

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        radioGroup = (RadioGroup) findViewById(R.id.radio_group); //related radio
        //radioGroup.check(sharedPreferences.getInt("radioGroup",0));//related radio
        SharedPreferences sharedPreferences_radio = getSharedPreferences(radio, MODE_PRIVATE);//related radio
        Integer radi = sharedPreferences_radio.getInt("radio",0);//related radio
        radioGroup.check(radi);//related radio

        et = (EditText)findViewById(R.id.user_name);
        SharedPreferences sharedPreferences = getSharedPreferences(name,MODE_PRIVATE);
        String str = sharedPreferences.getString("name","");
        et.setText(str);
    }

    public void onclick(View view) {

        Toast.makeText(this,"저장되었습니다.",Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onStop() {//어플 종료 되거나 그 화면을 나갔을때
        super.onStop();

        //이름 저장
        SharedPreferences sf = getSharedPreferences(name, 0);
        SharedPreferences.Editor editor = sf.edit();
        String str = et.getText().toString();
        editor.putString("name", str);
        //editor.putString("xx","xx");//없어도됨
        editor.commit();

        //라디오 버튼 저장
        SharedPreferences radi = getSharedPreferences(radio, 0);//related radio
        SharedPreferences.Editor editor2 = radi.edit();//related radio

        //Integer radi = radioGroup.getCheckedRadioButtonId();//related radio
        editor2.putInt("radio", radioGroup.getCheckedRadioButtonId());//related radio//하나로합침.
        //editor.putInt("radio", radi);//related radio
        editor2.commit();
    }
}
