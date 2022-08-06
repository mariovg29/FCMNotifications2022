package com.mariovaladez.fcmnotifications2022;

import androidx.annotation.NonNull;
import androidx.annotation.StringDef;
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
import com.google.android.libraries.places.api.Places;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.mariovaladez.fcmnotifications2022.databinding.ActivityMainBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    String keyServer = BuildConfig.KEYSERVER;
    String token = BuildConfig.TOKEN;




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
                   header.put("Authorization", "key="+keyServer);
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
                    header.put("Authorization", "key="+keyServer);
                    return header;

                }
            };
            myRequest.add(request);



        }catch (JSONException e){
            e.printStackTrace();
        }


    }
}