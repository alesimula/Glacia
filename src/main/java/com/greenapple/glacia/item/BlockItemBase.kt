package com.greenapple.glacia.item

import com.greenapple.glacia.block.IBlockBase
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.item.*
import net.minecraft.util.NonNullList
import net.minecraft.util.ResourceLocation
import net.minecraft.util.Util
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.TextComponentUtils
import net.minecraft.util.text.TranslationTextComponent
import net.minecraftforge.registries.IForgeRegistry

open class BlockItemBase private constructor(val stateInit: BlockState.() -> BlockState, block: Block, val unlocalizedName: String, private val registryNameSuffix: String, builder: Properties) : BlockItem(block, builder) {

    private val translationRegistryName = if (registryNameSuffix.isEmpty()) block.registryName else block.registryName?.run {ResourceLocation(namespace,"$path.$registryNameSuffix")}

    init {
        translationRegistryName?.run {setRegistryName(namespace, "block.$path")}
        if (registryNameSuffix.isEmpty()) {
            (block as? IBlockBase)?.blockItem = this
            if (BLOCK_TO_ITEM[block] !is BlockItemBase) BLOCK_TO_ITEM[block] = this
        }
    }

    constructor(block: Block, name: String, builder: Properties) : this({this}, block, name,"", builder)
    constructor(blockBase: IBlockBase, builder: Properties) : this({this}, blockBase.block,  blockBase.unlocalizedName,"", builder)

    companion object {
        private fun Block.toBlockItemVariant(name: String, variant: String, stateInit: BlockState.()->BlockState) = BlockItemBase(stateInit, this, name, variant, Properties())
    }

    private val unlocalizedNameText : ITextComponent by lazy {TextComponentUtils.toTextComponent {unlocalizedName}}
    private val variantTranslationKey by lazy {Util.makeTranslationKey("block", translationRegistryName);}
    private val variants = linkedMapOf<String, BlockItemBase>()
    val isVariant = registryNameSuffix.isNotEmpty()

    override fun getTranslationKey() : String = variantTranslationKey

    override fun getDisplayName(stack: ItemStack): ITextComponent = TranslationTextComponent(translationKey).let { name ->
        if (name.unformattedComponentText == translationKey) unlocalizedNameText
        else name
    }

    override fun getStateForPlacement(context: BlockItemUseContext): BlockState? = super.getStateForPlacement(context)?.let(stateInit)

    /**
     * Must be called before registering the associated ItemBlock
     * all variants have the same id as their parent plus ".SUFFIX"
     * Ex: mod_test:block_test.state_test
     */
    fun addVariant(registry: IForgeRegistry<Item>, registryNameSuffix: String, name: String="${getDisplayName(ItemStack(this)).formattedText} ($registryNameSuffix)", stateInit: BlockState.()->BlockState) : BlockItemBase {
        val blockVariant = block.toBlockItemVariant(name, registryNameSuffix, stateInit)
        registry.register(blockVariant)
        variants[registryNameSuffix] = blockVariant
        return this
    }
    fun getVariant(name: String) : BlockItemBase? = variants[name]

    override fun fillItemGroup(group: ItemGroup, items: NonNullList<ItemStack>) {
        super.fillItemGroup(group, items)
        if (group===this.group) for (variant in variants.values) items.add(ItemStack(variant))
    }
}

fun Block.toBlockItem(name: String, group: ItemGroup?=null) = (Item.BLOCK_TO_ITEM[this] as? BlockItemBase)?.takeIf {it.unlocalizedName == name} ?: BlockItemBase(this, name, Item.Properties().apply { group?.let { group(it) } })
fun IBlockBase.toBlockItem(group: ItemGroup?=null) = blockItem?.let {group?.let {null} ?: it} ?: block.toBlockItem(unlocalizedName, group)