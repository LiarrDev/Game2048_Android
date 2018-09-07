package com.example.game2048;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.List;

public class GameViewLayout extends GridLayout {

	private Card[][] cardMap = new Card[4][4];      // 记录游戏

	private List<Point> emptyPoints = new ArrayList<Point>();        // 空点列表

	public static GameViewLayout gameViewLayout = null;

	public static GameViewLayout getGameViewLayout() {
		return gameViewLayout;
	}

	public GameViewLayout(Context context) {
		super(context);
		gameViewLayout = this;
		initGameView();
	}

	public GameViewLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		gameViewLayout = this;
		initGameView();
	}

	public GameViewLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		gameViewLayout = this;
		initGameView();
	}

	private void initGameView() {

		setColumnCount(4);      // 设置列数为 4
		setBackgroundColor(0xFFBBADA0);

		/** 显示GridLayout */
		DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
		int cardSize = (displayMetrics.widthPixels - 10) / 4;
		Log.e("==CARDSIZE==", cardSize + "");
		addCards(cardSize);         // 把 Card 添加进来

		startGame();

		setOnTouchListener(new OnTouchListener() {

			float startX, startY;       // 起始位置坐标
			float endX, endY;           // 结束位置坐标
			float offsetX, offsetY;     // 偏移量

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				switch (event.getAction()) {

					case MotionEvent.ACTION_DOWN:       // 当触摸屏幕时获取坐标并把它定义为初始位置
						startX = event.getX();
						startY = event.getY();
						break;

					case MotionEvent.ACTION_UP:         // 当触摸结束时获取坐标并把它定义为结束位置
						endX = event.getX();
						endY = event.getY();

						// 计算偏移量
						offsetX = endX - startX;
						offsetY = endY - startY;

						// 偏移量绝对值确定用户是想进行水平方向上的移动还是垂直方向上的移动
						if (Math.abs(offsetX) > Math.abs(offsetY)) {
							// X 轴上的偏移量正负代表左右
							if (offsetX < -5) {
								Log.e("OFFSET", "Left");
								moveToLeft();
							} else if (offsetX > 5) {
								Log.e("OFFSET", "Right");
								moveToRight();
							}
						} else {
							// Y 轴上的偏移量正负代表上下
							if (offsetY < -5) {
								Log.e("OFFSET", "Up");
								moveToTop();
							} else if (offsetY > 5) {
								Log.e("OFFSET", "Down");
								moveToBottom();
							}
						}
				}

				return true;
			}
		});
	}

	// 向左移动
	private void moveToLeft() {

		boolean move = false;

		for (int x = 0; x < 4; x++) {           // 行循环
			for (int y = 0; y < 4; y++) {       // 列循环
				for (int j = y + 1; j < 4; j++) {       // 从当前位置往右扫描
					if (cardMap[x][j].getNumber() > 0) {
						if (cardMap[x][y].getNumber() <= 0) {
							cardMap[x][y].setNumber(cardMap[x][j].getNumber());     // 空位，左移
							cardMap[x][j].setNumber(0);     // 清空
							y--;        // 避免出现【2】【0】【2】不合并的清空
							move = true;
						} else if (cardMap[x][y].equals(cardMap[x][j])) {
							cardMap[x][y].setNumber(cardMap[x][y].getNumber() * 2);     // 左移，合并
							cardMap[x][j].setNumber(0);     // 清空
							MainActivity.getMainActivity().addScore(cardMap[x][y].getNumber());     // 加分
							move = true;
						}
						break;
					}
				}
			}
		}

		// 如果进行了移动操作，则添加新卡片
		if (move) {
			addRandomNumber();
			checkGame();        // 判断游戏是否结束
		}
	}

	// 向右移动
	private void moveToRight() {

		boolean move = false;

		for (int x = 0; x < 4; x++) {           // 行循环
			for (int y = 3; y >= 0; y--) {      // 列循环
				for (int j = y - 1; j >= 0; j--) {      // 从当前往左扫描
					if (cardMap[x][j].getNumber() > 0) {
						if (cardMap[x][y].getNumber() <= 0) {
							cardMap[x][y].setNumber(cardMap[x][j].getNumber());     // 空位，右移
							cardMap[x][j].setNumber(0);     // 清空
							y++;        // 避免出现【2】【0】【2】不合并的情况
							move = true;
						} else if (cardMap[x][y].equals(cardMap[x][j])) {
							cardMap[x][y].setNumber(cardMap[x][y].getNumber() * 2);     // 右移，合并
							cardMap[x][j].setNumber(0);
							MainActivity.getMainActivity().addScore(cardMap[x][y].getNumber());     // 加分
							move = true;
						}
						break;
					}
				}
			}
		}

		// 如果进行了移动操作，则添加新卡片
		if (move) {
			addRandomNumber();
			checkGame();        // 判断游戏是否结束
		}
	}

	// 向上移动
	private void moveToTop() {

		boolean move = false;

		for (int y = 0; y < 4; y++) {           // 列循环
			for (int x = 0; x < 4; x++) {       // 行循环
				for (int i = x + 1; i < 4; i++) {       // 从当前位置往下扫描
					if (cardMap[i][y].getNumber() > 0) {
						if (cardMap[x][y].getNumber() <= 0) {
							cardMap[x][y].setNumber(cardMap[i][y].getNumber());     // 空位，上移
							cardMap[i][y].setNumber(0);     // 清空
							x--;        // 避免出现【2】【0】【2】不会合并的情况
							move = true;
						} else if (cardMap[x][y].equals(cardMap[i][y])) {
							cardMap[x][y].setNumber(cardMap[x][y].getNumber() * 2);     // 上移，合并
							cardMap[i][y].setNumber(0);     // 清空
							MainActivity.getMainActivity().addScore(cardMap[x][y].getNumber());     // 加分
							move = true;
						}
						break;
					}
				}
			}
		}

		// 如果进行了移动操作，则添加新卡片
		if (move) {
			addRandomNumber();
			checkGame();        // 判断游戏是否结束
		}
	}

	// 向下移动
	private void moveToBottom() {

		boolean move = false;

		for (int y = 0; y < 4; y++) {           // 列循环
			for (int x = 3; x >= 0; x--) {      // 行循环
				for (int i = x - 1; i >= 0; i--) {      // 从当前位置往上扫描
					if (cardMap[i][y].getNumber() > 0) {
						if (cardMap[x][y].getNumber() <= 0) {
							cardMap[x][y].setNumber(cardMap[i][y].getNumber());     // 空位，下移
							cardMap[i][y].setNumber(0);     // 清空
							x++;        // 避免出现【2】【0】【2】不会合并的情况
							move = true;
						} else if (cardMap[x][y].equals(cardMap[i][y])) {
							cardMap[x][y].setNumber(cardMap[x][y].getNumber() * 2);     // 下移，合并
							cardMap[i][y].setNumber(0);     // 清空
							MainActivity.getMainActivity().addScore(cardMap[x][y].getNumber());     // 加分
							move = true;
						}
						break;
					}
				}
			}
		}

		// 如果进行了移动操作，则添加新卡片
		if (move) {
			addRandomNumber();
			checkGame();        // 判断游戏是否结束
		}
	}

	private void checkGame() {

		boolean complete = true;        // 默认游戏结束

		ALL:
		for (int i = 0; i < 4; i++) {           // 行循环
			for (int j = 0; j < 4; j++) {       // 列循环
				if (cardMap[i][j].getNumber() <= 0                                  // 还有空格
						|| (i > 0 && cardMap[i][j].equals(cardMap[i-1][j]))         // 可以左移
						|| (i < 3 && cardMap[i][j].equals(cardMap[i+1][j]))         // 可以右移
						|| (j > 0 && cardMap[i][j].equals(cardMap[i][j-1]))         // 可以上移
						|| (j < 3 && cardMap[i][j].equals(cardMap[i][j+1]))) {      // 可以下移
					complete = false;       // 游戏未结束
					break ALL;
				}
			}
		}

		if (complete) {
			new AlertDialog.Builder(getContext())
					.setMessage("Game Over.")
					.setCancelable(false)
					.setPositiveButton("Restart", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					MainActivity.getMainActivity().clearScore();
					startGame();
				}
			}).show();
		}
	}

//	@Override
//	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//		super.onSizeChanged(w, h, oldw, oldh);
////		DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
//		int cardSize = (Math.min(w, h) - 10) / 4;       // 计算 Card 的尺寸
////		int cardSize = (displayMetrics.widthPixels - 10) / 4;
//		Log.e("==CARDSIZE==", cardSize + "");
//		addCards(cardSize);         // 把 Card 添加进来
//		startGame();        // 开始游戏
//	}

	public void startGame() {

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				cardMap[i][j].setNumber(0);     // 清空游戏界面
			}
		}
		MainActivity.getMainActivity().score = 0;       // 分数清零

		// 初始化生成 2 个卡片
		addRandomNumber();
		addRandomNumber();
	}

	private void addCards(int cardSize) {
		Card card;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				card = new Card(getContext());
				card.setNumber(0);
				addView(card, cardSize, cardSize);
				cardMap[i][j] = card;
			}
		}
	}

	private void addRandomNumber() {
		emptyPoints.clear();        // 清空空格列表
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (cardMap[i][j].getNumber() <= 0) {       // 用 0 表示空格
					emptyPoints.add(new Point(i, j));
				}
			}
		}
		Point point = emptyPoints.remove((int) (Math.random() * emptyPoints.size()));        // 随机获取空格
		cardMap[point.x][point.y].setNumber(Math.random() > 0.1 ? 2 : 4);       // 按 9:1 的概率生成 2 和 4
	}
}
