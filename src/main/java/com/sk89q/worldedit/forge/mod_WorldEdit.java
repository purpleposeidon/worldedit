package com.sk89q.worldedit.forge;

import java.util.HashMap;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.World;
import net.minecraft.src.forge.IChatHandler;
import net.minecraft.src.forge.MinecraftForge;
import net.minecraft.src.forge.NetworkMod;

import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldVector;

public class mod_WorldEdit extends NetworkMod implements IChatHandler {
	static mod_WorldEdit instance;
	WorldEdit worldEdit;
	ForgeServerInterface serverInterface;
	ForgeLocalConfiguration localConfiguration;

	int itemStart = 1600 - 256;

	public mod_WorldEdit() {
		instance = this;
	}

	@Override
	public String getVersion() {
		return "0.0 with World Edit v" + WorldEdit.getVersion();
	}

	@Override
	public void load() {
		serverInterface = new ForgeServerInterface();
		localConfiguration = new ForgeLocalConfiguration();
		localConfiguration.wandItem = itemStart + 256;
		worldEdit = new WorldEdit(serverInterface, localConfiguration);
		System.out.println(getVersion());
		MinecraftForge.registerChatHandler(this);

		for (int i = itemStart; i < itemStart + 16; i++) {
			Item it = new WeTool(i);
		}

		ModLoader.setInGameHook(this, true, false);
	}

	@Override
	public String onServerChat(EntityPlayer player, String message) {
		return message;
	}

	@Override
	public boolean onChatCommand(EntityPlayer player, boolean isOp, String command) {
		return false;
	}

	HashMap<String, ForgeLocalPlayer> playerMap = new HashMap();

	ForgeLocalPlayer getPlayer(String username) {
		if (!playerMap.containsKey(username)) {
			EntityPlayer player = ModLoader.getMinecraftServerInstance().configManager.getPlayerEntity(username);
			System.out.println("WorldEdit: Added player " + username);
			ForgeLocalPlayer ret = new ForgeLocalPlayer(player, serverInterface);
			playerMap.put(username, ret);
			return ret;
		}
		return playerMap.get(username);
	}

	@Override
	public boolean onServerCommand(Object listener, String username, String command) {
		String split[] = ("/" + command).split(" ");
		return worldEdit.handleCommand(getPlayer(username), split);
	}

	@Override
	public String onServerCommandSay(Object listener, String username, String message) {
		return message;
	}

	@Override
	public String onClientChatRecv(String message) {
		return message;
	}

	class WeTool extends Item {
		protected WeTool(int par1) {
			super(par1);
		}

		@Override
		public String getItemName() {
			return "World Edit Tool #" + (this.shiftedIndex - itemStart);
		}

		@Override
		public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int X,
				int Y, int Z, int side) {
			WorldVector v = new WorldVector(new ForgeLocalWorld(world), X, Y, Z);
			if (player.isSneaking()) {
				worldEdit.handleBlockLeftClick(getPlayer(player.username), v);
			}
			else {
				worldEdit.handleBlockRightClick(getPlayer(player.username), v);
			}
			return true;
		}

		@Override
		public boolean onBlockStartBreak(ItemStack itemstack, int X, int Y, int Z,
				EntityPlayer player) {
			WorldVector clicked = new WorldVector(new ForgeLocalWorld(player.worldObj), X, Y, Z);
			worldEdit.handleBlockLeftClick(getPlayer(player.username), clicked);
			return true;
		}
	}

	@Override
	public boolean onTickInGame(MinecraftServer minecraftServer) {
		for (EntityPlayer player : (Iterable<EntityPlayer>) minecraftServer.configManager.playerEntities) {
			if (player.swingProgressInt == 1) {
				worldEdit.handleArmSwing(getPlayer(player.username));
			}
		}
		return true;
	}
}
