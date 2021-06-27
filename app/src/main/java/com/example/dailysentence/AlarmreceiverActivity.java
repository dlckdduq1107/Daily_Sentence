package com.example.dailysentence;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

//정해진 시간에 팝업기능하게 해주는 액티비티
public class AlarmreceiverActivity extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {



        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent notificationIntent = new Intent(context, TimeActivity.class);

        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingI = PendingIntent.getActivity(context, 0, notificationIntent, 0);
        //PendingIntent pendingIntent = PendingIntent.getActivity(context,
         //       0, notificationIntent,
          //      PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "default");

        //OREO API 26 이상에서는 채널 필요
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            builder.setSmallIcon(R.drawable.ic_launcher_foreground); //mipmap 사용시 Oreo 이상에서 시스템 UI 에러남


            String channelName ="매일 알람 채널";
            String description = "매일 정해진 시간에 알람합니다.";
            int importance = NotificationManager.IMPORTANCE_HIGH; //소리와 알림메시지를 같이 보여줌

            NotificationChannel channel = new NotificationChannel("default", channelName, importance);
            channel.setDescription(description);

            if (notificationManager != null) {
                // 노티피케이션 채널을 시스템에 등록
                notificationManager.createNotificationChannel(channel);
            }
        }
        else builder.setSmallIcon(R.mipmap.ic_launcher); // Oreo 이하에서 mipmap 사용하지 않으면 Couldn't create icon: StatusBarIcon 에러남

        if (((MainActivity)MainActivity.context_main).on)
        {//토글키가 온이면 팝업과 화면 동시에 출력 오프이면 화면만 출력
            //팝업함수실행
            ((MainActivity)MainActivity.context_main).popupshow(((MainActivity)MainActivity.context_main).on);

            //화면출력함수실행

            //함수작성해야함%$%$%$%$%중요%$%$%%%$$

        }
        else
        {//토글키가 오프이면 화면만 출력

            ////함수작성해야하하하하하함$%$%$%$%중요%$%$%%%$$
        }

        if (notificationManager != null) {//설정한 시간에 팝업기능 수행하는 코드

            // 노티피케이션 동작시킴//설정한 시간에 동작시켜주는 코드
            //notificationManager.notify(1234, ((MainActivity)MainActivity.context_main).mbuilder.build());
            //위에거 쓰면 설정한 시간에 알림이 두개뜬다.
            ((MainActivity)MainActivity.context_main).popupshow(((MainActivity)MainActivity.context_main).on);
            Calendar nextNotifyTime = Calendar.getInstance();

            // 내일 같은 시간으로 알람시간 결정
            nextNotifyTime.add(Calendar.DATE, 1);

            //  Preference에 설정한 값 저장
            SharedPreferences.Editor editor = context.getSharedPreferences("daily alarm", MODE_PRIVATE).edit();
            editor.putLong("nextNotifyTime", nextNotifyTime.getTimeInMillis());
            editor.apply();

            //Date currentDateTime = nextNotifyTime.getTime();
            //String date_text = new SimpleDateFormat("yyyy년 MM월 dd일 EE요일 a hh시 mm분 ", Locale.getDefault()).format(currentDateTime);
            //Toast.makeText(context.getApplicationContext(),"다음 알람은 " + date_text + "으로 알람이 설정되었습니다!", Toast.LENGTH_SHORT).show();//정해진 시간에 알람올때 메세지 보내는 기능.
        }


    }
}
