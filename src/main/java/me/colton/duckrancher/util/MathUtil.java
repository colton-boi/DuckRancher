package me.colton.duckrancher.util;

import com.mojang.math.Quaternion;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class MathUtil {
    public static double lerp(double a, double b, double interval){
        return (a*interval+b*(1-interval));
    }

    public static Location lerp(Location a, Location b, double interval){
        return a.clone().multiply(interval).add(b.clone().multiply(1-interval));
    }
    public static Vector[] getRotationMatrix(Quaternion q) {
        double w = q.r();
        double x = q.i();
        double y = q.j();
        double z = q.k();
        double n = w * w +
            x * x +
            y * y +
            z * z;
        double s = n == 0 ? 0 : 2 / n;
        return new Vector[]{
            new Vector(1 - s * (y * y + z * z), s * (x * y - w * z), s * (x * z + w * y)),
            new Vector(s * (x * y + w * z), 1 - s * (x * x + z * z), s * (y * z - w * x)),
            new Vector(s * (x * z - w * y), s * (y * z + w * x), 1 - s * (x * x + y * y))
        };
    }

    public static Vector applyRotationMatrix(Vector vec, Vector[] m) {
        return new Vector(
            vec.getX() * m[0].getX() + vec.getY() * m[0].getY() + vec.getZ() * m[0].getZ(),
            vec.getX() * m[1].getX() + vec.getY() * m[1].getY() + vec.getZ() * m[1].getZ(),
            vec.getX() * m[2].getX() + vec.getY() * m[2].getY() + vec.getZ() * m[2].getZ()
        );
    }

    public static Vector applyQuaternion(Vector vec, Quaternion quaternion){
        return applyRotationMatrix(vec,getRotationMatrix(quaternion));
    }
}
