package dev.tildejustin.nnbspfix.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.tildejustin.nnbspfix.CharUtils;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;

@Pseudo
// in fabric it's ToastManager or smth, but we can just let it fail :P
@Mixin(targets = "net.minecraft.class_370")
public class LegacyFabricTextRendererMixin {
    @ModifyExpressionValue(
            method = {
                    "method_957(Ljava/lang/String;FFIZ)I", /* 1.8-1.13.2 drawLayer */
                    "method_959(Ljava/lang/String;Z)V" /* 1.3-1.7.10 draw(String, bool) */
            },
            at = @At(value = "INVOKE", target = "Ljava/lang/String;charAt(I)C", ordinal = 0),
            require = 0, remap = false
    )
    private char mc13_1132(char c) {
        return CharUtils.replaceNNBSPCharWithSpace(c);
    }
}
