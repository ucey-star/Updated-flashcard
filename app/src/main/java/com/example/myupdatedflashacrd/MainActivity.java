package com.example.myupdatedflashacrd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    FlashcardDatabase flashcardDatabase;

    List<Flashcard> allFlashcards;
    int currentCardDisplayedIndex = 0;

    public int getRandomNumber(int minNumber, int maxNumber) {
        Random rand = new Random();
        return rand.nextInt((maxNumber - minNumber) + 1) + minNumber;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.stuff).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.answer).setVisibility(View.VISIBLE);
                findViewById(R.id.stuff).setVisibility(View.INVISIBLE);
            }
        });

        findViewById(R.id.answer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.answer).setVisibility(View.INVISIBLE);
                findViewById(R.id.stuff).setVisibility(View.VISIBLE);
            }
        });

        findViewById(R.id.myBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, addcircleactivity.class);
                MainActivity.this.startActivityForResult(intent, 100);
            }
        });

        findViewById(R.id.nextBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentCardDisplayedIndex += 1;



                // make sure we don't get an IndexOutOfBoundsError if we are viewing the last indexed card in our list
                if(currentCardDisplayedIndex >= allFlashcards.size()) {
                    Snackbar.make(view,
                            "You've reached the end of the cards, going back to start.",
                            Snackbar.LENGTH_SHORT)
                            .show();
                    currentCardDisplayedIndex = 0;
                }

                Flashcard currentcard = allFlashcards.get(getRandomNumber(0,currentCardDisplayedIndex));
                ((TextView) findViewById(R.id.stuff)).setText(currentcard.getQuestion());
                ((TextView) findViewById(R.id.answer)).setText(currentcard.getAnswer());


            }
        });

        findViewById(R.id.deleteBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flashcardDatabase.deleteCard(((TextView) findViewById(R.id.stuff)).getText().toString());
                Snackbar.make(v,
                        "You've successfully deleted this flashcard.",
                        Snackbar.LENGTH_SHORT)
                        .show();
            }
        });

        flashcardDatabase = new FlashcardDatabase(getApplicationContext());
        allFlashcards = flashcardDatabase.getAllCards();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == 100) { // this 100 needs to match the 100 we used when we called startActivityForResult!
            String string1 = data.getExtras().getString("string1"); // 'string1' needs to match the key we used when we put the string in the Intent
            String string2 = data.getExtras().getString("string2");
            ((TextView) findViewById(R.id.stuff)).setText(string1);
            ((TextView) findViewById(R.id.answer)).setText(string2);


            flashcardDatabase.insertCard(new Flashcard(string1,string2 ));



            allFlashcards = flashcardDatabase.getAllCards();

        }




    }
}


