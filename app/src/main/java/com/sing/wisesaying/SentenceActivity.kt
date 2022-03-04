package com.sing.wisesaying

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView

class SentenceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sentence)

        val sentenceList = mutableListOf<String>()
        sentenceList.add("The unexamined life is not worth living.\n - Socrates -")
        sentenceList.add("Youth isn't always all it's touted to be. \n - Lawana Blackwell -")
        sentenceList.add("Only a life lived for others is a life worth while. \n - H. L. Mencken -")
        sentenceList.add("Love is the triumph of imagination over intelligence. \n - Paul Tillich -")
        sentenceList.add("To love someone is to identify with them. \n - Aristotle -")
        sentenceList.add("Why be a man when you can be a success? \n - Bertolt Brecht -")
        sentenceList.add("There is no security on this earth, there is only opportunity. \n - General Douglas MacArthur -")
        sentenceList.add("If you judge people, you have no time to love them. \n - Mother Teresa -")
        sentenceList.add("Nothing is permanent in this wicked world - not even our troubles. \n - Charlie Chaplin -")
        sentenceList.add("Regret for wasted time is more wasted time. \n - Mason Cooley -")
        sentenceList.add("Try not to become a man of success but rather try to become a man of value. \n - Albert Einstein -")
        sentenceList.add("I violated the Noah rule: Predicting rain doesn't count; building arks does. \n - Warren Buffett -")
        sentenceList.add("Many an optimist has become rich by buying out a pessimist. \n - Robert G. Allen -")
        sentenceList.add("The journey is the reward. \n - Steve Jobs -")


        //ListView 생성
        // ListView
        // MainActivity --> adapter
        val sentenceadapter = ListViewAdapter(sentenceList)
        val listView = findViewById<ListView>(R.id.sentenceListView)
        
        listView.adapter = sentenceadapter 

    }
}