package com.example.dailysentence;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.Random; //랜덤함수 import

public class DatabaseActivity extends AppCompatActivity {
    static final String sentence = "sentence";
    public static Context context_database;//다른액티비티에서 함수가져다쓸수있게
    public static String set_sentence;//문장 변수

    private SQLiteDatabase sqliteDB;

    private String numbers[];
    {
        numbers = new String[]{"one","two","three","four"};
    }
    private String daily_sentence[];
    {
        daily_sentence = new String[]{"삶이 있는한 희망은 있다.",
                "언제나 현재에 집중할 수있다면 행복할것이다.",
                "진정으로 웃으려면 고통을 참아야하며, 나아가 즐길 줄 알아야 해.",
                "신은 용기있는자를 결코 버리지 않는다.",
                "단순하게 살아라. 우리는 얼마나 복잡한 삶을 살아가는가?",
                "먼저핀 꽃은 먼저진다. 서두를 필요없다.",
                "행복한 삶을 살기위해 필요한 것은 거의 없다",
                "절대 어제를 후회하지 마라",
                "모든 인생은 실험이다.",
                "한번의 실패와 영원한 실패를 혼동하지 마라",
                "내일 죽을 것처럼 오늘을 살아라",
                "1퍼센트의 가능성, 그것이 나의 길이다",
                "늙고 젊은 것은 신념이 늙었느냐 젊었느냐 하는데 있다",
                "해답은 그 물음 속에 있다",
                "해야 할 것을 하라",
                "용기를 잃은 사람은 모든것을 잃은 것이다",
                "고개 숙이지 마십시오",
                "자신을 내보여라",
                "믿는 대로 될것이다",
                "인생의 주인공은 나임을 잊지마라."};
    }



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);//데이터 베이스 액티비티와 로딩화면을 동시에 함

        context_database = this;//다른액티비티에서 함수가져다쓸수있게

        sqliteDB = init_database();
        init_tables();
        load_values();

        //Toast.makeText(getApplicationContext(), set_sentence, Toast.LENGTH_LONG).show();//변수에 잘들어갔나 확인
        startloading();


    }
    private void startloading(){//어플킬때 대기 화면 2초
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        },2000);
    }


    private SQLiteDatabase init_database() {//데이터베이스 생성

        SQLiteDatabase db = null;

        File file = new File(getFilesDir(), "database.db");

        System.out.println("PATH : " + file.toString());
        try {
            db = SQLiteDatabase.openOrCreateDatabase(file,null);
        } catch (SQLiteException e) {
            e.printStackTrace();
        }

        if (db == null) {
            System.out.println("DB creation failed. " + file.getAbsolutePath());
        }
        return db;
    }

    private void init_tables() {//데이터 베이스 테이블 생성

        if (sqliteDB != null) {
            String sqlCreateTbl = "CREATE TABLE IF NOT EXISTS ORDER_T (NUM TEXT, SENTENCE TEXT)";

            System.out.println(sqlCreateTbl);
            sqliteDB.execSQL(sqlCreateTbl);
            sqliteDB.execSQL("delete from ORDER_T ");//데이터테이블 초기화
        }
    }


    private void load_values() {

        for (int i = 0; i < numbers.length; i++)
        {//테이블에 리스트에 있는 것들 순서대로 데이터 추가
            String sqlInsert = "INSERT INTO ORDER_T (NUM, SENTENCE) VALUES ('" + numbers[i] + "', '" + daily_sentence[i]+"');";
            sqliteDB.execSQL(sqlInsert);
        }

        //데이터 추가
        //String sqlInsert = "INSERT INTO ORDER_T (NUM, SENTENCE) VALUES (1,'안녕하세요')";
        //sqliteDB.execSQL(sqlInsert);
        //String sqlInsert2 = "INSERT INTO ORDER_T (NUM, SENTENCE) VALUES (2,'hi')";
        //sqliteDB.execSQL(sqlInsert2);

        if (sqliteDB != null) {//비어있지 않으면?
            String sqlQueryTbl = "SELECT * FROM ORDER_T";
            Cursor cursor = null;

            // 쿼리 실행
            cursor = sqliteDB.rawQuery(sqlQueryTbl, null);

            int selection = new Random().nextInt(cursor.getCount());//0부터 문장개수만큼 랜덤한 정수 뽑기

            if (cursor.moveToPosition(selection)) { // 커서를 랜덤한 포지션으로 이동
                //usersname값으로 가져오는 걸로 바꾸기 ex) 창엽아, 포기하지마
                //글자수에 따라 3개로나눌지 2개로 나눌지 결정
                //do {
                    //String num = cursor.getString(0);
                    //TextView textView1 = (TextView) findViewById(R.id.usersname);
                    //textView1.setText(num);

                    //sentence값 가져오기
                    set_sentence = cursor.getString(1);//1은 문장의 column을 말함.
                    //TextView textView2 = (TextView) findViewById(R.id.sentence);
                    //textView2.setText(set_sentence);
                //}while(cursor.moveToNext());//커서가 다음으로 이동//결국 마지막 리스트만 출력
            }
        }
    }




}


//임의의 테이블 값 가져오는 거랑 원하는 위치의 데이터 가져오기. 랜덤으로 데이터 뽑히게 만들기(해결)
//처음엔 됬는데 수정하다가 뭘잘 못 건드렸는지 안됨..-> 데이터 테이블을 계속추가만 하고 리셋하지 않았음.(해결)
//데이터리스트화 하는 것을 따로 함수로 만들기 or 자바클래스를 하나 더만들어 데이터 전용 클래스를 만들기
/*//함수화 하다 실패한것
    private String[] name_list_set(){
        String numbers[];
        {
            numbers = new String[]{"one","two","three","four"};
        }
        return numbers;

    }
    private String[] sentence_list_set(){
        final String daily_sentence[];
        {
            daily_sentence = new String[]{"삶이 있는한 희망은 있다.",
                    "언제나 현재에 집중할수 있다면 행복할것이다.",
                    "진정으로 웃으려면 고통을 참아야하며, 나아가 즐길 줄 알아야 해.",
                    "신은 용기있는자를 결코 버리지 않는다."};
        }
        return daily_sentence;
    }*/
