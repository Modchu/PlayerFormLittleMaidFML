package modchu.pflm;

import org.lwjgl.opengl.GL11;

import net.aetherteam.aether.client.RenderPlayerAether;
import net.aetherteam.playercore_api.cores.PlayerCoreRender;
import net.minecraft.client.entity.AbstractClientPlayer;

public class PFLMF_RenderPlayerAether extends RenderPlayerAether
{
	public static float scale;

    public PFLMF_RenderPlayerAether()
    {
        this(0, (PlayerCoreRender)null);
    }

	public PFLMF_RenderPlayerAether(int playerCoreIndex, PlayerCoreRender renderPlayer) {
		super(playerCoreIndex, renderPlayer);
	}

	protected void renderPlayerScale(AbstractClientPlayer par1AbstractClientPlayer, float par2) {
		GL11.glScalef(scale, scale, scale);
	}

}