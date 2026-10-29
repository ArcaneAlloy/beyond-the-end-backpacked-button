package com.arcanealloy.btebackpackbutton.client;

import com.arcanealloy.btebackpackbutton.BteBackpackButton;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

/**
 * Botón en el inventario (a la izquierda) que abre la mochila de Backpacked.
 *  - Muestra la mochila que lleva equipada el jugador (con su aspecto) o la mochila normal si no lleva ninguna.
 *  - Sin mochila equipada el botón queda desactivado y el tooltip lo explica.
 *  - Se mueve con el inventario cuando se abre o cierra el libro de recetas.
 */
@Mod.EventBusSubscriber(modid = BteBackpackButton.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public final class BackpackInventoryButton {

    private BackpackInventoryButton() {}

    /** Posición respecto a la esquina superior izquierda del inventario (se ajustará con el resto de botones). */
    private static final int OFFSET_X = -24;
    private static final int OFFSET_Y = 56;
    private static final int SIZE = 20;

    @SubscribeEvent
    public static void onInit(ScreenEvent.Init.Post event) {
        if (!(event.getScreen() instanceof InventoryScreen screen)) return;
        event.addListener(new OpenBackpackButton(screen));
    }

    private static ItemStack defaultIcon() {
        Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation("backpacked", "backpack"));
        return item != null ? new ItemStack(item) : ItemStack.EMPTY;
    }

    private static class OpenBackpackButton extends Button {
        private final InventoryScreen screen;

        OpenBackpackButton(InventoryScreen screen) {
            super(screen.getGuiLeft() + OFFSET_X, screen.getGuiTop() + OFFSET_Y, SIZE, SIZE, Component.empty(),
                    b -> BackpackedBridge.openBackpack(),
                    (button, poseStack, mouseX, mouseY) -> screen.renderComponentTooltip(poseStack,
                            tooltip(), mouseX, mouseY));
            this.screen = screen;
        }

        private static List<Component> tooltip() {
            List<Component> lines = new ArrayList<>();
            lines.add(Component.translatable("gui.btebackpackbutton.open"));
            Player player = Minecraft.getInstance().player;
            if (player != null && BackpackedBridge.equippedBackpack(player).isEmpty()) {
                lines.add(Component.translatable("gui.btebackpackbutton.no_backpack").withStyle(ChatFormatting.RED));
            } else {
                KeyMapping key = BackpackedBridge.openKey();
                if (key != null && !key.isUnbound()) {
                    lines.add(Component.translatable("gui.btebackpackbutton.key", key.getTranslatedKeyMessage())
                            .withStyle(ChatFormatting.DARK_GRAY));
                }
            }
            return lines;
        }

        @Override
        public void renderButton(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
            // Sigue al inventario (el libro de recetas lo desplaza)
            this.x = screen.getGuiLeft() + OFFSET_X;
            this.y = screen.getGuiTop() + OFFSET_Y;

            Player player = Minecraft.getInstance().player;
            ItemStack worn = player != null ? BackpackedBridge.equippedBackpack(player) : ItemStack.EMPTY;
            // Sin mochila no se puede abrir (el tooltip lo explica)
            this.active = !worn.isEmpty();

            super.renderButton(poseStack, mouseX, mouseY, partialTick);
            ItemStack icon = worn.isEmpty() ? defaultIcon() : worn;
            if (!icon.isEmpty()) {
                Minecraft.getInstance().getItemRenderer().renderAndDecorateFakeItem(icon, this.x + 2, this.y + 2);
            }
        }
    }
}
