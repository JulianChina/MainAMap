package com.run.sg.aboutus;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.StyleSpan;
import android.widget.TextView;

import com.run.sg.amap3d.R;

/**
 * Created by yq on 2017/5/29.
 */
public class SGAboutUsActivity extends Activity {
    private int[] mTextIds = new int[]{
            R.id.about_us_text2_1,
            R.id.about_us_text2_2,
            R.id.about_us_text2_3,
            R.string.about_us_text_2_1,
            R.string.about_us_text_2_2,
            R.string.about_us_text_2_3
    };

    private String mAboutUsText1_1 = "好事，传播更多力所能及的善意。";
    private String mAboutUsText1_2 =
            "        我们是由好事团队以及政府扶贫办合理创造的善心传播平台。\n" +
                    "        您可以在此发现许多需要帮助的人群和家庭，通过地图寻找他们的店铺，用实际行动来温暖他们的生活。\n" +
                    "        坚持着“不以善小而不为”的理念，我们不辞辛苦地联系着更多需要帮助的家庭，尽可能地用爱传播社会正能量。";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.la_sg_about_us_activity_content);

        initViewAndText();
    }

    private void initViewAndText(){
        ((TextView)findViewById(R.id.about_us_text1_1)).setText(mAboutUsText1_1);
        ((TextView)findViewById(R.id.about_us_text1_2)).setText(mAboutUsText1_2);

        for (int i = 0;i < 3;++i){
            SpannableString spannableString2 = new SpannableString(
                    this.getResources().getString(mTextIds[i+3]));
            BackgroundColorSpan colorSpan = new BackgroundColorSpan(Color.parseColor("#AC00FF30"));
            spannableString2.setSpan(colorSpan, 0, spannableString2.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            ((TextView)findViewById(mTextIds[i])).setText(spannableString2);
        }

        SpannableString spannableString3 = new SpannableString(
                this.getResources().getString(R.string.about_us_text_3));
        StyleSpan styleSpan_B  = new StyleSpan(Typeface.BOLD);
        StyleSpan styleSpan_I  = new StyleSpan(Typeface.ITALIC);
        spannableString3.setSpan(styleSpan_B,
                0, 7, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString3.setSpan(styleSpan_I,
                8, spannableString3.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        ((TextView)findViewById(R.id.about_us_text3)).setMovementMethod(LinkMovementMethod.getInstance());
        ((TextView)findViewById(R.id.about_us_text3)).setHighlightColor(Color.parseColor("#36969696"));
        ((TextView)findViewById(R.id.about_us_text3)).setText(spannableString3);
    }
}
