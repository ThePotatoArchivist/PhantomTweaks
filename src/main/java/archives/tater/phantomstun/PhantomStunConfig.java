package archives.tater.phantomstun;

import folk.sisby.kaleido.api.WrappedConfig;
import folk.sisby.kaleido.lib.quiltconfig.api.annotations.ChangeWarning;
import folk.sisby.kaleido.lib.quiltconfig.api.metadata.ChangeWarning.Type;

@ChangeWarning(Type.RequiresRestart)
@SuppressWarnings("unused")
public class PhantomStunConfig extends WrappedConfig {
    public boolean phantomStun = true;
    public boolean phantomSizeTweak = true;
    public boolean phantomSpawnHealthTweak = true;
    public boolean phantomSpawnTimingTweak = true;
    public boolean phantomSpawnCountTweak = true;
}
