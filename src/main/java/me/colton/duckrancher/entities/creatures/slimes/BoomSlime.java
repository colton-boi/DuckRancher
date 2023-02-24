package me.colton.duckrancher.entities.creatures.slimes;

import me.colton.duckrancher.entities.creatures.Slime;
import org.bukkit.entity.Player;

public class BoomSlime extends Slime {
    @Override
    protected void playLandingParticles() {

    }

    @Override
    public void customTick() {
        if (getEntity().getNearbyEntities(2.5, 2.5, 2.5).stream().anyMatch(e -> e instanceof Player)) {
            if (random.nextDouble() > 0.99) {
                customAnger(false);
            }
        }
    }

    @Override
    public void playJumpingParticles() {

    }

    @Override
    public void customAnger(boolean skipCooldown) {
        if (lastAnger+10000 < System.currentTimeMillis() || skipCooldown) {
            lastAnger = System.currentTimeMillis();
            explode();
        }
    }

    public void explode() {

    }
}
