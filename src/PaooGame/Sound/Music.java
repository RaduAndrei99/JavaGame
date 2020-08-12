package PaooGame.Sound;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.rmi.server.ExportException;
import java.util.Arrays;

public class Music {

    public static File menu_music;
    public static File dungeaon_cave;



    static Clip lastPlayedMusic;

    public static void Init(){
        menu_music = new File("res/Sounds/menu_music.wav");

        dungeaon_cave = new File("res/Sounds/dungeon_cave.wav");
    }

    public static void playSound(File sound)  {

        try {

            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(sound));


            FloatControl volume= (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            volume.setValue(-50);

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

}
