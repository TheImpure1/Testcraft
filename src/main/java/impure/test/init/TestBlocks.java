package impure.test.init;

import impure.test.Reference;
import impure.test.blocks.TestMultiTextureBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by essen_000 on 6/27/2015.
 */
public class TestBlocks {

    public static Block testGrass;

    public static void init() {

        //testGrass = new TestMultiTextureBlock(Material.ground);
    }

    public static void register() {

    }

    public static void registerRenders() {

    }

    public void registerRender(Block block) {

        Item item = Item.getItemFromBlock(block);
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(Reference.MOD_ID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
    }

    public void registerBlock(Block block) {

        GameRegistry.registerBlock(block, block.getUnlocalizedName().substring(5));
    }
}
