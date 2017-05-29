package com.run.sg.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.run.sg.amap3d.R;


/**
 * Created by Daijie on 17/5/28.
 */

public class SGCustomDialog extends Dialog {
    private Context mContext;
    private String mTitle;
    private String mMessage;
    private ButtonClickListener mButtonClickListener;

    public interface ButtonClickListener {
        void doNavigate();
        void doReward();
        void doCancel();
    }

    public SGCustomDialog(Context context, String title, String message) {
        super(context, R.style.MyDialogStyle);
        this.mContext = context;
        this.mTitle = title;
        this.mMessage = message;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.sg_custom_dialog, null);
        setContentView(view);

        TextView titleTV = (TextView) findViewById(R.id.title);
        titleTV.setText(mTitle);
        TextView messageTV = (TextView) findViewById(R.id.message);
        messageTV.setText(mMessage);
        messageTV.setMovementMethod(ScrollingMovementMethod.getInstance());
        Button navigateBtn = (Button) findViewById(R.id.navigate);
        navigateBtn.setOnClickListener(new MyClickListener());
        Button rewardBtn = (Button) findViewById(R.id.reward);
        rewardBtn.setOnClickListener(new MyClickListener());
        ImageButton dialogClose = (ImageButton) findViewById(R.id.dialog_close);
        dialogClose.setOnClickListener(new MyClickListener());

        Window mDialogWindow = getWindow();
        if (mDialogWindow == null) {
            return;
        }
        mDialogWindow.setGravity(Gravity.START | Gravity.TOP);
        WindowManager.LayoutParams lp = mDialogWindow.getAttributes();
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        if (lp != null) {
            lp.x = (int) (dm.widthPixels * 0.1); // 新位置X坐标
            lp.y = (int) (dm.heightPixels * 0.2); // 新位置Y坐标
            lp.width = (int) (dm.widthPixels * 0.8); // 宽度
            lp.height = (int) (dm.heightPixels * 0.6); // 高度
            mDialogWindow.setAttributes(lp);
        }
    }

    private class MyClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.navigate:
                    mButtonClickListener.doNavigate();
                    break;
                case R.id.reward:
                    mButtonClickListener.doReward();
                    break;
                case R.id.dialog_close:
                    mButtonClickListener.doCancel();
                    break;
                default:
                    break;
            }
        }
    }

    public void setListener (ButtonClickListener buttonClickListener) {
        this.mButtonClickListener = buttonClickListener;
    }
}
