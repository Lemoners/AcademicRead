package com.example.testapplication;

import android.os.Build;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.textclassifier.TextLinks;
import android.widget.TextView;

public class mLinkMovementMethod extends LinkMovementMethod {

    @Override
    public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
        int action = event.getAction();
//        Log.e("Out", String.valueOf(event.getX()));
        if (action == MotionEvent.ACTION_MOVE && event.getPointerCount() == 1) {
//            Log.e("In", String.valueOf(event.getX()));
            float x =  event.getX();
            float y =  event.getY();

            x -= widget.getTotalPaddingLeft();
            y -= widget.getTotalPaddingTop();

            x += widget.getScrollX();
            y += widget.getScrollY();

            Layout layout = widget.getLayout();
            int line = layout.getLineForVertical((int)y);
            int off = layout.getOffsetForHorizontal(line, (int)x);

            float xLeft=layout.getPrimaryHorizontal(off);
            if(xLeft < x){
                off+=1;
            }

            ClickableSpan[] links = buffer.getSpans(off, off, ClickableSpan.class);

            if (links.length != 0) {
                ClickableSpan link = links[0];
                links[0].onClick(widget);
                Selection.setSelection(buffer,
                        buffer.getSpanStart(link),
                        buffer.getSpanEnd(link));
                return true;
            } else {
                Selection.removeSelection(buffer);
            }
        }
        return super.onTouchEvent(widget, buffer, event);
    }

    public static MovementMethod getInstance() {
        return new mLinkMovementMethod();
    }
}
