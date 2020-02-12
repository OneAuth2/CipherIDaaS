package com.portal.commons;

/**
 * Created by 95744 on 2018/6/8.
 */
public class ConstantsCMP {

    //用户行为常量
    public final static class UserBehaviorConstant {
        public static  final int   APPLICATION_VISIT=1;//应用访问
        public  static  final int HIT_STRATEGY=2;//命中策略
        public  static  final int ACCOUNT_MAINTAIN=3;//账号维护


      /*  public final static int LOGIN_IN_OUT = 1;
        public final static int APPLY_LOGIN = 2;
        public final static int PERSONAL_CHANGE = 3;
        public final static int BINDND_APP = 4;
        public final static int BIND_ACOUNT = 5;
        public final static int RESET_PASSWORD = 6;
        public final static int NICKNAME_UPDATE = 7;
        public final static int TELEPHONE_UPDATE = 8;*/

    }


    //应用常量
    public final static class ApplicationConstant {
        public final static int AUTHORIZE = 1;
        public final static int LOGIN = 2;

    }


    //应用常量
    public final static class SendMsgConstant {
        public final static int EMAIL = 1;
        public final static int SMS = 2;

    }



    public final static class ApplySubType{
        //1是从从账号与主账号一致0是不一致
        public final static int isSame = 1;
        public final static int isNoSame = 0;
    }

    public final static class Code {
        public final static int SUCCESS = 0;
        public final static int FAIL = 1;
    }
    }







