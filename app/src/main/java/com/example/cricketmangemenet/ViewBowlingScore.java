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

public class ViewBowlingScore extends AppCompatActivity implements JsonResponse {


    ListView lv1;

    public static String path,team_id;
    String[] name,overs,wicket,runs,place,pic,lid,value;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full sc
        setContentView(R.layout.activity_view_bowling_score);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        lv1=(ListView)findViewById(R.id.ltvideo);
      

        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) ViewBowlingScore.this;
        String q = "/viewbowlingscore?mid="+ViewMatch.match_id;
        q=q.replace(" ","%20");
        JR.execute(q);
    }

    @Override
    public void response(JSONObject jo) {
        try {

            String method=jo.getString("method");
            if(method.equalsIgnoreCase("viewbowlingscore")){
                String status=jo.getString("status");
                Log.d("pearl",status);
                if(status.equalsIgnoreCase("success")){

                    JSONArray ja1=(JSONArray)jo.getJSONArray("data");


                    name=new String[ja1.length()];
                    overs=new String[ja1.length()];
                    wicket=new String[ja1.length()];
                    runs=new String[ja1.length()];
                    value=new String[ja1.length()];

                    for(int i = 0;i<ja1.length();i++)
                    {

                        name[i]=ja1.getJSONObject(i).getString("playername");
                        overs[i]=ja1.getJSONObject(i).getString("overs_bowled");
                        wicket[i]=ja1.getJSONObject(i).getString("wickets_taken");
                        runs[i]=ja1.getJSONObject(i).getString("runs_conceded");

                        value[i]="Player: "+name[i]+"\nOvers: "+overs[i]+"\nWicket taken: "+wicket[i]+"\nRuns Conceded: "+runs[i];




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