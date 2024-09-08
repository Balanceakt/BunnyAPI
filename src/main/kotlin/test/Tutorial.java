package test;

import dev.motomoto.bunnyAPI.BunnyAPI;

public class Tutorial {
    public static void main(String args[]) {

        Integer i = BunnyAPI.Companion.getInstance().getDataHandleSimpleArgs().readTableCountKeys("CrazySucht", "Messages");
        System.out.println(i);
        
       String test =  BunnyAPI.Companion.getInstance().getDataHandleSimpleArgs().readColorCodes("CrazySucht", "Messages", "prefix", 0);
       System.out.println(test);
       
    }
}
