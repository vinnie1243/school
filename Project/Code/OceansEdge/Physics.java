package OceansEdge;

public class Physics {
    public static void update() {
        //player update
        playerUpdate();
        //enemy update
        enemyUpdate();
        //ship update
        shipUpdate();
        //bullet update
        bulletUpdate();
        //shell update
        shellUpdate();
        //other physics objects update

    }   

    private static void playerUpdate() {
        //slow down
        Client.player.motion.x *= 0.8;
        Client.player.motion.y *= 0.8;
        Client.player.motion.z *= 0.8;
        //gravity
        //Client.player.motion.y -= 0.1;
        //player movement
        Client.player.position.x += Client.player.motion.x;
        Client.player.position.y += Client.player.motion.y;
        Client.player.position.z += Client.player.motion.z;
        //player collision
        //need to be optimised later so it only checks for collison with things within 10 units of the player
        for (int i = 0; i < Drawing.chunks.size(); i++) {
            DataStructures.Chunk c = Drawing.chunks.get(i);
            for (int j = 0; j < c.objects.size(); j++) {
                for(int l = 0; l < c.objects.size(); l++) {
                    if(l != j) {
                        Entities.Obj o = c.objects.get(l);
                        Entities.Obj o2 = c.objects.get(j);
                        if (o.collision && o2.collision) {
                            //boolean coll = boxCollision(o.width, i, j, j, j, j, i, i, j, j, i, j)
                        }
                    }
                }
            }
        }

    }

    private static void shipUpdate() {
        //calculate recoil

        //calculate acceleration

        //collision

        //boyancy
        sphereBoxCollision(0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        //move ship
    }

    private static void enemyUpdate() {
        //enemy movement

        //enemy collision

        //enemy shooting
    }

    private static void bulletUpdate() {
        //bullet movement

        //bullet collision
        boxCollision(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    }

    private static void shellUpdate() {
        //calculate air resistance
        //calculate gravity drop
        //collision
        boxCollision(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    }

    private static boolean boxCollision(double width1, double height1, double depth1, double x1, double y1, double z1, double width2, double height2, double depth2, double x2, double y2, double z2) {
        if (x1 + width1 > x2 && x1 < x2 + width2 && y1 + height1 > y2 && y1 < y2 + height2 && z1 + depth1 > z2 && z1 < z2 + depth2) {
            return true;
        }
        return false;
    }

    private static boolean sphereCollision(double radius1, double x1, double y1, double z1, double radius2, double x2, double y2, double z2) {
        double distance = Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2) + Math.pow(z1 - z2, 2));
        if (distance < radius1 + radius2) {
            return true;
        }
        return false;
    }
    private static boolean sphereBoxCollision(double radius, double x, double y, double z, double width, double height, double depth, double x2, double y2, double z2) {
        double closestX = Math.max(x2, Math.min(x, x2 + width));
        double closestY = Math.max(y2, Math.min(y, y2 + height));
        double closestZ = Math.max(z2, Math.min(z, z2 + depth));
        double distance = Math.sqrt(Math.pow(closestX - x, 2) + Math.pow(closestY - y, 2) + Math.pow(closestZ - z, 2));
        if (distance < radius) {
            return true;
        }
        return false;
    }
    private static boolean triangleCollision(double x1, double y1, double z1, double x2, double y2, double z2, double x3, double y3, double z3, double x, double y, double z) {
        double area = 0.5 * Math.abs((x1 - x) * (y2 - y) - (x2 - x) * (y1 - y) + (x2 - x) * (y3 - y) - (x3 - x) * (y2 - y) + (x3 - x) * (y1 - y) - (x1 - x) * (y3 - y));
        double area1 = 0.5 * Math.abs((x1 - x) * (y2 - y) - (x2 - x) * (y1 - y) + (x2 - x) * (y3 - y) - (x3 - x) * (y2 - y) + (x3 - x) * (y1 - y) - (x1 - x) * (y3 - y));
        double area2 = 0.5 * Math.abs((x - x1) * (y2 - y1) - (x2 - x1) * (y - y1) + (x2 - x1) * (y3 - y1) - (x3 - x1) * (y2 - y1) + (x3 - x1) * (y - y1) - (x - x1) * (y3 - y1));
        double area3 = 0.5 * Math.abs((x1 - x) * (y - y1) - (x - x1) * (y1 - y1) + (x - x1) * (y3 - y1) - (x3 - x1) * (y - y1) + (x3 - x1) * (y1 - y1) - (x1 - x) * (y3 - y1));
        double area4 = 0.5 * Math.abs((x1 - x) * (y2 - y) - (x - x1) * (y1 - y) + (x - x1) * (y - y) - (x3 - x1) * (y2 - y) + (x3 - x1) * (y1 - y) - (x1 - x) * (y3 - y));
        if (area == area1 + area2 + area3 + area4) {
            return true;
        }   
        return false;
    }
}
