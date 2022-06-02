package com.example.learn_german;

public class Word {
    private String mDefaultTranslation;
    private String mGermanTranslation;
    private int mAudioResourceId;
    private int mImgResourceId = NO_IMAGE_PROVIDED;

    private static final int NO_IMAGE_PROVIDED = -1 ;

    public Word(String DefaultTranslation, String GermanTranslation,int AudioResourceId){
        mDefaultTranslation = DefaultTranslation;
        mGermanTranslation = GermanTranslation;
        mAudioResourceId = AudioResourceId;
    }

    public Word(String DefaultTranslation, String GermanTranslation, int ImgResourceId,int AudioResourceId){
        mDefaultTranslation = DefaultTranslation;
        mGermanTranslation = GermanTranslation;
        mImgResourceId = ImgResourceId;
        mAudioResourceId = AudioResourceId;
    }

    public String getmDefaultTranslation(){
        return mDefaultTranslation;
    }
    public String getmGermanTranslation(){
        return mGermanTranslation;
    }
    public int getmImgResourceId() { return mImgResourceId; }

    public boolean hasImage(){
        return mImgResourceId != NO_IMAGE_PROVIDED;
    }

    public int getmAudioResourceId(){ return mAudioResourceId; }

}
