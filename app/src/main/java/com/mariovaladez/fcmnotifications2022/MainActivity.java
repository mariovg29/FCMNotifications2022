package com.mariovaladez.fcmnotifications2022;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.mariovaladez.fcmnotifications2022.databinding.ActivityMainBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    String key = "AAAAsTsO5mc:APA91bEE2HyUK3mDCVrD8LriUP2FQZSb_gJXSESAXt2Jh5LVNglJdhWjTM0oYY4lS4aV6ZuVb1Htc8mkBye_vZEkeD6t5UmlgqLhOyZo2s3r7GmI4vxM8teY5YO0pTdvyj7IRmUiKq9i";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.notificaciNGeneral.setOnClickListener(view1 -> llamarGeneral());
        binding.notificarEspecifico.setOnClickListener(view1 -> llamarEspecifico());



    }
    public void suscribirATopico() {
        FirebaseMessaging.getInstance().subscribeToTopic("enviaratodos")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(MainActivity.this, "suscrito a Notificación General", Toast.LENGTH_SHORT).show();

                    }
                });
    }

    private void llamarEspecifico() {
        RequestQueue myRequest = Volley.newRequestQueue(getApplicationContext());
        JSONObject jsonObject = new JSONObject();

        try {
            String token = "f7S8vsvhRsmJgFkO1WOjpd:APA91bG-destAjhvhIxHJYy6w3TodmG6YqpnGj7YPEdYtCXP4FLWowIog_yulzRGe7FmO30PQhU_H73F73dp-q6B5muMpn6jKYCqyP_6ckqp0bJmIDIRhbUyLnWFKasmGQr6B40h4AUo";
            jsonObject.put("to", token);
            JSONObject notificacion = new JSONObject();
            notificacion.put("titulo", "soy un titulo");
            notificacion.put("detalle", "Soy un detalle");
            jsonObject.put("data", notificacion);

            String URL = "https://fcm.googleapis.com/fcm/send";
            JsonObjectRequest request = new JsonObjectRequest
                    (Request.Method.POST,URL,jsonObject,null,null){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                   Map<String,String> header = new HashMap<>();
                   header.put("Content-type","application/json");
                   header.put("Authorization", "key="+key);
                   return header;

                }
            };
            myRequest.add(request);



        }catch (JSONException e){
            e.printStackTrace();
        }

    }

    private void llamarGeneral() {
        suscribirATopico();
        RequestQueue myRequest = Volley.newRequestQueue(getApplicationContext());
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("to", "/topics/enviaratodos");
            JSONObject notificacion = new JSONObject();
            notificacion.put("titulo", "soy un titulo");
            notificacion.put("detalle", "Soy un detalle");
            jsonObject.put("data", notificacion);

            String URL = "https://fcm.googleapis.com/fcm/send";
            JsonObjectRequest request = new JsonObjectRequest
                    (Request.Method.POST,URL,jsonObject,null,null){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String> header = new HashMap<>();
                    header.put("Content-type","application/json");
                    header.put("Authorization", "key="+key);
                    return header;

                }
            };
            myRequest.add(request);



        }catch (JSONException e){
            e.printStackTrace();
        }


    }
}