package dev.tildejustin.nnbspfix.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.tildejustin.nnbspfix.CharUtils;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;

@Pseudo
// C_4399780 -> TextRenderer (ornithe 1.13+)
@Mixin(targets = "net.minecraft.unmapped.C_4399780")
public class Post1122OrnitheTextRendererMixin {
    @ModifyExpressionValue(
            method = {
                    "m_7763273(Ljava/lang/String;FFIZ)I", /* 1.13-1.13.2 draw */
                    "m_4428166(Ljava/lang/String;FFIZ)F" /*1.14-1.14.4 drawLayer */
            },
            at = @At(value = "INVOKE", target = "Ljava/lang/String;charAt(I)C", ordinal = 0),
            require = 0, remap = false
    )
    private char mc113_1144(char c) {
        return CharUtils.replaceNNBSPCharWithSpace(c);
    }
}
