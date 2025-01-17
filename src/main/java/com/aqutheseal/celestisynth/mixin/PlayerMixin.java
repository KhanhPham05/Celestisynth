package com.aqutheseal.celestisynth.mixin;

import com.aqutheseal.celestisynth.PlayerMixinSupport;
import com.aqutheseal.celestisynth.item.helpers.CSWeapon;
import net.minecraft.client.Minecraft;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity implements PlayerMixinSupport {
    @Shadow public abstract Inventory getInventory();

    private static final EntityDataAccessor<Integer> CAMERA_ANGLE_ORDINAL = SynchedEntityData.defineId(Player.class, EntityDataSerializers.INT);
    public final String
            SCREENSHAKE_DURATION = "cs.screenShakeDuration",
            SCREENSHAKE_FADEOUTBEGIN = "cs.screenShakeFadeoutStart",
            SCREENSHAKE_INTENSITY = "cs.screenShakeIntensity"
            ;

    protected PlayerMixin(EntityType<? extends LivingEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Inject(method = "attack", at = @At(value = "HEAD"), cancellable = true)
    public void attack(Entity pTarget, CallbackInfo ci) {
        if (cancelCI(getMainHandItem()) || cancelCI(getOffhandItem())) {
            ci.cancel();
        }
    }

    public boolean cancelCI(ItemStack stack) {
        if (stack.getItem() instanceof CSWeapon) {
            return stack.getOrCreateTagElement(CSWeapon.CS_CONTROLLER_TAG_ELEMENT).getBoolean(CSWeapon.ANIMATION_BEGUN_KEY);
        }
        return false;
    }

    @Inject(method = "defineSynchedData", at = @At(value = "HEAD"))
    public void defineSynchedData(CallbackInfo ci) {
        this.entityData.define(CAMERA_ANGLE_ORDINAL, 0);
    }

    @Inject(method = "tick", at = @At(value = "TAIL"))
    public void tickTail(CallbackInfo ci) {
        if (level.isClientSide()) {
            if (getScreenShakeDuration() > 0) {
                if (getScreenShakeIntensity() > 1.0f) {
                    setScreenShakeIntensity(1.0f);
                }
                setScreenShakeDuration(getScreenShakeDuration() - 1);
                if (getScreenShakeDuration() < getScreenShakeFadeoutBegin()) {
                    setScreenShakeIntensity(Math.max(0, getScreenShakeIntensity() - 0.001F));
                }
            }
        }
        if (getMainHandItem().getItem() instanceof CSWeapon cs) {
            cs.forceTick(getMainHandItem(), level, this, getInventory().selected, getInventory().getSelected() == getMainHandItem());
        }
        if (getOffhandItem().getItem() instanceof CSWeapon cs) {
            cs.forceTick(getOffhandItem(), level, this, Inventory.SLOT_OFFHAND, getInventory().getSelected() == getOffhandItem());
        }
    }

    @OnlyIn(Dist.CLIENT)
    public int getScreenShakeDuration() {
        return getPersistentData().getInt(SCREENSHAKE_DURATION);
    }

    @OnlyIn(Dist.CLIENT)
    public void setScreenShakeDuration(int duration) {
        getPersistentData().putInt(SCREENSHAKE_DURATION, duration);
    }

    @OnlyIn(Dist.CLIENT)
    public int getScreenShakeFadeoutBegin() {
            return getPersistentData().getInt(SCREENSHAKE_FADEOUTBEGIN);
    }

    @OnlyIn(Dist.CLIENT)
    public void setScreenShakeFadeoutBegin(int beginByValue) {
        getPersistentData().putInt(SCREENSHAKE_FADEOUTBEGIN, beginByValue);
    }

    @OnlyIn(Dist.CLIENT)
    public float getScreenShakeIntensity() {
        return getPersistentData().getFloat(SCREENSHAKE_INTENSITY);
    }

    @OnlyIn(Dist.CLIENT)
    public void setScreenShakeIntensity(float intensity) {
        getPersistentData().putFloat(SCREENSHAKE_INTENSITY, intensity);
    }

    @Override
    public int getCameraAngleOrdinal() {
        return entityData.get(CAMERA_ANGLE_ORDINAL);
    }

    @Override
    public void setCameraAngleOrdinal(int ordinal) {
        entityData.set(CAMERA_ANGLE_ORDINAL, ordinal);
    }
}
