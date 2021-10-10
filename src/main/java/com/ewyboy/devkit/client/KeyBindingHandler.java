package com.ewyboy.devkit.client;

import com.ewyboy.devkit.DevelopersToolkit;
import com.ewyboy.devkit.network.MessageHandler;
import com.ewyboy.devkit.network.messages.MessageCopyName;
import com.ewyboy.devkit.util.ModLogger;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.client.event.InputEvent.KeyInputEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.glfw.GLFW;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.Objects;

public class KeyBindingHandler {

    private static KeyBinding strip;

    public KeyBindingHandler() {}

    public static void initKeyBinding() {
        strip = new KeyBinding("Copy to Clipboard", KeyConflictContext.IN_GAME, InputMappings.Type.KEYSYM, GLFW.GLFW_KEY_C, DevelopersToolkit.MOD_ID);
        ClientRegistry.registerKeyBinding(strip);
    }

    @SubscribeEvent
    public static void onKeyInput(KeyInputEvent event) {
        ModLogger.info("TEST");
        if(strip.consumeClick()) {
            ModLogger.info("TEST 1 2 3");
            //MessageHandler.CHANNEL.sendToServer(new MessageCopyName());

            Minecraft instance = Minecraft.getInstance();

            PlayerEntity player = instance.player;

            if(instance.hitResult.getType() != RayTraceResult.Type.BLOCK){return;}

            Vector3d blockVector = instance.hitResult.getLocation();

            double bX = blockVector.x;
            double bY = blockVector.y;
            double bZ = blockVector.z;

            double pX = instance.player.getX();
            double pY = instance.player.getY();
            double pZ = instance.player.getZ();

            if(bX == Math.floor(bX) && bX <= pX){bX--;}
            if(bY == Math.floor(bY) && bY <= pY+1){bY--;} // +1 on Y to get y from player eyes instead of feet
            if(bZ == Math.floor(bZ) && bZ <= pZ){bZ--;}

            BlockState block = instance.level.getBlockState(new BlockPos(bX, bY, bZ));

            String nameCopy = Objects.requireNonNull(block.getBlock().getRegistryName()).toString();


        }
    }

}