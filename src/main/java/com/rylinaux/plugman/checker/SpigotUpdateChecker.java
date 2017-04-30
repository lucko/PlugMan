package com.rylinaux.plugman.checker;

import com.rylinaux.plugman.pojo.UpdateResult;

public class SpigotUpdateChecker implements UpdateChecker {

    public static final String API_BASE_URL = "https://api.spiget.org/v2/";

    @Override
    public int getId(String pluginName) {
        return 0;
    }

    @Override
    public UpdateResult fetchStatus(int id) {
        return null;
    }

}