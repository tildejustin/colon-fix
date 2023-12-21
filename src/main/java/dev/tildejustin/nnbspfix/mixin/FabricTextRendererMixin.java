package dev.tildejustin.nnbspfix.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.tildejustin.nnbspfix.CharUtils;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;

@Pseudo
@Mixin(targets = "net.minecraft.class_327", remap = false)
public class FabricTextRendererMixin {
    @ModifyExpressionValue(
            method = {
                    "Lnet/minecraft/class_327;method_1724(Ljava/lang/String;FFIZ)F", // drawLayer (1.14-1.14.4)
                    "Lnet/minecraft/class_327;method_1724(Ljava/lang/String;FFIZLnet/minecraft/class_1159;Lnet/minecraft/class_4597;ZII)F" // drawLayer (1.15-1.15.2)
            },
            at = @At(value = "INVOKE", target = "Ljava/lang/String;charAt(I)C", ordinal = 0), require = 0, remap = false
    )
    private char mc114_1152(char c) {
        return CharUtils.replaceNNBSPCharWithSpace(c);
    }

    @Pseudo
    @Mixin(targets = "net.minecraft.class_327$class_5224", remap = false)
    public static class ShadowDrawerMixin {
        @ModifyVariable(method = "onChar", at = @At(value = "HEAD"), index = 3, argsOnly = true, require = 0, remap = false)
        private int mc116_1161(int c) {
            return CharUtils.replaceNNBSPCharWithSpace(c);
        }
    }

    @Pseudo
    @Mixin(targets = "net.minecraft.class_327$class_5232", remap = false)
    public static class DrawerMixin {
        @ModifyVariable(method = "accept", at = @At(value = "HEAD"), index = 3, argsOnly = true, require = 0, remap = false)
        private int mc1162_1194(int c) {
            return CharUtils.replaceNNBSPCharWithSpace(c);
        }
    }
}

//@Mixin(WorldListWidget.class)
//public class WorldListWidgetMixin {
//    @ModifyExpressionValue(method = "<clinit>", at = @At(value = "NEW", target = "()Ljava/text/SimpleDateFormat;"))
//    // proper implementation of fix (well proper would be to use the ascii alternative format of the default pattern, but I don't think java supports that)
//    // see https://unicode-org.atlassian.net/browse/CLDR-16606,
//    // but it relies on deep reflection in java.base/java.text and Java 9+ Modules are stingy with access to that
//    private static SimpleDateFormat fixDateFormat(SimpleDateFormat format) throws NoSuchFieldException, IllegalAccessException {
//        // evil reflective hack
//        if (AddOpens.open("java.base", "java.text")) {
//            // keep the current pattern intact, the way it's generated though bundles is beyond me
//            // and instead edit it in place to stay faithful to the selected locale
//            // see https://bugs.openjdk.org/browse/JDK-8304925
//            Field pattern = format.getClass().getDeclaredField("compiledPattern");
//            pattern.setAccessible(true);
//            char[] compiledPattern = (char[]) pattern.get(format);
//            for (int i = 0; i < compiledPattern.length; i++)
//                if (compiledPattern[i] == '\u202F')
//                    compiledPattern[i] = ' ';
//            pattern.set(format, compiledPattern);
//        }
//        return format;
//    }
//}

//
///**
// * A utility class that allows to open arbitrary packages to the calling module
// * at runtime, so it is a kind of dynamic device for "--add-opens" that could be
// * used inside libraries instead of forcing the application to be run with
// * command line parameters like "--add-opens java.base/java.util=ALL-UNNAMED" or
// * having the "Add-Opens:" entries supplied in the application Jar manifest.
// * Note that this still works in the Java 17 GA release, dated 2021-09-14 but it
// * may break at any time in the future (theoretically even for a minor
// * release!).
// */
//public final class AddOpens {
//
//    // field offset of the override field (Warning: this may change at any time!)
//    private static final long OVERRIDE_OFFSET = 12;
//    private static final Unsafe theUnsafe = AddOpens.getUnsafe();
//
//    /**
//     * Open one or more packages in the given module to the current module. Example
//     * usage:
//     *
//     * <pre>{@code
//     *  boolean success = AddOpens.open("java.base", "java.util", "java.net");
//     * }</pre>
//     *
//     * @param moduleName   the module you want to open
//     * @param packageNames packages in that module you want to be opened
//     * @return {@code true} if the open operation has succeeded for all packages,
//     * otherwise {@code false}
//     */
//    public static boolean open(String moduleName, String... packageNames) {
//        try {
//            // the module we are currently running in (either named or unnamed)
//            Module thisModule = AddOpens.class.getModule();
//            // find the module to open
//            Object targetModule = AddOpens.findModule(moduleName);
//            // get the method that is also used by "--add-opens"
//            Method m = Module.class.getDeclaredMethod("implAddOpens", String.class, Module.class);
//            // override language-level access checks
//            AddOpens.setAccessible(m);
//            // open given packages in the target module to this module
//            for (String package_ : packageNames) {
//                m.invoke(targetModule, package_, thisModule);
//            }
//            return true;
//        } catch (Throwable ignore) {
//            return false;
//        }
//    }
//
//    private static Object findModule(String moduleName) {
//        ModuleLayer bootLayer = ModuleLayer.boot();
//        Optional<Module> module = bootLayer.findModule(moduleName);
//        return module.orElse(null);
//    }
//
//    private static void setAccessible(Method method) {
//        if (theUnsafe != null) theUnsafe.putBoolean(method, OVERRIDE_OFFSET, true);
//    }
//
//    private static Unsafe getUnsafe() {
//        try {
//            Field unsafe = Unsafe.class.getDeclaredField("theUnsafe");
//            unsafe.setAccessible(true);
//            return (Unsafe) unsafe.get(null);
//        } catch (Throwable ignore) {
//            return null;
//        }
//    }
//}
