package org.confluence.mod.block.crafting;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.registries.ForgeRegistries;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.item.ModItems;
import org.confluence.mod.item.common.Materials;
import org.confluence.mod.item.potion.EffectPotionItem;
import org.confluence.mod.item.potion.TerraPotions;
import org.confluence.mod.item.potion.VanillaPotionItem;
import org.confluence.mod.util.ModUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlchemyTableBlock extends BaseEntityBlock {
    private static int DEVIATIONS = 150000;
    public AlchemyTableBlock(){
        super(Properties.copy(Blocks.CRAFTING_TABLE));
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pState.getBlock() instanceof AlchemyTableBlock && pLevel.getBlockEntity(pPos) instanceof AlchemyTableBlock.Entity entity) {
            if (pPlayer.isShiftKeyDown()){
                if (entity.firstColor == 0){
                    return InteractionResult.PASS;
                }
                if (entity.craft()){
                    pPlayer.playSound(SoundEvents.FIRECHARGE_USE, 1, 1.8F);
                    return InteractionResult.PASS;
                }
                entity.craftPotion(pState, pLevel, pPos, pPlayer);

                return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
            }
            if (pPlayer.getItemInHand(pHand).getItem() instanceof PotionItem) {
                String potionId = pPlayer.getItemInHand(pHand).getOrCreateTag().getString("Potion");
                Potion effect = ForgeRegistries.POTIONS.getValue(new ResourceLocation(potionId));
                if (effect != null){
                    List<Integer> colors = new ArrayList<>();
                    for (MobEffectInstance effectInstance : effect.getEffects()) {
                        colors.add(effectInstance.getEffect().getColor());
                    }
                    if (entity.firstColor == 0){
                        entity.firstColor = calculateAverage(colors);
                        pPlayer.getItemInHand(pHand).shrink(1);
                    } else if (entity.secondColor == 0){
                        entity.secondColor = calculateAverage(colors);
                        pPlayer.getItemInHand(pHand).shrink(1);
                    }
                }
                pPlayer.playSound(SoundEvents.HONEY_DRINK, 1, 0.8F);
            } else if (pPlayer.getItemInHand(pHand).getItem() instanceof EffectPotionItem potion) {
                int color = potion.mobEffect.get().getColor();
                pPlayer.getItemInHand(pHand).shrink(1);
                if (entity.firstColor == 0){
                    entity.firstColor = color;
                    pPlayer.getItemInHand(pHand).shrink(1);
                } else if (entity.secondColor == 0){
                    entity.secondColor = color;
                    pPlayer.getItemInHand(pHand).shrink(1);
                }
                pPlayer.playSound(SoundEvents.HONEY_DRINK, 1, 0.8F);
            } else {
                ItemStack item = pPlayer.getItemInHand(pHand);
                if (item.is(Items.REDSTONE)) {
                    if (entity.isRedstone < 2 && item.getCount() >= (entity.isRedstone * (int) Math.pow(entity.isRedstone, Math.pow(entity.isRedstone, entity.isRedstone))) + 1){
                        entity.isRedstone++;
                        item.shrink(entity.isRedstone * (int) Math.pow(entity.isRedstone,
                                Math.pow(entity.isRedstone, entity.isRedstone)) + 1);
                        return InteractionResult.PASS;
                    }
                } else if (item.is(Items.GLOWSTONE_DUST)) {
                    if (entity.isGlowstone < 2 && item.getCount() >= (entity.isGlowstone * (int) Math.pow(entity.isGlowstone,
                            Math.pow(entity.isGlowstone, entity.isGlowstone))) + 1 ){
                        entity.isGlowstone++;
                        item.shrink(entity.isGlowstone * (int) Math.pow(entity.isGlowstone, Math.pow(entity.isGlowstone, entity.isGlowstone)) + 1);
                    }
                }
                if (getVault(item) != 0){
                    entity.operator = getVault(item);
                    item.shrink(1);
                }
            }
            pPlayer.sendSystemMessage(Component.literal(
                    entity.firstColor + " " + entity.secondColor + " " + entity.operator
            + " " + entity.isRedstone + " " + entity.isGlowstone));
        }

        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    public static int getVault(ItemStack item){
        if (item.is(ModItems.WATERLEAF.get())) {
            return 1;
        } else if (item.is(ModItems.MOONSHINE_GRASS.get())){
            return 2;
        } else if (item.is(ModItems.SHINE_ROOT.get())){
            return 3;
        } else if (item.is(ModItems.SHIVERINGTHORNS.get())){
            return 4;
        } else if (item.is(ModItems.SUNFLOWERS.get())){
            return 5;
        } else if (item.is(ModItems.DEATHWEED.get())){
            return 6;
        } else if (item.is(ModItems.FLAMEFLOWERS.get())){
            return 7;
        } else if (item.is(ModItems.EBONY_MUSHROOM.get()) || item.is(ModItems.GLOWING_MUSHROOM.get()) ||
                item.is(ModItems.LIFE_MUSHROOM.get()) || item.is(ModItems.TR_CRIMSON_MUSHROOM.get())){
            return 8;
        } else if (item.is(Materials.BLACK_PEARL.get())){
            return 9;
        }
        return 0;
    }

    public static int calculateAverage(List<Integer> numbers) {
        long sum = 0;
        for (int num : numbers) {
            sum += num;
        }
        return numbers.isEmpty() ? 0 : (int) (sum / numbers.size());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new AlchemyTableBlock.Entity(pPos, pState);
    }

    public static class Entity extends BlockEntity{

        public int firstColor;
        public int operator;
        public int secondColor;

        public int isRedstone;
        public int isGlowstone;


        public Entity(BlockPos pPos, BlockState pBlockState) {
            super(ModBlocks.ALCHEMY_TABLE_BLOCK_ENTITY.get(), pPos, pBlockState);
        }

        public void resetFirstColor(){ firstColor = 0; }


        @Override
        public void load(@NotNull CompoundTag nbt) {
            super.load(nbt);
            firstColor = nbt.getInt("first_color");
            operator = nbt.getInt("operator");
            secondColor = nbt.getInt("second_color");
        }

        @Override
        protected void saveAdditional(@NotNull CompoundTag nbt) {
            super.saveAdditional(nbt);
            nbt.putInt("first_color", firstColor);
            nbt.putInt("operator", operator);
            nbt.putInt("second_color", secondColor);
        }

        @Override
        public @NotNull CompoundTag getUpdateTag() {
            CompoundTag nbt = new CompoundTag();
            nbt.putInt("first_color", firstColor);
            nbt.putInt("operator", operator);
            nbt.putInt("second_color", secondColor);
            return nbt;
        }

        public void craftPotion(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer){
            List<MobEffect> effects = new ArrayList<>();
            if (pLevel instanceof ServerLevel serverLevel && pState.getBlock() instanceof AlchemyTableBlock block && pLevel.getBlockEntity(pPos) instanceof AlchemyTableBlock.Entity entity) {
                for (Object o : ForgeRegistries.MOB_EFFECTS.getValues().toArray()) {
                    if (o instanceof MobEffect effect) {
                        //get all effects
                        if (effect.getColor() - DEVIATIONS < firstColor && effect.getColor() + DEVIATIONS > firstColor) {
                            effects.add(effect);
                        }
                    }
                }
                if (effects.isEmpty()) {
                    return;
                }
                for (MobEffect effect : effects) {
                    ModUtils.testMessage(pPlayer, ForgeRegistries.MOB_EFFECTS.getKey(effects.get(pLevel.random.nextInt(effects.size()))).toString() + "   :   " + effect.getColor());
                }
                resetFirstColor();
                //todo 渲染bug
                VanillaPotionItem item = (VanillaPotionItem) TerraPotions.VANILLA_POTION.get();
                if (level != null) {
                    item.setDuration(level.random.nextInt(60 * (isRedstone + 1) , 1200 * (isRedstone + 1)));
                    item.setAmplifier(isGlowstone);
                }
                ItemStack potion = new ItemStack(item);
                isGlowstone = 0;
                isRedstone = 0;
                potion.getOrCreateTag().putString("Potion", ForgeRegistries.MOB_EFFECTS.getKey(effects.get(pLevel.random.nextInt(effects.size()))).toString());
                ModUtils.createItemEntity(potion, pPos.getCenter(), pLevel);
            }
        }

        public boolean craft() {
            if (firstColor != 0 && operator >= 6){
                int result;
                switch (operator){
                    case 6 -> result = (int) Math.sqrt(firstColor);
                    case 7 -> result = (int) Math.pow(firstColor, 2);
                    case 8 -> result = (int) Math.pow(firstColor, 3);
                    case 9 -> result = -firstColor;
                    default -> throw new RuntimeException("Invalid operator");
                }
                if (operator != 9){
                    firstColor = Math.abs(result);
                } else {
                    firstColor = result;
                }
                operator = 0;
            } else if (firstColor != 0 && secondColor != 0 && operator != 0){
                int result;
                switch (operator){
                    case 1 -> result = firstColor + secondColor;
                    case 2 -> result = firstColor - secondColor;
                    case 3 -> result = firstColor * secondColor;
                    case 4 -> result = firstColor / secondColor;
                    case 5 -> result = firstColor % secondColor;
                    default -> throw new RuntimeException("Invalid operator");
                }
                firstColor = Math.abs(result);
                operator = 0;
                secondColor = 0;
            } else {
                return false;
            }
            return true;
        }
    }
}