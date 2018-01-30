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
        LoginCount(6),
        DeviceId(7);

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
        Openid(5),
        ExpChange(6),
        Level(7),
        Time(8),
        Reason(9),
        SubReason(10);

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
        Openid(5),
        BattleID(6),
        BattleType(7),
        RoundScore(8),
        RoundTime(9),
        FightValue(10),
        Result(11),
        DeadCount(12),
        Level(13);


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
        Openid(5),
        Level(6),
        RoleID(7),
        DeviceId(8),
        BehaviourId(9),
        MapId(10),
        TypeId(11);

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
        Openid(5),
        Level(6),
        RoleID(7),
        TaskType(8),
        TaskId(9),
        TaskStatus(10),
        TaskArg1(11),
        TaskArg2(12),
        TaskArg3(13);


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
        Openid(5),
        Level(6),
        RoleID(7),
        ErrID(8),
        Arg1(9),
        Arg2(10),
        Arg3(11);


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
        Openid(5),
        Level(6),
        RoleID(7),
        ActivityType(8),
        ActivityId(9),
        JoinCount(10),
        Status(11),
        Arg1(12),
        Arg2(13),
        Arg3(14);

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
        Openid(5),
        Level(6),
        RoleID(7),
        LoginCount(8),
        TotalOnlineTime(9),
        PayTime(10),
        TotalMoney(11),
        CostMoney(12),
        APIType(13),
        APIID(14),
        Arg1(15),
        Arg2(16),
        Arg3(17);

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
        Openid(5),
        Level(6),
        RoleID(7),
        LoginCount(8),
        TotalOnlineTime(9),
        Money(10),
        PayCount(11),
        TotalMoney(12),
        ItemID(13),
        Status(14),
        Arg1(15),
        Arg2(16),
        Arg3(17);

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
        Openid(5),
        Level(6),
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
        Openid(5),
        Level(6),
        ItemType(7),
        ItemId(8),
        Count(9),
        AfterCount(10),
        Reason(11),
        SubReason(12),
        Money(13),
        MoneyType(14),
        AddOrReduce(15);


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
