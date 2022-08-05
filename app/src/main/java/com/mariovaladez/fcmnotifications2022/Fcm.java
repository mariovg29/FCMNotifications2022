package com.mariovaladez.fcmnotifications2022;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

public class Fcm extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        Log.e("token", "mi token es: " + token);
        saveToken(token);
    }

    private void saveToken(String token) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("token");
        ref.child("idDevice").setValue(token);

    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        String from = message.getFrom();
        Log.e("TAG", "Mensaje recibido de: " + from);

       /* if(message.getNotification() != null){
            Log.e("TAG","El titulo es: : "+ message.getNotification().getTitle());
            Log.e("TAG","El cuerpo es :  "+ message.getNotification().getBody());
        }





        */

        if (message.getData().size() > 0) {
           /* Log.e("TAG","El titulo es:  "+ message.getData().get("titulo"));
            Log.e("TAG","El detalle es:  "+ message.getData().get("detalle"));
            Log.e("TAG","El color es: : "+ message.getData().get("color"));

            */
            String titulo = message.getData().get("titulo");
            String detalle = message.getData().get("detalle");

            mayorQueOreo(titulo, detalle);

        }


    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    private void mayorQueOreo(String titulo, String detalle) {
        String id = "mensaje";

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, id);
        NotificationChannel channel = new NotificationChannel(id, "nuevo", NotificationManager.IMPORTANCE_HIGH);
        channel.setShowBadge(true);
        manager.createNotificationChannel(channel);

        builder.setAutoCancel(true)
                .setWhen(System.currentTimeMillis())
                .setContentTitle(titulo)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText(detalle)
                .setContentIntent(clickNoti())
                .setContentInfo("nuevo");

        Random random = new Random();
        int idNotify = random.nextInt(8000);

        manager.notify(idNotify, builder.build());

    }

    public PendingIntent clickNoti() {
        Intent nf = new Intent(getApplicationContext(), MainActivity.class);
        nf.putExtra("color", "rojo");
        nf.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        return PendingIntent.getActivity(this, 0, nf, 0);
    }

}
