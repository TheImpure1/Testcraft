package impure.test.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

/**
 * Created by essen_000 on 6/27/2015.
 */
public class TestItem extends Item{

    public TestItem(String unlocalizedName, CreativeTabs creativeTabs) {

        this.setUnlocalizedName(unlocalizedName);
        this.setCreativeTab(creativeTabs);
    }
}
