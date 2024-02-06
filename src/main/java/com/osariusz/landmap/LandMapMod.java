package com.osariusz.landmap;

import com.mojang.logging.LogUtils;
import com.osariusz.landmap.configs.ClientConfig;
import com.osariusz.landmap.configs.CommonConfig;
import com.osariusz.landmap.events.KeyBindingEvent;
import com.osariusz.landmap.events.ModEvents;
import com.osariusz.landmap.guiComponents.MapScreen;
import com.osariusz.landmap.menus.MapMenu;
import com.osariusz.landmap.networking.LogicalMapMessages;
import com.osariusz.landmap.server.ServerMapData;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(LandMapMod.MODID)
public class LandMapMod {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "landmapmod";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    // Create a Deferred Register to hold Blocks which will all be registered under the "examplemod" namespace

    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, MODID);

    public static final RegistryObject<MenuType<MapMenu>> MAP_MENU = MENUS.register("map_menu", () -> new MenuType(MapMenu::new));


    public LandMapMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, CommonConfig.SPEC,"geomap-common.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC,"geomap-client.toml");

        MENUS.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(new ModEvents());

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("[LANDMAP] Running common setup");
        LogicalMapMessages.register();
    }

    private void clientSetup(FMLClientSetupEvent event) {
        LOGGER.info("[LANDMAP] Running client setup");
        event.enqueueWork(
                () -> MinecraftForge.EVENT_BUS.register(new KeyBindingEvent())
        );
        event.enqueueWork(
                () -> MenuScreens.register(MAP_MENU.get(), MapScreen::new)
        );
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("Preparing landmap");
        ServerMapData.getInstance().prepareLogicalMap(event.getServer().getLevel(Level.OVERWORLD),new Vec3(0,0,0));
    }

}
