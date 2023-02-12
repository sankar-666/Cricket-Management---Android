package com.example.cricketmangemenet;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class ViewTeams extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {

    ListView lv1;
    String [] photo;
    public static String path,team_id;
    String[] name,email,about,city,tid,value;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full sc
        setContentView(R.layout.activity_view_teams);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        lv1=(ListView)findViewById(R.id.ltvideo);
        lv1.setOnItemClickListener(this);

        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) ViewTeams.this;
        String q = "/viewteams";
        q=q.replace(" ","%20");
        JR.execute(q);
    }

    @Override
    public void response(JSONObject jo) {
        try {

            String method=jo.getString("method");
            if(method.equalsIgnoreCase("viewteams")){
                String status=jo.getString("status");
                Log.d("pearl",status);
                if(status.equalsIgnoreCase("success")){

                    JSONArray ja1=(JSONArray)jo.getJSONArray("data");


                    name=new String[ja1.length()];
                    email=new String[ja1.length()];
                    about=new String[ja1.length()];
                    city=new String[ja1.length()];
                    tid=new String[ja1.length()];
                    value=new String[ja1.length()];




                    for(int i = 0;i<ja1.length();i++)
                    {



                        name[i]=ja1.getJSONObject(i).getString("team_name");
                        email[i]=ja1.getJSONObject(i).getString("email");
                        about[i]=ja1.getJSONObject(i).getString("team_about");
                        city[i]=ja1.getJSONObject(i).getString("city");
                        tid[i]=ja1.getJSONObject(i).getString("team_id");

                        value[i]="Team: "+name[i]+"\nAbout: "+about[i]+"\nCity: "+city[i]+"\nEmail: "+email[i];




                    }
//				Custimage clist=new Custimage(this,photo);
//				 lv1.setAdapter(clist);

                    ArrayAdapter<String> ar= new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,value);
                    lv1.setAdapter(ar);


                }

                else {
                    Toast.makeText(getApplicationContext(), "no data", Toast.LENGTH_LONG).show();

                }
            }

        }catch (Exception e)
        {
            // TODO: handle exception

            Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        final CharSequence[] items = {"View Players", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(ViewTeams.this);
        // builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("View Players")) {

                    startActivity(new Intent(getApplicationContext(), ChatHere.class));

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }

            }

        });
        builder.show();
    }
}