/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 * 
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 * 
 * File Created @ [Mar 18, 2014, 8:45:25 PM (GMT)]
 */
package vazkii.botania.common.block.subtile.generating;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.subtile.RadiusDescriptor;
import vazkii.botania.api.subtile.SubTileGenerating;
import vazkii.botania.common.core.helper.ExperienceHelper;
import vazkii.botania.common.integration.waila.IBotaiaWailaProvider;
import vazkii.botania.common.lexicon.LexiconData;

public class SubTileArcaneRose extends SubTileGenerating implements IBotaiaWailaProvider {

	private static final int RANGE = 1;

	@Override
	public void onUpdate() {
		super.onUpdate();

		if(mana >= getMaxMana())
			return;

		List<EntityPlayer> players = supertile.getWorldObj().getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(supertile.xCoord - RANGE, supertile.yCoord, supertile.zCoord - RANGE, supertile.xCoord + RANGE + 1, supertile.yCoord + 1, supertile.zCoord + RANGE + 1));
		for(EntityPlayer player : players)
			if(ExperienceHelper.getPlayerXP(player) >= 1 && player.onGround) {
				ExperienceHelper.drainPlayerXP(player, 1);
				mana += 50;
				return;
			}
	}

	@Override
	public RadiusDescriptor getRadius() {
		return new RadiusDescriptor.Square(toChunkCoordinates(), RANGE);
	}

	@Override
	public int getColor() {
		return 0xFF8EF8;
	}

	@Override
	public int getMaxMana() {
		return 6000;
	}

	@Override
	public LexiconEntry getEntry() {
		return LexiconData.arcaneRose;
	}

	@Override
	public void getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
							 IWailaConfigHandler config) {
		final NBTTagCompound tag = accessor.getNBTData();
		currenttip.add(StatCollector.translateToLocal("botaniamisc.mana") + ": "
				+ EnumChatFormatting.BOLD
				+ EnumChatFormatting.AQUA
				+ tag.getInteger("mana"));

	}

	@Override
	public void getWailaNBTData(final EntityPlayerMP player, final TileEntity tile, final NBTTagCompound tag,
								final World world, int x, int y, int z) {
		tag.setInteger("mana", this.mana);

	}

}
