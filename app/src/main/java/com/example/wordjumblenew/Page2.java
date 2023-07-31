package com.example.wordjumblenew;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.gridlayout.widget.GridLayout;

import java.util.Random;

public class Page2 extends AppCompatActivity {
    private String word, clue = "";
    private TextView tvSelectedText;
    private GridLayout gridLayout;
    private ImageView ivFirstLife, ivSecondLife, ivThirdLife;
    private int noOfLives = 3;
    private int maxWordSize = 16;

    char[] letters = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z'};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page2);
        word = getIntent().getExtras().getString("word", "");
        clue = getIntent().getExtras().getString("clue", "");

        tvSelectedText = findViewById(R.id.tv_selected_letter);
        Button btnCheck = findViewById(R.id.btn_check);
        Button btnReset = findViewById(R.id.btn_reset);
        ivFirstLife = findViewById(R.id.iv_first_life);
        ivSecondLife = findViewById(R.id.iv_second_life);
        ivThirdLife = findViewById(R.id.iv_third_life);
        ImageView ivInfo = findViewById(R.id.iv_info);
        gridLayout = findViewById(R.id.gridLayout);

        tvSelectedText.setText(getDefaultSelectedText());

        btnReset.setOnClickListener(v -> onResetClicked());
        btnCheck.setOnClickListener(v -> onCheckButtonClicked());
        ivInfo.setOnClickListener(v -> {
            showClueDialog();
        });

        shuffleGrid();
    }
    private char[] getRandomCharArray() {
        Random random = new Random();
        StringBuilder str = new StringBuilder(word);
        for (int i = word.length(); i < maxWordSize; i++) {
            str.append(letters[random.nextInt(letters.length)]);
        }
        char[] randomArray = str.toString().toCharArray();
        for (int i = randomArray.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            char temp = randomArray[i];
            randomArray[i] = randomArray[j];
            randomArray[j] = temp;
        }
        return randomArray;
    }

    private String getDefaultSelectedText() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < word.length(); i++) {
            str.append("_");
        }
        return str.toString();
    }

    private void onCheckButtonClicked() {
        String guessWord = tvSelectedText.getText().toString().replace("_", "");
        if (guessWord.length() == word.length()) {
            if (guessWord.equalsIgnoreCase(word)) {
                showDialog();
            } else {
                noOfLives = noOfLives - 1;
                tvSelectedText.setText(getDefaultSelectedText());
                checkLives();
                shuffleGrid();
                if (noOfLives == 0) {
                    showDialog();
                } else {
                    Toast.makeText(this, "Wrong Guess", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Toast.makeText(this, "Please complete word.", Toast.LENGTH_SHORT).show();
        }
    }

    private void showDialog() {
        Dialog dialog = new Dialog(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.layout_game_over, null);
        ((TextView)dialogView.findViewById(R.id.tv_score)).setText("Your score: " + noOfLives * 100);
        dialogView.findViewById(R.id.btn_home).setOnClickListener(v -> {
            dialog.dismiss();
            finish();
        });
        dialogView.findViewById(R.id.btn_play_again).setOnClickListener(v -> {
            dialog.dismiss();
            onResetClicked();
            noOfLives = 3;
        });
        dialog.setContentView(dialogView);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    private void onResetClicked() {
        tvSelectedText.setText(getDefaultSelectedText());
        for (int k = 0; k < gridLayout.getChildCount(); k++) {
            gridLayout.getChildAt(k).findViewById(R.id.tv_letter).setBackgroundResource(R.drawable.bg_text_selector);
        }
    }

    private void shuffleGrid() {
        gridLayout.post(() -> {
            gridLayout.removeAllViews();
            int width = gridLayout.getWidth();
            int height = gridLayout.getHeight();
            int size = width / 4;
            if (width > height)
                size = height / 4;

            int counter = 0;
            char[] charList = getRandomCharArray();

            for (int row = 0; row < 4; row++) {
                for (int col = 0; col < 4; col++) {

                    FrameLayout frameLayout = (FrameLayout) LayoutInflater.from(this).
                            inflate(R.layout.layout_grid_text, null);
                    TextView textView = frameLayout.findViewById(R.id.tv_letter);
                    textView.setText(Character.toString(charList[counter++]));
                    textView.setOnClickListener(v -> {
                        v.setBackgroundResource(R.drawable.bg_text_selected);
                        String text = ((TextView)v).getText().toString();
                        tvSelectedText.setText(tvSelectedText.getText().toString().replaceFirst("_", text));
                    });

                    GridLayout.LayoutParams params = new GridLayout.LayoutParams(
                            new FrameLayout.LayoutParams(size, size));
                    params.setGravity(Gravity.CENTER);
                    params.rowSpec = GridLayout.spec(row);
                    params.columnSpec = GridLayout.spec(col);
                    frameLayout.setLayoutParams(params);

                    gridLayout.addView(frameLayout);
                }
            }
        });
    }

    private void checkLives() {
        if (noOfLives == 2) {
            ivThirdLife.setImageResource(R.drawable.heart_icon_2);
        } else if (noOfLives == 1) {
            ivSecondLife.setImageResource(R.drawable.heart_icon_2);
        } else {
            ivFirstLife.setImageResource(R.drawable.heart_icon_2);
        }
    }
    private void showClueDialog() {
        Dialog dialog = new Dialog(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.layout_dialog_clue, null);
        ((TextView)dialogView.findViewById(R.id.tv_clue_text)).setText(clue);
        dialogView.findViewById(R.id.btn_okay).setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(this, R.color.transparent));
        dialog.setContentView(dialogView);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

}