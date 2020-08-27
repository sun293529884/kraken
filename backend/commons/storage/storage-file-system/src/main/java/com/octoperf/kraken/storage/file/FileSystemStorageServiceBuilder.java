package com.octoperf.kraken.storage.file;

import com.octoperf.kraken.security.entity.owner.Owner;
import com.octoperf.kraken.tools.event.bus.EventBus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
final class FileSystemStorageServiceBuilder implements StorageServiceBuilder {

  @NonNull OwnerToPath ownerToPath;
  @NonNull EventBus eventBus;

  @Override
  public StorageService build(final Owner owner) {
    final var root = ownerToPath.apply(owner);
    final var service = new FileSystemStorageService(owner, root, new FileSystemPathToStorageNode(root), ownerToPath, eventBus);
    return service;
  }

}
