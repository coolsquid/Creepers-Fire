package coolsquid.creepersfire;

import java.io.File;

import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

@Mod(modid = CreepersFire.MODID, name = CreepersFire.NAME, version = CreepersFire.VERSION, acceptableRemoteVersions = "*", acceptedMinecraftVersions = "[1.11.2,1.12]", guiFactory = "coolsquid.creepersfire.ConfigGuiFactory")
public class CreepersFire {

	public static final String MODID = "creepersfire";
	public static final String NAME = "Creepers Fire";
	public static final String VERSION = "2.0.4";
	public static final String DEPENDENCIES = "required-after:forge@[14.21.1.2387,)";

	public static final Configuration CONFIG = new Configuration(new File("./config/CreepersFire.cfg"));

	private static boolean hurtOnly;
	private static int fuseTime;

	@Mod.EventHandler
	public void onPreInit(FMLPreInitializationEvent event) {
		this.loadConfig();
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onLivingAttacked(LivingAttackEvent event) {
		if (!hurtOnly && (event.getSource() == DamageSource.ON_FIRE || event.getSource() == DamageSource.IN_FIRE)) {
			if (event.getEntity() instanceof Creeper) {
				((Creeper) event.getEntity()).fuse(fuseTime);
			} else if (event.getEntity() instanceof EntityCreeper) {
				EntityCreeper creeper = (EntityCreeper) event.getEntity();
				ReflectionHelper.setPrivateValue(EntityCreeper.class, creeper, fuseTime, 5);
				creeper.ignite();
			}
		}
	}

	@SubscribeEvent
	public void onLivingAttacked(LivingHurtEvent event) {
		if (hurtOnly && (event.getSource() == DamageSource.ON_FIRE || event.getSource() == DamageSource.IN_FIRE)) {
			if (event.getEntity() instanceof Creeper) {
				((Creeper) event.getEntity()).fuse(fuseTime);
			} else if (event.getEntity() instanceof EntityCreeper) {
				EntityCreeper creeper = (EntityCreeper) event.getEntity();
				ReflectionHelper.setPrivateValue(EntityCreeper.class, creeper, fuseTime, 5);
				creeper.ignite();
			}
		}
	}

	@SubscribeEvent
	public void onConfigChanged(OnConfigChangedEvent event) {
		if (event.getModID().equals(MODID)) {
			CONFIG.save();
			this.loadConfig();
		}
	}

	private void loadConfig() {
		CONFIG.load();
		hurtOnly = CONFIG.getBoolean("hurtOnly", "general", false,
				"If true, the creepers will only ignite if the fire actually hurts them.");
		fuseTime = CONFIG.getInt("fuseTime", "general", 30, 0, 12000,
				"The time in ticks between catching fire and exploding. Set to 0 for an immediate reaction.");
		if (CONFIG.hasChanged()) {
			CONFIG.save();
		}
	}
}
