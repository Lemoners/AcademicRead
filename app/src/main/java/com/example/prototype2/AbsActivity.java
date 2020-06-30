package com.example.prototype2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.Vector;

public class AbsActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
    TextView mTextView;
    AbsFileLoader mAbsFileLoader;
    Button mButton;

    String[] displayText;
    int startDisplayIndex = 0;
    Vector<Integer> historyStartDisplayIndex;
    int displayPerPage = 0;

    int startReadIndex = 0;

    float lastX;
    float lastY;

    private TextToSpeech textToSpeech;

    String readingNow = "";

    float tvWidth = 0;
    float tvHeight = 0;

    private class OnTvGlobalLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {
        @Override
        public void onGlobalLayout() {
            mTextView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//            final String newText = autoSplitText(mTextView);
//            if (!TextUtils.isEmpty(newText)) {
//                mTextView.setText(newText);
//            }
            Log.e("tvWidth here", String.valueOf(mTextView.getWidth()));
        }
    }

    private String autoSplitText(final TextView tv) {
        final String rawText = tv.getText().toString(); // rawText
        final Paint tvPaint = tv.getPaint(); // paint, include font info
        final int tvWidth = tv.getWidth() - tv.getPaddingRight() - tv.getPaddingLeft();
        Log.e("tvWidth", String.valueOf(tvWidth));

        String[] rawTextLines = rawText.replace("\r", "").split("\n");
        StringBuilder sbNewText = new StringBuilder();

        int lineCount = 0;
        for (int i = 0; i < rawTextLines.length; i++) {
            String rawTextLine = rawTextLines[i];
            if (tvPaint.measureText(rawTextLine) <= tvWidth) {
                if (lineCount%2 == 1) {
                    StringBuilder sbRawTextLine = new StringBuilder(rawTextLine);
                    while (tvPaint.measureText(sbRawTextLine.toString()) < tvWidth) {
                        sbRawTextLine.append(" ");
                    }
                    sbNewText.append(sbRawTextLine.reverse().toString());
                } else {
                    sbNewText.append(rawTextLine);
                }
                lineCount += 1;
            } else {
                float lineWidth = 0;
                StringBuilder sbRawTextLine = new StringBuilder();
                for (int cnt = 0; cnt != rawTextLine.length(); cnt++) {
                    char ch = rawTextLine.charAt(cnt);
                    lineWidth += tvPaint.measureText(String.valueOf(ch));
                    if (lineWidth <= tvWidth) {
                        sbRawTextLine.append(ch);
                    } else {
                        if (lineCount % 2 == 1) {
                            sbNewText.append(sbRawTextLine.reverse().toString());
                        } else {
                            sbNewText.append(sbRawTextLine.toString());
                        }
                        sbNewText.append("\n");
                        sbRawTextLine = new StringBuilder();
                        lineWidth = 0;
                        lineCount += 1;
                        --cnt;
                    }
                }
            }
            sbNewText.append("\n");
        }

        if (!rawText.endsWith("\n")) {
            sbNewText.deleteCharAt(sbNewText.length() - 1);
        }


        String newText = sbNewText.toString();
//        Log.e("Call", "in");
//        Log.e("Return", newText);
        return newText;
    }

    private String autoSplitText(final TextView tv, String text) {
        final String rawText = text; // rawText
        final Paint tvPaint = tv.getPaint(); // paint, include font info
        final int tvWidth = tv.getWidth() - tv.getPaddingRight() - tv.getPaddingLeft();
        Log.e("tvWidth", String.valueOf(tvWidth));
        if (tvWidth == 0) {
            return text;
        }

        String[] rawTextLines = rawText.replace("\r", "").split("\n");
        StringBuilder sbNewText = new StringBuilder();

        int lineCount = 0;
        for (int i = 0; i < rawTextLines.length; i++) {
            String rawTextLine = rawTextLines[i];
            if (tvPaint.measureText(rawTextLine) <= tvWidth) {
                if (lineCount%2 == 1) {
                    StringBuilder sbRawTextLine = new StringBuilder(rawTextLine);
                    while (tvPaint.measureText(sbRawTextLine.toString()) < tvWidth) {
                        sbRawTextLine.append(" ");
                    }
                    sbNewText.append(sbRawTextLine.reverse().toString());
                } else {
                    sbNewText.append(rawTextLine);
                }
                lineCount += 1;
            } else {
                float lineWidth = 0;
                StringBuilder sbRawTextLine = new StringBuilder();
                for (int cnt = 0; cnt != rawTextLine.length(); cnt++) {
                    char ch = rawTextLine.charAt(cnt);
                    lineWidth += tvPaint.measureText(String.valueOf(ch));
                    if (lineWidth <= tvWidth) {
                        sbRawTextLine.append(ch);
                    } else {
                        if (lineCount % 2 == 1) {
                            sbNewText.append(sbRawTextLine.reverse().toString());
                        } else {
                            sbNewText.append(sbRawTextLine.toString());
                        }
                        sbNewText.append("\n");
                        sbRawTextLine = new StringBuilder();
                        lineWidth = 0;
                        lineCount += 1;
                        --cnt;
                    }
                }
            }
            sbNewText.append("\n");
        }

        if (!rawText.endsWith("\n")) {
            sbNewText.deleteCharAt(sbNewText.length() - 1);
        }


        String newText = sbNewText.toString();
//        Log.e("Call", "in");
//        Log.e("Return", newText);N
        return newText;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abs);

        mTextView = (TextView) findViewById(R.id.mTextView);



//        mButton = (Button) findViewById(R.id.mButton);
        mAbsFileLoader = new AbsFileLoader();

//        mTextView.getViewTreeObserver().addOnGlobalLayoutListener(new OnTvGlobalLayoutListener());

        setSpace();
        initTextToSpeech();
        historyStartDisplayIndex = new Vector<Integer>();

        // modify here
//        displayText = mAbsFileLoader.lineText;
//        displayText = mAbsFileLoader.vocabText;
        displayText = mAbsFileLoader.charText;
        mTextView.post(new Runnable() {
            @Override
            public void run() {
                tvWidth = mTextView.getWidth() - mTextView.getPaddingLeft() - mTextView.getPaddingRight();
                tvHeight = mTextView.getHeight() - mTextView.getPaddingBottom() - mTextView.getPaddingTop();
                StringBuilder sb = new StringBuilder();
//                displayPerPage = -1;
//                while (mTextView.getPaint().measureText(sb.toString()) < tvWidth) {
//                    sb.append("中");
//                    displayPerPage += 1;
//                }
                displayPerPage = (int)(tvWidth / mTextView.getPaint().measureText("我") - 2) * (int)(tvHeight / (2.0*mTextView.getPaint().measureText("中"))) + 1;
                Log.e("Estimate pre line", String.valueOf(displayPerPage/8));
                set(displayText);
            }
        });

        Button btPrev = (Button) findViewById(R.id.mButtonPrev);
        btPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDisplayIndex = getPreviousStartDisplayIndex();
                Log.e("Prev", String.valueOf(startDisplayIndex));
                if (startDisplayIndex == -1) {
                    startDisplayIndex = 0;
                    set(displayText);
                    read("已到第一页");
                } else {
                    set(displayText);
                    read("上翻一页");
                }
            }
        });

        Button btNext = (Button) findViewById(R.id.mButtonNext);
        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startDisplayIndex < displayText.length) {
//                    Log.e("ADD", String.valueOf(startDisplayIndex));
                    historyStartDisplayIndex.add(startDisplayIndex);
                    set(displayText);
                    read("下翻一页");
                } else {
                    read("已到最后一页");
                }
            }
        });


    }


    public void set(String[] strList) {
        textToSpeech.stop();
//        String text = "";
        int count = 0;

        int tmptStartDisplayIndex = startDisplayIndex;

        for (int i = tmptStartDisplayIndex; i < strList.length; i++) {
            if (count + strList[i].length() < displayPerPage) {
                count += strList[i].length();
                if (i == strList.length - 1) {
                    startDisplayIndex = strList.length;
                }
            } else {
                startDisplayIndex = i;
                break;
            }
        }

        SpannableStringBuilder sbSpanString = new SpannableStringBuilder();
        Paint tvPaint = mTextView.getPaint();

        Vector<String> strListThisPage = new Vector<String>();
        for (int i = tmptStartDisplayIndex; i < startDisplayIndex; i++) {
            strListThisPage.add(strList[i]);
        }

        int lineCount = 0;
        StringBuilder lineWidth = new StringBuilder();
        int start = 0;
        Vector<String> thisLine = new Vector<String>();
        for (int i = tmptStartDisplayIndex; i < startDisplayIndex; i++) {
            if (tvPaint.measureText(lineWidth + strListThisPage.get(i-tmptStartDisplayIndex)) <= tvWidth) {
//                Log.e("Measure", String.valueOf(tvPaint.measureText(strListThisPage.get(i))));
                lineWidth.append(strListThisPage.get((i-tmptStartDisplayIndex)));
                thisLine.add(strListThisPage.get(i-tmptStartDisplayIndex));
            } else {
                StringBuilder charInThisLine = new StringBuilder();
                for (int cnt = 0; cnt < strListThisPage.get(i-tmptStartDisplayIndex).length(); cnt++) {
                    char ch = strListThisPage.get(i-tmptStartDisplayIndex).charAt(cnt);
                    if (tvPaint.measureText(lineWidth + String.valueOf(ch)) <= tvWidth) {
                        lineWidth.append(String.valueOf(ch));
                        charInThisLine.append(ch);
                    } else {
                        int length = strListThisPage.get(i-tmptStartDisplayIndex).length();
                        strListThisPage.set(i-tmptStartDisplayIndex, strListThisPage.get(i-tmptStartDisplayIndex).substring(cnt, length));
                        i--;
                        break;
                    }
                }
                thisLine.add(charInThisLine.toString());
                if (lineCount % 2 == 0) {
                    for (String s: thisLine) {
                        sbSpanString.append(s);
                        sbSpanString.setSpan(getClickableSpan(s), start, start+s.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        start += s.length();
                    }

                } else {
                    //reverse
                    for (int q = thisLine.size() - 1; q >= 0; q--) {
                        sbSpanString.append(new StringBuilder(thisLine.get(q)).reverse().toString());
                        sbSpanString.setSpan(getClickableSpan(thisLine.get(q)), start, start+thisLine.get(q).length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        start += thisLine.get(q).length();
                    }
                }
                sbSpanString.append("\n");
                start += 1;
                thisLine.clear();
                lineCount += 1;
                lineWidth = new StringBuilder();
            }
        }
        // blank space
        StringBuilder blank = new StringBuilder();
        while (tvPaint.measureText(lineWidth + " ") < tvWidth) {
            blank.append(" ");
            lineWidth.append(" ");
        }
        thisLine.add(blank.toString());

        //add what's left
        if(lineCount % 2 == 0) {
            for (String s: thisLine) {
                sbSpanString.append(s);
                if (s.replace(" ", "").equals("")) {
                    sbSpanString.setSpan(getClickableSpan("请翻页"), start, start+s.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                } else {
                    sbSpanString.setSpan(getClickableSpan(s), start, start+s.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                start += s.length();
            }
        } else {
            //reverse
            for (int q = thisLine.size() - 1; q >= 0; q--) {
                sbSpanString.append(new StringBuilder(thisLine.get(q)).reverse().toString());
                if (thisLine.get(q).replace(" ", "").equals("")) {
                    sbSpanString.setSpan(getClickableSpan("请翻页"), start, start+thisLine.get(q).length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                } else {
                    sbSpanString.setSpan(getClickableSpan(thisLine.get(q)), start, start + thisLine.get(q).length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                start += thisLine.get(q).length();
            }
        }
        thisLine.clear();

        mTextView.setText(sbSpanString, TextView.BufferType.SPANNABLE);
//        mTextView.setLineSpacing(14f, 1.6f);
        mTextView.setMovementMethod(mLinkMovementMethod.getInstance());
    }

    public int getPreviousStartDisplayIndex() {
        int size = historyStartDisplayIndex.size();
        if (size <= 1) {
            historyStartDisplayIndex.clear();
            return -1;
        } else {
            int res = historyStartDisplayIndex.elementAt(size - 2);
            historyStartDisplayIndex.removeElementAt(size-1);
            historyStartDisplayIndex.removeElementAt(size-2);
            return res;
        }
    }

    public void setSpace() {
        mTextView.setTextSize(25f);
        mTextView.setLineSpacing(0f, 1.5f);
    }

    public ClickableSpan getClickableSpan(final String _myText) {
        return new ClickableSpan() {
            String myText = _myText;
            @Override
            public void onClick(@NonNull View view) {
                TextView tv = (TextView) view;
                // Error input
                if (tv.getSelectionStart() == -1 || tv.getSelectionEnd() == -1) {
                    return;
                }

//                String s = tv
//                        .getText()
//                        .subSequence(tv.getSelectionStart(),
//                                tv.getSelectionEnd()).toString();
                String s = myText;

                Toast.makeText(AbsActivity.this,s, Toast.LENGTH_SHORT).show();

                if (!textToSpeech.isSpeaking() || !readingNow.equals(s)) {
                    read(s);
                }
                readingNow = s;
            }

            /*
            set unselected color
             */
            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(Color.BLACK);
                ds.setUnderlineText(false);
            }
        };
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getActionMasked() == MotionEvent.ACTION_POINTER_DOWN || ev.getActionMasked() == MotionEvent.ACTION_POINTER_UP) {
            if (ev.getActionMasked() == MotionEvent.ACTION_POINTER_DOWN) {
                lastX = ev.getX();
                lastY = ev.getY();
            } else {
                float x = ev.getX();
                float y = ev.getY();
                if (Math.abs(lastY - y) < 300) {
                    //do nothing
                } else {
//                    if (y > lastY) {
//                        startDisplayIndex = getPreviousStartDisplayIndex();
//                        if (startDisplayIndex == -1) {
//                            startDisplayIndex = 0;
//                            read("以到第一页");
//                        } else {
//                            read("上翻一页");
//                        }
//                        set(displayText);
//                    } else {
//                        if (startDisplayIndex < displayText.length) {
//                            historyStartDisplayIndex.add(startDisplayIndex);
//                            set(displayText);
//                            read("下翻一页");
//                        } else {
//                            read("已到最后一页");
//                        }
//                    }
                }

            }
            return true;
        } else {
            return super.dispatchTouchEvent(ev);
        }
    }



    private void initTextToSpeech() {
        textToSpeech = new TextToSpeech(this, this);
        // 音调，值越大越偏女声
        textToSpeech.setPitch(1.0f);
        // 设置语速
        textToSpeech.setSpeechRate(1.0f);
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = textToSpeech.setLanguage(Locale.CHINA);
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, String.format("TTS fail to work due to %d", result), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void read(String s, float r) {
        s = s.trim();
        if (textToSpeech != null) {
            textToSpeech.setSpeechRate(r);
            textToSpeech.speak(s, TextToSpeech.QUEUE_FLUSH, null, null);
        }
    }

    private void read(String s) {
        s = s.trim();
        if (textToSpeech != null) {
            textToSpeech.setSpeechRate(1.0f);
            textToSpeech.speak(s, TextToSpeech.QUEUE_FLUSH, null, null);
        }
    }

    @Override
    protected void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
            textToSpeech = null;
        }
        super.onDestroy();
    }

}