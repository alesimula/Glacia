package com.greenapple.glacia.entity.model;


import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL11;

@OnlyIn(Dist.CLIENT)
public class ModelPlayerSnowMan extends PlayerModel<AbstractClientPlayerEntity>
{
	//public ModelRenderer bipedBody;
    public ModelRenderer bipedBottomBody;
    //public ModelRenderer bipedBody;
    //public ModelRenderer bipedHead;
    //public ModelRenderer bipedHeadwear;
    
    //public ModelRenderer bipedRightArm;
    //public ModelRenderer bipedLeftArm;
    
    //public int heldItemLeft;
    //public int heldItemRight;
    
    public ModelPlayerSnowMan(int playerArmorChest)
    {
        this(0.0F, playerArmorChest);
    }
    
    public ModelPlayerSnowMan(float par1, int playerArmorChest)
    {
    	super(par1, true);
        float f = 4.0F;
        float f1 = 0.0F;
        
        /* TODO this
        this.bipedCloak = new ModelRenderer(this, 0, 0);
        this.bipedCloak.addBox(-5.0F, 3.9F, 3.0F, 10, 16, 1, par1);
        this.bipedEars = new ModelRenderer(this, 0, 0);
        this.bipedEars.isHidden = true;*/
        this.bipedHeadwear = new ModelRenderer(this, 0, 0);
        this.bipedHeadwear.showModel = false;

        this.bipedLeftLegwear.showModel = false;
        this.bipedRightLegwear.showModel = false;
        this.bipedLeftArmwear.showModel = false;
        this.bipedRightArmwear.showModel = false;
        this.bipedBodyWear.showModel = false;
        
        //this.bipedHead.setRotationPoint(0.0F, -3.0F, 0.0F);
        if (playerArmorChest == 0)
        {
        this.bipedHead = (new ModelRenderer(this, 0, 0)).setTextureSize(64, 64);
        this.bipedHead.addBox(-4.0F, -5.7F, -4.0F, 8, 8, 8, f1 - 0.5F);
        this.bipedHead.setRotationPoint(0.0F, 0.0F + f, 0.0F);
        this.bipedRightArm = new ModelRenderer(this, 56, 0);
        this.bipedRightArm.addBox(-2.0F, 0.0F, -1.0F, 2, 12, 2, - 0.5F);
        this.bipedRightArm.setRotationPoint(-14.5F, 5.0F, 0.0F);
        this.bipedLeftArm = new ModelRenderer(this, 56, 0);
        this.bipedLeftArm.addBox(-2.0F, 0.0F, -1.0F, 2, 12, 2, - 0.5F);
        this.bipedLeftArm.setRotationPoint(-14.5F, 4.2F, 0.0F);
        this.bipedBody = (new ModelRenderer(this, 0, 16)).setTextureSize(64, 64);
        this.bipedBody.addBox(-5.0F, - 11.0F, -5.0F, 10, 10, 10, f1 - 0.2F);
        this.bipedBody.setRotationPoint(0.0F, 0.0F + f + 9.0F, 0.0F);
        this.bipedBottomBody = (new ModelRenderer(this, 0, 36)).setTextureSize(64, 64);
        this.bipedBottomBody.addBox(-6.0F, - 1.4F, -6.0F, 12, 12, 12, f1 - 0.2F);
        this.bipedBottomBody.setRotationPoint(0.0F, 0.0F + f + 9.0F, 0.0F);
        this.bipedRightLeg = new ModelRenderer(this, 56, 0);
        this.bipedLeftLeg = new ModelRenderer(this, 56, 0);
        this.bipedRightLeg.showModel = false;
        this.bipedLeftLeg.showModel = false;
        }
        else if (playerArmorChest == 1)
        {
        	this.bipedHead = (new ModelRenderer(this, 0, 0)).setTextureSize(64, 32);
            this.bipedHead.setRotationPoint(0.0F, 0.0F + f, 0.0F);
            this.bipedHead.addBox(-4.0F, -7.2F, -4.0F, 8, 8, 8, f1);
            this.bipedHeadwear = (new ModelRenderer(this, 0, 0)).setTextureSize(64, 64);
            this.bipedHeadwear.setRotationPoint(0.0F, 0.0F + f, 0.0F);
            this.bipedRightArm = new ModelRenderer(this, 56, 0);
            this.bipedRightArm.setRotationPoint(-14.5F, 5.0F, 0.0F);
            this.bipedLeftArm = new ModelRenderer(this, 56, 0);
            this.bipedLeftArm.setRotationPoint(-14.5F, 4.2F, 0.0F);
            this.bipedBody = (new ModelRenderer(this, 16, 16)).setTextureSize(64, 32);
            //this.bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, par1);
            this.bipedBody.setRotationPoint(0.0F, 0.0F + f + 9.0F, 0.0F);
            this.bipedBottomBody = (new ModelRenderer(this, 0, 36)).setTextureSize(64, 64);
            this.bipedBottomBody.setRotationPoint(0.0F, 0.0F + f + 9.0F, 0.0F);
            this.bipedRightLeg = new ModelRenderer(this, 56, 0);
            this.bipedLeftLeg = new ModelRenderer(this, 56, 0);
        }
        else if (playerArmorChest == 2)
        {
        	this.bipedHead = (new ModelRenderer(this, 0, 0)).setTextureSize(64, 32);
            this.bipedHead.setRotationPoint(0.0F, 0.0F + f, 0.0F);
            this.bipedHead.addBox(-4.0F, -7.2F, -4.0F, 8, 8, 8, f1);
            this.bipedHeadwear = (new ModelRenderer(this, 0, 0)).setTextureSize(64, 64);
            this.bipedHeadwear.setRotationPoint(0.0F, 0.0F + f, 0.0F);
            this.bipedRightArm = new ModelRenderer(this, 56, 0);
            this.bipedRightArm.setRotationPoint(-14.5F, 5.0F, 0.0F);
            this.bipedLeftArm = new ModelRenderer(this, 56, 0);
            this.bipedLeftArm.setRotationPoint(-14.5F, 4.2F, 0.0F);
            this.bipedBody = (new ModelRenderer(this, 16, 16)).setTextureSize(64, 32);
            //this.bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, par1);
            this.bipedBody.setRotationPoint(0.0F, 0.0F + f + 9.0F, 0.0F);
            this.bipedBottomBody = (new ModelRenderer(this, 0, 36)).setTextureSize(64, 64);
            this.bipedBottomBody.setRotationPoint(0.0F, 0.0F + f + 9.0F, 0.0F);
            this.bipedRightLeg = new ModelRenderer(this, 56, 0);
            this.bipedLeftLeg = new ModelRenderer(this, 56, 0);
        }
    }
    
    private static final String __OBFID = "CL_00000859";

    /*public ModelPlayerSnowMan()
    {
    	super(0.0F, 0.0F, 64, 32);
    	float f = 4.0F;
        float f1 = 0.0F;
        this.bipedHead = (new ModelRenderer(this, 0, 0)).setTextureSize(64, 64);
        this.bipedHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, f1 - 0.5F);
        this.bipedHead.setRotationPoint(0.0F, 0.0F + f, 0.0F);
        this.bipedRightArm = (new ModelRenderer(this, 32, 0)).setTextureSize(64, 64);
        this.bipedRightArm.addBox(-1.0F, 0.0F, -1.0F, 12, 2, 2, f1 - 0.5F);
        this.bipedRightArm.setRotationPoint(-8.5F, 6.0F, 0.0F);
        this.bipedLeftArm = (new ModelRenderer(this, 32, 0)).setTextureSize(64, 64);
        this.bipedLeftArm.addBox(-1.0F, 0.0F, -1.0F, 12, 2, 2, f1 - 0.5F);
        this.bipedLeftArm.setRotationPoint(0.0F, 0.0F + f + 9.0F - 7.0F, 0.0F);
        this.bipedBody = (new ModelRenderer(this, 0, 16)).setTextureSize(64, 64);
        this.bipedBody.addBox(-5.0F, -10.0F, -5.0F, 10, 10, 10, f1 - 0.5F);
        this.bipedBody.setRotationPoint(0.0F, 0.0F + f + 9.0F, 0.0F);
        this.bipedBottomBody = (new ModelRenderer(this, 0, 36)).setTextureSize(64, 64);
        this.bipedBottomBody.addBox(-6.0F, -12.0F, -6.0F, 12, 12, 12, f1 - 0.5F);
        this.bipedBottomBody.setRotationPoint(0.0F, 0.0F + f + 20.0F, 0.0F);
    }*/

    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    @Override
    public void setRotationAngles(AbstractClientPlayerEntity entity, float par1, float par2, float par3, float par4, float par5)
    {
        super.setRotationAngles(entity, par1, par2, par3, par4, par5);
        this.bipedHead.rotateAngleY = par4 / (180F / (float)Math.PI);
        this.bipedHead.rotateAngleX = par5 / (180F / (float)Math.PI);
        this.bipedBody.rotateAngleY = par4 / (180F / (float)Math.PI) * 0.25F;
        //float f6 = MathHelper.sin(this.bipedBody.rotateAngleY);
        //float f7 = MathHelper.cos(this.bipedBody.rotateAngleY);
        //this.bipedRightArm.rotateAngleZ = 1.0F;
        //this.bipedLeftArm.rotateAngleZ = -1.0F;
        //this.bipedRightArm.rotateAngleY = 0.0F + this.bipedBody.rotateAngleY;
        //this.bipedLeftArm.rotateAngleY = (float)Math.PI + this.bipedBody.rotateAngleY;
        //this.bipedRightArm.rotationPointX = f7 * 5.0F;
        //this.bipedRightArm.rotationPointZ = -f6 * 5.0F;
        //this.bipedLeftArm.rotationPointX = -f7 * 5.0F;
        //this.bipedLeftArm.rotationPointZ = f6 * 5.0F;
        
        float f6 = MathHelper.sin(entity.swingProgress * (float)Math.PI);
        float f7 = MathHelper.sin((1.0F - (1.0F - this.swingProgress) * (1.0F - this.swingProgress)) * (float)Math.PI);
        
        this.bipedRightArm.rotateAngleZ = 0.5F;
        this.bipedLeftArm.rotateAngleZ = -0.5F;
        //this.bipedRightArm.rotateAngleY = -(-0.5F - f6 * 0.6F);
        //this.bipedLeftArm.rotateAngleY = 0.1F - f6 * 0.6F;
        this.bipedRightArm.rotateAngleX = -((float)Math.PI / 10F);
        this.bipedRightArm.rotateAngleX = 0.05F;
        this.bipedLeftArm.rotateAngleX = -((float)Math.PI / 10F);
        this.bipedLeftArm.rotateAngleX = -0.05F;
        //this.bipedRightArm.rotateAngleX -= f6 * 1.2F - f7 * 0.4F;
        //this.bipedLeftArm.rotateAngleX -= f6 * 1.2F - f7 * 0.4F;
        //this.bipedRightArm.rotateAngleZ += MathHelper.cos(par3 * 0.09F) * 0.05F + 0.05F;
        //this.bipedLeftArm.rotateAngleZ -= MathHelper.cos(par3 * 0.09F) * 0.05F + 0.05F;
        //this.bipedRightArm.rotateAngleX += MathHelper.sin(par3 * 0.067F) * 0.05F;
        //this.bipedLeftArm.rotateAngleX -= MathHelper.sin(par3 * 0.067F) * 0.05F;
        
        this.bipedRightArm.rotationPointZ = MathHelper.sin(this.bipedBody.rotateAngleY) * 5.0F;
        this.bipedRightArm.rotationPointX = -MathHelper.cos(this.bipedBody.rotateAngleY) * 5.0F +2;
        this.bipedLeftArm.rotationPointZ = -MathHelper.sin(this.bipedBody.rotateAngleY) * 5.0F;
        this.bipedLeftArm.rotationPointX = MathHelper.cos(this.bipedBody.rotateAngleY) * 5.0F;
        
        //this.bipedRightArm.rotateAngleZ += MathHelper.cos(par3 * 0.09F) * 0.05F + 0.05F;
        //this.bipedLeftArm.rotateAngleZ -= MathHelper.cos(par3 * 0.09F) * 0.05F + 0.05F;
        
        //this.bipedRightArm.rotateAngleX = MathHelper.sin(this.bipedBody.rotateAngleY) * 5.05F;
        //this.bipedLeftArm.rotateAngleX = MathHelper.sin(this.bipedBody.rotateAngleY) * 5.05F;
        
        this.bipedRightArm.rotateAngleX = 0.0F + this.bipedBody.rotateAngleY * 0.5F;
        this.bipedLeftArm.rotateAngleX = 0.0F - this.bipedBody.rotateAngleY * 0.5F;
        
        /* TODO this
        this.bipedCloak.rotateAngleY = this.bipedBody.rotateAngleY;
        this.bipedCloak.rotateAngleX = 0.4F;*/
        
        if (this.isSneak)
        {
        	this.bipedBody.rotateAngleX = 0.0F;
            this.bipedRightLeg.rotationPointZ = 0.1F;
            this.bipedLeftLeg.rotationPointZ = 0.1F;
            this.bipedRightLeg.rotationPointY = 12.0F;
            this.bipedLeftLeg.rotationPointY = 12.0F;
            this.bipedHead.rotationPointY = 0.0F;
            this.bipedHeadwear.rotationPointY = 0.0F;
        }
        else
        {
            this.bipedBody.rotateAngleX = 0.0F;
            this.bipedRightLeg.rotationPointZ = 0.1F;
            this.bipedLeftLeg.rotationPointZ = 0.1F;
            this.bipedRightLeg.rotationPointY = 12.0F;
            this.bipedLeftLeg.rotationPointY = 12.0F;
            this.bipedHead.rotationPointY = 0.0F;
            this.bipedHeadwear.rotationPointY = 0.0F;
        }
        
        /*if (entity.getHeldItemMainhand().getCount() > 0 &&  entity.getHeldItemMainhand().getItem() != Items.AIR)
        {
            this.bipedLeftArm.rotateAngleY = this.bipedLeftArm.rotateAngleY * 0.5F - ((float)Math.PI / 10F) * (float)this.heldItemLeft;
        }*/
        switch(this.leftArmPose) {
            case EMPTY:
                this.bipedLeftArm.rotateAngleY = 0.0F;
                break;
            case BLOCK:
                this.bipedLeftArm.rotateAngleX = this.bipedLeftArm.rotateAngleX * 0.5F - 0.9424779F;
                this.bipedLeftArm.rotateAngleY = ((float)Math.PI / 6F);
                break;
            case ITEM:
                this.bipedLeftArm.rotateAngleX = this.bipedLeftArm.rotateAngleX * 0.5F - ((float)Math.PI / 10F);
                this.bipedLeftArm.rotateAngleY = 0.0F;
        }

        /*if (this.heldItemRight != 0)
        {
            this.bipedRightArm.rotateAngleY = this.bipedRightArm.rotateAngleY * 0.5F - ((float)Math.PI / 10F) * (float)this.heldItemRight;
        }*/
        switch(this.rightArmPose) {
            case EMPTY:
                this.bipedRightArm.rotateAngleY = 0.0F;
                break;
            case BLOCK:
                this.bipedRightArm.rotateAngleX = this.bipedRightArm.rotateAngleX * 0.5F - 0.9424779F;
                this.bipedRightArm.rotateAngleY = (-(float)Math.PI / 6F);
                break;
            case ITEM:
                this.bipedRightArm.rotateAngleX = this.bipedRightArm.rotateAngleX * 0.5F - ((float)Math.PI / 10F);
                this.bipedRightArm.rotateAngleY = 0.0F;
                break;
            case THROW_SPEAR:
                this.bipedRightArm.rotateAngleX = this.bipedRightArm.rotateAngleX * 0.5F - (float)Math.PI;
                this.bipedRightArm.rotateAngleY = 0.0F;
        }
        
        //par7Entity.posY -= 0.025D;
    }
    
    public boolean isArmor = false;
    public ModelPlayerSnowMan setArmor()
    {
    	isArmor = true;
		return this;
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    @Override
    public void render(MatrixStack stack, IVertexBuilder vertexBuilder, int par2, int par3, float par4, float par5, float par6, float par7)
    {
    	float f1 = 0.625F;
    	//GL11.glTranslatef(0.0F, 0.05F, 0.0F);
        //GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
        //GL11.glScalef(f1, -f1, -f1);
    	GL11.glPushMatrix();
    	super.render(stack, vertexBuilder, par2, par3, par4, par5, par6, par7);
        //this.setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
        this.bipedBottomBody.render(stack, vertexBuilder, par2, par3, par4, par5, par6, par7);
        // TODO this.bipedEars.render(par7);
        GL11.glPopMatrix();
        if (!this.isArmor)
        {
        	GL11.glTranslatef(0.0F, 0.053F, 0.0F);
        	GL11.glScalef(1.08F, 1.08F, 1.08F);
        }
        //GL11.glScalef(1.1F, 1.1F, 1.1F);
        //double d3 = (double)par3 - (double)par1Entity.yOffset;
        //d3 -= 0.525D;
        //par1Entity.yOffset += 0.025F;
        //par1Entity.posY -= 0.025D;
    }
}