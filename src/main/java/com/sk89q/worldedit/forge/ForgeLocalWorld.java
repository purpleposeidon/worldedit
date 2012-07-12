package com.sk89q.worldedit.forge;

import net.minecraft.src.EntityItem;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

import com.sk89q.worldedit.BiomeType;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.EntityType;
import com.sk89q.worldedit.LocalWorld;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.Vector2D;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.blocks.BaseItemStack;
import com.sk89q.worldedit.regions.Region;

public class ForgeLocalWorld extends LocalWorld {
	World w;

	public ForgeLocalWorld(World w) {
		this.w = w;
	}

	@Override
	public String getName() {
		//XXX ???
		return w.toString();
	}

	@Override
	public boolean setBlockType(Vector pt, int type) {
		return w.setBlockWithNotify(pt.getBlockX(), pt.getBlockY(), pt.getBlockZ(), type);
	}

	@Override
	public int getBlockType(Vector pt) {
		return w.getBlockId(pt.getBlockX(), pt.getBlockY(), pt.getBlockZ());
	}

	@Override
	public void setBlockData(Vector pt, int data) {
		w.setBlockMetadataWithNotify(pt.getBlockX(), pt.getBlockY(), pt.getBlockZ(), data);
	}

	@Override
	public void setBlockDataFast(Vector pt, int data) {
		w.setBlockMetadata(pt.getBlockX(), pt.getBlockY(), pt.getBlockZ(), data);
	}

	@Override
	public BiomeType getBiome(Vector2D pt) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setBiome(Vector2D pt, BiomeType biome) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getBlockData(Vector pt) {
		return w.getBlockMetadata(pt.getBlockX(), pt.getBlockY(), pt.getBlockZ());
	}

	@Override
	public int getBlockLightLevel(Vector pt) {
		return w.getBlockLightValue(pt.getBlockX(), pt.getBlockY(), pt.getBlockZ());
	}

	@Override
	public boolean regenerate(Region region, EditSession editSession) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean copyToWorld(Vector pt, BaseBlock block) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean copyFromWorld(Vector pt, BaseBlock block) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean clearContainerBlockContents(Vector pt) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void dropItem(Vector pt, BaseItemStack item) {
		EntityItem ent = new EntityItem(w);
		ent.item = new ItemStack(Item.itemsList[item.getType()], item.getDamage());
	}

	@Override
	public int removeEntities(EntityType type, Vector origin, int radius) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean equals(Object other) {
		return w.equals(((ForgeLocalWorld) other).w);
	}

	@Override
	public int hashCode() {
		return w.hashCode();
	}

	@Override
	public int killMobs(Vector origin, double radius, int flags) {
		// TODO Auto-generated method stub
		return -1;
	}

}
