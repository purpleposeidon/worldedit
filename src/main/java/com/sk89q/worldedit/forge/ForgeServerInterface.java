package com.sk89q.worldedit.forge;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.minecraft.src.EntityList;
import net.minecraft.src.Item;
import net.minecraft.src.World;
import net.minecraft.src.forge.DimensionManager;

import com.sk89q.worldedit.BiomeTypes;
import com.sk89q.worldedit.LocalWorld;
import com.sk89q.worldedit.ServerInterface;

import cpw.mods.fml.common.ReflectionHelper;

public class ForgeServerInterface extends ServerInterface {

	Map<String, Class> entityString2Class;

	public ForgeServerInterface() {
		entityString2Class = ReflectionHelper.getPrivateValue(EntityList.class, null, 0);
	}

	@Override
	public int resolveItem(String name) {
		for (Item item : Item.itemsList) {
			if (item == null) {
				continue;
			}
			String n = item.getItemName();
			if (n == null) {
				continue;
			}
			if (n.equalsIgnoreCase(name)) {
				return item.shiftedIndex;
			}
		}
		return -1;
	}

	@Override
	public boolean isValidMobType(String type) {
		return entityString2Class.containsKey(type);
	}

	@Override
	public void reload() {
		// TODO Auto-generated method stub
		mod_WorldEdit.instance.playerMap.clear();
	}

	@Override
	public BiomeTypes getBiomes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LocalWorld> getWorlds() {
		ArrayList<LocalWorld> ret = new ArrayList();
		for (World w : DimensionManager.getWorlds()) {
			ret.add(new ForgeLocalWorld(w));
		}
		return ret;
	}
}
