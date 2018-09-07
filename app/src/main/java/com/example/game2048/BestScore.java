package com.example.game2048;

import android.content.Context;
import android.content.SharedPreferences;

public class BestScore {

	SharedPreferences sharedPreferences;

	BestScore(Context context) {
		sharedPreferences = context.getSharedPreferences("best", Context.MODE_PRIVATE);
	}

	public int getBestScore() {
		int bestScore = sharedPreferences.getInt("best", 0);
		return bestScore;
	}

	public void setBestScore(int bestScore) {
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putInt("best", bestScore);
		editor.apply();
	}
}
