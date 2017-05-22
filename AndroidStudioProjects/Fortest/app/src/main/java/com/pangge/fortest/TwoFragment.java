package com.pangge.fortest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by iuuu on 16/10/14.
 */
public class TwoFragment extends Fragment implements View.OnClickListener {
    private View twoView;
    private Button getWeather;
    private EditText inputCity;
    private TextView weather1;
    private TextView weather2;
    private TextView weather3;
    private TextView city;
    private TextView temp;
    private TextView warn;
    private TextView yesterday;
    private String cc;
    private String tempString;
    private String warnString;
    private ArrayList<String> foreString=new ArrayList<String>();
    private String yesString;
    private StringBuilder sBuilder;
    private MainActivity mainActivity;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        twoView = inflater.inflate(R.layout.activity_weather,container,false);
        getWeather = (Button)twoView.findViewById(R.id.getWeather);
        inputCity = (EditText)twoView.findViewById(R.id.cityInput);
        weather1 = (TextView)twoView.findViewById(R.id.weather1);
        weather2 = (TextView)twoView.findViewById(R.id.weather2);
        weather3 = (TextView)twoView.findViewById(R.id.weather3);
        city = (TextView)twoView.findViewById(R.id.cityText);
        temp = (TextView)twoView.findViewById(R.id.tempText);
        warn = (TextView)twoView.findViewById(R.id.warnText);
        yesterday = (TextView)twoView.findViewById(R.id.yesterdayText);
        mainActivity = (MainActivity)getActivity();

        getWeather.setOnClickListener(this);

        return twoView;
    }




    public void onClick(View view) {
      //  configDialog(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    // String city = "上海";
                    String city = inputCity.getText().toString();
                    String citiName = URLEncoder.encode(city,"utf-8");
                    URL url = new URL("http://wthrcdn.etouch.cn/weather_mini?city="+citiName);
                    HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                    //connection.addRequestProperty();
                    InputStream is = connection.getInputStream();
                    int x = 0;
                    byte[] bys = new byte[1024];
                    String weatherJson ="";
                    while ((x=is.read(bys)) != -1){
                        weatherJson+=new String(bys,0,x);
                    }
                   // configDialog(false);
                    parseWeather(weatherJson);
                }catch (Exception e){
                    e.printStackTrace();
                  //  configDialog(false);
                }

            }
        }).start();

    }

 //   private AlertDialog dialog;


    protected void parseWeather(final String weatherJson) throws Exception {
        JSONObject object = new JSONObject(weatherJson);
        String desc = object.getString("desc");

        if ("OK".equals(desc)) {
            JSONObject datas = object.getJSONObject("data");
            JSONArray fores = datas.getJSONArray("forecast");

            Iterator<String> keys = datas.keys();

            while (keys.hasNext()) {
                String next = keys.next();
                switch (next) {
                    case "wendu":
                        tempString = datas.getString("wendu");
                        break;
                    case "ganmao":
                        warnString = datas.getString("ganmao");
                        break;
                    case "forecast":
                        for (int i = 0; i < 3; i++) {
                            JSONObject cast = fores.getJSONObject(i);
                            sBuilder = new StringBuilder();
                            Iterator<String> keyForecast = cast.keys();
                            while (keyForecast.hasNext()) {
                                next = keyForecast.next();
                                sBuilder.append(cast.getString(next) + "\n");
                            }
                            foreString.add(sBuilder.toString());
                        }

                        break;
                    case "yesterday":
                        JSONObject yesObject = datas.getJSONObject("yesterday");
                        sBuilder = new StringBuilder();
                        Iterator<String> keyYes = yesObject.keys();
                        while (keyYes.hasNext()) {
                            next = keyYes.next();
                            sBuilder.append(yesObject.getString(next) + "\n");
                        }
                        yesString = sBuilder.toString();
                        break;
                    case "aqi":
                        break;
                    case "city":
                        cc = datas.getString("city");
                        break;
                    default:
                        break;

                }

            }


            mainActivity.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    city.setText(cc);
                    temp.setText(tempString + "°c");
                    warn.setText(warnString);
                    // weather1.setText(cc);
                    yesterday.setText(yesString);

                    weather1.setText(foreString.get(0));
                    weather2.setText(foreString.get(1));
                    weather3.setText(foreString.get(2));

                }
            });

        } else {

        }


    }
/*
    private void configDialog(boolean showInfo){
        if (showInfo){
            if (dialog==null){
                AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
                builder.setTitle("正在连接网络");
                builder.setMessage("等会儿...");
                builder.setCancelable(false);
                dialog = builder.create();
            }
            dialog.show();
        }else {
            dialog.dismiss();
        }
    }
*/

}
