import java.util.Set;
import java.util.HashSet;

public class Permutator {
  public static Set<String> permutate (int slots, String base) {
    if (slots > base.length()) {
      System.out.println("SLOT SIZE CAN NOT BE BIGGER THAN BASE SIZE.");
      System.exit(0);
    }
    Set<String> list = new HashSet<String>();
    perm("", base, slots, list);
    return list;
  }
  public static void perm (String prefix, String base, int slot, Set<String> list) {
    if (slot == 0) {
      list.add(prefix);
      return;
    }
    for (int c = 0; c < base.length(); c++) {
      perm(prefix + base.charAt(c), 
           base.substring(0, c) + base.substring(c + 1) , slot - 1, list);
    }
  }
}