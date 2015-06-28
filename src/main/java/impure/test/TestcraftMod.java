package impure.test;

import impure.test.event.ExtendedReachEventHandler;
import impure.test.proxy.CommonProxy;
import impure.test.utils.MessageExtendedReachAttack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)
public class TestcraftMod {

    public static final String NETWORK_CHANNEL_NAME = "Testcraft";
    public static SimpleNetworkWrapper network;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS,
        serverSide = Reference.SERVER_PROXY_CLASS)
    public static CommonProxy proxy;

    @Mod.Instance(Reference.MOD_ID)
    public static TestcraftMod instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {


    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

        proxy.registerRenders();

        NetworkRegistry.INSTANCE.registerGuiHandler(instance, proxy);

        TestcraftMod.network = NetworkRegistry.INSTANCE.newSimpleChannel(TestcraftMod.NETWORK_CHANNEL_NAME);

        int packetId = 0;

        TestcraftMod.network.registerMessage(MessageExtendedReachAttack.Handler.class,
                MessageExtendedReachAttack.class, packetId++, Side.SERVER);

        MinecraftForge.EVENT_BUS.register(new ExtendedReachEventHandler());
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }
}
