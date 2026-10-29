package com.arcanealloy.btebackpackbutton;

import net.minecraftforge.fml.common.Mod;

/**
 * Beyond The End - Backpacked Inventory Button.
 *
 * Añade al inventario un botón que abre la mochila de Backpacked (lo mismo que su tecla). Todo el trabajo se hace
 * en el cliente: el botón envía el propio mensaje de Backpacked para abrir la mochila, así que el servidor no
 * necesita tener este mod.
 */
@Mod(BteBackpackButton.MODID)
public class BteBackpackButton {

    public static final String MODID = "btebackpackbutton";

    public BteBackpackButton() {
        // Los eventos del cliente se registran solos (client.BackpackInventoryButton, @Mod.EventBusSubscriber)
    }
}
