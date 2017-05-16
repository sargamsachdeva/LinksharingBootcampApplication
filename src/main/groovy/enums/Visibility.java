package enums;

public enum Visibility {

    PUBLIC,
    PRIVATE;


    public static Visibility convertIntoEnum(String visibility) {

        if(visibility.equals("public") || visibility.equals("PUBLIC")) {

            return Visibility.PUBLIC ;

        }

        else
            return Visibility.PRIVATE ;
    }
}