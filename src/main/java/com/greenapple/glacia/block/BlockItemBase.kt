package com.greenapple.glacia.block

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.item.*
import net.minecraft.util.NonNullList
import net.minecraft.util.Util
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.TextComponentUtils
import net.minecraft.util.text.TranslationTextComponent
import net.minecraftforge.event.RegistryEvent

open class BlockItemBase private constructor(val stateInit: BlockState.() -> BlockState, block: Block, val unlocalizedName: String, private val registryNameSuffix: String, builder: Properties) : BlockItem(block, builder) {

    init {
        if (registryNameSuffix.isEmpty()) {
            (block as? IBlockNamed)?.blockItem = this
            registryName = block.registryName
        }
        else setRegistryName(block.registryName?.path+"."+registryNameSuffix)
    }

    constructor(block: Block, name: String, builder: Item.Properties) : this({this}, block, name,"", builder)
    constructor(blockNamed: IBlockNamed, builder: Item.Properties) : this({this}, blockNamed.block,  blockNamed.unlocalizedName,"", builder)

    companion object {
        private fun Block.toBlockItemVariant(name: String, variant: String, stateInit: BlockState.()->BlockState) = BlockItemBase(stateInit, this, name, variant, Properties())
    }

    private val unlocalizedNameText : ITextComponent by lazy {TextComponentUtils.toTextComponent {unlocalizedName}}
    private val variantTranslationKey by lazy {Util.makeTranslationKey("block", registryName);}
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
    fun addVariant(event: RegistryEvent.Register<Item>, registryNameSuffix: String, name: String="${getDisplayName(ItemStack(this)).formattedText} ($registryNameSuffix)", stateInit: BlockState.()->BlockState) : BlockItemBase {
        val blockVariant = block.toBlockItemVariant(name, registryNameSuffix, stateInit)
        event.registry.register(blockVariant)
        variants[registryNameSuffix] = blockVariant
        return this
    }
    fun getVariant(name: String) : BlockItemBase? = variants[name]

    override fun fillItemGroup(group: ItemGroup, items: NonNullList<ItemStack>) {
        super.fillItemGroup(group, items)
        if (group===this.group) for (variant in variants.values) items.add(ItemStack(variant))
    }
}

fun Block.toBlockItem(name: String, group: ItemGroup?=null) = BlockItemBase(this, name, Item.Properties().apply {group?.let {group(it)}})
fun IBlockNamed.toBlockItem(group: ItemGroup?=null) = blockItem?.let {group?.let {null} ?: it} ?: block.toBlockItem(unlocalizedName, group)