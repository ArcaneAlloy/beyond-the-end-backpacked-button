package com.arcanealloy.btebackpackbutton.mixin;

import com.mrcrayfish.backpacked.client.gui.screen.inventory.BackpackScreen;
import org.spongepowered.asm.mixin.Mixin;
import vazkii.quark.api.IQuarkButtonAllowed;

/**
 * Activa en la mochila de Backpacked los botones de Quark que salen en los cofres: buscar/filtrar y ordenar la
 * mochila, y meter, sacar y ordenar el inventario del jugador.
 *
 * Quark solo pone esos botones en las pantallas de su lista (las de vanilla y unas pocas de otros mods) o en las que
 * implementan su interfaz IQuarkButtonAllowed. La mochila no está en la lista, así que aquí se le añade la interfaz.
 * El resto lo hace Quark: el menú de la mochila tiene más de 27 casillas, que es lo que Quark pide para aceptarlo
 * (también en el servidor), y al meter objetos respeta las casillas de la mochila (no deja meter los objetos
 * prohibidos por Backpacked).
 *
 * Solo se aplica si Quark está instalado (BteBackpackMixinPlugin).
 */
@Mixin(value = BackpackScreen.class, remap = false)
public abstract class BackpackScreenQuarkMixin implements IQuarkButtonAllowed {
}
