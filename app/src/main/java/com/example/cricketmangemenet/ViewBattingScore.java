package com.example.cricketmangemenet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class ViewBattingScore extends AppCompatActivity implements JsonResponse {

    ListView lv1;

    public static String path,team_id;
    String[] name,score,balls,single,value,doub,triple,boundary,six,wicket;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full sc
        setContentView(R.layout.activity_view_batting_score);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        lv1=(ListView)findViewById(R.id.ltvideo);


        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) ViewBattingScore.this;
        String q = "/viewbattingscore?mid="+ViewMatch.match_id;
        q=q.replace(" ","%20");
        JR.execute(q);
    }

    @Override
    public void response(JSONObject jo) {
        try {

            String method=jo.getString("method");
            if(method.equalsIgnoreCase("viewbattingscore")){
                String status=jo.getString("status");
                Log.d("pearl",status);
                if(status.equalsIgnoreCase("success")){

                    JSONArray ja1=(JSONArray)jo.getJSONArray("data");


                    name=new String[ja1.length()];
                    score=new String[ja1.length()];

                    balls=new String[ja1.length()];
                    single=new String[ja1.length()];
                    value=new String[ja1.length()];
                    doub=new String[ja1.length()];
                    triple=new String[ja1.length()];
                    boundary=new String[ja1.length()];
                    six=new String[ja1.length()];
                    wicket=new String[ja1.length()];

                    for(int i = 0;i<ja1.length();i++)
                    {

                        name[i]=ja1.getJSONObject(i).getString("playername");
                        score[i]=ja1.getJSONObject(i).getString("score");

                        balls[i]=ja1.getJSONObject(i).getString("balls_faced");
                        single[i]=ja1.getJSONObject(i).getString("single");
                        doub[i]=ja1.getJSONObject(i).getString("double");
                        triple[i]=ja1.getJSONObject(i).getString("triple");
                        boundary[i]=ja1.getJSONObject(i).getString("boundaries");
                        six[i]=ja1.getJSONObject(i).getString("sixes");
                        wicket[i]=ja1.getJSONObject(i).getString("wicket_taken_by");

                        value[i]="Player: "+name[i]+"\nScore: "+score[i]+"\nBalls: "+balls[i]+"\nSingle: "+single[i]+"\nDouble: "+doub[i]+"\nTriple: "+triple[i]+"\nBoundary: "+boundary[i]+"\nSix: "+six[i]+"\nWicket taken By: "+wicket[i];




                    }
//                    Custimage clist=new Custimage(this,name,email,phone,role,place,pic);
//                    lv1.setAdapter(clist);

                    ArrayAdapter<String> ar= new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,value);
                    lv1.setAdapter(ar);


                }

                else {
                    Toast.makeText(getApplicationContext(), "No Data found!", Toast.LENGTH_LONG).show();

                }
            }

        }catch (Exception e)
        {
            // TODO: handle exception

            Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),ViewMatch.class);
        startActivity(b);
    }
}