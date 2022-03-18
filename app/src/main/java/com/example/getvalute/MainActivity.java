package com.example.getvalute;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private TextView Text2;
    private RequestQueue mQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Text2 = findViewById(R.id.text2);
        mQueue = Volley.newRequestQueue(this);
        jsonParse();
    }


    private void jsonParse(){
        String url = "https://www.cbr-xml-daily.ru/daily_json.js";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override // Ответ в случае успешного обращения
                    public void onResponse(JSONObject response) {
                        try {

                            JSONObject obj = response.getJSONObject("Valute");
                            JSONArray arr = obj.toJSONArray(obj.names());
                            for(int i = 0; i < arr.length(); i++ ){
                                JSONObject Valute = arr.getJSONObject(i);

                                String ID = Valute.getString("ID");
                                String NumCode = Valute.getString("NumCode") ;
                                String CharCode = Valute.getString("CharCode") ;
                                String Name = Valute.getString("Name") ;
                                int Value = Valute.getInt("Value") ;
                                int Previous = Valute.getInt("Previous") ;

                                Text2.append("ID: "+ID+"\n" );
                                Text2.append("Код валюты(цифр.): "+ NumCode+"\n" );
                                Text2.append("Код валюты(букв.): "+CharCode+"\n" );
                                Text2.append("Наименование: " +Name+"\n");
                                Text2.append("Текущее значение: "+ String.valueOf(Value) + "\n");
                                Text2.append("Предыдущее значение: "+ String.valueOf(Previous)+ "\n\n");

                                Text2.append(" "+"\n\n");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override // Ответ в случае возникновения ошибки
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

            }
        });

        mQueue.add(request);

    }
    
    
    
}