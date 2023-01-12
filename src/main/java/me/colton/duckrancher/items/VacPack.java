package me.colton.duckrancher.items;

import com.destroystokyo.paper.ParticleBuilder;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import me.colton.duckrancher.util.MathUtil;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.level.Level;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftHumanEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;

import static me.colton.duckrancher.SlimeRancher.instance;
import static org.bukkit.Bukkit.getScheduler;
import static org.bukkit.Bukkit.getWorlds;

public class VacPack {

    private final Player player;
    private int animationFrame = 0;

    public VacPack(Player player) {
        this.player = player;
        vacPacks.put(player,this);
        player.getInventory().setItemInOffHand(new ItemStack(Material.SHIELD));
    }

    private static final Map<Player,VacPack> vacPacks = new HashMap<>();

    public static VacPack getHandlerForPlayer(Player player) {
        return vacPacks.get(player);
    }

    private void useVacPack() {
        Vector facing = player.getEyeLocation().getDirection().normalize();
        Location pL = player.getEyeLocation();
        // double maxDist = (double) getPlayerData(player.getUniqueId()).get("VacPackDistance");
        double maxDist = 5;

        for (Entity e : player.getNearbyEntities(maxDist, maxDist, maxDist)) {
            if (e == player) return;

            Location l = e.getLocation();
            Vector toEntity = new Vector((l.getX()-pL.getX()), (l.getY()-pL.getY()), (l.getZ()-pL.getZ()));
            Vector normalToEntity = toEntity.clone().normalize();

            if (!(facing.getX()+0.5 > normalToEntity.getX() && normalToEntity.getX() > facing.getX()-0.5)) {
                continue;
            }
            if (!(facing.getY()+0.5 > normalToEntity.getY() && normalToEntity.getY() > facing.getY()-0.5)) {
                continue;
            }
            if (!(facing.getZ()+0.5 > normalToEntity.getZ() && normalToEntity.getZ() > facing.getZ()-0.5)) {
                continue;
            }

            e.setVelocity(e.getVelocity().add(toEntity.multiply(-0.05)));

        }
        playVacEffect();
        playVacEffect();
        playVacEffect();
        playVacEffect();
    }

    private void playVacEffect() {
        animationFrame +=1;
        animationFrame %=40;
        //if(animationFrame % 2 == 0) return;
        //Vector direction = player.getEyeLocation().getDirection();
        float f = (float) (Math.PI*2f/6.2f);
        float x= (float) Math.sin(animationFrame*f);
        float y= (float) Math.cos(animationFrame*f);
        Quaternion quaternion = Quaternion.fromXYZ(x/2.5f,y/2.5f,0);
        Quaternion playerRotation = Quaternion.fromYXZ(-(float) Math.toRadians(player.getEyeLocation().getYaw()), (float) Math.toRadians(player.getEyeLocation().getPitch()),0);
        Location location = MathUtil.lerp(player.getEyeLocation(),
            player.getEyeLocation().add(
                MathUtil.applyQuaternion(
                    MathUtil.applyQuaternion(new Vector(0, 0, 1), quaternion).multiply(3).add(new Vector(1,-0.5,0.5)),
                    playerRotation
                )
            ),
            animationFrame / 45d);
        player.spawnParticle(
            (animationFrame%2==0)?Particle.FIREWORKS_SPARK:Particle.CRIT,
            location,
            1,0,0,0,0
        );
//        quaternion.mul(Quaternion.fromYXZ(-0.1f,0,0));
//        player.spawnParticle(
//            Particle.ASH,
//            MathUtil.lerp(player.getEyeLocation(),
//                player.getEyeLocation().add(player.getEyeLocation().getDirection().multiply(5).rotateAroundY(-0.1)),
//                animationFrame/20d),
//            2
//        );
    }

    int task = getScheduler().scheduleSyncRepeatingTask(instance, new Runnable() {

        @Override
        public void run() {
            if (!player.isOnline()){
                getScheduler().cancelTask(task);
                vacPacks.remove(player);
            }
            if(player.isBlocking()){
                useVacPack();
            }
        }

    }, 0L, 1L);

    public void onRightClickSlime() {
        useVacPack();
    }
}
