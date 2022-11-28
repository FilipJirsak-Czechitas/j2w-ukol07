package cz.czechitas.java2webapps.ukol7.freemarker;

import freemarker.ext.beans.BeansWrapperConfiguration;
import freemarker.ext.beans.DateModel;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.DefaultObjectWrapperConfiguration;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.Version;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

public class Java8TimeObjectWrapper extends DefaultObjectWrapper {
  public Java8TimeObjectWrapper(Version incompatibleImprovements) {
    super(incompatibleImprovements);
  }

  public Java8TimeObjectWrapper(BeansWrapperConfiguration bwCfg, boolean writeProtected) {
    super(bwCfg, writeProtected);
  }

  public Java8TimeObjectWrapper(DefaultObjectWrapperConfiguration dowCfg, boolean writeProtected) {
    super(dowCfg, writeProtected);
  }

  @Override
  protected TemplateModel handleUnknownType(Object obj) throws TemplateModelException {
    if (obj instanceof LocalDate localDate) {
      Date date = Date.from(localDate.atStartOfDay()
              .atZone(ZoneId.systemDefault())
              .toInstant());
      return new DateModel(date, this);
    }
    if (obj instanceof LocalTime localTime) {
      Date date = Date.from(localTime.atDate(LocalDate.now())
              .atZone(ZoneId.systemDefault())
              .toInstant());
      return new DateModel(date, this);
    }
    if (obj instanceof OffsetDateTime offsetDateTime) {
      Date date = Date.from(offsetDateTime.toInstant());
      return new DateModel(date, this);
    }
    if (obj instanceof ZonedDateTime zonedDateTime) {
      Date date = Date.from(zonedDateTime.toInstant());
      return new DateModel(date, this);
    }
    if (obj instanceof OffsetTime offsetTime) {
      Date date = Date.from(offsetTime.atDate(LocalDate.now())
              .toInstant());
      return new DateModel(date, this);
    }
    if (obj instanceof Record record) {
      return new RecordModel(record);
    }
    return super.handleUnknownType(obj);
  }

  private class RecordModel implements TemplateHashModel {
    private final Record value;

    public RecordModel(Record value) {
      this.value = value;
    }

    @Override
    public TemplateModel get(String key) throws TemplateModelException {
      try {
        Object propertyValue = value.getClass()
                .getMethod(key)
                .invoke(value);
        return Java8TimeObjectWrapper.this.wrap(propertyValue);
      } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
        throw new RuntimeException(e);
      }
    }

    @Override
    public boolean isEmpty() throws TemplateModelException {
      return false;
    }
  }
}
