package archives.tater.phantomstun;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EntityType;

public class PhantomStunTags {
    public static final TagKey<DamageType> MELEE_STUN_TAG = TagKey.create(Registries.DAMAGE_TYPE, Identifier.fromNamespaceAndPath(PhantomStun.MOD_ID, "melee_stun"));
    public static final TagKey<DamageType> ALWAYS_STUN_DAMAGE_TAG = TagKey.create(Registries.DAMAGE_TYPE, Identifier.fromNamespaceAndPath(PhantomStun.MOD_ID, "always_stun"));
    public static final TagKey<EntityType<?>> ALWAYS_STUN_ENTITY_TAG = TagKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(PhantomStun.MOD_ID, "always_stun"));
}
