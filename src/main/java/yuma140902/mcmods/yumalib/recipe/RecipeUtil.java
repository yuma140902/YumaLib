package yuma140902.mcmods.yumalib.recipe;

import java.util.ArrayList;
import java.util.List;
import cpw.mods.fml.common.registry.FMLControlledNamespacedRegistry;
import cpw.mods.fml.common.registry.GameData;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import yuma140902.mcmods.yumalib.util.ListUtils;

public class RecipeUtil {
	
	/**
	 * Remove registered recipes by internal name of output recipe
	 * @param outputNameList
	 */
	@SuppressWarnings("unchecked")
	public static void removeRecipesByOutputName(List<String> outputNameList) {
		List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();
		List<IRecipe> removeList = new ArrayList<IRecipe>();
		FMLControlledNamespacedRegistry<Item> itemRegistry = GameData.getItemRegistry();
		
		for(IRecipe recipe : recipes) {
			//see: http://forum.minecraftuser.jp/viewtopic.php?f=39&t=33757
			if(recipe != null && recipe.getRecipeOutput() != null && recipe.getRecipeOutput().getItem() != null) {
				Item outputItem = recipe.getRecipeOutput().getItem();
				String name = itemRegistry.getNameForObject(outputItem);
				
				if(ListUtils.contains(outputNameList, name)) {
					removeList.add(recipe);

					outputNameList.remove(name);
				}
			}
		}
		
		for(IRecipe removeRecipe : removeList) {
			recipes.remove(removeRecipe);
		}
	}
}
