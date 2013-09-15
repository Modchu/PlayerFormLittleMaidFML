package net.minecraft.src;

import modchu.pflm.PFLMF_RenderPlayerAether;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

import org.lwjgl.opengl.GL11;

public class PFLMF_Aether {

	public static PFLMF_RenderPlayerAether renderPlayerAether;
	public static RenderPlayer render_PlayerCoreRender;
	public static PFLMF_ModelDummy dummyModel = new PFLMF_ModelDummy();
	private static Class RenderPlayerAether;

	public static void init() {
		RenderPlayerAether = Modchu_Reflect.loadClass("net.aetherteam.aether.client.RenderPlayerAether");
/*
		Class PlayerCoreType = Modchu_Reflect.loadClass("net.aetherteam.playercore_api.PlayerCoreAPI$PlayerCoreType");
		Object RENDER = PlayerCoreType != null ? Modchu_Reflect.getFieldObject(PlayerCoreType, "RENDER") : null;
		if (PlayerCoreType != null
				&& RENDER != null) {
			PFLMF.Debug("PFLMF_Aether PlayerCoreAPI setting Check ok.");
			Class PFLM_RenderPlayerAether = PFLMF_DummyCoreRender.class;
			Modchu_Reflect.invokeMethod("net.aetherteam.playercore_api.PlayerCoreAPI", "register", new Class[]{ PlayerCoreType, Class.class }, new Object[]{ RENDER, PFLM_RenderPlayerAether });
			//net.aetherteam.playercore_api.PlayerCoreAPI.register((net.aetherteam.playercore_api.PlayerCoreAPI$PlayerCoreType) RENDER, PFLM_RenderPlayerAether);
			//MinecraftForge.EVENT_BUS.register(new PFLMF_AetherEventHook());
		} else {
			PFLMF.Debug("PFLMF_Aether PlayerCoreAPI setting Check out.");
			//Debug("PFLMF_Aether PlayerCoreAPI setting Check out PlayerCoreType="+PlayerCoreType);
			//Debug("PFLMF_Aether PlayerCoreAPI setting Check out PFLM_RenderPlayerAether="+PFLM_RenderPlayerAether);
			//Debug("PFLMF_Aether PlayerCoreAPI setting Check out RENDER="+RENDER);
		}
*/
	}

	public static void func_130009_a(AbstractClientPlayer entity, double d, double d1, double d2, float f, float f1, boolean b, boolean b1, boolean b2) {
		//PFLMF.Debug("PFLMF_Aether func_130009_a renderPlayerAether="+renderPlayerAether);
		//PFLMF.Debug("PFLMF_Aether func_130009_a entity="+entity);
		if (renderPlayerAether != null
				&& entity != null) ;else return;
		renderPlayerAether.scale = f;
		Object o = Modchu_Reflect.getFieldObject(RenderPlayerAether, "modelMisc", renderPlayerAether);
		boolean t = false;
		t = Modchu_Reflect.setFieldObject(ModelBiped.class, "field_78117_n", "isSneak", o, b);
		t = Modchu_Reflect.setFieldObject(ModelBase.class, "field_78093_q", "isRiding", o, b1);
		t = Modchu_Reflect.setFieldObject(ModelBiped.class, "field_78118_o", "aimedBow", o, b2);
		Modchu_Reflect.invokeMethod(RenderPlayerAether, "renderMisc", new Class[]{ EntityPlayer.class, double.class, double.class, double.class, float.class, float.class }, renderPlayerAether, new Object[]{ entity, d, d1, d2, f, f1 });
		t = Modchu_Reflect.setFieldObject(ModelBiped.class, "field_78117_n", "isSneak", o, false);
		t = Modchu_Reflect.setFieldObject(ModelBase.class, "field_78093_q", "isRiding", o, false);
		t = Modchu_Reflect.setFieldObject(ModelBiped.class, "field_78118_o", "aimedBow", o, false);
	}

	public static void setMainModel(ModelBase par1ModelBase) {
/*
		if (render_PlayerCoreRender != null) {
			boolean t = false;
			t = Modchu_Reflect.setFieldObject(RendererLivingEntity.class, "field_77045_g", "mainModel", render_PlayerCoreRender, dummyModel);
			//Modchu_Debug.lDebug("setMainModel 1 t="+t);
			t = Modchu_Reflect.setFieldObject(RendererLivingEntity.class, "field_77045_g", "mainModel", renderPlayerAether, dummyModel);
			//Modchu_Debug.lDebug("setMainModel 2 t="+t);
			//t = Modchu_Reflect.setFieldObject(RenderPlayer.class, "field_77071_a", "modelBipedMain", render_PlayerCoreRender, dummyModel);
			//Modchu_Debug.lDebug("setMainModel 3 t="+t);
		} else {
			Modchu_Debug.lDebug("setMainModel render_PlayerCoreRender == null !!");
		}
*/
	}

	public static void setRenderPassModel(ModelBase par1ModelBase) {
/*
		if (render_PlayerCoreRender != null) {
			render_PlayerCoreRender.setRenderPassModel(dummyModel);
			Modchu_Reflect.setPrivateValue(RenderPlayer.class, render_PlayerCoreRender, 2, dummyModel);
			Modchu_Reflect.setPrivateValue(RenderPlayer.class, render_PlayerCoreRender, 3, dummyModel);
		} else {
			Modchu_Debug.lDebug("setRenderPassModel render_PlayerCoreRender == null !!");
		}
*/
	}

	public static void renderFirstPersonArm(EntityPlayer entityplayer) {
		if (renderPlayerAether != null
				&& entityplayer != null) ;else {
					Modchu_Debug.lDebug("renderFirstPersonArm renderPlayerAether == null !!");
					return;
				}
		Modchu_Reflect.invokeMethod(RenderPlayerAether, "renderFirstPersonGlow", new Class[]{ EntityPlayer.class }, renderPlayerAether, new Object[]{ entityplayer });
		Modchu_Reflect.invokeMethod(RenderPlayerAether, "renderFirstPersonGloves", new Class[]{ EntityPlayer.class }, renderPlayerAether, new Object[]{ entityplayer });
	}

	public static void renderSpecials(AbstractClientPlayer entityplayer, float f, float f1)
	{
		if (renderPlayerAether != null
				&& entityplayer != null) ;else return;
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glTranslatef(0.0F, f1, 0.0F);
		GL11.glScalef(1.02171F, 1.0271F, 1.0271F);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
		Modchu_Reflect.invokeMethod(RenderPlayerAether, "renderCape", new Class[]{ EntityPlayer.class, float.class }, renderPlayerAether, new Object[]{ entityplayer, f });
		GL11.glEnable(GL11.GL_LIGHTING);
		//GL11.glDepthMask(false);
		GL11.glPopMatrix();
	}

	public static void renderLivingAt(EntityLivingBase par1EntityLivingBase, double par2, double par4, double par6) {
		if (renderPlayerAether != null
				&& par1EntityLivingBase != null) ;else return;
		Modchu_Reflect.invokeMethod(RenderPlayer.class, "func_77039_a", "renderLivingAt", new Class[]{ EntityLivingBase.class, double.class, double.class, double.class }, renderPlayerAether, new Object[]{ par1EntityLivingBase, par2, par4, par6 });
	}
}