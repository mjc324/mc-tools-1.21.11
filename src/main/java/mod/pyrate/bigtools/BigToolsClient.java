package mod.pyrate.bigtools;

import mod.pyrate.bigtools.init.ModBlocks;
import mod.pyrate.bigtools.itemtintsources.GrassTintSource;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

@Mod(value = mod.pyrate.bigtools.BigTools.MOD_ID, dist = Dist.CLIENT)
public class BigToolsClient
{
    public static final String MOD_ID = "bigtools";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public BigToolsClient(IEventBus modBus)
    {
        LOGGER.info("Pyrate BigTools Client-Side constructor initialized.");


        LOGGER.info("Pyrate BigTools registered Client-Side ColorHandler events.");
        modBus.addListener(BigToolsClient::blockColorHandler);
        modBus.addListener(BigToolsClient::itemColorHandler);

        LOGGER.info("Pyrate BigTools Client-Side constructor completed.");

    }

    public static void blockColorHandler(RegisterColorHandlersEvent.Block event)
    {
        BlockColor pBlockColor = new BlockColor()
        {
            @Override
            public int getColor(BlockState pState, @Nullable BlockAndTintGetter pLevel, @Nullable BlockPos pPos, int pTintIndex)
            {
                return (pLevel != null && pPos != null
                        ? BiomeColors.getAverageGrassColor(pLevel, pPos)
                        : GrassColor.getDefaultColor());
            }
        };
        event.register(pBlockColor, ModBlocks.GRASS_STAIRS_BLOCK.get());
    }

    public static void itemColorHandler(RegisterColorHandlersEvent.ItemTintSources event)
    {
        event.register(Identifier.fromNamespaceAndPath(BigTools.MOD_ID, ModBlocks.GRASS_STAIRS_ID), GrassTintSource.MAP_CODEC);
    }
}
