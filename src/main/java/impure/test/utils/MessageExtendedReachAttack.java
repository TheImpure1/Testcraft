package impure.test.utils;

import impure.test.TestcraftMod;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageExtendedReachAttack implements IMessage {

	private int entityId;

	public MessageExtendedReachAttack() {
		
	}
	
	public MessageExtendedReachAttack(int parEntityID) {
		entityId = parEntityID;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
	
		entityId = ByteBufUtils.readVarInt(buf, 4);
		
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeVarInt(buf, entityId, 4);
	}
	
	public static class Handler implements IMessageHandler<MessageExtendedReachAttack,
		IMessage> {

		@Override
        public IMessage onMessage(final MessageExtendedReachAttack message, 

              MessageContext ctx) 
        {
            // DEBUG
            System.out.println("Message received");
            // Know it will be on the server so make it thread-safe
            final EntityPlayerMP thePlayer = (EntityPlayerMP) TestcraftMod.proxy.getPlayerEntityFromContext(ctx);
            thePlayer.getServerForPlayer().addScheduledTask(
                  new Runnable()
                  {
                      @Override
                      public void run() 
                      {
                          Entity theEntity = thePlayer.worldObj.

                                getEntityByID(message.entityId);
                          // DEBUG
                          System.out.println("Entity = "+theEntity);
                          
                          // Need to ensure that hackers can't cause trick kills, 

                          // so double check weapon type and reach
                          if (thePlayer.getCurrentEquippedItem() == null)
                          {
                              return;
                          }
                          if (thePlayer.getCurrentEquippedItem().getItem() instanceof 

                                IExtendedReach)
                          {
                              IExtendedReach theExtendedReachWeapon = 

                                    (IExtendedReach)thePlayer.getCurrentEquippedItem().

                                    getItem();
                              double distanceSq = thePlayer.getDistanceSqToEntity(

                                    theEntity);
                              double reachSq =theExtendedReachWeapon.getReach()*

                                    theExtendedReachWeapon.getReach();
                              if (reachSq >= distanceSq)
                              {
                                  thePlayer.attackTargetEntityWithCurrentItem(

                                        theEntity);
                              }
                          }
                          return; // no response in this case
                      }
                }
            );
            return null; // no response message
        }
		
	}

}
