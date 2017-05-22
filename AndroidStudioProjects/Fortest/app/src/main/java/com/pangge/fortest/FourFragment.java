package com.pangge.fortest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by iuuu on 16/10/14.
 */
public class FourFragment extends Fragment implements View.OnClickListener{
    private Bitmap bmVerifation;
    private EditText textNum;
    private EditText textCar;
    private EditText textVerifation;
    private ImageView imageView;
    private Button button;
    private String typeCar = "02";

    private String session;
    private String info;

    String VERIFATIONURL = "http://zzcgs.com.cn/GetYzmCode.aspx";
    String SEARCHURL = "http://zzcgs.com.cn/jdc_cx.aspx?ywdm=A_1";


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.arg1) {
                case 0:
                    Toast.makeText(getActivity(), "验证码填写有误", Toast.LENGTH_SHORT).show();
                    break;

                case 5:
                    Toast.makeText(getActivity(), "没有找到相关车辆信息", Toast.LENGTH_SHORT).show();
                    break;
                case 6:
                    responseText.setText(info);
                    break;

                case SHOW_RESPONSE:
                    //html
                    String response = (String)msg.obj;
                    //  responseText.setText(response);
                    break;
                case 10:
                    imageView.setImageBitmap(bmVerifation);
                    break;
            }
        }
    };

    public static final int SHOW_RESPONSE = 7;
    private TextView responseText;
    private View fourView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        fourView = inflater.inflate(R.layout.activity_car,container,false);

        RadioGroup groupType = (RadioGroup)fourView.findViewById(R.id.rdGroup_type);
        groupType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int radioButtonId = group.getCheckedRadioButtonId();
                RadioButton rb = (RadioButton)fourView.findViewById(radioButtonId);
                if(rb.getId() == R.id.small){
                    typeCar = "02";
                }else {
                    typeCar = "01";
                }
            }
        });
        textNum = (EditText)fourView.findViewById(R.id.num_sign);
        textCar = (EditText)fourView.findViewById(R.id.num_car);
        textVerifation = (EditText)fourView.findViewById(R.id.identify_code);
        button = (Button)fourView.findViewById(R.id.search_btn);
        imageView = (ImageView)fourView.findViewById(R.id.imageView);
        responseText = (TextView)fourView.findViewById(R.id.display);
        responseText.setMovementMethod(ScrollingMovementMethod.getInstance());
        button.setOnClickListener(this);
        imageView.setOnClickListener(this);
        return fourView;
    }


    @Override
    public void onResume() {
        super.onResume();
        getVerifation();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.search_btn:
                responseText.setText("");
             //   SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);

                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("__VIEWSTATE","/wEPDwUKLTc0OTgwNzU5M2RkohwiYYdkshv9e4n0ilFFvboGO+M=");
                hashMap.put("ddlHpzl",typeCar);
                hashMap.put("txtHphm",textNum.getText().toString());
                hashMap.put("txtClsbdh",textCar.getText().toString());
                hashMap.put("txtYzm",textVerifation.getText().toString());
                hashMap.put("Button1"," 查　询 ");
                if(TextUtils.isEmpty(textNum.getText())|| TextUtils.isEmpty(textCar.getText())||
                        TextUtils.isEmpty(textVerifation.getText())){
                    if(TextUtils.isEmpty(textNum.getText())){

                        textNum.setHint("车牌号不能为空");
                    }
                    if(TextUtils.isEmpty(textCar.getText())){

                        textCar.setHint("车辆识别代码不能为空");
                    }
                    if(TextUtils.isEmpty(textVerifation.getText())){
                        textVerifation.setHint("验证码不能为空");
                    }
                }else {

                    toSearch(SEARCHURL, hashMap);
                }



                break;
            case R.id.imageView:
                getVerifation();
                break;
        }
    }

    private void getVerifation(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                Log.i("information","verifation begin");
                try{
                    URL url = new URL(VERIFATIONURL);
                    Log.i("like joke","verifation begin");
                    connection = (HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("POST");
                    String cookie = connection.getHeaderField("set-cookie");
                    Log.i("Cookie",cookie);
                    session = cookie;
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in = connection.getInputStream();
                    bmVerifation = BitmapFactory.decodeStream(in);

                }catch (Exception e){
                    e.printStackTrace();

                }finally {
                    if(connection != null){
                        connection.disconnect();
                    }
                }
                Message msg = new Message();
                msg.arg1 = 10;
                handler.sendMessage(msg);


            }
        }).start();
    }

    private void toSearch(final String requestURL,
                          final HashMap<String, String> postDataParams){
        new Thread(new Runnable() {
            @Override
            public void run() {

                HttpURLConnection connection = null;
                String response = "";


                try{
                    URL url = new URL(requestURL);

                    connection = (HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    connection.setRequestProperty("Cookie",session);

                    OutputStream os = connection.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(
                            new OutputStreamWriter(os, "UTF-8"));

                    writer.write(getPostDataString(postDataParams));
                    writer.flush();
                    writer.close();
                    os.close();

                    int responseCode = connection.getResponseCode();

                    if(responseCode == HttpURLConnection.HTTP_OK){
                        String line;
                        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        while ((line = br.readLine()) != null){
                            response += line;
                        }
                        Log.i("number","first");
                    }else {
                        response = "";
                        Log.i("toSearch4","infomation");
                    }

                    Message message = new Message();
                    message.arg1 = SHOW_RESPONSE;
                    message.obj = response.toString();
                    loginSuccessful(response.toString());
                    handler.sendMessage(message);
                    getVerifation();

                }catch (Exception e){
                    e.printStackTrace();

                }finally {
                    if(connection != null){
                        connection.disconnect();
                    }
                }



            }
        }).start();
    }

    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();

        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()){

            if(first) {
                first = false;

            }
            else

            result.append("&");
            Log.i("toSearch9!","infomation");
            result.append(URLEncoder.encode(entry.getKey(),"UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(),"UTF-8"));
        }
        Log.i("toSearch8","infomation");
        return result.toString();
    }

    private void loginSuccessful(String result){
        Document document = Jsoup.parse(result);
        Elements elements = document.select("input");
        Elements output = document.select("script");
        Elements success = document.select("span");

        Message msg = new Message();

        for(Element link : success){

            if(link.attr("id").contains("lblInfo")){
                msg.arg1 = 6;
                info = link.text();
                Log.i("info",info);
                handler.sendMessage(msg);
                return;
            }

        }

        for(Element link : output){
            if(link.data().contains("提示")){

                msg.arg1 = 5;
                handler.sendMessage(msg);

            }
            //return;
        }

        for(Element link : elements){

            if(link.attr("value").contains("验证码填写有误1")){
                msg.arg1 = 0;
                handler.sendMessage(msg);
            }else if(link.attr("value").contains("验证码填写有误2")) {
                msg.arg1 = 0;
                handler.sendMessage(msg);
            }
        }
    }

}
