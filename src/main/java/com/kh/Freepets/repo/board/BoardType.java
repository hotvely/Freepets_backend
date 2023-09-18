package com.kh.Freepets.repo.board;

public enum BoardType
{
    community(1),
    freeMarket(2),
    information(3),
    notice(4),
    customerService(5),
    end(999);


    private final int value;

    // enum 값 생성할때 각 값에 value 할당시키기,
    BoardType(int i) { value = i;}

    public int getValue() {
        return this.value;
    }

//    public static BoardType getType(String idx)
//    {
//        return switch (idx)
//        {
//            case "community" -> community;
//            case "freeMarket" -> freeMarket;
//            case "information" -> information;
//            case "notice" -> notice;
//            case "customerService" -> customerService;
//            default -> null;
//        };
//    }
    public static BoardType getType(int idx)
    {
        return switch (idx)
        {
            case 0 -> community;
            case 1 -> freeMarket;
            case 2 -> information;
            case 3 -> notice;
            case 4 -> customerService;
            default -> null;
        };
    }
}
