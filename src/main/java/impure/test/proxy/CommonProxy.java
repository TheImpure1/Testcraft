package impure.test.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Created by essen_000 on 6/27/2015.
 */
public class CommonProxy implements IGuiHandler {

    public void registerRenders() {

    }

    public EntityPlayer getPlayerEntityFromContext(MessageContext ctx)
    {
        return ctx.getServerHandler().playerEntity;
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }
}
