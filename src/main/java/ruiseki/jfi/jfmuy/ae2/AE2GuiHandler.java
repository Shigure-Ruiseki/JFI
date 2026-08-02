package ruiseki.jfi.jfmuy.ae2;

import java.awt.Rectangle;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.Nullable;

import appeng.client.gui.AEBaseGui;
import appeng.client.gui.implementations.GuiCraftConfirm;
import appeng.client.gui.implementations.GuiCraftingStatus;
import appeng.client.gui.implementations.GuiMEMonitorable;
import ruiseki.jfmuy.api.gui.IAdvancedGuiHandler;

public class AE2GuiHandler implements IAdvancedGuiHandler<AEBaseGui> {

    @Override
    public Class<AEBaseGui> getGuiContainerClass() {
        return AEBaseGui.class;
    }

    @Override
    public @Nullable List<Rectangle> getGuiExtraAreas(AEBaseGui gui) {
        List<Rectangle> extraAreas = new ArrayList<>();

        if (gui instanceof GuiMEMonitorable statusGui) {
            Boolean isViewCell = (Boolean) getPrivateField(statusGui, "viewCell");
            if (isViewCell != null && isViewCell) {
                Object[] viewCells = (Object[]) getPrivateField(statusGui, "myCurrentViewCells");
                Object container = getPrivateField(statusGui, "monitorableContainer");

                int rw = 33;
                int rh = 14 + (viewCells != null ? viewCells.length : 0) * 18;

                if (container != null) {
                    try {
                        java.lang.reflect.Method isPatternMethod = container.getClass()
                            .getMethod("isAPatternTerminal");
                        if ((Boolean) isPatternMethod.invoke(container)) {
                            rh += 21;
                        }
                    } catch (Exception ignored) {}
                }

                if (rh > 0) {
                    int rx = statusGui.guiLeft + statusGui.xSize;
                    int ry = statusGui.guiTop;
                    extraAreas.add(new Rectangle(rx, ry, rw, rh));
                }
            }
        }

        if (gui instanceof GuiCraftingStatus craftingStatusGui) {
            Object cpuTable = getPrivateField(craftingStatusGui, "cpuTable");
            if (cpuTable != null) {
                try {
                    Integer cpuTableWidth = (Integer) getPrivateField(cpuTable, "CPU_TABLE_WIDTH");
                    Integer cpuTableHeight = (Integer) getPrivateField(cpuTable, "CPU_TABLE_HEIGHT");

                    if (cpuTableWidth != null && cpuTableHeight != null) {
                        int rx = craftingStatusGui.guiLeft - cpuTableWidth;
                        int ry = craftingStatusGui.guiTop;
                        extraAreas.add(new Rectangle(rx, ry, cpuTableWidth, cpuTableHeight));
                    }
                } catch (Exception ignored) {}
            }
        }

        if (gui instanceof GuiCraftConfirm craftConfirmGui) {
            Object cpuTable = getPrivateField(craftConfirmGui, "cpuTable");
            if (cpuTable != null) {
                try {
                    Integer cpuTableWidth = (Integer) getPrivateField(cpuTable, "CPU_TABLE_WIDTH");
                    Integer cpuTableHeight = (Integer) getPrivateField(cpuTable, "CPU_TABLE_HEIGHT");

                    if (cpuTableWidth != null && cpuTableHeight != null) {
                        int rx = craftConfirmGui.guiLeft - cpuTableWidth;
                        int ry = craftConfirmGui.guiTop;
                        extraAreas.add(new Rectangle(rx, ry, cpuTableWidth, cpuTableHeight));
                    }
                } catch (Exception ignored) {}
            }

            int sideX = craftConfirmGui.guiLeft + craftConfirmGui.xSize;
            int sideY = craftConfirmGui.guiTop;
            int sideW = 22;
            int sideH = 48;
            extraAreas.add(new Rectangle(sideX, sideY, sideW, sideH));
        }

        return extraAreas.isEmpty() ? null : extraAreas;
    }

    @Nullable
    private static Object getPrivateField(Object target, String fieldName) {
        if (target == null) {
            return null;
        }
        Class<?> clazz = target.getClass();
        while (clazz != null && clazz != Object.class) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field.get(target);
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            } catch (Exception e) {
                break;
            }
        }
        return null;
    }
}
