package ruiseki.jfi.jfmuy.ae2;

import ruiseki.jfmuy.api.IModPlugin;
import ruiseki.jfmuy.api.IModRegistry;
import ruiseki.jfmuy.api.JFMUYPlugin;

@JFMUYPlugin
public class AE2Plugin implements IModPlugin {

    @Override
    public void register(IModRegistry registry) {
        registry.addAdvancedGuiHandlers(new AE2GuiHandler());
    }
}
