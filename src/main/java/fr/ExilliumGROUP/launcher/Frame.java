package fr.ExilliumGROUP.launcher;

import fr.litarvan.openauth.microsoft.MicrosoftAuthenticationException;
import fr.theshark34.openlauncherlib.util.Saver;
import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.util.WindowMover;
import org.json.simple.parser.ParseException;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class Frame extends JFrame {

    private static Frame instance;
    private Panel panel;
    private static File ramFile = new File(String.valueOf(Launcher.getPath1()), "ram.txt");
    private static File saverFile = new File(String.valueOf(Launcher.getPath1()), "user.stock");
    private static Saver saver = new Saver(saverFile);
    public Frame() throws IOException, ParseException, MicrosoftAuthenticationException {
        instance = this;
        this.setTitle("Exillium Launcher");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(1280, 720);
        this.setUndecorated(true);
        this.setLocationRelativeTo(null);
        this.setIconImage(getImage("logo.png"));
        this.setContentPane(panel = new Panel());

        WindowMover mover = new WindowMover(this);
        this.addMouseListener(mover);
        this.addMouseMotionListener(mover);

        this.setBackground(Swinger.TRANSPARENT);

        this.setVisible(true);
    }

    public static void main(String[] args) throws IOException, ParseException, MicrosoftAuthenticationException {
        Launcher.crashFile.mkdirs();
        if (!ramFile.exists()) {
            ramFile.createNewFile();
        }
        if (!saverFile.exists()) {
            saverFile.createNewFile();
        }

        instance = new Frame();
    }


    public static Image getImage(String fichier) throws IOException {
        InputStream inputStream = Frame.getInstance().getClass().getClassLoader().getResourceAsStream(fichier);
        return ImageIO.read(inputStream);
    }

    public static BufferedImage getBufferedImage(String fichier) throws IOException {
        InputStream inputStream = Frame.getInstance().getClass().getClassLoader().getResourceAsStream(fichier);
        return ImageIO.read(inputStream);
    }

    public static Frame getInstance() {
        return instance;
    }

    public Panel getPanel() {
        return this.panel;
    }

    public static File getRamFile() {
        return ramFile;
    }
    public static Saver getSaver() {
        return saver;
    }
}
