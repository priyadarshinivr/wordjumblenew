package com.example.wordjumblenew;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        EditText edtWord=findViewById(R.id.edt_word);
        EditText edtClue=findViewById(R.id.edt_clue);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String word=edtWord.getText().toString();
                String clue=edtClue.getText().toString();
                openPage2(word,clue);

            }
        });

    }
    public void openPage2(String word, String clue){
        Intent intent = new Intent(this, Page2.class );
        intent.putExtra("word",word);
        intent.putExtra("clue",clue);
        startActivity(intent);
    }

    private void startPage2(Intent intent) {
    }

    /*GridLayout gridLayout = findViewById(R.id.gridLayout);

    char[] letters = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P','Q','R','S','T','U','V','W','X','Y','Z '};

    Random random = new Random();

    for (int row = 0; row < 4; row++) {
        for (int col = 0; col < 4; col++) {

        }   TextView textView = new TextView(this);
            textView.setText(Character.toString(letters[random.nextInt(letters.length)]));

            textView.setBackgroundColor(ContextCompat.getColor(this, R.color.your_color));


            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.rowSpec = GridLayout.spec(row);
            params.columnSpec = GridLayout.spec(col);
            textView.setLayoutParams(params);*/

            /*gridLayout.addView(textView);*/
}