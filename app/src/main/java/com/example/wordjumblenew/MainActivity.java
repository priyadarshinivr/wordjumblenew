package com.example.wordjumblenew;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private EditText edtWord;
    private EditText edtClue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        edtWord = findViewById(R.id.edt_word);
        edtClue = findViewById(R.id.edt_clue);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String word = edtWord.getText().toString();
                String clue = edtClue.getText().toString();
                if (word.length() == 0) {
                    Toast.makeText(view.getContext(), "Please add word (Upto 16 char).", Toast.LENGTH_SHORT).show();
                } else if (clue.length() == 0) {
                    Toast.makeText(view.getContext(), "Please add clue.", Toast.LENGTH_SHORT).show();
                } else {
                    openPage2(word, clue);
                }
            }
        });
    }

    public void openPage2(String word, String clue) {
        Intent intent = new Intent(this, Page2.class);
        intent.putExtra("word", word);
        intent.putExtra("clue", clue);
        startActivity(intent);
    }
}