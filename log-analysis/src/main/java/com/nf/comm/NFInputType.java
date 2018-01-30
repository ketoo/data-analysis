package com.nf.comm;

/**
 * Created by lushenghuang on 20/12/17.
 */
public class NFInputType
{
    public enum PlayerRegisterType
    {
        PlayerRegisterType(0),
        EventTime(1),
        GameAppId(2),
        PlatID(3),
        ClientVersion(4),
        SystemSoftware(5),
        Network(6),
        RegChannel(7),
        CpuHardware(8),
        Memory(9),
        GLRender(10),
        GLVersion(11),
        DeviceId(12);

        PlayerRegisterType(int code)
        {
            this.code = code;
        }
        public Integer getId() {
            return code;
        }
        private int code;
    }


    public enum PlayerLogin
    {
        PlayerLogin(0),
        GameSvrId(1),
        EventTime(2),
        GameAppId(3),
        PlatID(4),
        Openid(5),
        LoginCount(7),
        DeviceId(8);

        PlayerLogin(int code)
        {
            this.code = code;
        }
        public Integer getId() {
            return code;
        }

        private int code;
    }

    public enum PlayerLogout
    {
        PlayerLogout(0),
        GameSvrId(1),
        EventTime(2),
        GameAppId(3),
        PlatID(4),
        Openid(5),
        OnlineTime(6),
        TotalOnlineTime(7);

        PlayerLogout(int code)
        {
            this.code = code;
        }
        public Integer getId() {
            return code;
        }

        private int code;
    }

    public enum PlayerLevelFlow
    {
        PlayerLevelFlow(0),
        GameSvrId(1),
        EventTime(2),
        GameAppId(3),
        PlatID(4),
        Openid(6),
        ExpChange(7),
        Level(8),
        Time(9),
        Reason(10),
        SubReason(11);

        PlayerLevelFlow(int code)
        {
            this.code = code;
        }
        public Integer getId() {
            return code;
        }

        private int code;
    }

    public enum RoundFlow
    {
        RoundFlow(0),
        GameSvrId(1),
        EventTime(2),
        GameAppId(3),
        PlatID(4),
        Openid(6),
        BattleID(7),
        BattleType(8),
        RoundScore(9),
        RoundTime(10),
        FightValue(11),
        Result(12),
        DeadCount(13),
        Level(14);


        RoundFlow(int code)
        {
            this.code = code;
        }
        public Integer getId() {
            return code;
        }

        private int code;
    }

    public enum PlayerBehaviourFlow
    {
        PlayerBehaviourFlow(0),
        GameSvrId(1),
        EventTime(2),
        GameAppId(3),
        PlatID(4),
        Openid(6),
        Level(7),
        RoleID(8),
        DeviceId(9),
        BehaviourId(10),
        MapId(11),
        TypeId(12);

        PlayerBehaviourFlow(int code)
        {
            this.code = code;
        }
        public Integer getId() {
            return code;
        }

        private int code;
    }

    public enum TaskFlow
    {
        TaskFlow(0),
        GameSvrId(1),
        EventTime(2),
        GameAppId(3),
        PlatID(4),
        Openid(6),
        Level(7),
        RoleID(8),
        TaskType(9),
        TaskId(10),
        TaskStatus(11),
        TaskArg1(12),
        TaskArg2(13),
        TaskArg3(14);


        TaskFlow(int code)
        {
            this.code = code;
        }
        public Integer getId() {
            return code;
        }

        private int code;
    }

    public enum BlockFlow
    {
        BlockFlow(0),
        GameSvrId(1),
        EventTime(2),
        GameAppId(3),
        PlatID(4),
        Openid(6),
        Level(7),
        RoleID(8),
        ErrID(9),
        Arg1(10),
        Arg2(11),
        Arg3(12);


        BlockFlow(int code)
        {
            this.code = code;
        }
        public Integer getId() {
            return code;
        }

        private int code;
    }

    public enum ActivityFlow
    {
        ActivityFlow(0),
        GameSvrId(1),
        EventTime(2),
        GameAppId(3),
        PlatID(4),
        Openid(6),
        Level(7),
        RoleID(8),
        ActivityType(9),
        ActivityId(10),
        JoinCount(11),
        Status(12),
        Arg1(13),
        Arg2(14),
        Arg3(15);

        ActivityFlow(int code)
        {
            this.code = code;
        }
        public Integer getId() {
            return code;
        }

        private int code;
    }

    public enum APIFlow
    {
        APIFlow(0),
        GameSvrId(1),
        EventTime(2),
        GameAppId(3),
        PlatID(4),
        Openid(6),
        Level(7),
        RoleID(8),
        LoginCount(9),
        TotalOnlineTime(10),
        PayTime(11),
        TotalMoney(12),
        CostMoney(13),
        APIType(14),
        APIID(15),
        Arg1(16),
        Arg2(17),
        Arg3(18);

        APIFlow(int code)
        {
            this.code = code;
        }
        public Integer getId() {
            return code;
        }

        private int code;
    }

    public enum PayFlow
    {
        PayFlow(0),
        GameSvrId(1),
        EventTime(2),
        GameAppId(3),
        PlatID(4),
        Openid(6),
        Level(7),
        RoleID(8),
        LoginCount(9),
        TotalOnlineTime(10),
        Money(11),
        PayCount(12),
        TotalMoney(13),
        ItemID(14),
        Status(15),
        Arg1(16),
        Arg2(17),
        Arg3(18);

        PayFlow(int code)
        {
            this.code = code;
        }
        public Integer getId() {
            return code;
        }

        private int code;
    }

    public enum MoneyFlow
    {
        MoneyFlow(0),
        GameSvrId(1),
        EventTime(2),
        GameAppId(3),
        PlatID(4),
        Openid(6),
        Level(7),
        AfterMoney(7),
        Money(8),
        Reason(9),
        SubReason(10),
        AddOrReduce(11),
        MoneyType(12);

        MoneyFlow(int code)
        {
            this.code = code;
        }
        public Integer getId() {
            return code;
        }

        private int code;
    }

    public enum ItemFlow
    {
        ItemFlow(0),
        GameSvrId(1),
        EventTime(2),
        GameAppId(3),
        PlatID(4),
        Openid(6),
        Level(7),
        ItemType(8),
        ItemId(9),
        Count(10),
        AfterCount(11),
        Reason(12),
        SubReason(13),
        Money(14),
        MoneyType(15),
        AddOrReduce(16);


        ItemFlow(int code)
        {
            this.code = code;
        }
        public Integer getId() {
            return code;
        }

        private int code;
    }
}
