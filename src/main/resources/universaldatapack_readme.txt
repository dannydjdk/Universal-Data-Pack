================================================================================
  Universal Datapack
================================================================================

This folder is your global data pack. Everything you place in this "data"
folder follows standard Minecraft data pack structure and will be applied
to ALL worlds in this Minecraft instance automatically.

FOLDER STRUCTURE
----------------
  config/universaldatapack/
    pack.mcmeta              <-- Do not delete. You can edit the description.
    data/
      <namespace>/
        recipe/              <-- Custom or overridden recipes
        loot_table/          <-- Custom or overridden loot tables
        advancement/         <-- Custom or overridden advancements
        tags/                <-- Tag overrides (items, blocks, etc.)
        function/            <-- mcfunction files
        ... and any other data pack folder type

OVERRIDING ANOTHER MOD'S RECIPES
---------------------------------
To override a recipe from another mod, mirror that mod's namespace and path.

  Example: To override EnderIO's alloy smelting recipe for dark steel:

    data/enderio/recipe/alloy_smelting/dark_steel.json

  The file you place here will replace the original. To disable a recipe
  entirely, use an empty JSON object as the file contents:

    {}

ADDING NEW CONTENT
------------------
For new recipes or other content that doesn't override anything, use your
own namespace:

  Example:

    data/mymodpack/recipe/custom_sword.json
    data/mymodpack/advancement/craft_custom_sword.json

EXAMPLE: CUSTOM RECIPE
-----------------------
File: data/mymodpack/recipe/diamond_from_coal.json

  {
    "type": "minecraft:crafting_shaped",
    "pattern": [
      "CCC",
      "CCC",
      "CCC"
    ],
    "key": {
      "C": { "item": "minecraft:coal_block" }
    },
    "result": {
      "id": "minecraft:diamond",
      "count": 1
    }
  }

EXAMPLE: ADVANCEMENT
--------------------
File: data/mymodpack/advancement/got_diamond.json

  {
    "display": {
      "icon": { "id": "minecraft:diamond" },
      "title": "Compressed Carbon",
      "description": "Craft a diamond from coal blocks",
      "frame": "task",
      "show_toast": true,
      "announce_to_chat": true
    },
    "parent": "minecraft:story/mine_diamond",
    "criteria": {
      "got_diamond": {
        "trigger": "minecraft:recipe_unlocked",
        "conditions": {
          "recipe": "mymodpack:diamond_from_coal"
        }
      }
    }
  }

NOTES
-----
- Changes take effect on world load. For existing worlds, use /reload.
- This pack loads at highest priority, so it overrides all other data packs
  and mods.
- You can safely delete this README.txt file.
