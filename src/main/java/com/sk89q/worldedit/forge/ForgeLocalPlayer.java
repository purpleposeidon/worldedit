package com.sk89q.worldedit.forge;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;

import com.sk89q.worldedit.LocalPlayer;
import com.sk89q.worldedit.LocalWorld;
import com.sk89q.worldedit.ServerInterface;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.WorldVector;
import com.sk89q.worldedit.bags.BlockBag;

public class ForgeLocalPlayer extends LocalPlayer {
	EntityPlayer player;

	public ForgeLocalPlayer(EntityPlayer player, ServerInterface si) {
		super(si);
		this.player = player;
	}

	@Override
	public int getItemInHand() {
		ItemStack is = player.inventory.getCurrentItem();
		if (is == null) {
			return 0;
		}
		return is.itemID;
	}

	@Override
	public String getName() {
		return player.username;
	}

	@Override
	public WorldVector getPosition() {
		return new WorldVector(getWorld(), player.posX, player.posY, player.posZ);
	}

	@Override
	public LocalWorld getWorld() {
		return new ForgeLocalWorld(player.worldObj);
	}

	@Override
	public double getPitch() {
		return player.rotationPitch;
	}

	@Override
	public double getYaw() {
		return player.rotationYaw;
	}

	@Override
	public void giveItem(int type, int amt) {
		player.inventory.addItemStackToInventory(new ItemStack(type, 0, amt));
	}

	@Override
	public void printRaw(String msg) {
		player.addChatMessage(msg);
		print(msg);
	}

	@Override
	public void printDebug(String msg) {
		//???
		print(msg);
	}

	@Override
	public void print(String msg) {
		player.addChatMessage(msg);
	}

	@Override
	public void printError(String msg) {
		print(msg); //I guess we could put a fancy color here
	}

	@Override
	public void setPosition(Vector pos, float pitch, float yaw) {
		player.posX = pos.getX();
		player.posY = pos.getY();
		player.posZ = pos.getZ();
		player.rotationPitch = pitch;
		player.rotationYaw = yaw;
		//uh, do we need a packet?
	}

	@Override
	public String[] getGroups() {
		// TODO Auto-generated method stub
		//Ha. Maybe we could return 'op' if they're an op.
		return null;
	}

	@Override
	public BlockBag getInventoryBlockBag() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasPermission(String perm) {
		return ModLoader.getMinecraftServerInstance().configManager.isOp(player.username);
	}

}
