package impure.test.items;

import impure.test.utils.IExtendedReach;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemSword;

/**
 * Created by essen_000 on 6/27/2015.
 */
public class TestExtendedSword extends ItemSword implements IExtendedReach{

    float range;

    public TestExtendedSword(ToolMaterial m, String unlocalizedName, CreativeTabs tab, float range) {
        super(m);

        this.range = range;
    }

    @Override
    public float getReach() {
        return range;
    }
}
