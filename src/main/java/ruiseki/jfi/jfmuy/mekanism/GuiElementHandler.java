package ruiseki.jfi.jfmuy.mekanism;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.gui.inventory.GuiContainer;

import codechicken.lib.vec.Rectangle4i;
import mekanism.client.gui.GuiMekanism;
import mekanism.client.gui.element.GuiElement;
import ruiseki.jfmuy.api.gui.IAdvancedGuiHandler;

public class GuiElementHandler implements IAdvancedGuiHandler {

    @Override
    public Class getGuiContainerClass() {
        return GuiMekanism.class;
    }

    @Override
    @Nullable
    public List<Rectangle> getGuiExtraAreas(GuiContainer gui) {
        if (gui instanceof GuiMekanism) {
            GuiMekanism guiMek = (GuiMekanism) gui;
            List<Rectangle> extraAreas = new ArrayList<>();

            int guiWidth = guiMek.getXPos();
            int guiHeight = guiMek.getYPos();

            for (GuiElement element : guiMek.guiElements) {
                if (element != null) {
                    Rectangle4i bounds = element.getBounds(guiWidth, guiHeight);
                    if (bounds != null) {
                        extraAreas.add(new Rectangle(bounds.x, bounds.y, bounds.w, bounds.h));
                    }
                }
            }
            return extraAreas;
        }
        return null;
    }

    @Override
    public Object getIngredientUnderMouse(GuiContainer guiContainer, int mouseX, int mouseY) {
        return null;
    }
}
