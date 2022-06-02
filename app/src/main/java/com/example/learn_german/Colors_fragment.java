package com.example.learn_german;

import androidx.appcompat.app.AppCompatActivity;
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
public class Colors_fragment extends Fragment {

    private MediaPlayer mediaPlayer;
    private AudioManager maudioManager;

    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener =
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

    public Colors_fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.word_list, container, false);
        maudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("red","rot",R.drawable.red,R.raw.rot));
        words.add(new Word("pink","rosa",R.drawable.pink,R.raw.rosa));
        words.add(new Word("blue","blau",R.drawable.dark_blue,R.raw.blau));
        words.add(new Word("light blue","hellblau",R.drawable.blue,R.raw.hellblau));
        words.add(new Word("green","grün",R.drawable.green,R.raw.grun));
        words.add(new Word("yellow","gelb",R.drawable.yellow,R.raw.gelb));
        words.add(new Word("violet","violett",R.drawable.violet,R.raw.violett));
        words.add(new Word("orange","orange",R.drawable.orange,R.raw.orange));
        words.add(new Word("brown","braun",R.drawable.brown,R.raw.braun));
        words.add(new Word("white","weiß",R.drawable.white,R.raw.wisse));
        words.add(new Word("black","schwarz",R.drawable.black,R.raw.schwarz));
        words.add(new Word("gray","grau",R.drawable.grey,R.raw.grau));
        words.add(new Word("silver","silber",R.drawable.silver,R.raw.silber));
        words.add(new Word("gold","gold",R.drawable.gold,R.raw.gold));
        WordAdapter adapter = new WordAdapter(getActivity(),words,R.color.colors_violet);
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
                    mediaPlayer = MediaPlayer.create(getActivity(),word.getmAudioResourceId());
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