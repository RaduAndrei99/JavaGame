package PaooGame.Sound;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.rmi.server.ExportException;
import java.util.Arrays;

public class Sound {
    public static File stab ;

    public static File death_big_demon;
    public static File big_demon_sound1;

    public static File death_big_zombie;
    public static File zombie_sound1;
    public static File zombie_sound2;
    public static File zombie_sound3;

    public static File death_big_orc;
    public static File orc_sound1;


    public static File death_little_skeleton;

    public static File death_little_wizard;

    public static File fireBall;
    public static File fireBallExplosion;

    public static File blueSpell;
    public static File blueSpellExplosion;



    public static File sword_dash;
    public static File spike_trap;


    public static File mouse_over;

    public static void Init(){
        stab = new File("res/Sounds/stab.wav");
        death_big_demon = new File("res/Sounds/death_big_demon.wav");
        big_demon_sound1 = new File("res/Sounds/big_demon_sound1.wav");


        death_big_zombie = new File("res/Sounds/death_big_zombie.wav");
        zombie_sound1 = new File("res/Sounds/zombie_sound1.wav");
        zombie_sound1 = new File("res/Sounds/zombie_sound2.wav");
        zombie_sound1 = new File("res/Sounds/zombie_sound3.wav");


        death_big_orc = new File("res/Sounds/death_big_orc.wav");
        orc_sound1 = new File("res/Sounds/orc_sound1.wav");

        death_little_skeleton = new File("res/Sounds/death_little_skeleton.wav");
        death_little_wizard = new File("res/Sounds/dying_wizard.wav");

        mouse_over = new File("res/Sounds/mouse_over.wav");
        sword_dash = new File("res/Sounds/sword_dash.wav");
        spike_trap = new File("res/Sounds/spike.wav");

        fireBall = new File("res/Sounds/fireball.wav");
        fireBallExplosion = new File("res/Sounds/fireball_explode.wav");

        blueSpell =  new File("res/Sounds/blue_spell.wav");
        blueSpellExplosion =  new File("res/Sounds/blue_spell_explode.wav");

    }

    public static void playSound(File sound)  {

        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(sound));

            clip.start();

        }
        catch (Exception e){
            System.out.println("Error: " + Arrays.toString(e.getStackTrace()));
        }
    }

}
