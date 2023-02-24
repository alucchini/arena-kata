package org.katas

import kotlin.math.floor
import kotlin.math.max
import kotlin.math.roundToInt

class ArenaDamageCalculator {
    fun computeDamage(attacker: Hero, defenders: List<Hero>): List<Hero> {
        val disadvantagedHeroes = mutableListOf<Hero>()
        val neutralHeroes = mutableListOf<Hero>()
        val advantagedHeroes = mutableListOf<Hero>()

        // Fill lists
        if (attacker.getElement() === HeroElement.Water) {
            for (h in defenders) {
                if (h.lp == 0) {
                    continue
                }
                if (h.getElement() === HeroElement.Fire) {
                    disadvantagedHeroes.add(h)
                } else if (h.getElement() === HeroElement.Water) {
                    neutralHeroes.add(h)
                } else {
                    advantagedHeroes.add(h)
                }
            }
        } else if (attacker.getElement() === HeroElement.Fire) {
            for (h in defenders) {
                if (h.lp == 0) {
                    continue
                }
                if (h.getElement() === HeroElement.Fire) {
                    neutralHeroes.add(h)
                } else if (h.getElement() === HeroElement.Water) {
                    advantagedHeroes.add(h)
                } else {
                    disadvantagedHeroes.add(h)
                }
            }
        } else {
            for (h in defenders) {
                if (h.lp == 0) {
                    continue
                }
                if (h.getElement() === HeroElement.Fire) {
                    advantagedHeroes.add(h)
                } else if (h.getElement() === HeroElement.Water) {
                    disadvantagedHeroes.add(h)
                } else {
                    neutralHeroes.add(h)
                }
            }
        }
        val attacked = if (disadvantagedHeroes.size > 0) {
            disadvantagedHeroes[floor(Math.random() * disadvantagedHeroes.size).toInt()]
        } else if (neutralHeroes.size > 0) {
            neutralHeroes[floor(Math.random() * neutralHeroes.size).toInt()]
        } else {
            advantagedHeroes[floor(Math.random() * advantagedHeroes.size).toInt()]
        }
        val isCritical = Math.random() * 100 < attacker.crtr
        var dmg = if (isCritical) {
            ((attacker.pow + (0.5 + attacker.leth / 5000f) * attacker.pow).roundToInt() * (1 - attacked.def / 7500f)).toDouble()
        } else {
            (attacker.pow * (1 - attacked.def / 7500f)).toDouble()
        }

        // Buffs
        if (attacker.getBuffs().contains(Buff.Attack)) {
            dmg += if (isCritical) {
                ((attacker.pow * 0.25 + (0.5 + attacker.leth / 5000f) * attacker.pow * 0.25).roundToInt() * (1 - attacked.def / 7500f)).toDouble()
            } else {
                attacker.pow * 0.25 * (1 - attacked.def / 7500f)
            }
        }
        if (attacked.getBuffs().contains(Buff.Defense)) {
            dmg = dmg / (1 - attacked.def / 7500f) * (1 - attacked.def / 7500f - 0.25)
        }

        // Strengths and Weaknesses
        dmg = max(0.0, dmg)
        if (dmg > 0) {
            if (disadvantagedHeroes.contains(attacked)) {
                dmg += dmg * 20 / 100f
            } else if (advantagedHeroes.contains(attacked)) {
                dmg -= dmg * 20 / 100f
            }
            dmg = floor(dmg)
            if (dmg > 0) {
                attacked.lp = attacked.lp - dmg.toInt()
                if (attacked.lp < 0) {
                    attacked.lp = 0
                }
            }
        }
        return defenders
    }
}