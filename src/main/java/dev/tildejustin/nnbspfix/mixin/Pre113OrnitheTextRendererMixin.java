package dev.tildejustin.nnbspfix.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.tildejustin.nnbspfix.CharUtils;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;

@Pseudo
// C_3831727 -> TextRenderer (ornithe 1.2-1.12.2)
@Mixin(targets = "net.minecraft.unmapped.C_3831727")
public class Pre113OrnitheTextRendererMixin {
    @ModifyExpressionValue(
            method = {
                    "m_7632532(Ljava/lang/String;FFIZ)I", /* 1.8-1.12.2 drawLayer */
                    "m_1275050(Ljava/lang/String;Z)V" /* 1.3-1.7.10 drawLayer(String, bool) */
            },
            at = @At(value = "INVOKE", target = "Ljava/lang/String;charAt(I)C", ordinal = 0),
            require = 0, remap = false
    )
    private char mc11_1122(char c) {
        return CharUtils.replaceNNBSPCharWithSpace(c);
    }
}
