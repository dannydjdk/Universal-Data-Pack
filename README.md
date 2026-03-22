# Universal Data Pack

Load a global data pack from your config folder, applied to all worlds automatically.

Universal Data Pack is a lightweight utility mod for modpack developers. It creates a folder at `config/universaldatapack/` that acts as a standard Minecraft data pack — any content placed inside is applied to every world in the instance, both new and existing, without the player needing to do anything.

## Why?

Minecraft's built-in data pack system is per-world. If you're building a modpack and need to override recipes, adjust loot tables, or add advancements globally, you'd normally need to either include the data pack in every world manually or wrap it in a mod JAR. Universal Data Pack gives you a simple folder-based workflow instead — just drop your files in and go.

## How It Works

On first launch, the mod creates the following folder structure:

```
config/universaldatapack/
  pack.mcmeta
  data/
    README.txt
```

Everything inside `data/` follows standard Minecraft data pack conventions. The mod registers this folder as a required server data pack at the highest priority, so it loads last and overrides all other data packs and mods. A README file is generated inside the `data/` folder on first run with examples and usage instructions.

## Usage

**Override an existing recipe**:Mirror the target mod's namespace and path inside the `data/` folder. For example, to override an EnderIO smelting recipe:

```
config/universaldatapack/data/enderio/recipe/alloy_smelting/dark_steel.json
```

To disable a recipe entirely, use an empty JSON object as the file contents: `{}`

**Add new content**:Create your own namespace folder and add recipes, advancements, loot tables, tags, or any other data pack content:

```
config/universaldatapack/data/mymodpack/recipe/custom_sword.json
config/universaldatapack/data/mymodpack/advancement/craft_custom_sword.json
```

**Reload changes**:Changes take effect on world load. For worlds that are already open, use the `/reload` command.

## Can I use this in my modpack?

Definitely! This mod is designed to be included in modpacks. It contains no gameplay content on its own.