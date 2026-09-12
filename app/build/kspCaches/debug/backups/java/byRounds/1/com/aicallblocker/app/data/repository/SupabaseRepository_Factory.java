package com.aicallblocker.app.data.repository;

import com.aicallblocker.app.data.remote.SupabaseSdkClient;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class SupabaseRepository_Factory implements Factory<SupabaseRepository> {
  private final Provider<SupabaseSdkClient> supabaseSdkClientProvider;

  public SupabaseRepository_Factory(Provider<SupabaseSdkClient> supabaseSdkClientProvider) {
    this.supabaseSdkClientProvider = supabaseSdkClientProvider;
  }

  @Override
  public SupabaseRepository get() {
    return newInstance(supabaseSdkClientProvider.get());
  }

  public static SupabaseRepository_Factory create(
      Provider<SupabaseSdkClient> supabaseSdkClientProvider) {
    return new SupabaseRepository_Factory(supabaseSdkClientProvider);
  }

  public static SupabaseRepository newInstance(SupabaseSdkClient supabaseSdkClient) {
    return new SupabaseRepository(supabaseSdkClient);
  }
}
