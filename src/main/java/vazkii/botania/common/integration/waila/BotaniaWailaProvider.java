package vazkii.botania.common.integration.waila;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import vazkii.botania.api.subtile.SubTileEntity;
import vazkii.botania.common.block.tile.TileSpecialFlower;

import java.util.List;

public class BotaniaWailaProvider implements IWailaDataProvider {

    @Override
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return null;
    }

    @Override
    public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
                                     IWailaConfigHandler config) {
        return currenttip;
    }

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
                                     IWailaConfigHandler config) {
        final TileEntity tile = accessor.getTileEntity();
        if (tile instanceof IBotaiaWailaProvider) {
            ((IBotaiaWailaProvider) tile).getWailaBody(itemStack, currenttip, accessor, config);
        } else if (tile instanceof TileSpecialFlower) {
            TileSpecialFlower tileSpecial = (TileSpecialFlower) tile;
            SubTileEntity subTile = tileSpecial.getSubTile();
            if (subTile instanceof IBotaiaWailaProvider){
                ((IBotaiaWailaProvider) subTile).getWailaBody(itemStack, currenttip, accessor, config);
            }
        }

        return currenttip;
    }

    @Override
    public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
                                     IWailaConfigHandler config) {
        return currenttip;
    }

    @Override
    public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity tile, NBTTagCompound tag, World world, int x,
                                     int y, int z) {
        if (tile instanceof IBotaiaWailaProvider) {
            ((IBotaiaWailaProvider) tile).getWailaNBTData(player, tile, tag, world, x, y, z);
        } else if (tile instanceof TileSpecialFlower) {
            TileSpecialFlower tileSpecial = (TileSpecialFlower) tile;
            SubTileEntity subTile = tileSpecial.getSubTile();
            if (subTile instanceof IBotaiaWailaProvider){
                ((IBotaiaWailaProvider) subTile).getWailaNBTData(player, tile, tag, world, x, y, z);
            }
        }

        return tag;
    }
}
