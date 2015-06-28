package impure.test.init;

import impure.test.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by essen_000 on 6/27/2015.
 */
public class TestItems {

    public static void init() {

    }

    public static void register() {


    }

    public static void registerRenders() {

    }

    public static void registerRender(Item item) {

        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(Reference.MOD_ID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
    }

    public static void registerItem(Item item) {

        GameRegistry.registerItem(item, item.getUnlocalizedName().substring(5));
    }


}
