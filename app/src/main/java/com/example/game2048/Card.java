package com.example.game2048;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

public class Card extends FrameLayout {

	private int number = 0;         // 保存数字

	private TextView numberTV;        // TextView 用于显示卡片上面的数字

	public Card(Context context) {
		super(context);

		// 初始化 TextView
		numberTV = new TextView(getContext());
		numberTV.setTextSize(32);
		numberTV.setGravity(Gravity.CENTER);
		numberTV.setBackgroundColor(0x33FFFFFF);

		// 添加数字
		LayoutParams layoutParams = new LayoutParams(-1, -1);
		layoutParams.setMargins(10, 10, 0, 0);
		addView(numberTV, layoutParams);

		setNumber(0);       // 初始化数字为 0
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
		if (this.number > 0) {
			numberTV.setText(number + "");
			Log.e("===>0===", number+ "");
		} else {
			numberTV.setText("");       // 空文本，不显示数字 0
			Log.e("===<=0===", "INSIDE");
		}

		switch (number) {
			case 0:
				numberTV.setBackgroundColor(0x33FFFFFF);
				break;

			case 2:
				numberTV.setTextColor(getResources().getColor(R.color.text2));
				numberTV.setBackgroundColor(getResources().getColor(R.color.bg2));
				break;

			case 4:
				numberTV.setTextColor(getResources().getColor(R.color.text4));
				numberTV.setBackgroundColor(getResources().getColor(R.color.bg4));
				break;

			case 8:
				numberTV.setTextColor(getResources().getColor(R.color.text8));
				numberTV.setBackgroundColor(getResources().getColor(R.color.bg8));
				break;

			case 16:
				numberTV.setTextColor(getResources().getColor(R.color.text16));
				numberTV.setBackgroundColor(getResources().getColor(R.color.bg16));
				break;

			case 32:
				numberTV.setTextColor(getResources().getColor(R.color.text32));
				numberTV.setBackgroundColor(getResources().getColor(R.color.bg32));
				break;

			case 64:
				numberTV.setTextColor(getResources().getColor(R.color.text64));
				numberTV.setBackgroundColor(getResources().getColor(R.color.bg64));
				break;

			case 128:
				numberTV.setTextColor(getResources().getColor(R.color.text128));
				numberTV.setBackgroundColor(getResources().getColor(R.color.bg128));
				break;

			case 256:
				numberTV.setTextColor(getResources().getColor(R.color.text256));
				numberTV.setBackgroundColor(getResources().getColor(R.color.bg256));
				break;

			case 512:
				numberTV.setTextColor(getResources().getColor(R.color.text512));
				numberTV.setBackgroundColor(getResources().getColor(R.color.bg512));
				break;

			case 1024:
				numberTV.setTextColor(getResources().getColor(R.color.text1024));
				numberTV.setBackgroundColor(getResources().getColor(R.color.bg1024));
				break;

			case 2048:
				numberTV.setTextColor(getResources().getColor(R.color.text2048));
				numberTV.setBackgroundColor(getResources().getColor(R.color.bg2048));
				break;

			default:
				numberTV.setTextColor(getResources().getColor(R.color.textsuper));
				numberTV.setBackgroundColor(getResources().getColor(R.color.bgsuper));
				break;

		}
	}

	public boolean equals(Card card) {
		return getNumber() == card.getNumber();
	}
}
