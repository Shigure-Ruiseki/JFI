package ruiseki.jfi;

import ruiseki.okcore.config.ConfigurableProperty;
import ruiseki.okcore.config.ConfigurableTypeCategory;
import ruiseki.okcore.config.extendedconfig.DummyConfig;
import ruiseki.okcore.tracking.Versions;

public class GeneralConfig extends DummyConfig {

    /**
     * The current mod version, will be used to check if the player's config isn't out of date and
     * warn the player accordingly.
     */
    @ConfigurableProperty(
        category = ConfigurableTypeCategory.CORE,
        comment = "Config version for " + Reference.MOD_NAME + ".\nDO NOT EDIT MANUALLY!")
    public static String version = Reference.MOD_VERSION;

    /**
     * If the version checker should be enabled.
     */
    @ConfigurableProperty(
        category = ConfigurableTypeCategory.CORE,
        comment = "If the version checker should be enabled.")
    public static boolean versionChecker = true;

    /**
     * Create a new instance.
     */
    public GeneralConfig() {
        super(JFI._instance, true, "general", null, GeneralConfig.class);
    }

    @Override
    public void onRegistered() {
        // Check version of config file
        if (!version.equals(Reference.MOD_VERSION)) System.err.println(
            "The config file of " + Reference.MOD_NAME
                + " is out of date and might cause problems, please remove it so it can be regenerated.");

        if (versionChecker) {
            Versions.registerMod(getMod(), JFI._instance, Reference.VERSION_URL);
        }
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
