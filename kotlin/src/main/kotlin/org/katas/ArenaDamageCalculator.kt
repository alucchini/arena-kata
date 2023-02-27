package org.katas

import kotlin.math.floor
import kotlin.math.max
import kotlin.math.roundToInt

class ArenaDamageCalculator {
    fun computeDamage(attacker: Hero, defenders: List<Hero>, preference: Hero? = null): List<Hero> {
        val (disadvantagedHeroes, neutralHeroes, advantagedHeroes) = filterDefenders(attacker, defenders)
        var attacked = getAttacked(disadvantagedHeroes, neutralHeroes, advantagedHeroes)
        val isCritical = Math.random() * 100 < attacker.crtr
        val initialDamage = getInitialDamage(isCritical, attacker, attacked)
        val damageWithBuffs = computeBuffs(initialDamage, isCritical, attacker, attacked)

        if (attacker.getCounters().contains(Counter.Holy)){
            attacked = preference ?: attacked
            attacked.lp = (attacked.lp - attacker.pow * 0.8).toInt()
        } else {
            if (damageWithBuffs > 0) {
                val finalDamage = floor(computeStrengthsAndWeaknesses(damageWithBuffs, attacked, advantagedHeroes, disadvantagedHeroes))
                if (finalDamage > 0) {
                    attacked.lp = attacked.lp - finalDamage.toInt()
                }
            }
        }
        if (attacked.lp < 0) {
            attacked.lp = 0
        }
        return defenders
    }

    private fun filterDefenders(attacker:Hero, defenders: List<Hero>): Triple<List<Hero>, List<Hero>, List<Hero>> {
        val disadvantagedHeroes = mutableListOf<Hero>()
        val neutralHeroes = mutableListOf<Hero>()
        val advantagedHeroes = mutableListOf<Hero>()
        defenders.filter { it.lp > 0 }.forEach {
            if (attacker.getElement() === it.getElement()) {
                neutralHeroes.add(it)
            } else if (isAttackerStronger(attacker.getElement(), it.getElement())) {
                disadvantagedHeroes.add(it)
            } else {
                advantagedHeroes.add(it)
            }
        }
        return Triple(disadvantagedHeroes, neutralHeroes, advantagedHeroes)
    }

    private fun getAttacked(
        disadvantagedHeroes: List<Hero>,
        neutralHeroes: List<Hero>,
        advantagedHeroes: List<Hero>
    ): Hero {
        return if (disadvantagedHeroes.isNotEmpty()) {
            disadvantagedHeroes[floor(Math.random() * disadvantagedHeroes.size).toInt()]
        } else if (neutralHeroes.isNotEmpty()) {
            neutralHeroes[floor(Math.random() * neutralHeroes.size).toInt()]
        } else {
            advantagedHeroes[floor(Math.random() * advantagedHeroes.size).toInt()]
        }
    }

    private fun getInitialDamage(isCritical: Boolean, attacker: Hero, defender: Hero): Double {
        return if (isCritical) {
            ((attacker.pow + (0.5 + attacker.leth / 5000f) * attacker.pow).roundToInt() * (1 - defender.def / 7500f)).toDouble()
        } else {
            (attacker.pow * (1 - defender.def / 7500f)).toDouble()
        }
    }

    private fun isAttackerStronger(attackerElement: HeroElement, defenderElement: HeroElement): Boolean {
        return if (attackerElement === HeroElement.Fire && defenderElement === HeroElement.Earth) {
            true
        } else if (attackerElement === HeroElement.Water && defenderElement === HeroElement.Fire) {
            true
        } else attackerElement === HeroElement.Earth && defenderElement === HeroElement.Water
    }

    private fun computeBuffs(initialValue: Double, isCritical: Boolean, attacker: Hero, defender: Hero): Double {
        var result = initialValue
        attacker.getBuffs().forEach {
            result = getDamageWithBuff(result, isCritical, it, attacker, defender)
        }
        defender.getBuffs().forEach {
            result = getDamageWithBuff(result, isCritical, it, attacker, defender)
        }
        return result
    }

    private fun getDamageWithBuff(initialValue: Double, isCritical: Boolean, buff: Buff, attacker: Hero, defender: Hero): Double {
        return when (buff) {
            Buff.Attack -> {
                val newValue = if (isCritical) {
                    ((attacker.pow * 0.25 + (0.5 + attacker.leth / 5000f) * attacker.pow * 0.25).roundToInt()
                            * (1 - defender.def / 7500f)).toDouble()
                } else {
                    attacker.pow * 0.25 * (1 - defender.def / 7500f)
                }
                initialValue + newValue
            }
            Buff.Defense -> {
                initialValue / (1 - defender.def / 7500f) * (1 - defender.def / 7500f - 0.25)
            }
        }
    }

    private fun computeStrengthsAndWeaknesses(
        initialValue: Double,
        attacked: Hero,
        advantagedHeroes: List<Hero>,
        disadvantagedHeroes: List<Hero>
    ): Double {
        return if (disadvantagedHeroes.contains(attacked)) {
            initialValue * 1.2
        } else if (advantagedHeroes.contains(attacked)) {
            initialValue * 0.8
        } else initialValue
    }
}