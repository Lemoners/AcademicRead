package com.example.prototype2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

// Gesture Detector
import android.view.GestureDetector;
import android.view.MotionEvent;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnInitListener {

    private TextToSpeech tts;
    private TextView my_text;

    private TextView tv_gesture;
    private GestureDetector mGesture; // 声明一个手势检测器对象
    private String desc = "";

    private int[] pos={0, 0};
    private String[][] long_text;

        private long start_time;
    private long end_time;
    private long duration = 0;

    private float deltaY;

    private int width = 1080;
    private int height = 1920;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tts = new TextToSpeech(this, this);

        tv_gesture = findViewById(R.id.tv_gesture);
        // 创建一个手势检测器
        mGesture = new GestureDetector(this, new MyGestureListener());

        my_text = findViewById(R.id.my_text);
        my_text.setOnClickListener(this);

        long_text = new String[][]{
                {"细胞中的糖类",
                        "说到糖",
                        "我们并不陌生",
                        "除了白糖、冰糖这些我们熟知的糖以外",
                        "淀粉、纤维素等也属于糖类",
                        "这些糖类的分子有什么相同和不同之处呢？淀粉、纤维素并不甜",
                        "为什么也属于糖类呢？ 糖类（carbonydrate）分子都是由碳、氢、氧三种元素构成的",
                        "因为多数糖类分子中氢原子和氧原子之比是2比1"},
        {"类似水分子",
                "因而糖类又称为碳水化合物",
                "糖类大致可以分为单糖、二糖和多糖等几类",
                "单糖",
                "葡萄糖是细胞生命活动所需要的主要能量物质",
                "常被形容为生命的燃料",
                "葡萄糖不能水解",
                "可直接被细胞吸收"},
        {"像这样不能水解的糖就是单糖",
                "二糖",
                "二糖（C十二H二十二O十一）由两分子单糖脱水缩合而成",
                "二糖必须水解成单糖才能被细胞吸收",
                "生活中最常见的二糖是蔗糖",
                "红糖、白糖、冰糖等都是由蔗期加工制成的",
                "多糖",
                "生物体内的糖类绝大多数以多糖"},
        {"（C6H十O5）n）的形式存在",
                "淀粉是最常见的多糖",
                "淀粉不易溶于水",
                "人们食用的淀粉",
                "必须经过消化分解成葡萄糖",
                "才能被细胞吸收利用",
                "食物中的淀粉水解后变成葡萄糖",
                "这些葡萄糖成为人和动物体合成动物多糖——糖原的原料"},
        {"糖原主要分布在人和动物的肝脏和肌肉中",
                "是人和动物细胞的储能物质",
                "当细胞生命活动消耗了能量",
                "人和动物血液中葡萄糖低于正常含量时",
                "糖原便分解产生葡萄糖及时补充",
                "你注意过棉花、棕榈和麻类植物吗？它们都有长长的纤维细丝",
                "还有那些分布在其他植物茎秆和枝叶中的纤维",
                "以及所有植物细胞的细胞壁"},
        {"构成它们的主要成分都是纤维素",
                "纤维素也是多糖",
                "不溶于水",
                "在人和动物体内很难被消化",
                "即使草食类动物由发达的消化器官",
                "也需要借助某些微生物的帮忙才能分解这类多糖",
                "与淀粉和糖原一样",
                "纤维素也是由许多葡萄糖连接而成的，构成它们的基本单位都是葡萄糖分子"}
        };
    }

    //容器类的方法，控制事件分发
    public boolean dispatchTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_POINTER_DOWN:
                Toast.makeText(MainActivity.this, "双指", Toast.LENGTH_SHORT).show();
                break;
            default :
//                Toast.makeText(MainActivity.this, "单指", Toast.LENGTH_SHORT).show();
                mGesture.onTouchEvent(event);
                break;
        }
        return true;

//        mGesture.onTouchEvent(event); // 命令由手势检测器接管当前的手势事件
//        return true;
    }

    // 定义一个手势检测监听器
    final class MyGestureListener implements GestureDetector.OnGestureListener{

        // 在手势按下时触发
        public final boolean onDown(MotionEvent event) {
            //Toast.makeText(MainActivity.this, "您DOWN了屏幕", Toast.LENGTH_SHORT).show();
            // onDown的返回值没有作用，不影响其它手势的处理
            return true;
        }

        // 在手势飞快掠过时触发
        public final boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float offsetX = e1.getX() - e2.getX();
            float offsetY = e1.getY() - e2.getY();
            float y_value = e1.getY();
            float row = height / 8;
            if (Math.abs(offsetX) > Math.abs(offsetY)) { // 水平方向滑动
                if (offsetX > 0) {
                    desc = "您向左滑动了一下\n";
                } else {
//                    int action = e1.getActionMasked();
//                    switch (action) {
//                        case MotionEvent.ACTION_POINTER_DOWN:
//                            desc = "双指右划";
//                            break;
//                        default :
//                            desc = "单指右划";
//                            break;
//                    }
                    desc = "向右滑动"+y_value;
                    if (y_value < row){
                        pos[1] = 0;
                    }
                    else if (y_value < 2*row)
                    {
                        pos[1] = 1;
                    }
                    else if (y_value < 3*row)
                    {
                        pos[1] = 2;
                    }
                    else if (y_value < 4*row)
                    {
                        pos[1] = 3;
                    }
                    else if (y_value < 5*row)
                    {
                        pos[1] = 4;
                    }
                    else if (y_value < 6*row)
                    {
                        pos[1] = 5;
                    }
                    else if (y_value < 7*row)
                    {
                        pos[1] = 6;
                    }
                    else{
                        pos[1] = 7;
                    }
                    tts.speak(long_text[pos[0]][pos[1]], TextToSpeech.QUEUE_FLUSH, null, null);  //
                }
            } else { // 垂直方向滑动
                if (offsetY > 0) {
                    desc = "您向上滑动了一下\n";
                    pos[0] = Math.min(pos[0]+1, long_text.length-1);
                    tts.speak("第" + (pos[0]+1) +"页", TextToSpeech.QUEUE_FLUSH, null, null);
                } else {
                    desc = "您向下滑动了" + offsetY + "\n";
                    pos[0] = Math.max(0, pos[0]-1);
                    tts.speak("第" + (pos[0]+1) +"页", TextToSpeech.QUEUE_FLUSH, null, null);
                }
            }
            tv_gesture.setText(desc);
            return true;
        }

        // 在手势长按时触发
        public final void onLongPress(MotionEvent event) {
            desc = "您长按了一下\n";
            tv_gesture.setText(desc);
        }

        // 在手势滑动过程中触发
        public final boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            deltaY = e2.getY() - e1.getY();
            // desc = "您在X方向滑动" + e1.getX() + "," + e2.getX() + "\n" + "您在Y方向滑动" + e1.getY() + "," + e2.getY() + "\n";
            desc = "Y方向差值" + deltaY;
            tv_gesture.setText(desc);
            return false;
        }

        // 在已按下但还未滑动或松开时触发
        public final void onShowPress(MotionEvent event) {
        }

        // 在轻点弹起时触发，也就是点击时触发
        public boolean onSingleTapUp(MotionEvent event) {
            // 返回true表示我已经处理了，别处不要再处理这个手势
            return true;
        }

        public boolean onDoubleTap(MotionEvent event) {     //双击事件，检测不到
            pos[0] = Math.max(0, pos[0]-1);
            desc = "双击";
            tv_gesture.setText(desc);
            return true;
        }

        private void mySpeak() {  //
            start_time = System.currentTimeMillis();
            Log.i("my_info", String.format("IN %d, %d", pos[0], pos[1]));
            if(pos[0] > 3){
                pos[0] = 0;
            }
            for(; pos[0]<4; pos[0]++){
                if(pos[1] > 3){
                    pos[1] = 0;
                }
                /*for(; pos[1]<4;){
                    if(!tts.isSpeaking()){
                        tts.speak(long_text[pos[0]][pos[1]], TextToSpeech.QUEUE_ADD, null, null
                        );
                        Log.i("my_info", String.format("%d, %d", pos[0], pos[1]));
                        pos[1]++;
                    }
                }*/  //会阻塞，但是pos是即时的，尝试用多线程写
                for(; pos[1]<4;pos[1]++){//
                    tts.speak(long_text[pos[0]][pos[1]], TextToSpeech.QUEUE_ADD, null, null);
//                    Log.i("my_info", String.format("%d, %d", pos[0], pos[1]));
                }
            }
        }

        private void myStopLocation(){
            tts.stop();
            end_time = System.currentTimeMillis();
            duration = duration + end_time - start_time;
            int time_pos = (int) Math.floor(duration / 1360);
            if(time_pos > 11 ){
                pos[0] = 3;
                pos[1] = Math.min(time_pos - 12, 3);
            }
            else if(time_pos > 7){
                pos[0] = 2;
                pos[1] = Math.min(time_pos - 8, 3);
            }
            else if(time_pos > 3){
                pos[0] = 1;
                pos[1] = Math.min(time_pos - 4, 3);
            }
            else{
                pos[0] = 0;
                pos[1] = time_pos;
            }
            Log.i("my_time", String.format("%d", duration));
            Log.i("my_info", String.format("STOP %d, %d", pos[0], pos[1]));
        }
    }

    @Override
    public void onInit(int status) {
        // 判断是否转化成功
        if (status == TextToSpeech.SUCCESS){
            int result = tts.setLanguage(Locale.CHINESE);
                    /*if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                        Toast.makeText(MainActivity.this, "not available", Toast.LENGTH_SHORT).show();
                    }else{
                        tts.setLanguage(Locale.US);//不支持中文就将语言设置为英文
                    }*/
        }
        else
        {
            desc = "tts failed";
            tv_gesture.setText(desc);
            Toast.makeText(MainActivity.this, "not available", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        //朗读EditText里的内容
        tts.speak(my_text.getText().toString(), TextToSpeech.QUEUE_FLUSH,null,null);
        Toast.makeText(MainActivity.this, "您点击了文本", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if(tts != null)
            tts.shutdown();//关闭TTS
    }
}
