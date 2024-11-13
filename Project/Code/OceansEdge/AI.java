package OceansEdge;

public class AI {
    class PirateAI extends Entities.Pirate {
        public PirateAI(Entities entitiesInstance) {
            entitiesInstance.super();
        }
        
        public void think() {
            if(this.health == 0) {
                die();
            } else if(this.weapon == null) {
                pickWeapon();
            } 
        }
    }
}
