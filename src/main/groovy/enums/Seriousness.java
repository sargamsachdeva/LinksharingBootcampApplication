package enums;

 public enum Seriousness {

     SERIOUS,
     VERY_SERIOUS,
     CASUAL,
     NOTHING;

     public static Seriousness checkSeriousness(String seriousness) {

         if (seriousness.equals("serious") || seriousness.equals("SERIOUS")) {

             return Seriousness.SERIOUS;

         } else if (seriousness.equals("veryserious") || seriousness.equals("VERY_SERIOUS") ||
                 seriousness.equals("very_serious") || seriousness.equals("VERYSERIOUS"))

             return Seriousness.VERY_SERIOUS;

         else if(seriousness.equals("casual") || seriousness.equals("CASUAL"))

             return Seriousness.CASUAL;

        else
            return Seriousness.NOTHING;
     }
 }
