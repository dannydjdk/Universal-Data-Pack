package com.dannyandson.universaldatapack;

import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackSelectionConfig;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Mod(UniversalDatapack.MOD_ID)
public class UniversalDatapack {
    public static final String MOD_ID = "universaldatapack";
    private static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private final Path datapackRoot;

    public UniversalDatapack(IEventBus modEventBus) {
        this.datapackRoot = FMLPaths.CONFIGDIR.get().resolve(MOD_ID);
        createFolderStructure();
        modEventBus.addListener(this::onAddPackFinders);
    }

    private void createFolderStructure() {
        Path dataDir = datapackRoot.resolve("data");
        try {
            Files.createDirectories(dataDir);

            Path packMcmeta = datapackRoot.resolve("pack.mcmeta");
            if (!Files.exists(packMcmeta)) {
                Files.writeString(packMcmeta,
                        """
                        {
                          "pack": {
                            "description": "Universal Datapack",
                            "pack_format": 48
                          }
                        }
                        """);
            }

            Path readme = dataDir.resolve("README.txt");
            if (!Files.exists(readme)) {
                try (InputStream in = getClass().getResourceAsStream("/universaldatapack_readme.txt")) {
                    if (in != null) {
                        Files.copy(in, readme);
                    }
                }
            }
        } catch (IOException e) {
            LOGGER.error("Failed to create Universal Datapack folder structure", e);
        }
    }

    private void onAddPackFinders(AddPackFindersEvent event) {
        if (event.getPackType() != PackType.SERVER_DATA) return;

        event.addRepositorySource(consumer -> {
            PackLocationInfo location = new PackLocationInfo(
                    MOD_ID,
                    Component.literal("Universal Datapack"),
                    PackSource.DEFAULT,
                    Optional.empty()
            );

            Pack pack = Pack.readMetaAndCreate(
                    location,
                    new PathPackResources.PathResourcesSupplier(datapackRoot),
                    PackType.SERVER_DATA,
                    new PackSelectionConfig(true, Pack.Position.TOP, false)
            );

            if (pack != null) {
                consumer.accept(pack);
                LOGGER.info("Universal Datapack loaded from {}", datapackRoot);
            } else {
                LOGGER.warn("Universal Datapack could not be loaded — check pack.mcmeta in {}", datapackRoot);
            }
        });
    }
}
