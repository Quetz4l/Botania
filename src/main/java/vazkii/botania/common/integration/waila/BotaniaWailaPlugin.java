package vazkii.botania.common.integration.waila;

import cpw.mods.fml.common.event.FMLInterModComms;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import vazkii.botania.api.subtile.SubTileGenerating;
import vazkii.botania.common.block.tile.TileMod;

public class BotaniaWailaPlugin {

    public static void callbackRegister(IWailaRegistrar register) {
        final IWailaDataProvider wailaProvider = new BotaniaWailaProvider();

        register.registerBodyProvider(wailaProvider, TileMod.class);
        register.registerNBTProvider(wailaProvider, TileMod.class);

        register.registerBodyProvider(wailaProvider, SubTileGenerating.class);
        register.registerNBTProvider(wailaProvider, SubTileGenerating.class);
        register.registerTailProvider(wailaProvider, SubTileGenerating.class);

    }

    public static void init() {
        FMLInterModComms.sendMessage("Waila", "register", BotaniaWailaPlugin.class.getName() + ".callbackRegister");
    }
}
