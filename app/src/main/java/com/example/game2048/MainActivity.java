package com.example.game2048;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

	int score = 0;

	int best = 0;

	private TextView scoreTV;

	private TextView bestTV;

	private Button restartBtn;

	private static MainActivity mainActivity = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		scoreTV = (TextView) findViewById(R.id.score);
		bestTV = (TextView) findViewById(R.id.best);
		restartBtn = (Button) findViewById(R.id.restart);

		scoreTV.setText("0");

		BestScore bestScore = new BestScore(this);
		best = bestScore.getBestScore();
		bestTV.setText(best + "");

		restartBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(MainActivity.this)
						.setMessage("Restart the game?")
						.setPositiveButton("OK", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								clearScore();
								GameViewLayout.getGameViewLayout().startGame();
							}
						})
						.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {}
						})
						.show();
			}
		});
	}

	public MainActivity() {
		mainActivity = this;
	}

	public static MainActivity getMainActivity() {
		return mainActivity;
	}

	public void clearScore() {
		score = 0;
		showScore();
	}

	public void showScore() {
		scoreTV.setText(score + "");
	}

	public void addScore(int s) {
		score = score + s;
		showScore();

		if (score > best) {
			best = score;
			BestScore bestScore = new BestScore(this);
			bestScore.setBestScore(best);
			bestTV.setText(best + "");
		}
	}

	public int getScore() {
		return score;
	}

	public void setBestScore(int s) {
		best = s;
		showScore();
	}
}
