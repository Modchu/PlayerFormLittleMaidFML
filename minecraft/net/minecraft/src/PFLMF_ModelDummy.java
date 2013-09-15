package net.minecraft.src;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;

public class PFLMF_ModelDummy extends ModelBiped
{
    public PFLMF_ModelDummy()
    {
        super(0.0F);
        bipedHead = new ModelRenderer(this);
        bipedHeadwear = new ModelRenderer(this);
        bipedBody = new ModelRenderer(this);
        bipedRightArm = new ModelRenderer(this);
        bipedLeftArm = new ModelRenderer(this);
        bipedRightLeg = new ModelRenderer(this);
        bipedLeftLeg = new ModelRenderer(this);
        bipedEars = new ModelRenderer(this);
        bipedCloak = new ModelRenderer(this);
    }
}