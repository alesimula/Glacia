package com.greenapple.glacia.renderer

import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

import java.util.Comparator

@OnlyIn(Dist.CLIENT)
class QuadComparator(private val field_147627_d: IntArray, private val field_147630_a: Float, private val field_147628_b: Float, private val field_147629_c: Float) : Comparator<Number> {

    private fun compare(p_compare_1_: Int, p_compare_2_: Int): Int {
        val f = java.lang.Float.intBitsToFloat(this.field_147627_d[p_compare_1_]) - this.field_147630_a
        val f1 = java.lang.Float.intBitsToFloat(this.field_147627_d[p_compare_1_ + 1]) - this.field_147628_b
        val f2 = java.lang.Float.intBitsToFloat(this.field_147627_d[p_compare_1_ + 2]) - this.field_147629_c
        val f3 = java.lang.Float.intBitsToFloat(this.field_147627_d[p_compare_1_ + 8]) - this.field_147630_a
        val f4 = java.lang.Float.intBitsToFloat(this.field_147627_d[p_compare_1_ + 9]) - this.field_147628_b
        val f5 = java.lang.Float.intBitsToFloat(this.field_147627_d[p_compare_1_ + 10]) - this.field_147629_c
        val f6 = java.lang.Float.intBitsToFloat(this.field_147627_d[p_compare_1_ + 16]) - this.field_147630_a
        val f7 = java.lang.Float.intBitsToFloat(this.field_147627_d[p_compare_1_ + 17]) - this.field_147628_b
        val f8 = java.lang.Float.intBitsToFloat(this.field_147627_d[p_compare_1_ + 18]) - this.field_147629_c
        val f9 = java.lang.Float.intBitsToFloat(this.field_147627_d[p_compare_1_ + 24]) - this.field_147630_a
        val f10 = java.lang.Float.intBitsToFloat(this.field_147627_d[p_compare_1_ + 25]) - this.field_147628_b
        val f11 = java.lang.Float.intBitsToFloat(this.field_147627_d[p_compare_1_ + 26]) - this.field_147629_c
        val f12 = java.lang.Float.intBitsToFloat(this.field_147627_d[p_compare_2_]) - this.field_147630_a
        val f13 = java.lang.Float.intBitsToFloat(this.field_147627_d[p_compare_2_ + 1]) - this.field_147628_b
        val f14 = java.lang.Float.intBitsToFloat(this.field_147627_d[p_compare_2_ + 2]) - this.field_147629_c
        val f15 = java.lang.Float.intBitsToFloat(this.field_147627_d[p_compare_2_ + 8]) - this.field_147630_a
        val f16 = java.lang.Float.intBitsToFloat(this.field_147627_d[p_compare_2_ + 9]) - this.field_147628_b
        val f17 = java.lang.Float.intBitsToFloat(this.field_147627_d[p_compare_2_ + 10]) - this.field_147629_c
        val f18 = java.lang.Float.intBitsToFloat(this.field_147627_d[p_compare_2_ + 16]) - this.field_147630_a
        val f19 = java.lang.Float.intBitsToFloat(this.field_147627_d[p_compare_2_ + 17]) - this.field_147628_b
        val f20 = java.lang.Float.intBitsToFloat(this.field_147627_d[p_compare_2_ + 18]) - this.field_147629_c
        val f21 = java.lang.Float.intBitsToFloat(this.field_147627_d[p_compare_2_ + 24]) - this.field_147630_a
        val f22 = java.lang.Float.intBitsToFloat(this.field_147627_d[p_compare_2_ + 25]) - this.field_147628_b
        val f23 = java.lang.Float.intBitsToFloat(this.field_147627_d[p_compare_2_ + 26]) - this.field_147629_c
        val f24 = (f + f3 + f6 + f9) * 0.25f
        val f25 = (f1 + f4 + f7 + f10) * 0.25f
        val f26 = (f2 + f5 + f8 + f11) * 0.25f
        val f27 = (f12 + f15 + f18 + f21) * 0.25f
        val f28 = (f13 + f16 + f19 + f22) * 0.25f
        val f29 = (f14 + f17 + f20 + f23) * 0.25f
        val f30 = f24 * f24 + f25 * f25 + f26 * f26
        val f31 = f27 * f27 + f28 * f28 + f29 * f29
        return f31.compareTo(f30)
    }

    override fun compare(p_compare_1_: Number, p_compare_2_: Number): Int {
        return this.compare(p_compare_1_.toInt(), p_compare_2_.toInt())
    }
}