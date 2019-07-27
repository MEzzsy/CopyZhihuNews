package com.mezzsy.copyzhihunews.state;

public class LoginContext {
    UserState mState = new LogoutState();

    //  单例模式创建LoginContext
    private LoginContext() {
    }

    public static LoginContext getInstance() {
        return SingletonHolder.singleton;
    }

    public UserState getState() {
        return mState;
    }

    //设置状态
    public void setState(UserState state) {
        this.mState = state;
    }

    private static class SingletonHolder {
        private static final LoginContext singleton = new LoginContext();
    }
}
