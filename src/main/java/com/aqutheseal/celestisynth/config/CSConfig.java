package com.aqutheseal.celestisynth.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

@Mod.EventBusSubscriber
public class CSConfig {

    public static final ForgeConfigSpec COMMON_SPEC;
    public static final CSCommonConfig COMMON;
    public static final ForgeConfigSpec CLIENT_SPEC;
    public static final CSClientConfig CLIENT;

    static {
        final Pair<CSCommonConfig, ForgeConfigSpec> commonSpecPair = new ForgeConfigSpec.Builder().configure(CSCommonConfig::new);
        COMMON_SPEC = commonSpecPair.getRight();
        COMMON = commonSpecPair.getLeft();

        final Pair<CSClientConfig, ForgeConfigSpec> clientSpecPair = new ForgeConfigSpec.Builder().configure(CSClientConfig::new);
        CLIENT_SPEC = clientSpecPair.getRight();
        CLIENT = clientSpecPair.getLeft();
    }

    public static class CSCommonConfig {
        public final ForgeConfigSpec.ConfigValue<Integer> solarisDmg, crescentiaDmg;

        public final ForgeConfigSpec.ConfigValue<Double> solarisSkillDmg;
        public final ForgeConfigSpec.ConfigValue<Double> solarisShiftSkillDmg;
        public final ForgeConfigSpec.ConfigValue<Integer> solarisSkillCD;
        public final ForgeConfigSpec.ConfigValue<Integer> solarisShiftSkillCD;
        public final ForgeConfigSpec.ConfigValue<Double> crescentiaSkillDmg;
        public final ForgeConfigSpec.ConfigValue<Double> crescentiaShiftSkillDmg;
        public final ForgeConfigSpec.ConfigValue<Integer> crescentiaSkillCD;
        public final ForgeConfigSpec.ConfigValue<Integer> crescentiaShiftSkillCD;
        public final ForgeConfigSpec.ConfigValue<Double> breezebreakerSkillDmg;
        public final ForgeConfigSpec.ConfigValue<Double> breezebreakerShiftSkillDmg;
        public final ForgeConfigSpec.ConfigValue<Double> breezebreakerSprintSkillDmg;
        public final ForgeConfigSpec.ConfigValue<Double> breezebreakerMidairSkillDmg;
        public final ForgeConfigSpec.ConfigValue<Integer> breezebreakerSkillCD;
        public final ForgeConfigSpec.ConfigValue<Integer> breezebreakerShiftSkillCD;
        public final ForgeConfigSpec.ConfigValue<Integer> breezebreakerSprintSkillCD;
        public final ForgeConfigSpec.ConfigValue<Integer> breezebreakerMidairSkillCD;
        public final ForgeConfigSpec.ConfigValue<Double> poltergeistSkillDmg;
        public final ForgeConfigSpec.ConfigValue<Double> poltergeistShiftSkillDmg;
        public final ForgeConfigSpec.ConfigValue<Integer> poltergeistSkillCD;
        public final ForgeConfigSpec.ConfigValue<Integer> poltergeistShiftSkillCD;
        public final ForgeConfigSpec.ConfigValue<Double> aquafloraSkillDmg;
        public final ForgeConfigSpec.ConfigValue<Double> aquafloraShiftSkillDmg;
        public final ForgeConfigSpec.ConfigValue<Double> aquafloraBloomSkillDmg;
        public final ForgeConfigSpec.ConfigValue<Double> aquafloraBloomShiftSkillDmg;
        public final ForgeConfigSpec.ConfigValue<Integer> aquafloraSkillCD;
        public final ForgeConfigSpec.ConfigValue<Integer> aquafloraShiftSkillCD;
        public final ForgeConfigSpec.ConfigValue<Integer> aquafloraBloomSkillCD;
        public final ForgeConfigSpec.ConfigValue<Integer> aquafloraBloomShiftSkillCD;

        public final ForgeConfigSpec.ConfigValue<Double> rainfallSerenityArrowDmg;
        public final ForgeConfigSpec.ConfigValue<Double> rainfallSerenityQuasarArrowDmg;
        public final ForgeConfigSpec.ConfigValue<Double> rainfallSerenityDrawSpeed;

        protected CSCommonConfig(ForgeConfigSpec.Builder builder) {
            builder.push("Base Damage Modifications (Temporarily Unusable)");
            solarisDmg = baseDamage(builder, "solaris", 7);
            crescentiaDmg = baseDamage(builder, "crescentia", 8);
            builder.pop();

            builder.comment("Some attacks deal a specified amount of damage FOR EVERY HIT, so be careful as you tweak these values!");
            builder.comment(" ");

            builder.push("Value Modifiers - Solaris");
            solarisSkillDmg = skillDamage(builder, "solaris", "Spinning Flames - Full Round", 0.85);
            solarisShiftSkillDmg = skillDamage(builder, "solaris", "Spinning Flames - Soul Straight Dash [Shift]", 1.2);
            solarisSkillCD = skillCooldown(builder, "solaris", "Spinning Flames - Full Round", 70);
            solarisShiftSkillCD = skillCooldown(builder, "solaris", "Spinning Flames - Soul Straight Dash [Shift]", 130);
            builder.pop();

            builder.push("Value Modifiers - Crescentia");
            crescentiaSkillDmg = skillDamage(builder, "crescentia", "Lunar Celebration Barrage", 0.5);
            crescentiaShiftSkillDmg = skillDamage(builder, "crescentia", "Dragon Crescent Boom [Shift]", 0.7);
            crescentiaSkillCD = skillCooldown(builder, "crescentia", "Lunar Celebration Barrage", 100);
            crescentiaShiftSkillCD = skillCooldown(builder, "crescentia", "Dragon Crescent Boom [Shift]", 40);
            builder.pop();

            builder.push("Value Modifiers - Breezebreaker");
            breezebreakerSkillDmg = skillDamage(builder, "breezebreaker", "Galestorm + Dual Galestorm", 7.0);
            breezebreakerShiftSkillDmg = skillDamage(builder, "breezebreaker", "Full-Force Whirlwind Extravagance [Shift]", 0.85);
            breezebreakerSprintSkillDmg = skillDamage(builder, "breezebreaker", "Roar of the Wind [Sprint]", 11.5);
            breezebreakerMidairSkillDmg = skillDamage(builder, "breezebreaker", "Zephyr's Death Wheel [Mid-air]", 8.0);
            breezebreakerSkillCD = skillCooldown(builder, "breezebreaker", "Galestorm + Dual Galestorm", 15);
            breezebreakerShiftSkillCD = skillCooldown(builder, "breezebreaker", "Full-Force Whirlwind Extravagance [Shift]", 35);
            breezebreakerSprintSkillCD = skillCooldown(builder, "breezebreaker", "Roar of the Wind [Sprint]", 15);
            breezebreakerMidairSkillCD = skillCooldown(builder, "breezebreaker", "Zephyr's Death Wheel [Mid-air]", 40);
            builder.pop();

            builder.push("Value Modifiers - Poltergeist");
            poltergeistSkillDmg = skillDamage(builder, "Poltergeist", "Cosmic Steel Annihilation", 20.5);
            poltergeistShiftSkillDmg = skillDamage(builder, "Poltergeist", "Barrier Call", 10.0);
            poltergeistSkillCD = skillCooldown(builder, "Poltergeist", "Cosmic Steel Annihilation", 200);
            poltergeistShiftSkillCD = skillCooldown(builder, "Poltergeist", "Barrier Call", 10);
            builder.pop();

            builder.push("Value Modifiers - Aquaflora");
            aquafloraSkillDmg = skillDamage(builder, "Aquaflora", "Petal Pierces", 0.35);
            aquafloraShiftSkillDmg = skillDamage(builder, "Aquaflora", "Blasting Off Together", 7.0);
            aquafloraBloomSkillDmg = skillDamage(builder, "Aquaflora", "Exorbitant Slashing Frenzy", 1.15);
            aquafloraBloomShiftSkillDmg = skillDamage(builder, "Aquaflora", "Flowers Away", 0.0);
            aquafloraSkillCD = skillCooldown(builder, "Aquaflora", "Petal Pierces", 60);
            aquafloraShiftSkillCD = skillCooldown(builder, "Aquaflora", "Blasting Off Together", 40);
            aquafloraBloomSkillCD = skillCooldown(builder, "Aquaflora", "Exorbitant Slashing Frenzy", 180);
            aquafloraBloomShiftSkillCD = skillCooldown(builder, "Aquaflora", "Flowers Away", 40);
            builder.pop();

            builder.push("Value Modifiers - Rainfall Serenity");
            rainfallSerenityArrowDmg = builder.comment("Define how much damage does the Rainfall Serenity's Arrow deal.")
                    .defineInRange("Damage: Rainfall Serenity Arrow", 4.0, 0, 1000);
            rainfallSerenityQuasarArrowDmg = builder.comment("Define how much damage does the Rainfall Serenity's Arrow from Quasar Link deal.")
                    .defineInRange("Damage: Rainfall Serenity Arrow (Quasar)", 2.0, 0, 1000);
            rainfallSerenityDrawSpeed = builder.comment("Define how much damage does the Rainfall Serenity's Arrow from Quasar Link deal.")
                    .defineInRange("Extra: Rainfall Serenity Draw Speed", 7.5, 0, 1000);
            builder.pop();
        }

        public ForgeConfigSpec.ConfigValue<Double> skillDamage(ForgeConfigSpec.Builder builder, String weapon, String skillName, Double dmg) {
            return builder.comment("Define how much damage does the " + StringUtils.capitalize(weapon) + " deal in a specified attack skill.").defineInRange("Damage: " + skillName, dmg, 0, 1000);
        }

        public ForgeConfigSpec.ConfigValue<Integer> skillCooldown(ForgeConfigSpec.Builder builder, String weapon, String skillName, int cooldown) {
            return builder.comment("Define the duration of the cooldown provided by the " + StringUtils.capitalize(weapon) + " in a particular attack skill, measured in ticks.").defineInRange("Cooldown: " + skillName, cooldown, 0, 1000);
        }

        public ForgeConfigSpec.ConfigValue<Integer> baseDamage(ForgeConfigSpec.Builder builder, String weapon, int dmg) {
            return builder.comment("Define the base attack damage of the " + StringUtils.capitalize(weapon) + ".").defineInRange("Base Damage: " + StringUtils.capitalize(weapon), dmg, 0, 1000);
        }
    }

    public static class CSClientConfig {
        public final ForgeConfigSpec.ConfigValue<Boolean> visibilityOnFirstPerson;
        public final ForgeConfigSpec.ConfigValue<Boolean> showLeftArmOnAnimate;
        public final ForgeConfigSpec.ConfigValue<Boolean> showRightArmOnAnimate;

        protected CSClientConfig(ForgeConfigSpec.Builder builder) {
            builder.push("Client-side Configurations");
            visibilityOnFirstPerson = builder.comment("Should the weapon attack effects be visible on first person mode?").define("Is Visible?", true);
            showLeftArmOnAnimate = builder.comment("Defines if your left arm must be shown during the ability casting process.").define("Show Left Arm", true);
            showRightArmOnAnimate = builder.comment("Defines if your right arm must be shown during the ability casting process.").define("Show Right Arm", true);
            builder.pop();
        }
    }
}
