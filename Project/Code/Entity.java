public class Entity {
   public int health = 0;
   public int maxHealth = 0;
   public String name = "";
   public String model = "";

   public void create(String name, int health, int maxHealth, String model) {
      this.name = name;
      this.health = health;
      this.maxHealth = maxHealth;
      this.model = model;
   }
}
