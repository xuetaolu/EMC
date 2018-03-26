package me.deftware.client.framework.wrappers.item;

import me.deftware.client.framework.wrappers.item.items.IItemArmor;
import me.deftware.client.framework.wrappers.world.IBlock;
import me.deftware.client.framework.wrappers.world.IBlockPos;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.item.*;
import net.minecraft.nbt.JsonToNBT;

public class IItemStack {

	private ItemStack stack;

	public IItemStack(ItemStack stack) {
		this.stack = stack;
	}

	public IItemStack(IBlock block) {
		stack = new ItemStack(Item.getItemFromBlock(block.getBlock()));
	}

	public IItemStack(IItem item) {
		stack = new ItemStack(item.getItem());
	}

	public IItemStack(IItem item, int amount, int metadata) {
		stack = new ItemStack(item.getItem(), amount, metadata);
	}

	public IItemStack(String name) {
		stack = new ItemStack(Item.getByNameOrId(name));
	}

	public void setNBT(String nbt) throws Exception {
		stack.setTagCompound(JsonToNBT.getTagFromJson(nbt));
	}

	public void enchantAll(int level) {
		for (Enchantment enchantment : Enchantment.REGISTRY) {
			try {
				if (enchantment != Enchantments.SILK_TOUCH && enchantment != Enchantments.BINDING_CURSE
						&& enchantment != Enchantments.VANISHING_CURSE) {
					stack.addEnchantment(enchantment, 127);
				}
			} catch (Exception ex) {
			}
		}
	}

	public void setStackDisplayName(String name) {
		stack.setStackDisplayName(name);
	}

	public static boolean validName(String name) {
		return Item.getByNameOrId(name) != null;
	}

	public ItemStack getStack() {
		return stack;
	}

	public String getDisplayName() {
		return stack.getDisplayName();
	}

	public int getItemID() {
		return Item.getIdFromItem(stack.getItem());
	}

	public float getStrVsBlock(IBlockPos pos) {
		return stack.getStrVsBlock(Minecraft.getMinecraft().world.getBlockState(pos.getPos()));
	}

	public boolean isEmpty() {
		return stack.getItem() == Item.getItemFromBlock(Blocks.AIR);
	}

	public IItem getIItem() {
		if (stack.getItem() instanceof ItemArmor) {
			return new IItemArmor(stack.getItem());
		}
		return new IItem(stack.getItem());
	}

	public int getRarity() {
		if (stack.getRarity() == EnumRarity.COMMON) {
			return 0;
		} else if (stack.getRarity() == EnumRarity.UNCOMMON) {
			return 1;
		} else if (stack.getRarity() == EnumRarity.RARE) {
			return 2;
		} else if (stack.getRarity() == EnumRarity.EPIC) {
			return 3;
		}
		return 0;
	}

	public boolean isArmor() {
		if (stack.getItem() instanceof ItemArmor) {
			return true;
		}
		return false;
	}

	public boolean isBow() {
		if (stack.getItem() instanceof ItemBow) {
			return true;
		}
		return false;
	}

}