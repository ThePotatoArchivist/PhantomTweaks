package archives.tater.phantomstun;

import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Set;

public class PhantomStunMixinConfig implements IMixinConfigPlugin {
    public static final String MIXIN_PACKAGE_NAME = PhantomStunMixinConfig.class.getPackageName() + ".mixin.";
    private static Config CONFIG;

    @Override
    public void onLoad(String mixinPackage) {
        try {
            CONFIG = new GsonBuilder().create().fromJson(Files.newBufferedReader(FabricLoader.getInstance().getConfigDir().resolve(PhantomStun.MOD_ID + ".json")), Config.class);
        } catch (IOException ignored) {}
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (!mixinClassName.startsWith(MIXIN_PACKAGE_NAME)) return true;
        return switch (mixinClassName.substring(MIXIN_PACKAGE_NAME.length())) {
            case "PhantomEntityStunMixin", "FindTargetGoalMixin", "PhantomMoveControlMixin", "PlayerEntityMixin" -> CONFIG.phantomStun;
            case "PhantomEntitySizeMixin" -> CONFIG.phantomSizeTweak;
            case "PhantomSpawnerMixin" -> CONFIG.phantomSpawnTweak;
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

    @SuppressWarnings("FieldMayBeFinal")
    public static class Config {
        public boolean phantomStun = true;
        public boolean phantomSizeTweak = true;
        public boolean phantomSpawnTweak = true;
    }
}
