package main.E6BotIntegration.Blacklist;

public class Blacklist {
    //Removed Feral because all offending content should already fall under bestiality, Leaving Dragons and other Feral fantasy creatures to be posted, Helps with not timeouting
    public static String[] blacklist = new String[]{"cub", "rape", "gore", "scat", "death", "toddler", "dacad", "bestiality", "incest", "urine", "human", "fart", "blackkitten"};
        public static boolean check(String toBeChecked) {
            for (int i = 0; i < blacklist.length; i++) {
                if (toBeChecked.contains(blacklist[i])){
                    return true;
                }
            }
            return false;
        }
}
