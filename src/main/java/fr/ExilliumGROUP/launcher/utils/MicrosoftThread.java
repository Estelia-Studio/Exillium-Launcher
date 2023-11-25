package fr.ExilliumGROUP.launcher.utils;

import fr.ExilliumGROUP.launcher.Launcher;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticationException;

public class MicrosoftThread implements Runnable {

    @Override
    public void run() {
        try {
            Launcher.auth();
        } catch (MicrosoftAuthenticationException e) {
            Launcher.getReporter().catchError(e, "Erreur 001 : Connection Impossible, veuillez reessayé");
        }
    }
}
