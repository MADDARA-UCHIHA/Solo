package com.aicallblocker.app.data.remote;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class SupabaseSdkClient_Factory implements Factory<SupabaseSdkClient> {
  @Override
  public SupabaseSdkClient get() {
    return newInstance();
  }

  public static SupabaseSdkClient_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static SupabaseSdkClient newInstance() {
    return new SupabaseSdkClient();
  }

  private static final class InstanceHolder {
    private static final SupabaseSdkClient_Factory INSTANCE = new SupabaseSdkClient_Factory();
  }
}
