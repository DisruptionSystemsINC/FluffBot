package main.E6BotIntegration.Blacklist;

public class Blacklist {
    public static String[] blacklist = new String[]{"cub", "rape", "gore", "scat", "death", "toddler", "young", "dacad", "bestiality"};
        public boolean check(String toBeChecked) {
            for (int i = 0; i < blacklist.length; i++) {
                if (toBeChecked.contains(blacklist[i])){
                    return true;
                }
            }
            return false;
        }
}
