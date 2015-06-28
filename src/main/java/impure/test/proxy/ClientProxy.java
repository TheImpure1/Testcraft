package impure.test.proxy;

import impure.test.init.TestBlocks;
import impure.test.init.TestItems;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Created by essen_000 on 6/27/2015.
 */
public class ClientProxy extends CommonProxy{

    @Override
    public void registerRenders() {

        TestBlocks.registerRenders();
        TestItems.registerRenders();
    }

    @Override
    public EntityPlayer getPlayerEntityFromContext(MessageContext ctx)
    {
        // Note that if you simply return 'Minecraft.getMinecraft().thePlayer',
        // your packets will not work because you will be getting a client
        // player even when you are on the server! Sounds absurd, but it's true.

        // Solution is to double-check side before returning the player:
        return (ctx.side.isClient() ? Minecraft.getMinecraft().thePlayer : super.getPlayerEntityFromContext(ctx));
    }
}
