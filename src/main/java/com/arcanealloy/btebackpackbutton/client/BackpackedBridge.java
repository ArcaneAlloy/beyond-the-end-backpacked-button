package com.arcanealloy.btebackpackbutton.client;

import com.mrcrayfish.backpacked.Backpacked;
import com.mrcrayfish.backpacked.client.ClientHandler;
import com.mrcrayfish.backpacked.network.Network;
import com.mrcrayfish.backpacked.network.message.MessageOpenBackpack;
import net.minecraft.client.KeyMapping;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/**
 * Todo lo que toca clases de Backpacked está aquí, para que el resto del mod no dependa de ellas directamente.
 */
final class BackpackedBridge {

    private BackpackedBridge() {}

    /** Mochila que lleva equipada el jugador (vacío si no lleva). */
    static ItemStack equippedBackpack(Player player) {
        return Backpacked.getBackpackStack(player);
    }

    /** Pide al servidor que abra la mochila, igual que la tecla de Backpacked. */
    static void openBackpack() {
        Network.getPlayChannel().sendToServer(new MessageOpenBackpack());
    }

    /** Tecla de Backpacked para abrir la mochila (para mostrarla en el tooltip). */
    static KeyMapping openKey() {
        return ClientHandler.KEY_BACKPACK;
    }
}
