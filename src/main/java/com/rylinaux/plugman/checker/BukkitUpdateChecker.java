package com.rylinaux.plugman.checker;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.rylinaux.plugman.pojo.UpdateResult;
import org.json.JSONArray;
import org.json.JSONObject;

public class BukkitUpdateChecker implements UpdateChecker {

    private static final String CURSE_PROJECTS_BASE_URL = "https://api.curseforge.com/servermods/projects";

    private static final String CURSE_FILES_BASE_URL = "https://api.curseforge.com/servermods/files";

    public int fetchId(String pluginName) {

        try {

            HttpResponse<JsonNode> response = Unirest.get(CURSE_PROJECTS_BASE_URL + "?search=" + candidateSlug(pluginName)).asJson();
            System.out.println(response);

            JSONArray plugins = response.getBody().getArray();
            System.out.println(plugins);


            for (int i = 0; i < plugins.length(); i++) {
                JSONObject plugin = plugins.getJSONObject(i);
                System.out.println(plugin);

                if (plugin.getString("name").equalsIgnoreCase(pluginName)) {
                    System.out.println("Yup");
                    return plugin.getInt("id");
                }
            }

        } catch (UnirestException e) {
            e.printStackTrace();
        }

        return -1;

    }

    @Override
    public UpdateResult check(String pluginName) {

        int id = fetchId(pluginName);

        if (id < 0) {
            return null;
        }

        UpdateResult result = null;

        try {

            HttpResponse<JsonNode> response = Unirest.get(CURSE_FILES_BASE_URL + "?projectIds=" + id).asJson();

            JSONArray versions = response.getBody().getArray();
            JSONObject latest = versions.getJSONObject(versions.length() - 1);
            
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        return result;

//        HttpResponse<JsonNode> response =
//
//        Updater up = new Updater(PlugMan.getInstance(), id, PlugMan.getInstance().getFile(), Updater.UpdateType.NO_DOWNLOAD, false);

//        return new Updater(PlugMan.getInstance(), id, PlugMan.getInstance().getFile(), Updater.UpdateType.NO_DOWNLOAD, false).getResult();
    }

    /**
     * Takes a plugin name, removes single letter words, and returns the first element when split using a space.
     * <p>
     * Ex: "A Skyblock" returns "skyblock", a term that is probably in the plugin's slug on DBO.
     *
     * @param pluginName the plugin's name.
     * @return a candidate plugin slug.
     */
    private static String candidateSlug(String pluginName) {
        return pluginName.toLowerCase().replaceAll("\\s(\\S)\\s", " ").split(" ")[0];
    }

}