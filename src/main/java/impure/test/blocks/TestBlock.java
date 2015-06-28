package impure.test.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

/**
 * Created by essen_000 on 6/27/2015.
 */
public class TestBlock extends Block {

    public TestBlock(Material m, String unlocalizedName, CreativeTabs creativeTabs, float hardness, float resistance, String harvesttool, int harvestlevel) {
        super(m);

        this.setUnlocalizedName(unlocalizedName);
        this.setCreativeTab(creativeTabs);
        this.setHardness(hardness);
        this.setResistance(resistance);
        this.setHarvestLevel(harvesttool, harvestlevel);
    }
}
