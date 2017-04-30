package com.rylinaux.plugman.checker;

import com.rylinaux.plugman.pojo.UpdateResult;

public interface UpdateChecker {
    UpdateResult check(String pluginName);
}