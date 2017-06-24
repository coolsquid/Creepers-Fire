package coolsquid.creepersfire;

import net.minecraft.entity.monster.EntityCreeper;

/**
 * Implement on custom creeper classes to make them work with Creepers Fire. Not
 * needed if the class extends {@link EntityCreeper}.
 */
public interface Creeper {

	/**
	 * Called when the creeper is fused.
	 *
	 * @param fuseTime
	 *            the time in ticks until the creeper actually explodes
	 */
	void fuse(int fuseTime);
}