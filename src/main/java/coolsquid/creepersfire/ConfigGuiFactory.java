package coolsquid.creepersfire;

import java.util.Set;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraftforge.fml.client.config.GuiConfig;

public class ConfigGuiFactory implements IModGuiFactory {

	@Override
	public void initialize(Minecraft minecraftInstance) {
	}

	@Override
	public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
		return null;
	}

	public static class Gui extends GuiConfig {

		public Gui(GuiScreen parent) {
			super(parent, new ConfigElement(CreepersFire.CONFIG.getCategory("general")).getChildElements(),
					CreepersFire.MODID, CreepersFire.MODID, false, false, CreepersFire.NAME + " configuration",
					CreepersFire.CONFIG.getConfigFile().getAbsolutePath());
		}
	}

	@Override
	public boolean hasConfigGui() {
		return true;
	}

	@Override
	public GuiScreen createConfigGui(GuiScreen parentScreen) {
		return new Gui(parentScreen);
	}
}
