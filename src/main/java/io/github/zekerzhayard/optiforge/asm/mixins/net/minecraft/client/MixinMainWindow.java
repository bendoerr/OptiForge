package io.github.zekerzhayard.optiforge.asm.mixins.net.minecraft.client;

import net.minecraft.client.MainWindow;
import net.minecraftforge.fml.loading.progress.EarlyProgressVisualization;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MainWindow.class)
public abstract class MixinMainWindow {
    @Redirect(
        method = "Lnet/minecraft/client/MainWindow;<init>(Lnet/minecraft/client/renderer/IWindowEventListener;Lnet/minecraft/client/renderer/MonitorHandler;Lnet/minecraft/client/renderer/ScreenSize;Ljava/lang/String;Ljava/lang/String;)V",
        at = @At(
            value = "INVOKE",
            target = "Lorg/lwjgl/glfw/GLFW;glfwCreateWindow(IILjava/lang/CharSequence;JJ)J",
            remap = false
        ),
        require = 1,
        allow = 1
    )
    private long redirect$_init_$0(int width, int height, CharSequence title, long monitor, long share) {
        return EarlyProgressVisualization.INSTANCE.handOffWindow(() -> width, () -> height, () -> (String) title, () -> monitor);
    }
}
