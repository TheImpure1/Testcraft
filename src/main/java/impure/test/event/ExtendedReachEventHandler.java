package impure.test.event;

import impure.test.Reference;
import impure.test.TestcraftMod;
import impure.test.utils.IExtendedReach;
import impure.test.utils.MessageExtendedReachAttack;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by essen_000 on 6/27/2015.
 */
public class ExtendedReachEventHandler {

    public static List<String> flyingPlayers = new ArrayList();

    @SideOnly(Side.CLIENT)
    @SubscribeEvent(priority= EventPriority.NORMAL, receiveCanceled=true)
    public void onEvent(MouseEvent event)
    {
        if (event.button == 0 && event.buttonstate)
        {
            Minecraft mc = Minecraft.getMinecraft();
            EntityPlayer thePlayer = mc.thePlayer;
            if (thePlayer != null)
            {
                ItemStack itemstack = thePlayer.getCurrentEquippedItem();
                IExtendedReach ieri;
                if (itemstack != null)
                {
                    if (itemstack.getItem() instanceof IExtendedReach)
                    {
                        ieri = (IExtendedReach) itemstack.getItem();
                    } else
                    {
                        ieri = null;
                    }

                    if (ieri != null)
                    {
                        float reach = ieri.getReach();
                        MovingObjectPosition mov = getMouseOverExtended(reach);

                        if (mov != null)
                        {
                            if (mov.entityHit != null && mov.entityHit.hurtResistantTime == 0)
                            {
                                if (mov.entityHit != thePlayer )
                                {
                                    TestcraftMod.network.sendToServer(new MessageExtendedReachAttack(

                                            mov.entityHit.getEntityId()));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // This is mostly copied from the EntityRenderer#getMouseOver() method
    public static MovingObjectPosition getMouseOverExtended(float dist)
    {
        Minecraft mc = FMLClientHandler.instance().getClient();
        Entity theRenderViewEntity = mc.getRenderViewEntity();
        AxisAlignedBB theViewBoundingBox = new AxisAlignedBB(
                theRenderViewEntity.posX-0.5D,
                theRenderViewEntity.posY-0.0D,
                theRenderViewEntity.posZ-0.5D,
                theRenderViewEntity.posX+0.5D,
                theRenderViewEntity.posY+1.5D,
                theRenderViewEntity.posZ+0.5D
        );
        MovingObjectPosition returnMOP = null;
        if (mc.theWorld != null)
        {
            double var2 = dist;
            returnMOP = theRenderViewEntity.rayTrace(var2, 0);
            double calcdist = var2;
            Vec3 pos = theRenderViewEntity.getPositionEyes(0);
            var2 = calcdist;
            if (returnMOP != null)
            {
                calcdist = returnMOP.hitVec.distanceTo(pos);
            }

            Vec3 lookvec = theRenderViewEntity.getLook(0);
            Vec3 var8 = pos.addVector(lookvec.xCoord * var2,

                    lookvec.yCoord * var2,

                    lookvec.zCoord * var2);
            Entity pointedEntity = null;
            float var9 = 1.0F;
            @SuppressWarnings("unchecked")
            List<Entity> list = mc.theWorld.getEntitiesWithinAABBExcludingEntity(

                    theRenderViewEntity,

                    theViewBoundingBox.addCoord(

                            lookvec.xCoord * var2,

                            lookvec.yCoord * var2,

                            lookvec.zCoord * var2).expand(var9, var9, var9));
            double d = calcdist;

            for (Entity entity : list)
            {
                if (entity.canBeCollidedWith())
                {
                    float bordersize = entity.getCollisionBorderSize();
                    AxisAlignedBB aabb = new AxisAlignedBB(

                            entity.posX-entity.width/2,

                            entity.posY,

                            entity.posZ-entity.width/2,

                            entity.posX+entity.width/2,

                            entity.posY+entity.height,

                            entity.posZ+entity.width/2);
                    aabb.expand(bordersize, bordersize, bordersize);
                    MovingObjectPosition mop0 = aabb.calculateIntercept(pos, var8);

                    if (aabb.isVecInside(pos))
                    {
                        if (0.0D < d || d == 0.0D)
                        {
                            pointedEntity = entity;
                            d = 0.0D;
                        }
                    } else if (mop0 != null)
                    {
                        double d1 = pos.distanceTo(mop0.hitVec);

                        if (d1 < d || d == 0.0D)
                        {
                            pointedEntity = entity;
                            d = d1;
                        }
                    }
                }
            }

            if (pointedEntity != null && (d < calcdist || returnMOP == null))
            {
                returnMOP = new MovingObjectPosition(pointedEntity);
            }

        }
        return returnMOP;
    }
}
