package io.cell.service.habitat.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * Класс для реализации частичного обновления объекта.
 * Updater для объектов, обновляет поля целевого объекта на "не null" поля измененного объекта.
 */
public class EntityConditionMapper {

  private static final Logger LOG = LoggerFactory.getLogger(EntityConditionMapper.class);

  public <T> T update(T targetObj, T changedObj) {
    Arrays.stream(changedObj.getClass().getDeclaredFields())
        .peek(field -> field.setAccessible(true))
        .forEach(field -> {
          try {
            if (field.get(changedObj) != null) {
              field.set(targetObj, field.get(changedObj));
            }
          } catch (IllegalAccessException e) {
            LOG.debug("Не удалось заполнить целевой объект {} новыми данными.", targetObj);
          }
        });
    Arrays.stream(changedObj.getClass().getDeclaredFields())
        .forEach(field -> field.setAccessible(false));
    return targetObj;
  }
}
