package me.colton.slimerancher.Entities.Creatures.Slimes;

import me.colton.slimerancher.Entities.Creatures.Slime;
import org.bukkit.entity.Player;

public class BoomSlime extends Slime {
    @Override
    public void customTick() {
        if (getEntity().getNearbyEntities(2.5, 2.5, 2.5).stream().anyMatch(e -> e instanceof Player)) {
            if (random.nextDouble() > 0.95) {
                customAnger();
            }
        }
    }

    @Override
    public void customParticle() {

    }

    @Override
    public void customAnger() {

    }
}
