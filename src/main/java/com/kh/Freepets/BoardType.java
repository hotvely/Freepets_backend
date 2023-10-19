package com.kh.Freepets;

import lombok.Getter;

@Getter
public enum BoardType
{
    None(0),
    community(1),
    lost(2),
    information(3),
    notice(4),
    customerService(5),
    end(999);


    private final int value;

    // enum 값 생성할때 각 값에 value 할당시키기,
    BoardType(int i) {value = i;}


    public static BoardType getType(int idx)
    {
        return switch (idx)
        {
            case 0 -> None;
            case 1 -> community;
            case 2 -> lost;
            case 3 -> information;
            case 4 -> notice;
            case 5 -> customerService;
            default -> null;
        };
    }

    public static BoardType getType(String name)
    {
        return switch (name)
        {

            case "community" -> community;
            case "lost" -> lost;
            case "information" -> information;
            case "notice" -> notice;
            case "customerService" -> customerService;
            default -> null;
        };
    }

    public static int getTypeCode(BoardType type)
    {
        return switch (type)
        {
            case community -> 1;
            case lost -> 2;
            case information -> 3;
            case notice -> 4;
            case customerService -> 5;
            default -> -1;
        };
    }

//    public int getValue() {
//        return this.value;
//    }

    static public boolean isExist(BoardType type)
    {
        switch (type)
        {
            case community, lost, information, customerService, notice ->
            {
                return true;
            }
            default ->
            {
                return false;
            }
        }


    }
}
