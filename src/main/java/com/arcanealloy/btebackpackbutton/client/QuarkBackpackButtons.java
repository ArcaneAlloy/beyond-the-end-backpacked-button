package com.arcanealloy.btebackpackbutton.client;

import com.arcanealloy.btebackpackbutton.BteBackpackButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;

/**
 * Con los botones de Quark en la mochila (ver mixin.BackpackScreenQuarkMixin), los de ordenar y buscar de Quark se
 * colocan arriba a la derecha, justo encima de la última casilla de la primera fila, que es donde Backpacked pone sus
 * botones de personalizar la mochila y de configuración (buttonAlignment = RIGHT) y se solaparían.
 *
 * Así que, con Quark, los botones de Backpacked se pasan a la izquierda, junto al título "Backpack": la misma posición
 * que usa Backpacked con buttonAlignment = LEFT.
 */
@Mod.EventBusSubscriber(modid = BteBackpackButton.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public final class QuarkBackpackButtons {

    private QuarkBackpackButtons() {}

    private static final String BACKPACK_SCREEN = "com.mrcrayfish.backpacked.client.gui.screen.inventory.BackpackScreen";
    private static final String MINI_BUTTON = "com.mrcrayfish.backpacked.client.gui.screen.widget.MiniButton";

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onInit(ScreenEvent.Init.Post event) {
        if (!(event.getScreen() instanceof AbstractContainerScreen<?> screen)) return;
        if (!BACKPACK_SCREEN.equals(screen.getClass().getName())) return;
        if (!ModList.get().isLoaded("quark")) return;

        // Igual que BackpackScreen#init con ButtonAlignment.LEFT: detrás del título, separados 13 px
        int x = screen.getGuiLeft() + Minecraft.getInstance().font.width(screen.getTitle()) + 8 + 3;
        for (GuiEventListener listener : event.getListenersList()) {
            if (listener instanceof AbstractWidget widget && MINI_BUTTON.equals(widget.getClass().getName())) {
                widget.x = x;
                x += 13;
            }
        }
    }
}
