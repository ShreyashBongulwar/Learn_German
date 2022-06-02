package com.example.learn_german;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class Phrases_fragment extends Fragment {
    private MediaPlayer mediaPlayer;
    private AudioManager maudioManager;

    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                @Override
                public void onAudioFocusChange(int focusChange) {
                    if(focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                            focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){

                        mediaPlayer.pause();
                        mediaPlayer.seekTo(0);
                    }
                    else if(focusChange == AudioManager.AUDIOFOCUS_GAIN){
                        mediaPlayer.start();
                    }
                    else if(focusChange == AudioManager.AUDIOFOCUS_LOSS){
                        releaseMediaplayer();
                    }
                }
            };

    private MediaPlayer.OnCompletionListener mOnCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaplayer();
        }
    };

    public Phrases_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.word_list, container, false);

        maudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("Good day!","Guten Tag!",R.raw.gutentag));
        words.add(new Word("Good morning!","Guten Morgen!",R.raw.gutenmorgen));
        words.add(new Word("Good evening!","Guten Abend!",R.raw.gutenabend));
        words.add(new Word("Hello!","Hallo!",R.raw.halo));
        words.add(new Word("Hello, how are you?","Hallo, wie geht’s dir?",R.raw.halowiegehtsdir));
        words.add(new Word("Where are you from? ","Woher kommen Sie?",R.raw.woherkommensie));
        words.add(new Word("Pleasure to meet you!","Freut mich, Sie kennen zu lernen",R.raw.fruetmich));
        words.add(new Word("Excuse me please, I have a question.","Entschuldigung bitte, ich habe eine Frage.",R.raw.entschuldigungbitte));
        words.add(new Word("What time is it?","Wie viel Uhr ist es?",R.raw.wievieluhristes));
        words.add(new Word("Yes, please!","Ja, bitte!",R.raw.yabitte));
        words.add(new Word("Thank you very much","Vielen Dank",R.raw.vielendanke));
        words.add(new Word("Thank you, too","Danke dir auch",R.raw.dankedir));
        words.add(new Word("My name is ","Ich heiße",R.raw.ichheiss));
        words.add(new Word("All right","Alles klar",R.raw.allesklar));
        words.add(new Word("Never mind","Macht nichts ",R.raw.machtnicht));
        words.add(new Word("Excuse me!","Entschuldigung!",R.raw.entschuldigung));
        words.add(new Word("What is this?","Was ist das? ",R.raw.wasistdas));

        WordAdapter adapter = new WordAdapter(getActivity(),words,R.color.phrases_blue);
        ListView listView = rootView.findViewById(R.id.list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word = words.get(position);

                releaseMediaplayer();

                int result = maudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mediaPlayer = MediaPlayer.create(getActivity(), word.getmAudioResourceId());
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(mOnCompletionListener);
                }
            }
        });
        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaplayer();
    }

    private void releaseMediaplayer(){
        if(mediaPlayer != null){
            mediaPlayer.release();
            mediaPlayer = null;
            maudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }
}