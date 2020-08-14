package PaooGame.Sound;

import javax.sound.sampled.*;
import java.io.File;
import java.util.Arrays;

public class Music {

    public static File menu_music;
    public static File dungeon_cave;

    static float currentVolume;
    static final float DEFAULT_VOLUME_SCALE = (float) 0.1;
    static final float DEFAULT_VOLUME = (float) 0.8;

    public static final float MIN_VOLUME = (float) 0.0;
    public static final float MAX_VOLUME = (float) 1.0;


    static Clip lastPlayedMusic;

    public static void Init(){
        menu_music = new File("res/Sounds/menu_music.wav");

        dungeon_cave = new File("res/Sounds/dungeon_cave.wav");

        currentVolume = DEFAULT_VOLUME;
    }

    public static void playSound(File sound)  {

        try {

            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(sound));

            FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float dB = (float) (Math.log(DEFAULT_VOLUME) / Math.log(10) * 20);
            volume.setValue((float)dB);

            lastPlayedMusic = clip;

            clip.start();

        }
        catch (Exception e){
            System.out.println("Error: " + Arrays.toString(e.getStackTrace()));
        }
    }

    public static void stopLastSong(){
        lastPlayedMusic.stop();
    }

    public static void getVolumeDown(){
        if(lastPlayedMusic != null) {

            if(currentVolume > MIN_VOLUME) {
                currentVolume -= DEFAULT_VOLUME_SCALE;

                FloatControl volume = (FloatControl) lastPlayedMusic.getControl(FloatControl.Type.MASTER_GAIN);
                float dB = (float) (Math.log(currentVolume) / Math.log(10) * 20);
                volume.setValue((float)dB);

                System.out.println("Music up with " + DEFAULT_VOLUME_SCALE + " - current " + currentVolume);
            }

        }
    }

    public static void getVolumeUp(){
        if(lastPlayedMusic != null) {

            if(currentVolume < MAX_VOLUME) {
                currentVolume += DEFAULT_VOLUME_SCALE;

                FloatControl volume = (FloatControl) lastPlayedMusic.getControl(FloatControl.Type.MASTER_GAIN);
                float dB = (float) (Math.log(currentVolume) / Math.log(10) * 20);
                volume.setValue((float)dB);

                System.out.println("Music down " + DEFAULT_VOLUME_SCALE + " - current " + currentVolume);
            }

        }
    }


}
