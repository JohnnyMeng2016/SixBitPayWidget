package com.johnny.sixbitpaywidget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static android.view.View.GONE;

/**
 * 项目名称：SixBitPayWidget
 * 类描述：
 * 创建人：孟忠明
 * 创建时间：2016/12/5
 */
public class PasswordActivity extends Activity implements AdapterView.OnItemClickListener {

    private final static int NUMBER_BUTTON_DELETE = 11;
    private final static int NUMBER_BUTTON_ZERO = 10;//0号按键
    private final static int NUMBER_BUTTON_CLEAR = 9;//清除按键

    private ImageView backBtn;
    private PayPasswordView passwordView;
    private ImageView imageView;
    private GridView gridView;
    private TickView tickView;
    private TextView tipView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高
        android.view.WindowManager.LayoutParams p = getWindow().getAttributes();
        Point point = new Point();
        d.getSize(point);
        p.height = (int) (point.y * 0.65); // 高度设置为屏幕的0.65
        p.width = point.x;
        getWindow().setGravity(Gravity.LEFT | Gravity.BOTTOM);
        getWindow().setAttributes(p);

        backBtn = (ImageView) findViewById(R.id.dialog_back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        passwordView = (PayPasswordView) findViewById(R.id.pwd_pay);
        passwordView.setOnCompleteLinstener(new PayPasswordView.OnCompleteLinstener() {
            @Override
            public void onComplete() {
                passwordView.setVisibility(GONE);
                gridView.setVisibility(GONE);
                tipView.setVisibility(View.VISIBLE);
                tipView.setText("加载中...");
                imageView.setVisibility(View.VISIBLE);
                RotateAnimation an = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f);
                an.setInterpolator(new LinearInterpolator());
                an.setRepeatCount(-1);//重复次数
                an.setDuration(1000);
                imageView.startAnimation(an);
                imageView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imageView.clearAnimation();
                        imageView.setVisibility(GONE);
                        tipView.setVisibility(GONE);
                        tickView.setVisibility(View.VISIBLE);
                        tickView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, 1200);
                    }
                }, 3000);
            }
        });
        imageView = (ImageView) findViewById(R.id.iv_progress);
        tickView = (TickView) findViewById(R.id.progress_result);
        tipView = (TextView) findViewById(R.id.tv_tip);
        gridView = (GridView) findViewById(R.id.gv_keyboard);
        KeyBoardAdapter keyBoardAdapter = new KeyBoardAdapter(this);
        gridView.setAdapter(keyBoardAdapter);
        gridView.setOnItemClickListener(this);
    }

    @Override
    protected void onPause() {
        overridePendingTransition(R.anim.keyboard_show, R.anim.keyboard_hide);
        super.onPause();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String password = passwordView.getText().toString();
        if (position == NUMBER_BUTTON_CLEAR) {
            password = "";
        } else if (position == NUMBER_BUTTON_DELETE) {
            if (password.length() > 0) {
                password = password.substring(0, password.length() - 1);
            }
        } else {
            if (position == NUMBER_BUTTON_ZERO) {
                password += "0";
            } else {
                password += (position + 1);
            }
        }
        passwordView.setText(password);
    }

    class KeyBoardAdapter extends BaseAdapter {
        private static final String NUMBERS = "123456789C0#";

        private Context mContext;

        public KeyBoardAdapter(Context context) {
            mContext = context;
        }

        @Override
        public int getCount() {
            return NUMBERS.length();
        }

        @Override
        public Object getItem(int position) {
            return String.valueOf(NUMBERS.charAt(position));
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ItemHolder itemHolder;
            if (convertView == null) {
                itemHolder = new ItemHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_view_input_group_code, null);
                itemHolder.mRootView = (RelativeLayout) convertView.findViewById(R.id.number_root_view);
                itemHolder.mNumberTextView = (TextView) convertView.findViewById(R.id.number_textView);
                itemHolder.mDeleteImageView = (ImageView) convertView.findViewById(R.id.number_delete_imageView);
                convertView.setTag(itemHolder);
            } else {
                itemHolder = (ItemHolder) convertView.getTag();
            }
            String curNumber = (String) getItem(position);
            if ("C".equals(curNumber)) {
                itemHolder.mDeleteImageView.setVisibility(GONE);
                itemHolder.mNumberTextView.setVisibility(View.VISIBLE);
                itemHolder.mNumberTextView.setText(curNumber);
                itemHolder.mRootView.setBackgroundColor(getResources().getColor(R.color.gray));
            } else if ("#".equals(curNumber)) {
                itemHolder.mRootView.setBackgroundColor(getResources().getColor(R.color.gray));
                itemHolder.mNumberTextView.setVisibility(GONE);
                itemHolder.mDeleteImageView.setVisibility(View.VISIBLE);
            } else {
                itemHolder.mRootView.setBackgroundResource(R.drawable.list_selector);
                itemHolder.mDeleteImageView.setVisibility(GONE);
                itemHolder.mNumberTextView.setVisibility(View.VISIBLE);
                itemHolder.mNumberTextView.setText(curNumber);
            }
            return convertView;
        }
    }

    private static class ItemHolder {
        RelativeLayout mRootView;
        TextView mNumberTextView;
        ImageView mDeleteImageView;
    }
}
