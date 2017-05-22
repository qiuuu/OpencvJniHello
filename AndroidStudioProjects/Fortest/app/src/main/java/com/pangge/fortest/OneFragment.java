package com.pangge.fortest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iuuu on 16/10/14.
 */
public class OneFragment extends Fragment {

    private List<Detail> detailList = new ArrayList<Detail>();
    private DetailAdapter adapter;
    private Button button;
    //private RadioButton rb;
    private EditText textSalary;
    private EditText textFund;
    private int inputSalary;
    private int inputFund;
    private int inputPoint = 3500;
    private String outputSalary = "税后工资";
    private TextView tv;

    private View oneView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        oneView = inflater.inflate(R.layout.activity_money,container,false);
        button = (Button) oneView.findViewById(R.id.compute_btn);
        textSalary = (EditText)oneView.findViewById(R.id.text_salary);
        textFund = (EditText)oneView.findViewById(R.id.text_fund);
        tv = (TextView)oneView.findViewById(R.id.input_message);
        Log.i("tv",tv.getText().toString());


        RadioGroup groupType = (RadioGroup)oneView.findViewById(R.id.rdGroup_type);
        groupType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int radioButtonId = radioGroup.getCheckedRadioButtonId();
                RadioButton rb = (RadioButton)oneView.findViewById(radioButtonId);
                if(rb.getId() == R.id.after){

                    tv.setText("税后工资");
                    outputSalary = "税前工资";

                }else {

                    tv.setText("税前工资");
                    outputSalary = "税后工资";
                }
            }
        });


        RadioGroup groupPoint = (RadioGroup)oneView.findViewById(R.id.rdGroup_point);
        groupPoint.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int radioButtonId = radioGroup.getCheckedRadioButtonId();
                RadioButton rb = (RadioButton)oneView.findViewById(radioButtonId);

                if(rb.getId() == R.id.chinese){
                    inputPoint = 3500;
                }else {
                    inputPoint = 4800;
                }
            }
        });


        adapter = new DetailAdapter(getActivity(),
                R.layout.list_item, detailList);
        ListView listView = (ListView)oneView.findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.compute_btn:
                        if(TextUtils.isEmpty(textSalary.getText())|| TextUtils.isEmpty(textFund.getText())){

                            if(TextUtils.isEmpty(textSalary.getText())){
                                textSalary.setHint("请输入薪资");
                            }
                            if(TextUtils.isEmpty(textFund.getText())){
                                textFund.setHint("请输入五险一金");
                            }

                        }else {
                            inputSalary = Integer.parseInt(textSalary.getText().toString());
                            inputFund = Integer.parseInt(textFund.getText().toString());
                            adapter.clear();
                            addDetails();
                            adapter.notifyDataSetChanged();
                        }

                        break;
                    default:
                        break;
                }

            }
        });
        return oneView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void addDetails(){
        double money = 0;
        double moneyoftax = 0;
        double moneyofuntax = 0;
        double tax = 0;
        double rateoftax;
        int num;
       // Log.i("tv 's e cond",tv.getText().toString());
        if(TextUtils.equals(tv.getText(),"税前工资")){
           // Log.i("infor","before");
            moneyoftax = inputSalary-inputFund-inputPoint;
            if(moneyoftax<=1500 && moneyoftax>0){
                rateoftax = 0.03;
                num = 0;
            }else if(moneyoftax<=4500 && moneyoftax>1500){
                rateoftax = 0.1;
                num = 105;
            }else if(moneyoftax<=9000 && moneyoftax>4500){
                rateoftax = 0.2;
                num = 555;
            }else if(moneyoftax<=35000 && moneyoftax>9000){
                rateoftax = 0.25;
                num = 1005;
            }else if(moneyoftax<=55000 && moneyoftax>35000){
                rateoftax = 0.3;
                num = 2755;
            }else if(moneyoftax<=80000 && moneyoftax>55000){
                rateoftax = 0.35;
                num = 5505;
            }else {
                rateoftax= 0.45;
                num = 13505;
            }
            tax = moneyoftax * rateoftax - num;
            money = inputSalary - inputFund - tax;
        }else {
            moneyofuntax = inputSalary - inputPoint;
            if(moneyofuntax<=1455 && moneyofuntax>0){
                rateoftax = 0.03;
                num = 0;
            }else if(moneyofuntax<=4155 && moneyofuntax>1455){
                rateoftax = 0.1;
                num = 105;
            }else if(moneyofuntax<=7755 && moneyofuntax>4155){
                rateoftax = 0.2;
                num = 555;
            }else if(moneyofuntax<=27255 && moneyofuntax>7755){
                rateoftax = 0.25;
                num = 1005;
            }else if(moneyofuntax<=41255 && moneyofuntax>27255){
                rateoftax = 0.3;
                num = 2755;
            }else if(moneyofuntax<=57505 && moneyofuntax>41255){
                rateoftax = 0.35;
                num = 5505;
            }else {
                rateoftax= 0.45;
                num = 13505;
            }
            moneyoftax = (moneyofuntax-num)/(1-rateoftax);
            // moneyoftax = ((int)(moneyoftax*100))/100;
            money = moneyoftax + inputFund + inputPoint;
            tax = money - inputSalary - inputFund;
        }

        Detail title = new Detail("个人所得税计算结果","");
        detailList.add(title);

        moneyoftax = ((int)(moneyoftax*100))/100;
        money = ((int)(money*100))/100;
        tax = ((int)(tax*100))/100;


        Detail salary = new Detail(outputSalary, Double.toString(money));
        detailList.add(salary);
        Detail taxs_data = new Detail("应纳税所得额", Double.toString(moneyoftax));
        detailList.add(taxs_data);
        Detail tax_data = new Detail("应纳税额", Double.toString(tax));
        detailList.add(tax_data);
    }

}
