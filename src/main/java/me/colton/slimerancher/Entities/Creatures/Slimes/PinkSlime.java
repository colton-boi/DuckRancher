package me.colton.slimerancher.Entities.Creatures.Slimes;

import me.colton.slimerancher.Entities.Creatures.Slime;
import org.bukkit.Color;
import org.bukkit.Particle;

public class PinkSlime extends Slime {
    private boolean lastOnGround = true;

    @Override
    public void customTick() {
        boolean onGround = getEntity().isOnGround();
        if (!lastOnGround && onGround) {
            customParticle();
        }
        lastOnGround = onGround;
    }

    @Override
    public void customParticle() {
        getLocation().getWorld().spawnParticle(Particle.REDSTONE, getLocation(), 15, 0.5, 0.5, 0.5,
                0.025, new Particle.DustOptions(Color.fromRGB(255, 74, 214), 1.5F));
    }

    @Override
    public void customAnger(boolean skipCooldown) {

    }
}
