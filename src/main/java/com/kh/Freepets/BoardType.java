package com.kh.Freepets;

import lombok.Getter;

@Getter
public enum BoardType
{
    None(0),
    community(1),
    lost(2),
    sitter(3),
    hospitalReview(4),
    notice(5),
    chatting(6),

    end(999);

    private final int value;


    // enum 값 생성할때 각 값에 value 할당시키기,
    BoardType(int i)
    {
        value = i;
    }


    public static BoardType getType(int idx)
    {
        return switch (idx)
        {
            case 0 -> None;
            case 1 -> community;
            case 2 -> lost;
            case 3 -> sitter;
            case 4 -> hospitalReview;
            case 5 -> notice;
            case 6 -> chatting;
            default -> null;
        };
    }

    public static BoardType getType(String name)
    {
        return switch (name)
        {

            case "community" -> community;
            case "lost" -> lost;
            case "sitter" -> sitter;
            case "hospitalReview" -> hospitalReview;
            case "notice" -> notice;
            case "chatting" -> chatting;
            default -> null;
        };
    }

    public static int getTypeCode(BoardType type)
    {
        return switch (type)
        {
            case community -> 1;
            case lost -> 2;
            case sitter -> 3;
            case hospitalReview -> 4;
            case notice -> 5;
            case chatting -> 6;
            default -> -1;
        };
    }

    public static String getTypeName(BoardType type)
    {
        return switch (type)
        {
            case community -> "community";
            case lost -> "lost";
            case sitter -> "sitter";
            case hospitalReview -> "hospitalReview";
            case notice -> "notice";
            case chatting -> "chatting";
            default -> "";
        };
    }


//    public int getValue() {
//        return this.value;
//    }

    static public boolean isExist(BoardType type)
    {
        switch (type)
        {
            case community,
                    lost,
                    sitter,
                    hospitalReview,
                    notice,
                    chatting ->
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
