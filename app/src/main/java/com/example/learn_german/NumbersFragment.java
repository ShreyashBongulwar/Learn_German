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

public class NumbersFragment extends Fragment {
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

    private MediaPlayer mediaPlayer;
    private AudioManager maudioManager;


    public NumbersFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.word_list, container, false);

        maudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("one","eins", R.drawable.one,R.raw.eins));
        words.add(new Word("two","zwei",R.drawable.two,R.raw.zwei));
        words.add(new Word("three","drei",R.drawable.three,R.raw.drei));
        words.add(new Word("four","vier",R.drawable.four,R.raw.vier));
        words.add(new Word("five","fünf",R.drawable.five,R.raw.funf));
        words.add(new Word("six","sechs",R.drawable.six,R.raw.sechs));
        words.add(new Word("seven","sieben",R.drawable.seven,R.raw.sieben));
        words.add(new Word("eight","acht",R.drawable.eight,R.raw.acht));
        words.add(new Word("nine","neun",R.drawable.nine,R.raw.neun));
        words.add(new Word("ten","zehn",R.drawable.ten,R.raw.zehn));

        WordAdapter adapter = new WordAdapter(getActivity(), words, R.color.numbers_orange);
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

    private void releaseMediaplayer(){
        if(mediaPlayer != null){
            mediaPlayer.release();
            mediaPlayer = null;
            maudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaplayer();
    }
}