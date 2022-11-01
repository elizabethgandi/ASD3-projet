package org.example;

public class Color {

    public  static  Color yellow ;
    public  static  Color black ;
    public  static  Color blue ;
    public  static  Color white ;
    public  static  Color red ;
    public static int sameColorProbability;
    public static int randomColorChoose = (1-sameColorProbability)/5;

    public enum color {yellow, black, blue, white, red};

    //test
    Color chooseRandomColor1(Color parentColor){
        int randomNumber = (int) (Math.random() * 5);
        return intCorrespondingToColor(randomNumber);
    }

    Color chooseRandomColor2(){
        int randomNumber = (int) (Math.random() * 5);
        return intCorrespondingToColor(randomNumber);
    }
    public Color intCorrespondingToColor(int number) {

        switch (number) {

            case 1:
                return yellow;
            case 2:
                return black;

            case 3:
                return blue;

            case 4:
                return white;

            case 5:
                return red;

            default:
                return black; //default color
        }

    }

}
