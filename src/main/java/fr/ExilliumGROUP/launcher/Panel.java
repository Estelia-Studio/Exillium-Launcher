package fr.ExilliumGROUP.launcher;

import fr.ExilliumGROUP.launcher.utils.MicrosoftThread;
import fr.litarvan.openauth.microsoft.MicrosoftAuthResult;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticationException;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticator;
import fr.theshark34.openlauncherlib.minecraft.AuthInfos;
import fr.theshark34.openlauncherlib.util.ramselector.RamSelector;
import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;
import fr.theshark34.swinger.textured.STexturedButton;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;

import static fr.ExilliumGROUP.launcher.Frame.getBufferedImage;
import static fr.ExilliumGROUP.launcher.Frame.getImage;
import static fr.ExilliumGROUP.launcher.Launcher.authInfos;

public class Panel extends JPanel implements SwingerEventListener {

    private Image background = getImage("launcher_exi_background.png");

    //LOGIN
    private STexturedButton not_connected_background = new STexturedButton(getBufferedImage("launcher_exi_not_connected.png"), getBufferedImage("launcher_exi_not_connected.png"));
    private STexturedButton connect = new STexturedButton(getBufferedImage("launcher_exi_connect_1.png"), getBufferedImage("launcher_exi_connect_2.png"));

    //UPDATE
    private STexturedButton not_update = new STexturedButton(getBufferedImage("launcher_exi_not_update.png"), getBufferedImage("launcher_exi_not_update.png"));
    private STexturedButton update = new STexturedButton(getBufferedImage("launcher_exi_update_1.png"), getBufferedImage("launcher_exi_update_2.png"));

    //LAUNCH
    private STexturedButton launch_1 = new STexturedButton(getBufferedImage("launcher_exi_launch_1.png"), getBufferedImage("launcher_exi_launch_2.png"));
    private STexturedButton launch_2 = new STexturedButton(getBufferedImage("launcher_exi_launch_1.png"), getBufferedImage("launcher_exi_launch_2.png"));
    private STexturedButton back_1 = new STexturedButton(getBufferedImage("launcher_exi_mainmenu_back.png"), getBufferedImage("launcher_exi_mainmenu_back.png"));
    private STexturedButton back_2 = new STexturedButton(getBufferedImage("launcher_exi_mainmenu_back_final.png"), getBufferedImage("launcher_exi_mainmenu_back_final.png"));

    //MISC
    private STexturedButton discord = new STexturedButton(getBufferedImage("launcher_exi_discord.png"), getBufferedImage("launcher_exi_discord.png"));
    private STexturedButton tiktok = new STexturedButton(getBufferedImage("launcher_exi_tiktok.png"), getBufferedImage("launcher_exi_tiktok.png"));
    private STexturedButton twitter = new STexturedButton(getBufferedImage("launcher_exi_twitter.png"), getBufferedImage("launcher_exi_twitter.png"));
    private STexturedButton web = new STexturedButton(getBufferedImage("launcher_exi_web.png"), getBufferedImage("launcher_exi_web.png"));
    private STexturedButton quit = new STexturedButton(getBufferedImage("launcher_exi_close_1.png"), getBufferedImage("launcher_exi_close_2.png"));
    private STexturedButton ram = new STexturedButton(getBufferedImage("launcher_exi_settings.png"), getBufferedImage("launcher_exi_settings.png"));
    private RamSelector ramSelector = new RamSelector(Frame.getRamFile());
    private String pvp_faction_authorized;
    private String pvp_faction;
    private String discord_link;
    private String twitter_link;
    private String website_link;
    private String tiktok_link;
    public Panel() throws IOException, ParseException, MicrosoftAuthenticationException {
        String launcher_version = "1.0.0";
        String debug = "false";

        JSONParser parser = new JSONParser();
        URL url = new URL("https://exillium-pvp.fr/public/launcher.json");
        InputStream is = url.openStream();
        JSONObject jsonObject = (JSONObject) parser.parse(new InputStreamReader(is));
        pvp_faction = (String) jsonObject.get("pvp-faction");
        discord_link = (String) jsonObject.get("discord");
        twitter_link = (String) jsonObject.get("twitter");
        website_link = (String) jsonObject.get("website");
        tiktok_link = (String) jsonObject.get("tiktok");
        String version = (String) jsonObject.get("version");
        System.out.println("Version Serveur: " + version);
        System.out.println("Version CLient: " + launcher_version);

        MicrosoftAuthResult result;
        MicrosoftAuthenticator microsoftAuthenticator = new MicrosoftAuthenticator();
        final String refresh_token = Frame.getSaver().get("refresh_token");
        result = microsoftAuthenticator.loginWithRefreshToken(refresh_token);
        authInfos = new AuthInfos(result.getProfile().getName(), result.getAccessToken(), result.getProfile().getId(), result.getXuid());
        String motRecherche = result.getProfile().getId();
        String urlFichier = "https://exillium-pvp.fr/public/autorised.txt"; // Remplacez par l'URL de votre fichier

        System.out.println("UUID : " + motRecherche);

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new URL(urlFichier).openStream()))) {

            String ligne;
            while ((ligne = br.readLine()) != null) {
                if (ligne.contains(motRecherche)) {
                    System.out.println("Player authorized to connect to PVP-FACTION");
                    pvp_faction_authorized = "true";
                } else {
                    System.out.println("Player not authorized to connect to PVP-FACTION");
                    pvp_faction_authorized = "false";
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.setLayout(null);

        discord.setBounds(52, 41);
        discord.setLocation(28, 630);
        discord.addEventListener(this);
        this.add(discord);
        discord.setVisible(false);

        twitter.setBounds(48, 48);
        twitter.setLocation(28, 560);
        twitter.addEventListener(this);
        this.add(twitter);
        twitter.setVisible(false);

        tiktok.setBounds(49, 56);
        tiktok.setLocation(28, 485);
        tiktok.addEventListener(this);
        this.add(tiktok);
        tiktok.setVisible(false);

        web.setBounds(50, 50);
        web.setLocation(28, 415);
        web.addEventListener(this);
        this.add(web);
        web.setVisible(false);

        quit.setBounds(42, 41);
        quit.setLocation(1230, 5);
        quit.addEventListener(this);
        this.add(quit);

        connect.setBounds(400, 100);
        connect.setLocation(460, 437);
        connect.addEventListener(this);
        this.add(connect);
        connect.setVisible(false);

        launch_1.setBounds(200, 91);
        launch_1.setLocation(765, 507);
        launch_1.addEventListener(this);
        this.add(launch_1);
        launch_1.setVisible(false);

        launch_2.setBounds(200, 91);
        launch_2.setLocation(215, 507);
        launch_2.addEventListener(this);
        this.add(launch_2);
        launch_2.setVisible(false);

        ram.setBounds(52, 48);
        ram.setLocation(28, 85);
        ram.addEventListener(this);
        this.add(ram);
        ram.setVisible(false);

        update.setBounds(385, 113);
        update.setLocation(470, 437);
        update.addEventListener(this);
        this.add(update);
        update.setVisible(false);

        not_connected_background.setBounds(650, 400);
        not_connected_background.setLocation(340, 207);
        not_connected_background.addEventListener(this);
        this.add(not_connected_background);
        not_connected_background.setVisible(false);

        not_update.setBounds(650, 400);
        not_update.setLocation(340, 207);
        not_update.addEventListener(this);
        this.add(not_update);
        not_update.setVisible(false);

        back_1.setBounds(1280, 720);
        back_1.setLocation(0, 0);
        back_1.addEventListener(this);
        this.add(back_1);
        back_1.setVisible(false);

        back_2.setBounds(1280, 720);
        back_2.setLocation(0, 0);
        back_2.addEventListener(this);
        this.add(back_2);
        back_2.setVisible(false);

        if(refresh_token != null && !refresh_token.isEmpty()) {
            if (version.equals(launcher_version)) {
                if (pvp_faction.equals("false")) {
                    if (pvp_faction_authorized == "false") {
                        this.not_update.setVisible(false);
                        this.update.setVisible(false);
                        this.launch_1.setVisible(true);
                        this.launch_2.setVisible(false);
                        this.back_1.setVisible(true);
                        this.back_2.setVisible(false);
                        this.ram.setVisible(true);
                        this.discord.setVisible(true);
                        this.twitter.setVisible(true);
                        this.tiktok.setVisible(true);
                        this.web.setVisible(true);
                    } else if (pvp_faction_authorized == "true") {
                        this.not_update.setVisible(false);
                        this.update.setVisible(false);
                        this.launch_1.setVisible(true);
                        this.launch_2.setVisible(true);
                        this.back_1.setVisible(false);
                        this.back_2.setVisible(true);
                        this.ram.setVisible(true);
                        this.discord.setVisible(true);
                        this.twitter.setVisible(true);
                        this.tiktok.setVisible(true);
                        this.web.setVisible(true);
                    }
                } else {
                    this.not_update.setVisible(false);
                    this.update.setVisible(false);
                    this.launch_1.setVisible(true);
                    this.launch_2.setVisible(false);
                    this.back_1.setVisible(false);
                    this.back_2.setVisible(true);
                    this.ram.setVisible(true);
                    this.discord.setVisible(true);
                    this.twitter.setVisible(true);
                    this.tiktok.setVisible(true);
                    this.web.setVisible(true);
                }
            } else {
                if (debug.equals("false")) {
                        this.not_update.setVisible(true);
                        this.update.setVisible(true);
                        this.launch_1.setVisible(false);
                        this.ram.setVisible(false);
                } else {
                        this.not_update.setVisible(false);
                        this.update.setVisible(false);
                        this.launch_1.setVisible(true);
                        this.launch_2.setVisible(false);
                        this.back_1.setVisible(true);
                        this.ram.setVisible(true);
                        this.discord.setVisible(true);
                        this.twitter.setVisible(true);
                        this.tiktok.setVisible(true);
                        this.web.setVisible(true);
                }
            }
        } else {
            launch_1.setVisible(false);
            ram.setVisible(false);
            not_connected_background.setVisible(true);
            connect.setVisible(true);
        }

    }

    @Override
    public void paintComponent (Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(),this);
    }

    @Override
    public void onEvent(SwingerEvent swingerEvent) {
        if (swingerEvent.getSource() == quit) {
            System.exit(0);
        } else if (swingerEvent.getSource() == launch_1) {
            ramSelector.save();
            this.launch_1.setVisible(false);

            new Thread(() -> {
                try {
                    Launcher.update1();
                    Launcher.launch1();
                } catch (Exception e) {
                    Launcher.getReporter().catchError(e, "Impossible de lancer le jeu");
                }
            }).start();
            this.launch_1.setVisible(true);
        } else if (swingerEvent.getSource() == launch_2) {
            ramSelector.save();
            this.launch_2.setVisible(false);

            new Thread(() -> {
                try {
                    Launcher.update2();
                    Launcher.launch2();
                } catch (Exception e) {
                    Launcher.getReporter().catchError(e, "Impossible de lancer le jeu");
                }
            }).start();
            this.launch_1.setVisible(true);
        } else if (swingerEvent.getSource() == ram) {
            ramSelector.display();
        } else if (swingerEvent.getSource() == connect) {
            Thread t = new Thread(new MicrosoftThread());
            t.start();
        } else if (swingerEvent.getSource() == update) {
            Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
            if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
                try {
                    URI uri = new URI("https://exillium-pvp.fr/launcher");
                    desktop.browse(uri);
                } catch (Exception e) {
                    Launcher.getReporter().catchError(e, "Impossible d'ouvrir : https://exillium-pvp.fr/launcher");
                }
            }
        } else if (swingerEvent.getSource() == discord) {
            Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
            if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
                try {
                    URI uri = new URI(discord_link);
                    desktop.browse(uri);
                } catch (Exception e) {
                    Launcher.getReporter().catchError(e, "Impossible d'ouvrir : https://discord.gg/R6XzzjDSXC");
                }
            }
        } else if (swingerEvent.getSource() == twitter) {
            Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
            if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
                try {
                    URI uri = new URI(twitter_link);
                    desktop.browse(uri);
                } catch (Exception e) {
                    Launcher.getReporter().catchError(e, "Impossible d'ouvrir : https://twitter.com/Exillium_fr");
                }
            }
        } else if (swingerEvent.getSource() == tiktok) {
            Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
            if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
                try {
                   URI uri = new URI(tiktok_link);
                   desktop.browse(uri);
                } catch (Exception e) {
                   Launcher.getReporter().catchError(e, "Impossible d'ouvrir : https://www.tiktok.com/@exillium_official");
                }
            }
        } else if (swingerEvent.getSource() == web) {
            Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
            if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
                try {
                    URI uri = new URI(website_link);
                    desktop.browse(uri);
                } catch (Exception e) {
                    Launcher.getReporter().catchError(e, "Impossible d'ouvrir : https://exillium-pvp.fr");
                }
            }
        }
    }

    public RamSelector getRamSelector() {
        return ramSelector;
    }

}
