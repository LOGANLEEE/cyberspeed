package com.slotmachine;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.InputStream;

public class ConfigLoader {
  private final JSONObject config;

  public ConfigLoader(String path) {
    try (InputStream is = getClass().getClassLoader().getResourceAsStream(path)) {
      if (is == null) {
        throw new RuntimeException("Config file not found: " + path);
      }
      JSONTokener tokener = new JSONTokener(is);
      config = new JSONObject(tokener);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public JSONObject getConfig() {
    return config;
  }
}