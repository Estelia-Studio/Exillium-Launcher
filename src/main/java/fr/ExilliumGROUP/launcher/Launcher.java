package fr.ExilliumGROUP.launcher;

import fr.flowarg.flowupdater.FlowUpdater;
import fr.flowarg.flowupdater.download.json.CurseFileInfo;
import fr.flowarg.flowupdater.download.json.CurseModPackInfo;
import fr.flowarg.flowupdater.utils.ModFileDeleter;
import fr.flowarg.flowupdater.utils.UpdaterOptions;
import fr.flowarg.flowupdater.versions.AbstractForgeVersion;
import fr.flowarg.flowupdater.versions.ForgeVersionBuilder;
import fr.flowarg.flowupdater.versions.ForgeVersionType;
import fr.flowarg.flowupdater.versions.VanillaVersion;
import fr.flowarg.openlauncherlib.NoFramework;
import fr.litarvan.openauth.microsoft.MicrosoftAuthResult;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticationException;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticator;
import fr.theshark34.openlauncherlib.minecraft.AuthInfos;
import fr.theshark34.openlauncherlib.minecraft.GameFolder;
import fr.theshark34.openlauncherlib.minecraft.util.GameDirGenerator;
import fr.theshark34.openlauncherlib.util.CrashReporter;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

public class Launcher {

    //private static GameInfos gameInfos = new GameInfos("Exillium Launcher", new GameVersion("1.16.5", GameType.V1_13_HIGHER_FORGE), new GameTweak[]{GameTweak.FORGE});
    private static Path path1 = GameDirGenerator.createGameDir("Exillium_campagne", true);
    private static Path path2 = GameDirGenerator.createGameDir("Exillium_PvP_Faction", true);
    public static File crashFile = new File(String.valueOf(path1), "crashes");

    private static CrashReporter reporter = new CrashReporter(String.valueOf(crashFile), path1);
    public static AuthInfos authInfos;
    public static void auth() throws MicrosoftAuthenticationException {
        MicrosoftAuthenticator microsoftAuthenticator = new MicrosoftAuthenticator();
        final String refresh_token = Frame.getSaver().get("refresh_token");
        MicrosoftAuthResult result;
        if (refresh_token != null && !refresh_token.isEmpty()) {
            result = microsoftAuthenticator.loginWithRefreshToken(refresh_token);
            authInfos = new AuthInfos(result.getProfile().getName(), result.getAccessToken(), result.getProfile().getId(), result.getXuid());
            System.exit(0);
        } else {
            result = microsoftAuthenticator.loginWithWebview();
            Frame.getSaver().set("refresh_token", result.getRefreshToken());
            authInfos = new AuthInfos(result.getProfile().getName(), result.getAccessToken(), result.getProfile().getId());
            System.exit(0);
        }
    }

    public static void update1() throws Exception {
        VanillaVersion vanillaVersion = new VanillaVersion.VanillaVersionBuilder().withName("1.16.5").build();
        UpdaterOptions options = new UpdaterOptions.UpdaterOptionsBuilder().build();

        final CurseFileInfo curseModPackInfo = CurseFileInfo.getFilesFromJson("https://apocalyptic-survival.wstr.fr/public/launcher.json").get(0);
        AbstractForgeVersion version = new ForgeVersionBuilder(ForgeVersionType.NEW).withCurseModPack(new CurseModPackInfo(curseModPackInfo.getProjectID(), curseModPackInfo.getFileID(), true)).withForgeVersion("36.2.39").withFileDeleter(new ModFileDeleter(true)).build();

        FlowUpdater updater = new FlowUpdater.FlowUpdaterBuilder().withVanillaVersion(vanillaVersion).withUpdaterOptions(options).withModLoaderVersion(version).build();
        updater.update(path1);
    }
    public static void update2() throws Exception {
        VanillaVersion vanillaVersion = new VanillaVersion.VanillaVersionBuilder().withName("1.16.5").build();
        UpdaterOptions options = new UpdaterOptions.UpdaterOptionsBuilder().build();

        final CurseFileInfo curseModPackInfo = CurseFileInfo.getFilesFromJson("https://apocalyptic-survival.wstr.fr/public/launcher.json").get(0);
        AbstractForgeVersion version = new ForgeVersionBuilder(ForgeVersionType.NEW).withCurseModPack(new CurseModPackInfo(curseModPackInfo.getProjectID(), curseModPackInfo.getFileID(), true)).withForgeVersion("36.2.39").withFileDeleter(new ModFileDeleter(true)).build();

        FlowUpdater updater = new FlowUpdater.FlowUpdaterBuilder().withVanillaVersion(vanillaVersion).withUpdaterOptions(options).withModLoaderVersion(version).build();
        updater.update(path2);
    }

    public static void launch1() throws Exception {
        NoFramework noFramework = new NoFramework(path1, authInfos, GameFolder.FLOW_UPDATER);
        noFramework.getAdditionalVmArgs().addAll(List.of(Frame.getInstance().getPanel().getRamSelector().getRamArguments()));
        noFramework.launch("1.16.5", "36.2.39", NoFramework.ModLoader.FORGE);
    }
    public static void launch2() throws Exception {
        NoFramework noFramework = new NoFramework(path2, authInfos, GameFolder.FLOW_UPDATER);
        noFramework.getAdditionalVmArgs().addAll(List.of(Frame.getInstance().getPanel().getRamSelector().getRamArguments()));
        noFramework.launch("1.16.5", "36.2.39", NoFramework.ModLoader.FORGE);
    }
    public static CrashReporter getReporter() {
        return reporter;
    }
    public static Path getPath1() {
        return path1;
    }

    public static AuthInfos getAuthInfos() {
        return authInfos;
    }
}
