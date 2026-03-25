package archives.tater.phantomstun;

import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.objectweb.asm.tree.ClassNode;

import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Set;

public class PhantomStunMixinConfig implements IMixinConfigPlugin {
    public static final String MIXIN_PACKAGE_NAME = PhantomStunMixinConfig.class.getPackageName() + ".mixin.";

    @Override
    public void onLoad(String mixinPackage) {
    }

    @Override
    public @Nullable String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (!mixinClassName.startsWith(MIXIN_PACKAGE_NAME)) return true;
        return switch (mixinClassName.substring(MIXIN_PACKAGE_NAME.length())) {
            case "PhantomEntityStunMixin", "FindTargetGoalMixin", "PhantomMoveControlMixin", "PlayerEntityMixin", "LivingEntityMixin" -> PhantomStun.CONFIG.phantomStun;
            case "PhantomEntitySizeMixin" -> PhantomStun.CONFIG.phantomSizeTweak;
            case "PhantomSpawnerHealthMixin" -> PhantomStun.CONFIG.phantomSpawnHealthTweak;
            case "PhantomSpawnerTimingMixin" -> PhantomStun.CONFIG.phantomSpawnTimingTweak;
            case "PhantomSpawnerCountMixin" -> PhantomStun.CONFIG.phantomSpawnCountTweak;
            default -> true;
        };
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        return List.of();
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

}
