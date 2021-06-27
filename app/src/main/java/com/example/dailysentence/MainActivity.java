package com.example.dailysentence;


import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MainActivity extends AppCompatActivity {
    public static  Context context_main;//다른액티비티에서 함수가져다쓸수있게
    public boolean on;//다른액티비티에서 변수가져다쓸수있게
    public NotificationCompat.Builder mbuilder;//다른액티비티에서 변수가져다쓸수있게
    ToggleButton toggleButton;//related toggle save
    String toggle = "tgFile";//related toggle save
    public TextView sentence_content;//문장내용위한 전역텍스트뷰변수


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context_main = this;//다른액티비티에서 함수가져다쓸수있게

        Intent intent = new Intent(this, DatabaseActivity.class);//대기화면+데이터베이스로감
        startActivity(intent);//원래의 subActivity를 업애고 데이터 베이스 액티비티로 넘김

        //여기해결해야함
        sentence_content = findViewById(R.id.tev_first_sentence);//문장내용
        //문장내용이 안넘어옴
        Toast.makeText(getApplicationContext(),((DatabaseActivity)DatabaseActivity.context_database).set_sentence, Toast.LENGTH_LONG).show();
        //여기해결해야함


        toggleButton = findViewById(R.id.btn_popup);//related toggle save
        SharedPreferences sharedPreferences_toggle = getSharedPreferences(toggle, MODE_PRIVATE);//related toggle save
        boolean tog = sharedPreferences_toggle.getBoolean("toggle", false);//related toggle save
        toggleButton.setChecked(tog);//related toggle save


    }

    public void onclick(View view) {
        switch (view.getId())
        {
            case R.id.btn_my:
            case R.id.btn_start:
                Intent intent_start = new Intent(this, StartActivity.class);
                startActivity(intent_start);
                break;
            case R.id.btn_time:

                Intent intent_time = new Intent(this, TimeActivity.class);
                startActivity(intent_time);
                break;
            //case R.id.btn_popup:
            //문장있는 액티비티로 넘어가는 기능
            //Intent intent_database = new Intent(this, DatabaseActivity.class);
            //startActivity(intent_database);

            //break;
            case R.id.btn_info:
                Intent intent_info = new Intent(this, InfoActivity.class);
                startActivity(intent_info);
                break;
        }

    }


    public void onToggleClicked(View view) {//팝업기능함수
        on = ((ToggleButton) view).isChecked();//  on|off 확인하기 위한 변수
        popupshow(on);
    }

    @Override
    protected void onStop() {//related toggle save- 껐다 켜도 popup데이터 유지
        super.onStop();

        SharedPreferences tog = getSharedPreferences(toggle, 0);//related toggle save
        SharedPreferences.Editor editor_toggle = tog.edit();//related toggle save
        editor_toggle.putBoolean("toggle", toggleButton.isChecked());//related toggle save
        editor_toggle.commit();//related toggle save
    }

    public void popupshow(boolean on)
    {

//        Bitmap mLargeIconForNoti = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
//        //오른쪽에 큰아이콘
//        PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this,
//                0, new Intent(getApplicationContext(), MainActivity.class),
//                PendingIntent.FLAG_UPDATE_CURRENT);
//        //팝업누를때 어플실행하기위한 Intent
//
//        mbuilder = new NotificationCompat.Builder(MainActivity.this)//기본적 notification method 구현
//                .setSmallIcon(R.mipmap.ic_launcher)//아이콘
//                .setContentTitle("제목")//제목
//                .setContentText("내용")//내용
//                .setDefaults(Notification.DEFAULT_ALL)
//                //Notification.DEFAULT_SOUND : 소리 발생
//                //Notification.DEFAULT_VIBRATE :진동
//                //Notification.DEFAULT_LIGHTS : 불빛
//                //Notification.DEFAULT_ALL : 위의 세가지
//                .setOngoing(on)// on 일때 지워지지 않게 하기
//                .setLargeIcon(mLargeIconForNoti)
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT)//우선순위
//                .setAutoCancel(false)//true면 팝업누르면 사라짐 false면 눌러도 안사라짐
//                .setContentIntent(pendingIntent);//팝업누르면 main으로 돌아옴
//
//        if(on){//on버튼 일때만 팝업기능 실행
//            NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//            mNotificationManager.notify(0,mbuilder.build());
//        }
//        else {//off버튼 누르면 사라짐
//            NotificationManagerCompat.from(this).cancel(0);
//        }


        //알림(Notification)을 관리하는 관리자 객체를 운영체제(Context)로부터 소환하기
        NotificationManager notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if(on){
            //추가된 것
            PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this,
                    0, new Intent(getApplicationContext(), MainActivity.class),
                    PendingIntent.FLAG_UPDATE_CURRENT);
//        //팝업누를때 어플실행하기위한 Intent

            //Notification 객체를 생성해주는 건축가객체 생성(AlertDialog 와 비슷)
            NotificationCompat.Builder builder= null;

            //Oreo 버전(API26 버전)이상에서는 알림시에 NotificationChannel 이라는 개념이 필수 구성요소가 됨.
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

                String channelID="channel_01"; //알림채널 식별자
                String channelName="MyChannel01"; //알림채널의 이름(별명)

                //알림채널 객체 만들기
                NotificationChannel channel= new NotificationChannel(channelID,channelName,NotificationManager.IMPORTANCE_DEFAULT);

                //알림매니저에게 채널 객체의 생성을 요청
                notificationManager.createNotificationChannel(channel);

                //알림건축가 객체 생성
                builder=new NotificationCompat.Builder(this, channelID);


            }else{
                //알림 건축가 객체 생성
                builder= new NotificationCompat.Builder(this, null);
            }

            //건축가에게 원하는 알림의 설정작업
            builder.setSmallIcon(android.R.drawable.ic_menu_view);

            //상태바를 드래그하여 아래로 내리면 보이는
            //알림창(확장 상태바)의 설정
            builder.setContentTitle("Title");//알림창 제목
            builder.setContentText("Messages....");//알림창 내용

            //추가된것
            builder.setOngoing(on); //on일때 지워지지 않게 하기
            builder.setAutoCancel(false);//true면 팝업누르면 사라짐 false면 눌러도 안사라짐
            builder.setContentIntent(pendingIntent);//팝업누르면 main으로 돌아옴

            //알림창의 큰 이미지
//          Bitmap bm= BitmapFactory.decodeResource(getResources(),R.drawable.gametitle_09);
//          builder.setLargeIcon(bm);//매개변수가 Bitmap을 줘야한다.

            //건축가에게 알림 객체 생성하도록
            Notification notification=builder.build();

            //알림매니저에게 알림(Notify) 요청
            notificationManager.notify(1, notification);

            //알림 요청시에 사용한 번호를 알림제거 할 수 있음.
            //notificationManager.cancel(1);
        }
        else{
            notificationManager.cancelAll();
        }
    }

}


//푸쉬알림 지우기에도 안없어지게 만들기(해결)
// 팝업 on|off기능 활성화(해결)
// 어플 다시켜도 팝업 on,off에 관한 데이터 보존하기(해결)
// 설정한 시간에 새로운 문장 알람 및 팝업기능(해결)
// 팝업키고끌때 하루당 한문장 안바뀌고 그대로 팝업에 노출되게 유지하기
// 토글버튼 위치 조정하기
// 문장데이터 받아서 출력하기
// 아이콘 디자인 하기
//어플 다 만들고 디자인 관해서 알아보기


//다른액티비티 클래스 함수사용하는 거 실패함.(해결)
//popupmethod만들고 거기에 팝업기능 넣고 다른액티비티에서 필요할때마다 불러올라했는데 실패함.(해결)
// ->MainActivity에 함수만들어서 다른액티비티에 썼더니 되서 이런식으로 함.
//다른클래스의 함수 사용하는 방법 더알아봐야함. + 다른클래스의 변수 사용하는 방법도(변수는 해결)->(둘다해결)
//related toggle save관련 코드 고쳐야함 어플이 아예 안켜짐. 토글데이터 유지 검색해야함.(해결)

//2020.04.18
//데이터베이스에 있는 set_sentence의 값이 다른 액티비티로 넘어올때 비어서 넘어옴.
// 데이터베이스 액티비티안에서는 set-sentence에 값이 잘들어간듯.(랜덤으로 잘 들어가는지는 확인해봐야함)
//SubActivity를 없애고 데이터베이스액티비티와 로딩화면을 합침.
//데이터 베이스 액티비티에서 2초 기다리고 끝내지말고 2초 동안 pause한 다음 intent와 같이 문장을 Main으로 보낸다.
//로딩될때마다 다른 문장을 가져올수 있다.
// ㄴㅡ>데이터베이스 액티비티에 시간과 관련된 설정을 해서 그 시간동안에는 똑같은 문장만 가져오게 하면 될듯.
// (메인은 항상 켜있으므로 값이 안없어진다.다른액티비티에서는 메인을 참고.)
// 앱이 종료되면 메인이 피니쉬되는데 이때는 share~~~로 값을 유지해서 다시로드해주면 될듯
