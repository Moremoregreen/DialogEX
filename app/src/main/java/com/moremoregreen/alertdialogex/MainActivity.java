package com.moremoregreen.alertdialogex;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button normalDialog;
    private Button listDialog;
    private Button singleDialog;
    private Button multiDialog;
    private Button customDialog;
    private List<String> lunch;
    private View.OnClickListener buttonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.normal_dialog:
                    NormalDialogEvent();
                    break;
                case R.id.list_dialog:
                    listDialogEvent();
                    break;
                case R.id.single_choice_dialog:
                    singleDialogEvent();
                    break;
                case R.id.multi_choice_dialog:
                    multiDialogEvent();
                    break;
                case R.id.custom_dialog:
                    CustomDialogEvent();
                    break;
            }
        }
    };
    private int singleChoiceIndex;
    private int checkedIndex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();


    }
    private void initData() {
        lunch = new ArrayList<>();
        lunch.add("滷肉飯");
        lunch.add("牛肉麵");
        lunch.add("炒飯");
        lunch.add("鍋貼");
        lunch.add("蔥抓餅");
        lunch.add("雞肉飯");
    }

    public void initView(){
        normalDialog = (Button) findViewById(R.id.normal_dialog);
        listDialog = (Button) findViewById(R.id.list_dialog);
        singleDialog = (Button) findViewById(R.id.single_choice_dialog);
        multiDialog = (Button) findViewById(R.id.multi_choice_dialog);
        customDialog = (Button) findViewById(R.id.custom_dialog);
        setButtonEvent();
    }

    public void setButtonEvent(){
        normalDialog.setOnClickListener(buttonListener);
        listDialog.setOnClickListener(buttonListener);
        singleDialog.setOnClickListener(buttonListener);
        multiDialog.setOnClickListener(buttonListener);
        customDialog.setOnClickListener(buttonListener);
    }
    private void NormalDialogEvent(){
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("午餐時間")
                .setMessage("要吃了嗎?")
                .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "GOGO", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("等一下拉", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "可是我好餓喔", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("我還不餓", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "減肥喔????", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }
    private void CustomDialogEvent(){
        final View item = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_layout, null);
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("你的名字?")
                .setView(item)
                .setPositiveButton("確認", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText editText = item.findViewById(R.id.edit_text);
                        String name = editText.getText().toString();
                        if(TextUtils.isEmpty(name)){
                            Toast.makeText(MainActivity.this, "你的名字辣!", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(MainActivity.this, "安安你好", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .show();
    }
    private void listDialogEvent() {
        new AlertDialog.Builder(MainActivity.this)
                .setItems(lunch.toArray(new String[lunch.size()]), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = lunch.get(which);
                        Toast.makeText(MainActivity.this, "你選擇吃" + name, Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }
    private void singleDialogEvent(){
        new AlertDialog.Builder(MainActivity.this)
                .setSingleChoiceItems(lunch.toArray(new String[lunch.size()]), singleChoiceIndex, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        singleChoiceIndex = which;
                    }
                })
                .setPositiveButton("確認", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "你選擇的是" + lunch.get(which), Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                })
                .show();
    }
    private void multiDialogEvent(){
        final List<Boolean> checkedStatusList = new ArrayList<>();
        for (String s : lunch){
            checkedStatusList.add(false);
        }
        new AlertDialog.Builder(MainActivity.this)
                .setMultiChoiceItems(
                        lunch.toArray(new String[lunch.size()]),
                        new boolean[lunch.size()],
                        new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        checkedStatusList.set(which, isChecked);
                    }
                })
                .setPositiveButton("確認", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        StringBuilder sb = new StringBuilder();
                        boolean isEmpty = true;
                        for(int i = 0; i<checkedStatusList.size() ; i++){
                            if(checkedStatusList.get(i)){
                                sb.append(lunch.get(i));
                                sb.append("  ");
                                isEmpty = false;
                            }
                        }
                        if(!isEmpty){
                            Toast.makeText(MainActivity.this, "你選擇的是" + sb.toString(), Toast.LENGTH_SHORT).show();
                            for (String s : lunch)
                                checkedStatusList.add(false);
                        }else {
                            Toast.makeText(MainActivity.this, "請選擇項目", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .show();
    }
}
